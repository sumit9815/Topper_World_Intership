import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class VotingSystem {
    private JFrame frame;
    private Map<String, String> users;
    private Map<String, String> votes;
    private String currentUser;

    public VotingSystemGUI() {
        frame = new JFrame("Online Voting System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        users = new HashMap<>();
        votes = new HashMap<>();

        JPanel loginPanel = new JPanel(new GridLayout(4, 1));

        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();

        JButton loginButton = new JButton("Login");
        JLabel statusLabel = new JLabel();

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (authenticateUser(username, password)) {
                    statusLabel.setText("Login successful.");
                    currentUser = username;
                    createVotingPanel();
                } else {
                    statusLabel.setText("Login failed. Invalid username or password.");
                }

                usernameField.setText("");
                passwordField.setText("");
            }
        });

        loginPanel.add(usernameLabel);
        loginPanel.add(usernameField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);
        loginPanel.add(loginButton);
        loginPanel.add(statusLabel);

        frame.add(loginPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private boolean authenticateUser(String username, String password) {
        // Simulated user authentication (replace with your own logic)
        if (users.containsKey(username) && users.get(username).equals(password)) {
            return true;
        }
        return false;
    }

    private void createVotingPanel() {
        frame.getContentPane().removeAll();
        frame.revalidate();
        frame.repaint();

        JPanel votingPanel = new JPanel(new GridLayout(4, 1));

        JLabel welcomeLabel = new JLabel("Welcome, " + currentUser + "!");
        JLabel selectCandidateLabel = new JLabel("Select a candidate:");

        JComboBox<String> candidateComboBox = new JComboBox<>(new String[]{"Candidate 1", "Candidate 2", "Candidate 3"});

        JButton voteButton = new JButton("Vote");
        JLabel voteStatus = new JLabel();

        voteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedCandidate = (String) candidateComboBox.getSelectedItem();
                if (selectedCandidate != null) {
                    votes.put(currentUser, selectedCandidate);
                    voteStatus.setText("Vote cast successfully for " + selectedCandidate);
                    candidateComboBox.setEnabled(false);
                    voteButton.setEnabled(false);
                } else {
                    voteStatus.setText("Please select a candidate.");
                }
            }
        });

        votingPanel.add(welcomeLabel);
        votingPanel.add(selectCandidateLabel);
        votingPanel.add(candidateComboBox);
        votingPanel.add(voteButton);
        votingPanel.add(voteStatus);

        frame.add(votingPanel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new VotingSystemGUI();
            }
        });
    }
}
