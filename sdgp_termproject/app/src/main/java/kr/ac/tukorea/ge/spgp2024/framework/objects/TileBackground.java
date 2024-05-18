package kr.ac.tukorea.ge.spgp2024.framework.objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

import kr.ac.tukorea.ge.spgp2024.dragonflight.game.Enemy;
import kr.ac.tukorea.ge.spgp2024.dragonflight.game.MainScene;
import kr.ac.tukorea.ge.spgp2024.framework.interfaces.IBoxCollidable;
import kr.ac.tukorea.ge.spgp2024.framework.view.Metrics;
public class TileBackground extends Sprite implements IBoxCollidable {

 // Change to width for horizontal scrolling
 private final Bitmap MapBitmap;
    private final Bitmap TileBitmap;
    private final Bitmap CatleBitmap;

    protected RectF collisionRect = new RectF();
    public TileBackground(int bitmapResId) {
        super(bitmapResId);
        this.width = bitmap.getWidth() * Metrics.height / bitmap.getHeight();// Calculate width based on bitmap's aspect ratio
        //setPosition(Metrics.width / 2, Metrics.height / 2, width, Metrics.height);
        setPosition(Metrics.width / 2, Metrics.height / 2, Metrics.width, height);
        //this.speed = speed;
        int x = 0;
        int y = 594;
        int width = 789;
        int height = 430;
        MapBitmap = Bitmap.createBitmap(bitmap, x, y, width, height);

        x = 82;
        y = 222;
        width = 16;
        height = 18;
        TileBitmap = Bitmap.createBitmap(bitmap, x, y, width, height);

        x = 488;
        y = 220;
        width = 64;
        height = 148;
        CatleBitmap = Bitmap.createBitmap(bitmap, x, y, width, height);
    }
    @Override
    public void update(float elapsedSeconds) {
        collisionRect.set(dstRect);
        collisionRect.inset(-0.55f, -50.11f);
        //this.x += speed * elapsedSeconds; // Update x-coordinate for horizontal scrolling
    }

    @Override
    public void draw(Canvas canvas) {

        dstRect.set(0, 0, this.width, Metrics.height);
        canvas.drawBitmap(MapBitmap, null, dstRect, null);

        float tileWidth = this.width/16;
        float tileHight = Metrics.height/9;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 4; j++) {
                dstRect.set(this.width/3 + tileWidth * i,  Metrics.height/3+tileHight*j,
                        this.width/3 +tileWidth * (i + 1), Metrics.height/3+tileHight*(j+1));
                canvas.drawBitmap(TileBitmap, null, dstRect, null);
            }
        }
        float x = this.width/6;
        float y = Metrics.height/9;
        dstRect.set(x, y, x+this.width/8, y+Metrics.height/2);
        canvas.drawBitmap(CatleBitmap, null, dstRect, null);
    }

    @Override
    public RectF getCollisionRect() {
        return collisionRect;
    }
}
