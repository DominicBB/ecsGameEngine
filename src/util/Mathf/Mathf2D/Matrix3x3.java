/*
package util.geometry;

import components.Camera;
import components.Scene;

public class Matrix3x3 {
    private float[][] values;

    private Matrix3x3(float[][] fs) {
        values = fs;

    }




    public Matrix3x3 transpose() {
        return new Matrix3x3(new float[][] {
                { values[0][0], values[1][0], values[2][0]},
                { values[0][1], values[1][1], values[2][1]},
                { values[0][2], values[1][2], values[2][2]}
        });
    }


    public Vector2D multiplyProjection(Vector2D v2d) {
        //float w = 1;
        float x = (v2d.x * values[0][0] + v2d.y * values[1][0] + v2d.z * values[2][0]) + values[3][0];
        float y = (v2d.x * values[0][1] + v2d.y * values[1][1] + v2d.z * values[2][1]) + values[3][1];
        float z = (v2d.x * values[0][2] + v2d.y * values[1][2] + v2d.z * values[2][2]) + values[3][2];
        float e = (v2d.x * values[0][3] + v2d.y * values[1][3] + v2d.z * values[2][3]) + values[3][3];
        if (e != 0) {
            x = x / e;
            y = y / e;
            z = z / e;
        }
        return new Vector2D(x, y);
    }

    public Triangle multiplyProjection(Triangle t) {

        return new Triangle(multiplyProjection(t.vectors[0]), multiplyProjection(t.vectors[1]),
                multiplyProjection(t.vectors[2]),t);

    }

    public Vector2D multiply3x3(Vector2D v2d, float w) {
        float x = (v2d.x * values[0][0] + v2d.y * values[1][0]) + (w*values[2][0]);
        float y = (v2d.x * values[0][1] + v2d.y * values[1][1]) + (w*values[2][1]);
        float z = (v2d.x * values[0][2] + v2d.y * values[1][2])  + (w*values[2][2]);

        return new Vector2D(x, y);
    }

    public Triangle multiply3x3(Triangle t, float w) {
        return new Triangle(multiply3x3(t.textures[0], 1),multiply3x3(t.textures[1], 1), multiply3x3(t.textures[2], 1), t);
    }

    public static Matrix3x3 newPointAt(Vector2D f, Vector2D r, Vector2D newUp, Vector2D offset) {
        return new Matrix3x3(
                new float[][] {
                        { r.x, r.y, 0.0f },
                        { newUp.x, newUp.y, 0.0f },
                        { f.x, f.y, 0.0f},
                        {offset.x, offset.y, 1.0f }});
    }

    public static Matrix3x3 newLookAt(Vector2D f, Vector2D r, Vector2D newUp, Vector2D offset) {
        return new Matrix3x3(
                new float[][] {
                        { r.x, newUp.x, f.x },
                        { r.y, newUp.y, f.y},
                        {-offset.dotProduct(r), -offset.dotProduct(newUp), -offset.dotProduct(f), 1.0f }});
    }


    public static Matrix3x3 newProjectionMatrix(Scene s, Camera c) {
        return new Matrix3x3(
                new float[][] {
                        { s.aRatio* c.fFov, 0.0f, 0.0f, 0.0f },
                        { 0.0f, c.fFov, 0.0f, 0.0f },
                        { 0.0f, 0.0f,  c.zRange , 1.0f },
                        { 0.0f, 0.0f, -c.zNear * c.zRange, 0.0f }
                });
    }

    public static Matrix3x3 newTranslation(float tx, float ty, float tz) {
        return new Matrix3x3(
                new float[][] {
                        { 1.0f, 0.0f, 0.0f},
                        { 0.0f, 1.0f, 0.0f},
                        { tx, ty, 1.0f}});
    }

    public static Matrix3x3 newScale(float tx, float ty, float tz) {
        return new Matrix3x3(
                new float[][] {
                        { tx, 0.0f, 0.0f },
                        { 0.0f, ty, 0.0f},
                        { 0.0f, 0.0f, 1.0f},
                });
    }


    public static Matrix3x3 newXRotation(float th) {
        float sinth = (float) Math.sin(th);
        float costh = (float) Math.cos(th);
        return new Matrix3x3(new float[][] {
                { 1.0f, 0.0f, 0.0f},
                { 0.0f, costh, -sinth },
                { 0.0f, sinth, costh},
        });
    }

    public static Matrix3x3 newYRotation(float th) {
        float sinth = (float) Math.sin(th);
        float costh = (float) Math.cos(th);
        return new Matrix3x3(new float[][] {
                { costh, 0.0f, sinth },
                { 0.0f, 1.0f, 0.0f},
                { -sinth, 0.0f, costh},
        });
    }

    public static Matrix3x3 newZRotation(float th) {
        float sinth = (float) Math.sin(th);
        float costh = (float) Math.cos(th);
        return new Matrix3x3(new float[][] {
                { costh, -sinth, 0.0f},
                { sinth, costh, 0.0f},
                { 0.0f, 0.0f, 1.0f},
        });
    }

    public static Matrix3x3 newIdentityMatrix() {
        return new Matrix3x3(new float[][] {
                { 1.0f, 0.0f, 0.0f },
                { 0.0f, 1.0f, 0.0f},
                { 0.0f, 0.0f, 1.0f}
        });

    }



    public Matrix3x3 compose(Matrix3x3 other) {
        float[][] ans = new float[3][3];
        for(int row = 0; row<3;row++) {
            for(int col = 0; col<3;col++) {
                for(int i = 0; i<3;i++) {
                    ans[row][col] += this.values[row][i] * other.values[i][col];

                }
            }
        }
        return new Matrix3x3(ans);
    }



    public String toString() {
        StringBuilder ans = new StringBuilder();
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                ans.append(values[row][col]).append(' ');
            }
            ans.append('\n');
        }
        return ans.toString();
    }
}
*/
