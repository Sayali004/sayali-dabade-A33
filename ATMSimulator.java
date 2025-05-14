import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ATMSimulator extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;

    private JPanel welcomePanel, pinPanel, accountTypePanel, menuPanel, balancePanel, withdrawPanel;
    private JTextField pinField;
    private JLabel balanceLabel;

    private double savingsBalance = 2000.00;
    private double currentBalance = 5000.00;
    private String currentPin = "1234";

    private String selectedAccount = "SAVINGS"; 

    public ATMSimulator() {
        setTitle("ATM Simulator"); 
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        createWelcomePanel();
        createPinPanel();
        createAccountTypePanel();
        createMenuPanel();
        createBalancePanel();
        createWithdrawPanel();
        createChangePinPanel();

        
        JLabel bankLabel = new JLabel("Dr. D Y Patil Bank", SwingConstants.CENTER);
        bankLabel.setFont(new Font("Arial", Font.BOLD, 22));
        bankLabel.setForeground(Color.BLUE);
        bankLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        setLayout(new BorderLayout());
        add(bankLabel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private void createWelcomePanel() {
        welcomePanel = new JPanel();
        welcomePanel.setBackground(Color.LIGHT_GRAY);
        JButton insertCardButton = new JButton("Insert Card");
        insertCardButton.setBackground(Color.GREEN);
        insertCardButton.addActionListener(e -> cardLayout.show(mainPanel, "PIN"));
        welcomePanel.add(insertCardButton);
        mainPanel.add(welcomePanel, "WELCOME");
    }

    private void createPinPanel() {
        pinPanel = new JPanel(new BorderLayout());
        pinPanel.setBackground(Color.CYAN);

        JLabel label = new JLabel("Enter PIN:", SwingConstants.CENTER);
        pinField = new JTextField(10);
        JButton submitPin = new JButton("Submit");
        submitPin.setBackground(Color.ORANGE);
        submitPin.addActionListener(e -> {
            if (pinField.getText().equals(currentPin)) {
                cardLayout.show(mainPanel, "ACCOUNT_TYPE");
            } else {
                JOptionPane.showMessageDialog(this, "Incorrect PIN!");
            }
        });

        JPanel center = new JPanel();
        center.add(pinField);
        center.add(submitPin);

        pinPanel.add(label, BorderLayout.NORTH);
        pinPanel.add(center, BorderLayout.CENTER);
        mainPanel.add(pinPanel, "PIN");
    }

    private void createAccountTypePanel() {
        accountTypePanel = new JPanel(new GridLayout(3, 1, 10, 10));
        accountTypePanel.setBackground(Color.LIGHT_GRAY);

        JLabel label = new JLabel("Select Account Type:", SwingConstants.CENTER);
        JButton savingsBtn = new JButton("Savings Account");
        JButton currentBtn = new JButton("Current Account");

        savingsBtn.setBackground(Color.YELLOW);
        currentBtn.setBackground(Color.PINK);

        savingsBtn.addActionListener(e -> {
            selectedAccount = "SAVINGS";
            cardLayout.show(mainPanel, "MENU");
        });

        currentBtn.addActionListener(e -> {
            selectedAccount = "CURRENT";
            cardLayout.show(mainPanel, "MENU");
        });

        accountTypePanel.add(label);
        accountTypePanel.add(savingsBtn);
        accountTypePanel.add(currentBtn);

        mainPanel.add(accountTypePanel, "ACCOUNT_TYPE");
    }

    private void createMenuPanel() {
        menuPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        menuPanel.setBackground(Color.DARK_GRAY);

        JButton checkBalance = new JButton("Check Balance");
        checkBalance.setBackground(Color.YELLOW);
        checkBalance.addActionListener(e -> {
            if (selectedAccount.equals("SAVINGS")) {
                balanceLabel.setText("Savings Balance: $" + savingsBalance);
            } else {
                balanceLabel.setText("Current Balance: $" + currentBalance);
            }
            cardLayout.show(mainPanel, "BALANCE");
        });

        JButton withdraw = new JButton("Withdraw");
        withdraw.setBackground(Color.RED);
        withdraw.addActionListener(e -> cardLayout.show(mainPanel, "WITHDRAW"));

        JButton changePin = new JButton("Change PIN");
        changePin.setBackground(Color.CYAN);
        changePin.addActionListener(e -> cardLayout.show(mainPanel, "CHANGE_PIN"));

        JButton exit = new JButton("Exit");
        exit.setBackground(Color.GRAY);
        exit.addActionListener(e -> cardLayout.show(mainPanel, "WELCOME"));

        menuPanel.add(checkBalance);
        menuPanel.add(withdraw);
        menuPanel.add(changePin);
        menuPanel.add(exit);
        mainPanel.add(menuPanel, "MENU");
    }

    private void createBalancePanel() {
        balancePanel = new JPanel();
        balancePanel.setBackground(Color.WHITE);
        balanceLabel = new JLabel("Your Balance: $0.00", SwingConstants.CENTER);

        JButton backButton = new JButton("Back");
        backButton.setBackground(Color.MAGENTA);
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "MENU"));

        balancePanel.add(balanceLabel);
        balancePanel.add(backButton);
        mainPanel.add(balancePanel, "BALANCE");
    }

    private void createWithdrawPanel() {
        withdrawPanel = new JPanel(new BorderLayout());
        withdrawPanel.setBackground(Color.PINK);

        JTextField amountField = new JTextField(10);
        JButton withdrawBtn = new JButton("Withdraw");
        withdrawBtn.setBackground(Color.BLUE);
        withdrawBtn.addActionListener(e -> {
            try {
                double amount = Double.parseDouble(amountField.getText());
                if (selectedAccount.equals("SAVINGS")) {
                    if (amount <= savingsBalance) {
                        savingsBalance -= amount;
                        JOptionPane.showMessageDialog(this, "Withdrawn from Savings.\nNew Balance: $" + savingsBalance);
                        cardLayout.show(mainPanel, "MENU");
                    } else {
                        JOptionPane.showMessageDialog(this, "Insufficient savings balance!");
                    }
                } else {
                    if (amount <= currentBalance) {
                        currentBalance -= amount;
                        JOptionPane.showMessageDialog(this, "Withdrawn from Current.\nNew Balance: $" + currentBalance);
                        cardLayout.show(mainPanel, "MENU");
                    } else {
                        JOptionPane.showMessageDialog(this, "Insufficient current balance!");
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid amount.");
            }
        });

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Enter amount:"));
        inputPanel.add(amountField);
        inputPanel.add(withdrawBtn);

        withdrawPanel.add(inputPanel, BorderLayout.CENTER);
        mainPanel.add(withdrawPanel, "WITHDRAW");
    }

    private void createChangePinPanel() {
        JPanel changePinPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        changePinPanel.setBackground(Color.LIGHT_GRAY);

        JLabel oldPinLabel = new JLabel("Current PIN:");
        JTextField oldPinField = new JTextField();

        JLabel newPinLabel = new JLabel("New PIN:");
        JTextField newPinField = new JTextField();

        JLabel confirmPinLabel = new JLabel("Confirm New PIN:");
        JTextField confirmPinField = new JTextField();

        JButton confirmButton = new JButton("Change");
        confirmButton.setBackground(Color.GREEN);
        confirmButton.addActionListener(e -> {
            String oldPin = oldPinField.getText();
            String newPin = newPinField.getText();
            String confirmPin = confirmPinField.getText();

            if (!oldPin.equals(currentPin)) {
                JOptionPane.showMessageDialog(this, "Incorrect current PIN.");
            } else if (!newPin.equals(confirmPin)) {
                JOptionPane.showMessageDialog(this, "New PIN and confirmation do not match.");
            } else if (newPin.length() < 4) {
                JOptionPane.showMessageDialog(this, "New PIN must be at least 4 digits.");
            } else {
                currentPin = newPin;
                JOptionPane.showMessageDialog(this, "PIN successfully changed!");
                cardLayout.show(mainPanel, "MENU");
            }
        });

        JButton backButton = new JButton("Back");
        backButton.setBackground(Color.RED);
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "MENU"));

        changePinPanel.add(oldPinLabel);
        changePinPanel.add(oldPinField);
        changePinPanel.add(newPinLabel);
        changePinPanel.add(newPinField);
        changePinPanel.add(confirmPinLabel);
        changePinPanel.add(confirmPinField);
        changePinPanel.add(confirmButton);
        changePinPanel.add(backButton);

        mainPanel.add(changePinPanel, "CHANGE_PIN");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ATMSimulator::new);
    }
}
