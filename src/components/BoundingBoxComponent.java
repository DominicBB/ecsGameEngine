package components;

import util.geometry.MinimumBoundingBox;

public class BoundingBoxComponent {
    public MinimumBoundingBox minimumBoundingBox;

    public BoundingBoxComponent(MinimumBoundingBox minimumBoundingBox){
       this.minimumBoundingBox = minimumBoundingBox;
    }
}
