package si.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Swarm implements Movable {
    private List<EnemyShip> ships;
    private int rows, cols;
    private int count = 0;
    private double moveX;
    private double moveY;
    private BouncyAsteroidsGame game;

    public Swarm(int r, int c, double sX, double sY, BouncyAsteroidsGame g) {
        game = g;
        rows = r;
        cols = c;
        moveX = sX;
        moveY = sY;
        ships = new ArrayList<EnemyShip>();
        Random rnd = new Random();

        for (int j = 0; j < cols; j++) {
            EnemyShip a;
            int x0 = rnd.nextInt(30, BouncyAsteroidsGame.SCREEN_WIDTH-30);
            int y0 = rnd.nextInt(30, BouncyAsteroidsGame.SCREEN_HEIGHT-30);
            if (Math.abs(x0- BouncyAsteroidsGame.SCREEN_WIDTH/2)<100 && Math.abs(y0- BouncyAsteroidsGame.SCREEN_HEIGHT/2)<100){
                x0 += 100;
            }


            a = new EnemyShip(x0, y0, AlienType.A);
            ships.add(a);

        }
    }

    public void move() {
        List<EnemyShip> remove = new ArrayList<EnemyShip>();
        for (EnemyShip s : ships) {
            if (!s.isAlive()) {
                remove.add(s);
            }
        }
        ships.removeAll(remove);


        for (EnemyShip s : ships) {
            s.move(moveX, moveY);
        }
    }



    public List<Hittable> getHittable() {
        return new ArrayList<Hittable>(ships);
    }

    public List<EnemyShip> getCurEnemyShips() {
        return ships;
    }

    public List<EnemyShip> getEnemyShips() {
        return new ArrayList<EnemyShip>(ships);
    }

    public void setEnemyShips(List<EnemyShip> les) {
        ships = les;
    }

    public int getShipsRemaining() {
        return ships.size();
    }
}
