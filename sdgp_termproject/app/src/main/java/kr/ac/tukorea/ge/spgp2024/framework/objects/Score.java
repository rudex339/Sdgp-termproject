package kr.ac.tukorea.ge.spgp2024.framework.objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import kr.ac.tukorea.ge.spgp2024.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2024.framework.res.BitmapPool;

public class Score implements IGameObject {
    private final Bitmap bitmap;
    private final float right, top, dstCharWidth, dstCharHeight;
    private final Rect srcRect = new Rect();
    private final RectF dstRect = new RectF();
    private final int srcCharWidth, srcCharHeight;
    private int score, displayScore;

    public Score(int mipmapId, float right, float top, float width) {
        this.bitmap = BitmapPool.get(mipmapId);
        this.right = right;
        this.top = top;
        this.dstCharWidth = width;
        this.srcCharWidth = bitmap.getWidth() / 10;
        this.srcCharHeight = bitmap.getHeight();
        this.dstCharHeight = dstCharWidth * srcCharHeight / srcCharWidth;
    }

    public void setScore(int score) {
        this.score = this.displayScore = score;
    }

    @Override
    public void update(float elapsedSeconds) {
        int diff = score - displayScore;
        if (diff == 0) return;
        if (-10 < diff && diff < 0) {
            displayScore--;
        } else if (0 < diff && diff < 10) {
            displayScore++;
        } else {
            displayScore += diff / 10;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        //int value = this.displayScore;
        int value=10;
        float x = right;
        Paint paint = new Paint();
        paint.setTextSize(1);  // 텍스트 크기 설정
        paint.setColor(Color.RED);  // 텍스트 색상 설정
        paint.setTextAlign(Paint.Align.RIGHT);  // 텍스트 정렬 설정

        // 텍스트를 문자열로 변환
        String text = String.valueOf(value);

        // 텍스트 출력
        canvas.drawText("hp | "+text, x, top + dstCharHeight, paint);
    }

    public void add(int amount) {
        score += amount;
    }

    public int getScore() {
        return score;
    }
}
