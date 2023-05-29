import stanford.karel.SuperKarel;

import java.awt.*;


public class Homework extends SuperKarel {
    int totalNumberOfMoves;
    int totalNumberOfBeepers;
    int width;
    int length;
    boolean isWidthEven;
    boolean isLengthEven;
    int boxSideLength;
    int halfBoxSideLength;
    int widthOutOfBoxEachSide;
    int lengthOutOfBoxEachSide;
    boolean showRepeatedPoints = true;

    public void calculateWidth() {
        while (frontIsClear()) {
            move();
            width++;
        }
        turnLeft();
    }

    public void calculateLength() {
        while (frontIsClear()) {
            move();
            length++;
        }
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
            } else {
                if (showRepeatedPoints) paintCorner(Color.red);
            }
        }
    }

    public void moveWithoutBeeper(int numberOfMoves) {
        while (numberOfMoves-- > 0) {
            move();
            totalNumberOfMoves++;
            if (beepersPresent() && showRepeatedPoints) paintCorner(Color.red);
        }
    }

    public void fillEvenAndReturn(int numberOfMovesBeforeTurning, int numberOfMovesAfterTurning) {
        moveWithBeeper(numberOfMovesBeforeTurning, false);
        turnLeft();
        move();
        totalNumberOfMoves++;
        turnLeft();
        moveWithBeeper(numberOfMovesAfterTurning, true);
    }

    public void goToStartPoint() {
        turnLeft();
        moveWithoutBeeper((width - boxSideLength) / 2);
        turnLeft();
        moveWithoutBeeper((length - boxSideLength) / 2);
    }

    public void temp1(int outOfBoxEachSide, boolean preBeeper) {
        moveWithBeeper(halfBoxSideLength + 1, preBeeper);
        turnLeft();
        fillEvenAndReturn(outOfBoxEachSide, outOfBoxEachSide + boxSideLength);
    }

    public void temp2(int outOfBoxEachSide) {
        fillEvenAndReturn(outOfBoxEachSide, outOfBoxEachSide);
        turnLeft();
        moveWithBeeper(halfBoxSideLength + 1, false);
        turnRight();
    }

    public void temp3(int turn) {
        int numberOfMoves = turn == 1 || turn == 4 ? boxSideLength : halfBoxSideLength;
        moveWithBeeper(numberOfMoves, false);
        turnRight();
    }

    public void evenWithEven() {
        goToStartPoint();

        boolean preBeeper = true;
        int outOfBoxEachSide = widthOutOfBoxEachSide;

        for (int i = 0; i < 2; i++) {
            temp1(outOfBoxEachSide, preBeeper);
            temp2(outOfBoxEachSide);

            preBeeper = !preBeeper;
            outOfBoxEachSide = lengthOutOfBoxEachSide;
        }
        for (int i = 0; i < 5; i++) {
            temp3(i);
        }
        moveWithBeeper(halfBoxSideLength - 1, false);
    }

    public void oddWithOdd() {}

    public void fillBoxSide(boolean halfBox, int turn) {
        int numberOfMoves = halfBox ? boxSideLength / 2
                : turn != 2 ? boxSideLength
                : boxSideLength - 1;
        moveWithBeeper(numberOfMoves, false);
        turnLeft();
    }

    public void oddWithEven() {

        int halfWidth = width / 2;
        int halfLength = length / 2;

        int firstHalf;
        int secondHalf;

        int firstOutOfBox;
        int secondOutOfBox;

        if (isWidthEven) {
            firstHalf = halfLength;
            secondHalf = halfWidth;

            firstOutOfBox = widthOutOfBoxEachSide;
            secondOutOfBox = lengthOutOfBoxEachSide;
        } else {
            firstHalf = halfWidth;
            secondHalf = halfLength;

            firstOutOfBox = lengthOutOfBoxEachSide;
            secondOutOfBox = widthOutOfBoxEachSide;
        }

        if (isWidthEven) {
            turnAround();
            moveWithoutBeeper(firstHalf);
            turnRight();
        } else {
            turnLeft();
            moveWithoutBeeper(firstHalf);
            turnLeft();
        }

        moveWithBeeper(firstOutOfBox, true);

        turnRight();

        boolean halfBox = true;

        for (int i = 0; i < 5; i++) {
            fillBoxSide(halfBox, i);
            if (i == 0 || i == 3) halfBox = !halfBox;
        }

        moveWithBeeper(halfBoxSideLength, false);

        turnRight();
        for (int i = 0; i < 2; i++) {
            fillEvenAndReturn(halfBoxSideLength + secondOutOfBox,
                    halfBoxSideLength + secondOutOfBox);
        }
        turnLeft();
        moveWithBeeper(secondHalf, false);
    }

    @Override
    public void run() {
        setBeepersInBag(1000);

        width = 1;
        length = 1;
        totalNumberOfBeepers = 0;
        totalNumberOfMoves = 0;

        calculateWidth();
        calculateLength();

        isWidthEven = width % 2 == 0;
        isLengthEven = length % 2 == 0;

        boxSideLength = Math.min(isWidthEven ? width - 3 : width - 2,
                isLengthEven ? length - 3 : length - 2);

        halfBoxSideLength = boxSideLength / 2;

        widthOutOfBoxEachSide = (width - boxSideLength) / 2;
        lengthOutOfBoxEachSide = (length - boxSideLength) / 2;


        System.out.println("Width = " + width);
        System.out.println("Length = " + length);
        System.out.println("Box Side Length = " + boxSideLength);

        if (isLengthEven && isWidthEven) {
            evenWithEven();
        } else if (!isWidthEven && !isLengthEven) {
            oddWithOdd();
        } else {
            oddWithEven();
        }

        System.out.println("Total number of placed Beepers = " + totalNumberOfBeepers);
        System.out.println("Total number of Moves = " + totalNumberOfMoves);
    }
}
