package ui;

import dao.ResultDAO;
import model.QuizResult;
import model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PerformanceFrame extends JFrame {

    private final User loggedInUser;

    private List<QuizResult> results;

    public PerformanceFrame(User user) {

        this.loggedInUser = user;

        UITheme.setupFrame(
                this,
                "AptiQuiz - My Performance",
                1000,
                700
        );

        loadResults();
        createUI();
    }

    // =====================================================
    // LOAD RESULTS
    // =====================================================

    private void loadResults() {

        ResultDAO resultDAO =
                new ResultDAO();

        results =
                resultDAO.getUserResults(
                        loggedInUser.getUserId()
                );
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

        JLabel pageLabel =
                new JLabel(
                        "MY PERFORMANCE"
                );

        pageLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        13
                )
        );

        pageLabel.setForeground(
                UITheme.BLUE
        );

        topBar.add(
                logo,
                BorderLayout.WEST
        );

        topBar.add(
                pageLabel,
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

        JLabel title =
                new JLabel(
                        "My Performance"
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
                        "Track your progress and keep improving your placement readiness."
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
                Box.createVerticalStrut(25)
        );

        // =================================================
        // STATISTICS
        // =================================================

        JPanel statistics =
                createStatisticsPanel();

        statistics.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        content.add(statistics);

        content.add(
                Box.createVerticalStrut(28)
        );

        // =================================================
        // HISTORY TITLE
        // =================================================

        JLabel historyTitle =
                new JLabel(
                        "Quiz History"
                );

        historyTitle.setFont(
                UITheme.SUBHEADING
        );

        historyTitle.setForeground(
                UITheme.NAVY
        );

        historyTitle.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        content.add(historyTitle);

        content.add(
                Box.createVerticalStrut(12)
        );

        // =================================================
        // HISTORY TABLE
        // =================================================

        JPanel historyCard =
                UITheme.createCard();

        historyCard.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        historyCard.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        280
                )
        );

        historyCard.setLayout(
                new BorderLayout()
        );

        JTable table =
                createHistoryTable();

        JScrollPane scrollPane =
                new JScrollPane(table);

        scrollPane.setBorder(null);

        scrollPane.setBackground(
                Color.WHITE
        );

        scrollPane.getViewport()
                .setBackground(
                        Color.WHITE
                );

        historyCard.add(
                scrollPane,
                BorderLayout.CENTER
        );

        content.add(historyCard);

        // =================================================
        // BACK BUTTON
        // =================================================

        content.add(
                Box.createVerticalStrut(20)
        );

        JButton backButton =
                UITheme.secondaryButton(
                        "← Back to Dashboard"
                );

        backButton.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        backButton.addActionListener(e -> {

            DashboardFrame dashboardFrame =
                    new DashboardFrame(
                            loggedInUser
                    );

            dashboardFrame.setVisible(true);

            dispose();
        });

        content.add(backButton);

        // =================================================
        // SCROLL PANE
        // =================================================

        JScrollPane mainScroll =
                new JScrollPane(content);

        mainScroll.setBorder(null);

        mainScroll.setBackground(
                UITheme.BACKGROUND
        );

        mainScroll.getViewport()
                .setBackground(
                        UITheme.BACKGROUND
                );

        mainScroll.getVerticalScrollBar()
                .setUnitIncrement(16);

        background.add(
                mainScroll,
                BorderLayout.CENTER
        );

        add(background);
    }

    // =====================================================
    // STATISTICS PANEL
    // =====================================================

    private JPanel createStatisticsPanel() {

        int attempts =
                results.size();

        int totalCorrect = 0;

        double bestPercentage = 0;

        double totalPercentage = 0;

        for (QuizResult result : results) {

            totalCorrect +=
                    result.getCorrectAnswers();

            totalPercentage +=
                    result.getPercentage();

            if (result.getPercentage()
                    > bestPercentage) {

                bestPercentage =
                        result.getPercentage();
            }
        }

        double averagePercentage = 0;

        if (attempts > 0) {

            averagePercentage =
                    totalPercentage / attempts;
        }

        JPanel panel =
                new JPanel(
                        new GridLayout(
                                1,
                                4,
                                15,
                                0
                        )
                );

        panel.setOpaque(false);

        panel.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        115
                )
        );

        panel.add(
                createStatCard(
                        "Total Attempts",
                        String.valueOf(
                                attempts
                        )
                )
        );

        panel.add(
                createStatCard(
                        "Average Score",
                        String.format(
                                "%.1f%%",
                                averagePercentage
                        )
                )
        );

        panel.add(
                createStatCard(
                        "Best Score",
                        String.format(
                                "%.1f%%",
                                bestPercentage
                        )
                )
        );

        panel.add(
                createStatCard(
                        "Correct Answers",
                        String.valueOf(
                                totalCorrect
                        )
                )
        );

        return panel;
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
                        27
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
                Box.createVerticalStrut(5)
        );

        panel.add(titleLabel);

        card.add(
                panel,
                BorderLayout.CENTER
        );

        return card;
    }

    // =====================================================
    // HISTORY TABLE
    // =====================================================

    private JTable createHistoryTable() {

        String[] columns = {
                "Attempt",
                "Questions",
                "Correct",
                "Wrong",
                "Unanswered",
                "Score",
                "Percentage"
        };

        DefaultTableModel model =
                new DefaultTableModel(
                        columns,
                        0
                ) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column) {

                        return false;
                    }
                };

        int attemptNumber = 1;

        for (QuizResult result : results) {

            Object[] row = {

                    attemptNumber,

                    result.getTotalQuestions(),

                    result.getCorrectAnswers(),

                    result.getWrongAnswers(),

                    result.getUnanswered(),

                    result.getScore(),

                    String.format(
                            "%.1f%%",
                            result.getPercentage()
                    )
            };

            model.addRow(row);

            attemptNumber++;
        }

        JTable table =
                new JTable(model);

        table.setFont(
                UITheme.BODY
        );

        table.setForeground(
                UITheme.TEXT
        );

        table.setBackground(
                Color.WHITE
        );

        table.setRowHeight(38);

        table.setGridColor(
                UITheme.BORDER
        );

        table.setShowVerticalLines(false);

        table.setSelectionBackground(
                UITheme.BLUE_LIGHT
        );

        table.setSelectionForeground(
                UITheme.NAVY
        );

        table.setAutoResizeMode(
                JTable.AUTO_RESIZE_ALL_COLUMNS
        );

        // =================================================
        // TABLE HEADER
        // =================================================

        table.getTableHeader()
                .setFont(
                        new Font(
                                "SansSerif",
                                Font.BOLD,
                                13
                        )
                );

        table.getTableHeader()
                .setForeground(
                        UITheme.NAVY
                );

        table.getTableHeader()
                .setBackground(
                        UITheme.BLUE_LIGHT
                );

        table.getTableHeader()
                .setPreferredSize(
                        new Dimension(
                                0,
                                40
                        )
                );

        // =================================================
        // CENTER ALIGNMENT
        // =================================================

        DefaultTableCellRenderer centerRenderer =
                new DefaultTableCellRenderer();

        centerRenderer.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        for (int i = 0;
             i < table.getColumnCount();
             i++) {

            table.getColumnModel()
                    .getColumn(i)
                    .setCellRenderer(
                            centerRenderer
                    );
        }

        return table;
    }
}