package ui;

import dao.ResultDAO;
import model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DashboardFrame extends JFrame {

    private final User loggedInUser;

    public DashboardFrame(User user) {
        this.loggedInUser = user;

        UITheme.setupFrame(
                this,
                "AptiQuiz - Dashboard",
                1000,
                700
        );

        createUI();
    }

    private void createUI() {

        JPanel background = UITheme.createBackgroundPanel();
        background.setLayout(new BorderLayout());

        // ================= TOP BAR =================

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(Color.WHITE);

        topBar.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(
                                0, 0, 1, 0,
                                UITheme.BORDER
                        ),
                        new EmptyBorder(18, 30, 18, 30)
                )
        );

        JPanel logoPanel = new JPanel();
        logoPanel.setOpaque(false);
        logoPanel.setLayout(
                new BoxLayout(
                        logoPanel,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel logo = new JLabel("AptiQuiz");
        logo.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        24
                )
        );
        logo.setForeground(UITheme.NAVY);

        JLabel tagline = new JLabel(
                "Placement Preparation"
        );
        tagline.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        12
                )
        );
        tagline.setForeground(
                UITheme.TEXT_SECONDARY
        );

        logoPanel.add(logo);
        logoPanel.add(tagline);

        JPanel userPanel = new JPanel(
                new FlowLayout(
                        FlowLayout.RIGHT,
                        12,
                        0
                )
        );
        userPanel.setOpaque(false);

        JLabel userLabel = new JLabel(
                loggedInUser.getName()
        );
        userLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        14
                )
        );
        userLabel.setForeground(UITheme.NAVY);

        JButton logoutButton =
                UITheme.secondaryButton("Logout");

        logoutButton.addActionListener(
                e -> logout()
        );

        userPanel.add(userLabel);
        userPanel.add(logoutButton);

        topBar.add(
                logoPanel,
                BorderLayout.WEST
        );

        topBar.add(
                userPanel,
                BorderLayout.EAST
        );

        background.add(
                topBar,
                BorderLayout.NORTH
        );

        // ================= MAIN CONTENT =================

        JPanel content = new JPanel();

        content.setOpaque(false);

        content.setBorder(
                new EmptyBorder(
                        30,
                        40,
                        30,
                        40
                )
        );

        content.setLayout(
                new BoxLayout(
                        content,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel welcomeLabel = new JLabel(
                "Good to see you, "
                        + loggedInUser.getName()
                        + "!"
        );

        welcomeLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        30
                )
        );

        welcomeLabel.setForeground(
                UITheme.NAVY
        );

        welcomeLabel.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JLabel subtitle = new JLabel(
                "Ready to sharpen your skills and get placement ready?"
        );

        subtitle.setFont(UITheme.BODY);
        subtitle.setForeground(
                UITheme.TEXT_SECONDARY
        );

        subtitle.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        content.add(welcomeLabel);
        content.add(
                Box.createVerticalStrut(6)
        );
        content.add(subtitle);
        content.add(
                Box.createVerticalStrut(25)
        );

        // ================= DATABASE STATISTICS =================

        ResultDAO resultDAO = new ResultDAO();

        int categoryCount =
                resultDAO.getCategoryCount();

        int questionCount =
                resultDAO.getTotalQuestionCount();

        int difficultyCount =
                resultDAO.getDifficultyCount();

        JPanel statsPanel = new JPanel(
                new GridLayout(
                        1,
                        3,
                        18,
                        0
                )
        );

        statsPanel.setOpaque(false);

        statsPanel.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        120
                )
        );

        statsPanel.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        statsPanel.add(
                createStatCard(
                        "Practice Topics",
                        String.valueOf(categoryCount),
                        "Topics available"
                )
        );

        statsPanel.add(
                createStatCard(
                        "Question Bank",
                        String.valueOf(questionCount),
                        "Practice questions"
                )
        );

        statsPanel.add(
                createStatCard(
                        "Difficulty",
                        String.valueOf(difficultyCount),
                        "Levels available"
                )
        );

        content.add(statsPanel);

        content.add(
                Box.createVerticalStrut(28)
        );

        // ================= START QUIZ CARD =================

        JPanel startCard = UITheme.createCard();

        startCard.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        startCard.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        155
                )
        );

        startCard.setLayout(
                new BorderLayout(
                        20,
                        10
                )
        );

        JPanel startText = new JPanel();

        startText.setOpaque(false);

        startText.setLayout(
                new BoxLayout(
                        startText,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel startTitle = new JLabel(
                "Ready for your next challenge?"
        );

        startTitle.setFont(
                UITheme.SUBHEADING
        );

        startTitle.setForeground(
                UITheme.NAVY
        );

        JLabel startDescription = new JLabel(
                "<html>Choose a category, select your "
                        + "difficulty, and start practicing.</html>"
        );

        startDescription.setFont(
                UITheme.BODY
        );

        startDescription.setForeground(
                UITheme.TEXT_SECONDARY
        );

        startText.add(startTitle);

        startText.add(
                Box.createVerticalStrut(8)
        );

        startText.add(startDescription);

        JButton startQuizButton =
                UITheme.primaryButton(
                        "Start New Quiz  →"
                );

        startQuizButton.setPreferredSize(
                new Dimension(
                        180,
                        48
                )
        );

        startQuizButton.addActionListener(
                e -> openQuizSetup()
        );

        startCard.add(
                startText,
                BorderLayout.CENTER
        );

        startCard.add(
                startQuizButton,
                BorderLayout.EAST
        );

        content.add(startCard);

        content.add(
                Box.createVerticalStrut(25)
        );

        // ================= QUICK ACTIONS =================

        JLabel quickTitle = new JLabel(
                "Quick Actions"
        );

        quickTitle.setFont(
                UITheme.SUBHEADING
        );

        quickTitle.setForeground(
                UITheme.NAVY
        );

        quickTitle.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        content.add(quickTitle);

        content.add(
                Box.createVerticalStrut(12)
        );

        JPanel actionsPanel = new JPanel(
                new GridLayout(
                        1,
                        2,
                        18,
                        0
                )
        );

        actionsPanel.setOpaque(false);

        actionsPanel.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        115
                )
        );

        actionsPanel.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        // Performance

        JPanel performanceCard =
                createActionCard(
                        "My Performance",
                        "View your quiz history, scores and progress.",
                        "View Performance"
                );

        JButton performanceButton =
                findButton(performanceCard);

        if (performanceButton != null) {

            performanceButton.addActionListener(
                    e -> openPerformance()
            );
        }

        // Profile

        JPanel profileCard =
                createActionCard(
                        "Your Profile",
                        "View your student information stored in AptiQuiz.",
                        "View Profile"
                );

        JButton profileButton =
                findButton(profileCard);

        if (profileButton != null) {

            profileButton.addActionListener(
                    e -> showProfile()
            );
        }

        actionsPanel.add(performanceCard);
        actionsPanel.add(profileCard);

        content.add(actionsPanel);

        // ================= SCROLL =================

        JScrollPane scrollPane =
                new JScrollPane(content);

        scrollPane.setBorder(null);

        scrollPane.setBackground(
                UITheme.BACKGROUND
        );

        scrollPane.getViewport()
                .setBackground(
                        UITheme.BACKGROUND
                );

        scrollPane.getVerticalScrollBar()
                .setUnitIncrement(16);

        background.add(
                scrollPane,
                BorderLayout.CENTER
        );

        add(background);
    }

    // ================= STAT CARD =================

    private JPanel createStatCard(
            String title,
            String value,
            String description
    ) {

        JPanel card =
                UITheme.createCard();

        card.setLayout(
                new BorderLayout()
        );

        JLabel valueLabel =
                new JLabel(value);

        valueLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        28
                )
        );

        valueLabel.setForeground(
                UITheme.NAVY
        );

        JLabel titleLabel =
                new JLabel(title);

        titleLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        14
                )
        );

        titleLabel.setForeground(
                UITheme.TEXT
        );

        JLabel descriptionLabel =
                new JLabel(description);

        descriptionLabel.setFont(
                UITheme.SMALL
        );

        descriptionLabel.setForeground(
                UITheme.TEXT_SECONDARY
        );

        JPanel textPanel = new JPanel();

        textPanel.setOpaque(false);

        textPanel.setLayout(
                new BoxLayout(
                        textPanel,
                        BoxLayout.Y_AXIS
                )
        );

        textPanel.add(valueLabel);

        textPanel.add(
                Box.createVerticalStrut(3)
        );

        textPanel.add(titleLabel);

        textPanel.add(
                Box.createVerticalStrut(3)
        );

        textPanel.add(descriptionLabel);

        card.add(
                textPanel,
                BorderLayout.CENTER
        );

        return card;
    }

    // ================= ACTION CARD =================

    private JPanel createActionCard(
            String title,
            String description,
            String buttonText
    ) {

        JPanel card =
                UITheme.createCard();

        card.setLayout(
                new BorderLayout(
                        10,
                        5
                )
        );

        JLabel titleLabel =
                new JLabel(title);

        titleLabel.setFont(
                UITheme.SUBHEADING
        );

        titleLabel.setForeground(
                UITheme.NAVY
        );

        JLabel descriptionLabel =
                new JLabel(
                        "<html>"
                                + description
                                + "</html>"
                );

        descriptionLabel.setFont(
                UITheme.SMALL
        );

        descriptionLabel.setForeground(
                UITheme.TEXT_SECONDARY
        );

        JButton button =
                UITheme.secondaryButton(
                        buttonText
                );

        button.setPreferredSize(
                new Dimension(
                        155,
                        40
                )
        );

        JPanel textPanel =
                new JPanel();

        textPanel.setOpaque(false);

        textPanel.setLayout(
                new BoxLayout(
                        textPanel,
                        BoxLayout.Y_AXIS
                )
        );

        textPanel.add(titleLabel);

        textPanel.add(
                Box.createVerticalStrut(5)
        );

        textPanel.add(descriptionLabel);

        card.add(
                textPanel,
                BorderLayout.CENTER
        );

        card.add(
                button,
                BorderLayout.EAST
        );

        return card;
    }

    // ================= FIND BUTTON =================

    private JButton findButton(
            Container container
    ) {

        for (Component component :
                container.getComponents()) {

            if (component instanceof JButton) {
                return (JButton) component;
            }

            if (component instanceof Container) {

                JButton button =
                        findButton(
                                (Container) component
                        );

                if (button != null) {
                    return button;
                }
            }
        }

        return null;
    }

    // ================= QUIZ SETUP =================

    private void openQuizSetup() {

        QuizSetupFrame quizSetupFrame =
                new QuizSetupFrame(
                        loggedInUser
                );

        quizSetupFrame.setVisible(true);

        dispose();
    }

    // ================= PERFORMANCE =================

    private void openPerformance() {

        PerformanceFrame performanceFrame =
                new PerformanceFrame(
                        loggedInUser
                );

        performanceFrame.setVisible(true);

        dispose();
    }

    // ================= PROFILE =================

    private void showProfile() {

        String message =
                "<html>"
                        + "<div style='width:320px'>"
                        + "<b>Name:</b> "
                        + loggedInUser.getName()
                        + "<br><br>"
                        + "<b>Email:</b> "
                        + loggedInUser.getEmail()
                        + "<br><br>"
                        + "<b>College:</b> "
                        + loggedInUser.getCollege()
                        + "<br><br>"
                        + "<b>Department:</b> "
                        + loggedInUser.getDepartment()
                        + "<br><br>"
                        + "<b>Year:</b> "
                        + loggedInUser.getYear()
                        + "<br><br>"
                        + "<b>Phone:</b> "
                        + loggedInUser.getPhone()
                        + "</div>"
                        + "</html>";

        JOptionPane.showMessageDialog(
                this,
                message,
                "My Profile",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // ================= LOGOUT =================

    private void logout() {

        int choice =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to logout?",
                        "Logout",
                        JOptionPane.YES_NO_OPTION
                );

        if (choice ==
                JOptionPane.YES_OPTION) {

            LoginFrame loginFrame =
                    new LoginFrame();

            loginFrame.setVisible(true);

            dispose();
        }
    }
}