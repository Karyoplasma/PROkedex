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

import javax.swing.JLabel;
import javax.swing.JTextField;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import core.DumpFileReader;
import core.LandSpawn;
import core.SurfSpawn;
import core.comparators.ComparatorLandRoutePokemon;
import core.comparators.ComparatorLandRouteStringLength;
import core.comparators.ComparatorSurfRoutePokemon;
import core.comparators.ComparatorSurfRouteStringLength;
import core.comparators.ComparatorTierLandSpawn;
import core.comparators.ComparatorTierSurfSpawn;

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
		lblResults.setBounds(10, 98, 750, 17);
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
		btnSearch.setBounds(639, 66, 121, 25);
		btnSearch.addActionListener(this);
		frmProkedex.getContentPane().add(btnSearch);
		
		dumpReader = new DumpFileReader();
		try {
			dumpReader.readSpawns();
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
				String pokemon = textFieldPokemon.getText().substring(0,1).toUpperCase() + textFieldPokemon.getText().substring(1,textFieldPokemon.getText().length()).toLowerCase();
				if (chckbxRepel.isSelected()) {
					lblResults.setText("Repel spawns for " + pokemon + ":");
				} else {
					lblResults.setText("Results for " + pokemon + ":");
				}			
				txtAreaResults.append(this.getLandSpawnsPokemon(pokemon, chckbxRepel.isSelected()));
				txtAreaResults.append(this.getSurfSpawnsPokemon(pokemon, chckbxRepel.isSelected()));
				if (txtAreaResults.getText().equals("")) {
					txtAreaResults.setText("Pokemon/Map could not be found.");
				}
				this.blankTextFields();
			} else {
				if (! textFieldArea.getText().isEmpty()) {
					txtAreaResults.setText("");
					String area = textFieldArea.getText().toLowerCase();
					String[] areaSplit = area.split("\\s");
					area = "";
					for (String s : areaSplit) {
						if (this.stringHasDigits(s)) {
							area += s.toUpperCase() + " ";
						} else {
							area += s.substring(0, 1).toUpperCase() + s.substring(1, s.length()) + " ";
						}
					}
					area = area.trim();
					lblResults.setText("Results for " + area + ":");
					txtAreaResults.append(this.getLandSpawnsArea(area));
					txtAreaResults.append(this.getSurfSpawnsArea(area));
					if (txtAreaResults.getText().equals("")) {
						txtAreaResults.setText("Pokemon/Map could not be found.");
					}
					this.blankTextFields();
				}
			}
		}
	}
	
	private String getSurfSpawnsArea(String area) {
		List<SurfSpawn> surfSpawns = (ArrayList<SurfSpawn>) dumpReader.getSurfSpawnsRoute().get(area);
		if (surfSpawns == null) {
			return "";
		}
		return stringifySurfSpawnsArea(surfSpawns);
	}

	private String stringifySurfSpawnsArea(List<SurfSpawn> surfSpawns) {
		StringBuffer buffer = new StringBuffer();
		if (surfSpawns == null || surfSpawns.isEmpty()) {
			return "";
		}
		ComparatorSurfRoutePokemon cmpLength = new ComparatorSurfRoutePokemon();
		ComparatorTierSurfSpawn cmpTier = new ComparatorTierSurfSpawn();
		Collections.sort(surfSpawns, cmpLength);	
		int tabs = surfSpawns.get(0).getRoute().length() / 8;
		Collections.sort(surfSpawns, cmpTier);
		for (SurfSpawn s : surfSpawns) {
			buffer.append(s.getPokemon());
			for (int i = s.getPokemon().length() / 8; i <= tabs; i++) {
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
			if (s.isFishOnly()) {
				buffer.append("Fish (" + s.getRod() + ")\t");
			} else {
				if (s.isSurfOnly()) {
					buffer.append("Surf\t\t\t");
				} else {
					buffer.append("Surf/Fish (" + s.getRod() + ")\t");
				}
			}
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
		return buffer.toString();
	}

	private String getLandSpawnsArea(String area) {
		List<LandSpawn> landSpawns = (ArrayList<LandSpawn>) dumpReader.getLandSpawnsRoute().get(area);
		if (landSpawns == null) {
			return "";
		}
		return stringifyLandSpawnsArea(landSpawns);
	}

	private String stringifyLandSpawnsArea(List<LandSpawn> landSpawns) {
		StringBuffer buffer = new StringBuffer();
		if (landSpawns == null || landSpawns.isEmpty()) {
			return "";
		}
		ComparatorLandRoutePokemon cmpLength = new ComparatorLandRoutePokemon();
		ComparatorTierLandSpawn cmpTier = new ComparatorTierLandSpawn();
		Collections.sort(landSpawns, cmpLength);	
		int tabs = landSpawns.get(0).getPokemon().length() / 8;
		Collections.sort(landSpawns, cmpTier);
		for (LandSpawn s : landSpawns) {
			buffer.append(s.getPokemon());
			for (int i = s.getPokemon().length() / 8; i <= tabs; i++) {
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
	private String getSurfSpawnsPokemon(String pokemon, boolean repelOnly) {
		List<SurfSpawn> surfSpawns = (ArrayList<SurfSpawn>) dumpReader.getSurfSpawnsPokemon().get(pokemon);
		Map<String, Collection<SurfSpawn>> surfSpawnsRoute = new HashMap<String, Collection<SurfSpawn>>(dumpReader.getSurfSpawnsRoute());
		if (surfSpawns == null) {
			return "";
		}
		if (repelOnly) {
			List<SurfSpawn> repelSpawnsSurf = new ArrayList<SurfSpawn>();
			for (SurfSpawn s : surfSpawns) {
				int minLvl = s.getMinLvl();
				int[] time = s.getTime();
				boolean sameTime = false;
				boolean repellable = false;
				for (SurfSpawn rs : surfSpawnsRoute.get(s.getRoute())) {
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
					repelSpawnsSurf.add(s);
				}
			}
			return this.stringifySurfSpawns(repelSpawnsSurf);
		} else {
			return this.stringifySurfSpawns(surfSpawns);
		}
	}

	

	private String getLandSpawnsPokemon(String pokemon, boolean repelOnly) {
		List<LandSpawn> landSpawns = (ArrayList<LandSpawn>) dumpReader.getLandSpawnsPokemon().get(pokemon);
		Map<String, Collection<LandSpawn>> landSpawnsRoute = new HashMap<String, Collection<LandSpawn>>(dumpReader.getLandSpawnsRoute());
		if (landSpawns == null) {
			return "";
		}
		if (repelOnly) {
			List<LandSpawn> repelSpawnsLand = new ArrayList<LandSpawn>();
			for (LandSpawn s : landSpawns) {
				int minLvl = s.getMinLvl();
				int[] time = s.getTime();
				boolean sameTime = false;
				boolean repellable = false;
				for (LandSpawn rs : landSpawnsRoute.get(s.getRoute())) {
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
					repelSpawnsLand.add(s);
				}
				
			}
			return this.stringifyLandSpawns(repelSpawnsLand);
		} else {
			return this.stringifyLandSpawns(landSpawns);
		}
	}
	
	private String stringifyLandSpawns(List<LandSpawn> landSpawns) {
		StringBuffer buffer = new StringBuffer();
		if (landSpawns == null || landSpawns.isEmpty()) {
			return "";
		}
		ComparatorLandRouteStringLength cmpLength = new ComparatorLandRouteStringLength();
		ComparatorTierLandSpawn cmpTier = new ComparatorTierLandSpawn();
		Collections.sort(landSpawns, cmpLength);	
		int tabs = landSpawns.get(0).getRoute().length() / 8;
		Collections.sort(landSpawns, cmpTier);
		for (LandSpawn s : landSpawns) {
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
		return buffer.toString();
	}
	
	private String stringifySurfSpawns(List<SurfSpawn> surfSpawns) {
		StringBuffer buffer = new StringBuffer();
		if (surfSpawns == null || surfSpawns.isEmpty()) {
			return "";
		}
		ComparatorSurfRouteStringLength cmpLength = new ComparatorSurfRouteStringLength();
		ComparatorTierSurfSpawn cmpTier = new ComparatorTierSurfSpawn();
		Collections.sort(surfSpawns, cmpLength);	
		int tabs = surfSpawns.get(0).getRoute().length() / 8;
		Collections.sort(surfSpawns, cmpTier);
		for (SurfSpawn s : surfSpawns) {
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
			if (s.isFishOnly()) {
				buffer.append("Fish (" + s.getRod() + ")\t");
			} else {
				if (s.isSurfOnly()) {
					buffer.append("Surf\t\t\t");
				} else {
					buffer.append("Surf/Fish (" + s.getRod() + ")\t");
				}
			}
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
		return buffer.toString();
	}
	private void blankTextFields() {
		textFieldPokemon.setText("");
		textFieldArea.setText("");
	}
}
