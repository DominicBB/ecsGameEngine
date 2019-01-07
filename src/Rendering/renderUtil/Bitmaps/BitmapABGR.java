package Rendering.renderUtil.Bitmaps;

import util.Mathf.Mathf3D.Vec4f;
import util.Mathf.Mathf3D.Vec4fi;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class BitmapABGR {
    protected final int width;
    protected final int height;

    protected final byte[] byteArray;

    public BitmapABGR(int width, int height) {
        this.width = width;
        this.height = height;
        this.byteArray = new byte[width * height * 4];
    }

    public BitmapABGR(int width, int height, byte[] byteArray) {
        this.width = width;
        this.height = height;
        if (byteArray.length != width * height << 2)
            this.byteArray = Arrays.copyOf(byteArray, width * height << 2);
        else
            this.byteArray = byteArray;
    }

    /**
     * loads an image from file
     *
     * @param filename
     * @throws IOException
     */
    public BitmapABGR(String filename) throws IOException {
        BufferedImage image = ImageIO.read(new File(filename));

        this.width = image.getWidth();
        this.height = image.getHeight();
        byteArray = new byte[width * height * 4];

        int[] pixels = new int[this.width * this.height];
        image.getRGB(0, 0, this.width, this.height, pixels, 0, width);
        int pixel;//ARGB int
        int index;
        for (int i = 0, len = this.width * this.height; i < len; i++) {
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
    public void setPixel(int x, int y, Vec4f color) {
        setPixel(x, y,
                (byte) (((int) color.w) & 0xFF),
                (byte) (((int) color.x) & 0xFF),
                (byte) (((int) color.y) & 0xFF),
                (byte) (((int) color.z) & 0xFF)
        );
    }

    /**
     * Set ABGR of pixel
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
     * set_unsafe all elements in byteArray to shadeWhiteLight of grey
     *
     * @param shade
     */
    public void clear(byte shade) {
        for (int i = 0, len = byteArray.length; i < len; i++) {
            byteArray[i] = shade;
        }
    }

    private static final byte black = (byte) 0;

    /**
     * set_unsafe all elements in byteArray to shadeWhiteLight of grey
     */
    public void clearToBlack() {
        clear(black);
    }

    /**
     * converts byteArray into 3byte BGR buffer
     *
     * @param destination
     */
    public void copyToBGR(byte[] destination) {
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

    public void copyToABGR(byte[] destination) {
        for (int i = 0; i < byteArray.length; i++) {
            destination[i] = byteArray[i];
        }
    }

    public Vec4f getPixel(int x, int y) {
        int i = (y * width + x) << 2;
        return new Vec4f(
                byteArray[i + 3] & 0xFF,
                byteArray[i + 2] & 0xFF,
                byteArray[i + 1] & 0xFF,
                byteArray[i] & 0xFF
        );
    }

    public Vec4fi getPixel_vInt(int x, int y, int D_SHIFT) {
        int i = (y * width + x) << 2;
        return new Vec4fi(
                (byteArray[i + 3] & 0xFF) << D_SHIFT,
                (byteArray[i + 2] & 0xFF) << D_SHIFT,
                (byteArray[i + 1] & 0xFF) << D_SHIFT,
                (byteArray[i] & 0xFF) << D_SHIFT,
                D_SHIFT
        );
    }

    public void getPixelNonAlloc(int x, int y, Vec4f out) {
        int i = (y * width + x) << 2;
        out.x = byteArray[i + 3] & 0xFF;
        out.y = byteArray[i + 2] & 0xFF;
        out.z = byteArray[i + 1] & 0xFF;
        out.w = byteArray[i] & 0xFF;
    }

    public void getPixelNonAlloc_vInt(int x, int y, Vec4fi out) {
        int i = (y * width + x) << 2;
        out.x = (byteArray[i + 3] & 0xFF) << out.D_SHIFT;
        out.y = (byteArray[i + 2] & 0xFF) << out.D_SHIFT;
        out.z = (byteArray[i + 1] & 0xFF) << out.D_SHIFT;
        out.w = (byteArray[i] & 0xFF) << out.D_SHIFT;
    }

    public void getPixelNonAlloc_vInt_noshift(int x, int y, Vec4fi out) {
        int i = (y * width + x) << 2;
        out.x = (byteArray[i + 3] & 0xFF);
        out.y = (byteArray[i + 2] & 0xFF);
        out.z = (byteArray[i + 1] & 0xFF);
        out.w = (byteArray[i] & 0xFF);
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
