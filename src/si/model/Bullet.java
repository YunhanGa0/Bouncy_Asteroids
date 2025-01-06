package si.model;

import java.awt.*;

public class Bullet implements Movable, Hittable {
    private int x, y;
    private boolean direction; // True for up, false for down
    private boolean alive = true;
    private Rectangle hitBox;
    private String name;
    private static int bulletCounter = 0;
    public static final int BULLET_HEIGHT = 8;
    public static final int BULLET_WIDTH = 4;
    private static final int BULLET_SPEED = 5;
    //private Player player;
    private double angle;

    public Bullet(int x, int y, boolean direction, String name) {
        this.direction = direction;
        this.x = x;
        this.y = y;
        this.name = name + " " + bulletCounter++;
        hitBox = new Rectangle(x, y, BULLET_WIDTH, BULLET_HEIGHT);
    }
    public void move() {
        /*if (direction) {
            y -= BULLET_SPEED;
        } else {
            y += BULLET_SPEED;
        }*/
        //int x1 = (int)(Math.sin(Math.toRadians(-player.getAngle()))*100)/20;
        //int y1 = (int)(Math.cos(Math.toRadians(-player.getAngle()))*100)/20;
        if (this.name.contains("Player")){
            int x1 = (int)(Math.sin(Math.toRadians(-angle))*100)/20;
            int y1 = (int)(Math.cos(Math.toRadians(-angle))*100)/20;
            x= x - x1;
            y = y - y1;
        } else {
            int x1 = (int)(Math.sin(Math.toRadians(-angle-90))*100)/20;
            int y1 = (int)(Math.cos(Math.toRadians(-angle-90))*100)/20;
            x= x - x1;
            y = y - y1;
        }


        hitBox.setLocation(x, y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    //public void setPlayer(Player player){this.player = player;}

    public void setAngle(double angle){
        this.angle = angle;
    }
    public boolean isHit(Hittable b) {
        if (b instanceof EnemyShip){
            if (this.name.contains("EnemyShip")){
                return false;
            }
        }
        boolean hit = getHitBox().intersects(b.getHitBox());
        if (hit) {
            alive = false;
        }
        return hit;
    }

    public Rectangle getHitBox() {
        return new Rectangle(hitBox);
    }

    public String getName(){
        return name;
    }

    public boolean isAlive() {
        return alive;
    }

    public int getPoints() {
        return 0;
    }

    public boolean isPlayer() {
        return false;
    }

    public void destroy() {
        alive = false;
    }

}
