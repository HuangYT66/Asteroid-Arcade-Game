package si.model;


import java.awt.*;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;

public class Swarm implements Movable {
    private List<Aerolite> ships;
    private boolean direction = true;
    private double x , y;
    private double ASTEROIDS_SPEED = 0.5;
    private double h;
    private int space = 30;
    private Aerolite[][] shipGrid;
    private int rows, cols;
    private int aerolite_num;
    private int count = 0;
    private double moveX;
    private double moveY;
    private AsteroidsGame game;
    private AlienType Type = AlienType.A;;
    private int xa,ya,xb,yb;
    private double hb,hc;





    public Swarm(int a, AsteroidsGame g) {
        game = g;
        aerolite_num = a;
        ships = new ArrayList<Aerolite>();
        for(int i=0 ; i< aerolite_num ; i++) {
            Aerolite ae;
            ae = new Aerolite((int)x, (int) y, Type, h, ASTEROIDS_SPEED);
            if (Type == AlienType.A ) {
                ae.randomPosition();
                h = ae.getAeroliteMoveHA();
                ASTEROIDS_SPEED = 0.5;
                ships.add(ae);
            }
        }

    }





    public void move() {
    }

    public void moveAerolite() {
        List<Aerolite> remove = new ArrayList<Aerolite>();
        List<Aerolite> addAerolite = new ArrayList<Aerolite>();


        for (Aerolite s : ships) {
            s.move1();
            s.AeroliteRotateH();
            if (!s.isAlive()) {
                remove.add(s);
                Aerolite aeX1;
                Aerolite aeX2;
                if (s.getType() == AlienType.A){
                    this.Type =AlienType.B;
                    xa = s.getX();
                    ya = s.getY();
                    hb = s.getAeroliteMoveHB();
                    aeX1 = new Aerolite(xa, ya , Type, hb, Math.random()*(1-0.5)+0.5);


                    hb = s.getAeroliteMoveHB();
                    aeX2 = new Aerolite(xa, ya , Type, hb, Math.random()*(1-0.5)+0.5);
                    addAerolite.add(aeX1);
                    addAerolite.add(aeX2);
                }else if (s.getType() == AlienType.B){
                    this.Type =AlienType.C;
                    xb = s.getX();
                    yb = s.getY();
                    hc = s.getAeroliteMoveHC();
                    aeX1 = new Aerolite(xb , yb , Type, hc, Math.random()*(1.5-1)+1);
                    hc = s.getAeroliteMoveHC();
                    aeX2 = new Aerolite(xb , yb , Type, hc, Math.random()*(1.5-1)+1);

                    addAerolite.add(aeX1);
                    addAerolite.add(aeX2);
                }
            }
        }
        ships.removeAll(remove);
        ships.addAll(addAerolite);

    }


    public void tick() {
        count++;
    }

    public List<Hittable> getHittable() {
        return new ArrayList<Hittable>(ships);
    }

    public List<Aerolite> getAerolites() {
        return new ArrayList<Aerolite>(ships);
    }

    public int getAeroliteRemaining() {
        return ships.size();
    }
}
