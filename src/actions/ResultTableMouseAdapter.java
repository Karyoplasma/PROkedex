package actions;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import gui.PROkedexGUI;

public class ResultTableMouseAdapter extends MouseAdapter {
	private final PROkedexGUI gui;

	public ResultTableMouseAdapter(PROkedexGUI gui) {
		this.gui = gui;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (SwingUtilities.isRightMouseButton(e)) {
			int row = gui.getTableResults().rowAtPoint(e.getPoint());
			int column = gui.getTableResults().columnAtPoint(e.getPoint());

			if (row >= 0 && column >= 0) {
				String cellValue = gui.getTableResults().getValueAt(row, column).toString();
				int columnCount = gui.getTableResults().getColumnCount();
				StringBuilder rowContent = new StringBuilder();

				for (int i = 0; i < columnCount; i++) {
					Object cell = gui.getTableResults().getValueAt(row, i);
					rowContent.append(cell != null ? cell.toString() : "-");
					if (i < columnCount - 1) {
						rowContent.append("\t");
					}
				}
				JPopupMenu popupMenu = new JPopupMenu();
				JMenuItem copyItem = new JMenuItem(new ResultTableCopyAction(rowContent.toString()));
				JMenuItem searchItem = new JMenuItem(new ResultTableSearchAction(cellValue, gui));

				popupMenu.add(copyItem);
				popupMenu.add(searchItem);
				popupMenu.show(e.getComponent(), e.getX(), e.getY());
			}
		}
	}
}
