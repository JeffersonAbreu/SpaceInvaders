package model;

import app.Game;
import control.Manager;
import control.UserControls;
import tool.Settings;

public class Player extends Ship {
	private int munition = 0;
	private boolean right, left, up, down, shot;

	public Player() {
		this.x = Settings.DEFAULT_WINDOW_WIDTH / 2 - Settings.PLAYER_WIDTH / 2;
		this.y = Settings.DEFAULT_WINDOW_HEIGHT - Settings.PLAYER_HEIGHT;
		this.width = Settings.PLAYER_WIDTH;
		this.height = Settings.PLAYER_HEIGHT;
		this.setSprite("nave.png");
		this.munition = 20;
		this.addShild(Settings.PLAYER_DEFAULT_SHILD);
		this.addLife(Settings.PLAYER_DEFAULT_LIFE);
	}

	public void setMunition(int qtd) {
		this.munition = qtd;
	}
	public void addMunition(int qtd) {
		this.munition += qtd;
	}

	public void upSpeed() {
		this.setSpeedHorz(1);
		this.setSpeedVert(1);
	}

	public void moverHorz() {
		if (this.right) {
			this.x += this.getSpeedHorz();
		} else if (this.left) {
			this.x -= this.getSpeedHorz();
		}

		if (x + width > Settings.DEFAULT_WINDOW_WIDTH) {
			x = Settings.DEFAULT_WINDOW_WIDTH - width;
		}

		else if (x < 0) {
			x = 0;
		}
	}

	public void moverVert() {
		if (this.down) {
			y += this.getSpeedVert();
		} else if (this.up) {
			y -= this.getSpeedVert();
		}

		if (y + height > Settings.DEFAULT_WINDOW_HEIGHT) {
			y = Settings.DEFAULT_WINDOW_HEIGHT - height;
		} else if (y < Settings.PLAYER_HEIGHT_MAX) {
			y = Settings.PLAYER_HEIGHT_MAX;
		}
	}

	public void controlar() {
		this.left = UserControls.isLeft();
		this.right = UserControls.isRight();
		this.up = UserControls.isUp();
		this.down = UserControls.isDown();
		this.shot = UserControls.isShot();
		this.moverHorz();
		this.moverVert();

		if (this.shot && this.munition > 0) {
			this.shoot();
		}

	}

	@Override
	public void update() {
		this.controlar();
	}

	@Override
	public void shoot() {
		Manager.addBulletPlayer((int) this.getCenterX(), this.y);
		this.munition--;
		Game.FIRE = this.munition;
		UserControls.setShot(false);
	}

	public int getMunition() {
		return munition;
	}
}
