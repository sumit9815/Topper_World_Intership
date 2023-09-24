import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class Atm  {
    private Map<String, Account> accounts = new HashMap<>();
    private Account currentUser;

    private JFrame frame;
    private JTextField accountNumberField;
    private JPasswordField pinField;
    private JLabel messageLabel;

    public Atm () {
        initializeAccounts();

        frame = new JFrame("ATM Interface");
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel loginPanel = new JPanel(new GridLayout(3, 2));
        JLabel accountNumberLabel = new JLabel("Account Number:");
        JLabel pinLabel = new JLabel("PIN:");
        accountNumberField = new JTextField();
        pinField = new JPasswordField();
        JButton loginButton = new JButton("Login");
        messageLabel = new JLabel("");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String accountNumber = accountNumberField.getText();
                String pin = new String(pinField.getPassword());

                if (authenticate(accountNumber, pin)) {
                    showTransactionOptions();
                } else {
                    messageLabel.setText("Authentication failed. Please try again.");
                }
            }
        });

        loginPanel.add(accountNumberLabel);
        loginPanel.add(accountNumberField);
        loginPanel.add(pinLabel);
        loginPanel.add(pinField);
        loginPanel.add(new JLabel()); // Empty cell for spacing
        loginPanel.add(loginButton);

        frame.add(loginPanel, BorderLayout.CENTER);
        frame.add(messageLabel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Atm();
            }
        });
    }

    private void initializeAccounts() {
        // Create sample accounts
        Account account1 = new Account("12345", "1234", 1000.0);
        Account account2 = new Account("67890", "5678", 500.0);

        accounts.put(account1.getAccountNumber(), account1);
        accounts.put(account2.getAccountNumber(), account2);
    }

    private boolean authenticate(String accountNumber, String pin) {
        Account account = accounts.get(accountNumber);
        if (account != null && account.getPin().equals(pin)) {
            currentUser = account;
            return true;
        }
        return false;
    }

    private void showTransactionOptions() {
        frame.getContentPane().removeAll();
        frame.revalidate();
        frame.repaint();

        JPanel transactionPanel = new JPanel(new GridLayout(4, 1));
        JLabel titleLabel = new JLabel("Choose Transaction:");
        JButton checkBalanceButton = new JButton("Check Balance");
        JButton depositButton = new JButton("Deposit");
        JButton withdrawButton = new JButton("Withdraw");
        JButton logoutButton = new JButton("Logout");

        checkBalanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showMessage("Your balance: $" + currentUser.getBalance());
            }
        });

        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String depositAmount = JOptionPane.showInputDialog(frame, "Enter the deposit amount:");
                try {
                    double amount = Double.parseDouble(depositAmount);
                    if (amount > 0) {
                        currentUser.deposit(amount);
                        showMessage("Deposit successful. Your new balance: $" + currentUser.getBalance());
                    } else {
                        showMessage("Invalid amount. Please enter a positive number.");
                    }
                } catch (NumberFormatException ex) {
                    showMessage("Invalid amount. Please enter a valid number.");
                }
            }
        });

        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String withdrawAmount = JOptionPane.showInputDialog(frame, "Enter the withdrawal amount:");
                try {
                    double amount = Double.parseDouble(withdrawAmount);
                    if (amount > 0 && currentUser.getBalance() >= amount) {
                        currentUser.withdraw(amount);
                        showMessage("Withdrawal successful. Your new balance: $" + currentUser.getBalance());
                    } else if (amount <= 0) {
                        showMessage("Invalid amount. Please enter a positive number.");
                    } else {
                        showMessage("Insufficient balance.");
                    }
                } catch (NumberFormatException ex) {
                    showMessage("Invalid amount. Please enter a valid number.");
                }
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentUser = null;
                frame.getContentPane().removeAll();
                frame.revalidate();
                frame.repaint();
                initializeGUI();
            }
        });

        transactionPanel.add(titleLabel);
        transactionPanel.add(checkBalanceButton);
        transactionPanel.add(depositButton);
        transactionPanel.add(withdrawButton);
        transactionPanel.add(logoutButton);

        frame.add(transactionPanel, BorderLayout.CENTER);
        frame.add(messageLabel, BorderLayout.SOUTH);
        frame.revalidate();
    }

    protected void initializeGUI() {
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(frame, message);
    }

    private class Account {
        private String accountNumber;
        private String pin;
        private double balance;

        public Account(String accountNumber, String pin, double balance) {
            this.accountNumber = accountNumber;
            this.pin = pin;
            this.balance = balance;
        }

        public String getAccountNumber() {
            return accountNumber;
        }

        public String getPin() {
            return pin;
        }

        public double getBalance() {
            return balance;
        }

        public void deposit(double amount) {
            balance += amount;
        }

        public void withdraw(double amount) {
            balance -= amount;
        }
    }
}