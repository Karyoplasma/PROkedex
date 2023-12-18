package actions;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.table.TableModel;
import comparator.SpawnRarityComparator;
import core.SpellChecker;
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
		SpellChecker spellChecker = new SpellChecker();
		List<DistanceResult> spellCheckerResult = spellChecker.spellCheckRequest(request, type);

		if (spellCheckerResult.size() == 1) {
			return spellCheckerResult.get(0).getWord();
		}
		this.presentSpellcheckResults(spellCheckerResult);
		return null;
	}

	private void presentSpellcheckResults(List<DistanceResult> spellcheckResults) {
		CorrectionTableModel newModel = new CorrectionTableModel(spellcheckResults);
		gui.attachModel(newModel);
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
		if (type == RequestType.POKEMON && gui.getFilterRepel()) {
			RepelChecker repelChecker = new RepelChecker(gui.getAllSpawns());
			results = repelChecker.checkRepel(request, results);
		}

		TableModel newModel = this.getResultTableModel(results, type);
		gui.attachModel(newModel);
		gui.setLabel("Results for " + request + ":");
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
}
