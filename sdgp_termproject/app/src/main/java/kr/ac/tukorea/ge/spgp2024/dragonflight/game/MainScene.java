package kr.ac.tukorea.ge.spgp2024.dragonflight.game;

import android.view.MotionEvent;

import kr.ac.tukorea.ge.spgp2024.dragonflight.R;
import kr.ac.tukorea.ge.spgp2024.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2024.framework.objects.TileBackground;
import kr.ac.tukorea.ge.spgp2024.framework.objects.Score;
import kr.ac.tukorea.ge.spgp2024.framework.scene.Failscene;
import kr.ac.tukorea.ge.spgp2024.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2024.framework.view.Metrics;

public class MainScene extends Scene {
    private static final String TAG = MainScene.class.getSimpleName();
    private final TowerGenerator towercontroller;
    Score score; // package private

    public int getScore() {
        return score.getHp();
    }

    public enum Layer {
       bbg, bg, enemy, bullet, tower, ui, controller, touch ,COUNT
    }
    public MainScene() {
        //Metrics.setGameSize(16, 16);
        float w = Metrics.width, h = Metrics.height;
        float cx = w / 2, cy = h / 2;

        initLayers(Layer.COUNT);

        add(Layer.controller, new EnemyGenerator());

        towercontroller = new TowerGenerator();
        add(Layer.controller, towercontroller);

        add(Layer.controller, new CollisionChecker(this));

        //add(Layer.bg, new VertScrollBackground(R.mipmap.bg_city, 0.2f));
        //add(Layer.bg, new VertScrollBackground(R.mipmap.clouds, 0.4f));
        add(Layer.bbg, new Sprite(R.mipmap.trans_50b, cx, cy, w, h));
        add(Layer.bg, new TileBackground(R.mipmap.medievalpack16x16));

        add(Layer.touch, new Button(R.mipmap.button_0, 1.5f, 8.0f, 1.0f, 1.25f, new Button.Callback() {
            @Override
            public boolean onTouch(Button.Action action) {
                //Log.d(TAG, "Button: Slide " + action);
                //player.slide(action == Button.Action.pressed);
                towercontroller.choose_Tower=0;
                return true;
            }
        }));
        add(Layer.touch, new Button(R.mipmap.button_1, 2.6f, 8.0f, 1.0f, 1.25f, new Button.Callback() {
            @Override
            public boolean onTouch(Button.Action action) {
                //Log.d(TAG, "Button: Slide " + action);
                //player.slide(action == Button.Action.pressed);
                towercontroller.choose_Tower=1;
                return true;
            }
        }));
        add(Layer.touch, new Button(R.mipmap.button_2, 3.7f, 8.0f, 1.0f, 1.25f, new Button.Callback() {
            @Override
            public boolean onTouch(Button.Action action) {
                //Log.d(TAG, "Button: Slide " + action);
                //player.slide(action == Button.Action.pressed);
                towercontroller.choose_Tower=2;
                return true;
            }
        }));
        add(Layer.touch, new Button(R.mipmap.button_3, 4.8f, 8.0f, 1.0f, 1.25f, new Button.Callback() {
            @Override
            public boolean onTouch(Button.Action action) {
                //Log.d(TAG, "Button: Slide " + action);
                //player.slide(action == Button.Action.pressed);
                towercontroller.choose_Tower=3;
                return true;
            }
        }));

        add(Layer.touch, new Button(R.mipmap.costup, 5.9f, 8.0f, 1.0f, 1.0f, new Button.Callback() {
            @Override
            public boolean onTouch(Button.Action action) {
                //Log.d(TAG, "Button: Slide " + action);
                //player.slide(action == Button.Action.pressed);
                if(towercontroller.cost >=6 && towercontroller.upcost!=6) {
                    towercontroller.cost -= 6;
                    towercontroller.upcost += 2;
                }
                return true;
            }
        }));

        this.score = new Score(R.mipmap.number_24x32, Metrics.width - 0.5f, 0.5f, 0.6f);
        score.setHp(5);
        add(Layer.ui, score);
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
        towercontroller.onTouch(event);

        return super.onTouch(event);
    }

    @Override
    protected int getTouchLayerIndex() {

        return Layer.touch.ordinal();
    }
}
