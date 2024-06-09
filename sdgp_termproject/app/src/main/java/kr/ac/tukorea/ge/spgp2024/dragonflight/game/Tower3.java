package kr.ac.tukorea.ge.spgp2024.dragonflight.game;

import android.graphics.Canvas;
import android.graphics.Rect;

import kr.ac.tukorea.ge.spgp2024.dragonflight.R;
import kr.ac.tukorea.ge.spgp2024.framework.res.BitmapPool;
import kr.ac.tukorea.ge.spgp2024.framework.scene.RecycleBin;
import kr.ac.tukorea.ge.spgp2024.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2024.framework.view.Metrics;

public class Tower3 extends Tower{
    private static final float RADIUS = 0.5f;
    private static final int[] resIds = {
            R.mipmap.medievalpack16x16,
            R.mipmap.alternative_3_07,
            R.mipmap.alternative_3_08,
            R.mipmap.alternative_3_09,
            R.mipmap.alternative_3_10,
            R.mipmap.alternative_3_11,
            R.mipmap.alternative_3_12
    };
    public Tower3(float[] pos, int index) {
        super(pos, index);
        init(pos, index);
    }
    private void init(float[] pos, int index) {
        this.level = 5;
        this.life = this.maxLife = (level + 1) * 23-40;
        this.attack = false;
        this.enemy_stop = false;
        this.effect_frame = 0;
        this.cooltime = 0.0f;
        this.attackOk = false;
        this.damage = 50;
        setAnimationResource(resIds[0], ANIM_FPS);
        if(this.srcRect == null)
            this.srcRect=new Rect(  83, 43,   83 + 17, 43+17);
        else this.srcRect.set(83, 43,   83 + 17, 43+17);


        setPosition(pos[0], pos[1], RADIUS);
    }

    public static Tower get(float[] pos, int index) {
        Tower3 tower = (Tower3) RecycleBin.get(Tower.class);
        if (tower != null) {
            tower.init(pos, index);
            return tower;
        }
        return new Tower3(pos, index);
    }

    @Override
    public void update(float elapsedSeconds) {
        //super.update(elapsedSeconds);
        if (dstRect.top > Metrics.height) {
            Scene.top().remove(MainScene.Layer.tower, this);
        }
        collisionRect.set(dstRect);
        collisionRect.inset(0.11f, 0.11f);

        attackRect.set(dstRect);
        attackRect.inset(-2.f,-2.f);
        //attackRect.inset(0.3f, 0.3f);
        if(this.attack){
            if(effect_frame == 5){
                effect_frame = 0;
                attack= false;
            }
            else effect_frame = (float) ((effect_frame+0.5)%6.f);
        }
        else if(enemy_stop){
            if(cooltime<= 0.0f){
                attackOk= true;
                attack = true;
                cooltime = 1.5f;
            }
            else{
                cooltime -= elapsedSeconds;
            }
        }
        enemy_stop=false;
    }
    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, srcRect, dstRect, null);
        if(attack){
            canvas.drawBitmap(BitmapPool.get(resIds[(int)effect_frame +1])
                    , null, attackRect, null);

        }
        canvas.save();



        float width = dstRect.width();
        canvas.translate(x - width / 2, dstRect.bottom);
        canvas.scale(width, width);
        gauge.draw(canvas, (float)life / maxLife);
        canvas.restore();
    }
}
