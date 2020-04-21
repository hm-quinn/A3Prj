package com.mycompany.cmd;
import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameWorld;

public class Strategy extends Command {
	private GameWorld gw;
	public Strategy(GameWorld gw) {
		super("Change Strategies");
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		System.out.println("Change strategy of NPC.");
		gw.changeStrategy();
	}

}