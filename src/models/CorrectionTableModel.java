package models;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import core.DistanceResult;

public class CorrectionTableModel extends AbstractTableModel {

	private static final long serialVersionUID = -5672621764258715750L;
	private final List<DistanceResult> corrections;
	private final String[] columnNames = { "Suggestion", "Distance" };

	public CorrectionTableModel(List<DistanceResult> corrections) {
		this.corrections = corrections;
	}

	@Override
	public int getRowCount() {
		return corrections.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		DistanceResult correction = corrections.get(rowIndex);

		switch (columnIndex) {
		case 0:
			return correction.getWord();
		case 1:
			return correction.getDistance();
		default:
			return null;
		}
	}

}
