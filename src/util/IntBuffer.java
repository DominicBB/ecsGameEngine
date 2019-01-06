package util;

import java.util.Arrays;

public class IntBuffer {
    private final int[] fBuffer;
    public final int width;
    public final int height;

    public IntBuffer(int width, int height) {
        this.width = width;
        this.height = height;
        this.fBuffer = new int[width * height];
    }

    public int getInt(int index) {
        return fBuffer[index];
    }

    public int getInt(int x, int y) {
        return fBuffer[y * width + x];
    }

    public void setInt(int x, int y, int value) {
        this.fBuffer[y * width + x] = value;
    }

    public void setInt(int index, int value) {
        fBuffer[index] = value;
    }

    public int[] getBuffer() {
        return fBuffer;
    }

    public void resetMaxVal() {
        /*for (int i = 0; i < fBuffer.length; i++) {
            fBuffer[i] = Float.POSITIVE_INFINITY;
        }*/
        Arrays.fill(fBuffer, Integer.MAX_VALUE);
    }

    public void reset(int resetTo) {
        for (int i = 0; i < fBuffer.length; i++) {
            fBuffer[i] = resetTo;
        }
    }
}
