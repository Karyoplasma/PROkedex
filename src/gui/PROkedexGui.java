package gui;

import java.awt.EventQueue;
import javax.swing.JFrame;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JLabel;
import javax.swing.JTextField;
import org.json.simple.parser.ParseException;
import core.DumpFileReader;
import core.Spawn;
import core.SurfSpawn;
import core.comparators.ComparatorPokemonLength;
import core.comparators.ComparatorRouteStringLength;
import core.comparators.ComparatorTier;
import javax.swing.JCheckBox;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;

public class PROkedexGui implements ActionListener{

	private JFrame frmProkedex;
	private JTextField textFieldPokemon;
	private JTextField textFieldArea;
	private JLabel lblResults;
	private JTextArea txtAreaResults;
	private JScrollPane scrollPane;
	private JButton btnSearch;
	private DumpFileReader dumpReader;
	private JCheckBox chckbxRepel;
	private final int distanceThreshold = 2;
	private JTextField textFieldItem;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PROkedexGui window = new PROkedexGui();
					window.frmProkedex.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public PROkedexGui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmProkedex = new JFrame();
		frmProkedex.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 14));
		frmProkedex.setResizable(false);
		frmProkedex.setTitle("PROkeDex");
		frmProkedex.setBounds(100, 100, 776, 547);
		frmProkedex.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmProkedex.getContentPane().setLayout(null);
		
		JLabel lblPokemon = new JLabel("Pokemon:");
		lblPokemon.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPokemon.setBounds(10, 11, 63, 17);
		frmProkedex.getContentPane().add(lblPokemon);
		
		textFieldPokemon = new JTextField();
		textFieldPokemon.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textFieldPokemon.setBounds(83, 9, 550, 20);
		frmProkedex.getContentPane().add(textFieldPokemon);
		textFieldPokemon.setColumns(10);
		
		JLabel lblArea = new JLabel("Area:");
		lblArea.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblArea.setBounds(10, 39, 32, 17);
		frmProkedex.getContentPane().add(lblArea);
		
		textFieldArea = new JTextField();
		textFieldArea.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textFieldArea.setBounds(83, 37, 677, 20);
		frmProkedex.getContentPane().add(textFieldArea);
		textFieldArea.setColumns(10);
		
		chckbxRepel = new JCheckBox("Check for Repel");
		chckbxRepel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		chckbxRepel.setBounds(639, 7, 121, 25);
		frmProkedex.getContentPane().add(chckbxRepel);
		
		lblResults = new JLabel("");
		lblResults.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblResults.setBounds(10, 99, 623, 17);
		frmProkedex.getContentPane().add(lblResults);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 127, 750, 381);
		frmProkedex.getContentPane().add(scrollPane);
		
		txtAreaResults = new JTextArea();
		txtAreaResults.setFont(new Font("Monospaced", Font.PLAIN, 14));
		txtAreaResults.setEditable(false);
		scrollPane.setViewportView(txtAreaResults);
		
		btnSearch = new JButton("Search");
		btnSearch.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnSearch.setBounds(639, 99, 121, 25);
		btnSearch.addActionListener(this);
		frmProkedex.getContentPane().add(btnSearch);
		
		JLabel lblItem = new JLabel("Item:");
		lblItem.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblItem.setBounds(10, 67, 33, 17);
		frmProkedex.getContentPane().add(lblItem);
		
		textFieldItem = new JTextField();
		textFieldItem.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textFieldItem.setBounds(83, 65, 677, 23);
		frmProkedex.getContentPane().add(textFieldItem);
		textFieldItem.setColumns(10);
		
		dumpReader = new DumpFileReader();
		try {
			dumpReader.processSpawns();
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			lblResults.setText("Something went horribly wrong during DumpFileReader initialization!");
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnSearch) {
			if (!textFieldPokemon.getText().isEmpty()) {
				txtAreaResults.setText("");
				String pokemon = "";
				String[] pokemonSplit = textFieldPokemon.getText().split("\\s+");
				for (String s : pokemonSplit) {
					if (this.stringHasDigits(s)) {
						pokemon += s.toUpperCase() + " ";
					} else {
						pokemon += s.substring(0, 1).toUpperCase() + s.substring(1, s.length()) + " ";
					}
				}
				pokemon = pokemon.trim();
				for (Map.Entry<String, Collection<Spawn>> pokemonEntry : dumpReader.getSpawnsPokemon().entrySet()) {
					if (calculateLevenshteinDistance(pokemonEntry.getKey(), pokemon) <= this.distanceThreshold) {
						pokemon = pokemonEntry.getKey();
						break;
					}
				}
				if (chckbxRepel.isSelected()) {
					lblResults.setText("Repel spawns for " + pokemon + ":");
				} else {
					lblResults.setText("Results for " + pokemon + ":");
				}			
				txtAreaResults.append(this.getSpawnsPokemon(pokemon, chckbxRepel.isSelected()));
				if (txtAreaResults.getText().equals("")) {
					txtAreaResults.setText("Pokemon/Map could not be found.");
				}
				this.blankTextFields();
			} else {
				if (! textFieldArea.getText().isEmpty()) {
					txtAreaResults.setText("");
					String area = textFieldArea.getText().toLowerCase();
					String[] areaSplit = area.split("\\s+");
					area = "";
					for (String s : areaSplit) {
						if (this.stringHasDigits(s)) {
							area += s.toUpperCase() + " ";
						} else {
							area += s.substring(0, 1).toUpperCase() + s.substring(1, s.length()) + " ";
						}
					}
					area = area.trim();
					HashMap<Integer, Collection<String>> routeCorrection = new HashMap<Integer, Collection<String>>();
					for (Map.Entry<String, Collection<Spawn>> routeEntry : dumpReader.getSpawnsRoute().entrySet()) {
						int key = calculateLevenshteinDistance(routeEntry.getKey(), area);
						if (key == 0) {
							routeCorrection.clear();
							break;
						} else {
							if (routeCorrection.containsKey(key)) {
								routeCorrection.get(key).add(routeEntry.getKey());
							} else {
								ArrayList<String> put = new ArrayList<String>();
								put.add(routeEntry.getKey());
								routeCorrection.put(key, put);
							}
						}
					}
					if (routeCorrection.isEmpty()) {
						lblResults.setText("Results for " + area + ":");
						txtAreaResults.append(this.getSpawnsArea(area));
						if (txtAreaResults.getText().equals("")) {
							txtAreaResults.setText("Pokemon/Map could not be found.");
						}
						this.blankTextFields();
					} else {
						lblResults.setText("Exact area could not be found. Did you mean:");
						presentRouteCorrections(routeCorrection);
						if (txtAreaResults.getText().equals("")) {
							txtAreaResults.setText("Pokemon/Map could not be found.");
						}
					}
				} else {
					if (!textFieldItem.getText().isEmpty()) {
						txtAreaResults.setText("");
						String item = textFieldItem.getText().toLowerCase();
						String[] itemSplit = item.split("\\s+");
						item = "";
						for (String s : itemSplit) {
							if (this.stringHasDigits(s)) {
								item += s.toUpperCase() + " ";
							} else {
								item += s.substring(0, 1).toUpperCase() + s.substring(1, s.length()) + " ";
							}
						}
						item = item.trim();
						HashMap<Integer, Collection<String>> itemCorrection = new HashMap<Integer, Collection<String>>();
						for (Map.Entry<String, Collection<Spawn>> itemEntry : dumpReader.getSpawnsItem().entrySet()) {
							int key = calculateLevenshteinDistance(itemEntry.getKey(), item);				
								if (key == 0) {
									itemCorrection.clear();
									break;
								} else {
									if (itemCorrection.containsKey(key)) {
										itemCorrection.get(key).add(itemEntry.getKey());
									} else {
										ArrayList<String> put = new ArrayList<String>();
										put.add(itemEntry.getKey());
										itemCorrection.put(key, put);
									}
								}
							}
						if (itemCorrection.size() <= 1) {
							lblResults.setText("Results for " + item + ":");
							txtAreaResults.append(this.getItemSpawn(item));
							if (txtAreaResults.getText().equals("")) {
								txtAreaResults.setText("Item cannot spawn on wild pokemon.");
							}
							this.blankTextFields();
						} else {
							lblResults.setText("Exact item could not be found. Did you mean:");
							presentItemCorrections(itemCorrection);
							if (txtAreaResults.getText().equals("")) {
								txtAreaResults.setText("Item cannot spawn on wild pokemon.");
							}
						}
					}
				}
			} 
		}
	}
	
	private void presentItemCorrections(HashMap<Integer, Collection<String>> itemCorrection) {
		for (Entry<Integer, Collection<String>> area : itemCorrection.entrySet()) {
			if (area.getKey() < 4) {
				for(String s : area.getValue()) {
					txtAreaResults.append(s);
					txtAreaResults.append(System.getProperty("line.separator"));
				}
			}
		}
		
	}

	private String getItemSpawn(String item) {
		ArrayList<Spawn> spawns = (ArrayList<Spawn>) dumpReader.getSpawnsItem().get(item);
		if (spawns == null) {
			return "";
		}
		return stringifySpawnsItem(spawns);
	}

	private String stringifySpawnsItem(ArrayList<Spawn> spawns) {
		StringBuffer buffer = new StringBuffer();
		ComparatorPokemonLength cmpLength = new ComparatorPokemonLength();
		ComparatorRouteStringLength cmpRouteLength = new ComparatorRouteStringLength();
		ComparatorTier cmpTier = new ComparatorTier();
		Collections.sort(spawns, cmpLength);	
		int tabsPokemon = spawns.get(0).getPokemon().length() / 8;
		Collections.sort(spawns, cmpRouteLength);
		int tabsRoute = spawns.get(0).getRouteLength() / 8;
		Collections.sort(spawns, cmpTier);
		for (Spawn s : spawns ) {
			buffer.append(s.getPokemon());
			for (int i = s.getPokemonLength() / 8; i <= tabsPokemon; i++) {
				buffer.append("\t");
			}
			buffer.append(s.getRoute());
			for (int i = s.getRoute().length() / 8; i <= tabsRoute; i++) {
				buffer.append("\t");
			}
			int[] temp = s.getTime();
			if (temp[0] == 1) {
				buffer.append("M/");
			}
			if (temp[1] == 1) {
				buffer.append("D/");
			}
			if (temp[2] == 1) {
				buffer.append("N");
			}
			if (buffer.charAt(buffer.length() - 1) == '/') {
				buffer.setLength(buffer.length() - 1);
			}
			buffer.append("\t");
			buffer.append("Tier " + s.getTier()).append("\t");
			if (s instanceof SurfSpawn) {
				if (s.isFishOnly()) {
					buffer.append("Fish (" + s.getRod() + ")\t");
				} else {
					if (s.isSurfOnly()) {
						buffer.append("Surf\t");
					} else {
						buffer.append("Surf/Fish (" + s.getRod() + ")\t");
					}
				}
			}
			if (s.isMs()) {
				buffer.append("MS only").append(System.getProperty("line.separator"));
			} else {
				buffer.append(System.getProperty("line.separator"));
			}
		}
		return buffer.toString();
	}

	private void presentRouteCorrections(HashMap<Integer, Collection<String>> routeCorrection) {
		for (Entry<Integer, Collection<String>> area : routeCorrection.entrySet()) {
			if (area.getKey() < 6) {
				for(String s : area.getValue()) {
					txtAreaResults.append(s);
					txtAreaResults.append(System.getProperty("line.separator"));
				}
			}
		}
	}

	private String getSpawnsArea(String area) {
		List<Spawn> spawns = (ArrayList<Spawn>) dumpReader.getSpawnsRoute().get(area);
		if (spawns == null) {
			return "";
		}
		return stringifySpawnsArea(spawns);
	}
	
	private String stringifySpawnsArea(List<Spawn> spawns) {
		StringBuffer buffer = new StringBuffer();
		if (spawns == null || spawns.isEmpty()) {
			return "";
		}
		ComparatorPokemonLength cmpLength = new ComparatorPokemonLength();
		ComparatorTier cmpTier = new ComparatorTier();
		Collections.sort(spawns, cmpLength);	
		int tabs = spawns.get(0).getPokemon().length() / 8;
		Collections.sort(spawns, cmpTier);
		for (Spawn s : spawns) {
			if (s instanceof SurfSpawn) {
				
				buffer.append(s.getPokemon());
				for (int i = s.getPokemonLength() / 8; i <= tabs; i++) {
					buffer.append("\t");
				}
				int[] temp = s.getTime();
				if (temp[0] == 1) {
					buffer.append("M/");
				}
				if (temp[1] == 1) {
					buffer.append("D/");
				}
				if (temp[2] == 1) {
					buffer.append("N");
				}
				if (buffer.charAt(buffer.length() - 1) == '/') {
					buffer.setLength(buffer.length() - 1);
				}
				buffer.append("\t");
				buffer.append("Tier " + s.getTier()).append("\t");
				if (s.isMs()) {
					buffer.append("MS only\t");
				} else {
					buffer.append("\t");
				}
				buffer.append(s.getMinLvl() + "-" + s.getMaxLvl()).append("\t");
				if (s.getItem() == null) {
					buffer.append("-\t\t");
				} else {
					buffer.append(s.getItem() + "\t");
				}
				if (s.isFishOnly()) {
					buffer.append("Fish (" + s.getRod() + ")").append(System.getProperty("line.separator"));;
				} else {
					if (s.isSurfOnly()) {
						buffer.append("Surf").append(System.getProperty("line.separator"));
					} else {
						buffer.append("Surf/Fish (" + s.getRod() + ")").append(System.getProperty("line.separator"));;
					}
				}
			} else {
				buffer.append(s.getPokemon());
				for (int i = s.getPokemonLength() / 8; i <= tabs; i++) {
					buffer.append("\t");
				}
				int[] temp = s.getTime();
				if (temp[0] == 1) {
					buffer.append("M/");
				}
				if (temp[1] == 1) {
					buffer.append("D/");
				}
				if (temp[2] == 1) {
					buffer.append("N");
				}
				if (buffer.charAt(buffer.length() - 1) == '/') {
					buffer.setLength(buffer.length() - 1);
				}
				buffer.append("\t");
				buffer.append("Tier " + s.getTier()).append("\t");
				if (s.isMs()) {
					buffer.append("MS only\t");
				} else {
					buffer.append("\t");
				}
				buffer.append(s.getMinLvl() + "-" + s.getMaxLvl()).append("\t");
				if (s.getItem() == null) {
					buffer.append("-\t").append(System.getProperty("line.separator"));
				} else {
					buffer.append(s.getItem() + "\t").append(System.getProperty("line.separator"));
				}
			}
		}
		return buffer.toString();
	}

	private boolean stringHasDigits(String s) {
		boolean hasDigit = false;
	    for (int i = 0; i < s.length(); i++) {
	        if (Character.isDigit(s.charAt(i))) {
	            hasDigit = true;
	            break;
	        }
	    }
	    return hasDigit;
	}
	
	private String getSpawnsPokemon(String pokemon, boolean repelOnly) {
		List<Spawn> spawns = (ArrayList<Spawn>) dumpReader.getSpawnsPokemon().get(pokemon);
		Map<String, Collection<Spawn>> spawnsRoute = new HashMap<String, Collection<Spawn>>(dumpReader.getSpawnsRoute());
		if (spawns == null) {
			return "";
		}
		if (repelOnly) {
			List<Spawn> repelSpawns = new ArrayList<Spawn>();
			for (Spawn s : spawns) {
				int minLvl = s.getMinLvl();
				int[] time = s.getTime();
				boolean sameTime = false;
				boolean repellable = false;
				for (Spawn rs : spawnsRoute.get(s.getRoute())) {
					if (rs.getPokemon().equals(pokemon)) {
						continue;
					} else {
						if (rs.getMaxLvl() >= minLvl) {
							for (int i = 0; i < 3; i++) {
								if (rs.getTime()[i] == 1 && time[i] == 1) {
									sameTime = true;
									break;
								}
							}
							if (sameTime) {
								repellable = false;
								sameTime = false;
								break;
							}
						}
					}
					repellable = true;
				}
				if (repellable) {
					repelSpawns.add(s);
				}
				
			}
			return this.stringifySpawnsPokemon(repelSpawns);
		} else {
			return this.stringifySpawnsPokemon(spawns);
		}
		
	}
		
	private String stringifySpawnsPokemon(List<Spawn> spawns) {
		StringBuffer buffer = new StringBuffer();
		if (spawns == null || spawns.isEmpty()) {
			return "";
		}
		ComparatorRouteStringLength cmpLength = new ComparatorRouteStringLength();
		ComparatorTier cmpTier = new ComparatorTier();
		Collections.sort(spawns, cmpLength);	
		int tabs = spawns.get(0).getRouteLength() / 8;
		Collections.sort(spawns, cmpTier);
		for (Spawn s : spawns) {
			if (s instanceof SurfSpawn) {
				buffer.append(s.getRoute());
				for (int i = s.getRoute().length() / 8; i <= tabs; i++) {
					buffer.append("\t");
				}
				int[] temp = s.getTime();
				if (temp[0] == 1) {
					buffer.append("M/");
				}
				if (temp[1] == 1) {
					buffer.append("D/");
				}
				if (temp[2] == 1) {
					buffer.append("N");
				}
				if (buffer.charAt(buffer.length() - 1) == '/') {
					buffer.setLength(buffer.length() - 1);
				}
				buffer.append("\t");
				buffer.append("Tier " + s.getTier()).append("\t");
				if (s.isMs()) {
					buffer.append("MS only\t");
				} else {
					buffer.append("\t");
				}
				buffer.append(s.getMinLvl() + "-" + s.getMaxLvl()).append("\t");
				if (s.getItem() == null) {
					buffer.append("-\t");
				} else {
					buffer.append(s.getItem() + "\t");
				}
				if (s.isFishOnly()) {
					buffer.append("Fish (" + s.getRod() + ")").append(System.getProperty("line.separator"));;
				} else {
					if (s.isSurfOnly()) {
						buffer.append("Surf").append(System.getProperty("line.separator"));
					} else {
						buffer.append("Surf/Fish (" + s.getRod() + ")").append(System.getProperty("line.separator"));;
					}
				}
			} else {
				buffer.append(s.getRoute());
				for (int i = s.getRoute().length() / 8; i <= tabs; i++) {
					buffer.append("\t");
				}
				int[] temp = s.getTime();
				if (temp[0] == 1) {
					buffer.append("M/");
				}
				if (temp[1] == 1) {
					buffer.append("D/");
				}
				if (temp[2] == 1) {
					buffer.append("N");
				}
				if (buffer.charAt(buffer.length() - 1) == '/') {
					buffer.setLength(buffer.length() - 1);
				}
				buffer.append("\t");
				buffer.append("Tier " + s.getTier()).append("\t");
				if (s.isMs()) {
					buffer.append("MS only\t");
				} else {
					buffer.append("\t");
				}
				buffer.append(s.getMinLvl() + "-" + s.getMaxLvl()).append("\t");
				if (s.getItem() == null) {
					buffer.append("-\t").append(System.getProperty("line.separator"));
				} else {
					buffer.append(s.getItem() + "\t").append(System.getProperty("line.separator"));
				}
			}
		}
		return buffer.toString();
	}
	
	private void blankTextFields() {
		textFieldPokemon.setText("");
		textFieldArea.setText("");
		textFieldItem.setText("");
	}
	
	private int calculateLevenshteinDistance(String string, String target) {
		int[][] distance = new int [string.length() + 1][target.length() + 1];
		for (int i = 1; i <= string.length(); i++) {
			distance[i][0] = i;
		}
		for (int j = 1; j <= target.length(); j++) {
			distance[0][j] = j;
		}
		int substitutionCost = 0;
		for (int j = 1; j <= target.length(); j++) {
			for (int i = 1; i <= string.length(); i++) {
				if (string.charAt(i-1) == target.charAt(j-1)) {
					substitutionCost = 0;
				} else {
					substitutionCost = 1;
				}
				
				distance[i][j] = Math.min(Math.min(distance[i-1][j] + 1, distance[i][j-1] + 1), distance[i-1][j-1] + substitutionCost);
			}
		}
		return distance[string.length() - 1][target.length() - 1];
	}
}
