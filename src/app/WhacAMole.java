package app;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;
public class WhacAMole {

    // Create a window
    int boardWidth = 600;
    int boardHeight = 650; //room for text for the score

    JFrame frame = new JFrame("Whac A Mole");
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();

    JButton[] board = new JButton[9];

    ImageIcon moleIcon;
    ImageIcon plantIcon;

    JButton currMoleTile;
    JButton currPlantTile;

    Random random = new Random();
    Timer setMoleTimer;
    Timer setPlantTimer;
    int score = 0;

    //Constructor
    WhacAMole(){
//		frame.setVisible(true); //My window is visible before all of my components are loaded
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        textLabel.setFont(new Font("Arial", Font.PLAIN, 50));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Score: 0");
        textLabel.setOpaque(true);

        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel);
        frame.add(textPanel, BorderLayout.NORTH);

        boardPanel.setLayout(new GridLayout(3, 3));
//		boardPanel.setBackground(Color.black);
        frame.add(boardPanel);

//		plantIcon = new ImageIcon(getClass().getResource("piranha.png")); This is how you import image

        //import and scale
        Image plantImg = new ImageIcon(getClass().getResource("piranha.png")).getImage();
        plantIcon = new ImageIcon(plantImg.getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH));

        Image moleImg = new ImageIcon(getClass().getResource("monty.png")).getImage();
        moleIcon = new ImageIcon(moleImg.getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH));

        for(int i = 0; i < 9; i++) {
            JButton tile = new JButton();
            board[i] = tile;
            boardPanel.add(tile);
            tile.setFocusable(false);
//			tile.setIcon(moleIcon); test the image

            tile.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    JButton tile = (JButton) e.getSource(); //cast because it returns object
                    if(tile == currMoleTile) {
                        score += 10;
                        textLabel.setText("Score: " + Integer.toString(score));
                    }
                    else if(tile == currPlantTile) {
                        textLabel.setText("Game Over: " + Integer.toString(score));
                        setMoleTimer.stop(); //stop mole from moving
                        setPlantTimer.stop(); //stop plant from moving
                        for(int i = 0; i < 9; i++) {
                            board[i].setEnabled(false); //disable buttons
                        }
                    }
                }
            });
        }

        setMoleTimer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                //remove mole from current tile
                if(currMoleTile != null) {
                    currMoleTile.setIcon(null);
                    currMoleTile = null;
                }

                //randomly select another tile
                int num = random.nextInt(9); //random number from 0-8
                JButton tile = board[num];

                //if tile is occupied by plant, skip tile for this turn
                if(currPlantTile == tile) return;

                //set tile to mole
                currMoleTile = tile;
                currMoleTile.setIcon(moleIcon);
            }
        });

        setPlantTimer = new Timer(1500, new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                //remove mole from current tile
                if(currPlantTile != null) {
                    currPlantTile.setIcon(null);
                    currPlantTile = null;
                }

                //randomly select another tile
                int num = random.nextInt(9); //random number from 0-8
                JButton tile = board[num];

                //if tile is occupied by mole, skip tile for this turn
                if(currMoleTile == tile) return;


                //set tile to mole
                currPlantTile = tile;
                currPlantTile.setIcon(plantIcon);
            }
        });

        setMoleTimer.start();
        setPlantTimer.start();

        frame.setVisible(true); //I make the window visible after all of my components are loaded
    }
}
