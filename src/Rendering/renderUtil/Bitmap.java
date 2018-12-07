package Rendering.renderUtil;

import util.Mathf.Mathf3D.Vector3D;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Bitmap {
    protected int width;
    protected int height;

    protected byte[] byteArray;

    public Bitmap(int width, int height) {
        this.width = width;
        this.height = height;
        this.byteArray = new byte[width * height * 4];
    }

    /**
     * loads an image from file, useful for texture mapping
     *
     * @param filename
     * @throws IOException
     */
    public Bitmap(String filename) throws IOException {
        BufferedImage image = ImageIO.read(new File(filename));

        this.width = image.getWidth();
        this.height = image.getHeight();
        byteArray = new byte[width * height * 4];

        int[] pixels = new int[this.width * this.height];
        image.getRGB(0, 0, this.width, this.height, pixels, 0, width);
        int pixel;//RBG int
        int index;

        for (int i = 0; i < this.width * this.height; i++) {
            pixel = pixels[i];
            index = i << 2;
            byteArray[index] = (byte) ((pixel >> 24) & 0xFF); // A
            byteArray[index + 1] = (byte) ((pixel) & 0xFF); // B
            byteArray[index + 2] = (byte) ((pixel >> 8) & 0xFF); // G
            byteArray[index + 3] = (byte) ((pixel >> 16) & 0xFF); // R
        }
    }

    /**
     * Set ABGR of pixel
     *
     * @param x
     * @param y
     * @param color
     */
    public void setPixel(int x, int y, Vector3D color) {
        setPixel(x, y,
                (byte) (((int) color.w >> 24) & 0xFF),
                (byte) (((int) color.z) & 0xFF),
                (byte) (((int) color.y >> 8) & 0xFF),
                (byte) (((int) color.x >> 16) & 0xFF)
        );
    }

    /**
     * Set ARGB of pixel
     *
     * @param x
     * @param y
     * @param a
     * @param r
     * @param g
     * @param b
     */
    public void setPixel(int x, int y, byte a, byte r, byte g, byte b) {
        int i = (x + y * width) << 2;
        byteArray[i] = a;
        byteArray[i + 1] = b;
        byteArray[i + 2] = g;
        byteArray[i + 3] = r;
    }

    /**
     * set all elements in byteArray to shadeWhiteLight of grey
     *
     * @param shade
     */
    public void clear(byte shade) {
        Arrays.fill(byteArray, shade);
    }

    /**
     * converts byteArray into 3byte BGR buffer
     *
     * @param destination
     */
    public void copyTo3BGR(byte[] destination) {
        int length = width * height;
        int indexByteAr;
        int indexDest;
        for (int i = 0; i < length; i++) {
            indexByteAr = i << 2;
            indexDest = i * 3;

            destination[indexDest] = byteArray[indexByteAr + 1];
            destination[indexDest + 1] = byteArray[indexByteAr + 2];
            destination[indexDest + 2] = byteArray[indexByteAr + 3];
        }
    }

    //TODO: does this work
    public Vector3D getPixelColor(int x, int y) {
        int i = (y * width + x) << 2;
        return new Vector3D(
                byteArray[i + 3] & 0xFF,
                byteArray[i + 2] & 0xFF,
                byteArray[i + 1] & 0xFF,
                byteArray[i] & 0xFF
        );
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
