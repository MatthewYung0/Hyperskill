package tictactoe;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Computer extends User {

    private final String difficulty;
    private final char symbol;
    private final int ownSum;
    private final int otherPlayerSum;

    public Computer(String difficulty, char symbol) {
        this.difficulty = difficulty;
        this.symbol = symbol;
        // Sets the symbol in which computer will play (i.e. X or O)
        if (this.symbol == 'X') {
            ownSum = 208;
            otherPlayerSum = 190;
        } else {
            ownSum = 190;
            otherPlayerSum = 208;
        }
    }

    public void run(List<Character> gameField) {
        switch (difficulty) {
            case "easy":
                computerEasy((ArrayList<Character>) gameField);
                break;
            case "medium":
                computerMedium((ArrayList<Character>) gameField);
        }
    }

    public void computerEasy(ArrayList<Character> gameField) {
        int position;
        boolean moveMade = false;
        while (!moveMade) {
            position = ThreadLocalRandom.current().nextInt(0, 8 + 1);
            if (gameField.get(position) == ' ') {
                gameField.set(position, symbol);
                moveMade = true;
            }
        }
    }

    public void computerMedium(ArrayList<Character> gameField) {
        int sumToLookFor;
        boolean makeRandomMove = true;
        for (int i = 0; i < 2; i++) {
            // In the first loop, computer will look for its own winning move.
            // In the second loop, if it doesn't find any, it will look to see if the other has a winning move
            if (i == 0) {
                sumToLookFor = ownSum;
            } else {
                sumToLookFor = otherPlayerSum;
            }
            if (checkRows(gameField, sumToLookFor) ||
                    checkColumns(gameField, sumToLookFor) ||
                    checkDiagonals(gameField, sumToLookFor)) {
                makeRandomMove = false;
                break;
            }
        }
        // If neither the computer or the opponent has a winning move, then it will randomly make a move
        if (makeRandomMove) {
            computerEasy(gameField);
        }
    }

    public boolean checkDiagonals(ArrayList<Character> gameField, int sumToLookFor) {
        if (gameField.get(0) + gameField.get(4) + gameField.get(8) == sumToLookFor) {
            for (int i = 0; i < 9; i += 4) {
                if (gameField.get(i) == 32) {
                    if (setSymbolInGameField(gameField, this.symbol, i)) {
                        return true;
                    }
                }
            }
        }
        if (gameField.get(2) + gameField.get(4) + gameField.get(6) == sumToLookFor) {
            for (int i = 2; i < 7; i += 2) {
                if (gameField.get(i) == 32) {
                    if (setSymbolInGameField(gameField, this.symbol, i)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean checkColumns(ArrayList<Character> gameField, int sumToLookFor) {
        for (int column = 0; column < 3; column ++) {
            if (gameField.get(column) + gameField.get(column+3) + gameField.get(column+6) == sumToLookFor) {
                for (int row = column; row < 9; row += 3) {
                    if (setSymbolInGameField(gameField, this.symbol, row)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean checkRows(ArrayList<Character> gameField, int sumToLookFor) {
        for (int i = 0; i < 9; i += 3) {
            if (gameField.get(i) + gameField.get(i+1) + gameField.get(i+2) == sumToLookFor) {
                for (int j = i; j < 9; j++) {
                    if (setSymbolInGameField(gameField, this.symbol, j)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean setSymbolInGameField(ArrayList<Character> gameField, char symbol, int index) {
        if (gameField.get(index) == 32) {
            gameField.set(index, symbol);
            return true;
        }
        return false;
    }

    public String getDifficulty() {
        return difficulty;
    }
}
