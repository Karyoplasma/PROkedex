package actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import enums.RequestType;
import gui.PROkedexGUI;

public class ResultTableSearchAction extends AbstractAction {
	
	private static final long serialVersionUID = 8211825636538334914L;
	private final String cellValue;
	private final PROkedexGUI gui;
	
	public ResultTableSearchAction(String cellValue, PROkedexGUI gui) {
		super("Search for " + cellValue);
		this.cellValue = cellValue;
		this.gui = gui;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		RequestType type = gui.getSpellChecker().getSetForString(cellValue);
		if (type != null) {
			this.performSearch(type);
		}
	}

	private void performSearch(RequestType type) {
		gui.getTextFieldMap().setText("");
		gui.getTextFieldItem().setText("");
		gui.getTextFieldPokemon().setText("");
		switch (type) {
			case POKEMON:
				gui.getTextFieldPokemon().setText(cellValue);
				break;
			case MAP:
				gui.getTextFieldMap().setText(cellValue);
				break;
			case ITEM:
				gui.getTextFieldItem().setText(cellValue);
				break;
			default:
				return;
		}
		gui.clickSearchButton();
	}
}
