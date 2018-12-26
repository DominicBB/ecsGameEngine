package core.display;

import Rendering.renderUtil.Bitmaps.BitmapBGR;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

public class Buffer {
    public final BufferedImage bufferedImage;
    public final BitmapBGR bitmapBGR;

    public Buffer(int width, int height) {
        bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        bitmapBGR = new BitmapBGR(width, height, ((DataBufferByte) bufferedImage.getRaster().getDataBuffer()).getData());
    }
}
