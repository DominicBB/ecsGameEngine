package util.geometry;
public class Vector2D {
    public float x;
    public float y;
    public float w;
    
    public Vector2D(float x, float y){
        this.x = x;
        this.y = y;
        this.w = 1;
    }

    public Vector2D(float x, float y, float w){
        this.x = x;
        this.y = y;
        this.w = 1;
    }


    public Vector2D minus(Vector2D other) {
        return new Vector2D(this.x - other.x, this.y - other.y);
    }

    public Vector2D plus(Vector2D other) {
        return new Vector2D(this.x + other.x, this.y + other.y);
    }

    public Vector2D scale(float scaler) {
        return new Vector2D(this.x *scaler, this.y *scaler);
    }

    public Vector2D unit() {
        float m = this.magnitude();
        return new Vector2D(this.x / m, this.y / m);
    }

    private float magnitude() {
        return (float) Math.sqrt((this.x * this.x) + (this.y * this.y));
    }
    public String toString() {
        return "["+ x +","+ y +"]";
    }


}
