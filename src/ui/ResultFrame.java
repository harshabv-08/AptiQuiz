package ui;

import dao.ResultDAO;
import model.Question;
import model.QuizResult;
import model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class ResultFrame extends JFrame {

    private final User loggedInUser;
    private final List<Question> questions;
    private final String[] selectedAnswers;
    private final int categoryId;

    private final int totalQuestions;
    private final int correctAnswers;
    private final int wrongAnswers;
    private final int unanswered;
    private final int score;
    private final double percentage;

    public ResultFrame(
            User user,
            List<Question> questions,
            String[] selectedAnswers,
            int categoryId,
            int totalQuestions,
            int correctAnswers,
            int wrongAnswers,
            int unanswered,
            int score,
            double percentage) {

        this.loggedInUser = user;
        this.questions = questions;
        this.selectedAnswers = selectedAnswers;
        this.categoryId = categoryId;

        this.totalQuestions = totalQuestions;
        this.correctAnswers = correctAnswers;
        this.wrongAnswers = wrongAnswers;
        this.unanswered = unanswered;
        this.score = score;
        this.percentage = percentage;

        UITheme.setupFrame(
                this,
                "AptiQuiz - Quiz Result",
                1000,
                700
        );

        saveResult();
        createUI();
    }

    // =====================================================
    // SAVE RESULT
    // =====================================================

    private void saveResult() {

        QuizResult result =
                new QuizResult(
                        0,
                        loggedInUser.getUserId(),
                        categoryId,
                        totalQuestions,
                        correctAnswers,
                        wrongAnswers,
                        unanswered,
                        score,
                        percentage
                );

        ResultDAO resultDAO =
                new ResultDAO();

        boolean saved =
                resultDAO.saveResult(
                        result,
                        questions,
                        selectedAnswers
                );

        if (!saved) {

            JOptionPane.showMessageDialog(
                    this,
                    "The quiz result could not be saved.",
                    "Database Warning",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    // =====================================================
    // CREATE UI
    // =====================================================

    private void createUI() {

        JPanel background =
                UITheme.createBackgroundPanel();

        background.setLayout(
                new BorderLayout()
        );

        // =================================================
        // TOP BAR
        // =================================================

        JPanel topBar =
                new JPanel(
                        new BorderLayout()
                );

        topBar.setBackground(
                Color.WHITE
        );

        topBar.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(
                                0,
                                0,
                                1,
                                0,
                                UITheme.BORDER
                        ),
                        new EmptyBorder(
                                18,
                                35,
                                18,
                                35
                        )
                )
        );

        JLabel logo =
                new JLabel("AptiQuiz");

        logo.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        23
                )
        );

        logo.setForeground(
                UITheme.NAVY
        );

        JLabel resultLabel =
                new JLabel("QUIZ RESULT");

        resultLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        13
                )
        );

        resultLabel.setForeground(
                UITheme.BLUE
        );

        topBar.add(
                logo,
                BorderLayout.WEST
        );

        topBar.add(
                resultLabel,
                BorderLayout.EAST
        );

        background.add(
                topBar,
                BorderLayout.NORTH
        );

        // =================================================
        // CONTENT
        // =================================================

        JPanel content =
                new JPanel();

        content.setOpaque(false);

        content.setBorder(
                new EmptyBorder(
                        35,
                        70,
                        35,
                        70
                )
        );

        content.setLayout(
                new BoxLayout(
                        content,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel title =
                new JLabel(
                        "Quiz completed!"
                );

        title.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        32
                )
        );

        title.setForeground(
                UITheme.NAVY
        );

        title.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        JLabel subtitle =
                new JLabel(
                        "Great work, "
                                + loggedInUser.getName()
                                + ". Here's your performance."
                );

        subtitle.setFont(
                UITheme.BODY
        );

        subtitle.setForeground(
                UITheme.TEXT_SECONDARY
        );

        subtitle.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        content.add(title);

        content.add(
                Box.createVerticalStrut(7)
        );

        content.add(subtitle);

        content.add(
                Box.createVerticalStrut(25)
        );

        // =================================================
        // SCORE CARD
        // =================================================

        JPanel scoreCard =
                UITheme.createCard();

        scoreCard.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        scoreCard.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        150
                )
        );

        scoreCard.setLayout(
                new BorderLayout()
        );

        JLabel scoreValue =
                new JLabel(
                        String.format(
                                "%.0f%%",
                                percentage
                        ),
                        SwingConstants.CENTER
                );

        scoreValue.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        42
                )
        );

        scoreValue.setForeground(
                UITheme.BLUE
        );

        JLabel scoreText =
                new JLabel(
                        "Overall Score",
                        SwingConstants.CENTER
                );

        scoreText.setFont(
                UITheme.SUBHEADING
        );

        scoreText.setForeground(
                UITheme.NAVY
        );

        JPanel scoreCenter =
                new JPanel();

        scoreCenter.setOpaque(false);

        scoreCenter.setLayout(
                new BoxLayout(
                        scoreCenter,
                        BoxLayout.Y_AXIS
                )
        );

        scoreValue.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        scoreText.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        scoreCenter.add(scoreValue);

        scoreCenter.add(
                Box.createVerticalStrut(3)
        );

        scoreCenter.add(scoreText);

        scoreCard.add(
                scoreCenter,
                BorderLayout.CENTER
        );

        content.add(scoreCard);

        content.add(
                Box.createVerticalStrut(20)
        );

        // =================================================
        // STAT CARDS
        // =================================================

        JPanel statsPanel =
                new JPanel(
                        new GridLayout(
                                1,
                                4,
                                15,
                                0
                        )
                );

        statsPanel.setOpaque(false);

        statsPanel.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        105
                )
        );

        statsPanel.add(
                createStatCard(
                        "Total",
                        String.valueOf(
                                totalQuestions
                        )
                )
        );

        statsPanel.add(
                createStatCard(
                        "Correct",
                        String.valueOf(
                                correctAnswers
                        )
                )
        );

        statsPanel.add(
                createStatCard(
                        "Wrong",
                        String.valueOf(
                                wrongAnswers
                        )
                )
        );

        statsPanel.add(
                createStatCard(
                        "Unanswered",
                        String.valueOf(
                                unanswered
                        )
                )
        );

        content.add(statsPanel);

        content.add(
                Box.createVerticalStrut(25)
        );

        // =================================================
        // PERFORMANCE MESSAGE
        // =================================================

        JLabel performanceLabel =
                new JLabel(
                        getPerformanceMessage(),
                        SwingConstants.CENTER
                );

        performanceLabel.setFont(
                UITheme.BODY
        );

        performanceLabel.setForeground(
                UITheme.TEXT_SECONDARY
        );

        performanceLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        content.add(
                performanceLabel
        );

        content.add(
                Box.createVerticalStrut(22)
        );

        // =================================================
        // BUTTONS
        // =================================================

        JPanel actions =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                12,
                                0
                        )
                );

        actions.setOpaque(false);

        JButton reviewButton =
                UITheme.secondaryButton(
                        "Review Answers"
                );

        JButton anotherQuizButton =
                UITheme.secondaryButton(
                        "Take Another Quiz"
                );

        JButton dashboardButton =
                UITheme.primaryButton(
                        "Back to Dashboard"
                );

        reviewButton.addActionListener(e -> {

            AnswerReviewFrame reviewFrame =
                    new AnswerReviewFrame(
                            loggedInUser,
                            questions,
                            selectedAnswers
                    );

            reviewFrame.setVisible(true);

            dispose();
        });

        anotherQuizButton.addActionListener(e -> {

            QuizSetupFrame quizSetupFrame =
                    new QuizSetupFrame(
                            loggedInUser
                    );

            quizSetupFrame.setVisible(true);

            dispose();
        });

        dashboardButton.addActionListener(e -> {

            DashboardFrame dashboardFrame =
                    new DashboardFrame(
                            loggedInUser
                    );

            dashboardFrame.setVisible(true);

            dispose();
        });

        actions.add(reviewButton);
        actions.add(anotherQuizButton);
        actions.add(dashboardButton);

        content.add(actions);

        background.add(
                content,
                BorderLayout.CENTER
        );

        add(background);
    }

    // =====================================================
    // PERFORMANCE MESSAGE
    // =====================================================

    private String getPerformanceMessage() {

        if (percentage >= 80) {

            return "Excellent performance! You're making strong progress.";

        } else if (percentage >= 60) {

            return "Good job! Keep practicing to improve your score.";

        } else if (percentage >= 40) {

            return "Good attempt. Review your answers and keep practicing.";

        } else {

            return "Keep practicing. Every attempt helps you improve.";
        }
    }

    // =====================================================
    // STAT CARD
    // =====================================================

    private JPanel createStatCard(
            String title,
            String value) {

        JPanel card =
                UITheme.createCard();

        card.setLayout(
                new BorderLayout()
        );

        JLabel valueLabel =
                new JLabel(
                        value,
                        SwingConstants.CENTER
                );

        valueLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        26
                )
        );

        valueLabel.setForeground(
                UITheme.NAVY
        );

        JLabel titleLabel =
                new JLabel(
                        title,
                        SwingConstants.CENTER
                );

        titleLabel.setFont(
                UITheme.SMALL
        );

        titleLabel.setForeground(
                UITheme.TEXT_SECONDARY
        );

        JPanel panel =
                new JPanel();

        panel.setOpaque(false);

        panel.setLayout(
                new BoxLayout(
                        panel,
                        BoxLayout.Y_AXIS
                )
        );

        valueLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        titleLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        panel.add(valueLabel);

        panel.add(
                Box.createVerticalStrut(4)
        );

        panel.add(titleLabel);

        card.add(
                panel,
                BorderLayout.CENTER
        );

        return card;
    }
}