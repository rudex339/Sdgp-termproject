package kr.ac.tukorea.ge.spgp2024.dragonflight.game;

import static java.sql.Types.NULL;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import java.util.ArrayList;

import kr.ac.tukorea.ge.spgp2024.dragonflight.R;
import kr.ac.tukorea.ge.spgp2024.framework.interfaces.IBoxCollidable;
import kr.ac.tukorea.ge.spgp2024.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2024.framework.interfaces.IRecyclable;
import kr.ac.tukorea.ge.spgp2024.framework.objects.AnimSprite;
import kr.ac.tukorea.ge.spgp2024.framework.res.BitmapPool;
import kr.ac.tukorea.ge.spgp2024.framework.scene.RecycleBin;
import kr.ac.tukorea.ge.spgp2024.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2024.framework.util.Gauge;
import kr.ac.tukorea.ge.spgp2024.framework.view.Metrics;

public class Tower2 extends Tower {
    //private static final float SPEED = 3.0f;
    private static final float RADIUS = 0.5f;
    private static final int[] resIds = {
            R.mipmap.medievalpack16x16,
            R.mipmap.alternative_1_25,
            R.mipmap.alternative_1_26,
            R.mipmap.alternative_1_27,
            R.mipmap.alternative_1_28,
            R.mipmap.alternative_1_29,
            R.mipmap.alternative_1_30
    };
    private Tower Target;
    private Tower2(float[] pos, int index) {
        super(pos, index);
        init(pos, index);
    }

    private void init(float[] pos, int index) {

        this.level = 5;
        this.life = this.maxLife = (level + 1) * 6;
        this.attack = false;
        this.enemy_stop = false;
        this.effect_frame = 0;
        this.cooltime = 0.0f;
        this.attackOk = false;
        this.damage = 10;
        //setAnimationResource(resIds[0], ANIM_FPS);
        if(srcRect == null)
            srcRect=new Rect(   61, 38,   61 + 18, 38+22);
        else srcRect.set( 61, 38,   61 + 18, 38+22);


        setPosition(pos[0], pos[1], RADIUS);
    }

    public static Tower2 get(float[] pos, int index) {
        Tower2 tower = (Tower2) RecycleBin.get(Tower.class);
        if (tower != null) {
            tower.init(pos, index);
            return tower;
        }
        return new Tower2(pos, index);
    }
    @Override
    public void update(float elapsedSeconds) {
        if (dstRect.top > Metrics.height) {
            Scene.top().remove(MainScene.Layer.tower, this);
        }
        collisionRect.set(dstRect);
        collisionRect.inset(0.11f, 0.11f);

        attackRect.set(dstRect);
        //attackRect.inset(0.3f, 0.3f);
        if(this.attack){
            if(effect_frame == 5){
                effect_frame = 0;
                attack= false;
            }
            else effect_frame = (float) ((effect_frame+0.5)%6.f);
        }
        else {
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

    @Override
    public RectF getCollisionRect() {
        return collisionRect;
    }


    @Override
    public void onRecycle() {

    }

    public int getScore() {
        return (level + 1) * 100;
    }
    @Override
    public boolean attack(Enemy enemy){

        return false;
    }
    @Override
    public boolean targetAttack(){
        if(attackOk) {
            attackOk = false;
            if(Target!=null) {
                Log.d("Tower", " Target life: " + Target.life);
                Target.decreaseLife(-damage);
                Log.d("Tower", "Target attacked. Damage (healing): " + -damage + ", Target life: " + Target.life);
                return true;
            }
        }
        return false;
    }
    @Override
    public boolean fineTarget(ArrayList<IGameObject> enemies, ArrayList<IGameObject> towers){
        Tower minLifeTower = (Tower)towers.get(0);
        for (IGameObject tower : towers) {
            if (((Tower)tower).life < minLifeTower.life) {
                minLifeTower = (Tower)tower;
            }
        }
        Target = minLifeTower;
        return true;
    }
}