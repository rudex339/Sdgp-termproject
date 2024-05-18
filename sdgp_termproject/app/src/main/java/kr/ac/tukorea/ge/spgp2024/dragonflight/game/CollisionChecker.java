package kr.ac.tukorea.ge.spgp2024.dragonflight.game;

import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;

import kr.ac.tukorea.ge.spgp2024.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2024.framework.objects.TileBackground;
import kr.ac.tukorea.ge.spgp2024.framework.util.CollisionHelper;

public class CollisionChecker implements IGameObject {
    private static final String TAG = CollisionChecker.class.getSimpleName();
    private final MainScene scene;

    public CollisionChecker(MainScene scene) {
        this.scene = scene;
    }

    @Override
    public void update(float elapsedSeconds) {
        ArrayList<IGameObject> enemies = scene.objectsAt(MainScene.Layer.enemy);
        TileBackground catle = (TileBackground)scene.objectsAt(MainScene.Layer.bg).get(0);
        for (int e = enemies.size() - 1; e >= 0; e--) {
            Enemy enemy = (Enemy)enemies.get(e);
            if(CollisionHelper.collides(enemy, catle)){
                scene.remove(MainScene.Layer.enemy, enemy);
            }
            else {
                ArrayList<IGameObject> towers = scene.objectsAt(MainScene.Layer.tower);
                for (int b = towers.size() - 1; b >= 0; b--) {
                    Tower tower = (Tower) towers.get(b);
                    if (CollisionHelper.collides(enemy, tower)) {
                        //Log.d(TAG, "Collision !!");
                        //scene.remove(MainScene.Layer.bullet, bullet);
                        // boolean dead = enemy.decreaseLife(bullet.getPower());
                        //if (dead) {
                        //scene.remove(MainScene.Layer.enemy, enemy);
                        //scene.addScore(enemy.getScore());
                        //}
                        enemy.CollisionAction();
                        tower.setEnemyStop(true);
                        boolean Edead = false;
                        boolean Tdead = false;
                        if (tower.attack(enemy)) {
                            Edead = enemy.decreaseLife(tower.damage);
                        }
                        if (enemy.attack(tower)) {
                            Tdead = tower.decreaseLife(enemy.damage);
                        }
                        if (Edead) {
                            scene.remove(MainScene.Layer.enemy, enemy);
                            //scene.addScore(enemy.getScore());
                        }
                        if (Tdead) {
                            scene.remove(MainScene.Layer.tower, tower);
                            //scene.addScore(enemy.getScore());
                        }
                        //attaack
                        break;
                    }


                }
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {

    }
}
