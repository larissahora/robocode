package time;

import robocode.AdvancedRobot;
import robocode.BulletHitEvent;
import robocode.HitByBulletEvent;
import robocode.ScannedRobotEvent;

public class Robo extends AdvancedRobot {

	double enemyPreviousEnergy = 100;
	int direction = 1;
	int gun = 1;
	int wallHitBox = 60;

	public void run() {
		setTurnGunRight(99999);
		execute();
	}

	public void onScannedRobot(ScannedRobotEvent e) {
		setTurnRight(e.getBearing() + 90 - 30 * direction);

		if (getVelocity() == 0) {
			System.out.println("parei");

			setAhead(1000 * direction);

		}

		double energy = enemyPreviousEnergy - e.getEnergy();
		if (energy > 0 && energy <= 3) {
			direction = -direction;
			setAhead((e.getDistance() / 4 + 25) * direction);

		}

		gun = -gun;
		setTurnGunRight(99999 * gun);
		fire(2);

		enemyPreviousEnergy = e.getEnergy();
	}

	public boolean closeToTheWall() {
		return (getX() <= wallHitBox || getX() >= getBattleFieldWidth() - wallHitBox || getY() <= wallHitBox
				|| getY() >= getBattleFieldHeight() - wallHitBox);
	}

	@Override
	public void onHitByBullet(HitByBulletEvent event) {
		double enemyRecharge = event.getPower() / 3;
		enemyPreviousEnergy = enemyPreviousEnergy + enemyRecharge;
	}

	@Override
	public void onBulletHit(BulletHitEvent event) {
		enemyPreviousEnergy = event.getEnergy() - 6;
	}

}
