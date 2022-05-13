package model;

import control.Manager;
import sounds.Sounds;
import tool.Settings;

public abstract class Collectable extends GameObject {

	public Collectable(int x) {
		this.x = x;
		this.y = 0;
		this.width = Settings.PLAYER_WIDTH / 2;
		this.height = Settings.PLAYER_HEIGHT / 2;
		this.setLife(2);
	}

	@Override
	public void impact(int dano) {
		this.setLife(this.getLife() - dano);
		if (this.getLife() <= 0) {
			this.setDeadth();
			this.setLife(0);
			this.bonification();
			Manager.refreshINFOs();
		}
	}

	public abstract void bonification();
}
