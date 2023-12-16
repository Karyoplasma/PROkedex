package actions;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import comparator.SpawnRarityComparator;
import core.DistanceResult;
import core.RepelChecker;
import core.Spawn;
import core.SpawnManager;
import enums.RequestType;
import gui.PROkedexGUI;
import models.CorrectionTableModel;
import models.ItemTableModel;
import models.MapTableModel;
import models.PokemonTableModel;

public class SearchButtonAction extends AbstractAction {

	private static final long serialVersionUID = -8429157178554651708L;

	private PROkedexGUI gui;

	public SearchButtonAction(PROkedexGUI gui) {
		putValue(Action.NAME, "Search");
		this.gui = gui;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String request = gui.getTextFieldPokemon().getText().trim();
		RequestType type = RequestType.POKEMON;
		if (request.isEmpty()) {
			request = gui.getTextFieldMap().getText().trim();
			type = RequestType.MAP;
			if (request.isEmpty()) {
				request = gui.getTextFieldItem().getText().trim();
				type = RequestType.ITEM;
			}
		}

		if (!request.isEmpty()) {
			request = this.spellCheckRequest(request, type);
			this.search(request, type);
		}
	}

	private String spellCheckRequest(String request, RequestType type) {
		Set<String> dictionary = gui.getSpellChecker().getDictionary(type);
		if (dictionary.contains(request)) {
			return request;
		}
		List<DistanceResult> spellcheckResults = gui.getSpellChecker().compareWithLevenshteinDistance(request,
				dictionary);
		if (spellcheckResults.isEmpty()) {
			return null;
		}
		if (gui.getSpellChecker()
				.getResultsWithSmallerDistanceThan(spellcheckResults, spellcheckResults.get(0).getDistance() + 1)
				.size() == 1) {
			return spellcheckResults.get(0).getWord();
		}
		this.presentSpellcheckResults(gui.getSpellChecker().getResultsWithSmallerDistanceThan(spellcheckResults,
				this.getSpellcheckCutoff(type)));
		return null;
	}

	private int getSpellcheckCutoff(RequestType type) {
		switch (type) {
		case POKEMON:
			return 5;
		case MAP:
			return 7;
		case ITEM:
			return 5;
		default:
			return 0;
		}
	}

	private void presentSpellcheckResults(List<DistanceResult> spellcheckResults) {
		CorrectionTableModel newModel = new CorrectionTableModel(spellcheckResults);
		this.attachModel(newModel);
		gui.setLabel("Did you mean any of these:");
	}

	private void search(String request, RequestType type) {
		if (request == null) {
			return;
		}
		List<Spawn> results;
		SpawnManager spawnManager = new SpawnManager(gui.getAllSpawns());
		switch (type) {
		case POKEMON:
			results = spawnManager.filterByPokemon(request);
			break;
		case MAP:
			results = spawnManager.filterByMap(request);
			break;
		case ITEM:
			results = spawnManager.filterByItem(request);
			break;
		default:
			results = new ArrayList<Spawn>();
		}
		if (gui.filterMemberOnly()) {
			spawnManager = new SpawnManager(results);
			results = spawnManager.filterMemberOnly();
		}
		if (gui.filterFishingOnly()) {
			spawnManager = new SpawnManager(results);
			results = spawnManager.filterFishingOnly();
		}
		if (type.equals(RequestType.POKEMON) && gui.getFilterRepel()) {
			RepelChecker repelChecker = new RepelChecker(gui.getAllSpawns());
			results = repelChecker.checkRepel(request, results);
		}

		TableModel newModel = this.getResultTableModel(results, type);
		this.attachModel(newModel);
		gui.setLabel("Results for " + request + ":");
	}

	private void attachModel(TableModel newModel) {
		gui.getTableResults().setModel(newModel);
		this.resizeColumnWidth(gui.getTableResults());
	}

	private TableModel getResultTableModel(List<Spawn> results, RequestType type) {
		SpawnManager manager = new SpawnManager(results);
		List<Spawn> landSpawns = manager.filterBySpawnType(false);
		List<Spawn> surfSpawns = manager.filterBySpawnType(true);
		Collections.sort(landSpawns, new SpawnRarityComparator());
		Collections.sort(surfSpawns, new SpawnRarityComparator());
		List<Spawn> tableSpawns = new ArrayList<Spawn>();
		tableSpawns.addAll(landSpawns);
		tableSpawns.addAll(surfSpawns);
		switch (type) {
		case POKEMON:
			return new PokemonTableModel(tableSpawns);
		case MAP:
			return new MapTableModel(tableSpawns);
		case ITEM:
			return new ItemTableModel(tableSpawns);
		default:
			return null;
		}
	}

	private void resizeColumnWidth(JTable table) {
		final TableColumnModel columnModel = table.getColumnModel();
		for (int column = 0; column < table.getColumnCount(); column++) {
			int width = 15; // Min width
			for (int row = 0; row < table.getRowCount(); row++) {
				TableCellRenderer renderer = table.getCellRenderer(row, column);
				Component comp = table.prepareRenderer(renderer, row, column);
				width = Math.max(comp.getPreferredSize().width + 1, width);
			}
			if (width > 400)
				width = 400; // Max width
			columnModel.getColumn(column).setPreferredWidth(width);
		}
	}
}
