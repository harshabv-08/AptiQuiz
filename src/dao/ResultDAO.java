package dao;

import model.Question;
import model.QuizResult;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ResultDAO {

    // =====================================================
    // SAVE COMPLETE QUIZ RESULT
    // =====================================================

    public boolean saveResult(
            QuizResult result,
            List<Question> questions,
            String[] selectedAnswers) {

        String attemptSql =
                "INSERT INTO quiz_attempts " +
                "(user_id, category_id, total_questions, " +
                "correct_answers, wrong_answers, unanswered, " +
                "score, percentage) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        String answerSql =
                "INSERT INTO quiz_answers " +
                "(attempt_id, question_id, selected_answer, " +
                "correct_answer, is_correct) " +
                "VALUES (?, ?, ?, ?, ?)";

        Connection connection = null;

        try {

            connection =
                    DatabaseConnection.getConnection();

            connection.setAutoCommit(false);

            int attemptId;

            // =================================================
            // SAVE QUIZ ATTEMPT
            // =================================================

            try (PreparedStatement statement =
                         connection.prepareStatement(
                                 attemptSql,
                                 Statement.RETURN_GENERATED_KEYS
                         )) {

                statement.setInt(
                        1,
                        result.getUserId()
                );

                statement.setInt(
                        2,
                        result.getCategoryId()
                );

                statement.setInt(
                        3,
                        result.getTotalQuestions()
                );

                statement.setInt(
                        4,
                        result.getCorrectAnswers()
                );

                statement.setInt(
                        5,
                        result.getWrongAnswers()
                );

                statement.setInt(
                        6,
                        result.getUnanswered()
                );

                statement.setInt(
                        7,
                        result.getScore()
                );

                statement.setDouble(
                        8,
                        result.getPercentage()
                );

                statement.executeUpdate();

                try (ResultSet keys =
                             statement.getGeneratedKeys()) {

                    if (!keys.next()) {

                        throw new SQLException(
                                "Unable to retrieve quiz attempt ID."
                        );
                    }

                    attemptId =
                            keys.getInt(1);
                }
            }

            // =================================================
            // SAVE INDIVIDUAL ANSWERS
            // =================================================

            try (PreparedStatement statement =
                         connection.prepareStatement(answerSql)) {

                for (int i = 0;
                     i < questions.size();
                     i++) {

                    Question question =
                            questions.get(i);

                    String selectedAnswer =
                            selectedAnswers[i];

                    String correctAnswer =
                            question.getCorrectAnswer();

                    boolean isCorrect =
                            selectedAnswer != null
                                    && selectedAnswer.equals(
                                    correctAnswer
                            );

                    statement.setInt(
                            1,
                            attemptId
                    );

                    statement.setInt(
                            2,
                            question.getQuestionId()
                    );

                    if (selectedAnswer == null) {

                        statement.setNull(
                                3,
                                Types.CHAR
                        );

                    } else {

                        statement.setString(
                                3,
                                selectedAnswer
                        );
                    }

                    statement.setString(
                            4,
                            correctAnswer
                    );

                    statement.setBoolean(
                            5,
                            isCorrect
                    );

                    statement.addBatch();
                }

                statement.executeBatch();
            }

            connection.commit();

            return true;

        } catch (Exception e) {

            if (connection != null) {

                try {
                    connection.rollback();

                } catch (SQLException rollbackException) {
                    rollbackException.printStackTrace();
                }
            }

            e.printStackTrace();

            return false;

        } finally {

            if (connection != null) {

                try {

                    connection.setAutoCommit(true);
                    connection.close();

                } catch (SQLException closeException) {
                    closeException.printStackTrace();
                }
            }
        }
    }

    // =====================================================
    // GET USER QUIZ HISTORY
    // =====================================================

    public List<QuizResult> getUserResults(
            int userId) {

        List<QuizResult> results =
                new ArrayList<>();

        String sql =
                "SELECT * FROM quiz_attempts " +
                "WHERE user_id = ? " +
                "ORDER BY attempt_date DESC";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    userId
            );

            ResultSet resultSet =
                    statement.executeQuery();

            while (resultSet.next()) {

                QuizResult result =
                        new QuizResult(
                                resultSet.getInt("attempt_id"),
                                resultSet.getInt("user_id"),
                                resultSet.getInt("category_id"),
                                resultSet.getInt("total_questions"),
                                resultSet.getInt("correct_answers"),
                                resultSet.getInt("wrong_answers"),
                                resultSet.getInt("unanswered"),
                                resultSet.getInt("score"),
                                resultSet.getDouble("percentage")
                        );

                results.add(result);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return results;
    }

    // =====================================================
    // GET TOTAL QUESTION COUNT
    // =====================================================

    public int getTotalQuestionCount() {

        String sql =
                "SELECT COUNT(*) FROM questions";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql);

                ResultSet resultSet =
                        statement.executeQuery()
        ) {

            if (resultSet.next()) {

                return resultSet.getInt(1);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return 0;
    }

    // =====================================================
    // GET CATEGORY COUNT
    // =====================================================

    public int getCategoryCount() {

        String sql =
                "SELECT COUNT(*) FROM categories";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql);

                ResultSet resultSet =
                        statement.executeQuery()
        ) {

            if (resultSet.next()) {

                return resultSet.getInt(1);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return 0;
    }

    // =====================================================
    // GET DIFFICULTY COUNT
    // =====================================================

    public int getDifficultyCount() {

        String sql =
                "SELECT COUNT(DISTINCT difficulty) " +
                "FROM questions";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql);

                ResultSet resultSet =
                        statement.executeQuery()
        ) {

            if (resultSet.next()) {

                return resultSet.getInt(1);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return 0;
    }
}