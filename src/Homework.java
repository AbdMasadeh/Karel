import stanford.karel.SuperKarel;


public class Homework extends SuperKarel {

    /* You fill the code here */

    @Override
    public void run() {
        while (frontIsClear()) {
            move();
            turnLeft();
            turnRight();
            putBeeper();
            pickBeeper();
        }
    }
}
