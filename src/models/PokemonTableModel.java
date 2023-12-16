package models;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import core.Spawn;

public class PokemonTableModel extends AbstractTableModel {

	private static final long serialVersionUID = -7367559598628578938L;
	private final List<Spawn> spawnData;
	private final String[] columnNames = {"Map", "Area", "Daytime", "Rarity", "MS", "Level", "Item"};
	
	public PokemonTableModel(List<Spawn> spawnData) {
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

        // "Map", "Area", "Daytime", "Rarity", "MS", "Level", "Item"
        switch (columnIndex) {
            case 0:
                return spawn.getMap();
            case 1:
                return spawn.getArea();
            case 2:
                return spawn.getDaytimeString();
            case 3:
            	return spawn.getTier();
            case 4:
            	return spawn.getMS();
            case 5:
            	return spawn.getLevel();
            case 6:
            	return spawn.getItemData();
            default:
                return null;
        }
	}

}
