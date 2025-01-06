package si.display;

import si.model.*;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class GameScreen extends JPanel {
    private static final long serialVersionUID = -8282302849760730222L;
    private BouncyAsteroidsGame game;

    public GameScreen(BouncyAsteroidsGame game){
        this.game = game;
    }

    private void drawPlayerShip(Graphics2D gc, Player p) {
        int x = p.getX();
        int y = p.getY();
        int angle = p.getAngle();
        int r = 5;
        int[] x_coords = new int[3];
        int[] y_coords = new int[3];
        x_coords[0] = (int)(Math.cos(Math.toRadians(-225+angle)) * r);
        y_coords[0] = (int)(Math.sin(Math.toRadians(-225+angle)) * r);
        x_coords[1] = (int)(Math.cos(Math.toRadians(-90+angle)) * r);
        y_coords[1] = (int)(Math.sin(Math.toRadians(-90+angle)) * r);
        x_coords[2] = (int)(Math.cos(Math.toRadians(45+angle)) * r);
        y_coords[2] = (int)(Math.sin(Math.toRadians(45+angle)) * r);
        int[] x_adjusted = new int[x_coords.length];
        int[] y_adjusted = new int[y_coords.length];
        for (int i = 0; i < x_coords.length; i++) {
            x_adjusted[i] = x + x_coords[i] * Player.SHIP_SCALE;
            y_adjusted[i] = y + y_coords[i] * Player.SHIP_SCALE;
        }
        Polygon pg = new Polygon(x_adjusted, y_adjusted, x_adjusted.length);
        gc.setColor(Color.WHITE);
        gc.fillPolygon(pg);

    }

    private void drawBullet(Graphics2D gc, Bullet b) {
        gc.setColor(Color.WHITE);
        gc.fillRect(b.getX(), b.getY(), b.BULLET_WIDTH, b.BULLET_HEIGHT);
    }

    private void drawEnemeyShip(Graphics2D gc, EnemyShip es, Player player) {

        if (es.getType() == AlienType.A) {
            drawEnemyA(gc, es);
        } else if (es.getType() == AlienType.B) {
            drawEnemyB(gc, es);
        } else if (es.getType() == AlienType.C){
            drawEnemyC(gc, es);
        } else if (es.getType() == AlienType.D) {
            drawEnemyD(gc, es);
        } else if (es.getType() == AlienType.E){
            drawEnemyE(gc, es);
        }
    }

    private void drawEnemy(Graphics2D gc, EnemyShip es,double size){
        int x = es.getX();
        int y = es.getY();

        Random rnd = new Random();
        double factor = 20;//10*1.0 + rnd.nextInt(10);

        double c1 = Math.cos(Math.PI * 2 / 5);
        double c2 = Math.cos(Math.PI / 5);
        double s1 = Math.sin(Math.PI * 2 / 5);
        double s2 = Math.sin(Math.PI * 4 / 5);

        double[] x_coords = new double[]{factor, factor * c1, -1 * factor * c2,-1 * factor * c2,factor * c1};
        double[] y_coords = new double[]{0.0,-1 * factor * s1,-1 * factor * s2,factor * s2,factor * s1,};

        int[] x_adjusted = new int[x_coords.length];
        int[] y_adjusted = new int[y_coords.length];
        for (int i = 0; i < x_coords.length; i++) {
            x_adjusted[i] = (int) (x + x_coords[i] * size * EnemyShip.SHIP_SCALE);
            y_adjusted[i] = (int) (y + y_coords[i] * size * EnemyShip.SHIP_SCALE);
        }

        gc.setColor(Color.GREEN);
        gc.fillPolygon(x_adjusted, y_adjusted, x_adjusted.length);
    }
    private void drawEnemyA(Graphics2D gc, EnemyShip es) {
        drawEnemy(gc, es, AlienType.largeSize);
    }

    private void drawEnemyB(Graphics2D gc, EnemyShip es) {
        drawEnemy(gc, es, AlienType.middleSize);
    }

    private void drawEnemyC(Graphics2D gc, EnemyShip es) {
        drawEnemy(gc, es, AlienType.littleSize);
    }

    private void drawEnemyD(Graphics2D gc, EnemyShip es) {
        int x = es.getX();
        int y = es.getY();
        int[] x_coords = new int[]{0, 0, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8, 9, 9, 8, 8, 7, 7, 6, 6, 5, 5, 4, 4, 3, 3, 2, 2, 1, 1, 0};
        int[] y_coords = new int[]{7, 4, 4, 3, 3, 2, 2, 1, 1, 2, 2, 1, 1, 2, 2, 3, 3, 4, 4, 7, 7, 5, 5, 7, 7, 6, 6, 7, 7, 6, 6, 7, 7, 5, 5, 7, 7};
        int[] x_adjusted = new int[x_coords.length];
        int[] y_adjusted = new int[y_coords.length];
        for (int i = 0; i < x_coords.length; i++) {
            x_adjusted[i] = x + x_coords[i] * EnemyShip.SHIP_SCALE_D;
            y_adjusted[i] = y + y_coords[i] * EnemyShip.SHIP_SCALE_D;
        }
        gc.setColor(Color.RED);
        gc.fillPolygon(x_adjusted, y_adjusted, x_adjusted.length);
        gc.fillRect(x + 2 * EnemyShip.SHIP_SCALE_D, y + EnemyShip.SHIP_SCALE_D * 0, EnemyShip.SHIP_SCALE_D, EnemyShip.SHIP_SCALE_D);
        gc.fillRect(x + 6 * EnemyShip.SHIP_SCALE_D, y + EnemyShip.SHIP_SCALE_D * 0, EnemyShip.SHIP_SCALE_D, EnemyShip.SHIP_SCALE_D);

        // creating eye holes
        gc.setColor(Color.BLACK);
        gc.fillRect(x + 3 * EnemyShip.SHIP_SCALE_D, y + EnemyShip.SHIP_SCALE_D * 3, EnemyShip.SHIP_SCALE_D, EnemyShip.SHIP_SCALE_D);
        gc.fillRect(x + 5 * EnemyShip.SHIP_SCALE_D, y + EnemyShip.SHIP_SCALE_D * 3, EnemyShip.SHIP_SCALE_D, EnemyShip.SHIP_SCALE_D);
    }

    private void drawEnemyE(Graphics2D gc, EnemyShip es) {
        int x = es.getX();
        int y = es.getY();
        int[] x_coords = new int[]{3, 2, 1, 0, 3, 6, 0, 2, 5, 1, 3, 6, 0, 2, 5, 7};
        int[] y_coords = new int[]{0, 1, 2, 3, 3, 3, 4, 5, 5, 6, 6, 6, 7, 7, 7, 7};
        int[] widths = new int[]{2, 4, 6, 2, 2, 2, 8, 1, 1, 1, 2, 1, 1, 1, 1, 1};
        int[] heights = new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
        gc.setColor(Color.RED);
        for (int i = 0; i < x_coords.length; i++) {
            gc.fillRect(x + x_coords[i] * EnemyShip.SHIP_SCALE, y + EnemyShip.SHIP_SCALE * y_coords[i], EnemyShip.SHIP_SCALE * widths[i], EnemyShip.SHIP_SCALE * heights[i]);
        }
    }


    protected void paintComponent(Graphics g) {
        if (game != null) {
            Graphics2D g2 = (Graphics2D) g;
            g.setColor(Color.black);
            g.fillRect(0, 0, BouncyAsteroidsGame.SCREEN_WIDTH, BouncyAsteroidsGame.SCREEN_HEIGHT);
            g.setColor(Color.green);
            g.drawString("Lives: " + game.getLives(), 0, 20);
            g.drawString("Score: " + game.getPlayerScore(), BouncyAsteroidsGame.SCREEN_WIDTH / 2, 20);
            g.drawString("Level: " + game.getCurrentLevel(), BouncyAsteroidsGame.SCREEN_WIDTH - 60, 20);

            drawPlayerShip(g2,game.getShip() );

            for (Bullet bullet : game.getBullets()) {
                drawBullet(g2, bullet);
            }
            for (EnemyShip e: game.getEnemyShips()){
                drawEnemeyShip(g2,e, game.getShip());
            }
            if (game.isPaused() && !game.isGameOver()) {
                g.setColor(Color.GREEN);
                g.drawString("Press 'p' to continue ", 256, 256);
            } else if (game.isGameOver()) {
                g.setColor(Color.GREEN);
                g.drawString("Game over ", 480, 256);
            }
        }
    }
}
