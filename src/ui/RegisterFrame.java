package ui;

import dao.UserDAO;
import model.User;

import javax.swing.*;
import java.awt.*;

public class RegisterFrame extends JFrame {

    private JTextField nameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JTextField collegeField;
    private JTextField departmentField;
    private JComboBox<String> yearBox;
    private JTextField phoneField;

    public RegisterFrame() {

        UITheme.setupFrame(
                this,
                "AptiQuiz - Create Account",
                900,
                650
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
                        620,
                        570
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
                        "Create your AptiQuiz account"
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
                        "Join AptiQuiz and start preparing for placements."
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
                                7,
                                2,
                                15,
                                13
                        )
                );

        form.setOpaque(false);

        nameField =
                UITheme.textField();

        emailField =
                UITheme.textField();

        passwordField =
                UITheme.passwordField();

        collegeField =
                UITheme.textField();

        departmentField =
                UITheme.textField();

        yearBox =
                new JComboBox<>(
                        new String[]{
                                "1",
                                "2",
                                "3",
                                "4"
                        }
                );

        phoneField =
                UITheme.textField();

        yearBox.setFont(
                UITheme.BODY
        );

        form.add(
                UITheme.label("Full Name")
        );

        form.add(nameField);

        form.add(
                UITheme.label("Email")
        );

        form.add(emailField);

        form.add(
                UITheme.label("Password")
        );

        form.add(passwordField);

        form.add(
                UITheme.label("College")
        );

        form.add(collegeField);

        form.add(
                UITheme.label("Department")
        );

        form.add(departmentField);

        form.add(
                UITheme.label("Year")
        );

        form.add(yearBox);

        form.add(
                UITheme.label("Phone Number")
        );

        form.add(phoneField);

        card.add(
                form,
                BorderLayout.CENTER
        );

        // ---------- BUTTONS ----------

        JPanel buttons =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                12,
                                5
                        )
                );

        buttons.setOpaque(false);

        JButton registerButton =
                UITheme.primaryButton(
                        "Create Account"
                );

        JButton backButton =
                UITheme.secondaryButton(
                        "Back to Login"
                );

        buttons.add(registerButton);
        buttons.add(backButton);

        card.add(
                buttons,
                BorderLayout.SOUTH
        );

        registerButton.addActionListener(
                e -> registerUser()
        );

        backButton.addActionListener(e -> {

            LoginFrame loginFrame =
                    new LoginFrame();

            loginFrame.setVisible(true);

            dispose();
        });

        background.add(card);

        add(background);
    }

    private void registerUser() {

        String name =
                nameField.getText().trim();

        String email =
                emailField.getText().trim();

        String password =
                new String(
                        passwordField.getPassword()
                );

        String college =
                collegeField.getText().trim();

        String department =
                departmentField.getText().trim();

        String phone =
                phoneField.getText().trim();

        if (name.isEmpty()
                || email.isEmpty()
                || password.isEmpty()
                || college.isEmpty()
                || department.isEmpty()
                || phone.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please complete all fields.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (!email.matches(
                "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid email address.",
                    "Invalid Email",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (password.length() < 6) {

            JOptionPane.showMessageDialog(
                    this,
                    "Password must contain at least 6 characters.",
                    "Invalid Password",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (!phone.matches("\\d{10}")) {

            JOptionPane.showMessageDialog(
                    this,
                    "Phone number must contain exactly 10 digits.",
                    "Invalid Phone",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int year =
                Integer.parseInt(
                        (String) yearBox.getSelectedItem()
                );

        User user =
                new User(
                        0,
                        name,
                        email,
                        password,
                        college,
                        department,
                        year,
                        phone
                );

        UserDAO userDAO =
                new UserDAO();

        if (userDAO.registerUser(user)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Account created successfully!",
                    "Welcome to AptiQuiz",
                    JOptionPane.INFORMATION_MESSAGE
            );

            LoginFrame loginFrame =
                    new LoginFrame();

            loginFrame.setVisible(true);

            dispose();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Registration failed. The email may already be registered.",
                    "Registration Failed",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}