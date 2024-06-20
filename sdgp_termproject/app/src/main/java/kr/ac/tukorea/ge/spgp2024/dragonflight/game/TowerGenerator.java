package kr.ac.tukorea.ge.spgp2024.dragonflight.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.MotionEvent;

import java.util.Random;

import kr.ac.tukorea.ge.spgp2024.dragonflight.R;
import kr.ac.tukorea.ge.spgp2024.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2024.framework.res.BitmapPool;
import kr.ac.tukorea.ge.spgp2024.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2024.framework.view.Metrics;

public class TowerGenerator implements IGameObject {
    private static final String TAG = TowerGenerator.class.getSimpleName();
    public static final float GEN_INTERVAL = 1.0f;
    private final Random random = new Random();
    private final Bitmap TBmp;
    private float enemyTime = 0;
    private Rect[] srcRect= {new Rect(  107, 44,   107 + 13, 44+16),
            new Rect(  83, 43,   83 + 17, 43+17),
            new Rect(  61, 38,   61 + 18, 38+22),
            new Rect(  1, 38,   1 + 21, 38+22)};
    private float[] pts;
    private boolean[][] tilecheck= new boolean[8][4];
    public int choose_Tower = -1,cost=10,upcost=1;
    public TowerGenerator() {
        TBmp = BitmapPool.get(R.mipmap.medievalpack16x16);

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 4; j++) {
                tilecheck[i][j] = true; // 모든 요소를 false로 초기화
            }
        }
    }

    @Override
    public void update(float elapsedSeconds) {
        enemyTime -= elapsedSeconds;//특정시간때마다 generate를 호출하여 적을 생성
        if (enemyTime < 0) {
            cost += upcost;
            enemyTime = GEN_INTERVAL;
        }
    }

    private void generate() {


        //Log.v(TAG, "Generating: wave " + wave);
        //for (int i = 0; i < 5; i++) {
            //scene.add(MainScene.Layer.enemy, Enemy.get(0, i));
        //}
    }

    @Override
    public void draw(Canvas canvas) {
        if(choose_Tower != -1){
            //Rect srcRect=new Rect(  107, 44,   107 + 13, 44+16);
            RectF dstRect = new RectF();
            dstRect.set(pts[0] -  0.7f, pts[1] -  0.7f,
                    pts[0] +  0.7f, pts[1] +  0.7f);
            canvas.drawBitmap(TBmp, srcRect[choose_Tower], dstRect, null);
        }
    }

    public boolean onTouch(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                //choose_Tower= 0;
                //if(choose_Tower != -1)
                    pts = Metrics.fromScreen(event.getX(), event.getY());
                //setTargetX(pts[0]);
                return true;
            case MotionEvent.ACTION_UP:
                Scene scene = Scene.top();
                if (scene == null) return true;
                if(choose_Tower != -1) {
                    int cellX = Math.round(pts[0] - 16.f / 3 - 0.5f); // x축에서 가장 가까운 칸의 인덱스 계산
                    int cellY = Math.round(pts[1] - 3.f); // y축에서 가장 가까운 칸의 인덱스 계산

                    int numColumns = 8; // 가로 칸의 갯수
                    int numRows = 4; // 세로 칸의 갯수

                    pts[0] = Math.max(6, Math.min(cellX + 6, 6 + numColumns - 1)); // x좌표를 (6, 6 + numColumns - 1) 범위 내로 조정
                    pts[1] = Math.max(3, Math.min(cellY + 3, 3 + numRows - 1));

                    if (cellX >= 0 && cellX < numColumns && cellY >= 0 && cellY < numRows) {
                        if (tilecheck[cellX][cellY]) {
                            // 칸이 비어 있으면 오브젝트를 배치하고 tileCheck 값을 변경
                            scene.add(MainScene.Layer.tower, Tower.get(pts, choose_Tower));
                            tilecheck[cellX][cellY] = false;
                        }

                    }
                    choose_Tower = -1;
                }
                return true;
        }
        return false;
    }
}
