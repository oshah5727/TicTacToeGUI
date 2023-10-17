import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeFrame extends JFrame {
    private JPanel boardPnl;
    private JButton quitBtn;
    private static int ROW = 3;
    private static int COL = 3;
    private static String[][] board = new String[ROW][COL];
    static String player = "X";
    static int moveCnt = 0;
    static final int MOVES_FOR_WIN = 5;
    final int MOVES_FOR_TIE = 7;
    private static TicTacToeButton[][] buttons;

    public TicTacToeFrame() {
        setLayout(new BorderLayout());

        boardPnl = new JPanel();
        boardPnl.setLayout(new GridLayout(ROW, COL));

        buttons = new TicTacToeButton[ROW][COL];
        buttonClickListener buttonListener = new buttonClickListener();
        for (int row = 0; row < ROW; row++) {
            for (int column = 0; column < COL; column++) {
                buttons[row][column] = new TicTacToeButton(row, column);
                buttons[row][column].addActionListener(buttonListener);
                boardPnl.add(buttons[row][column]);
            }
        }

        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                board[row][col] = " ";
            }
        }

        add(boardPnl, BorderLayout.CENTER);

        quitBtn = new JButton("Quit");
        quitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int response = JOptionPane.showConfirmDialog(
                        TicTacToeFrame.this,
                        "Are you sure you want to quit?",
                        "Quit",
                        JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
        add(quitBtn, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

    }

    private class buttonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            TicTacToeButton clickedButton = (TicTacToeButton) e.getSource();
            int row = clickedButton.getRow();
            int column = clickedButton.getCol();

            if (isValidMove(row, column)) {
                buttons[row][column].setText(player);
                moveCnt++;

                if (isWin(player)) {
                    JOptionPane.showMessageDialog(TicTacToeFrame.this, "Player " + player + " wins!");
                    int response = JOptionPane.showConfirmDialog(TicTacToeFrame.this, "Do you want to play another game?", "Play Again", JOptionPane.YES_NO_OPTION);
                    if (response == JOptionPane.YES_OPTION) {
                        clearBoard();
                        display();
                    }
                    else {
                        System.exit(0);
                    }
                }

                else if (isTie()) {
                    JOptionPane.showMessageDialog(TicTacToeFrame.this, "It's a Tie!");
                    int response = JOptionPane.showConfirmDialog(TicTacToeFrame.this, "Do you want to play another game?", "Play Again", JOptionPane.YES_NO_OPTION);
                    if (response == JOptionPane.YES_OPTION) {
                        clearBoard();
                        display();
                    }
                    else {
                        System.exit(0);
                    }
                }

                else {
                    player = (player.equals("X")) ? "O" : "X";
                }
            }

            else {
                JOptionPane.showMessageDialog(TicTacToeFrame.this, "Invalid move! Try again.");
            }
        }
    }

        private static void clearBoard() {
            // sets all the board elements to a space
            for (int row = 0; row < ROW; row++) {
                for (int col = 0; col < COL; col++) {
                    board[row][col] = " ";
                }
            }
        }

    private static void display() {
        // shows the Tic Tac Toe game
        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                buttons[row][col].setText(board[row][col]);
            }
        }
    }
        private static boolean isValidMove(int row, int col) {
            boolean retVal = false;
            if (board[row][col].equals(" ")) {
                retVal = true;
                board[row][col] = player;
                moveCnt++;
            }
            return retVal;

        }

        private static boolean isWin(String player) {
         if (moveCnt < MOVES_FOR_WIN) {
            return false;
        }

         if (isColWin(player) || isRowWin(player) || isDiagnalWin(player)) {
                return true;
         }

            return false;
        }

        private static boolean isColWin(String player) {
            // checks for a col win for specified player
            for (int col = 0; col < COL; col++) {
                if (board[0][col].equals(player) &&
                        board[1][col].equals(player) &&
                        board[2][col].equals(player)) {
                    return true;
                }
            }
            return false; // no col win
        }

        private static boolean isRowWin(String player) {
            // checks for a row win for the specified player
            for (int row = 0; row < ROW; row++) {
                if (board[row][0].equals(player) &&
                        board[row][1].equals(player) &&
                        board[row][2].equals(player)) {
                    return true;
                }
            }
            return false; // no row win
        }

        private static boolean isDiagnalWin(String player) {
            // checks for a diagonal win for the specified player

            if (board[0][0].equals(player) &&
                    board[1][1].equals(player) &&
                    board[2][2].equals(player)) {
                return true;
            }
            if (board[0][2].equals(player) &&
                    board[1][1].equals(player) &&
                    board[2][0].equals(player)) {
                return true;
            }
            return false;
        }

    // checks for a tie before board is filled.
    // check for the win first to be efficient
    private boolean isTie() {
        boolean xFlag = false;
        boolean oFlag = false;
        // Check all 8 win vectors for an X and O so
        // no win is possible
        // Check for row ties
        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                if (board[row][col].equals("X")) {
                    xFlag = true; // there is an X in this row
                }
                if (board[row][col].equals("O")) {
                    oFlag = true; // there is an O in this row
                }
            }


            if (!(xFlag && oFlag))
            {
                return false; // No tie can still have a row win
            }

            xFlag = oFlag = false;
        }
        // Now scan the columns
        for (int col = 0; col < COL; col++) {
            for (int row = 0; row < ROW; row++) {
                if (board[row][col].equals("X")) {
                    xFlag = true; // there is an X in this col
                }
                if (board[row][col].equals("O")) {
                    oFlag = true; // there is an O in this col
                }
            }


            if (!(xFlag && oFlag))
            {
                return false; // No tie can still have a col win
            }

            xFlag = oFlag = false;
        }

        // Now check for the diagonals
        for (int i = 0; i < ROW; i++) {
            if (board[i][i].equals("X")) {
                xFlag = true;
            }
            if (board[i][i].equals("O")) {
                oFlag = true;
            }
        }

        if (!(xFlag && oFlag)) {
            return false; // No tie can still have a diag win
        }

        xFlag = oFlag = false;

        for (int i = 0; i < ROW; i++) {
            if (board[i][ROW - 1 - i].equals("X")) {
                xFlag = true;
            }
            if (board[i][ROW - 1 - i].equals("O")) {
                oFlag = true;
            }
        }

        // Checked every vector so I know I have a tie

        return moveCnt >= MOVES_FOR_TIE;

    }

        public class TicTacToeButton extends JButton {
            private int row;
            private int col;

            public TicTacToeButton(int row, int col) {
                this.row = row;
                this.col = col;
            }

            public int getRow() {
                return row;
            }

            public int getCol() {
                return col;
            }
            
        }
    }

