package kr.ac.tukorea.ge.spgp2024.dragonflight.game;

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

public class Enemy extends AnimSprite implements IBoxCollidable, IRecyclable {
    private static final float SPEED = 0.5f;
    private static final float RADIUS = 0.3f;
    private static final int[] resIds = {
            R.mipmap.medievalpack16x16,
            R.mipmap.alternative_3_07,
            R.mipmap.alternative_3_08,
            R.mipmap.alternative_3_09,
            R.mipmap.alternative_3_10,
            R.mipmap.alternative_3_11,
            R.mipmap.alternative_3_12};
    public static final int MAX_LEVEL = resIds.length - 1;
    public static final float ANIM_FPS = 10.0f;
    public int damage;
    protected RectF collisionRect = new RectF();
    protected RectF attackRect = new RectF();
    protected  boolean attackOk;
    private int level;
    private int life, maxLife;
    private float effect_frame;
    private boolean attack;
    private boolean stop;
    private float cooltime;
    protected static Gauge gauge = new Gauge(0.1f, R.color.enemy_gauge_fg, R.color.enemy_gauge_bg);

    public Enemy(int level, int index) {
        super(0, 0);
        init(level, index);
        dx = -SPEED;
    }

    private void init(int level, int index) {
        this.level = level;
        this.life = this.maxLife = (level + 1) * 40;
        this.stop = false;
        this.attack = false;
        this.effect_frame = 0;
        this.damage = 5;
        this.cooltime = 0.0f;
        this.attackOk = false;
        setAnimationResource(resIds[0], ANIM_FPS);
        if(srcRect == null)
            srcRect=new Rect(  140, 44,   140 + 13, 44+16);
        else srcRect.set(140, 44,   140 + 13, 44+16);

        setPosition(16.f/3 + 1.f * 7, 3.f+1.f*(index), 0.5f);
        //setPosition(16.f/3 + 1.f * 7, 3.f+1.f*(index), 1.f);
    }

    public static Enemy get(int level, int index) {
        Enemy enemy = (Enemy) RecycleBin.get(Enemy.class);
        if (enemy != null) {
            enemy.init(level, index);
            return enemy;
        }
        return new Enemy(level, index);
    }
    @Override
    public void update(float elapsedSeconds) {
        if(!stop)
            super.update(elapsedSeconds);
        //if (dstRect.right < 16.f/3) {
        //    Scene.top().remove(MainScene.Layer.enemy, this);
        //}

        collisionRect.set(dstRect);
        collisionRect.inset(0.11f, 0.11f);

        dx = -SPEED;

        attackRect.set(dstRect);
        //attackRect.inset(0.3f, 0.3f);
        if(this.attack){
            if(effect_frame == 5){
                effect_frame = 0;
                attack= false;
            }
            else effect_frame = (float) ((effect_frame+0.5)%6.f);
        }
        else if(stop) {
            if (cooltime <= 0.0f) {
                attackOk = true;
                attack = true;
                cooltime = 3.f;
            } else {
                cooltime -= elapsedSeconds;
            }
        }
        stop=false;
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

    public void CollisionAction() {
        stop=true;
        //dx = 0;
    }
    @Override
    public void onRecycle() {

    }

    public int getScore() {
        return (level + 1) * 100;
    }
    public boolean attack(Tower tower){
        if(attackOk) {
            attackOk = false;
            //enemy.decreaseLife(10);
            return true;
        }
        return false;
    }
    public boolean decreaseLife(int power) {
        life -= power;
        return life <= 0;
    }

    public boolean fineTarget(ArrayList<IGameObject> enemies, ArrayList<IGameObject> towers){
        return true;
    }
    public boolean targetAttack(){

        return false;
    }
}
