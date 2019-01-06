package util.Mathf.Mathf3D;

import util.Mathf.Mathf3D.Bounds.AABoundingBox;

public class Transform {
    private Quaternion rotation;
    //private Vec4f rotation;
    private Vec4f scale;

    private Vec4f position;

    private Vec4f forwardDir;
    private Vec4f upDir;
    private Vec4f rightDir;

    private AABoundingBox aaBoundingBox;

//    private float fYaw;

    public Transform() {
        this.position = Vec4f.newZeros();
        this.forwardDir = Vec4f.newForward();
        this.upDir = Vec4f.newUp();
        this.rightDir = Vec4f.newRight();
        this.scale = Vec4f.newOnes();
        this.rotation = Quaternion.newIdentity();

        this.aaBoundingBox = AABoundingBox.zeros();

//        this.fYaw = 0.0f;
    }

    public void translate(float x, float y, float z) {
        position.x += x;
        position.y += y;
        position.z += z;
    }

    public void translate(Vec4f position) {
        this.position.add(position);
    }

    public void scale(float x, float y, float z) {
        scale.x += x;
        scale.y += y;
        scale.z += z;
    }

    public void setScale(float x, float y, float z) {
        scale.x = x;
        scale.y = y;
        scale.z = z;
    }

    /**
     *
     * @param angle in radians
     * @param axis of rotation
     */
    public void rotate(float angle, Vec4f axis) {
        Quaternion newRot = new Quaternion(angle, axis);
        rotate(newRot);
    }

    public void rotate(Quaternion rotation) {
        this.rotation = rotation.multiply(this.rotation);
        updateDirections(rotation);
    }

    public void setRotation(float angle, Vec4f axis) {
        Quaternion newRot = new Quaternion(angle, axis);
        setRotation(newRot);
    }

    public void setRotation(Quaternion rotation) {
        this.rotation = rotation;
        setDirections(rotation);
    }


    public Matrix4x4 compose() {
        return Matrix4x4.newRotation(rotation).compose(Matrix4x4.newScale(scale)).
                compose(Matrix4x4.newTranslation(position));
        /*return Matrix4x4.newTranslation(position).compose(Matrix4x4.newScale(scale)).
                compose(Matrix4x4.newRotation(rotation));*/
    }


    public Matrix4x4 composeWith(Transform transform2) {
        Quaternion newR = rotation.multiply(transform2.rotation);
        Vec4f newS = scale.plus(transform2.scale);
        Vec4f newT = position.plus(transform2.position);

        Matrix4x4 rot = Matrix4x4.newRotation(newR);
        Matrix4x4 compsedScale = Matrix4x4.newScale(newS);
        Matrix4x4 composedTranslation = Matrix4x4.newTranslation(newT);

        return rot.compose(compsedScale).compose(composedTranslation);
        // return composedTranslation.compose(compsedScale).compose(rot);
    }

    private void updateDirections(Quaternion rotation) {
        this.upDir = rotation.rotate(upDir);
        this.forwardDir = rotation.rotate(forwardDir);
        this.rightDir = rotation.rotate(rightDir);
    }

    /**
     * relative to Vec4f.UP,Vec4f.FORWARD,Vec4f.RIGHT
     * @param rotation
     */
    private void setDirections(Quaternion rotation) {
        this.upDir = rotation.rotate(Vec4f.UP);
        this.forwardDir = rotation.rotate(Vec4f.FORWARD);
        this.rightDir = rotation.rotate(Vec4f.RIGHT);
    }

    public void setForwardDir(Vec4f forwardDir) {
        this.forwardDir = forwardDir;
        this.upDir = calcUpFromDir(forwardDir);
        this.rightDir = upDir.crossProduct(forwardDir);
    }

    public void setRightDir(Vec4f rightDir) {
        this.rightDir = rightDir;
        this.upDir = calcUpFromDir(rightDir);
        this.forwardDir = upDir.crossProduct(rightDir);
    }

    public void setUpDir(Vec4f upDir) {
        this.upDir = upDir;
        //TODO: need to test
        this.forwardDir = Vec4f.FORWARD.minus(upDir.mul(Vec4f.FORWARD.dotProduct(upDir)));
        this.rightDir = upDir.crossProduct(forwardDir);
    }

    public Vec4f getForwardDir() {
        return forwardDir;
    }

    public Vec4f getUpDir() {
        return upDir;
    }

    public Vec4f getRightDir() {
        return rightDir;
    }

    public Quaternion getRotation() {
        return rotation;
    }

    public Vec4f getScale() {
        return scale;
    }

    public Vec4f getPosition() {
        return position;
    }

    public void setPosition(float x, float y, float z) {
        this.position.x = x;
        this.position.y = y;
        this.position.z = z;
    }

    public void setPosition(Vec4f position) {
        this.position = position;
    }

    private Vec4f calcUpFromDir(Vec4f dir) {
        return Vec4f.UP.minus(dir.mul(Vec4f.UP.dotProduct(dir)));
    }
}
