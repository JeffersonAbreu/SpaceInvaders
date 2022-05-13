package control;

public class UserControls {
    private static boolean left;
    private static boolean right;
    private static boolean up;
    private static boolean down;
    private static boolean shot;

    public static boolean isLeft() {
        return left;
    }
    public static void setLeft(boolean left) {
        UserControls.left = left;
    }
    public static boolean isRight() {
        return right;
    }
    public static void setRight(boolean right) {
        UserControls.right = right;
    }
    public static boolean isUp() {
        return up;
    }
    public static void setUp(boolean up) {
        UserControls.up = up;
    }
    public static boolean isDown() {
        return down;
    }
    public static void setDown(boolean down) {
        UserControls.down = down;
    }
    public static boolean isShot() {
        return shot;
    }
    public static void setShot(boolean shot) {
        UserControls.shot = shot;
    }    
}
