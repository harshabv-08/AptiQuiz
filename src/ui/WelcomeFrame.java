package ui;

import javax.swing.*;
import java.awt.*;

public class WelcomeFrame extends JFrame {

    public WelcomeFrame() {

        UITheme.setupFrame(
                this,
                "AptiQuiz - Placement Preparation",
                900,
                600
        );

        JPanel background =
                UITheme.createBackgroundPanel();

        background.setLayout(
                new BorderLayout()
        );

        // ---------- TOP BAR ----------

        JPanel topBar =
                new JPanel(
                        new BorderLayout()
                );

        topBar.setOpaque(false);

        topBar.setBorder(
                BorderFactory.createEmptyBorder(
                        25, 40, 10, 40
                )
        );

        JLabel logo =
                new JLabel("AptiQuiz");

        logo.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        22
                )
        );

        logo.setForeground(
                UITheme.NAVY
        );

        JLabel badge =
                new JLabel(
                        "PLACEMENT PREPARATION"
                );

        badge.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        11
                )
        );

        badge.setForeground(
                UITheme.BLUE
        );

        topBar.add(
                logo,
                BorderLayout.WEST
        );

        topBar.add(
                badge,
                BorderLayout.EAST
        );

        background.add(
                topBar,
                BorderLayout.NORTH
        );

        // ---------- CENTER ----------

        JPanel center =
                new JPanel();

        center.setOpaque(false);

        center.setLayout(
                new BoxLayout(
                        center,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel smallTitle =
                new JLabel(
                        "YOUR PLACEMENT JOURNEY STARTS HERE"
                );

        smallTitle.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        smallTitle.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        12
                )
        );

        smallTitle.setForeground(
                UITheme.BLUE
        );

        JLabel title =
                new JLabel(
                        "Practice smarter."
                );

        title.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        title.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        44
                )
        );

        title.setForeground(
                UITheme.NAVY
        );

        JLabel title2 =
                new JLabel(
                        "Get placement ready."
                );

        title2.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        title2.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        44
                )
        );

        title2.setForeground(
                UITheme.BLUE
        );

        JLabel subtitle =
                new JLabel(
                        "<html><center>"
                                + "Build confidence with aptitude, reasoning, "
                                + "programming and interview-focused practice."
                                + "</center></html>"
                );

        subtitle.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        subtitle.setFont(
                UITheme.BODY
        );

        subtitle.setForeground(
                UITheme.TEXT_SECONDARY
        );

        JButton startButton =
                UITheme.primaryButton(
                        "Get Started  →"
                );

        startButton.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        startButton.setPreferredSize(
                new Dimension(
                        190,
                        52
                )
        );

        startButton.addActionListener(e -> {

            LoginFrame loginFrame =
                    new LoginFrame();

            loginFrame.setVisible(true);

            dispose();
        });

        center.add(
                Box.createVerticalGlue()
        );

        center.add(smallTitle);

        center.add(
                Box.createVerticalStrut(16)
        );

        center.add(title);

        center.add(title2);

        center.add(
                Box.createVerticalStrut(18)
        );

        center.add(subtitle);

        center.add(
                Box.createVerticalStrut(30)
        );

        center.add(startButton);

        center.add(
                Box.createVerticalGlue()
        );

        background.add(
                center,
                BorderLayout.CENTER
        );

        // ---------- FOOTER ----------

        JLabel footer =
                new JLabel(
                        "Practice  •  Improve  •  Get Placement Ready",
                        SwingConstants.CENTER
                );

        footer.setFont(
                UITheme.SMALL
        );

        footer.setForeground(
                UITheme.TEXT_SECONDARY
        );

        footer.setBorder(
                BorderFactory.createEmptyBorder(
                        10, 10, 22, 10
                )
        );

        background.add(
                footer,
                BorderLayout.SOUTH
        );

        add(background);
    }
}