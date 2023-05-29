public class DimensionsTooSmallException extends RuntimeException {
    String dimension;

    DimensionsTooSmallException(String dimension) {
        super(String.format("%s is too small to be divided in 4 equal chambers with a box between",
                dimension));
    }
}
