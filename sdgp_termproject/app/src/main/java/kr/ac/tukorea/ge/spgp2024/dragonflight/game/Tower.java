package kr.ac.tukorea.ge.spgp2024.dragonflight.game;

import static kr.ac.tukorea.ge.spgp2024.dragonflight.game.MainScene.Layer.enemy;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

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
import kr.ac.tukorea.ge.spgp2024.dragonflight.game.Enemy;

public class Tower extends AnimSprite implements IBoxCollidable, IRecyclable {
    private static final float SPEED = 3.0f;
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

    public static final int MAX_LEVEL = resIds.length - 1;
    public static final float ANIM_FPS = 0.0f;
    public RectF collisionRect = new RectF();
    public RectF attackRect = new RectF();
    public  boolean attackOk;
    public int level;
    public int life, maxLife;
    public int damage;
    public float effect_frame;

    public boolean attack, enemy_stop;
    public float cooltime;
    public static Gauge gauge = new Gauge(0.1f, R.color.enemy_gauge_fg, R.color.enemy_gauge_bg);

    public Tower(float[] pos, int index) {
        super(0, 1);
        init(pos, index);
    }

    private void init(float[] pos, int index) {
        this.level = 5;
        this.life = this.maxLife = (level + 1) * 30;
        this.attack = false;
        this.enemy_stop = false;
        this.effect_frame = 0;
        this.cooltime = 0.0f;
        this.attackOk = false;
        this.damage = 10;
        setAnimationResource(resIds[0], ANIM_FPS);
        if(srcRect == null)
            srcRect=new Rect(  107, 44,   107 + 13, 44+16);
        else srcRect.set(107, 44,   107 + 13, 44+16);


        setPosition(pos[0], pos[1], RADIUS);
    }

    public static Tower get(float[] pos, int index) {
        Tower tower = (Tower) RecycleBin.get(Tower.class);
        if (tower != null) {
            tower.init(pos, index);
            return tower;
        }
        return new Tower(pos, index);
    }
    @Override
    public void update(float elapsedSeconds) {
        super.update(elapsedSeconds);
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
        else if(enemy_stop){
            if(cooltime<= 0.0f){
                attackOk= true;
                attack = true;
                cooltime = 1.0f;
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

    public void setEnemyStop(boolean input){
        this.enemy_stop = input;
    }
    public boolean getEnemyStop(){
        return this.enemy_stop;
    }
    @Override
    public void onRecycle() {

    }

    public int getScore() {
        return (level + 1) * 100;
    }

    public boolean fineTarget(ArrayList<IGameObject> enemies, ArrayList<IGameObject> towers){
        return true;
    }
    public boolean attack(Enemy enemy){
        if(attackOk) {
            attackOk = false;
            //enemy.decreaseLife(10);
            return true;
        }
            return false;
    }
    public boolean targetAttack(){

        return false;
    }
    public boolean decreaseLife(int power) {
        life -= power;
        if(life >= maxLife) life= maxLife;
        return life <= 0;
    }
}
