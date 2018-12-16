package Rendering.renderUtil.Bitmaps;

import util.Mathf.Mathf3D.Vector3D;


public class ColorBuffer extends BitmapABGR {

    public ColorBuffer(int width, int height) {
        super(width, height);
    }

    public final void setPixelFromBitmap(int x, int y, BitmapABGR bitmapABGR, int u, int v, Vector3D shade) {
        int buffIndex = (x + y * width) << 2;
        int tIndex = (u + v * bitmapABGR.width) << 2;
        byteArray[buffIndex] = bitmapABGR.byteArray[tIndex];//a
        byteArray[buffIndex + 1] = (byte) ((bitmapABGR.byteArray[tIndex + 1] & 0xFF) * shade.z); //b
        byteArray[buffIndex + 2] = (byte) ((bitmapABGR.byteArray[tIndex + 2] & 0xFF) * shade.y); //g
        byteArray[buffIndex + 3] = (byte) ((bitmapABGR.byteArray[tIndex + 3] & 0xFF) * shade.x);//r
    }
}
