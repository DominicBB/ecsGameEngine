package util.geometry;

import components.Camera;
import components.Scene;
import components.Transform;
import core.Window;

import java.util.ArrayList;
import java.util.List;

public class Pipeline {

    public static boolean isHidden(Triangle t, Scene s, Camera c) {
//		return t.normal.dotProduct(s.getActiveCamera().lookDir) > 0;
//		System.out.println(t.vectors[0].minus(s.getActiveCamera().position).unit());
        return /*t.normal.dotProduct(s.getActiveCamera().lookDir) > 0 && */t.normal.dotProduct(t.vectors[0].minus(c.position).unit()) > 0;
    }

    public static Triangle orderCW(Triangle t){

        if(t.normal.z < 0){
           return t;
        }
        Vector3D temp = t.vectors[1];
        t.vectors[1] = t.vectors[2];
        t.vectors[2] = temp;

        Vector2D temp2 = t.textures[1];
        t.textures[1] = t.textures[2];
        t.textures[2] = temp2;

        t.normal = t.normal();

        return t;


    }


    public static Vector3D[] calculateShadingGradient(Triangle t, Scene s, Camera c) {
        float cosTheta = (t.normal.dotProduct(c.position.minus(t.vectors[0]).unit()));
        Vector3D[] newColors = new Vector3D[t.colors.length];

        for (int i = 0; i < t.colors.length; i++) {
            Vector3D color = new Vector3D(t.colors[i].w);
            color.x = (int) (s.ambientLight.getRed() * t.colors[i].x + s.lightColor.getRed() /** 0.5 */
                    * t.colors[i].x * cosTheta);

            color.y = (int) (s.ambientLight.getGreen() * t.colors[i].y + s.lightColor.getGreen() /** 0.5 */
                    * t.colors[i].y * cosTheta);

            color.z = (int) (s.ambientLight.getBlue() * t.colors[i].z + s.lightColor.getBlue() /** 0.5 */
                    * t.colors[i].z * cosTheta);
            newColors[i] = color;
        }

        return newColors;
    }

    public static Vector3D calculateShade(Triangle t, Scene s, Camera c) {
        float cosTheta = (t.normal.dotProduct(c.position.minus(t.vectors[0]).unit()));
        Vector3D shade = new Vector3D(1);
        shade.x = s.lightColor.getRed() * cosTheta;

        shade.y = s.lightColor.getGreen() * cosTheta;

        shade.z = s.lightColor.getBlue() * cosTheta;
        return shade;
    }

    public static Triangle shadeTriangle(Triangle t, Scene s, Camera c) {
//        t.colors = calculateShadingGradient(t, s, c);
        t.setShade(calculateShade(t, s, c));
        return t;
    }



}
