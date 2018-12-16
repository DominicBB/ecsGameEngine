package core;

public class BufferManager {
    private Buffer frontBuffer;
    private Buffer backBuffer;

    //TODO: be able to switch between abgr and bgr
    public BufferManager(int width, int height) {
        backBuffer = new Buffer(width, height);
        frontBuffer = new Buffer(width, height);
    }

    public Buffer getFrontBuffer() {
        return frontBuffer;
    }

    public Buffer getBackBuffer() {
        return backBuffer;
    }

    public void swap(){
        Buffer temp = frontBuffer;
        frontBuffer = backBuffer;
        backBuffer = temp;
    }
}
