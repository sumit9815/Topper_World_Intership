import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

class Atm {
    private String name;
    private String party;

    public Candidate(String name, String party) {
        this.name = name;
        this.party = party;
    }

    public String getName() {
        return name;
    }

    public String getParty() {
        return party;
    }
}

class Election {
    private String name;
    private Set<Candidate> candidates;

    public Election(String name, Set<Candidate> candidates) {
        this.name = name;
        this.candidates = candidates;
    }

    public String getName() {
        return name;
    }

    public Set<Candidate> getCandidates() {
        return candidates;
    }
}

class Voter {
    private String username;
    private Set<Election> votedElections;

    public Voter(String username) {
        this.username = username;
        this.votedElections = new HashSet<>();
    }

    public String getUsername() {
        return username;
    }

    public Set<Election> getVotedElections() {
        return votedElections;
    }

    public void vote(Election election, Candidate candidate) {
        if (!votedElections.contains(election)) {
            votedElections.add(election);
            System.out.println(username + " voted for " + candidate.getName() + " in " + election.getName());
        } else {
            System.out.println(username + " has already voted in " + election.getName());
        }
    }
}

public class OnlineVotingSystemGUI {
    private JFrame frame;
    private JTextArea outputTextArea;

    private Election currentElection;

    private Voter currentUser;

    public OnlineVotingSystemGUI() {
        frame = new JFrame("Online Voting System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        outputTextArea = new JTextArea();
        outputTextArea.setEditable(false);

        JPanel controlPanel = new JPanel(new GridLayout(3, 2));

        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField();
        JButton loginButton = new JButton("Login");
        JLabel electionLabel = new JLabel("Election:");
        JComboBox<String> electionComboBox = new JComboBox<>(new String[]{"Presidential Election", "Mayoral Election"});
        JButton voteButton = new JButton("Vote");

        controlPanel.add(usernameLabel);
        controlPanel.add(usernameField);
        controlPanel.add(loginButton);
        controlPanel.add(electionLabel);
        controlPanel.add(electionComboBox);
        controlPanel.add(voteButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                currentUser = new Voter(username);
                outputTextArea.append("Logged in as " + username + "\n");
            }
        });

        voteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentUser != null) {
                    String selectedElection = (String) electionComboBox.getSelectedItem();
                    if (selectedElection != null) {
                        if (selectedElection.equals("Presidential Election")) {
                            // Define candidates for the presidential election
                            Set<Candidate> presidentialCandidates = new HashSet<>();
                            presidentialCandidates.add(new Candidate("Candidate A", "Party X"));
                            presidentialCandidates.add(new Candidate("Candidate B", "Party Y"));
                            currentElection = new Election(selectedElection, presidentialCandidates);
                        } else if (selectedElection.equals("Mayoral Election")) {
                            // Define candidates for the mayoral election
                            Set<Candidate> mayoralCandidates = new HashSet<>();
                            mayoralCandidates.add(new Candidate("Candidate X", "Party A"));
                            mayoralCandidates.add(new Candidate("Candidate Y", "Party B"));
                            currentElection = new Election(selectedElection, mayoralCandidates);
                        }
                        outputTextArea.append("Selected election: " + selectedElection + "\n");

                        if (currentElection != null) {
                            voteDialog();
                        }
                    }
                } else {
                    outputTextArea.append("Please log in first.\n");
                }
            }
        });

        frame.add(controlPanel, BorderLayout.NORTH);
        frame.add(new JScrollPane(outputTextArea), BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void voteDialog() {
        JFrame voteFrame = new JFrame("Vote");
        voteFrame.setSize(300, 200);
        voteFrame.setLayout(new BorderLayout());

        JPanel votePanel = new JPanel(new GridLayout(3, 1));

        JLabel candidateLabel = new JLabel("Select a candidate:");
        JComboBox<String> candidateComboBox = new JComboBox<>();
        for (Candidate candidate : currentElection.getCandidates()) {
            candidateComboBox.addItem(candidate.getName());
        }
        JButton confirmButton = new JButton("Confirm");

        votePanel.add(candidateLabel);
        votePanel.add(candidateComboBox);
        votePanel.add(confirmButton);

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedCandidateName = (String) candidateComboBox.getSelectedItem();
                if (selectedCandidateName != null) {
                    for (Candidate candidate : currentElection.getCandidates()) {
                        if (candidate.getName().equals(selectedCandidateName)) {
                            currentUser.vote(currentElection, candidate);
                            voteFrame.dispose();
                            break;
                        }
                    }
                } else {
                    outputTextArea.append("Please select a candidate.\n");
                }
            }
        });

        voteFrame.add(votePanel, BorderLayout.CENTER);
        voteFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new OnlineVotingSystemGUI();
            }
        });
    }
}