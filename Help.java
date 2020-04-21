package com.mycompany.cmd;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;

public class Help extends Command {
	public Help() {
		super("Help");
	}
	
	@Override 
	public void actionPerformed(ActionEvent ev) {
		String text = "    KEY                   ACTION\n"
				    + "------------------------------------\n"
				    + "     a                   Accelerate\n"
				    + "   b                      Brake\n" 
				    + "    l                   Left Turn\n"
				    + "     r                   Right Turn\n"
				    + "     e                  Collide with\n"
				    + "                       Energy Station\n"
				    + "     g                  Collide with\n"
				    + "                             Base\n"
				    + "     t                    Clock Tick\n";
		Dialog.show("Help", text, "Done", null);
	}
}