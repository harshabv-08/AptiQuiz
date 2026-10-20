package dao;

import model.Question;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class QuestionDAO {

    public List<Question> getRandomQuestions(
            int categoryId,
            String difficulty,
            int limit) {

        List<Question> questions =
                new ArrayList<>();

        String sql =
                "SELECT * FROM questions " +
                "WHERE category_id = ? " +
                "AND difficulty = ? " +
                "ORDER BY RAND() LIMIT ?";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    categoryId
            );

            statement.setString(
                    2,
                    difficulty
            );

            statement.setInt(
                    3,
                    limit
            );

            ResultSet resultSet =
                    statement.executeQuery();

            while (resultSet.next()) {

                Question question =
                        new Question(
                                resultSet.getInt(
                                        "question_id"
                                ),

                                resultSet.getInt(
                                        "category_id"
                                ),

                                resultSet.getString(
                                        "question"
                                ),

                                resultSet.getString(
                                        "option_a"
                                ),

                                resultSet.getString(
                                        "option_b"
                                ),

                                resultSet.getString(
                                        "option_c"
                                ),

                                resultSet.getString(
                                        "option_d"
                                ),

                                resultSet.getString(
                                        "correct_answer"
                                ),

                                resultSet.getString(
                                        "difficulty"
                                ),

                                resultSet.getString(
                                        "explanation"
                                )
                        );

                questions.add(question);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return questions;
    }

    public int getQuestionCount(
            int categoryId,
            String difficulty) {

        String sql =
                "SELECT COUNT(*) FROM questions " +
                "WHERE category_id = ? " +
                "AND difficulty = ?";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    categoryId
            );

            statement.setString(
                    2,
                    difficulty
            );

            ResultSet resultSet =
                    statement.executeQuery();

            if (resultSet.next()) {

                return resultSet.getInt(1);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return 0;
    }
}