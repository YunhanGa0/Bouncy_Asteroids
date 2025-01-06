package si.model;

import java.awt.*;
import java.util.Random;

public class EnemyShip implements Hittable {
    private boolean alive;
    private double x, y;
    private AlienType type;
    private Random rand;
    public static final int SHIP_SCALE = 2;
    public static final int SHIP_SCALE_D = 4;
    private boolean directionX = true; // true for moving right false for left
    private boolean directionY= true; // true for moving up false for down
    Player player;


    private double angle ; //using by enemy ship fire


    public EnemyShip(int x, int y, AlienType type) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.rand = new Random(x * 100 + y);
        this.alive = true;
        this.directionX = new Random().nextBoolean();
        this.directionY = new Random().nextBoolean();
        this.player = new Player();
        this.angle = 0;
    }



    public boolean isHit(Hittable h) {
        if (h instanceof Bullet){
            if (((Bullet) h).getName().contains("EnemyShip")){
                return false;
            }
        }

        boolean hit = getHitBox().intersects(h.getHitBox());
        if (hit) {
            alive = false;
        }
        return hit;
    }

    public void setAlive(boolean a){
        alive = a;
    }


    @Override
    public Rectangle getHitBox() {
        //return new Rectangle((int) x, (int) y, SHIP_SCALE * type.getWidth(), SHIP_SCALE * type.getHeight());
        if (type == AlienType.D || type == AlienType.E){
            return new Rectangle((int) x, (int) y, type.getWidth(), type.getHeight());
        } else {
            return new Rectangle((int) x - 20, (int) y - 20, type.getWidth(), type.getHeight());
        }
    }

    public Rectangle getHitBoxE() {
        return new Rectangle((int) x, (int) y, type.getWidth(), type.getHeight());
    }

    public boolean isAlive() {
        return alive;
    }

    public int getPoints() {
        return type.getScore();
    }

    public boolean isPlayer() {
        return false;
    }



    public void move(double moveX, double moveY) {
        //x += cX;
        //y += cY;
        double cX = ((directionX) ? moveX : -moveX);
        double cY = ((directionY) ? moveY : -moveY);
        if (x + cX > BouncyAsteroidsGame.SCREEN_WIDTH -30 || x + cX  < 30) {
            directionX = !directionX;
            cX = ((directionX) ? moveX : -moveX);
            if (directionX) {
                moveX += 0.25;
            }
        } else if (y + cY > BouncyAsteroidsGame.SCREEN_HEIGHT -30 || y + cY <30) {
            directionY = !directionY;
            cY = ((directionY) ? moveY : -moveY);
            if (directionY) {
                moveY += 0.25;
            }
        } else {
            double typeSpeed = 1;
            if (type == AlienType.A){typeSpeed = 0.5;}
            else if (type == AlienType.B) {typeSpeed = 1.2;}
            else if (type == AlienType.C) {typeSpeed = 2;}
            y = y + typeSpeed*cY;
            x = x + typeSpeed*cX;
        }
    }

    public Bullet fire() {
        Bullet bul = null;
        if (type == AlienType.D){
            if (rand.nextInt() % 80 == 0) {
                int a = (int) x + 20;//+ (type.getWidth() * SHIP_SCALE) / 2);
                int b = (int) y - 30; //+ (SHIP_SCALE * type.getHeight());
                bul = new Bullet(a, b, false, "EnemyShip");

                bul.setAngle(this.angle);
            }
        } else if (type == AlienType.E) {
            if (rand.nextInt() % 30 == 0) {
                int a = (int) x + 20;//+ (type.getWidth() * SHIP_SCALE) / 2);
                int b = (int) y; //+ (SHIP_SCALE * type.getHeight());
                bul = new Bullet(a, b, false, "EnemyShip");

                bul.setAngle(this.angle);
            }
        }
        return bul;
    }

    public Shape getShape() {
        return new Rectangle();
    }

    public int getX() {
        return (int) x;
    }

    public int getY() {
        return (int) y;
    }

    public AlienType getType() {
        return type;
    }


    public double getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

}
