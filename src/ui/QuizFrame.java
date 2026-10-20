package ui;

import model.Question;
import model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class QuizFrame extends JFrame {

    private final User loggedInUser;
    private final List<Question> questions;
    private final int categoryId;

    private int currentQuestionIndex = 0;

    private JLabel progressLabel;
    private JLabel categoryLabel;
    private JLabel questionLabel;

    private JRadioButton optionA;
    private JRadioButton optionB;
    private JRadioButton optionC;
    private JRadioButton optionD;

    private ButtonGroup optionGroup;

    private String[] selectedAnswers;

    public QuizFrame(
            User user,
            List<Question> questions,
            int categoryId) {

        this.loggedInUser = user;
        this.questions = questions;
        this.categoryId = categoryId;

        selectedAnswers =
                new String[questions.size()];

        UITheme.setupFrame(
                this,
                "AptiQuiz - Practice Quiz",
                1000,
                700
        );

        createUI();
        loadQuestion();
    }

    private void createUI() {

        JPanel background =
                UITheme.createBackgroundPanel();

        background.setLayout(
                new BorderLayout()
        );

        // =====================================================
        // TOP BAR
        // =====================================================

        JPanel topBar =
                new JPanel(
                        new BorderLayout()
                );

        topBar.setBackground(Color.WHITE);

        topBar.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(
                                0, 0, 1, 0,
                                UITheme.BORDER
                        ),
                        new EmptyBorder(
                                18, 35, 18, 35
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

        // =====================================================
        // MAIN CONTENT
        // =====================================================

        JPanel content =
                new JPanel();

        content.setOpaque(false);

        content.setBorder(
                new EmptyBorder(
                        35, 70, 25, 70
                )
        );

        content.setLayout(
                new BorderLayout(
                        20,
                        20
                )
        );

        // =====================================================
        // QUESTION CARD
        // =====================================================

        JPanel questionCard =
                UITheme.createCard();

        questionCard.setLayout(
                new BorderLayout(
                        15,
                        20
                )
        );

        JPanel questionHeader =
                new JPanel(
                        new BorderLayout()
                );

        questionHeader.setOpaque(false);

        categoryLabel =
                new JLabel();

        categoryLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        13
                )
        );

        categoryLabel.setForeground(
                UITheme.BLUE
        );

        JLabel instruction =
                new JLabel(
                        "Choose one answer"
                );

        instruction.setFont(
                UITheme.SMALL
        );

        instruction.setForeground(
                UITheme.TEXT_SECONDARY
        );

        questionHeader.add(
                categoryLabel,
                BorderLayout.WEST
        );

        questionHeader.add(
                instruction,
                BorderLayout.EAST
        );

        questionCard.add(
                questionHeader,
                BorderLayout.NORTH
        );

        questionLabel =
                new JLabel();

        questionLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        21
                )
        );

        questionLabel.setForeground(
                UITheme.TEXT
        );

        questionCard.add(
                questionLabel,
                BorderLayout.CENTER
        );

        // =====================================================
        // OPTIONS
        // =====================================================

        JPanel optionsPanel =
                new JPanel();

        optionsPanel.setOpaque(false);

        optionsPanel.setLayout(
                new GridLayout(
                        4,
                        1,
                        10,
                        10
                )
        );

        optionA =
                createOptionButton();

        optionB =
                createOptionButton();

        optionC =
                createOptionButton();

        optionD =
                createOptionButton();

        optionGroup =
                new ButtonGroup();

        optionGroup.add(optionA);
        optionGroup.add(optionB);
        optionGroup.add(optionC);
        optionGroup.add(optionD);

        optionsPanel.add(optionA);
        optionsPanel.add(optionB);
        optionsPanel.add(optionC);
        optionsPanel.add(optionD);

        questionCard.add(
                optionsPanel,
                BorderLayout.SOUTH
        );

        content.add(
                questionCard,
                BorderLayout.CENTER
        );

        background.add(
                content,
                BorderLayout.CENTER
        );

        // =====================================================
        // BOTTOM NAVIGATION
        // =====================================================

        JPanel bottom =
                new JPanel(
                        new BorderLayout()
                );

        bottom.setBackground(Color.WHITE);

        bottom.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(
                                1, 0, 0, 0,
                                UITheme.BORDER
                        ),
                        new EmptyBorder(
                                15, 35, 15, 35
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

        JButton submitButton =
                UITheme.primaryButton(
                        "Submit Quiz"
                );

        previousButton.addActionListener(e -> {

            saveCurrentAnswer();

            if (currentQuestionIndex > 0) {

                currentQuestionIndex--;

                loadQuestion();

            }
        });

        nextButton.addActionListener(e -> {

            saveCurrentAnswer();

            if (currentQuestionIndex
                    < questions.size() - 1) {

                currentQuestionIndex++;

                loadQuestion();

            }
        });

        submitButton.addActionListener(
                e -> {

                    saveCurrentAnswer();

                    submitQuiz();
                }
        );

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

        centerButtons.add(nextButton);
        centerButtons.add(submitButton);

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
    // OPTION BUTTON
    // =====================================================

    private JRadioButton createOptionButton() {

        JRadioButton button =
                new JRadioButton();

        button.setFont(
                UITheme.BODY
        );

        button.setForeground(
                UITheme.TEXT
        );

        button.setBackground(
                Color.WHITE
        );

        button.setFocusPainted(false);

        button.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                UITheme.BORDER,
                                1
                        ),
                        BorderFactory.createEmptyBorder(
                                12, 15, 12, 15
                        )
                )
        );

        return button;
    }

    // =====================================================
    // LOAD QUESTION
    // =====================================================

    private void loadQuestion() {

        Question question =
                questions.get(
                        currentQuestionIndex
                );

        progressLabel.setText(
                "Question "
                        + (currentQuestionIndex + 1)
                        + " of "
                        + questions.size()
        );

        categoryLabel.setText(
                "PLACEMENT PRACTICE"
        );

        questionLabel.setText(
                "<html><body style='width:700px'>"
                        + question.getQuestion()
                        + "</body></html>"
        );

        optionA.setText(
                "A. " + question.getOptionA()
        );

        optionB.setText(
                "B. " + question.getOptionB()
        );

        optionC.setText(
                "C. " + question.getOptionC()
        );

        optionD.setText(
                "D. " + question.getOptionD()
        );

        optionGroup.clearSelection();

        String savedAnswer =
                selectedAnswers[
                        currentQuestionIndex
                ];

        if (savedAnswer != null) {

            switch (savedAnswer) {

                case "A":
                    optionA.setSelected(true);
                    break;

                case "B":
                    optionB.setSelected(true);
                    break;

                case "C":
                    optionC.setSelected(true);
                    break;

                case "D":
                    optionD.setSelected(true);
                    break;
            }
        }
    }

    // =====================================================
    // SAVE ANSWER
    // =====================================================

    private void saveCurrentAnswer() {

        if (optionA.isSelected()) {

            selectedAnswers[
                    currentQuestionIndex
            ] = "A";

        } else if (optionB.isSelected()) {

            selectedAnswers[
                    currentQuestionIndex
            ] = "B";

        } else if (optionC.isSelected()) {

            selectedAnswers[
                    currentQuestionIndex
            ] = "C";

        } else if (optionD.isSelected()) {

            selectedAnswers[
                    currentQuestionIndex
            ] = "D";
        }
    }

    // =====================================================
    // SUBMIT QUIZ
    // =====================================================

    private void submitQuiz() {

        int choice =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to submit your quiz?",
                        "Submit Quiz",
                        JOptionPane.YES_NO_OPTION
                );

        if (choice !=
                JOptionPane.YES_OPTION) {

            return;
        }

        int correct = 0;
        int wrong = 0;
        int unanswered = 0;

        for (int i = 0;
             i < questions.size();
             i++) {

            String selected =
                    selectedAnswers[i];

            if (selected == null) {

                unanswered++;

            } else if (
                    selected.equals(
                            questions
                                    .get(i)
                                    .getCorrectAnswer()
                    )
            ) {

                correct++;

            } else {

                wrong++;
            }
        }

        int total =
                questions.size();

        int score =
                correct;

        double percentage =
                ((double) correct / total)
                        * 100;

        ResultFrame resultFrame =
                new ResultFrame(
                        loggedInUser,
                        questions,
                        selectedAnswers,
                        categoryId,
                        total,
                        correct,
                        wrong,
                        unanswered,
                        score,
                        percentage
                );

        resultFrame.setVisible(true);

        dispose();
    }
}