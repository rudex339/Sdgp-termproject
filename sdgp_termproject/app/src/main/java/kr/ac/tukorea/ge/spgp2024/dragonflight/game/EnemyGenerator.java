package kr.ac.tukorea.ge.spgp2024.dragonflight.game;

import android.graphics.Canvas;
import android.util.Log;

import java.util.Random;

import kr.ac.tukorea.ge.spgp2024.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2024.framework.scene.Scene;

public class EnemyGenerator implements IGameObject {
    private static final String TAG = EnemyGenerator.class.getSimpleName();
    public float GEN_INTERVAL = 3.0f;
    private final Random random = new Random();
    private float enemyTime = 0;
    private int wave=1, maw_wave=3,numEnemy=0, maxEnemy=10;
    //10 20 30
    @Override
    public void update(float elapsedSeconds) {
        enemyTime -= elapsedSeconds;//특정시간때마다 generate를 호출하여 적을 생성
        if (enemyTime < 0) {
            if(numEnemy<=maxEnemy) {
                generate();
            }
            else{
                wave++;
                numEnemy=0;
                maxEnemy +=10;
                GEN_INTERVAL-=0.5f;
            }

            enemyTime =  random.nextFloat()*GEN_INTERVAL;

        }
    }

    private void generate() {
        Scene scene = Scene.top();
        if (scene == null) return;

        //wave++;
        //Log.v(TAG, "Generating: wave " + wave);
        for (int i = 0; i < random.nextInt(2+wave-1); i++) {
            numEnemy++;
            switch(random.nextInt(4)%wave){
                case 0:
                    scene.add(MainScene.Layer.enemy, Enemy.get(0, random.nextInt(4)));
                    break;
                case 1:
                    scene.add(MainScene.Layer.enemy, Enemy2.get(0, random.nextInt(4)));
                    break;
                case 2:
                    scene.add(MainScene.Layer.enemy, Enemy3.get(0, random.nextInt(4)));
                    break;
                case 3:
                    scene.add(MainScene.Layer.enemy, Enemy.get(0, random.nextInt(4)));
                    break;
            }

        }
    }

    @Override
    public void draw(Canvas canvas) {

    }
}
