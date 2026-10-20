package ui;

import model.Question;
import model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class AnswerReviewFrame extends JFrame {

    private final User loggedInUser;
    private final List<Question> questions;
    private final String[] selectedAnswers;

    private int currentIndex = 0;

    private JLabel progressLabel;
    private JLabel questionLabel;
    private JLabel yourAnswerLabel;
    private JLabel correctAnswerLabel;
    private JLabel statusLabel;
    private JTextArea explanationArea;

    public AnswerReviewFrame(
            User user,
            List<Question> questions,
            String[] selectedAnswers) {

        this.loggedInUser = user;
        this.questions = questions;
        this.selectedAnswers = selectedAnswers;

        UITheme.setupFrame(
                this,
                "AptiQuiz - Answer Review",
                1000,
                700
        );

        createUI();
        loadQuestion();
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

        topBar.setBackground(Color.WHITE);

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

        progressLabel =
                new JLabel();

        progressLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        14
                )
        );

        progressLabel.setForeground(
                UITheme.BLUE
        );

        topBar.add(
                logo,
                BorderLayout.WEST
        );

        topBar.add(
                progressLabel,
                BorderLayout.EAST
        );

        background.add(
                topBar,
                BorderLayout.NORTH
        );

        // =================================================
        // MAIN CONTENT
        // =================================================

        JPanel content =
                new JPanel();

        content.setOpaque(false);

        content.setBorder(
                new EmptyBorder(
                        30,
                        70,
                        25,
                        70
                )
        );

        content.setLayout(
                new BoxLayout(
                        content,
                        BoxLayout.Y_AXIS
                )
        );

        // =================================================
        // PAGE TITLE
        // =================================================

        JLabel title =
                new JLabel(
                        "Answer Review"
                );

        title.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        30
                )
        );

        title.setForeground(
                UITheme.NAVY
        );

        title.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JLabel subtitle =
                new JLabel(
                        "Check your answers and understand the correct solution."
                );

        subtitle.setFont(
                UITheme.BODY
        );

        subtitle.setForeground(
                UITheme.TEXT_SECONDARY
        );

        subtitle.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        content.add(title);

        content.add(
                Box.createVerticalStrut(5)
        );

        content.add(subtitle);

        content.add(
                Box.createVerticalStrut(20)
        );

        // =================================================
        // QUESTION CARD
        // =================================================

        JPanel questionCard =
                UITheme.createCard();

        questionCard.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        questionCard.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        250
                )
        );

        questionCard.setLayout(
                new BorderLayout(
                        10,
                        15
                )
        );

        questionLabel =
                new JLabel();

        questionLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        19
                )
        );

        questionLabel.setForeground(
                UITheme.TEXT
        );

        questionCard.add(
                questionLabel,
                BorderLayout.NORTH
        );

        JPanel answerPanel =
                new JPanel(
                        new GridLayout(
                                3,
                                1,
                                8,
                                8
                        )
                );

        answerPanel.setOpaque(false);

        yourAnswerLabel =
                createInfoLabel();

        correctAnswerLabel =
                createInfoLabel();

        statusLabel =
                createInfoLabel();

        answerPanel.add(
                yourAnswerLabel
        );

        answerPanel.add(
                correctAnswerLabel
        );

        answerPanel.add(
                statusLabel
        );

        questionCard.add(
                answerPanel,
                BorderLayout.CENTER
        );

        content.add(questionCard);

        content.add(
                Box.createVerticalStrut(18)
        );

        // =================================================
        // EXPLANATION CARD
        // =================================================

        JPanel explanationCard =
                UITheme.createCard();

        explanationCard.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        explanationCard.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        145
                )
        );

        explanationCard.setLayout(
                new BorderLayout(
                        10,
                        8
                )
        );

        JLabel explanationTitle =
                new JLabel(
                        "Explanation"
                );

        explanationTitle.setFont(
                UITheme.SUBHEADING
        );

        explanationTitle.setForeground(
                UITheme.NAVY
        );

        explanationArea =
                new JTextArea();

        explanationArea.setFont(
                UITheme.BODY
        );

        explanationArea.setForeground(
                UITheme.TEXT_SECONDARY
        );

        explanationArea.setBackground(
                Color.WHITE
        );

        explanationArea.setLineWrap(true);
        explanationArea.setWrapStyleWord(true);
        explanationArea.setEditable(false);
        explanationArea.setBorder(null);

        explanationCard.add(
                explanationTitle,
                BorderLayout.NORTH
        );

        explanationCard.add(
                explanationArea,
                BorderLayout.CENTER
        );

        content.add(
                explanationCard
        );

        background.add(
                content,
                BorderLayout.CENTER
        );

        // =================================================
        // BOTTOM NAVIGATION
        // =================================================

        JPanel bottom =
                new JPanel(
                        new BorderLayout()
                );

        bottom.setBackground(
                Color.WHITE
        );

        bottom.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(
                                1,
                                0,
                                0,
                                0,
                                UITheme.BORDER
                        ),
                        new EmptyBorder(
                                14,
                                35,
                                14,
                                35
                        )
                )
        );

        JButton previousButton =
                UITheme.secondaryButton(
                        "← Previous"
                );

        JButton nextButton =
                UITheme.secondaryButton(
                        "Next →"
                );

        JButton dashboardButton =
                UITheme.primaryButton(
                        "Back to Dashboard"
                );

        previousButton.addActionListener(e -> {

            if (currentIndex > 0) {

                currentIndex--;

                loadQuestion();
            }
        });

        nextButton.addActionListener(e -> {

            if (currentIndex <
                    questions.size() - 1) {

                currentIndex++;

                loadQuestion();
            }
        });

        dashboardButton.addActionListener(e -> {

            DashboardFrame dashboardFrame =
                    new DashboardFrame(
                            loggedInUser
                    );

            dashboardFrame.setVisible(true);

            dispose();
        });

        bottom.add(
                previousButton,
                BorderLayout.WEST
        );

        JPanel centerButtons =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                10,
                                0
                        )
                );

        centerButtons.setOpaque(false);

        centerButtons.add(
                nextButton
        );

        centerButtons.add(
                dashboardButton
        );

        bottom.add(
                centerButtons,
                BorderLayout.CENTER
        );

        background.add(
                bottom,
                BorderLayout.SOUTH
        );

        add(background);
    }

    // =====================================================
    // LOAD QUESTION
    // =====================================================

    private void loadQuestion() {

        Question question =
                questions.get(currentIndex);

        progressLabel.setText(
                "Question "
                        + (currentIndex + 1)
                        + " of "
                        + questions.size()
        );

        questionLabel.setText(
                "<html><body style='width:760px'>"
                        + question.getQuestion()
                        + "</body></html>"
        );

        // -----------------------------------------------
        // USER ANSWER
        // -----------------------------------------------

        String selected =
                selectedAnswers[currentIndex];

        String selectedText;

        if (selected == null) {

            selectedText =
                    "Your answer: Not answered";

        } else {

            selectedText =
                    "Your answer: "
                            + getOptionText(
                            question,
                            selected
                    );
        }

        yourAnswerLabel.setText(
                selectedText
        );

        // -----------------------------------------------
        // CORRECT ANSWER
        // -----------------------------------------------

        String correct =
                question.getCorrectAnswer();

        correctAnswerLabel.setText(
                "Correct answer: "
                        + getOptionText(
                        question,
                        correct
                )
        );

        // -----------------------------------------------
        // STATUS
        // -----------------------------------------------

        if (selected == null) {

            statusLabel.setText(
                    "Status: Unanswered"
            );

            statusLabel.setForeground(
                    UITheme.TEXT_SECONDARY
            );

        } else if (
                selected.equals(correct)
        ) {

            statusLabel.setText(
                    "Status: Correct ✓"
            );

            statusLabel.setForeground(
                    UITheme.SUCCESS
            );

        } else {

            statusLabel.setText(
                    "Status: Incorrect"
            );

            statusLabel.setForeground(
                    UITheme.ERROR
            );
        }

        // -----------------------------------------------
        // EXPLANATION
        // -----------------------------------------------

        String explanation =
                question.getExplanation();

        if (explanation == null ||
                explanation.trim().isEmpty()) {

            explanationArea.setText(
                    "No explanation is available for this question."
            );

        } else {

            explanationArea.setText(
                    explanation
            );
        }

        explanationArea.setCaretPosition(0);
    }

    // =====================================================
    // INFORMATION LABEL
    // =====================================================

    private JLabel createInfoLabel() {

        JLabel label =
                new JLabel();

        label.setFont(
                UITheme.BODY
        );

        label.setForeground(
                UITheme.TEXT
        );

        return label;
    }

    // =====================================================
    // OPTION TEXT
    // =====================================================

    private String getOptionText(
            Question question,
            String option) {

        String text;

        switch (option) {

            case "A":
                text = question.getOptionA();
                break;

            case "B":
                text = question.getOptionB();
                break;

            case "C":
                text = question.getOptionC();
                break;

            case "D":
                text = question.getOptionD();
                break;

            default:
                text = "Unknown";
        }

        return option + ". " + text;
    }
}