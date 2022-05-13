package model;

import java.util.Random;

import app.Game;
import control.Manager;
import sounds.Sounds;
import tool.Settings;

public class BoxClosed extends Collectable {

	public BoxClosed(int x) {
		super(x);
		this.setSprite("collectable_box_closed.png");
	}

	@Override
	public void bonification() {
		Random random = new Random();
		int tipo = random.nextInt(0, 2);
		if (tipo == 0 && Game.PLAYER.getLife() == Settings.PLAYER_DEFAULT_LIFE) {
			tipo = 1;
		}

		int qtd = 0;
		switch (tipo) {
		case 0:
			qtd = random.nextInt(10, 25);
			Manager.addMsgBonus(new Bonus(this, qtd, "Life", true));
			Game.PLAYER.addLife(qtd);
			break;
		case 1:
			qtd = random.nextInt(10, 15);
			Manager.addMsgBonus(new Bonus(this, qtd, "Shild", true));
			Game.PLAYER.addShild(qtd);
			break;
		case 2:
			qtd = random.nextInt(50, 150);
			Manager.addMsgBonus(new Bonus(this, qtd, "Bullet", true));
			Game.PLAYER.setMunition(Game.PLAYER.getMunition() + qtd);
			break;
		}
	}

}
