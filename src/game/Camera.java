package game;

public class Camera {

    static int x = 0;
    static int y = 0;
    static int z = 0;

    public static void setLocation(int x, int y, int z) {
        Camera.x = x;
        Camera.y = y;
        Camera.z = z;
    }

    public static void moveLeft(int dx) {
        x -= dx;
    }

    public static void moveRight(int dx) {
        x += dx;
    }

    public static void moveUp(int dy) {
        y -= dy;
    }

    public static void moveDown(int dy) {
        y += dy;
    }
}