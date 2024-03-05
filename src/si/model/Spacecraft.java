package si.model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Spacecraft implements Hittable {
    int  playerx=1;
    int  playery=1;
    double realX;
    public double realY;
    double willRealX = 50;
    double willRealY;
    double bulletH = 180;
    double moveH = -180;

    private Polygon hitBox;
    private Polygon willHitBoxN;
    private Polygon willHitBoxS;
    private Polygon willHitBoxW;
    private Polygon willHitBoxE;
    private String name;
    private int count;
    private int reAppearence;
    private boolean alive = true;
    private Rectangle AeroliteHitbox;



    public Spacecraft(){
        hitBox = drawSpacecraftPolygon(this);
        realY =  Math.random()*(450-5+1)+5;
        realX=50;
    }





    public Bullet fire() {
        getBulletH();
        Bullet bul;
        count = count+1;
        if (count>60){
            bul = new Bullet((int) realX, (int) realY, -180+bulletH , name);
            count=0;
            return bul;
        }

        return null;
    }

    public List<Bullet> ebullet() {
        Bullet b = fire();
        List<Bullet> eBullets = new ArrayList<Bullet>();
        if (b != null) {
            eBullets.add(b);
        }
        return eBullets;
    }



    public void move(int a,int b) {
        this.playerx = a;
        this.playery = b;
        double willRealX=realX;
        double willRealY=realY;

        double y1 = Math.cos(Math.toRadians(moveH-45))*0.5;
        double x1 = Math.sin(Math.toRadians(moveH-45))*0.5;
        double y2 = Math.cos(Math.toRadians(moveH-45))*10;
        double x2 = Math.sin(Math.toRadians(moveH-45))*10;



        this.realY = realY +y1;
        this.realX = realX + x1;
        this.willRealX = willRealX + x2;
        this.willRealY = willRealY + y2;


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


        hitBox = drawSpacecraftPolygon(this);
        willHitBoxN =drawWillSpacecraftPolygon(this);
    }


    public int getX() {
        int a = (int) realX;
        return a;

    }

    public int getY() {
        int b = (int) realY;
        return b;
    }

    public void getBulletH(){
        if (realY<playery) {
            double deltax = realX - playerx;
            double deltay = realY - playery;
            this.bulletH = Math.toDegrees(Math.atan( deltax/ deltay));

        }else{
            double deltax =  playerx - realX;
            double deltay =  playery - realY;
            this.bulletH = Math.toDegrees(Math.atan(deltax / deltay))+180;
        }
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

    public Polygon getHitBox() {
        return hitBox;
    }
    public Rectangle getHitBoxR() {
        return null;
    }


    public boolean isAlive() {
        if (alive==false) {
            this.realX = -200;
            this.realY = -200;
        }
        reAppearence++;
        if (reAppearence>500) {
            new Spacecraft();
            alive = true;
            reAppearence = 0;
        }
        return alive;
    }

    public boolean isSpacecraft(){
        return true;
    }

    public boolean isPlayer(){
        return false;
    }


    public void willHit(List<Aerolite> ae){
        for(Aerolite a :ae) {
            Polygon aeHitebox = a.getHitBox();
            if (aeHitebox.intersects(willHitBoxN.getBounds())) {
                moveH=moveH+186;

            }
        }
    }

    @Override
    public int getPoints() {
        return 200;
    }

    public Polygon drawSpacecraftPolygon(Spacecraft sp){

        int xa =  sp.getX();
        int ya = sp.getY();

        int xb = xa + 10;
        int yb = ya;
        int xc = xa +10;
        int yc = ya +4;
        int xd = xa +20;
        int yd = ya +12;
        int xe = xa +12;
        int ye = ya +20;
        int xf = xa -2;
        int yf = ya +20;
        int xg = xa -10;
        int yg = ya +12;
        int xh = xa;
        int yh = ya +4;
        Polygon pg = new Polygon();
        pg.addPoint(xa,ya);
        pg.addPoint(xb,yb);
        pg.addPoint(xc,yc);
        pg.addPoint(xd,yd);
        pg.addPoint(xe,ye);
        pg.addPoint(xf,yf);
        pg.addPoint(xg,yg);
        pg.addPoint(xh,yh);
        return pg;
    }

    public Polygon drawWillSpacecraftPolygon(Spacecraft sp){

        int xa =  (int)realX;
        int ya = (int)realY;
        Polygon pg = new Polygon();
        pg.addPoint(xa-10,ya-10);
        pg.addPoint(xa + 20,ya-10);
        pg.addPoint(xa +20,ya +4);
        pg.addPoint(xa +30,ya +12);
        pg.addPoint(xa +22,ya +30);
        pg.addPoint( xa -12,ya +30);
        pg.addPoint(xa -20,ya +12);
        pg.addPoint(xa-10, ya +4);
        return pg;
    }

}




