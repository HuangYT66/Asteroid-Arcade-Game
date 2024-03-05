package si.model;

import java.awt.*;
import java.util.Random;

public class Aerolite implements Hittable {
    private String name;
    private boolean alive;
    private double x ;
    private double y ;
    private AlienType type;
    private Random rand;
    public static final int SHIP_SCALE = 2;
    double[] arrAngle = new double[]{-1,-1,-1,-1,-1,-1,-1,-1};
    private double aeroliteRotateH=0;
    private double aeroliteMoveH;
    private double ASTEROIDS_SPEED;




    public Aerolite(int x, int y,  AlienType type, double h,double speed) {
        this.aeroliteMoveH = h;
        this.x = x;
        this.y = y;
        this.ASTEROIDS_SPEED = speed;
        this.type = type;
        this.rand = new Random(x * 100 + y);
        this.alive = true;

    }

    public boolean isHit(Bullet b) {
        boolean hit = getHitBox().intersects(b.getHitBoxR());
        if (hit) {
            alive = false;
        }
        return hit;
    }

    @Override

    public Rectangle getHitBoxR() {
        return null;
    }

    public Polygon getHitBox() {
        Polygon polygon = new Polygon();
        if (type == AlienType.A) {
            polygon = drawAerolitePolygonA(this);
        }
        if (type == AlienType.B) {
            polygon = drawAerolitePolygonB(this);
        }
        if (type == AlienType.C) {
            polygon = drawAerolitePolygonC(this);
        }
        return polygon;
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

    public void move(double cX, double cY) {
        x += cX;
        y += cY;
    }

    public Bullet fire() {
        Bullet bul = null;
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
    public double[] ran_angle() {
        if (arrAngle[0] <= -1) {
            int countNum = 0;
            while (countNum < 6) {
                double angle =  Math.random() * (360 - 1 + 1) + 1;
                if (0 < angle && angle <= 60 && arrAngle[0] == -1) {
                    arrAngle[0] = angle;
                    countNum = countNum + 1;
                }
                if (60 < angle && angle <= 120 && arrAngle[1] == -1) {
                    arrAngle[1] = angle;
                    countNum = countNum + 1;
                }
                if (120 < angle && angle <= 180 && arrAngle[2] == -1) {
                    arrAngle[2] = angle;
                    countNum = countNum + 1;
                }
                if (180 < angle && angle <= 240 && arrAngle[3] == -1) {
                    arrAngle[3] = angle;
                    countNum = countNum + 1;
                }
                if (240 <= angle && angle <= 300 && arrAngle[4] == -1) {
                    arrAngle[4] = angle;
                    countNum = countNum + 1;
                }
                if (300 < angle && angle < 360 && arrAngle[5] == -1) {
                    arrAngle[5] = angle;
                    countNum = countNum + 1;

                }
            }

        }
        return arrAngle;
    }

    public void AeroliteRotateH(){
        aeroliteRotateH+=1;
    }
    public double getAeroliteRotateH(){
        return  aeroliteRotateH;
    }

    public int[] randomPosition(){
        this.x = Math.random()*((760-1)+1);
        this.y = Math.random()*((510-1)+1);
        while(this.x >350 && this.x<410){
            this.x = Math.random()*((760-1)+1);
        }
        while(this.y >230 && this.y<280){
            this.y = Math.random()*((510-1)+1);
        }
        int a = (int)this.x;
        int b = (int)this.x;
        int[] aa = new int[]{a,b};
        return  aa;
    }

    public double getAeroliteMoveHA(){
        aeroliteMoveH = Math.random()*((360-1)+1);
        return aeroliteMoveH;

    }
    public double getAeroliteMoveHB(){
        double temp = aeroliteMoveH;
        double tempMax = aeroliteMoveH+30;
        double tempMin = aeroliteMoveH-30;
        this.aeroliteMoveH =  Math.random()*(tempMax-tempMin+1)+tempMin;;
        while (aeroliteMoveH<(temp+10)&&aeroliteMoveH>(temp-10)){
            aeroliteMoveH =  Math.random()*(tempMax-tempMin+1)+tempMin;;
        }
        return aeroliteMoveH;
    }
    public double getAeroliteMoveHC(){
        double temp = aeroliteMoveH;
        double tempMax = aeroliteMoveH+40;
        double tempMin = aeroliteMoveH-40;
        this.aeroliteMoveH =  Math.random()*(tempMax-tempMin+1)+tempMin;;
        while (aeroliteMoveH<(temp+20)&&aeroliteMoveH>(temp-20)){
            aeroliteMoveH =  Math.random()*(tempMax-tempMin+1)+tempMin;;
        }
        return aeroliteMoveH;
    }


    public void move1(){
        double y1 = Math.cos(Math.toRadians(aeroliteMoveH-180))*ASTEROIDS_SPEED;
        double x1 = Math.sin(Math.toRadians(aeroliteMoveH-180))*ASTEROIDS_SPEED;

        int width = AsteroidsGame.SCREEN_WIDTH;
        int height = AsteroidsGame.SCREEN_HEIGHT;
        this.y = y + y1;
        this.x = x + x1;

        if (this.x >= width) {
            this.x = 1;
        } else if (this.x <= 0) {
            this.x = width-1;
        }else if(this.y >= height){
            this.y = 1;
        }else if (this.y <= 0) {
            this.y = height-1;
        }
    }

    public boolean isSpacecraft(){
        return false;
    }

    public Polygon drawAerolitePolygonA( Aerolite as) {
        double aeroliteRotateH = as.getAeroliteRotateH();
        int r = 15;
        int R = 35;
        double[] arr_angle = as.ran_angle();

        Polygon pg = new Polygon();
        double a_nx1;
        double a_ny1;
        for (int i = 0; i < 6; i++) {
            double angle_n = arr_angle[i];
            ;
            if (i == 3) {
                a_nx1 = x + r * Math.sin(Math.toRadians(angle_n + aeroliteRotateH));
                a_ny1 = y + r * Math.cos(Math.toRadians(angle_n + aeroliteRotateH));
            } else {
                a_nx1 = x + R * Math.sin(Math.toRadians(angle_n + aeroliteRotateH));
                a_ny1 = y + R * Math.cos(Math.toRadians(angle_n + aeroliteRotateH));
            }

            int a_nx = (int) a_nx1;
            int a_ny = (int) a_ny1;
            pg.addPoint(a_nx, a_ny);

        }
        return pg;
    }

    public Polygon drawAerolitePolygonB( Aerolite as){
        int x =  as.getX();
        int y = as.getY();
        double aeroliteRotateH = as.getAeroliteRotateH();

        int r = 20;
        int R = 25;
        double[] arr_angle= as.ran_angle();

        Polygon pg = new Polygon();
        double a_nx1;
        double a_ny1;
        for(int i =0; i<6; i++){
            double angle_n = arr_angle[i];;
            if(i == 3 || i == 4){
                a_nx1 = x+r*Math.sin(Math.toRadians(angle_n +aeroliteRotateH ));
                a_ny1 = y+r*Math.cos(Math.toRadians(angle_n +aeroliteRotateH ));
            }else {
                a_nx1 = x +R*Math.sin(Math.toRadians(angle_n +aeroliteRotateH ));
                a_ny1 = y + R * Math.cos(Math.toRadians(angle_n+aeroliteRotateH));
            }
            int a_nx = (int) a_nx1;
            int a_ny = (int) a_ny1;
            pg.addPoint(a_nx,a_ny);
        }
        return pg;
    }
    public Polygon drawAerolitePolygonC( Aerolite as){
        int x =  as.getX();
        int y = as.getY();
        double aeroliteRotateH = as.getAeroliteRotateH();
        int r = 18;
        int R = 15;
        double[] arr_angle= as.ran_angle();

        Polygon pg = new Polygon();
        double a_nx1;
        double a_ny1;
        for(int i =0; i<6; i++){
            double angle_n = arr_angle[i];;
            if(i == 3 || i == 4){
                a_nx1 = x+r*Math.sin(Math.toRadians(angle_n+aeroliteRotateH));
                a_ny1 = y+r*Math.cos(Math.toRadians(angle_n+aeroliteRotateH));
            }else {
                a_nx1 = x +R*Math.sin(Math.toRadians(angle_n+aeroliteRotateH));
                a_ny1 = y + R*Math.cos(Math.toRadians(angle_n+aeroliteRotateH));
            }
            int a_nx = (int) a_nx1;
            int a_ny = (int) a_ny1;
            pg.addPoint(a_nx,a_ny);
        }
        return pg;
    }



    public void destroy() {
        alive = false;
    }

    @Override
    public boolean isRush(Aerolite as) {
        return true;
    }
}
