import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
/*
@author Igor I.
* version 0.0.1
* Dated by 21.05.2019*/


class GameMines  extends JFrame {
    //constants
    final String TITLE_OF_PROGRAM = "Mines";
    final String SIGN_OF_FLAG = "F";
    final int BLOCK_SIZE = 30;
    final int FIELD_SIZE = 9; //size gamefield
    final int FIELD_DX = 6;
    final int FIELD_DY = 28;
    final int START_LOCATION = 200;
    final int MOUSE_BUTTON_LEFT = 1;
    final int MOUSE_BUTTON_RIGHT = 3;
    final int NUMBER_OF_MINES = 10; //mines in gamefield
    final int[] COLOR_OF_NUMBERS = {0x0000FF, 0x008000, 0xFF0000, 0x800000, 0x0};
    Cell[][] field  = new Cell[FIELD_SIZE][FIELD_SIZE];
    Random random = new Random(); //random generator
    int countOpenedCells;
    boolean youWon, youLose; //win or lose?
    int bangX, bangY; //where is bada-buum




    public static void main(String args[]) {
        new GameMines(); //make the object of main class

    }

    GameMines() {
        setTitle(TITLE_OF_PROGRAM);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(START_LOCATION,START_LOCATION,FIELD_SIZE*BLOCK_SIZE
                +FIELD_DX,FIELD_SIZE*BLOCK_SIZE+FIELD_DY);           //start window position and sizes
        setResizable(false);
        Canvas canvas = new Canvas();
        canvas.setBackground(Color.white);
        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                int x = e.getX()/BLOCK_SIZE;
                int y = e.getY()/BLOCK_SIZE;
                if (!youLose && !youWon) {
                    if (e.getButton() == MOUSE_BUTTON_LEFT) // left button mouse
                        if (field[y][x].isNotOpen()) {
//                            openCells(x, y);
                            field[y][x].open();
                            youWon = countOpenedCells == FIELD_SIZE*FIELD_SIZE - NUMBER_OF_MINES; // winning check
                            if (youLose) {
                                bangX = x;
                                bangY = y;
                            }
                        }
                    if (e.getButton() == MOUSE_BUTTON_RIGHT) field[y][x].inverseFlag(); // right button mouse
//                    if (youLose || youWon) timeLabel.stopTimer(); // game over
                    canvas.repaint();
                }
            }
        });
        add(BorderLayout.CENTER, canvas);
//        add(BorderLayout.SOUTH, timeLabel);
        setVisible(true);
        initField();
    }


    void initField() {// initialization of the playing field
        int x, y, countMines = 0;
        // create cells for the field
        for (x = 0; x < FIELD_SIZE; x++)
            for (y = 0; y < FIELD_SIZE; y++)
                field[y][x] = new Cell();
        // to mine field
        while (countMines < NUMBER_OF_MINES) {
            do {
                x = random.nextInt(FIELD_SIZE);
                y = random.nextInt(FIELD_SIZE);
            } while (field[y][x].isMined());
            field[y][x].mine();
            countMines++;
        }
        // to count dangerous neighbours
        for (x = 0; x < FIELD_SIZE; x++)
            for (y = 0; y < FIELD_SIZE; y++)
                if (!field[y][x].isMined()) {
                    int count = 0;
                    for (int dx = -1; dx < 2; dx++)
                        for (int dy = -1; dy < 2; dy++) {
                            int nX = x + dx;
                            int nY = y + dy;
                            if (nX < 0 || nY < 0 || nX > FIELD_SIZE - 1 || nY > FIELD_SIZE - 1) {
                                nX = x;
                                nY = y;
                            }
                            count += (field[nY][nX].isMined()) ? 1 : 0;
                        }
                    field[y][x].setCountBomb(count);
                }

    }

    class Cell  {
        void open() {

        }
        void inverseFlag() {

        }
        boolean isNotOpen() {
            return false;

        }
        boolean isMined() {
            return false;

        }
        void mine () {

        }
        void setCountBomb(int count) {

        }
        void paint(Graphics g, int x, int y) {

        }

    }

    class Canvas extends JPanel {
        @Override
        public void paint(Graphics g) {
            super.paint(g);  //call for parent method
            for (int x = 0; x < FIELD_SIZE; x++)
                for (int y = 0; y < FIELD_SIZE; y++) field[y][x].paint(g, x, y);
        }


    }

}
