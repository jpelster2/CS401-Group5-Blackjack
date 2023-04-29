import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class GUI {

	private JFrame frame;
    private JLabel playerHandLabel;
    private JLabel dealerHandLabel;
    private JLabel statusLabel;
    private JButton hitButton;
    private JButton standButton;
    private JButton leaveGameButton;


    public GUI(ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream) {
        initialize(objectOutputStream, objectInputStream);
    }

    private void initialize(ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream) {
    	frame = new JFrame("Blackjack Game");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 600);

        JPanel mainPanel = new JPanel(new BorderLayout());
        frame.setContentPane(mainPanel);

        playerHandLabel = new JLabel("Player Hand: ");
        dealerHandLabel = new JLabel("Dealer Hand: ");
        statusLabel = new JLabel("Status: ");
        hitButton = new JButton("Hit");
        standButton = new JButton("Stand");
        leaveGameButton = new JButton("Leave Game");

        mainPanel.add(playerHandLabel, BorderLayout.NORTH);
        mainPanel.add(dealerHandLabel, BorderLayout.CENTER);
        mainPanel.add(statusLabel, BorderLayout.SOUTH);

        JPanel buttonPanel = new JPanel();
        mainPanel.add(buttonPanel, BorderLayout.EAST);
        buttonPanel.add(hitButton);
        buttonPanel.add(standButton);
        buttonPanel.add(leaveGameButton);

        hitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	String hit = Client.doHit(objectOutputStream, objectInputStream);
            	statusLabel.setText(statusLabel.getText()+" Player has hit");
            	playerHandLabel.setText(playerHandLabel.getText()+" "+hit);
            }
        });

        standButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	//disable buttons until the dealers turn comes and the round is over. 
            	hitButton.setEnabled(false);
                standButton.setEnabled(false);
                
                statusLabel.setText(statusLabel.getText()+" Player has passed their turn");
                
                String stand = Client.doStand(objectOutputStream, objectInputStream);
                
                statusLabel.setText(statusLabel.getText()+" Dealer has taken its turn");
                dealerHandLabel.setText(dealerHandLabel.getText()+" "+stand);
            }
        });
        
        leaveGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	statusLabel.setText(statusLabel.getText()+" Player Left");
            	frame.dispose();
                Client.lobby(null, null);
            }
        });
        frame.setVisible(true);
    }

}