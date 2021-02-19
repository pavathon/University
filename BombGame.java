package Game;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Random;
import java.util.ArrayList;

/**
 * GUI Application.
 * A game where the player clicks a series of squares.
 * Aim is to get the highest score by clicking the most
 * squares before clicking on a square that has a bomb 
 * inside it.
 * When the player clicks on square that has a bomb,
 * the player loses the game.
 *
 * @author Mark Browne
 * @version 1
 */
public class BombGame extends JFrame implements MouseListener
{
    // Used to make the grid layered panels in the game panel
    private final int ROWS = 2;
    private final int COLUMNS = 5;
    private final int GAP = 2;
    private final int AREA = ROWS * COLUMNS;
    
    // Colours for each of the panels
    private final Color GAMEPANEL_COLOUR = Color.RED;
    private final Color MENUPANEL_COLOUR = Color.ORANGE;
    private final Color DIFFICULTYPANEL_COLOUR = Color.MAGENTA;
    
    // The three main panels of the game
    private final JPanel gamePanel = new JPanel(new GridLayout(ROWS, COLUMNS, GAP, GAP));
    private final JPanel menuPanel = new JPanel(new FlowLayout());
    private final JPanel difficultyPanel = new JPanel(new FlowLayout());
    
    // Refers to the panel that has the bomb
    private JPanel bomb;
    
    // Stores all the grid layered panels in an array.
    private final JPanel[] squares = new JPanel[AREA];

    // Displays the current score of the player
    private final JLabel currentPointsLabel = new JLabel("Your points: 0");
    
    // Label that will be displayed when the game is over
    private final JLabel finishedMessage = new JLabel("");
   
    // The amount of points the player has
    private int points = 0;
    
    // Amount of points needed for the player to win
    // Default difficulty is intermediate
    private int pointsToWin = 7;
    
    // Stores all the panels in the game panel that have been clicked
    private final ArrayList<JPanel> clickedSquares = new ArrayList<>();
    
    /**
     * Constructor for the class BombGame.
     */
    public BombGame()
    {
        super("chasing-bombs-mb2021");
        setSize(800, 400);
        makeFrame();
        setVisible(true);
    }
    
    /**
     * All the main features of the GUI are implemented here.
     */
    private void makeFrame()
    {       
        Container cp = getContentPane();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Set layout for the container
        cp.setLayout(new GridLayout());
        
        Font labelFont = new Font("Arial",Font.BOLD, 15);
        
        // Labels used in the difficulty panel
        JLabel difficultyLabel = new JLabel("Difficulty: Intermediate");
        JLabel ptwLabel = new JLabel("Points needed to win: 7");
        
        // All the buttons the user will press
        JButton startButton, exitButton, easyButton, medButton, hardButton;
        startButton = new JButton("Play the game");
        exitButton = new JButton("Exit");
        easyButton = new JButton("Easy");
        medButton = new JButton("Intermediate");
        hardButton = new JButton("Difficult");
        
        // Set background color for the panels and font for the labels
        menuPanel.setBackground(MENUPANEL_COLOUR);
        difficultyPanel.setBackground(DIFFICULTYPANEL_COLOUR);
        
        difficultyLabel.setFont(labelFont);
        ptwLabel.setFont(labelFont);
        currentPointsLabel.setFont(labelFont);
        finishedMessage.setFont(labelFont);

        // Make the actual game and add the bomb to the game.
        makeGameBoard();
        addBomb();
        
        // Add functionality to the buttons
        startButton.addActionListener(e -> restartGame());
        exitButton.addActionListener(e -> System.exit(0));
        
        String diffString = "Difficulty: ";
        String ptwString = "Points needed to win: ";

        // Sets the game to easy difficulty
        easyButton.addActionListener(e -> {
            pointsToWin = 5;
            difficultyLabel.setText(diffString + "Easy");
            ptwLabel.setText(ptwString + pointsToWin);
        });
        // Sets the game to medium difficulty
        medButton.addActionListener(e -> {
            pointsToWin = 7;
            difficultyLabel.setText(diffString + "Intermediate");
            ptwLabel.setText(ptwString + pointsToWin);
        });
        // Sets the game to hard difficulty
        hardButton.addActionListener(e -> {
            pointsToWin = 9;
            difficultyLabel.setText(diffString + "Difficult");
            ptwLabel.setText(ptwString + pointsToWin);
        });
        
        // Add the buttons and labels for each panel
        menuPanel.add(startButton);
        menuPanel.add(exitButton);
        menuPanel.add(currentPointsLabel);
        menuPanel.add(finishedMessage);

        // Add all the difficulty buttons to the difficulty frame
        difficultyPanel.add(easyButton);
        difficultyPanel.add(medButton);
        difficultyPanel.add(hardButton);
        difficultyPanel.add(difficultyLabel);
        difficultyPanel.add(ptwLabel);
        
        // Add all the panels to the main frame
        add(gamePanel);
        add(menuPanel);
        add(difficultyPanel);
    }
    
    /**
     * Create all the grid layered panels
     */
    private void makeGameBoard()
    {
        for (int sq = 0; sq < AREA; sq++) {
            JPanel pane = new JPanel();
            squares[sq] = pane;
            gamePanel.add(pane);
            pane.setBackground(GAMEPANEL_COLOUR);
            pane.addMouseListener(this);
        }
    }
    
    /**
     * Add a bomb to one random block in the game panel.
     */
    private void addBomb()
    {
        int bombIndex = new Random().nextInt(squares.length);
        bomb = squares[bombIndex];
    }
    
    /**
     * Resets the game to its original state.
     */
    private void restartGame()
    {
        points = 0;
        clickedSquares.forEach(square -> square.setBackground(GAMEPANEL_COLOUR));
        clickedSquares.clear();
        finishedMessage.setText("");
        currentPointsLabel.setText(showCurrentPoints());
        addBomb();
    }
    
    /**
     * Displays the current points the player has.
     * @return The amount of points the player currently has.
     */
    private String showCurrentPoints()
    {
        return "Your points: " + points;
    }
    
    /**
     * Displays the points the player has when the game has ended.
     * @return The amount of points the player has when the game
     * ended.
     */
    private String showEndPoints()
    {
        String returnString = "You got " + points + " point";
        if (points != 1) returnString += "s";

        return returnString;
    }
    
    /**
     * When mouse button has been pressed and released on
     * a component.
     * Changes color of panel to a darker version as to show it's 
     * been clicked.
     * Shows a message to the player when they complete the game
     * on whether the lost or won.
     */
    @Override
    public void mouseClicked(MouseEvent e) 
    {
        JPanel source = (JPanel) e.getSource();  
        if (bomb != source) {
            points++;
            currentPointsLabel.setText(showCurrentPoints());
            
            source.setBackground(GAMEPANEL_COLOUR.darker().darker());
            clickedSquares.add(source);

            if (points == pointsToWin) finishedMessage.setText("You win! " + showEndPoints());
        }
        else finishedMessage.setText("You lose! " + showEndPoints());
    }
    
    /**
     * When mouse enters a component
     */
    @Override
    public void mouseEntered(MouseEvent e) 
    {
    }
    
    /**
     * When mouse exits a component
     */
    @Override
    public void mouseExited(MouseEvent e) 
    {
    }
    
    /**
     * When mouse button has been pressed on a component
     */
    @Override
    public void mousePressed(MouseEvent e) 
    {
    }
    
    /**
     * When a mouse button has been released on a component
     */
    @Override
    public void mouseReleased(MouseEvent e) 
    {
    }
}
