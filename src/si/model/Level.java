package si.model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Level {
    private Swarm swarm;
    private double startingSpeed;
    private int rows;
    private int cols;
    private BouncyAsteroidsGame game;

    public Level(double ss, int row, int col, BouncyAsteroidsGame g) {
        game = g;
        startingSpeed = ss;
        rows = row;
        cols = col;
        reset();
    }

    public int getShipsRemaining() {
        return swarm.getShipsRemaining();
    }

    /*public int getBottomY() {
        return swarm.getBottomY();
    }*/

    public List<Hittable> getHittable() {
        List<Hittable> targets = new ArrayList<Hittable>();
        targets.addAll(swarm.getHittable());
/*        for (int i = 0; i < bunkers.length; i++) {
            targets.add(bunkers[i]);
        }*/
        return targets;
    }

    public List<Bullet> move() {
        swarm.move();
        //List<EnemyShip> ships = swarm.getBottom();
        List<EnemyShip> ships = swarm.getCurEnemyShips();
        List<Bullet> eBullets = new ArrayList<Bullet>();
        for (EnemyShip s : ships) {
            Bullet b = s.fire();
            if (b != null) {
                eBullets.add(b);
            }
        }
        return eBullets;
    }
    public List<EnemyShip> getEnemyShips() {
        return swarm.getEnemyShips();
    }

    public void setEnemyShips(List<EnemyShip> ships) {
        swarm.setEnemyShips(ships);
    }


    public void reset() {
/*        bunkers = new Bunker[4];
        for (int i = 0; i < bunkers.length; i++) {
            bunkers[i] = new Bunker((i + 1) * game.getScreenWidth() / 5 - (5 * Bunker.BRICK_SCALE), SpaceInvadersGame.BUNKER_TOP);
        }*/
        swarm = new Swarm(rows, cols, startingSpeed, startingSpeed, game);
    }
}
