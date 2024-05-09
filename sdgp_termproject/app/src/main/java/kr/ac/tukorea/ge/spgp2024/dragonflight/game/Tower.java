package kr.ac.tukorea.ge.spgp2024.dragonflight.game;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

import kr.ac.tukorea.ge.spgp2024.dragonflight.R;
import kr.ac.tukorea.ge.spgp2024.framework.interfaces.IBoxCollidable;
import kr.ac.tukorea.ge.spgp2024.framework.interfaces.IRecyclable;
import kr.ac.tukorea.ge.spgp2024.framework.objects.AnimSprite;
import kr.ac.tukorea.ge.spgp2024.framework.scene.RecycleBin;
import kr.ac.tukorea.ge.spgp2024.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2024.framework.util.Gauge;
import kr.ac.tukorea.ge.spgp2024.framework.view.Metrics;

public class Tower extends AnimSprite implements IBoxCollidable, IRecyclable {
    private static final float SPEED = 3.0f;
    private static final float RADIUS = 0.5f;
    private static final int[] resIds = {
            R.mipmap.medievalpack16x16};

    public static final int MAX_LEVEL = resIds.length - 1;
    public static final float ANIM_FPS = 10.0f;
    protected RectF collisionRect = new RectF();
    private int level;
    private int life, maxLife;
    protected static Gauge gauge = new Gauge(0.1f, R.color.enemy_gauge_fg, R.color.enemy_gauge_bg);

    private Tower(float[] pos, int index) {
        super(0, 1);
        init(pos, index);
    }

    private void init(float[] pos, int index) {
        this.level = level;
        this.life = this.maxLife = (level + 1) * 10;
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
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, srcRect, dstRect, null);
       canvas.save();



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

    public boolean decreaseLife(int power) {
        life -= power;
        return life <= 0;
    }
}
