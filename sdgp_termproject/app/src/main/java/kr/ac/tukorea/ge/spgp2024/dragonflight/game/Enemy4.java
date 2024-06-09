package kr.ac.tukorea.ge.spgp2024.dragonflight.game;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

import java.util.ArrayList;

import kr.ac.tukorea.ge.spgp2024.dragonflight.R;
import kr.ac.tukorea.ge.spgp2024.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2024.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2024.framework.res.BitmapPool;
import kr.ac.tukorea.ge.spgp2024.framework.scene.RecycleBin;
import kr.ac.tukorea.ge.spgp2024.framework.util.Gauge;

public class Enemy4 extends Enemy{
    private static final float SPEED = 1.5f;
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
    private Tower Target;
    protected static Gauge gauge = new Gauge(0.1f, R.color.enemy_gauge_fg, R.color.enemy_gauge_bg);

    private Enemy4(int level, int index) {
        super(level, index);
        init(level, index);
        dx = -SPEED;
    }

    private void init(int level, int index) {
        this.level = level;
        this.life = this.maxLife = (level + 1) * 40;
        this.stop = false;
        this.attack = false;
        this.effect_frame = 0;
        this.damage = 3;
        this.cooltime = 0.0f;
        this.attackOk = false;
        setAnimationResource(resIds[0], ANIM_FPS);
        if(srcRect == null)
            srcRect=new Rect(  243, 38,   243 + 11, 38+22);
        else srcRect.set(243, 38,   243 + 11, 38+22);

        setPosition(16.f/3 + 1.f * 7, 3.f+1.f*(index), 0.5f);
        //setPosition(16.f/3 + 1.f * 7, 3.f+1.f*(index), 1.f);
    }

    public static Enemy4 get(int level, int index) {
        Enemy4 enemy = (Enemy4) RecycleBin.get(Enemy4.class);
        if (enemy != null) {
            enemy.init(level, index);
            return enemy;
        }
        return new Enemy4(level, index);
    }
    @Override
    public void update(float elapsedSeconds) {
        if(!stop||!this.attack) {
            super.update(elapsedSeconds);
        }
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
            if(attackOk){
                if(Target!=null){
                    targetAttack();
                }
                attackOk = false;
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
        return false;
    }
    public boolean decreaseLife(int power) {
        life -= power;
        return life <= 0;
    }

    public boolean targetAttack(){
        if(attackOk) {
            attackOk = false;
            if(Target!=null) {
                //Log.d("Tower", " Target life: " + Target.life);
                Target.decreaseLife(damage);
                //Log.d("Tower", "Target attacked. Damage (healing): " + -damage + ", Target life: " + Target.life);
                return true;
            }
        }
        return false;
    }
    @Override
    public boolean fineTarget(ArrayList<IGameObject> enemies, ArrayList<IGameObject> towers){
        Sprite minenemy = (Sprite)towers.get(0);
        double minDistance = calculateDistance(x,y,minenemy.x,minenemy.y);
        ;
        for (IGameObject tower : towers) {
            double distance = calculateDistance(x, y,  ((Sprite)tower).x,  ((Sprite)tower).y);
            if(distance<minDistance) {
                minDistance = distance;
                minenemy = (Sprite) tower;
            }
        }
        Target = (Tower) minenemy;
        return true;
    }
    private double calculateDistance(float x1, float y1, float x2, float y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }
}
