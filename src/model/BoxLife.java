package model;

import app.Game;
import control.Manager;
import sounds.Sounds;

public class BoxLife extends Collectable {

	public BoxLife(int x) {
		super(x);
		this.setSprite("collectable_life.png");
	}

	@Override
	public void bonification() {
		Game.PLAYER.addLife(10);
		Manager.addMsgBonus(new Bonus(this, 10, "Life", true));
	}

}
