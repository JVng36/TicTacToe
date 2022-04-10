/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package TicTacToeProject;

import javax.swing.JOptionPane;
import java.util.Random;

/**
 *
 * @author JVang
 */
public class TicTacToeProject {

    static void homeworkHeader() {
        System.out.println("Ticket : 86713");
        System.out.println("Course : CMPR 112 ");
        System.out.println("Student : Jay Vang");
        System.out.println("Instructor : Joel Kirscher");
        System.out.println("Environment: Win 10 NetBeans ");
        System.out.println("Project Title: Homework 10 ");
        System.out.println();
    }
//Going to make a class for the Tic Tac Toe game. Will create methods within class.
//Then manipulate the methods to simulate TTT game.

    public static class TicTacToe {

        final int DIM = 3;//Size for 2D array. Will also use this for loop conditions
        private char[][] board;
        private boolean userTurn = true; //Using this boolean to check for who's turn and winner. Starting off on player's turn.

        public TicTacToe() {
            board = new char[DIM][DIM];
            createBoard();
        }//constructor makes the 2D array

        public void createBoard() {//Creates tictactoe board and puts in - for blanks
            for (int i = 0; i < DIM; i++) {
                for (int j = 0; j < DIM; j++) {
                    board[i][j] = '-';
                }
            }
        }

        public void printBoard() {//Copied from lecture
            for (int rows = 0; rows < board.length; rows++) {
                System.out.print("\t\t|");
                for (int cols = 0; cols < board.length; cols++) {
                    System.out.print(board[rows][cols] + "|");
                }
                System.out.println();
            }
            System.out.println();
        }

        public boolean boardFull() {//Check if board is full
            boolean isFull = true;//Setting the board to not full. Will loop until it is full.
            for (int i = 0; i < DIM; i++) {//Check all rows and cols for a hyphen.
                for (int j = 0; j < DIM; j++) {
                    if (board[i][j] == '-') {
                        isFull = false;
                    }
                }
            }
            return isFull;
        }

        /* Ways to win in a TTT game:
1.rows. from left to right + vice versa
2.columns. from up to down + vice versa
3.diagonals. from bottom left/right to top left/right.
         */
        public boolean winCheck() {
//Check each row
            for (int rowCheck = 0; rowCheck < 3; rowCheck++) {
                if (board[rowCheck][0] == board[rowCheck][1] && board[rowCheck][1] == board[rowCheck][2] && board[rowCheck][0] != '-') {
                    return true;
                }
            }
//Check each column
            for (int colCheck = 0; colCheck < 3; colCheck++) {
                if (board[0][colCheck] == board[1][colCheck] && board[1][colCheck] == board[2][colCheck] && board[0][colCheck] != '-') {
                    return true;
                }
            }

//Check the diagonals
            if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != '-') {
                return true;
            }
            if (board[2][0] == board[1][1] && board[1][1] == board[0][2] && board[2][0] != '-') {
                return true;
            }
            return false;
        }

        public void switchTurns() { //This function will switch the boolean to true or false.
            if (userTurn == true) {
                userTurn = false;
            } else {
                userTurn = true;
            }
        }

        public boolean checkValidMove(int row, int col) {//If the data inside the array slot is '-' then it is a valid move.
            if (board[row][col] == '-') {
                return true;
            }
            return false;
        }

        public void placeMark(int row, int col) {//Places the mark decision. Using this for both user and AI
            if (board[row][col] == '-') {//Not checking if within bounds here because I will check it when it is being inputted.
                if (userTurn == true) {
                    board[row][col] = 'x';
                } else {
                    board[row][col] = 'o';
                }
            }

        }
    }

    static void gameIntro() {// Just a function for instructions on how to play.
        System.out.println("This Tic-Tac-Toe game's moves can be referenced to as 0-3 for both rows and columns.");
        System.out.println("Here is a diagram to reference the points.");
        System.out.println(" 0 1 2");
        System.out.println("0 - - -");
        System.out.println("1 - - -");
        System.out.println("2 - - -");
        System.out.println();
    }

    static void playGame() {//Function that runs the game
        TicTacToe game = new TicTacToe();//Create new game object
        game.printBoard();
        try {
            while (game.boardFull() != true) {

//User's Turn
                String userIn; //Doing input with JOptionPane.

                int row;
                int col;

//Keep the user looped in here if they do not input correct number or they are inputting an invalid spot.
                do {
                    userIn = JOptionPane.showInputDialog("Please enter row: ");
                    row = Integer.parseInt(userIn);
                    userIn = JOptionPane.showInputDialog("Please enter column: ");
                    col = Integer.parseInt(userIn);

                    if (row < 0 || row > 3 || col < 0 || col > 3 || game.checkValidMove(row, col) == false) {
                        System.out.println("The coordinates you entered are either not within bounds (0-3) or already taken. Try again.");
                    }
                } while (row < 0 || row > 3 || col < 0 || col > 3 || game.checkValidMove(row, col) == false);

                game.placeMark(row, col);//Insert the user's input into the class object
                System.out.println("You Moved.");
                game.printBoard();
                System.out.println();
                game.switchTurns(); //Change to opponent's turn

//Win condition will check after user or CPU moves.
                if (game.winCheck() == true) {
                    System.out.println("You are the winner! Congratulations!");
                    break;
                } else if (game.boardFull() == true) {
                    System.out.println("It is a draw!");
                    break;
                }

//Opponent's turn
                Random randomNumbers = new Random();//Random generator for the AI

                int cpuRow = randomNumbers.nextInt(3);//Generate random number from 0-3 for row and col
                int cpuCol = randomNumbers.nextInt(3);

                while (game.checkValidMove(cpuRow, cpuCol) == false) {//If the move generated are not valid, keep looping for new ones.
                    cpuRow = randomNumbers.nextInt(3);
                    cpuCol = randomNumbers.nextInt(3);
                }

                game.placeMark(cpuRow, cpuCol);//Then place them

                System.out.println("Opponent Moved.");
                game.printBoard();
                System.out.println();

                game.switchTurns();//Change back to user's turn
                if (game.winCheck()) {
                    System.out.println("You lose! The CPU beat you.");
                    break;
                } else if (game.boardFull() == true) {
                    System.out.println("It is a draw!");
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Please make sure all your inputs are numbers that are within bounds.");
        }
    }

    public static void main(String[] args) {
        homeworkHeader();
        System.out.println("Welcome!");
        gameIntro();

        String userDecision = JOptionPane.showInputDialog("Would you like to play tic tac toe?(y for yes, n for no");
        if ("y".equals(userDecision)) {
            playGame();
        } else {
            System.out.println("Goodbye.");
        }

    }
}
