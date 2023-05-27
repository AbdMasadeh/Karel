/*
 * File: BlankKarel.java
 * ---------------------
 * This class is a blank one that you can change at will.
 */

import stanford.karel.*;


public class BlankKarel extends SuperKarel {

    int totalNumberOfMoves = 0;
    int totalNumberOfBeepers = 0;

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

    public void fillEvenAndReturn(int numberOfMoves) {

        moveWithBeeper(numberOfMoves, false);

        turnLeft();
        move();
        turnLeft();

//        if (facingNorth()) {
//            turnLeft();
//            move();
//            turnLeft();
//        } else if (facingSouth()) {
//            turnRight();
//            move();
//            turnRight();
//        } else { //if (facingEast())
//            turnLeft();
//            move();
//            turnLeft();
//        }

        moveWithBeeper(numberOfMoves, true);
    }

    public void moveWithBeeper(int numberOfMoves, boolean preBeeper) {

        if (preBeeper) {
            putBeeper();
            totalNumberOfBeepers++;
        }

        while (numberOfMoves-- > 0) {
            move();
            totalNumberOfMoves++;
            if (noBeepersPresent()) {
                putBeeper();
                totalNumberOfBeepers++;
            }
        }
    }

    public void moveWithoutBeeper(int numberOfMoves) {
        while (numberOfMoves-- > 0) {
            move();
            totalNumberOfMoves++;
        }
    }

    public void goToStart(int width, int length, int boxSideLength) {
        turnLeft();
        moveWithoutBeeper((int) Math.ceil((width - boxSideLength) / 2.0));
        turnLeft();
        moveWithoutBeeper((int) Math.ceil((length - boxSideLength) / 2.0));
    }

    public void evenAndEven(int width, int length, int boxSideLength) {

        int outOfBoxWidthEachSide = (width - boxSideLength) / 2;
        int outOfBoxLengthEachSide = (length - boxSideLength) / 2;
        int halfBoxSideLength = boxSideLength / 2;

        //After goToStart
        moveWithBeeper(halfBoxSideLength, true);
        turnLeft();
        //first out of box
        fillEvenAndReturn(outOfBoxWidthEachSide);
        // to the opposite side
        moveWithBeeper(boxSideLength - 1, false);
        //second out of box width
        fillEvenAndReturn(outOfBoxWidthEachSide);
        turnLeft();
        // going up
        moveWithBeeper(halfBoxSideLength, false);
        turnRight();
        //going to the middle
        moveWithBeeper(halfBoxSideLength, false);
        turnLeft();
        //Up center out of box
        fillEvenAndReturn(outOfBoxLengthEachSide);
        // going to the bottom
        moveWithBeeper(boxSideLength - 1, false);
        //Down center out of box
        fillEvenAndReturn(outOfBoxLengthEachSide);
        turnLeft();
        // going left
        moveWithBeeper(halfBoxSideLength,false);
        turnRight();

        moveWithBeeper(halfBoxSideLength - 1, false);
        turnRight();
        moveWithBeeper(boxSideLength - 1,false);
        turnRight();
        moveWithBeeper(halfBoxSideLength - 1, false);
        turnRight();
        moveWithBeeper(halfBoxSideLength - 1, false);
        turnRight();
        moveWithBeeper(boxSideLength - 1, false);
        turnRight();
        moveWithBeeper(halfBoxSideLength - 1, false);
    }

    public void run() {

        int width = calculateWidth();
        int length = calculateLength();

        boolean isWidthEven = width % 2 == 0;
        boolean isLengthEven = length % 2 == 0;

        int halfWidth = isWidthEven ? width / 2 - 1 : width / 2;
        int halfLength = isLengthEven ? length / 2 - 1 : length / 2;

        int boxSideLength = Math.min(width - 2, length - 2);


        int halfBoxSideLength = boxSideLength / 2;

        System.out.println("Width = " + width);
        System.out.println("Length = " + length);
        System.out.println("Box Side Length = " + boxSideLength);


        goToStart(width, length, boxSideLength);

        evenAndEven(width, length, boxSideLength);

        System.out.println("Total number of placed Beepers = " + totalNumberOfBeepers);
        System.out.println("Total number of Moves = " + totalNumberOfMoves);


    }
}