import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class BankAccount {
    private double balance;
    private int pin;

    public BankAccount(double initialBalance, int pin) {
        balance = initialBalance;
        this.pin = pin;
    }

    public double getBalance() {
        return balance;
    }

    public boolean verifyPin(int enteredPin) {
        return pin == enteredPin;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited: $" + amount);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Withdrawn: $" + amount);
            return true;
        } else {
            System.out.println("Insufficient balance or invalid withdrawal amount.");
            return false;
        }
    }
}

class ATM {
    private BankAccount account;

    public ATM(BankAccount account) {
        this.account = account;
    }

    public void checkBalance() {
        double balance = account.getBalance();
        System.out.println("Current Balance: $" + balance);
    }

    public void deposit(double amount) {
        account.deposit(amount);
    }

    public boolean withdraw(double amount) {
        return account.withdraw(amount);
    }
}

public class ATMGUI extends JFrame {
    private BankAccount userAccount;
    private ATM atm;

    private JLabel cardLabel;
    private JComboBox<String> cardTypeComboBox;
    private JLabel pinLabel;
    private JPasswordField pinPasswordField;
    private JButton submitButton;

    private JLabel balanceLabel;
    private JTextField balanceTextField;
    private JLabel depositLabel;
    private JTextField depositTextField;
    private JButton depositButton;
    private JLabel withdrawLabel;
    private JTextField withdrawTextField;
    private JButton withdrawButton;

    public ATMGUI() {
        setTitle("ATM GUI");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout());
        cardLabel = new JLabel("Card Type:");
        String[] cardTypes = {"Debit", "Credit"};
        cardTypeComboBox = new JComboBox<>(cardTypes);
        pinLabel = new JLabel("PIN:");
        pinPasswordField = new JPasswordField(4);
        submitButton = new JButton("Submit");

        topPanel.add(cardLabel);
        topPanel.add(cardTypeComboBox);
        topPanel.add(pinLabel);
        topPanel.add(pinPasswordField);
        topPanel.add(submitButton);
        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        balanceLabel = new JLabel("Current Balance: $");
        centerPanel.add(balanceLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        balanceTextField = new JTextField(10);
        balanceTextField.setEditable(false);
        centerPanel.add(balanceTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        depositLabel = new JLabel("Deposit Amount: $");
        centerPanel.add(depositLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        depositTextField = new JTextField(10);
        centerPanel.add(depositTextField, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        depositButton = new JButton("Deposit");
        centerPanel.add(depositButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        withdrawLabel = new JLabel("Withdraw Amount: $");
        centerPanel.add(withdrawLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        withdrawTextField = new JTextField(10);
        centerPanel.add(withdrawTextField, gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;
        withdrawButton = new JButton("Withdraw");
        centerPanel.add(withdrawButton, gbc);

        add(centerPanel, BorderLayout.CENTER);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedCardType = (String) cardTypeComboBox.getSelectedItem();
                int enteredPin = Integer.parseInt(new String(pinPasswordField.getPassword()));

                if (selectedCardType.equals("Debit") && enteredPin == 1234) {
                    userAccount = new BankAccount(1000.0, enteredPin);
                    atm = new ATM(userAccount);
                    balanceTextField.setText(String.valueOf(userAccount.getBalance()));
                } else if (selectedCardType.equals("Credit") && enteredPin == 5678) {
                    userAccount = new BankAccount(2000.0, enteredPin);
                    atm = new ATM(userAccount);
                    balanceTextField.setText(String.valueOf(userAccount.getBalance()));
                } else {
                    showError("Incorrect PIN or invalid card type.");
                }
            }
        });

        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double amount = Double.parseDouble(depositTextField.getText());
                    atm.deposit(amount);
                    balanceTextField.setText(String.valueOf(userAccount.getBalance()));
                } catch (NumberFormatException ex) {
                    showError("Invalid deposit amount");
                }
            }
        });

        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double amount = Double.parseDouble(withdrawTextField.getText());
                    boolean success = atm.withdraw(amount);
                    if (success) {
                        balanceTextField.setText(String.valueOf(userAccount.getBalance()));
                    }
                } catch (NumberFormatException ex) {
                    showError("Invalid withdrawal amount");
                }
            }
        });
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ATMGUI atmGUI = new ATMGUI();
            atmGUI.setVisible(true);
        });
    }
}
