package com.mycompany.a3;
import java.util.Observable;
import java.util.Observer;

import com.codename1.ui.Container;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.FlowLayout;

public class ScoreView extends Container implements Observer {
	private GameWorld gw;
	private Label time, live, lastBase, energyLevel, damageLevel, sound;

	public ScoreView() {
		this.setLayout(new FlowLayout());

		setTime();
		setLive();
		setBase();
		setEnergy();
		setDamage();
		setSound();
	}

	// Set up Time value
	public void setTime() {
		Label timeL = new Label("Time:");
		//this.time.setText("" + Integer.toString(gw.getTime()));
		time = new Label("0");
		time.getAllStyles().setPaddingRight(8);
		this.add(timeL);
		this.add(time);
	}

	// Set up Lives left value
	public void setLive() {
		Label liveL = new Label("Lives Left:");
		//this.live.setText("" + Integer.toString(gw.getLive()));
		live = new Label("3");
		this.live.getAllStyles().setPadding(RIGHT, 8);
		this.add(liveL);
		this.add(live);
	}

	// set up last base value
	public void setBase() {
		Label baseL = new Label("Player Last Base Reached:");
		//this.lastBase.setText("" + Integer.toString(gw.getBase()));
		lastBase = new Label("1");
		this.lastBase.getAllStyles().setPadding(RIGHT, 8);
		this.add(baseL);
		this.add(lastBase);
	}

	// set up energy level value
	public void setEnergy() {
		Label energy = new Label("Player Energy Level:");
		//this.energyLevel.setText("" + Integer.toString(gw.getEnergy()));
		energyLevel = new Label("80");
		this.energyLevel.getAllStyles().setPadding(RIGHT, 8);
		this.add(energy);
		this.add(energyLevel);
	}

	// set up damage level value
	public void setDamage() {
		Label damage = new Label("Player Damage Level:");
		//this.damageLevel.setText("" + Integer.toString(gw.getDamage()));
		damageLevel = new Label("0");
		this.damageLevel.getAllStyles().setPadding(RIGHT, 8);
		this.add(damage);
		this.add(damageLevel);
	}

	// set up sound value
	public void setSound() {
		Label soundL = new Label("Sound:");
		this.add(soundL);
		// check sound on or off
		sound = new Label("OFF");
		//sound.getAllStyles().setPadding(RIGHT, 2);
		this.add(sound);
	}

	@Override
	public void update(Observable observable, Object data) {
		// TODO Auto-generated method stub
		gw = (GameWorld)data;
		this.time.setText("" + Integer.toString(gw.getTime()));
		this.live.setText("" + Integer.toString(gw.getLive()));
		this.lastBase.setText("" + Integer.toString(gw.getBase()));
		this.energyLevel.setText("" + Integer.toString(gw.getEnergy()));
		this.damageLevel.setText("" + Integer.toString(gw.getDamage()));
		if(gw.getSound())
			this.sound.setText("ON");
		else
			this.sound.setText("OFF");
		this.repaint();
	}

}
