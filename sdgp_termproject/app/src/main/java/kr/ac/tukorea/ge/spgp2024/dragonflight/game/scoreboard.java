package kr.ac.tukorea.ge.spgp2024.dragonflight.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import kr.ac.tukorea.ge.spgp2024.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2024.framework.objects.Score;
import kr.ac.tukorea.ge.spgp2024.framework.view.Metrics;

public class scoreboard implements IGameObject {
    private int num_mon_kill, hp, allscore;
    private boolean success;
    private Paint paint;
    public scoreboard(Score score, boolean suc){
        num_mon_kill = score.kill;
        hp = score.hp;
        success = score.clear;
        allscore = num_mon_kill*50 + hp*100;
        if(success)allscore+=500;

        paint = new Paint();
        paint.setTextSize(1);
        paint.setColor(Color.RED);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTypeface(Typeface.DEFAULT_BOLD);
        paint.setLetterSpacing(0.1f);
    }
    @Override
    public void update(float elapsedSeconds) {

    }

    @Override
    public void draw(Canvas canvas) {


        // 화면의 가운데 좌표 계산, h = ;
        float canvasWidth = Metrics.width;
        float canvasHeight = Metrics.height;
        float centerX = canvasWidth / 2;
        float centerY = canvasHeight / 2;

        // 출력할 텍스트 설정
        String killText = "KILL: " + num_mon_kill;
        String hpText = "HP: " + hp;
        String successText = success ? "sucess" : "fail";
        String scoreText = "score: " + allscore;

        // 각 텍스트 줄의 y 좌표 계산
        float lineHeight = paint.descent() - paint.ascent();
        float textY = centerY - (1.5f * lineHeight); // 첫 번째 텍스트 줄의 y 좌표

        // 텍스트 출력
        canvas.drawText(killText, centerX, textY, paint);
        textY += lineHeight; // 다음 줄로 이동
        canvas.drawText(hpText, centerX, textY, paint);
        textY += lineHeight; // 다음 줄로 이동
        canvas.drawText(successText, centerX, textY, paint);
        textY += lineHeight; // 다음 줄로 이동
        canvas.drawText(scoreText, centerX, textY, paint);
    }

    private void drawTextWithSpacingAdjustment(Canvas canvas, String text, float x, float y, Paint paint) {
        float letterSpacing = paint.getLetterSpacing();
        float currentX = x - (paint.measureText(text) / 2);

        for (char c : text.toCharArray()) {
            canvas.drawText(String.valueOf(c), currentX, y, paint);
            currentX += paint.measureText(String.valueOf(c))/2+0.01f;
        }
    }
}
