package kr.ac.tukorea.ge.spgp2024.framework.activity;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import kr.ac.tukorea.ge.spgp2024.framework.view.GameView;

public class GameActivity extends AppCompatActivity {

    public static GameActivity activity;
    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        gameView = new GameView(this);
        //gameView.setFullScreen();
        setContentView(gameView);
        //new MainScene().push();

        getOnBackPressedDispatcher().addCallback(onBackPressedCallback);
    }

    private final OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) {
        @Override
        public void handleOnBackPressed() {
            gameView.onBackPressed();
        }
    };


    @Override
    protected void onPause() {
        gameView.pauseGame();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.resumeGame();
    }

    @Override
    protected void onDestroy() {
        gameView.destroyGame();
        super.onDestroy();
    }
}