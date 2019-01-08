package util.Mathf.Mathf3D;

import Rendering.renderUtil.Vertex;
import Rendering.renderUtil.VertexOut;
import util.Mathf.Mathf2D.Vec2f;

import java.util.Arrays;

public class Triangle extends Polygon {

    public Triangle(Vec4f[] vs, Vec2f[] ts) {
        vectors = Arrays.copyOf(vs, 3);
        colors = new Vec4f[3];
        textures = new Vec2f[3];

        if (ts != null) {
            textures[0] = ts[0];
            textures[1] = ts[1];
            textures[2] = ts[2];

        }

        colors[0] = new Vec4f(0f, 255f, 0f);
        colors[1] = new Vec4f(0f, 0f, 255f);
        colors[2] = new Vec4f(255f, 0f, 0f);


//		reflectance = new Color(85, 146, 127);
/*
        reflectance = Color.magenta;
*/
//		shadedColor = reflectance;
        normal = normal();
    }

    public Triangle(Vec4f[] vs, Vec2f[] ts, Polygon p) {
        vectors = vs;
        textures = ts;

        this.shade = p.shade;

        normal = normal();
    }

    public Triangle(float x1, float y1, float z1, float x2, float y2, float z2, float x3, float y3, float z3) {
        vectors = new Vec4f[3];
        vectors[0] = new Vec4f(x1, y1, z1);
        vectors[1] = new Vec4f(x2, y2, z2);
        vectors[2] = new Vec4f(x3, y3, z3);
/*
        reflectance = Color.CYAN;
*/
        normal = normal();
    }


    public Triangle(Vec4f v1, Vec4f v2, Vec4f v3, Polygon p) {
        vectors = new Vec4f[3];
        textures = new Vec2f[3];

        vectors[0] = v1;
        vectors[1] = v2;
        vectors[2] = v3;

        textures[0] = p.textures[0];
        textures[1] = p.textures[1];
        textures[2] = p.textures[2];


        this.shade = p.shade;

        normal = normal();
    }

    public Triangle(Vec4f v1, Vec4f v2, Vec4f v3, Vec2f t1, Vec2f t2, Vec2f t3, Polygon p) {
        vectors = new Vec4f[3];
        textures = new Vec2f[3];

        vectors[0] = v1;
        vectors[1] = v2;
        vectors[2] = v3;

        textures[0] = p.textures[0];
        textures[1] = p.textures[1];
        textures[2] = p.textures[2];

        this.shade = p.shade;

        this.normal = normal();
    }

    public Triangle(Triangle t) {
        vectors = t.vectors;
        textures = t.textures;

        this.shade = t.shade;

        normal = normal();
    }


    @Override
    public Vec4f normal() {
        return vectors[1].minus(vectors[0]).crossProduct(vectors[2].minus(vectors[0])).normal();
    }

    public static VertexOut[] orderCW(VertexOut[] vertices) {
        float z_n = z_crossProd(vertices[0].p_proj, vertices[1].p_proj, vertices[2].p_proj);
        if (z_n < 0) {
            return vertices;
        }

        VertexOut temp = vertices[1];
        vertices[1] = vertices[2];
        vertices[2] = temp;

        VertexOut temp2 = vertices[1];
        vertices[1] = vertices[2];
        vertices[2] = temp2;

        return vertices;
    }

    public static float z_crossProd(Vec4f v1, Vec4f v2, Vec4f v3) {
        float e1X = v2.x - v1.x;
        float e1Y = v2.y - v1.y;

        float e2X = v3.x - v1.x;
        float e2Y = v3.y - v1.y;

        return e1X * e2Y - e2X * e1Y;
    }

    public static Vec4f normal(Vertex[] vertices) {
        return ((vertices[1].vec.minus(vertices[0].vec)).crossProduct(vertices[2].vec.minus(vertices[0].vec))).normal();
    }

    public static Vec4f normal(VertexOut[] vertices) {
//        return ((vertices[1].p_proj.minus(vertices[0].p_proj)).crossProduct(vertices[2].p_proj.minus(vertices[0].p_proj))).normal();
        return ((vertices[1].p_ws.minus(vertices[0].p_ws)).crossProduct(vertices[2].p_ws.minus(vertices[0].p_ws))).normal();
    }

    public static Vec4f normal(VertexOut v1, VertexOut v2, VertexOut v3) {
        return ((v2.p_ws.minus(v1.p_ws)).crossProduct(v3.p_ws.minus(v1.p_ws))).normal();
    }
}

