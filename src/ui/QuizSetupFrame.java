package ui;

import dao.QuestionDAO;
import model.Question;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class QuizSetupFrame extends JFrame {

    private User loggedInUser;

    private JComboBox<String> categoryBox;
    private JComboBox<String> difficultyBox;
    private JComboBox<Integer> questionCountBox;

    private JLabel availabilityLabel;

    public QuizSetupFrame(User user) {

        this.loggedInUser = user;

        UITheme.setupFrame(
                this,
                "AptiQuiz - Quiz Setup",
                900,
                600
        );

        createUI();

        updateQuestionCounts();
    }

    private void createUI() {

        JPanel background =
                UITheme.createBackgroundPanel();

        background.setLayout(
                new GridBagLayout()
        );

        JPanel card =
                UITheme.createCard();

        card.setPreferredSize(
                new Dimension(
                        620,
                        500
                )
        );

        card.setLayout(
                new BorderLayout(
                        20,
                        20
                )
        );

        // ---------- HEADER ----------

        JPanel header =
                new JPanel();

        header.setOpaque(false);

        header.setLayout(
                new BoxLayout(
                        header,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel title =
                new JLabel(
                        "Create Your Quiz"
                );

        title.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        title.setFont(
                UITheme.HEADING
        );

        title.setForeground(
                UITheme.NAVY
        );

        JLabel subtitle =
                new JLabel(
                        "Choose your topic, difficulty and question count."
                );

        subtitle.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        subtitle.setFont(
                UITheme.SMALL
        );

        subtitle.setForeground(
                UITheme.TEXT_SECONDARY
        );

        header.add(title);

        header.add(
                Box.createVerticalStrut(6)
        );

        header.add(subtitle);

        card.add(
                header,
                BorderLayout.NORTH
        );

        // ---------- FORM ----------

        JPanel form =
                new JPanel(
                        new GridLayout(
                                3,
                                2,
                                18,
                                20
                        )
                );

        form.setOpaque(false);

        categoryBox =
                new JComboBox<>(
                        new String[]{
                                "Quantitative Aptitude",
                                "Logical Reasoning",
                                "Verbal Ability",
                                "Data Interpretation",
                                "Programming",
                                "Java",
                                "SQL & DBMS",
                                "Object-Oriented Programming",
                                "Computer Fundamentals"
                        }
                );

        difficultyBox =
                new JComboBox<>(
                        new String[]{
                                "Beginner",
                                "Intermediate",
                                "Advanced"
                        }
                );

        questionCountBox =
                new JComboBox<>();

        categoryBox.setFont(
                UITheme.BODY
        );

        difficultyBox.setFont(
                UITheme.BODY
        );

        questionCountBox.setFont(
                UITheme.BODY
        );

        form.add(
                UITheme.label("Category")
        );

        form.add(categoryBox);

        form.add(
                UITheme.label("Difficulty")
        );

        form.add(difficultyBox);

        form.add(
                UITheme.label("Number of Questions")
        );

        form.add(questionCountBox);

        card.add(
                form,
                BorderLayout.CENTER
        );

        // ---------- BOTTOM ----------

        JPanel bottom =
                new JPanel();

        bottom.setOpaque(false);

        bottom.setLayout(
                new BoxLayout(
                        bottom,
                        BoxLayout.Y_AXIS
                )
        );

        availabilityLabel =
                UITheme.secondaryLabel(
                        ""
                );

        availabilityLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        JButton startButton =
                UITheme.primaryButton(
                        "Start Quiz  →"
                );

        startButton.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        JButton backButton =
                UITheme.secondaryButton(
                        "Back to Dashboard"
                );

        backButton.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        bottom.add(
                availabilityLabel
        );

        bottom.add(
                Box.createVerticalStrut(18)
        );

        bottom.add(
                startButton
        );

        bottom.add(
                Box.createVerticalStrut(10)
        );

        bottom.add(
                backButton
        );

        card.add(
                bottom,
                BorderLayout.SOUTH
        );

        // ---------- EVENTS ----------

        categoryBox.addActionListener(
                e -> updateQuestionCounts()
        );

        difficultyBox.addActionListener(
                e -> updateQuestionCounts()
        );

        startButton.addActionListener(
                e -> startQuiz()
        );

        backButton.addActionListener(e -> {

            DashboardFrame dashboardFrame =
                    new DashboardFrame(
                            loggedInUser
                    );

            dashboardFrame.setVisible(true);

            dispose();
        });

        background.add(card);

        add(background);
    }

    private void updateQuestionCounts() {

        int categoryId =
                categoryBox.getSelectedIndex()
                        + 1;

        String difficulty =
                (String)
                        difficultyBox
                                .getSelectedItem();

        QuestionDAO questionDAO =
                new QuestionDAO();

        int available =
                questionDAO.getQuestionCount(
                        categoryId,
                        difficulty
                );

        questionCountBox.removeAllItems();

        if (available == 0) {

            questionCountBox.addItem(0);

            availabilityLabel.setText(
                    "No questions are currently available for this selection."
            );

            return;
        }

        List<Integer> counts =
                new ArrayList<>();

        for (int count :
                new int[]{5, 10, 15, 20}) {

            if (count <= available) {

                counts.add(count);
            }
        }

        if (counts.isEmpty()
                || !counts.contains(available)) {

            counts.add(available);
        }

        for (Integer count : counts) {

            questionCountBox.addItem(
                    count
            );
        }

        availabilityLabel.setText(
                available
                        + " question(s) available for this selection."
        );
    }

    private void startQuiz() {

        int categoryId =
                categoryBox.getSelectedIndex()
                        + 1;

        String difficulty =
                (String)
                        difficultyBox
                                .getSelectedItem();

        Integer selectedCount =
                (Integer)
                        questionCountBox
                                .getSelectedItem();

        if (selectedCount == null
                || selectedCount <= 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "No questions are available for this selection.",
                    "No Questions",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        QuestionDAO questionDAO =
                new QuestionDAO();

        List<Question> questions =
                questionDAO.getRandomQuestions(
                        categoryId,
                        difficulty,
                        selectedCount
                );

        if (questions.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load questions.",
                    "Quiz Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        QuizFrame quizFrame =
                new QuizFrame(
                        loggedInUser,
                        questions,
                        categoryId
                );

        quizFrame.setVisible(true);

        dispose();
    }
}