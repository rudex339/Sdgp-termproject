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
    public int hp, displayScore, kill=0,numEnemy=0;
    public boolean clear=false;
    public Score(int mipmapId, float right, float top, float width) {
        this.bitmap = BitmapPool.get(mipmapId);
        this.right = right;
        this.top = top;
        this.dstCharWidth = width;
        this.srcCharWidth = bitmap.getWidth() / 10;
        this.srcCharHeight = bitmap.getHeight();
        this.dstCharHeight = dstCharWidth * srcCharHeight / srcCharWidth;
    }

    public void setHp(int score) {
        this.hp = this.displayScore = score;
    }

    @Override
    public void update(float elapsedSeconds) {
        int diff = hp - displayScore;
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
        int value= hp;
        float x = right-4.5f;
        Paint paint = new Paint();
        paint.setTextSize(1);  // 텍스트 크기 설정
        paint.setColor(Color.RED);  // 텍스트 색상 설정
        paint.setTextAlign(Paint.Align.LEFT);  // 텍스트 정렬 설정
        // 텍스트를 문자열로 변환
        String text = String.valueOf(value);
        text = "hp "+text;
        // 텍스트 출력
        float spacing = 1.0f;  // 문자 사이 간격

        // 각 문자 개별 출력
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            // 개별 문자의 폭을 측정
            float charWidth = paint.measureText(String.valueOf(c));
            canvas.drawText(String.valueOf(c), x, top + (dstCharHeight/2), paint);
            x += spacing;  // 문자 너비에 간격을 추가하여 x 좌표 이동
        }
    }

    public void add(int amount) {
        hp += amount;
    }

    public int getHp() {
        return hp;
    }
}
