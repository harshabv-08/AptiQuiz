package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class UITheme {

    // ================= COLORS =================

    public static final Color NAVY =
            new Color(18, 38, 63);

    public static final Color NAVY_DARK =
            new Color(11, 28, 48);

    public static final Color BLUE =
            new Color(47, 103, 190);

    public static final Color BLUE_LIGHT =
            new Color(235, 243, 255);

    public static final Color BACKGROUND =
            new Color(247, 249, 252);

    public static final Color CARD =
            Color.WHITE;

    public static final Color TEXT =
            new Color(31, 42, 55);

    public static final Color TEXT_SECONDARY =
            new Color(100, 116, 139);

    public static final Color BORDER =
            new Color(221, 228, 238);

    public static final Color SUCCESS =
            new Color(34, 145, 92);

    public static final Color ERROR =
            new Color(210, 70, 70);

    // ================= FONTS =================

    public static final Font TITLE =
            new Font("SansSerif", Font.BOLD, 32);

    public static final Font HEADING =
            new Font("SansSerif", Font.BOLD, 24);

    public static final Font SUBHEADING =
            new Font("SansSerif", Font.BOLD, 18);

    public static final Font BODY =
            new Font("SansSerif", Font.PLAIN, 15);

    public static final Font SMALL =
            new Font("SansSerif", Font.PLAIN, 13);

    public static final Font BUTTON =
            new Font("SansSerif", Font.BOLD, 14);

    // ================= FRAME =================

    public static void setupFrame(
            JFrame frame,
            String title,
            int width,
            int height) {

        frame.setTitle(title);
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
    }

    // ================= PANEL =================

    public static JPanel createBackgroundPanel() {

        JPanel panel = new JPanel();

        panel.setBackground(BACKGROUND);

        return panel;
    }

    public static JPanel createCard() {

        JPanel panel = new RoundedPanel(
                22,
                CARD
        );

        panel.setBorder(
                new EmptyBorder(
                        28, 30, 28, 30
                )
        );

        return panel;
    }

    // ================= BUTTON =================

    public static JButton primaryButton(
            String text) {

        RoundedButton button =
                new RoundedButton(
                        text,
                        NAVY,
                        new Color(29, 57, 91)
                );

        button.setFont(BUTTON);

        return button;
    }

    public static JButton secondaryButton(
            String text) {

        RoundedButton button =
                new RoundedButton(
                        text,
                        Color.WHITE,
                        BLUE_LIGHT
                );

        button.setForeground(NAVY);
        button.setBorderColor(BORDER);
        button.setFont(BUTTON);

        return button;
    }

    // ================= TEXT FIELD =================

    public static JTextField textField() {

        JTextField field =
                new JTextField();

        styleTextField(field);

        return field;
    }

    public static JPasswordField passwordField() {

        JPasswordField field =
                new JPasswordField();

        styleTextField(field);

        return field;
    }

    private static void styleTextField(
            JTextField field) {

        field.setFont(BODY);
        field.setForeground(TEXT);
        field.setBackground(Color.WHITE);

        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER,
                                1
                        ),
                        BorderFactory.createEmptyBorder(
                                9, 12, 9, 12
                        )
                )
        );
    }

    // ================= LABEL =================

    public static JLabel label(
            String text) {

        JLabel label =
                new JLabel(text);

        label.setFont(BODY);
        label.setForeground(TEXT);

        return label;
    }

    public static JLabel secondaryLabel(
            String text) {

        JLabel label =
                new JLabel(text);

        label.setFont(SMALL);
        label.setForeground(
                TEXT_SECONDARY
        );

        return label;
    }

    // ================= ROUNDED PANEL =================

    private static class RoundedPanel
            extends JPanel {

        private final int radius;
        private final Color background;

        RoundedPanel(
                int radius,
                Color background) {

            this.radius = radius;
            this.background = background;

            setOpaque(false);
        }

        @Override
        protected void paintComponent(
                Graphics g) {

            Graphics2D g2 =
                    (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            g2.setColor(background);

            g2.fillRoundRect(
                    0,
                    0,
                    getWidth(),
                    getHeight(),
                    radius,
                    radius
            );

            g2.dispose();

            super.paintComponent(g);
        }
    }

    // ================= ROUNDED BUTTON =================

    private static class RoundedButton
            extends JButton {

        private final Color normalColor;
        private final Color hoverColor;

        private Color borderColor =
                new Color(0, 0, 0, 0);

        RoundedButton(
                String text,
                Color normalColor,
                Color hoverColor) {

            super(text);

            this.normalColor = normalColor;
            this.hoverColor = hoverColor;

            setForeground(Color.WHITE);
            setBackground(normalColor);

            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);
            setOpaque(false);

            setCursor(
                    new Cursor(
                            Cursor.HAND_CURSOR
                    )
            );

            setBorder(
                    BorderFactory.createEmptyBorder(
                            12, 22, 12, 22
                    )
            );
        }

        void setBorderColor(
                Color color) {

            borderColor = color;
        }

        @Override
        protected void paintComponent(
                Graphics g) {

            Graphics2D g2 =
                    (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            Color fill =
                    getModel().isPressed()
                            ? hoverColor
                            : getModel().isRollover()
                            ? hoverColor
                            : normalColor;

            g2.setColor(fill);

            g2.fill(
                    new RoundRectangle2D.Float(
                            0,
                            0,
                            getWidth() - 1,
                            getHeight() - 1,
                            16,
                            16
                    )
            );

            if (borderColor.getAlpha() > 0) {

                g2.setColor(borderColor);

                g2.draw(
                        new RoundRectangle2D.Float(
                                0,
                                0,
                                getWidth() - 1,
                                getHeight() - 1,
                                16,
                                16
                        )
                );
            }

            g2.dispose();

            super.paintComponent(g);
        }
    }
}