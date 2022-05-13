package model;

import sounds.Sounds;
import tool.Settings;

public abstract class Destructible extends GameObject {
	private static final long serialVersionUID = 1L;
	private int shild = 0;

	public int getShild() {
		return this.shild;
	}

	public void impact(int dano) {
		if (this.shild >= dano) {
			this.shild -= dano;
		} else {
			dano -= this.shild;
			this.shild = 0;
			this.setLife(this.getLife() - dano * Settings.DAMAGE_BULLET_MULT);
			if (this.getLife() <= 0) {
				this.setDeadth();
				this.setLife(0);
			}
		}
		Sounds.play(Sounds.FIRE_STORM2);
	}

	public void addLife(int bonus) {
		this.setLife(bonus + this.getLife());
		if (this.getLife() > Settings.PLAYER_DEFAULT_LIFE)
			this.setLife(Settings.PLAYER_DEFAULT_LIFE);
	}

	public void addShild(int bonus) {
		this.shild += bonus;
		if (this.shild > Settings.PLAYER_DEFAULT_SHILD * 2)
			this.shild = Settings.PLAYER_DEFAULT_SHILD * 2;
	}
}
