package time;

import robocode.AdvancedRobot;
import robocode.ScannedRobotEvent;
import robocode.WinEvent;

public class TheDodgeRobot extends AdvancedRobot {

	double enemyPreviousEnergy = 120;
	int direction = 1;
	int gun = 1;
	int wallHitBox = 60;

	public void run() {

		setTurnGunRight(99999);

	}

	public void onScannedRobot(ScannedRobotEvent e) {
		//seta posição do robo perpendicular a do inimigo 
		setTurnRight(e.getBearing() + 90 - 30 * direction);

		//se o inimigo atirar, ele vai gastar energia
		//calcula a energia inicial menos a energia atual
		// se for maior que zero e menor ou igual a 3, é porque ele atirou
		double energy = enemyPreviousEnergy - e.getEnergy();
		if (energy > 0 && energy <= 3) {
			//muda de direção
			direction = -direction;
			setAhead((e.getDistance()) * direction);
		}
		
		
		gun = -gun;
		setTurnRadarRight(360 * gun);
		//encontra a diferença do heading do robo e do heading do canhao e soma com o bearing do inimigo
		//isso faz com que a arma esteja sempre apontada pro inimigo
		setTurnGunRight(getHeading() - getGunHeading() + e.getBearing());

		//quando a distancia inimiga aumenta, a força do tiro diminui, para gastar menos energia
		setFire(400 / e.getDistance());
		
		//grava a energia para o proximo scan
		enemyPreviousEnergy = e.getEnergy();

	}

	
	@Override
	public void onWin(WinEvent event) {
		setAhead(99999);
		setTurnRight(9999);
		setTurnGunLeft(99999);
		setTurnRadarRight(99999);
		execute();
	}

}
