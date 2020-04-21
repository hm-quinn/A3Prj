package com.mycompany.cmd;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;

public class About extends Command {
	public About() {
		super("About");
	}
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		String text = "Hang Mai | CSC 133 | Sili-Challenge Game | 2020";
		Dialog.show("About", text, "Done", null);
	}
}
