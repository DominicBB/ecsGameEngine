package util;

public class FloatBuffer {

    private final float[] fBuffer;
    public final int width;
    public final int height;

    public FloatBuffer(int width, int height) {
        this.width = width;
        this.height = height;
        this.fBuffer = new float[width * height];
    }

    public float getFloat(int index) {
        return fBuffer[index];
    }

    public float getFloat(int x, int y) {
        return fBuffer[y * width + x];
    }

    public void setFloat(int x, int y, float value) {
        this.fBuffer[y * width + x] = value;
    }

    public void setFloat(int index, float value) {
        fBuffer[index] = value;
    }

    public float[] getBuffer() {
        return fBuffer;
    }

    public void resetPositiveInf() {
        for (int i = 0; i < fBuffer.length; i++) {
            fBuffer[i] = Float.POSITIVE_INFINITY;
        }
    }

    public void reset(float resetTo) {
        for (int i = 0; i < fBuffer.length; i++) {
            fBuffer[i] = resetTo;
        }
    }

}
