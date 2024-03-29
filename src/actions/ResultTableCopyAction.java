package actions;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;

public class ResultTableCopyAction extends AbstractAction {

	private static final long serialVersionUID = -5274674145263836683L;
	private final String cellValue;

	public ResultTableCopyAction(String cellValue) {
		putValue(Action.NAME, "Copy");
		this.cellValue = cellValue;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		StringSelection stringSelection = new StringSelection(cellValue);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);

	}

}
