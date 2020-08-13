// Kaushik Nadimpalli
// Assignment 3 Q1 - Tetris Game
// This is the main file where most of our requirements are written for this project
// The goal is to design a tetris game with a user interface that the user can access by clicking on "Settings"

import java.awt.*; import java.awt.event.*;
import java.util.Hashtable; import java.util.Random; import java.util.Timer; import java.util.TimerTask;
import javax.swing.*; import javax.swing.event.ChangeEvent; import javax.swing.event.ChangeListener;

// User Interface class - We set the intial static values for all our user settings in this class to later expand
// functionality later depending on how the game was played. Attributes in class - columns, rows, block size, and scoring factors - M, N, and S
class userInterface {
    static int column_len = 20, row_len = 30, squareLen = 10; static float S = 0.1f;
    static int mFactor = 1, nFactor = 20, Lines = 0, userlvl = 1, userPoints = 0;
}

// The tetris schema is where we are defining the different game functionalities in our program
public class tetrisSchema extends Canvas {
    static class GameTimerTask extends TimerTask {
        tetrisSchema newTetris;
        GameTimerTask(tetrisSchema newTetris) {super(); this.newTetris = newTetris;}
        @Override public void run() { newTetris.descend();}}

    int midpointWidth, midpointLength;
    float wid_board = 600.0F, len_board = 600.0F, clickX, clickY, moveX, moveY, px;
    static boolean tetrisBoard = false; static boolean istetrisBoard = false;
    static boolean isInSquare = false;   static boolean isInSquarenow = false;
    int xPos(float x) { return Math.round(midpointWidth + x / px);}
    int yPos(float y) { return Math.round(midpointLength - y / px);}
    int brickLength(float l) { return Math.round(l / px);}
    int textFont(float f) { return Math.round(f / px);}

     // We need to check whether tetris board is active or not and set a timer according to the game
    public void setistetrisBoard(boolean istetrisBoard) {
        if (this.istetrisBoard != istetrisBoard) {
            if (istetrisBoard) {
                if (t != null)
                    t.cancel();
                    t = null;}
            else {
                t = new Timer();
                float interval = (float) 1000 / (2f + userInterface.S * (float) userInterface.userlvl);
                t.scheduleAtFixedRate(new GameTimerTask(this), 0, (int) interval);}
            this.istetrisBoard = istetrisBoard;}}

   // Randomized Selection Prameters
    int min = 1, max = 7; Random r = new Random();
    int curTetro = r.nextInt(max) % (1+(max-min)) + min;
    int nextTetro = r.nextInt(max) % (1+(max-min)) + min;

    // Tetromino X and Y Positions - Beginning of game
    int tetrominoxposition = 5, tetrominoyposition = 4, rotation = 0;
    Tetromino startingTetromino; Timer t; boolean isendOfBoard = false;
    int descentPosition[][] = new int[userInterface.column_len][1+userInterface.row_len];
    int playboard[][] = new int[userInterface.column_len][userInterface.row_len];

    // In our game, we are only supposed to use the mouse to control our play
    // As such there are some helpful java functions like MouseListener and WheelListener
    // to keep track of mouse movements and add to functionality as we desire.
    // Below we fulfill the move left/move right and rotate features with mouse
    tetrisSchema(Frame fr) {  this.fr = fr;
      addMouseListener(new MouseAdapter() {
          public void mousePressed(MouseEvent hoverInBoard) {
              int mouseButtonPressed = hoverInBoard.getButton();
              clickX = hoverInBoard.getX();
              clickY = hoverInBoard.getY();
              if (((clickX > xPos(20)) && (clickY < yPos(-100)) && clickX < xPos(80)) && (clickY > yPos(-80)))
                  {System.exit(0);}
              else if((clickX < xPos(80)) && (clickX > xPos(20)) && (clickY < yPos(-70)) && (clickY > yPos(-50)))
                  {setistetrisBoard(true);
                  userInterface();}
              else if (((moveX < xPos(0)) && moveX > xPos(-userInterface.column_len * userInterface.squareLen)) && (moveY > yPos(userInterface.row_len * userInterface.squareLen / 2))
                                    && (moveY < yPos(-userInterface.row_len * userInterface.squareLen / 2)))
                  {return;}
               else {
                  switch (mouseButtonPressed) {
                      case MouseEvent.BUTTON1:
                          if (isendOfBoard)
                              return;
                          for (int x = 0; x < 4; x++)
                              if (startingTetromino.tetrominoStructure[x][0] == 0)
                                  return;
                          tetrominoxposition = tetrominoxposition - 1;
                          startingTetromino.setLocation(tetrominoxposition, tetrominoyposition);
                          startingTetromino.drawTetros();
                          boolean checkFill = true;
                          if (checkFill)
                            repaint();
                          break;

                      case MouseEvent.BUTTON3:
                          if (isendOfBoard)
                              return;
                          for (int x = 0; x < 4; x++)
                              if (startingTetromino.tetrominoStructure[x][0] == userInterface.column_len - 1)
                                  return;
                          tetrominoxposition += 1;
                          startingTetromino.setLocation(tetrominoxposition, tetrominoyposition);
                          startingTetromino.drawTetros();
                          boolean recheckFill = true;
                          if (recheckFill)
                            repaint();
                          break; }}}});
        addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent click) {
                super.mouseWheelMoved(click);
                boolean checkFill = true;
                if(isendOfBoard)
                    return;
                if(tetrisBoard)
                    return;
                for (int i = 0; i < 4; i++) {
                    if (startingTetromino.tetrominoStructure[i][0] == userInterface.column_len - 1 || startingTetromino.tetrominoStructure[i][0] == 0 )
                        {checkFill = false;
                        return;}}
                if(click.getWheelRotation() < 0)
                    rotation += 1;
                    if (rotation < 0)
                      rotation += 4;
                    rotation = rotation % 4;
                    startingTetromino.setLocation(tetrominoxposition, tetrominoyposition);
                    startingTetromino.drawTetros();
                    if (checkFill)
                        repaint();
                else {
                    rotation -= 1;
                    if (rotation < 0)
                      rotation += 5;
                    rotation = rotation % 4;
                    startingTetromino.setLocation(tetrominoxposition, tetrominoyposition);
                    startingTetromino.drawTetros();
                    if (checkFill)
                        repaint();
                      }}});
        addMouseMotionListener(new MouseAdapter() {
            public void mouseMoved(MouseEvent mouseClick) {
                if (isendOfBoard) { return;}
                moveX = mouseClick.getX();
                moveY = mouseClick.getY();
                if ((moveX < xPos(0)) && (moveX > xPos(-userInterface.column_len * userInterface.squareLen)) &&
                    (moveY < yPos(-userInterface.row_len * userInterface.squareLen / 2)) &&
                    (moveY > yPos(userInterface.row_len * userInterface.squareLen / 2))){
                    tetrisBoard = true;
                    if (istetrisBoard != tetrisBoard)
                        repaint();
                        setistetrisBoard(tetrisBoard);
                } else
                    tetrisBoard = false;
                    if (istetrisBoard != tetrisBoard)
                        repaint();
                        setistetrisBoard(tetrisBoard);

                // Mouse clicks move tetrominos to left and right depending on board and current location considerations
                // We first need to store all the 4x2 current coordiantes into an integer and multiply that integer
                // the tetroX and tetroY positions below define the new coordinates.
                int playAreax1 = startingTetromino.tetrominoStructure[0][0];
                int playAreay1 = startingTetromino.tetrominoStructure[0][1];
                int playAreax2 = startingTetromino.tetrominoStructure[1][0];
                int playAreay2 = startingTetromino.tetrominoStructure[1][1];
                int playAreax3 = startingTetromino.tetrominoStructure[2][0];
                int playAreay3 = startingTetromino.tetrominoStructure[2][1];
                int playAreax4 = startingTetromino.tetrominoStructure[3][0];
                int playAreay4 = startingTetromino.tetrominoStructure[3][1];
                int tetroX1 =  userInterface.column_len * userInterface.squareLen + userInterface.squareLen * playAreax1;
                int tetroY1 =  userInterface.row_len *  userInterface.squareLen / 2 -  userInterface.squareLen * playAreay1;
                int tetroX2 =  userInterface.column_len * userInterface.squareLen + userInterface.squareLen * playAreax2;
                int tetroY2 =  userInterface.row_len *  userInterface.squareLen / 2 - userInterface.squareLen * playAreay2;
                int tetroX3 =  userInterface.column_len * userInterface.squareLen + userInterface.squareLen * playAreax3;
                int tetroY3 =  userInterface.row_len * userInterface.squareLen / 2 - userInterface.squareLen * playAreay3;
                int tetroX4 =  userInterface.column_len * userInterface.squareLen + userInterface.squareLen * playAreax4;
                int tetroY4 =  userInterface.row_len * userInterface.squareLen / 2 - userInterface.squareLen * playAreay4;

                // Not working properly -- bug needs to be fixed ;(
                // Ran out of time to fix point in poly - purpose is to choose shape another than current/next shape during play
                if (((moveX > xPos(tetroX1)) &&
                   (moveX < xPos(tetroX1 + userInterface.squareLen)) &&
                   (moveY < yPos(tetroY1 - userInterface.squareLen)) &&
                   (moveY > yPos(tetroY1))) || ((moveX > xPos(tetroX2)) && (moveX < xPos(tetroX2 + userInterface.squareLen)) &&
                   (moveY < yPos(tetroY2 - userInterface.squareLen)) && (moveY > yPos(tetroY2))) || ((moveX > xPos(tetroX3)) &&
                   (moveX < yPos(tetroX3 + userInterface.squareLen)) && (moveY < yPos(tetroY3 - userInterface.squareLen)) &&
                   (moveY > yPos(tetroY3))) || ((moveX > xPos(tetroX4)) && (moveX < xPos(tetroX4 + userInterface.squareLen)) &&
                   (moveY < yPos(tetroY4 - userInterface.squareLen)) && (moveY > yPos(tetroY4)))) {
                    isInSquare = true;
                    if (isInSquare != isInSquarenow)
                      // Below is how user points will be calculated ---- Score - Level X M with point in polygon
                        userInterface.userPoints = userInterface.userPoints - userInterface.userlvl * userInterface.mFactor;
                        int pointInpolyCheck;
                        do
                          pointInpolyCheck = min + r.nextInt(max) % (max - min + 1);
                        while (pointInpolyCheck == curTetro || pointInpolyCheck == nextTetro);
                        curTetro = pointInpolyCheck;
                        startingTetromino.drawTetros();
                        repaint();
                        isInSquarenow = true;
                } else
                    isInSquarenow = false;}});

        float interval = (float) 1000 / (userInterface.S * (float) userInterface.userlvl + 2f);
        t = new Timer();
        t.scheduleAtFixedRate(new GameTimerTask(this), 0, (int) interval);
    }

   // One of the most important methods in our Game
   // Ensures that tetromino blocks are falling like they are supposed to and keeps track of position
   // In Graphics, there is no exact fall .... instead it is a motion where you draw....
   // ...the next set of coordinates which are incremented in  constant manner.
    public void descend() {
        if (startingTetromino == null) { return;}
        tetrominoyposition = tetrominoyposition + 1;
        startingTetromino.setLocation(tetrominoxposition, tetrominoyposition);
        startingTetromino.drawTetros();
        startingTetromino.setPath(rotation);
        if (isendOfBoard) { return; }
        boolean scoreChanges = true;
        for (int i = 0; i < 4; i++) {
            if (descentPosition[startingTetromino.tetrominoStructure[i][0]][startingTetromino.tetrominoStructure[i][1]] != 0) {
                isendOfBoard = true; tetrominoyposition = tetrominoyposition - 1;
                startingTetromino.setLocation(tetrominoxposition, tetrominoyposition);
                startingTetromino.drawTetros();
                startingTetromino.setPath(rotation);
                for (int j = 0; j < 4; j++) {
                    descentPosition[startingTetromino.tetrominoStructure[j][0]][startingTetromino.tetrominoStructure[j][1]] = startingTetromino.orientation;
                }
                for (int k = 0; k < (int) userInterface.column_len; k++) {
                    for (int l = 0; l < (int) userInterface.row_len; l++) {
                        playboard[k][l] = 0;
                }}
                for (int m = userInterface.row_len - 1; m > 0; m--) {
                    for (int n = 0; n < userInterface.column_len; n++) {
                        if (descentPosition[n][m] == 0) {
                            scoreChanges = false; break; }}
                    if (scoreChanges) {
                        userInterface.Lines++;
                        if (userInterface.Lines == userInterface.nFactor) {
                            userInterface.Lines = userInterface.Lines - userInterface.nFactor;
                            userInterface.userlvl++;
                        }
                        // User Score is calculated as specified.......Score = Score+Level*M
                        userInterface.userPoints = userInterface.userPoints + userInterface.userlvl * userInterface.mFactor;
                        for (int k = i; k > 0; k--)
                            for (int z = 0; z < userInterface.column_len; z++)
                                if (k == 0)
                                    descentPosition[z][k] = 0;
                                else
                                    descentPosition[z][k] = descentPosition[z][k - 1];

                        i++; System.out.println(i);
                    } scoreChanges = true;
                }

                for (int x = 0;  x < (int) userInterface.column_len; x++) {
                    if (descentPosition[x][1] != 0) {
                        System.out.println("You lost!");
                        System.out.println("Game will exit! Please re-run the program to play again!");
                        System.exit(0);
                    }}
                tetrominoxposition = 2;
                tetrominoyposition = 1;
                rotation = 0;
                curTetro = nextTetro;
                nextTetro = r.nextInt(max) % (max - min + 1) + min;
                startingTetromino.setLocation(tetrominoxposition, tetrominoyposition);
                startingTetromino.drawTetros();
                startingTetromino.setPath(rotation);
                isendOfBoard = false;
                break;
            }} boolean checkColor = true;
        if (checkColor = true) {repaint();}}

    void initialize() {
        Dimension dimension = getSize();
        int xDim = dimension.width - 1, yDim = dimension.height - 1;
        px = Math.max(wid_board / xDim, len_board / yDim);
        midpointWidth = xDim / 2; midpointLength = yDim / 2;
    }

    // We draw our shapes in the below function
    // Some shapes are only invoked upon user action like the Pause and Quit.
    // Other rudimentary shapes include the main playing area, the next shape area.
    public void paint(Graphics shape) { initialize();
        for (int i = 0; i < userInterface.column_len; i++) {
            descentPosition[i][userInterface.row_len] = 4 + 3;
            descentPosition[i][userInterface.row_len] = 4 + 3;
        }

        // Below rectangles are the same ones drawn from our previous assignments
        // DrawLine and DrawRect - To draw our rectangles used in the game - same as Assignment 1

        if (tetrisBoard) { shape.setColor(Color.red);
           shape.drawString("Pause", yPos(80), xPos(-60));}

        Font text = new Font("Helvetica", Font.PLAIN, textFont(10));
        shape.setFont(text);
        shape.drawString("Settings", xPos(25),yPos(-68));
        shape.drawString("Quit Game", xPos(25), yPos(-98));
        shape.drawString("LEVEL: " + userInterface.userlvl, xPos(20), yPos(20));
        shape.drawString("LINES: " + userInterface.Lines, xPos(20), yPos(0));
        shape.drawString("SCORE: " + userInterface.userPoints, xPos(20), yPos(-20));

        int mainBoardTopLeftX = xPos(-userInterface.column_len * userInterface.squareLen), mainBoardTopLeftY = yPos(userInterface.row_len * userInterface.squareLen / 2);
        int mainBoardBottomLeftX = xPos(-userInterface.column_len * userInterface.squareLen), mainBoardBottomLeftY = yPos((-1 * userInterface.row_len * userInterface.squareLen) / 2);
        int mainBoardTopRightX = xPos(0), mainBoardTopRightY = yPos(userInterface.row_len * userInterface.squareLen / 2);
        int mainBoardBottomRightX = xPos(0), mainBoardBottomRightY = yPos(-userInterface.row_len * userInterface.squareLen / 2);
        shape.drawLine(mainBoardTopLeftX, mainBoardTopLeftY, mainBoardBottomLeftX, mainBoardBottomLeftY);
        shape.drawLine(mainBoardTopRightX, mainBoardTopRightY, mainBoardBottomRightX, mainBoardBottomRightY);
        shape.drawLine(mainBoardTopLeftX, mainBoardTopLeftY, mainBoardTopRightX, mainBoardTopRightY);
        shape.drawLine(mainBoardBottomLeftX, mainBoardBottomLeftY, mainBoardBottomRightX, mainBoardBottomRightY);

        int nextBoardTopLeftX = xPos(20), nextBoardTopLeftY = yPos(100);
        int nextBoardBottomLeftX = xPos(20), nextBoardBottomLeftY = yPos(60);
        int nextBoardTopRightX = xPos(100), nextBoardTopRightY = yPos(100);
        int nextBoardBottomRightX = xPos(100), nextBoardBottomRightY = yPos(60);
        shape.drawLine(nextBoardTopLeftX, nextBoardTopLeftY, nextBoardBottomLeftX, nextBoardBottomLeftY);
        shape.drawLine(nextBoardTopRightX, nextBoardTopRightY, nextBoardBottomRightX, nextBoardBottomRightY);
        shape.drawLine(nextBoardTopLeftX, nextBoardTopLeftY, nextBoardTopRightX, nextBoardTopRightY);
        shape.drawLine(nextBoardBottomLeftX, nextBoardBottomLeftY, nextBoardBottomRightX, nextBoardBottomRightY);

        int quitBoardTopLeftX = xPos(20), quitBoardTopLeftY = yPos(-80);
        int quitBoardBottomLeftX = xPos(20), quitBoardBottomLeftY = yPos(-100);
        int quitBoardTopRightX = xPos(80), quitBoardTopRightY = yPos(-80);
        int quitBoardBottomRightX = xPos(80), quitBoardBottomRightY = yPos(-100);
        shape.drawLine(quitBoardTopLeftX, quitBoardTopLeftY, quitBoardBottomLeftX, quitBoardBottomLeftY);
        shape.drawLine(quitBoardTopRightX, quitBoardTopRightY, quitBoardBottomRightX, quitBoardBottomRightY);
        shape.drawLine(quitBoardTopLeftX, quitBoardTopLeftY, quitBoardTopRightX, quitBoardTopRightY);
        shape.drawLine(quitBoardBottomLeftX, quitBoardBottomLeftY, quitBoardBottomRightX, quitBoardBottomRightY);

        int  startRectltX = xPos(20),  startRectltY = yPos(-50);
        int  startRectlbX = xPos(20),  startRectlbY = yPos(-70);
        int  startRectrtX = xPos(80),  startRectrtY = yPos(-50);
        int  startRectrbX = xPos(80),  startRectrbY = yPos(-70);
        shape.drawLine(startRectltX, startRectltY, startRectlbX, startRectlbY);
        shape.drawLine(startRectrtX, startRectrtY, startRectrbX, startRectrbY);
        shape.drawLine(startRectltX, startRectltY, startRectrtX, startRectrtY);
        shape.drawLine(startRectlbX, startRectlbY, startRectrbX, startRectrbY);

        // Selecting next shape at random
        int playboard[][] = new int[(int) userInterface.column_len][(int) userInterface.row_len];
        this.startingTetromino = new Tetromino(curTetro);
        startingTetromino.setLocation(tetrominoxposition, tetrominoyposition);
        int type = startingTetromino.getOrientation();
        startingTetromino.setPath(rotation); startingTetromino.drawTetros();
        playboard[startingTetromino.tetrominoStructure[0][0]][startingTetromino.tetrominoStructure[0][1]] = type;
        playboard[startingTetromino.tetrominoStructure[1][0]][startingTetromino.tetrominoStructure[1][1]] = type;
        playboard[startingTetromino.tetrominoStructure[2][0]][startingTetromino.tetrominoStructure[2][1]] = type;
        playboard[startingTetromino.tetrominoStructure[3][0]][startingTetromino.tetrominoStructure[3][1]] = type;

      // Tetrominos during constant fall
        for (int dropX = 0; dropX < (int) userInterface.column_len; dropX++)
            for (int dropY = 0; dropY < (int) userInterface.row_len; dropY++) {
                if (descentPosition[dropX][dropY] != 0) {
                    int tetroX = -(int) userInterface.column_len * (int) userInterface.squareLen + (int) userInterface.squareLen * dropX;
                    int tetroY = (int) userInterface.row_len * (int) userInterface.squareLen / 2 - (int) userInterface.squareLen * dropY;
                    switch (descentPosition[dropX][dropY]) {
                        case 1: shape.setColor(Color.yellow); break;
                        case 2: shape.setColor(new Color(0, 0, 204)); break;
                        case 3: shape.setColor(new Color(102, 0, 153)); break;
                        case 4: shape.setColor(Color.red); break;
                        case 5: shape.setColor(new Color(51, 153, 255)); break;
                        case 6: shape.setColor(new Color(255, 204, 51)); break;
                        case 7: shape.setColor(new Color(0, 153, 0)); break; }
                    shape.fillRect(xPos(tetroX), yPos(tetroY), brickLength(userInterface.squareLen), brickLength(userInterface.squareLen));
                    shape.setColor(Color.black);
                    shape.drawRect(xPos(tetroX), yPos(tetroY), brickLength(userInterface.squareLen), brickLength(userInterface.squareLen));
                }}

        // Tetrominos in Main Play Area
        for (int playAreax = 0; playAreax < (int) userInterface.column_len; playAreax++)
          for (int playAreay = 0; playAreay < (int) userInterface.row_len; playAreay++) {
              if (playboard[playAreax][playAreay] != 0) {
                  int tetroX = -(int) userInterface.column_len * (int) userInterface.squareLen + (int) userInterface.squareLen * playAreax;
                  int tetroY = (int) userInterface.squareLen / 2 * (int) userInterface.row_len - (int) userInterface.squareLen * playAreay;
                  switch (playboard[playAreax][playAreay]) {
                    case 1: shape.setColor(Color.yellow); break;
                    case 2: shape.setColor(new Color(0, 0, 204)); break;
                    case 3: shape.setColor(new Color(102, 0, 153)); break;
                    case 4: shape.setColor(Color.red); break;
                    case 5: shape.setColor(new Color(51, 153, 255)); break;
                    case 6: shape.setColor(new Color(255, 204, 51)); break;
                    case 7: shape.setColor(new Color(0, 153, 0)); break; }
                    shape.fillRect(xPos(tetroX), yPos(tetroY), brickLength(userInterface.squareLen), brickLength(userInterface.squareLen));
                    shape.setColor(Color.black);
                    shape.drawRect(xPos(tetroX), yPos(tetroY), brickLength(userInterface.squareLen), brickLength(userInterface.squareLen));
                }}

       // We need to define another array list to store next tetromono
       // Below, we are defining the next shape to fall which will be the new shape in our main Board
       // We have 7 shapes as always so we need to design all the positional rotations of these shapes.
        int nextShape[][] = new int[8][4];
        Tetromino tetromino = new Tetromino(nextTetro);
        tetromino.setLocation(2, 1);
        int newshape = tetromino.getOrientation();
        int posX = tetromino.getX(), posY = tetromino.getY();
        switch (newshape) {
            case 1: nextShape[posX][posY] = newshape; nextShape[posX - 1][posY + 1] = newshape;
                nextShape[posX + 1][posY] = newshape; nextShape[posX][posY + 1] = newshape;
                break;
            case 2:
                nextShape[posX][posY] = newshape; nextShape[posX + 1][posY] = newshape;
                nextShape[posX + 1][posY + 1] = newshape; nextShape[posX + 2][posY + 1] = newshape;
                break;
            case 3:
                nextShape[posX][posY] = newshape; nextShape[posX][posY + 1] = newshape;
                nextShape[posX + 2][posY + 1] = newshape; nextShape[posX + 1][posY + 1] = newshape;
                break;
            case 4:
                nextShape[posX][posY + 1] = newshape; nextShape[posX + 1][posY + 1] = newshape;
                nextShape[posX + 2][posY] = newshape; nextShape[posX + 2][posY + 1] = newshape;
                break;
            case 5:
                nextShape[posX][posY] = newshape; nextShape[posX + 1][posY] = newshape;
                nextShape[posX][posY + 1] = newshape; nextShape[posX + 1][posY + 1] = newshape;
                break;
            case 6:
                nextShape[posX][posY + 1] = newshape;
                nextShape[posX + 1][posY] = newshape;
                nextShape[posX + 1][posY + 1] = newshape;
                nextShape[posX + 2][posY + 1] = newshape;
                break;
            case 7:
                nextShape[posX][posY] = newshape;
                nextShape[posX + 1][posY] = newshape;
                nextShape[posX + 2][posY] = newshape;
                nextShape[posX + 3][posY] = newshape;
                break;}

        // Tetrominos Next Shape - Next Box
        for (int xPosNextShape = 0; xPosNextShape < 8; xPosNextShape++)
            for (int yPosNextShape = 0; yPosNextShape < 4; yPosNextShape++) {
                if (nextShape[xPosNextShape][yPosNextShape] != 0) {
                    int nextBlockX = 20 + 10 * xPosNextShape;
                    int nextBlockY = 100 - 10 * yPosNextShape;
                    switch (nextShape[xPosNextShape][yPosNextShape]) {
                      case 1: shape.setColor(Color.yellow); break;
                      case 2: shape.setColor(new Color(0, 0, 204)); break;
                      case 3: shape.setColor(new Color(102, 0, 153)); break;
                      case 4: shape.setColor(Color.red); break;
                      case 5: shape.setColor(new Color(51, 153, 255)); break;
                      case 6: shape.setColor(new Color(255, 204, 51)); break;
                      case 7: shape.setColor(new Color(0, 153, 0)); break;}
                    shape.fillRect(xPos(nextBlockX), yPos(nextBlockY), brickLength(10), brickLength(10));
                    shape.setColor(Color.black);
                    shape.drawRect(xPos(nextBlockX), yPos(nextBlockY), brickLength(10), brickLength(10));
                }}
      }

// An important part of the game is letting the user choose the Settings
// JDialog and Java Frame objects were utilized to create user interface Settings for the game
// User modifies the necessary settings, which will be saved, and alter the game schema
    public JDialog dialog;
    public Frame fr;
    public void userInterface() {
        if (SwingUtilities.isEventDispatchThread())
        {
            JPanel panel = new JPanel();
            panel.setOpaque(true);
            panel.setForeground(Color.blue);
            panel.setBackground(Color.ORANGE);
            dialog = new JDialog(fr, "User Interface - Tetris", true);
            dialog.add(panel);
            {
                JPanel uiPanel = new JPanel();
                uiPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); uiPanel.add(new JLabel("User Settings"));
                uiPanel.setBackground(Color.BLUE); uiPanel.setPreferredSize(new Dimension(100, 100));
                panel.add(uiPanel);}
            {
                JPanel newText = new JPanel(); GridBagConstraints grid = new GridBagConstraints();
                grid.fill = GridBagConstraints.HORIZONTAL;
                // Multipliers - S, M, N
                {
                    JPanel userBoard = new JPanel();
                    userBoard.add(new JLabel("Multipliers"));
                    // S Factor -> 0.1-10
                    // We adjust the scroll bar and convert 0.1-1.0 floating point and stronlgy typecast it to integer
                     {
                         JPanel uiPanel = new JPanel();
                         uiPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
                         uiPanel.add(new JLabel("S :"));
                        // bug in display of JSlider......not showing 0.1-1 ...need to fix
                         JSlider scroll = new JSlider(JSlider.VERTICAL, (int)(10*.1),(int)((float)1*(float)10), (int)(userInterface.S*10));
                         JLabel lb = new JLabel(String.format("%.1f", userInterface.S));
                         Hashtable multiplerHashmap = new Hashtable();
                         multiplerHashmap.put( new Integer( 0 ), new JLabel("0.0") );
                         multiplerHashmap.put( new Integer( 5 ), new JLabel("0.5") );
                         multiplerHashmap.put( new Integer( 10 ), new JLabel("1.0") );
                         scroll.setLabelTable(multiplerHashmap);
                         scroll.addChangeListener(new ChangeListener() {
                             @Override public void stateChanged(ChangeEvent e) {
                                 userInterface.S = scroll.getValue() / 3 + 2;
                                 lb.setText(String.format("%.3f", userInterface.S));}});
                         uiPanel.add(scroll);
                         uiPanel.add(lb);
                         userBoard.add(uiPanel);
                     }
                    // M factor -> 1-15
                    {
                        JPanel uiPanel = new JPanel();
                        uiPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
                        uiPanel.add(new JLabel("M :"));

                        JSlider scroll = new JSlider(JSlider.VERTICAL, 1, 15, userInterface.mFactor);
                        JLabel lb = new JLabel(String.format("%01d", userInterface.mFactor));
                        scroll.addChangeListener(new ChangeListener() {
                            @Override public void stateChanged(ChangeEvent e) {
                                userInterface.mFactor = scroll.getValue();
                                lb.setText(String.format("%01d", userInterface.mFactor));}});
                        uiPanel.add(scroll);
                        uiPanel.add(lb);
                        userBoard.add(uiPanel);
                    }
                    // N Factor -> 20-40
                    {
                        JPanel uiPanel = new JPanel();
                        uiPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
                        uiPanel.add(new JLabel("N :"));

                        JSlider scroll = new JSlider(JSlider.VERTICAL, 20, 40, userInterface.nFactor);
                        JLabel lb = new JLabel(String.format("%02d", userInterface.nFactor));
                        scroll.addChangeListener(new ChangeListener() {
                            @Override public void stateChanged(ChangeEvent e) {
                                userInterface.nFactor = scroll.getValue();
                                lb.setText(String.format("%01d", userInterface.nFactor));
                            }});
                        uiPanel.add(scroll);
                        uiPanel.add(lb);
                        userBoard.add(uiPanel);
                    } grid.gridx = 0; newText.add(userBoard, grid);
                }

                // UI Buttons - Modify & Exit
                // After user creates the changes and adjusts same settings. We created two buttons to return to the game.
                // The Modify button saves user settings and starts the game again with the new settings
                // Exit button exits the user interface in case user does not want to make any setting changes.
               {
                   JPanel newPanel = new JPanel();

                   Button Modify = new Button("Modify");
                   Modify.addActionListener(new ActionListener() {
                       public void actionPerformed(ActionEvent e) { dialog.setVisible(false);}});
                   newPanel.add(Modify);

                   Button Exit = new Button("Exit");
                   Exit.addActionListener(new ActionListener() {
                       public void actionPerformed(ActionEvent e) {dialog.setVisible(false);}});
                   newPanel.add(Exit); panel.add(newPanel);}

                // User Interface - Board
                // Below we utilize JPanel dialog feature and scrollbar sliders to create the
                // square block size, number of rows, and number of columns that the user can adjust.
                // Parameters and limitations on what they can be set to were done according to assignemnt instructions
                {
                    JPanel panelBoardSettings = new JPanel();
                    panelBoardSettings.add(new JLabel("Board Settings:"));
                    // Block Size Interface -> 0-10 (for visually impared)
                    {
                        JPanel uiPanel = new JPanel();
                        uiPanel.add(new JLabel("Square Size:"));

                        JSlider scroll = new JSlider(JSlider.VERTICAL, 0, 10, userInterface.squareLen);
                        JLabel lb = new JLabel(String.format("%03d", userInterface.squareLen));
                        scroll.addChangeListener(new ChangeListener() {
                            @Override
                            public void stateChanged(ChangeEvent e) {
                                userInterface.squareLen = scroll.getValue();
                                lb.setText(String.format("%02d", userInterface.squareLen));
                                repaintDialog();}});
                        uiPanel.add(scroll); uiPanel.add(lb); grid.gridx = 1; panelBoardSettings.add(uiPanel);
                    }   newText.add(panelBoardSettings, grid);
                    // Column Interface -> 10-20
                    {
                        JPanel uiPanel = new JPanel();
                        uiPanel.add(new JLabel("# Columns:"));

                        JSlider scroll = new JSlider(JSlider.VERTICAL, 10, 20, userInterface.column_len);
                        JLabel lb = new JLabel(String.format("%03d", userInterface.column_len));
                        scroll.addChangeListener(new ChangeListener() {
                            @Override
                            public void stateChanged(ChangeEvent e) {
                                userInterface.column_len = scroll.getValue();
                                lb.setText(String.format("%02d", userInterface.column_len));}});
                        uiPanel.add(scroll); uiPanel.add(lb); panelBoardSettings.add(uiPanel);
                    }
                    // Rows Interface -> 20-30
                    {
                        JPanel uiPanel = new JPanel();
                        uiPanel.add(new JLabel("# Rows"));

                        JSlider scroll = new JSlider(JSlider.VERTICAL, 20, 30, userInterface.row_len);
                        JLabel lb = new JLabel(String.format("%03d", userInterface.row_len));
                        scroll.addChangeListener(new ChangeListener() {
                            @Override
                            public void stateChanged(ChangeEvent e) {
                                userInterface.row_len = scroll.getValue();
                                lb.setText(String.format("%02d", userInterface.row_len));}});
                        uiPanel.add(scroll); uiPanel.add(lb); panelBoardSettings.add(uiPanel);
                    }}  panel.add(newText);}
                dialog.pack(); dialog.setVisible(true);
        }
        else { try { SwingUtilities.invokeAndWait(new Runnable() {
                    @Override public void run() {userInterface();}});
          } catch (Exception e) {e.printStackTrace();}}}

// The SwingUtilities utility invoke and wait causes doRun.run() to be executed synchronously on the AWT event dispatching thread.
// It will be used to override the run method and repaint the dialog box concurrently
private void repaintDialog(){
      if(SwingUtilities.isEventDispatchThread()){dialog.revalidate(); dialog.repaint(); dialog.pack();}
      else { try { SwingUtilities.invokeAndWait(new Runnable() {@Override public void run() { repaintDialog();}});}
             catch (Exception exp){exp.printStackTrace();}}}}
