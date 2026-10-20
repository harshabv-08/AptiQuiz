package ui;

import dao.UserDAO;
import model.User;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private JTextField emailField;
    private JPasswordField passwordField;

    public LoginFrame() {

        UITheme.setupFrame(
                this,
                "AptiQuiz - Login",
                900,
                600
        );

        JPanel background =
                UITheme.createBackgroundPanel();

        background.setLayout(
                new GridBagLayout()
        );

        JPanel card =
                UITheme.createCard();

        card.setPreferredSize(
                new Dimension(
                        460,
                        430
                )
        );

        card.setLayout(
                new BoxLayout(
                        card,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel logo =
                new JLabel(
                        "AptiQuiz"
                );

        logo.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        logo.setFont(
                UITheme.TITLE
        );

        logo.setForeground(
                UITheme.NAVY
        );

        JLabel title =
                new JLabel(
                        "Welcome back"
                );

        title.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        title.setFont(
                UITheme.HEADING
        );

        title.setForeground(
                UITheme.TEXT
        );

        JLabel subtitle =
                new JLabel(
                        "Continue your placement preparation"
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

        emailField =
                UITheme.textField();

        passwordField =
                UITheme.passwordField();

        emailField.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        45
                )
        );

        passwordField.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        45
                )
        );

        JLabel emailLabel =
                UITheme.label("Email address");

        JLabel passwordLabel =
                UITheme.label("Password");

        JButton loginButton =
                UITheme.primaryButton(
                        "Login"
                );

        loginButton.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        loginButton.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        48
                )
        );

        JButton registerButton =
                UITheme.secondaryButton(
                        "New student? Create an account"
                );

        registerButton.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        registerButton.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        45
                )
        );

        card.add(logo);

        card.add(
                Box.createVerticalStrut(10)
        );

        card.add(title);

        card.add(
                Box.createVerticalStrut(5)
        );

        card.add(subtitle);

        card.add(
                Box.createVerticalStrut(30)
        );

        card.add(emailLabel);

        card.add(
                Box.createVerticalStrut(7)
        );

        card.add(emailField);

        card.add(
                Box.createVerticalStrut(18)
        );

        card.add(passwordLabel);

        card.add(
                Box.createVerticalStrut(7)
        );

        card.add(passwordField);

        card.add(
                Box.createVerticalStrut(25)
        );

        card.add(loginButton);

        card.add(
                Box.createVerticalStrut(12)
        );

        card.add(registerButton);

        loginButton.addActionListener(
                e -> loginUser()
        );

        registerButton.addActionListener(e -> {

            RegisterFrame registerFrame =
                    new RegisterFrame();

            registerFrame.setVisible(true);

            dispose();
        });

        background.add(card);

        add(background);
    }

    private void loginUser() {

        String email =
                emailField.getText().trim();

        String password =
                new String(
                        passwordField.getPassword()
                );

        if (email.isEmpty()
                || password.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter your email and password.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        UserDAO userDAO =
                new UserDAO();

        User user =
                userDAO.loginUser(
                        email,
                        password
                );

        if (user != null) {

            DashboardFrame dashboardFrame =
                    new DashboardFrame(user);

            dashboardFrame.setVisible(true);

            dispose();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid email or password.",
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}