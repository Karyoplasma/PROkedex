package models;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class AlignmentTableCellRenderer extends DefaultTableCellRenderer {

	private static final long serialVersionUID = -7994623418868286688L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		if (column == 0) {
			setHorizontalAlignment(SwingConstants.LEFT);
		} else {
			setHorizontalAlignment(SwingConstants.CENTER);
		}

		return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	}

}
