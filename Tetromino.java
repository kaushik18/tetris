// Kaushik Nadimpalli
// Assignment 3 Q1 - Tetris Game

// The purpose of this method is to create out tetromino positonal coordinates
// We have 7 4x2 shapes that we will be working with.
// And so, we need to get all rotational positions in different case scenarios.

public class Tetromino {
    int orientation, x, y, rotation;
    int[][] tetrominoStructure = new int[4][2];
    public Tetromino(int orientation) { this.orientation = orientation;}
    public int getOrientation() { return orientation;}
    public void setLocation(int locationX, int locationY) { x = locationX; y = locationY; }
    public void setPath(int rotation) { this.rotation = rotation; }
    public int getX() { return x; }
    public int getY() { return y; }
    public void settetros(int one, int two, int three, int four, int five, int six, int seven, int eight)
    { tetrominoStructure[0][0] = one; tetrominoStructure[0][1] = two; tetrominoStructure[1][0] = three; tetrominoStructure[1][1] = four;
      tetrominoStructure[2][0] = five; tetrominoStructure[2][1] = six; tetrominoStructure[3][0] = seven; tetrominoStructure[3][1] = eight;}
    public void drawTetros() { switch (orientation) {
            case 1: switch (rotation) {
                case 0:settetros(x,y,x,y - 1,x - 1,y,x + 1,y - 1); break;
                case 1:settetros(x,y,x,y - 1,x + 1,y,x + 1,y + 1); break;
                case 2:settetros(x,y-1,x,y - 2,x - 1,y-1,x + 1,y - 2); break;
                case 3:settetros(x-1,y,x-1,y - 1,x,y,x,y + 1); break;
            } break;
            case 2: switch (rotation) {
                case 0:settetros(x,y,x,y - 1,x - 1,y-1,x + 1,y); break;
                case 1:settetros(x,y,x,y + 1,x + 1,y,x + 1,y - 1); break;
                case 2:settetros(x,y+1,x,y,x - 1,y,x + 1,y + 1); break;
                case 3:settetros(x-1,y,x-1,y + 1,x,y,x,y - 1); break;
            } break;
            case 3: switch (rotation) {
                case 0:settetros(x,y,x+2,y + 1,x,y+1,x + 1,y + 1); break;
                case 1:settetros(x+1,y,x+2,y,x + 1,y+2,x + 1,y + 1); break;
                case 2:settetros(x+2,y+2,x+2,y + 1,x,y+1,x + 1,y + 1); break;
                case 3:settetros(x+1,y,x,y +2,x + 1,y+2,x + 1,y + 1); break;
            } break;
            case 4: switch (rotation) {
                case 0:settetros(x+2,y,x+2,y + 1,x,y+1,x + 1,y + 1); break;
                case 1:settetros(x+1,y,x+2,y + 2,x+1,y+2,x + 1,y + 1); break;
                case 2:settetros(x,y+2,x+2,y + 1,x,y+1,x + 1,y + 1); break;
                case 3:settetros(x+1,y,x,y,x+1,y+2,x + 1,y + 1); break;
            } break;
            case 5: settetros(x,y,x+1,y,x,y+1,x + 1,y + 1); break;
            case 6: switch (rotation) {
                case 0:settetros(x+1,y,x+2,y + 1,x,y+1,x + 1,y + 1); break;
                case 1:settetros(x+1,y,x+2,y + 1,x+1,y+2,x + 1,y + 1); break;
                case 2:settetros(x,y+1,x+2,y + 1,x+1,y+2,x + 1,y + 1); break;
                case 3:settetros(x,y+1,x+1,y,x+1,y+2,x + 1,y + 1); break;
            } break;
            case 7: switch (rotation) {
                case 0:settetros(x+3,y+1,x+2,y + 1,x,y+1,x + 1,y + 1); break;
                case 1:settetros(x+3,y,x+3,y + 1,x+3,y+2,x + 3,y + 3); break;
                case 2:settetros(x+3,y+2,x+2,y + 2,x,y+2,x + 1,y + 2); break;
                case 3:settetros(x+2,y,x+2,y + 1,x+2,y+2,x + 2,y + 3); break;
            } break;}}}
