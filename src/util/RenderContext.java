package util;

import util.geometry.Vector3D;

public class RenderContext extends Bitmap {


    public RenderContext(int width, int height) {
        super(width, height);
    }

    public void setPixelFromTexture(int x, int y, Bitmap texture, int u, int v, Vector3D shade) {
        int buffIndex = (x + y * width) << 2;
        int tIndex = (u + v * texture.width) << 2;
        byteArray[buffIndex] = texture.byteArray[tIndex];//a
        byteArray[buffIndex + 1] = (byte) ((texture.byteArray[tIndex + 1] & 0xFF) * shade.z); //b
        byteArray[buffIndex + 2] = (byte) ((texture.byteArray[tIndex + 2] & 0xFF) *shade.y); //g
        byteArray[buffIndex + 3] = (byte) ((texture.byteArray[tIndex + 3] & 0xFF)* shade.x);//r
    }

}
