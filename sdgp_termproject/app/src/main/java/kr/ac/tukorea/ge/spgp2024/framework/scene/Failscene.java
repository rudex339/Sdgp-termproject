package kr.ac.tukorea.ge.spgp2024.framework.scene;

import android.app.AlertDialog;
import android.content.DialogInterface;

import kr.ac.tukorea.ge.spgp2024.dragonflight.R;
import kr.ac.tukorea.ge.spgp2024.dragonflight.game.Button;
import kr.ac.tukorea.ge.spgp2024.dragonflight.game.scoreboard;
import kr.ac.tukorea.ge.spgp2024.framework.activity.GameActivity;
import kr.ac.tukorea.ge.spgp2024.framework.objects.Score;
import kr.ac.tukorea.ge.spgp2024.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2024.framework.view.Metrics;

public class Failscene extends Scene{
    public enum Layer {
        bg, title, score,touch, COUNT
    }
    //private final Sprite title;
    private float angle;
    public Failscene(Score num) {
        initLayers(Layer.COUNT);
        float w = Metrics.width, h = Metrics.height;
        float cx = w / 2, cy = h / 2;
        add(Layer.bg, new Sprite(R.mipmap.trans_50b, cx, cy, w, h));
        add(Layer.bg, new Sprite(R.mipmap.trans_50b, cx, cy, 12.00f, 6.75f));
        //title = new Sprite(R.mipmap.title, cx, cy-2.0f, 3.69f, 1.38f);
        //add(Layer.title, title);
        add(Layer.touch, new Button(R.mipmap.btn_exit_n, 8f, 7.0f, 2.667f, 1f, new Button.Callback() {
            @Override
            public boolean onTouch(Button.Action action) {
                new AlertDialog.Builder(GameActivity.activity)
                        .setTitle("Confirm")
                        .setMessage("Do you really want to exit the game?")
                        .setNegativeButton("No", null)
                        .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finishActivity();
                            }
                        })
                        .create()
                        .show();
                return false;
            }
        }));
        add(Layer.bg, new scoreboard(num, false));
    }
    public void addScoreBoard(Score score){

    }
    @Override
    public void update(float elapsedSeconds) {
        super.update(elapsedSeconds);
    }

    @Override
    protected int getTouchLayerIndex() {
        return Layer.touch.ordinal();
    }

    @Override
    public boolean isTransparent() {
        return true;
    }
}
