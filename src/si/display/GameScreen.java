package si.display;

import si.model.*;

import javax.swing.*;
import java.awt.*;

public class GameScreen extends JPanel {
    private static final long serialVersionUID = -8282302849760730222L;
    private AsteroidsGame game;



    public GameScreen(AsteroidsGame game){
        this.game = game;
    }

    private void drawPlayer(Graphics2D gc, Player p) {
//        int x =  p.getRealX();
//        int y = p.getRealY();
//
//        int h = p. getH()-180;
//        int r = 16;
//        double angle_a = h;
//        double angle_b = h+150;
//        double angle_c = h+210;
//
//        double a_x1 = x+r*Math.sin(Math.toRadians(angle_a));
//        double a_y1 = y+r*Math.cos(Math.toRadians(angle_a));
//        double b_x1 = x+r*Math.sin(Math.toRadians(angle_b));
//        double b_y1 = y+r*Math.cos(Math.toRadians(angle_b));
//        double c_x1 = x+r*Math.sin(Math.toRadians(angle_c));
//        double c_y1 = y+r*Math.cos(Math.toRadians(angle_c));
//
//        int a_x = (int) a_x1;
//        int a_y = (int) a_y1;
//        int b_x = (int) b_x1;
//        int b_y = (int) b_y1;
//        int c_x = (int) c_x1;
//        int c_y = (int) c_y1;
//
//        Polygon pg = new Polygon();
//        pg.addPoint(a_x,a_y);
//        pg.addPoint(b_x,b_y);
//        pg.addPoint(c_x,c_y);

        Polygon pg =p.drawPlayerPolygon(p);
        gc.setColor(Color.GREEN);
        gc.drawPolygon(pg);
    }




    private void drawBullet(Graphics2D gc, Bullet b) {
        gc.setColor(Color.GREEN);
        gc.fillRect( b.getX(), b.getY(), b.BULLET_WIDTH, b.BULLET_HEIGHT);
    }

    private void drawAerolite(Graphics2D gc, Aerolite es) {
        if (es.getType() == AlienType.A) {
            drawAeroliteA(gc, es);
        } else if (es.getType() == AlienType.B) {
            drawAeroliteB(gc, es);
        } else {
            drawAeroliteC(gc, es);
        }
    }





    private void drawAeroliteA(Graphics2D gc, Aerolite as) {
        Polygon pg = as.drawAerolitePolygonA(as);
        gc.setColor(Color.GREEN);
        gc.drawPolygon(pg);
    }


    private void drawAeroliteB(Graphics2D gc, Aerolite as) {
        Polygon pg = as.drawAerolitePolygonB(as);
        gc.setColor(Color.CYAN );
        gc.drawPolygon(pg);
    }

    private void drawAeroliteC(Graphics2D gc, Aerolite as) {
        Polygon pg = as.drawAerolitePolygonC(as);
        gc.setColor(Color.YELLOW);
        gc.drawPolygon(pg);
    }


    private void drawSpacecraft(Graphics2D gc, Spacecraft sc) {
        int xa = game.getSpacecraft().getX();
        int ya = game.getSpacecraft().getY();
        Polygon pg = new Polygon();
        pg.addPoint(xa,ya);
        pg.addPoint(xa + 10,ya);
        pg.addPoint(xa +10,ya +4);
        pg.addPoint(xa +20,ya +12);
        pg.addPoint(xa +12,ya +20);
        pg.addPoint(xa -2,ya +20);
        pg.addPoint(xa -10,ya +12);
        pg.addPoint(xa, ya +4);
        gc.setColor(Color.WHITE );
        gc.drawPolygon(pg);
    }

    protected void paintComponent(Graphics g) {
        if (game != null) {
            Graphics2D g2 = (Graphics2D) g;
            g.setColor(Color.black);
            g.fillRect(0, 0, AsteroidsGame.SCREEN_WIDTH, AsteroidsGame.SCREEN_HEIGHT);
            g.setColor(Color.green);
            g.drawString("Lives: " + game.getLives(), 0, 20);
            g.drawString("Score: " + game.getPlayerScore(), AsteroidsGame.SCREEN_WIDTH / 2, 20);

            drawPlayer(g2,game.getPlayer());
            drawSpacecraft(g2,game.getSpacecraft());

            for (Bullet bullet : game.getBullets()) {
                drawBullet(g2, bullet);
            }

            for (Aerolite e: game.getAerolites()){
                drawAerolite(g2,e);
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
