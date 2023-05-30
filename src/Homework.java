import stanford.karel.SuperKarel;

import java.awt.*;

public class Homework extends SuperKarel {
    private int totalNumberOfMoves;
    private int totalNumberOfBeepers;
    private int width;
    private int length;
    private boolean isWidthEven;
    private int boxSideLength;
    private int halfBoxSideLength;
    private int halfWidth;
    private int halfLength;
    private int widthOutOfBoxEachSide;
    private int lengthOutOfBoxEachSide;
    private int movesToReachStartPoint;
    private int firstHalf;
    private int secondHalf;
    private int firstOutOfBox;
    private int secondOutOfBox;
    private boolean showRepeatedPoints = false;

    public void getDimensions() {
        while (frontIsClear()) {
            move();
            width++;
        }
        turnLeft();
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

    public void fillOddAndReturn(int numberOfMovesBeforeTurning, int numberOfMovesAfterTurning) {
        moveWithBeeper(numberOfMovesBeforeTurning, false);
        turnAround();
        moveWithoutBeeper(numberOfMovesAfterTurning);
    }

    public void fillEvenAndReturn(int numberOfMovesBeforeTurning, int numberOfMovesAfterTurning) {
        moveWithBeeper(numberOfMovesBeforeTurning, false);
        turnLeft();
        move();
        totalNumberOfMoves++;
        turnLeft();
        moveWithBeeper(numberOfMovesAfterTurning, true);
    }

    public void goToEvenEvenStartPoint() {
        movesToReachStartPoint = widthOutOfBoxEachSide + lengthOutOfBoxEachSide - 1;
        turnLeft();
        moveWithoutBeeper(widthOutOfBoxEachSide);
        turnLeft();
        moveWithoutBeeper(lengthOutOfBoxEachSide);
    }

    public void evenEvenFirstPattern(int outOfBoxEachSide, boolean preBeeper) {
        moveWithBeeper(halfBoxSideLength + 1, preBeeper);
        turnLeft();
        fillEvenAndReturn(outOfBoxEachSide, outOfBoxEachSide + boxSideLength);
    }

    public void evenEvenSecondPattern(int outOfBoxEachSide) {
        fillEvenAndReturn(outOfBoxEachSide, outOfBoxEachSide);
        turnLeft();
        moveWithBeeper(halfBoxSideLength + 1, false);
        turnRight();
    }

    public void evenEvenThirdPattern(int turn) {
        int numberOfMoves = turn == 1 || turn == 4 ? boxSideLength : halfBoxSideLength;
        moveWithBeeper(numberOfMoves, false);
        turnRight();
    }

    public void goToHalfWidth() {
        turnAround();
        moveWithoutBeeper(halfLength);
        turnRight();
    }

    public void goToHalfLength() {
        turnLeft();
        moveWithoutBeeper(halfWidth);
        turnLeft();
    }

    public void initializeVariable(boolean condition) {
        if (condition) {
            firstHalf = halfLength;
            secondHalf = halfWidth;
            firstOutOfBox = widthOutOfBoxEachSide;
            secondOutOfBox = lengthOutOfBoxEachSide;

            goToHalfWidth();
        } else {
            firstHalf = halfWidth;
            secondHalf = halfLength;
            firstOutOfBox = lengthOutOfBoxEachSide;
            secondOutOfBox = widthOutOfBoxEachSide;

            goToHalfLength();
        }
    }

    public void fillBoxSides(boolean halfBox, int turn) {
        int numberOfMoves = halfBox ? boxSideLength / 2 : turn != 2 ? boxSideLength : boxSideLength - 1;
        moveWithBeeper(numberOfMoves, false);
        turnLeft();
    }

    public void divideEvenWithEvenMap() {
        goToEvenEvenStartPoint();

        boolean preBeeper = true;
        int outOfBoxEachSide = widthOutOfBoxEachSide;

        for (int i = 0; i < 2; i++) {
            evenEvenFirstPattern(outOfBoxEachSide, preBeeper);
            evenEvenSecondPattern(outOfBoxEachSide);

            preBeeper = !preBeeper;
            outOfBoxEachSide = lengthOutOfBoxEachSide;
        }
        for (int i = 0; i < 5; i++) {
            evenEvenThirdPattern(i);
        }
        moveWithBeeper(halfBoxSideLength - 1, false);
    }

    public void divideOddWithOddMap() {
        initializeVariable(width > length);
        movesToReachStartPoint = firstHalf - 1;

        moveWithBeeper(firstOutOfBox, true);

        turnRight();

        boolean halfBox = true;
        for (int i = 0; i < 5; i++) {
            fillBoxSides(halfBox, 2);
            if (i == 0 || i == 3) halfBox = !halfBox;
        }

        moveWithBeeper(halfBoxSideLength, false);
        turnRight();

        for (int i = 0; i < 2; i++) {
            fillOddAndReturn(halfBoxSideLength + secondOutOfBox, halfBoxSideLength + secondOutOfBox);
        }
        turnLeft();
        moveWithBeeper(secondHalf, false);
    }

    public void divideOddWithEvenMap() {
        initializeVariable(isWidthEven);
        movesToReachStartPoint = firstHalf - 1;

        moveWithBeeper(firstOutOfBox, true);
        turnRight();

        boolean halfBox = true;
        for (int i = 0; i < 5; i++) {
            fillBoxSides(halfBox, i);
            if (i == 0 || i == 3) halfBox = !halfBox;
        }
        moveWithBeeper(halfBoxSideLength, false);
        turnRight();
        for (int i = 0; i < 2; i++) {
            fillEvenAndReturn(halfBoxSideLength + secondOutOfBox, halfBoxSideLength + secondOutOfBox);
        }
        turnLeft();
        moveWithBeeper(secondHalf, false);
    }

    public void divideMap() {
        width = 1;
        length = 1;
        totalNumberOfBeepers = 0;
        totalNumberOfMoves = 0;
        movesToReachStartPoint = 0;

        getDimensions();

        if (width < 7) throw new DimensionsTooSmallException("width");
        if (length < 7) throw new DimensionsTooSmallException("length");

        isWidthEven = width % 2 == 0;
        boolean isLengthEven = length % 2 == 0;

        boxSideLength = Math.min(isWidthEven ? width - 3 : width - 2,
                isLengthEven ? length - 3 : length - 2);

        halfBoxSideLength = boxSideLength / 2;
        halfWidth = width / 2;
        halfLength = length / 2;

        widthOutOfBoxEachSide = (width - boxSideLength) / 2;
        lengthOutOfBoxEachSide = (length - boxSideLength) / 2;

        System.out.println("Width = " + width);
        System.out.println("Length = " + length);
        System.out.println("Box Side Length = " + boxSideLength);

        if (isLengthEven && isWidthEven) {
            divideEvenWithEvenMap();
        } else if (!isWidthEven && !isLengthEven) {
            divideOddWithOddMap();
        } else {
            divideOddWithEvenMap();
        }
        System.out.println("Total number of placed Beepers = " + totalNumberOfBeepers);
        System.out.println("Total number of Moves = " + (totalNumberOfMoves - movesToReachStartPoint));
        System.out.println("Total number of moves to reach the Start point = " + movesToReachStartPoint + "\n");
    }

    @Override
    public void run() {
        setBeepersInBag(1000);

        divideMap();
    }
}