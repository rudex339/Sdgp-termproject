package kr.ac.tukorea.ge.spgp2024.dragonflight.game;

import android.view.MotionEvent;

import kr.ac.tukorea.ge.spgp2024.dragonflight.R;
import kr.ac.tukorea.ge.spgp2024.framework.objects.TileBackground;
import kr.ac.tukorea.ge.spgp2024.framework.objects.Score;
import kr.ac.tukorea.ge.spgp2024.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2024.framework.view.Metrics;

public class MainScene extends Scene {
    private static final String TAG = MainScene.class.getSimpleName();
    private final TowerGenerator towercontroller;
    Score score; // package private

    public int getScore() {
        return score.getScore();
    }

    public enum Layer {
        bg, enemy, bullet, tower, ui, controller, COUNT
    }
    public MainScene() {
        //Metrics.setGameSize(16, 16);
        initLayers(Layer.COUNT);

        add(Layer.controller, new EnemyGenerator());

        towercontroller = new TowerGenerator();
        add(Layer.controller, towercontroller);

        add(Layer.controller, new CollisionChecker(this));

        //add(Layer.bg, new VertScrollBackground(R.mipmap.bg_city, 0.2f));
        //add(Layer.bg, new VertScrollBackground(R.mipmap.clouds, 0.4f));

        add(Layer.bg, new TileBackground(R.mipmap.medievalpack16x16));

        //this.fighter = new Fighter();
        //add(Layer.player, fighter);

        this.score = new Score(R.mipmap.number_24x32, Metrics.width - 0.5f, 0.5f, 0.6f);
        score.setScore(0);
        //add(Layer.ui, score);
    }

    public void addScore(int amount) {
        score.add(amount);
    }

    @Override
    public void update(float elapsedSeconds) {
        super.update(elapsedSeconds);
    }

    @Override
    public boolean onTouch(MotionEvent event) {
        return towercontroller.onTouch(event);
    }
}
