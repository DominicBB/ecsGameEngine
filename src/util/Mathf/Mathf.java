package util.Mathf;

public class Mathf {
    public static float clamp(float min, float value, float max) {
        return (value > max) ? max : (value < min) ? min : value;
    }
}
