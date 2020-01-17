package ru.lokincompany.lokengine.tools;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import ru.lokincompany.lokengine.render.Camera;

public class MatrixTools {

    public static double radiansToDegrees(float radians) {
        return ((radians / 3.14159265358979323846) * 180.0f);
    }

    public static double degressToRadians(float degress) {
        return (degress * (3.14159265358979323846 / 180.0f));
    }

    public static Matrix4f toOrtho(Matrix4f m, float left, float right, float bottom, float top, float near, float far) {
        if (m == null)
            m = new Matrix4f();
        float x_orth = 2 / (right - left);
        float y_orth = 2 / (top - bottom);
        float z_orth = -2 / (far - near);

        float tx = -(right + left) / (right - left);
        float ty = -(top + bottom) / (top - bottom);
        float tz = -(far + near) / (far - near);

        m.m00 = x_orth;
        m.m10 = 0;
        m.m20 = 0;
        m.m30 = 0;
        m.m01 = 0;
        m.m11 = y_orth;
        m.m21 = 0;
        m.m31 = 0;
        m.m02 = 0;
        m.m12 = 0;
        m.m22 = z_orth;
        m.m32 = 0;
        m.m03 = tx;
        m.m13 = ty;
        m.m23 = tz;
        m.m33 = 1;
        return m;
    }

    public static Matrix4f createOrthoMatrix(float width, float height) {
        return toOrtho(null, -width / 2, width / 2, -height / 2, height / 2, -1.0f, 1000.0f);
    }

    public static Matrix4f createViewMatrix(Camera camera) {
        Matrix4f viewMatrix = new Matrix4f();
        viewMatrix.setIdentity();

        Matrix4f.rotate((float) degressToRadians(camera.rollRotation), new Vector3f(0, 0, 1), viewMatrix, viewMatrix);
        Matrix4f.translate(new Vector3f(-camera.position.x, -camera.position.y, -500), viewMatrix, viewMatrix);
        return viewMatrix;
    }

    public static Matrix4f createModelMatrix(float angle, Vector3f pos) {
        Matrix4f matrix = new Matrix4f();
        matrix.setIdentity();
        Matrix4f.translate(pos, matrix, matrix);
        Matrix4f.rotate((float) degressToRadians(angle), new Vector3f(0, 0, 1), matrix, matrix);
        return matrix;
    }
}
