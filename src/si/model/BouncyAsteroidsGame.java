package si.model;

import si.display.PlayerListener;
import ucd.comp2011j.engine.Game;

import java.awt.*;
import java.util.*;
import java.util.List;

public class BouncyAsteroidsGame implements Game {
    public static final int SCREEN_WIDTH = 768;
    public static final int SCREEN_HEIGHT = 512;
    private static final int NO_LEVELS = 10;
    private static final Rectangle SCREEN_BOUNDS = new Rectangle(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
    public static int time = 0;

    private int playerLives;
    private int playerScore;
    private boolean pause = true;
    private List<Bullet> playerBullets;
    private List<Bullet> enemyBullets;
    private ArrayList<Hittable> targets;
    private PlayerListener listener;
    private Player player;
    private Level[] level;
    private int currentLevel = 0;

    private EnemyShip shipD = null;

    private EnemyShip shipE = null;

    private boolean isUpPressed;
    private int plusLives = 0;


    public BouncyAsteroidsGame(PlayerListener listener) {
        this.listener = listener;
        startNewGame();
    }

    @Override
    public int getPlayerScore() {
        return playerScore;
    }

    @Override
    public int getLives() {
        return playerLives;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void checkForPause() {
        if (listener.hasPressedPause()) {
            pause = !pause;
            listener.resetPause();
        }
    }

    @Override
    public void updateGame() {
        if (!isPaused()) {
            player.tick();
            targets.clear();
            targets.addAll(level[currentLevel].getHittable());
            targets.add(player);

            playerBullets();
            enemyBullets();
            enemyBullets.addAll(level[currentLevel].move());
            movePlayer();

            time++;
            // Let Alien appear after 30 seconds
            if (time == 600){
                Random rnd = new Random();
                if (currentLevel < 5 && shipD == null){
                    int x0 = rnd.nextInt(30, BouncyAsteroidsGame.SCREEN_WIDTH-30);
                    int y0 = rnd.nextInt(30, BouncyAsteroidsGame.SCREEN_HEIGHT-30);
                    EnemyShip d = new EnemyShip(x0, y0, AlienType.D);
                    shipD = d;
                    shipE = null;

                    List<EnemyShip> ships = level[currentLevel].getEnemyShips();
                    ships.add(shipD);
                    level[currentLevel].setEnemyShips(ships);

                } else if(currentLevel > 4  && shipE == null){
                    int x0 = rnd.nextInt(30, BouncyAsteroidsGame.SCREEN_WIDTH-30);
                    int y0 = rnd.nextInt(30, BouncyAsteroidsGame.SCREEN_HEIGHT-30);
                    EnemyShip e = new EnemyShip(x0, y0, AlienType.E);
                    shipE = e;
                    shipD = null;
                    List<EnemyShip> ships = level[currentLevel].getEnemyShips();
                    ships.add(shipE);
                    level[currentLevel].setEnemyShips(ships);

                }
            }
            if (shipD != null){
                //get enemy angle here
                double enemyAngle = Math.toDegrees(Math.atan2(player.getY() - shipD.getY(), player.getX() - shipD.getX()));
                shipD.setAngle((int)enemyAngle);
            }
            if (shipE != null){
                //get enemy angle here
                double enemyAngle = Math.toDegrees(Math.atan2(player.getY() - shipE.getY(), player.getX() - shipE.getX()));
                shipE.setAngle((int)enemyAngle);
            }
        }
    }

    private void movePlayer() {
        if (listener.hasPressedFire()) {
            Bullet b = player.fire();
            if (b != null) {
                playerBullets.add(b);
                listener.resetFire();
            }
        }
        if (listener.isPressingLeft()) {
            player.setAngle((player.getAngle() - 7)%360);
        } else if (listener.isPressingRight()) {
            player.setAngle((player.getAngle() + 7)%360);
        } else if (listener.isPressingUp()) {
            isUpPressed = true;
            int x1 = (int)(Math.sin(Math.toRadians(-player.getAngle()))*100)/40;
            int y1 = (int)(Math.cos(Math.toRadians(-player.getAngle()))*100)/40;
            player.move(-x1, -y1);
        }
        // Make Player go forward all the time
        if (isUpPressed){
            int x1 = (int)(Math.sin(Math.toRadians(-player.getAngle()))*100)/40;
            int y1 = (int)(Math.cos(Math.toRadians(-player.getAngle()))*100)/40;
            //player.move(-x1, -y1);
            if (x1 > 0){
                x1 = x1 - (int)(Math.sin(Math.toRadians(-player.getAngle()))*100)/90;
            }
            if (y1 > 0){
                y1 = y1 - (int)(Math.cos(Math.toRadians(-player.getAngle()))*100)/90;
            }
            player.move(-x1, -y1);
        }
    }

    private void playerBullets() {
        // asteroid crush with Player and EnemyShip
        List<EnemyShip> ships = level[currentLevel].getEnemyShips();
        for (EnemyShip ship:ships){
            boolean hit = player.isHit(ship);
            if (hit){
                playerLives--;
                pause = true;
            }
            if (ship.getType() == AlienType.D || ship.getType() == AlienType.E){
                for (EnemyShip s:ships){
                    if (s.getType() != AlienType.D && s.getType() != AlienType.E){
                        boolean hit1 = s.isHit(ship);
                        if (hit1){
                            s.setAlive(false);
                            ship.setAlive(false);
                        }
                    }

                }
            }

        }

        List<Bullet> remove = new ArrayList<Bullet>();
        for (int i = 0; i < playerBullets.size(); i++) {
            if (playerBullets.get(i).isAlive() && playerBullets.get(i).getHitBox().intersects(SCREEN_BOUNDS)) {
                //playerBullets.get(i).setPlayer(player);
                playerBullets.get(i).move();
                for (Hittable t : targets) {
                    if (t != playerBullets.get(i)) {
                        boolean isPlayer = t instanceof Player;
                        if (!isPlayer && t.isHit(playerBullets.get(i))) {
                            playerScore += t.getPoints();
                            if (playerScore/10000 > plusLives){
                                plusLives = playerScore/10000;
                                playerLives++;
                            }
                            playerBullets.get(i).destroy();
                            //draw 2 little asteriods
                            if (t instanceof EnemyShip){
                                EnemyShip es = (EnemyShip)t;

                                if (es != null && es.getType()==AlienType.A){
                                    EnemyShip a;
                                    EnemyShip b;
                                    int x0 = es.getX();
                                    int y0 = es.getY();
                                    a = new EnemyShip(x0 - 20, y0, AlienType.B);
                                    ships.add(a);
                                    b = new EnemyShip(x0 + 20, y0, AlienType.B);
                                    ships.add(b);
                                    level[currentLevel].setEnemyShips(ships);
                                } else if (es != null && es.getType()==AlienType.B) {
                                    EnemyShip c;
                                    EnemyShip d;
                                    int x0 = es.getX();
                                    int y0 = es.getY();
                                    c = new EnemyShip(x0 - 20, y0, AlienType.C);
                                    ships.add(c);
                                    d = new EnemyShip(x0 + 20, y0, AlienType.C);
                                    ships.add(d);
                                    level[currentLevel].setEnemyShips(ships);
                                }
                            }

                        }
                    }
                }
            } else {
                remove.add(playerBullets.get(i));
            }
        }
        playerBullets.removeAll(remove);
    }

    private void enemyBullets() {
        List<Bullet> remove = new ArrayList<Bullet>();
        for (int i = 0; i < enemyBullets.size(); i++) {
            Bullet b = enemyBullets.get(i);
            if (b.isAlive() && b.getHitBox().intersects(SCREEN_BOUNDS)) {
                b.move();
                for (Hittable t : targets) {
                    if (t != b) {
                        if (t.isHit(b)) {
                            if (t.isPlayer()) {
                                playerLives--;
                                pause = true;
                            }else {
                                if (t instanceof EnemyShip){
                                    EnemyShip ship = (EnemyShip) t;
                                    if (ship.getType() != AlienType.D && ship.getType() != AlienType.E){
                                        ship.setAlive(false);
                                    }
                                }
                            }
                            b.destroy();
                        }
                    }
                }
            } else {
                remove.add(b);
            }
        }
        enemyBullets.removeAll(remove);
    }

    public static Rectangle getScreenBounds() {
        return new Rectangle(SCREEN_BOUNDS);
    }

    @Override
    public boolean isPaused() {
        return pause;
    }

    @Override
    public void startNewGame() {
        targets = new ArrayList<Hittable>();
        playerLives = 3;
        playerScore = 0;
        playerBullets = new ArrayList<Bullet>();
        enemyBullets = new ArrayList<Bullet>();
        player = new Player();
        level = new Level[10];
        currentLevel = 0;
        level[0] = new Level(0.5, 1, 1, this);
        level[1] = new Level(0.8, 1, 2, this);
        level[2] = new Level(1.2, 1, 3, this);
        level[3] = new Level(1.6, 1, 4, this);
        level[4] = new Level(2.0, 1, 5, this);
        level[5] = new Level(2.4, 1, 6, this);
        level[6] = new Level(2.8, 1, 7, this);
        level[7] = new Level(3.2, 1, 8, this);
        level[8] = new Level(3.6, 1, 9, this);
        level[9] = new Level(4.0, 1, 10, this);

        shipD = null;
        shipE = null;

    }

    @Override
    public boolean isLevelFinished() {
        if (currentLevel < NO_LEVELS) {
            int noShips = level[currentLevel].getShipsRemaining();
            return noShips == 0;
        } else {
            return true;
        }
    }

    @Override
    public boolean isPlayerAlive() {
        return player.isAlive();
    }

    @Override
    public void resetDestroyedPlayer() {
        player.resetDestroyed();
        isUpPressed = false;
        playerBullets = new ArrayList<Bullet>();
        enemyBullets = new ArrayList<Bullet>();
    }

    @Override
    public void moveToNextLevel() {
        pause = true;
        currentLevel++;
        player.resetDestroyed();
        isUpPressed = false;
        playerBullets = new ArrayList<Bullet>();
        enemyBullets = new ArrayList<Bullet>();
        time = 0;
        shipD = null;
        shipE = null;
    }


    @Override
    public boolean isGameOver() {
        return !(playerLives > 0 && currentLevel <= NO_LEVELS);
    }

    @Override
    public int getScreenWidth() {
        return SCREEN_WIDTH;
    }

    @Override
    public int getScreenHeight() {
        return SCREEN_HEIGHT;
    }

    public Player getShip() {
        return player;
    }

    public List<Bullet> getBullets() {
        ArrayList<Bullet> bullets = new ArrayList<Bullet>();
        bullets.addAll(playerBullets);
        bullets.addAll(enemyBullets);
        return bullets;
    }

    public List<EnemyShip> getEnemyShips() {
        return level[currentLevel].getEnemyShips();
    }

}
