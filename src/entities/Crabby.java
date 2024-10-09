package entities;
import main.Game;

import static utilz.Constants.Directions.LEFT;
import static utilz.Constants.EnemyConstants.*;
import static utilz.HelpMethods.*;

public class Crabby extends Enemy{
    public Crabby(float x, float y) {
        super(x, y, CRABBY_WIDTH, CRABBY_HEIGHT, CRABBY);
        initHitbox(x, y, (int)(22 * Game.SCALE), (int)(19 * Game.SCALE));
    }

    public void update(int[][] lvldata, Player player) {
        updateMove(lvldata, player);
        updateAnimationTick();
    }

    private void updateMove(int[][] lvldata, Player player) {// Giong voi player
        if(firstUpdate)
            firstUpdateCheck(lvldata);

        if(inAir) {
            updateInAir(lvldata);
        }
        else {
            switch (enemyState) {
                case IDLE:
                    newState(RUNNING);
                    break;
                case RUNNING:
                    if(canSeePlayer(lvldata, player))
                        turnTowardsPlayer(player);
                    if(isPlayerCloseforAttack(player))
                        newState(ATTACK);

                    move(lvldata);
                    break;
            }
        }

    }

}
