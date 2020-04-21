package com.mycompany.cmd;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameWorld;

public class Exit extends Command {
	private GameWorld gw;
	
	public Exit(GameWorld gw) {
		super("Exit");
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		Boolean ok = Dialog.show("Exit Game", "Are you sure you want to exit?", "Yes", "No");
		if(ok)
			gw.exit();
	}
}