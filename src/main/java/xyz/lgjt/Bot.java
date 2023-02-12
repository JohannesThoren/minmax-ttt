package xyz.lgjt;


import java.util.ArrayList;

import static xyz.lgjt.Consts.*;

// this is a bot that will use minmax to win in tic-tac-toe
public class Bot {

    public Bot() {
    }



    public Stats minmax(boolean max, int[] board, int depth) {
        // create a clone of the board that can be manipulated, so we can try to moves
        int[] bc = board.clone();

        // a list containing all possible moves
        ArrayList<Integer> possibleMoves = getMossibleMoves(bc);

        // do some prechecks, such as checking victory states and so on.
        Stats pc = preChecks(depth, bc, possibleMoves);
        if (pc != null) return pc;

        // run the algorithm.
        Stats move = minOrMax(max, depth, bc, possibleMoves);

        // return the move and score
        return move;

    }

    // this function only checks if we should run min or max depending on the max boolean.
    private Stats minOrMax(boolean max, int depth, int[] bc, ArrayList<Integer> possibleMoves) {

        if (max) {
            return max(depth, bc, possibleMoves);
        } else {
            return min(depth, bc, possibleMoves);
        }
    }

    private static Stats preChecks(int depth, int[] bc, ArrayList<Integer> possibleMoves) {
        if (possibleMoves.isEmpty())
            return new Stats(0, 0);
        if (Game.checkVictory(BOT_PLAYER, bc))
            return new Stats(0, 1);
        else if (Game.checkVictory(HUMAN_PLAYER, bc))
            return new Stats(0, -1);
        else if (depth == 0)
            return new Stats(0, 0);
        return null;
    }

    private Stats max(int depth, int[] bc, ArrayList<Integer> possibleMoves) {

        int bestScore = -SCORE;

        // iterate over all possible moves
        for (int move : possibleMoves) {

            // check if it is possible to make a move
            if (Game.makeMove(move, bc, BOT_PLAYER))
                // run min-max again so we treverse the tree and check if the score is better than best score. aka > -100
                if (minmax(false, bc, depth - 1).moveScore() > bestScore)
                    // return the stats of the move
                    return new Stats(move, SCORE);
        }
        return null;
    }

    private Stats min(int depth, int[] bc, ArrayList<Integer> possibleMoves) {
        // does the same thing as the max function except we are now checking if a the human player can make a specific move.
        // we also check if the score of the move is less than 100

        int bestScore = SCORE;
        for (int move : possibleMoves) {
            if (Game.makeMove(move, bc, HUMAN_PLAYER))
                if (minmax(false, bc, depth - 1).moveScore() < bestScore)
                    return new Stats(move, -SCORE);
        }
        return null;
    }


    // generates a list of possible moves.
    private ArrayList<Integer> getMossibleMoves(int[] board) {
        ArrayList<Integer> moves = new ArrayList<>();

        for (int i = 0; i < board.length; i++) {
            if (!Game.isOccupied(i, board)) {
                moves.add(i);
            }
        }
        return moves;
    }
}
