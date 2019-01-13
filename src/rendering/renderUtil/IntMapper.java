package rendering.renderUtil;

@FunctionalInterface
public interface IntMapper {
    int map(int c);


}

final class MapToSelf implements IntMapper {
    @Override
    public int map(int c) {
        return c;
    }
}

final class MapTo255 implements IntMapper {
    @Override
    public int map(int c) {
        return 255;
    }
}
