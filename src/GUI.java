import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class GUI {

    private JFrame frame;
    private JPanel panel;
    private JLabel playerHandLabel;
    private JLabel dealerHandLabel;
    private JLabel deckLabel;
    private JLabel statusLabel;
    private JButton hitButton;
    private JButton standButton;
    private JButton leaveGameButton;


    public GUI(ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream) {
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Blackjack");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        panel = new JPanel();
        frame.add(panel);
        panel.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        panel.add(topPanel, BorderLayout.NORTH);
        topPanel.setLayout(new GridLayout(2, 1));

        dealerHandLabel = new JLabel("Dealer's hand: ");
        topPanel.add(dealerHandLabel);
        playerHandLabel = new JLabel("Player's hand: ");
        topPanel.add(playerHandLabel);

        deckLabel = new JLabel("Deck: ");
        panel.add(deckLabel, BorderLayout.CENTER);

        statusLabel = new JLabel("Status: ");
        panel.add(statusLabel, BorderLayout.SOUTH);

        JPanel buttonPanel = new JPanel();
        panel.add(buttonPanel, BorderLayout.EAST);
        buttonPanel.setLayout(new GridLayout(2, 1));

        hitButton = new JButton("Hit");
        buttonPanel.add(hitButton);
        hitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	playerHandLabel.setText("Player's hand: 10 of Hearts");
            }
        });

        standButton = new JButton("Stand");
        buttonPanel.add(standButton);
        standButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement stand action
            }
        });
        
        leaveGameButton = new JButton("Leave Game");
        buttonPanel.add(leaveGameButton);
        leaveGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                client.lobby(null, null);
            }
        });
    }
}