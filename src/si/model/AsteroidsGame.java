package si.model;


import si.display.PlayerListener;
import ucd.comp2011j.engine.Game;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AsteroidsGame implements Game {
    public static final int SCREEN_WIDTH = 768;
    public static final int SCREEN_HEIGHT = 512;
    public static final int BUNKER_TOP = 350;
    private static final Rectangle SCREEN_BOUNDS = new Rectangle(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

    private int playerLives;
    private int playerScore = 0;
    private boolean pause = true;
    private List<Bullet> playerBullets;
    private List<Bullet> enemyBullets;
    private List<Aerolite> aerolite;
    private ArrayList<Hittable> targets;
    private PlayerListener listener;
    public Player player;
    private  Spacecraft spacecraft;
    private Level level;
    private int currentLevel = 0;
    private int playerx,playery;

    public AsteroidsGame(PlayerListener listener) {
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
            targets.addAll(level.getHittable());
            addSpacecraft();
            targets.add(player);
            playerBullets();
            enemyBullets();
            AeroliteRush();
            enemyBullets.addAll(spacecraft.ebullet());
            aerolite = level.getAerolites();
            movePlayer();
            moveSpacecraft();
            level.changeAerolite();
            Playerflash();
            callWillHit();

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

        if (listener.isPressingUp()) {
            player.addS();
        }
        player.move();
        playerx = player.getRealX();

        if (listener.isPressingLeft()) {
            player.rotate(2);
        }
        if (listener.isPressingRight()) {
            player.rotate(-2);
        }

    }


    private void moveSpacecraft() {
        spacecraft.move(player.getRealX(),player.getRealY());


    }


    private void playerBullets() {
        List<Bullet> remove = new ArrayList<Bullet>();
        for (int i = 0; i < playerBullets.size(); i++) {
            if (playerBullets.get(i).isAlive() && playerBullets.get(i).getHitBoxR().intersects(SCREEN_BOUNDS)) {//子弹活着且子弹在屏幕内
                playerBullets.get(i).move();
                for (Hittable t : targets) {
                    if (t != playerBullets.get(i)) {
                        if (t.isHit(playerBullets.get(i))) {
                            playerScore += t.getPoints();
                            playerBullets.get(i).destroy();
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
            if (b.isAlive() && b.getHitBoxR().intersects(SCREEN_BOUNDS)) {
                b.move();
                for (Hittable t : targets) {
                    if (!t.isSpacecraft()) {
                        if (t != b) {
                            if (t.isHit(b)) {
                                if (t.isPlayer()) {
                                playerLives--;
                                pause = true;
                                player.resetDestroyed();
                                }
                                b.destroy();
                            }
                        }

                    }
                }
            } else {
                remove.add(b);
            }
        }
        enemyBullets.removeAll(remove);
    }

    private void AeroliteRush() {
        List<Aerolite> remove = new ArrayList<Aerolite>();
        for (int i = 0; i < aerolite.size(); i++) {
            Aerolite a = aerolite.get(i);
            if (a.isAlive() && a.getHitBox().intersects(SCREEN_BOUNDS)) {
                a.move1();
                for (Hittable t : targets) {
                    if (t != a) {
                        if (t.isPlayer()) {
                            if (t.isRush(a)) {
                                playerLives--;
                                pause = true;
                                player.resetDestroyed();
                                a.destroy();
                            }
                        }else if (t.isSpacecraft()){
                            if (t.isRush(a)) {
                                a.destroy();
                            }
                        }
                    }
                }
            } else {
                remove.add(a);
            }
        }
        aerolite.removeAll(remove);
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
        aerolite = new ArrayList<Aerolite>();
        player = new Player();
        level = new Level(currentLevel,this);

        spacecraft = level.getSpacecraft();
    }


    @Override
    public boolean isLevelFinished() {
        int noAerolite = level.getAeroliteRemaining();
        if (noAerolite == 0) {
            currentLevel = currentLevel+1;
            return true;
        }
        return false;
    }

    @Override
    public int getTargetFPS() {
        return 0;
    }

    @Override
    public boolean isPlayerAlive() {
        return player.isAlive();
    }

    public void Playerflash(){
        if (listener.isPressingDown()) {
            boolean can = false;
            double xx = Math.random() * (730 - 5 + 1) + 5;
            double yy = Math.random()*(450-5+1)+5;

            for (int i = 0; i < aerolite.size(); i++) {
                Aerolite a = aerolite.get(i);
                if (player.isRush(a)&&player.getHitBox().intersects(spacecraft.getHitBox().getBounds())){
                     xx = Math.random() * (730 - 5 + 1) + 5;
                     yy = Math.random()*(450-5+1)+5;
                    can = true;
                }
            }
            while (can == true){
                 xx = Math.random() * (730 - 5 + 1) + 5;
                 yy = Math.random()*(450-5+1)+5;
                for (int i = 0; i < aerolite.size(); i++) {
                    Aerolite a = aerolite.get(i);
                    if (player.isRush(a)&&player.getHitBox().intersects(spacecraft.getHitBox().getBounds())) {
                         xx = Math.random() * (730 - 5 + 1) + 5;
                         yy = Math.random()*(450-5+1)+5;
                        can = true;
                    }
                }

            }
            player.setRealX(xx);
            player.setRealY(yy);

        }
    }

    @Override
    public void resetDestroyedPlayer() {
        player.resetDestroyed();
        playerBullets = new ArrayList<Bullet>();
    }


    @Override
    public void moveToNextLevel() {
        pause = true;
        player.resetDestroyed();

        playerBullets = new ArrayList<Bullet>();
        enemyBullets = new ArrayList<Bullet>();
        spacecraft = new Spacecraft();
        level = new Level(currentLevel,this);

    }


    @Override
    public boolean isGameOver() {
        if(playerLives ==  0 ){
            return true;
        };
        return false;
    }

    @Override
    public int getScreenWidth() {
        return SCREEN_WIDTH;
    }

    @Override
    public int getScreenHeight() {
        return SCREEN_HEIGHT;
    }

    public Player getPlayer() {
        return player;
    }

    public List<Bullet> getBullets() {
        ArrayList<Bullet> bullets = new ArrayList<Bullet>();
        bullets.addAll(playerBullets);
        bullets.addAll(enemyBullets);
        return bullets;
    }

    public List<Aerolite> getAerolites() {
        return level.getAerolites();
    }


    public Spacecraft getSpacecraft() {
        return spacecraft;
    }


    public void addSpacecraft(){
        if (spacecraft.isAlive()){
            targets.add(spacecraft);
        }
    }

    public void callWillHit(){
        spacecraft.willHit(getAerolites());
    }

}
