package actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;

import core.SpawnDumpFileUpdater;
import core.SpellcheckListCreator;
import gui.PROkedexGUI;

public class UpdateSpawnsMenuAction extends AbstractAction {

	private static final long serialVersionUID = 1499410282344142979L;
	private PROkedexGUI gui;

	public UpdateSpawnsMenuAction(PROkedexGUI gui) {
		putValue(Action.NAME, "Update Spawns");
		this.gui = gui;

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(SpawnDumpFileUpdater.downloadSpawns()).append("\n");
		buffer.append(SpellcheckListCreator.updateLists());

		gui.readDatabase();
		JOptionPane.showMessageDialog(gui.getFrame(), buffer.toString(), "Update Log", JOptionPane.INFORMATION_MESSAGE);
	}

}
