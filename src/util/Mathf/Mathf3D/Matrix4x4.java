package util.Mathf.Mathf3D;

public class Matrix4x4 {

    private float[][] values;

    private Matrix4x4(float[][] fs) {
        values = fs;

    }

    //transpose top newLeft 3x3. dot product last row buy other rows
    //pseudo inverse of pointAt
    public static Matrix4x4 pointAtToLookAt(Matrix4x4 pointAtMatrix) {
        Matrix4x4 p = pointAtMatrix;
        Vector3D a = new Vector3D(p.values[0][0], p.values[0][1], p.values[0][2]);
        Vector3D b = new Vector3D(p.values[1][0], p.values[1][1], p.values[1][2]);
        Vector3D c = new Vector3D(p.values[2][0], p.values[2][1], p.values[2][2]);
        Vector3D t = new Vector3D(p.values[3][0], p.values[3][1], p.values[3][2]);
        return new Matrix4x4(new float[][]{
                {p.values[0][0], p.values[1][0], p.values[2][0], 0.0f},
                {p.values[0][1], p.values[1][1], p.values[2][1], 0.0f},
                {p.values[0][2], p.values[1][2], p.values[2][2], 0.0f},
                {-t.dotProduct(a), -t.dotProduct(b), -t.dotProduct(c), 1.0f}
        });
    }

    public Matrix4x4 transpose() {
        if (this.values.length == 4) return transpose4x4();
        return new Matrix4x4(new float[][]{
                {values[0][0], values[1][0], values[2][0]},
                {values[0][1], values[1][1], values[2][1]},
                {values[0][2], values[1][2], values[2][2]}
        });
    }

    public Matrix4x4 transpose4x4() {
        return new Matrix4x4(new float[][]{
                {values[0][0], values[1][0], values[2][0], values[3][0]},
                {values[0][1], values[1][1], values[2][1], values[3][1]},
                {values[0][2], values[1][2], values[2][2], values[3][2]},
                {values[0][3], values[1][3], values[2][3], values[3][3]}
        });
    }

    /*public Vector3D multiply4x4(Vector3D v) {
        float x = (v.x * values[0][0] + v.y * values[0][1] + v.z * values[0][2]) + (v.w * values[0][3]);
        float y = (v.x * values[1][0] + v.y * values[1][1] + v.z * values[1][2]) + (v.w * values[1][3]);
        float z = (v.x * values[2][0] + v.y * values[2][1] + v.z * values[2][2]) + (v.w * values[2][3]);
        float w = (v.x * values[3][0] + v.y * values[3][1] + v.z * values[3][2]) + (v.w * values[3][3]);

        return new Vector3D(x, y, z, w);
    }*/
    public Vector3D multiply4x4(Vector3D v) {
        float x = (v.x * values[0][0] + v.y * values[1][0] + v.z * values[2][0]) + (v.w * values[3][0]);
        float y = (v.x * values[0][1] + v.y * values[1][1] + v.z * values[2][1]) + (v.w * values[3][1]);
        float z = (v.x * values[0][2] + v.y * values[1][2] + v.z * values[2][2]) + (v.w * values[3][2]);
        float w = (v.x * values[0][3] + v.y * values[1][3] + v.z * values[2][3]) + (v.w * values[3][3]);

        return new Vector3D(x, y, z, w);
    }

    public Triangle multiply4x4(Triangle t) {
        return new Triangle(multiply4x4(t.vectors[0]), multiply4x4(t.vectors[1]), multiply4x4(t.vectors[2]), t);
    }

    public static Matrix4x4 newPointAt(Vector3D f, Vector3D r, Vector3D up, Vector3D offset) {
        return new Matrix4x4(
                new float[][]{
                        {r.x, up.x, f.x, offset.x},
                        {r.y, up.y, f.y, offset.y},
                        {r.z, up.z, f.z, offset.z},
                        {0.0f, 0.0f, 0.0f, 1.0f}});
    }

    public static Matrix4x4 newViewToWorld(Vector3D f, Vector3D r, Vector3D up, Vector3D offset) {
        return new Matrix4x4(
                new float[][]{
                        {r.x, up.x, f.x, offset.x},
                        {r.y, up.y, f.y, offset.y},
                        {r.z, up.z, f.z, offset.z},
                        {0.0f, 0.0f, 0.0f, 1.0f}});
    }

    public static Matrix4x4 newLookAt(Vector3D f, Vector3D r, Vector3D up, Vector3D offset) {
        return new Matrix4x4(
                new float[][]{
                        {r.x, r.y, r.z, 0f},
                        {up.x, up.y, up.z, 0f},
                        {f.x, f.y, f.z, 0f},
                        {offset.x, offset.y, offset.z, 1.0f}});
    }


   /* public static Matrix4x4 newProjection(float aRatio, float fFov, float zNear, float zRange) {
        return new Matrix4x4(
                new float[][]{
                        {aRatio * fFov, 0.0f, 0.0f, 0.0f},
                        {0.0f, fFov, 0.0f, 0.0f},
                        {0.0f, 0.0f, zRange, 1.0f},
                        {0.0f, 0.0f, -zNear * zRange, 0.0f}
                });
    }*/

    public static Matrix4x4 newProjection(float fFov_aRatio, float fFov, float zNear, float zFar) {
        float zRange = (zFar - zNear);
        return new Matrix4x4(
                new float[][]{
                        {fFov_aRatio, 0.0f, 0.0f, 0.0f},
                        {0.0f, -fFov, 0.0f, 0.0f},
                        {0.0f, 0.0f, -zFar / zRange, -1.0f},
                        {0.0f, 0.0f, -(zFar * zNear) / zRange, 0.0f}
                });
    }

    public static Matrix4x4 InitPerspective(float fov, float aspectRatio, float zNear, float zFar) {
        //float tanHalfFOV = (float) Math.tan(fov / 2);
        float zRange = zNear - zFar;
        return new Matrix4x4(new float[][]{
                {aspectRatio, 0, 0, 0},
                {0, -fov, 0, 0},
                {0, 0, -(-zNear - zFar) / zRange, -1f},
                {0.0f, 0f, 2f * zFar * zNear / zRange, 0f}
        });
    }

    public static Matrix4x4 newProjectionToView(float fFov_aRatio, float fFov, float zNear, float zFar) {
        float zRange = zNear - zFar;
        return new Matrix4x4(
                new float[][]{
                        {1 / fFov_aRatio, 0.0f, 0.0f, 0.0f},
                        {0.0f, 1 / fFov, 0.0f, 0.0f},
                        {0.0f, 0.0f, 0.0f, -1.0f},
                        {0.0f, 0.0f, zRange / (2 * zFar * zNear), (zFar + zNear) / (2 * zFar * zNear)}
                });
    }

    public static Matrix4x4 newTranslation(Vector3D translation) {
        return newTranslation(translation.x, translation.y, translation.z);
    }

    public static Matrix4x4 newTranslation(float tx, float ty, float tz) {
        return new Matrix4x4(
                new float[][]{
                        {1.0f, 0.0f, 0.0f, 0.0f},
                        {0.0f, 1.0f, 0.0f, 0.0f},
                        {0.0f, 0.0f, 1.0f, 0.0f},
                        {tx, ty, tz, 1.0f}});
    }

    public static Matrix4x4 newScale(Vector3D translation) {
        return newScale(translation.x, translation.y, translation.z);
    }

    public static Matrix4x4 newScale(float tx, float ty, float tz) {
        return new Matrix4x4(
                new float[][]{
                        {tx, 0.0f, 0.0f, 0.0f},
                        {0.0f, ty, 0.0f, 0.0f},
                        {0.0f, 0.0f, tz, 0.0f},
                        {0.0f, 0.0f, 0.0f, 1.0f}});
    }


    public static Matrix4x4 newXRotation(float th) {
        float sinth = (float) Math.sin(th);
        float costh = (float) Math.cos(th);
        return new Matrix4x4(new float[][]{
                {1.0f, 0.0f, 0.0f, 0.0f},
                {0.0f, costh, -sinth, 0.0f},
                {0.0f, sinth, costh, 0.0f},
                {0.0f, 0.0f, 0.0f, 1.0f}});
    }

    public static Matrix4x4 newYRotation(float th) {
        float sinth = (float) Math.sin(th);
        float costh = (float) Math.cos(th);
        return new Matrix4x4(new float[][]{
                {costh, 0.0f, sinth, 0.0f},
                {0.0f, 1.0f, 0.0f, 0.0f},
                {-sinth, 0.0f, costh, 0.0f},
                {0.0f, 0.0f, 0.0f, 1.0f}});
    }

    public static Matrix4x4 newZRotation(float th) {
        float sinth = (float) Math.sin(th);
        float costh = (float) Math.cos(th);
        return new Matrix4x4(new float[][]{
                {costh, -sinth, 0.0f, 0.0f},
                {sinth, costh, 0.0f, 0.0f},
                {0.0f, 0.0f, 1.0f, 0.0f},
                {0.0f, 0.0f, 0.0f, 1.0f}
        });
    }

    /**
     * QUATERNION MUST BE NORMALISED
     *
     * @param q QUATERNION MUST BE NORMALISED
     * @return
     */
    public static Matrix4x4 newRotationNormalisedQ(Quaternion q) {
        float xx = q.getX() * q.getX();
        float xy = q.getX() * q.getY();
        float xz = q.getX() * q.getZ();
        float xw = q.getX() * q.getW();

        float yy = q.getY() * q.getY();
        float yz = q.getY() * q.getZ();
        float yw = q.getY() * q.getW();

        float zz = q.getZ() * q.getZ();
        float zw = q.getZ() * q.getW();

        return new Matrix4x4(new float[][]{
                {1 - 2 * (yy + zz), 2 * (xy + zw), 2 * (xz + yw), 0.0f},
                {2 * (xy + zw), 1 - 2 * (xx + zz), 2 * (yz + xw), 0.0f},
                {2 * (xz - yw), 2 * (yz + xw), 1 - 2 * (xx + yy), 0.0f},
                {0.0f, 0.0f, 0.0f, 1.0f}
        });
    }

    /**
     * @param q
     * @return
     */
    public static Matrix4x4 newRotation(Quaternion q) {
        float sqw = q.getW() * q.getW();
        float sqx = q.getX() * q.getX();
        float sqy = q.getY() * q.getY();
        float sqz = q.getZ() * q.getZ();

        float invs = 1 / (sqx + sqy + sqz + sqw);

        float xy = q.getX() * q.getY();
        float zw = q.getZ() * q.getW();

        float xz = q.getX() * q.getZ();
        float yw = q.getY() * q.getW();

        float yz = q.getY() * q.getZ();
        float xw = q.getX() * q.getW();


        return new Matrix4x4(new float[][]{
                {(sqx - sqy - sqz + sqw) * invs, 2.0f * (xy - zw) * invs, 2.0f * (xz + yw) * invs, 0.0f},
                {2.0f * (xy + zw) * invs * invs, (-sqx + sqy - sqz + sqw) * invs, 2.0f * (yz - xw) * invs, 0.0f},
                {2.0f * (xz - yw) * invs, 2.0f * (yz + xw) * invs, (-sqx - sqy + sqz + sqw) * invs, 0.0f},
                {0.0f, 0.0f, 0.0f, 1.0f}
        });
    }

    public static Matrix4x4 newRotation(Vector3D r) {
        Matrix4x4 rz = Matrix4x4.newZRotation(r.z);
        Matrix4x4 rx = Matrix4x4.newXRotation(r.x);
        Matrix4x4 ry = Matrix4x4.newYRotation(r.y);
        return ry.compose(rx).compose(rz);
    }

    public static Matrix4x4 newIdentityMatrix() {
        return new Matrix4x4(new float[][]{
                {1.0f, 0.0f, 0.0f, 0.0f},
                {0.0f, 1.0f, 0.0f, 0.0f},
                {0.0f, 0.0f, 1.0f, 0.0f},
                {0.0f, 0.0f, 0.0f, 1.0f}
        });

    }

    public Matrix4x4 compose(Matrix4x4 other) {
        float[][] ans = new float[4][4];
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                for (int i = 0; i < 4; i++) {
                    ans[row][col] += this.values[row][i] * other.values[i][col];
                }
            }
        }
        return new Matrix4x4(ans);
    }


    public String toString() {
        StringBuilder ans = new StringBuilder();
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                ans.append(values[row][col]+" ");
            }
            ans.append('\n');
        }
        return ans.toString();
    }
}
