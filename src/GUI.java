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
        initialize(objectOutputStream, objectInputStream);
    }

    private void initialize(ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream) {
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
        String dealerHand = client.doGameState(objectOutputStream, objectInputStream);
        dealerHandLabel.setText(dealerHandLabel.getText()+" "+dealerHand);
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
            	String hit = client.doHit(objectOutputStream, objectInputStream);
            	statusLabel.setText(statusLabel.getText()+" Player has hit");
            	playerHandLabel.setText(playerHandLabel.getText()+" "+hit);
            }
        });

        standButton = new JButton("Stand");
        buttonPanel.add(standButton);
        standButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	//disable buttons until the dealers turn comes and the round is over. 
            	hitButton.setEnabled(false);
                standButton.setEnabled(false);
                
                statusLabel.setText(statusLabel.getText()+" Player has passed their turn");
                
                String stand = client.doStand(objectOutputStream, objectInputStream);
                
                statusLabel.setText(statusLabel.getText()+" Dealer has taken its turn");
                dealerHandLabel.setText(dealerHandLabel.getText()+" "+stand);
            }
        });
        
        leaveGameButton = new JButton("Leave Game");
        buttonPanel.add(leaveGameButton);
        leaveGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	statusLabel.setText(statusLabel.getText()+" Player Left");
            	frame.dispose();
                client.lobby(null, null);
            }
        });
        
    }
}