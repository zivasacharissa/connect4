package com.thg.accelerator23.connectn.ai.Connect4CaptainsMistress;

public class MctsAnalyser {
    public final int height;
    public final int width;
    private int[][] board;
    private boolean nextTurn;
    // board contents
    public static final int EMPTY_SLOT = 0;
    public static final int PLAYER_1_DISK = 1;
    public static final int PLAYER_2_DISK = 2;

    // turns
    public static final boolean PLAYER_1_TURN = true;

    // game state
    public static final int ONGOING = 0;
    public static final int PLAYER_1_WON = 1;
    public static final int PLAYER_2_WON = 2;
    public static final int TIE = 3;

    public MctsAnalyser(int width, int height) {
        this.width = width;
        this.height = height;
        board = new int[height][width]; // default all 0
        nextTurn = PLAYER_1_TURN;
    }

    public MctsAnalyser() {
        this(10, 8);
    }

    public MctsAnalyser(int[][] contents, boolean nextTurn) {
        this(contents[0].length, contents.length);
        loadContents(contents);
        this.nextTurn = nextTurn;
    }

    public boolean canPlace(int column) {
        return column >= 0 && column < width && board[0][column] == 0;
    }

    public boolean place(int column) {
        int disk = (nextTurn == PLAYER_1_TURN) ? PLAYER_1_DISK : PLAYER_2_DISK;
        if(!canPlace(column))
            return false;
        int diskHeight = height - 1;
        while(board[diskHeight][column] != EMPTY_SLOT)
            diskHeight--;
        board[diskHeight][column] = disk;
        nextTurn = !nextTurn;
        return true;
    }

    public MctsAnalyser getNextState(int column) {
        MctsAnalyser next = this.copy();
        next.place(column);
        return next;
    }

    public void loadContents(int[][] contents) {
        for(int i = 0; i < height; i++)
            for(int j = 0; j < width; j++)
                board[i][j] = contents[i][j];
    }

    public MctsAnalyser copy() {
        return new MctsAnalyser(board, this.nextTurn);
    }

    public boolean didPlayerWin(int playerDisk) {
        // check horizontal
        int height = board.length;
        int width = board[0].length;
        for(int i = 0; i < height; i++)
            for(int j = 0; j < width - 3; j++)
                for(int k = j; k < j + 4 && board[i][k] == playerDisk; k++)
                    if(k == j+3)
                        return true;
        // check vertical
        for(int i = 0; i < height - 3; i++)
            for(int j = 0; j < width; j++)
                for(int k = i; k < i + 4 && board[k][j] == playerDisk; k++)
                    if(k == i+3)
                        return true;
        // check diagonal down right
        for(int i = 0; i < height - 3; i++)
            for(int j = 0; j < width - 3; j++)
                for(int k = 0; k < 4 && board[i+k][j+k] == playerDisk; k++)
                    if(k == 3)
                        return true;
        // check diagonal down
        for(int i = 0; i < height - 3; i++)
            for(int j = 3; j < width; j++)
                for(int k = 0; k < 4 && board[i+k][j-k] == playerDisk; k++)
                    if(k == 3)
                        return true;
        return false;
    }

    public boolean isFull() {
        for(int i = 0; i < board.length; i++)
            for(int j = 0; j < board[i].length; j++)
                if(board[i][j] == EMPTY_SLOT)
                    return false;
        return true;
    }

    public int currentGameState() {
        return this.didPlayerWin(PLAYER_1_DISK) ? PLAYER_1_WON
                : this.didPlayerWin(PLAYER_2_DISK) ? PLAYER_2_WON
                : this.isFull() ? TIE
                : ONGOING;
    }

    public boolean getNextTurn() {
        return nextTurn;
    }

}
