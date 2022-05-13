package model;

import app.Game;
import control.Manager;
import sounds.Sounds;

public class BoxBullet extends Collectable {

	public BoxBullet(int x) {
		super(x);
		this.setSprite("collectable_ammor_box_green.png");
	}

	@Override
	public void bonification() {
		Game.PLAYER.addMunition(100);
		Manager.addMsgBonus(new Bonus(this, 100, "Munition", true));
	}
}
