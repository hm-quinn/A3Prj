package com.mycompany.cmd;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameWorld;

public class Accelerate extends Command {
	private GameWorld gw;
	public Accelerate(GameWorld gw) {
		super("Accelerate");
		this.gw = gw;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		gw.changeSpeed('a');
	}

}