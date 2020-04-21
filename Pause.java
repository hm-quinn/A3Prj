package com.mycompany.cmd;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.Game;

public class Pause extends Command {
	private Game game;
	
	public Pause(Game game) {
		super("Pause");
		this.game = game;
	}
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		game.gamePause();
	}
}