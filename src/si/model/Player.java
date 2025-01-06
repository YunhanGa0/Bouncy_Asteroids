package si.model;

import java.awt.*;

public class Player implements Hittable {
    private int x;
    private int y;

    private int angle;
    private Rectangle hitBox;
    private int weaponCountdown;
    private boolean alive = true;
    public static final int SHIP_SCALE = 4;
    private static final int WIDTH = SHIP_SCALE * 8;


    private int currentLevel;

    public Player() {
        x = BouncyAsteroidsGame.SCREEN_WIDTH / 2;
        //y = 450;
        y = BouncyAsteroidsGame.SCREEN_HEIGHT / 2;
        //hitBox = new Rectangle(x, y, 8 * SHIP_SCALE, 5 * SHIP_SCALE);
        hitBox = new Rectangle(x, y, 20, 20);
        hitBox.setFrameFromCenter(x,y,x+10,y+15);
        angle = 0;
    }

    public boolean isHit(Hittable b) {
        Rectangle s = b.getHitBox();
        boolean hit = this.hitBox.intersects(s);
        if (hit) {
            alive = false;
        }
        return hit;
    }

    @Override
    public Rectangle getHitBox() {
        return hitBox;
    }

    public void tick() {
        if (weaponCountdown > 0) {
            weaponCountdown--;
        } else {
            weaponCountdown = 10;
        }
    }

    public boolean isAlive() {
        return alive;
    }

    public void resetDestroyed() {
        alive = true;
        x = BouncyAsteroidsGame.SCREEN_WIDTH / 2;
        y = BouncyAsteroidsGame.SCREEN_HEIGHT / 2;
        //hitBox = new Rectangle(x, y, 8 * SHIP_SCALE, 5 * SHIP_SCALE);
        hitBox = new Rectangle(x, y, 20, 20);
        hitBox.setFrameFromCenter(x,y,x+10,y+15);
    }

    public int getPoints() {
        return -100;
    }

    public boolean isPlayer() {
        return true;
    }

    public Bullet fire() {
        Bullet b = null;
        if (weaponCountdown == 0) {
            //b = new Bullet(x + 3 * SHIP_SCALE, y - 1 * SHIP_SCALE, true, "Player");
            b = new Bullet(x + (int)(Math.cos(Math.toRadians(-90+angle)) * 4) * SHIP_SCALE, y + (int)(Math.sin(Math.toRadians(-90+angle)) * 4) * Player.SHIP_SCALE, true, "Player");
            b.setAngle(this.angle);
        }
        return b;
    }

    public void move(int x1, int y1) {
        Rectangle newBox = new Rectangle(x + x1, y + y1, 20, 20);
        newBox.setFrameFromCenter(x,y,x+10,y+15);
        int x0 = x + x1;
        int y0 = y + y1;
        /*if (SpaceInvadersGame.getScreenBounds().contains(newBox.getBounds())) {
            hitBox = newBox;
            this.x += x1;
            this.y += y1;
        }*/
        if (x > 0 && x < BouncyAsteroidsGame.SCREEN_WIDTH && y > 0 && y < BouncyAsteroidsGame.SCREEN_HEIGHT){
            hitBox = newBox;
            this.x += x1;
            this.y += y1;
        } else if (x0 < 0 || x0 > BouncyAsteroidsGame.SCREEN_WIDTH) {
            if (x0 < 0){
                x = BouncyAsteroidsGame.SCREEN_WIDTH - 5;
            }
            else {
                x = 5;
            }
            hitBox = newBox;
            this.x += x1;
            this.y += y1;
        } else if (y0 < 0 || y0 > BouncyAsteroidsGame.SCREEN_HEIGHT) {
            if (y0 < 0){
                y = BouncyAsteroidsGame.SCREEN_HEIGHT - 5;
            }
            else {
                y = 5;
            }
            hitBox = newBox;
            this.x += x1;
            this.y += y1;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getAngle() {
        return angle;
    }

    public void setAngle(int newAngle) {angle = newAngle;}


    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }
}
