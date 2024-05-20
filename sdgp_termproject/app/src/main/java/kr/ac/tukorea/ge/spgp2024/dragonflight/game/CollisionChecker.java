package kr.ac.tukorea.ge.spgp2024.dragonflight.game;

import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;

import kr.ac.tukorea.ge.spgp2024.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2024.framework.objects.TileBackground;
import kr.ac.tukorea.ge.spgp2024.framework.scene.Failscene;
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
        TileBackground castle = (TileBackground)scene.objectsAt(MainScene.Layer.bg).get(0);
        for (int e = enemies.size() - 1; e >= 0; e--) {
            Enemy enemy = (Enemy)enemies.get(e);
            if(CollisionHelper.collides(enemy, castle)){
                scene.score.numEnemy++;
                scene.remove(MainScene.Layer.enemy, enemy);
                scene.addScore(-1);
                if(scene.getScore() <=0){
                    new Failscene(scene.score).push();
                }
            }
            else {
                ArrayList<IGameObject> towers = scene.objectsAt(MainScene.Layer.tower);
                for (int b = towers.size() - 1; b >= 0; b--) {
                    Tower tower = (Tower) towers.get(b);
                    if (CollisionHelper.collides(enemy, tower)) {

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
                            scene.score.kill++;
                            scene.score.numEnemy++;
                            scene.remove(MainScene.Layer.enemy, enemy);
                            if( scene.score.numEnemy>=50){
                                scene.score.clear =true;
                                new Failscene(scene.score).push();
                            }
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
