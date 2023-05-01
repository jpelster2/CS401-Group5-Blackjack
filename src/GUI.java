import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI {

	private JFrame frame;
    private JLabel playerHandLabel;
    private JLabel dealerHandLabel;
    private JLabel statusLabel;
    private JButton hitButton;
    private JButton standButton;
    private JButton leaveGameButton;
    private JButton startGameButton;
    private JButton betGameButton;


    public GUI() {
        initialize();
    }

    private void initialize() {
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
        startGameButton = new JButton("Start Game");
        betGameButton = new JButton("Bet Amount");

        mainPanel.add(playerHandLabel, BorderLayout.NORTH);
        mainPanel.add(dealerHandLabel, BorderLayout.CENTER);
        mainPanel.add(statusLabel, BorderLayout.SOUTH);

        JPanel buttonPanel = new JPanel();
        mainPanel.add(buttonPanel, BorderLayout.EAST);
        buttonPanel.add(hitButton);
        buttonPanel.add(standButton);
        buttonPanel.add(leaveGameButton);
        buttonPanel.add(startGameButton);
        buttonPanel.add(betGameButton);

        hitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	String hit = Client.doHit();
            	statusLabel.setText(statusLabel.getText()+" Player has hit. ");
            	playerHandLabel.setText(playerHandLabel.getText()+" "+hit);
            	if(hit.equals("player busted")) {
            		Client.doStand();
            		statusLabel.setText(statusLabel.getText()+" Player has passed their turn. ");
            	}
            }
        });

        standButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	//disable buttons until the dealers turn comes and the round is over. 
            	hitButton.setEnabled(false);
                standButton.setEnabled(false);
                startGameButton.setEnabled(false);
                
                String stand = Client.doStand();
                
                statusLabel.setText(statusLabel.getText()+ stand + " is up next. ");

            }
        });
        
        leaveGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	Client.doLeaveGame();
            	statusLabel.setText(statusLabel.getText()+" Player Left ");
            	frame.dispose();
                Client.lobby();
            }
        });
        
        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	String turn = null;
            	
            	turn = Client.doGameStart();
            	dealerHandLabel.setText(dealerHandLabel.getText()+" "+turn);
            	statusLabel.setText(statusLabel.getText()+ turn +" The game has begun! ");
            	
            	hitButton.setEnabled(false);
                standButton.setEnabled(false);
                startGameButton.setEnabled(false);
                betGameButton.setEnabled(false);
                
            	while(!turn.equals("go")) {
            		turn = Client.doWhosTurn();
            		statusLabel.setText(statusLabel.getText()+ turn +" Player's turn. ");
            		if(turn.equals("dealer")) {
            			statusLabel.setText(statusLabel.getText()+" Dealer's turn. ");
            			dealerHandLabel.setText("Dealer Hand: "+turn);
            			Client.doWinnings();
            			startGameButton.setEnabled(true);
            			break;	// ADDED BY JAMES AND GRAYSON SINCE WE THINK WE NEED IT
            		}
            	}
            	if (turn.equals("go")) {
            		hitButton.setEnabled(true);
                    standButton.setEnabled(true);
                    startGameButton.setEnabled(true);
            		statusLabel.setText(statusLabel.getText()+ "Your turn. ");
            	}
            }
        });
        
        betGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String betBalance = JOptionPane.showInputDialog("Enter the amount to bet: ");
                String bet = Client.dobet(betBalance);
                
                if(bet.equals("success")) {
                	statusLabel.setText(statusLabel.getText()+"Player bet: " + betBalance +" ");
                }
            }
        });
        
        frame.setVisible(true);
    }

}