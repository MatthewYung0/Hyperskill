package minesweeper;

public enum STATES {

    HIDDEN('.'),
    UNHIDDEN('/'),
    MINE('X'),
    FLAGGED('*'),
    NEARBY_1('1'),
    NEARBY_2('2'),
    NEARBY_3('3'),
    NEARBY_4('4'),
    NEARBY_5('5'),
    NEARBY_6('6'),
    NEARBY_7('7'),
    NEARBY_8('8');

    final char state;

    STATES(char state) {
        this.state = state;
    }

    public char getState() {
        return state;
    }
  
}
