package com.mycompany.cmd;
import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameWorld;

public class Right extends Command{
	private GameWorld gw;
	
	public Right(GameWorld gw) {
		super("Right Turn");
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		gw.CBSteer('r');
	}
}
