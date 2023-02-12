package xyz.lgjt;

import java.util.Scanner;

import static xyz.lgjt.Consts.BOT_PLAYER;
import static xyz.lgjt.Consts.HUMAN_PLAYER;

public class Game {

    private int[] board = new int[3 * 3];

    // Player 1 is the human and 2 is bot
    private int currentPlayer;
    private boolean victory = false;
    private int winner;

    private Bot bot;

    public Game(int starter) {
        this.currentPlayer = starter;
        this.bot = new Bot();
    }

    public void makeMove(int i) {
        if (!isOccupied(i))
            board[i] = this.currentPlayer;
        System.out.println("Player " + currentPlayer + ": " + i);
        nextPlayer();
    }

    public static boolean makeMove(int move, int[] board, int player) {
        if (!Game.isOccupied(move, board)) {
            board[move] = player;
            return true;
        }

        return false;
    }

    public void nextPlayer() {
        if (currentPlayer == HUMAN_PLAYER)
            currentPlayer = BOT_PLAYER;
        else
            currentPlayer = HUMAN_PLAYER;
    }

    public void play() {
        while (!victory) {
            printBoard();
            playRound();
            victory = (checkVictory(HUMAN_PLAYER) || checkVictory(BOT_PLAYER));
        }

        System.out.println("Player " + winner + " Won!");
    }

    private void playRound() {
        if (currentPlayer == HUMAN_PLAYER) {
            humanRound();
        }

        // bot
        else {
            botRound();
        }
    }

    private void botRound() {
        int move;
        move = bot.minmax(true, board, 9).bestMove();
        makeMove(move);
    }

    private void humanRound() {
        int move;
        // reads the input from the player
        move = getInput();
        // if occupied try again
        while (isOccupied(move))
            move = getInput();
        // make the move
        makeMove(move);
    }

    public boolean isOccupied(int i) {
        return this.board[i] != 0;
    }

    public static boolean isOccupied(int i, int[] board) {
        return board[i] != 0;
    }

    public boolean checkVictory(int player) {
        boolean victory;

        if (
            // all horizontal victory states
                (board[0] == player && board[1] == player && board[2] == player) ||
                        (board[3] == player && board[4] == player && board[5] == player) ||
                        (board[6] == player && board[7] == player && board[8] == player) ||

                        // all vertical victory states
                        (board[0] == player && board[3] == player && board[6] == player) ||
                        (board[1] == player && board[4] == player && board[7] == player) ||
                        (board[2] == player && board[5] == player && board[8] == player) ||

                        // all diagonal
                        (board[0] == player && board[4] == player && board[8] == player) ||
                        (board[2] == player && board[5] == player && board[6] == player)
        ) {
            this.winner = player;
            return true;
        }
        return false;
    }

    public static boolean checkVictory(int player, int[] board) {
        // all horizontal victory states
        if (
                (board[0] == player && board[1] == player && board[2] == player) ||
                        (board[3] == player && board[4] == player && board[5] == player) ||
                        (board[6] == player && board[7] == player && board[8] == player) ||

                        // all vertical victory states
                        (board[0] == player && board[3] == player && board[6] == player) ||
                        (board[1] == player && board[4] == player && board[7] == player) ||
                        (board[2] == player && board[5] == player && board[8] == player) ||

                        // all diagonal
                        (board[0] == player && board[4] == player && board[8] == player) ||
                        (board[2] == player && board[5] == player && board[6] == player)
        ) {
            return true;
        } else return false;
    }


    public void printBoard() {
        System.out.printf("\n\n%d│%d│%d\n─╀─╀─\n%d│%d│%d\n─╀─╀─\n%d│%d│%d%n", board[0], board[1], board[2], board[3], board[4], board[5], board[6], board[7], board[8]);
    }

    public int getInput() {
        System.out.println("Make a move: ");
        Scanner s = new Scanner(System.in);
        return s.nextInt();
    }
}
