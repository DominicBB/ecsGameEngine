package Rendering.renderUtil.Bitmaps;

import util.Mathf.Mathf3D.Vector3D;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class BitmapBGR {

    protected final int width;
    protected final int height;

    protected final byte[] byteArray;

    public BitmapBGR(int width, int height) {
        this.width = width;
        this.height = height;
        this.byteArray = new byte[width * height * 3];
    }

    public BitmapBGR(int width, int height, byte[] byteArray) {
        this.width = width;
        this.height = height;
        if (byteArray.length != width * height * 3)
            this.byteArray = Arrays.copyOf(byteArray, width * height * 3);
        else
            this.byteArray = byteArray;
    }

    /**
     * loads an image from file
     *
     * @param filename
     * @throws IOException
     */
    public BitmapBGR(String filename) throws IOException {
        BufferedImage image = ImageIO.read(new File(filename));

        this.width = image.getWidth();
        this.height = image.getHeight();
        byteArray = new byte[width * height * 3];

        int[] pixels = new int[this.width * this.height];
        image.getRGB(0, 0, this.width, this.height, pixels, 0, width);

        int pixel;//RBG int
        int index;
        int end = this.width * this.height;

        for (int i = 0; i < end; i++) {
            pixel = pixels[i];
            index = i * 3;
            byteArray[index] = (byte) ((pixel) & 0xFF); // B
            byteArray[index + 1] = (byte) ((pixel >> 8) & 0xFF); // G
            byteArray[index + 2] = (byte) ((pixel >> 16) & 0xFF); // R
        }
    }

    /**
     * Set BGR of pixel
     *
     * @param x
     * @param y
     * @param color
     */
    public void setPixel(int x, int y, Vector3D color) {
        setPixel(x, y,
                (byte) (((int) color.x) & 0xFF),
                (byte) (((int) color.y) & 0xFF),
                (byte) (((int) color.z) & 0xFF)
        );
    }

    /**
     * Set BGR of pixel
     *
     * @param x
     * @param y
     * @param r
     * @param g
     * @param b
     */
    public void setPixel(int x, int y, byte r, byte g, byte b) {
        int i = (x + y * width) * 3;
        byteArray[i] = b;
        byteArray[i + 1] = g;
        byteArray[i + 2] = r;
    }

    /**
     * set all elements in byteArray to shadeWhiteLight of grey
     *
     * @param shade
     */
    public void clear(byte shade) {
        for (int i = 0; i < byteArray.length; i++) {
            byteArray[i] = shade;
        }
    }

    private static final byte black = (byte) 0;

    /**
     * set all elements in byteArray to shadeWhiteLight of grey
     */
    public void clearToBlack() {
        for (int i = 0; i < byteArray.length; i++) {
            byteArray[i] = black;
        }
    }

    /**
     * converts byteArray into 3byte BGR buffer
     *
     * @param destination
     */
    public void copyTo(byte[] destination) {
        int length = width * height * 3;
        for (int i = 0; i < length; i++) {
            destination[i] = byteArray[i];
        }
    }


    public Vector3D getPixel(int x, int y) {
        int i = (y * width + x) * 3;
        return new Vector3D(
                byteArray[i + 2] & 0xFF,
                byteArray[i + 1] & 0xFF,
                byteArray[i] & 0xFF,
                255f
        );
    }

    public void getPixelNonAlloc(int x, int y, Vector3D out) {
        int i = (y * width + x) * 3;
        out.x = byteArray[i + 2] & 0xFF;
        out.y = byteArray[i + 1] & 0xFF;
        out.z = byteArray[i] & 0xFF;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public byte[] getByteArray() {
        return byteArray;
    }
}
