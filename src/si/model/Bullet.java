package si.model;

import java.awt.*;

public class Bullet implements Movable, Hittable {
    private int x, y;
    private double realX ;
    private double realY ;
    int count = 0;
    private double h;
    private boolean alive = true;
    private Rectangle hitBox;
    private String name;
    private static int bulletCounter = 0;
    public static final int BULLET_HEIGHT = 4;
    public static final int BULLET_WIDTH = 4;
    private static final int BULLET_SPEED=5;


    public Bullet(int x, int y, double h1, String name) {

        this.h = h1;
        this.x = x;
        this.y = y;
        this.realX = x;
        this.realY = y;
        this.name = name + " " + bulletCounter++;
        hitBox = new Rectangle(x , y, BULLET_WIDTH, BULLET_HEIGHT);
    }

    public void move() {

        double y1 = Math.cos(Math.toRadians(h-180))*BULLET_SPEED;
        double x1 = Math.sin(Math.toRadians(h-180))*BULLET_SPEED;


        this.realY = realY + y1;
        this.realX = realX + x1;

        int width = AsteroidsGame.SCREEN_WIDTH;
        int height = AsteroidsGame.SCREEN_HEIGHT;


        if (this.realX >= width) {
            this.realX = 1;
        } else if (this.realX <= 0) {
            this.realX = width-1;
        }else if(this.realY >= height){
            this.realY = 1;
        }else if (this.realY <= 0) {
            this.realY = height-1;
        }


        this.x = (int) realX;
        this.y = (int) realY;



        hitBox.setLocation(x, y);


        count = count+1;
        if (count>70){
            count = 0;
            destroy();
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;

    }

    public boolean isHit(Bullet b) {
        if (hitBox.intersects(b.hitBox)) {
            alive = false;
            b.alive = false;
        }
        return hitBox.intersects(b.hitBox);
    }

    public Rectangle getHitBoxR() {
        return new Rectangle(hitBox);
    }

    public Polygon getHitBox(){
        return null;
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

    public boolean isSpacecraft(){
        return false;
    }

    public boolean isRush(Aerolite as){
        return false;
    }

}
