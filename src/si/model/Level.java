package si.model;



import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Level {
    private Swarm swarm;
    private Spacecraft spacecraft;
    private double startingSpeed;
    private int aerolite_num;
    private AsteroidsGame game;



    public Level(int level, AsteroidsGame g) {
        game = g;
        aerolite_num = level+4;
        reset();
        if(level!=0){
            getSpacecraftNew();
        }
    }

    public int getAeroliteRemaining() {
        return swarm.getAeroliteRemaining();
    }




    public List<Hittable> getHittable() {
        spacecraft.move(game.player.getRealX(),game.player.getRealY());
        List<Hittable> targets = new ArrayList<Hittable>();
        targets.addAll(swarm.getHittable());
        return targets;
    }

    public List<Aerolite> getAerolites() {
        return swarm.getAerolites();
    }


    public void reset() {
        swarm = new Swarm(aerolite_num, game);
        this.spacecraft = new Spacecraft();
    }

    public Spacecraft getSpacecraft(){
        return spacecraft;
    }

    public Spacecraft getSpacecraftNew() {
        Spacecraft spacecraftnew = new Spacecraft();
        return spacecraftnew;
    }

    public void changeAerolite(){
        swarm.moveAerolite();
    }




}
