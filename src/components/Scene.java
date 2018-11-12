package components;

import java.awt.*;

public class Scene extends Component {
    public int height;
    public int width;
    public float aRatio /* = height/width */;

    //	public Vector3D lightDir;
    public Color lightColor;
    public Color ambientLight;

    public Scene(int height, int width){
       testScene(height, width);
    }
    public void testScene(int height, int width) {
        this.height = height;
        this.width = width;
        aRatio = height / width;

//		lightDir = new Vector3D(0.0f, 0.0f, -1.0f);
        lightColor = new Color(1, 1, 1);//white
        ambientLight = new Color(0, 0, 0);//none

    }

}
