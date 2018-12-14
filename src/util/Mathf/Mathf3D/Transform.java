package util.Mathf.Mathf3D;

import util.Mathf.Mathf3D.Bounds.AABoundingBox;

public class Transform {
    private Quaternion rotation;
    //private Vector3D rotation;
    private Vector3D scale;

    private Vector3D position;

    private Vector3D forwardDir;
    private Vector3D upDir;
    private Vector3D rightDir;

    private AABoundingBox aaBoundingBox;

//    private float fYaw;

    public Transform() {
        this.position = Vector3D.newZeros();
        this.forwardDir = Vector3D.newForward();
        this.upDir = Vector3D.newUp();
        this.rightDir = Vector3D.newRight();
        this.scale = Vector3D.newOnes();
        this.rotation = Quaternion.newIdentity();

        this.aaBoundingBox = AABoundingBox.zeros();

//        this.fYaw = 0.0f;
    }

    public void translate(float x, float y, float z) {
        position.x += x;
        position.y += y;
        position.z += z;
    }

    public void translate(Vector3D position) {
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

    public void rotate(float angle, Vector3D axis) {
        Quaternion newRot = new Quaternion(angle, axis);
        rotate(newRot);
    }

    public void rotate(Quaternion rotation) {
        this.rotation = rotation.multiply(this.rotation);
        updateDirections(rotation);
    }

    public void setRotation(float angle, Vector3D axis) {
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
        Vector3D newS = scale.plus(transform2.scale);
        Vector3D newT = position.plus(transform2.position);

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

    private void setDirections(Quaternion rotation) {
        this.upDir = rotation.rotate(Vector3D.UP);
        this.forwardDir = rotation.rotate(Vector3D.FORWARD);
        this.rightDir = rotation.rotate(Vector3D.RIGHT);
    }

    public void setForwardDir(Vector3D forwardDir) {
        this.forwardDir = forwardDir;
        this.upDir = calcUpFromDir(forwardDir);
        this.rightDir = upDir.crossProduct(forwardDir);
    }

    public void setRightDir(Vector3D rightDir) {
        this.rightDir = rightDir;
        this.upDir = calcUpFromDir(rightDir);
        this.forwardDir = upDir.crossProduct(rightDir);
    }

    public void setUpDir(Vector3D upDir) {
        this.upDir = upDir;
        //TODO: need to test
        this.forwardDir = Vector3D.FORWARD.minus(upDir.mul(Vector3D.FORWARD.dotProduct(upDir)));
        this.rightDir = upDir.crossProduct(forwardDir);
    }

    public Vector3D getForwardDir() {
        return forwardDir;
    }

    public Vector3D getUpDir() {
        return upDir;
    }

    public Vector3D getRightDir() {
        return rightDir;
    }

    public Quaternion getRotation() {
        return rotation;
    }

    public Vector3D getScale() {
        return scale;
    }

    public Vector3D getPosition() {
        return position;
    }

    public void setPosition(float x, float y, float z) {
        this.position.x = x;
        this.position.y = y;
        this.position.z = z;
    }

    public void setPosition(Vector3D position) {
        this.position = position;
    }

    private Vector3D calcUpFromDir(Vector3D dir) {
        return Vector3D.UP.minus(dir.mul(Vector3D.UP.dotProduct(dir)));
    }
}
