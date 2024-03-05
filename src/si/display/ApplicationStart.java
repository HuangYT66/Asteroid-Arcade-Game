package si.display;

import si.database.PersistentScoreKeeper;
import si.model.AsteroidsGame;
import ucd.comp2011j.engine.GameManager;

import javax.swing.*;

public class ApplicationStart {
    public static void main(String[] args) {
        JFrame mainWindow = new JFrame();
        mainWindow.setSize(AsteroidsGame.SCREEN_WIDTH, AsteroidsGame.SCREEN_HEIGHT);
        mainWindow.setResizable(false);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setTitle("Asteroids Game");
        mainWindow.setLocationRelativeTo(null);

        PlayerListener playerListener = new PlayerListener();
        mainWindow.addKeyListener(playerListener);
        MenuListener menuListener = new MenuListener();
        mainWindow.addKeyListener(menuListener);
        AsteroidsGame game = new AsteroidsGame(playerListener);
        GameScreen gameScreen = new GameScreen(game);
        MenuScreen menuScreen = new MenuScreen();
        PersistentScoreKeeper scoreKeeper = new PersistentScoreKeeper();
        GameManager mmm = new GameManager(game, mainWindow, menuListener, menuScreen, new AboutScreen(), new ScoreScreen(scoreKeeper), gameScreen, scoreKeeper);
        mainWindow.setVisible(true);
        mmm.run();
    }
}
