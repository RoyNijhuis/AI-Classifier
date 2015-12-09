package Controller;

import View.TUI;
import View.UI;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Main {

	public static void main(String[] args) {
		UI ui = new TUI();
		ui.askForTrainFolder();
		Classifier c = new Classifier();
		c.test();
	}
}
