package com.mycompany.cmd;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameWorld;


public class Position extends Command {
	private GameWorld gw;
	
	public Position(GameWorld gw) {
		super("Position");
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		if(gw.getPause())
			gw.setRePos(true);
		else
			gw.setRePos(false);
	}
}