package util.Mathf;

public class FixedPoint {
    public int value;
    public final int D_SHIFT;
    public final int D_FACT;

    public FixedPoint(int d_SHIFT) {
        D_SHIFT = d_SHIFT;
        D_FACT = 1 << d_SHIFT;
    }

    public FixedPoint(int value, int d_SHIFT) {
        this(d_SHIFT);
        this.value = value;
    }
}
