package util.Mathf.Mathf3D;

import util.Mathf.Mathf3D.Bounds.AABoundingBox;

public class Transform {
    public Quaternion qRotation;
    public Vector3D rotation;
    public Vector3D scale;
    public Vector3D translation;

    public Vector3D position;

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
        this.translation = Vector3D.newZeros();
//        this.qRotation =
        this.rotation = Vector3D.newZeros();

        this.aaBoundingBox = AABoundingBox.zeros();

//        this.fYaw = 0.0f;
    }

    public void translate(float x, float y, float z) {
        translation.x = x;
        translation.y = y;
        translation.z = z;
    }

    public void setScale(float x, float y, float z) {
        scale.x = x;
        scale.y = y;
        scale.z = z;
    }

    public void rotate(float x, float y, float z) {
        rotation.x += x;
        rotation.y += y;
        rotation.z += z;
    }

    public void setRotation(float x, float y, float z) {
        rotation.x = x;
        rotation.y = y;
        rotation.z = z;
    }


    public Matrix4x4 compose() {
        return Matrix4x4.newTranslation(translation).compose(Matrix4x4.newScale(scale)).
                compose(Matrix4x4.newRotation(rotation));
        //TODO why do I have to keep changing the order
//		return composedRotatation().compose(transform.scaleM.compose(transform.translationM));

    }


    public Matrix4x4 composeWith(Transform transform2) {
        Vector3D newR = rotation.plus(transform2.rotation);
        Vector3D newS = scale.plus(transform2.scale);
        Vector3D newT = translation.plus(transform2.translation);

        Matrix4x4 rot = Matrix4x4.newRotation(newR);
        Matrix4x4 compsedScale = Matrix4x4.newScale(newS);
        Matrix4x4 composedTranslation = Matrix4x4.newTranslation(newT);

        return rot.compose(compsedScale).compose(composedTranslation);
        // return composedTranslation.compose(compsedScale).compose(rot);

    }

    public void updateDirections(Matrix4x4 rotation) {
        this.upDir = rotation.multiply4x4(upDir);
        this.forwardDir = rotation.multiply4x4(forwardDir);
        this.rightDir = rotation.multiply4x4(rightDir);
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


    private Vector3D calcUpFromDir(Vector3D dir) {
        return Vector3D.UP.minus(dir.mul(Vector3D.UP.dotProduct(dir)));
    }
}
