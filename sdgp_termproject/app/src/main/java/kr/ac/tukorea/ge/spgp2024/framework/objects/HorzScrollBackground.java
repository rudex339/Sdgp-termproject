package kr.ac.tukorea.ge.spgp2024.framework.objects;

import android.graphics.Canvas;

import kr.ac.tukorea.ge.spgp2024.framework.view.Metrics;


public class HorzScrollBackground extends Sprite {
    private final float speed;
    private final float width; // Change to width for horizontal scrolling
    public HorzScrollBackground(int bitmapResId, float speed) {
        super(bitmapResId);
        this.width = bitmap.getWidth() * Metrics.height / bitmap.getHeight(); // Calculate width based on bitmap's aspect ratio
        setPosition(Metrics.width / 2, Metrics.height / 2, width, Metrics.height);
        this.speed = speed;
    }
    @Override
    public void update(float elapsedSeconds) {
        this.x += speed * elapsedSeconds; // Update x-coordinate for horizontal scrolling
    }

    @Override
    public void draw(Canvas canvas) {
        float curr = x % width; // Calculate current position based on x-coordinate
        if (curr > 0) curr -= width;
        while (curr < Metrics.width) {
            dstRect.set(curr, 0, curr + width, Metrics.height); // Adjust dstRect for horizontal scrolling
            canvas.drawBitmap(bitmap, null, dstRect, null);
            curr += width;
        }
    }
}