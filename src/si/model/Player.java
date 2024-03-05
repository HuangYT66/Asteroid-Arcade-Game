package si.model;

//import javafx.geometry.Rectangle2D;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ImageObserver;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderableImage;
import java.text.AttributedCharacterIterator;
import java.util.Map;

public class Player implements Hittable {
    private int x;
    private int y;
    private double realX;
    private double realY;
    private int h;
    int count;
    private double speedX =0.0;
    private double speedY =0.0;
    private Polygon hitBox;
    private int weaponCountdown;
    private boolean alive = true;
    public static final int SHIP_SCALE = 4;
    private static final int WIDTH = SHIP_SCALE * 8;


    public Player() {
        x = AsteroidsGame.SCREEN_WIDTH / 2;
        y = AsteroidsGame.SCREEN_HEIGHT / 2;
        realX = AsteroidsGame.SCREEN_WIDTH / 2;
        realY = AsteroidsGame.SCREEN_HEIGHT / 2;
        hitBox = drawPlayerPolygon(this);
    }

    public boolean isHit(Bullet b) {
        Rectangle s = b.getHitBoxR();
        if (s.intersects(hitBox.getBounds())) {
            alive = false;
        }
        return s.intersects(hitBox.getBounds());
    }

    public boolean isRush(Aerolite ae){
        Polygon aeHitebox = ae.getHitBox();
        if (aeHitebox.intersects(hitBox.getBounds())) {
            alive = false;
        }
        return aeHitebox.intersects(hitBox.getBounds());
    }

    @Override

    public Rectangle getHitBoxR() {
        return null;
    }


    public Polygon getHitBox() {
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
        hitBox = drawPlayerPolygon(this);
        count = 0;
    }

    public int getPoints() {
        return -100;
    }

    public boolean isPlayer() {
        return true;
    }


    public Bullet fire() {
        int hB= h-180;
        Bullet b = null;
        double a_x1 = getRealX()+16*Math.sin(Math.toRadians(hB));
        double a_y1 = getRealY()+16*Math.cos(Math.toRadians(hB));
        int a_x = (int) a_x1;
        int a_y = (int) a_y1;
        if (weaponCountdown == 0) {
            b = new Bullet(a_x-2, a_y-2 , this.h, "Player");
        }
        return b;
    }

    public void temphero(){
        count = count+1;
        if (count<100){
            Polygon pg = new Polygon();
            pg.addPoint(0, 0);
            pg.addPoint(0, 0);
            hitBox = pg;
        }
    }





    public void move( ) {
        int width = AsteroidsGame.SCREEN_WIDTH;
        int height = AsteroidsGame.SCREEN_HEIGHT;
        if (this.realX >= width || this.realX <= 0) {
            this.realX = width - this.realX;
            this.realX += speedX;
            this.realY += speedY;
        }else if (this.realY >= height || this.realY <= 0) {
            this.realY = height - this.realY;
            this.realX += speedX;
            this.realY += speedY;

        }else{
            this.realX += speedX;
            this.realY += speedY;
        }
        this.x += (int) speedX;
        this.y += (int) speedX;
        hitBox = drawPlayerPolygon(this);
        temphero();



    }

    public void rotate(int h1) {
        this.h += h1;
    }

    public void addS(){
         this.speedX = this.speedX+0.05*Math.sin(Math.toRadians(h-180));
         this.speedY = this.speedY+0.05*Math.cos(Math.toRadians(h-180));
    }

    public int getRealX() {
        int a = (int) realX;
        return a;

    }

    public int getRealY() {
        int b = (int) realY;
        return b;
    }


    public int getH() {
        return h;
    }

    public void setRealX(double x){
        realX = x;
    }

    public void setRealY(double y){
        realY = y;
    }


    public boolean isSpacecraft(){
        return false;
    }

    public Polygon drawPlayerPolygon( Player p) {
        int x = p.getRealX();
        int y = p.getRealY();

        int h = p.getH() - 180;
        int r = 16;
        double angle_a = h;
        double angle_b = h + 150;
        double angle_c = h + 210;

        double a_x1 = x + r * Math.sin(Math.toRadians(angle_a));
        double a_y1 = y + r * Math.cos(Math.toRadians(angle_a));
        double b_x1 = x + r * Math.sin(Math.toRadians(angle_b));
        double b_y1 = y + r * Math.cos(Math.toRadians(angle_b));
        double c_x1 = x + r * Math.sin(Math.toRadians(angle_c));
        double c_y1 = y + r * Math.cos(Math.toRadians(angle_c));

        int a_x = (int) a_x1;
        int a_y = (int) a_y1;
        int b_x = (int) b_x1;
        int b_y = (int) b_y1;
        int c_x = (int) c_x1;
        int c_y = (int) c_y1;

        Polygon pg = new Polygon();
        pg.addPoint(a_x, a_y);
        pg.addPoint(b_x, b_y);
        pg.addPoint(c_x, c_y);

        return pg;
    }

}
