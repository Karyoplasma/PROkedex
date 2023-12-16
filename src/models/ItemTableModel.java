package models;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import core.Spawn;

public class ItemTableModel extends AbstractTableModel {

	private static final long serialVersionUID = -979237780756508464L;
	private final List<Spawn> spawnData;
	private final String[] columnNames = { "Pokemon", "Map", "Area", "Daytime", "Rarity", "MS", "Level" };

	public ItemTableModel(List<Spawn> spawnData) {
		this.spawnData = spawnData;
	}

	@Override
	public int getRowCount() {
		return spawnData.size();
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
		Spawn spawn = spawnData.get(rowIndex);

		// "Pokemon", "Map", "Area", "Daytime", "Rarity", "MS", "Level"
		switch (columnIndex) {
		case 0:
			return spawn.getPokemon();
		case 1:
			return spawn.getMap();
		case 2:
			return spawn.getArea();
		case 3:
			return spawn.getDaytimeString();
		case 4:
			return spawn.getTier();
		case 5:
			return spawn.getMS();
		case 6:
			return spawn.getLevel();
		default:
			return null;
		}
	}

}
