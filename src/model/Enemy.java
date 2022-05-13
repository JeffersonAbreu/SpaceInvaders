package model;

import control.Manager;
import tool.Settings;

public class Enemy extends Ship {
	private int timer = 0;

	public Enemy(int x) {
		this.x = x;
		this.y = 0;
		this.width = Settings.PLAYER_WIDTH;
		this.height = Settings.PLAYER_HEIGHT;
		this.setSprite("enemy1.png");
		this.addLife(10);
	}

	@Override
	public void shoot() {
		Manager.addBulletEnemy((int) this.getCenterX(), (int) this.getCenterY());
	}

	@Override
	public void update() {
		timer++;
		if (timer % Settings.SPEED_GAME_OBJECT == 0) {
			moverVert();
		}
		if (timer % 50 == 0) {
			shoot();
		}
	}

}
