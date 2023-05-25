/*
 * File: BlankKarel.java
 * ---------------------
 * This class is a blank one that you can change at will.
 */

import stanford.karel.*;


public class BlankKarel extends SuperKarel {

    public int calculateWidth() {
        int width = 1;
        while (frontIsClear()) {
            move();
            width++;
        }
        turnLeft();
        return width;
    }

    public int calculateLength() {
        int length = 1;
        while (frontIsClear()) {
            move();
            length++;
        }
        return length;
    }

    public void fillOddAndReturn(int numberOfMoves) {
        moveWithBeeper(numberOfMoves, false);
        turnAround();
        moveWithoutBeeper(numberOfMoves);
    }

    public void fillEvenZigZagHorizontally(int numberOfMoves) {

        putBeeper();
        move();
        putBeeper();

        while (numberOfMoves > 0) {
            for (int i = 0; i < 2; i++) {
                turnRight();
                move();
                putBeeper();
            }

            numberOfMoves--;

            for (int i = 0; i < 2 && numberOfMoves > 0; i++) {
                turnLeft();
                move();
                putBeeper();
            }
            numberOfMoves--;
        }
    }

    public void fillEvenZigZagVertically(int numberOfMoves) {

        putBeeper();
        move();
        putBeeper();

        while (numberOfMoves > 0) {
            for (int i = 0; i < 2; i++) {
                turnLeft();
                move();
                putBeeper();
            }

            numberOfMoves--;

            for (int i = 0; i < 2 && numberOfMoves > 0; i++) {
                turnRight();
                move();
                putBeeper();
            }
            numberOfMoves--;
        }
    }

    public void fillEvenAndReturn(int numberOfMoves, boolean turnLeftOrRight) {
        moveWithBeeper(numberOfMoves, false);
        if (turnLeftOrRight) {
            turnLeft();
        } else {
            turnRight();
        }
        move();
        if (turnLeftOrRight) {
            turnLeft();
        } else {
            turnRight();
        }
        moveWithBeeper(numberOfMoves, true);
    }

    public void moveWithBeeper(int numberOfMoves, boolean preBeeper) {

        if (preBeeper) putBeeper();

        while (numberOfMoves-- > 0) {
            move();
            putBeeper();

        }
    }

    public void moveWithoutBeeper(int numberOfMoves) {
        while (numberOfMoves-- > 0) {
            move();
        }
    }



    public void evenWidthEvenLength(int movesToReachHalfWidth, int movesToReachHalfLength) {
        turnAround();
        moveWithoutBeeper(movesToReachHalfLength);
        fillEvenZigZagHorizontally(movesToReachHalfWidth);

        fillEvenAndReturn(movesToReachHalfLength, false);

        turnLeft();
        fillEvenAndReturn(movesToReachHalfWidth, false);

        turnLeft();
        fillEvenAndReturn(movesToReachHalfLength, false);

        pickBeeper();
    }

    public void evenWidthOddLength(int movesToReachHalfWidth, int movesToReachHalfLength) {
        turnAround();
        moveWithoutBeeper(movesToReachHalfLength);

        turnRight();
        moveWithBeeper(movesToReachHalfWidth, true);

        turnLeft();
        fillEvenAndReturn(movesToReachHalfLength, false);

        fillEvenAndReturn(movesToReachHalfLength, false);

        pickBeeper();

        turnRight();
        move();

        moveWithBeeper(movesToReachHalfWidth, false);

    }

    public void oddWidthEvenLength(int movesToReachHalfWidth, int movesToReachHalfLength) {

        turnAround();
        moveWithoutBeeper(movesToReachHalfLength);

        fillEvenZigZagHorizontally(movesToReachHalfWidth);

        fillOddAndReturn(movesToReachHalfLength);

        turnLeft();

        fillEvenAndReturn(movesToReachHalfWidth, false);

        pickBeeper();

        turnLeft();

        moveWithBeeper(movesToReachHalfLength, false);


    }

    public void oddWidthOddLength(int movesToReachHalfWidth, int movesToReachHalfLength) {

        turnAround();
        moveWithoutBeeper(movesToReachHalfLength);

        turnRight();

        moveWithBeeper(movesToReachHalfWidth, true);

        turnLeft();
        fillOddAndReturn(movesToReachHalfLength);

        fillOddAndReturn(movesToReachHalfLength);

        turnRight();

        moveWithBeeper(movesToReachHalfWidth, false);
    }

    public void evenLengthEvenWidth(int movesToReachHalfWidth, int movesToReachHalfLength) {
        turnLeft();
        moveWithoutBeeper(movesToReachHalfWidth);

        fillEvenZigZagVertically(movesToReachHalfLength);
        fillEvenAndReturn(movesToReachHalfWidth, true);

        turnRight();
        fillEvenAndReturn(movesToReachHalfLength, true);

        turnRight();
        fillEvenAndReturn(movesToReachHalfWidth, true);
        pickBeeper();
    }

    public void evenLengthOddWidth(int movesToReachHalfWidth, int movesToReachHalfLength) {

        turnLeft();
        moveWithoutBeeper(movesToReachHalfWidth);

        fillEvenZigZagVertically(movesToReachHalfLength);

        fillOddAndReturn(movesToReachHalfWidth);

        move();

        fillOddAndReturn(movesToReachHalfWidth);

        turnRight();

        fillEvenAndReturn(movesToReachHalfLength, true);

        pickBeeper();
    }

    public void oddLengthOddWidth(int movesToReachHalfWidth, int movesToReachHalfLength) {

        turnLeft();
        moveWithoutBeeper(movesToReachHalfWidth);

        turnLeft();
        moveWithBeeper(movesToReachHalfLength, true);

        turnLeft();
        fillOddAndReturn(movesToReachHalfWidth);

        fillOddAndReturn(movesToReachHalfWidth);

        turnRight();

        moveWithBeeper(movesToReachHalfLength, false);
    }

    public void oddLengthEvenWidth(int movesToReachHalfWidth, int movesToReachHalfLength) {

        turnLeft();
        moveWithoutBeeper(movesToReachHalfWidth);

        turnLeft();
        moveWithBeeper(movesToReachHalfLength, true);

        turnLeft();

        fillEvenAndReturn(movesToReachHalfWidth, false);
        fillEvenAndReturn(movesToReachHalfWidth, false);

        pickBeeper();
        turnRight();
        move();

        moveWithBeeper(movesToReachHalfLength, false);
    }

    public void divide4Equals(int width, int length) {
        boolean widthIsEven = width % 2 == 0;
        boolean lengthIsEven = length % 2 == 0;

        int movesToReachHalfWidth = widthIsEven ? width / 2 - 1 : width / 2;
        int movesToReachHalfLength = lengthIsEven ? length / 2 - 1 : length / 2;


        if (movesToReachHalfWidth > movesToReachHalfLength) {
            if (widthIsEven && lengthIsEven) { // 14x12
                evenWidthEvenLength(movesToReachHalfWidth, movesToReachHalfLength);
            } else if (widthIsEven) { // 14x11
                evenWidthOddLength(movesToReachHalfWidth, movesToReachHalfLength);
            } else if (lengthIsEven) { // 13 x 10
                oddWidthEvenLength(movesToReachHalfWidth, movesToReachHalfLength);
            } else { // 13 x 7
                oddWidthOddLength(movesToReachHalfWidth, movesToReachHalfLength);
            }
        } else {
            if (widthIsEven && lengthIsEven) { // 12x14
                evenLengthEvenWidth(movesToReachHalfWidth, movesToReachHalfLength);
            } else if (widthIsEven) { // 10x13
                evenLengthOddWidth(movesToReachHalfWidth, movesToReachHalfLength);
            } else if (lengthIsEven) { //9x12
                oddLengthEvenWidth(movesToReachHalfWidth, movesToReachHalfLength);
            } else { // 9x13
                oddLengthOddWidth(movesToReachHalfWidth, movesToReachHalfLength);
            }
        }


    }


    public void run() {

        int width = calculateWidth();
        int length = calculateLength();
        System.out.println("Width = " + width);
        System.out.println("Length = " + length);
        divide4Equals(width, length);


    }
}