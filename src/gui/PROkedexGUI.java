package gui;

import java.awt.EventQueue;
import javax.swing.JFrame;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Font;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.JButton;
import actions.EnterKeyAction;
import actions.ResultTableMouseAdapter;
import actions.SearchButtonAction;
import actions.OpenResourcesMenuAction;
import actions.UpdateSpawnsMenuAction;
import core.Spawn;
import core.SpawnDumpReader;
import core.SpellChecker;
import models.AlignmentTableCellRenderer;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;

public class PROkedexGUI {

	private JFrame frmProkedex;
	private JCheckBox chckbxRepel;
	private JCheckBox chckbxMemberOnly;
	private JTextField textFieldPokemon;
	private JTextField textFieldMap;
	private JTextField textFieldItem;
	private JLabel lblResult;
	private JButton btnSearch;
	private List<Spawn> allSpawns;
	private JScrollPane scrollPane;
	private JTable tableResults;
	private SpellChecker spellChecker;
	private JCheckBox chckbxFishingOnly;
	private JMenuBar menuBar;
	private JMenu mnActions;
	private JMenuItem mntmOpenResources;
	private JMenuItem mntmUpdate;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PROkedexGUI window = new PROkedexGUI();
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
	public PROkedexGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.readDatabase();

		frmProkedex = new JFrame();
		frmProkedex.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 14));
		frmProkedex.setTitle("PROkedex");
		frmProkedex.setBounds(100, 100, 788, 461);
		frmProkedex.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmProkedex.getContentPane().setLayout(new MigLayout("", "[fill][grow,fill][fill]", "[][][][][grow]"));

		JLabel lblPokemon = new JLabel("Pok\u00E9mon:");
		lblPokemon.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPokemon.setHorizontalAlignment(SwingConstants.RIGHT);
		frmProkedex.getContentPane().add(lblPokemon, "cell 0 0,alignx trailing");

		textFieldPokemon = new JTextField();
		textFieldPokemon.setFont(new Font("Tahoma", Font.PLAIN, 14));
		frmProkedex.getContentPane().add(textFieldPokemon, "cell 1 0,growx");
		textFieldPokemon.setColumns(10);

		chckbxRepel = new JCheckBox("Check for repel trick");
		chckbxRepel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		frmProkedex.getContentPane().add(chckbxRepel, "cell 2 0");

		JLabel lblMap = new JLabel("Route:");
		lblMap.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMap.setFont(new Font("Tahoma", Font.PLAIN, 14));
		frmProkedex.getContentPane().add(lblMap, "cell 0 1,alignx trailing");

		textFieldMap = new JTextField();
		textFieldMap.setFont(new Font("Tahoma", Font.PLAIN, 14));
		frmProkedex.getContentPane().add(textFieldMap, "cell 1 1,growx");
		textFieldMap.setColumns(10);

		chckbxMemberOnly = new JCheckBox("Exclude member only");
		chckbxMemberOnly.setFont(new Font("Tahoma", Font.PLAIN, 14));
		frmProkedex.getContentPane().add(chckbxMemberOnly, "cell 2 1");

		JLabel lblItem = new JLabel("Item:");
		lblItem.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblItem.setHorizontalAlignment(SwingConstants.RIGHT);
		frmProkedex.getContentPane().add(lblItem, "cell 0 2,alignx trailing");

		textFieldItem = new JTextField();
		textFieldItem.setFont(new Font("Tahoma", Font.PLAIN, 14));
		frmProkedex.getContentPane().add(textFieldItem, "cell 1 2,growx");
		textFieldItem.setColumns(10);

		chckbxFishingOnly = new JCheckBox("Exclude fishing only");
		chckbxFishingOnly.setFont(new Font("Tahoma", Font.PLAIN, 14));
		chckbxFishingOnly.setSelected(true);
		frmProkedex.getContentPane().add(chckbxFishingOnly, "cell 2 2");

		lblResult = new JLabel("");
		lblResult.setFont(new Font("Tahoma", Font.BOLD, 14));
		frmProkedex.getContentPane().add(lblResult, "cell 0 3 2 1");

		scrollPane = new JScrollPane();
		frmProkedex.getContentPane().add(scrollPane, "cell 0 4 3 1,grow");

		tableResults = new JTable();
		tableResults.addMouseListener(new ResultTableMouseAdapter(this));
		tableResults.setDefaultRenderer(Object.class, new AlignmentTableCellRenderer());
		tableResults.setRowSelectionAllowed(false);
		tableResults.setShowVerticalLines(false);
		tableResults.setShowHorizontalLines(false);
		tableResults.setShowGrid(false);
		tableResults.setFillsViewportHeight(true);
		tableResults.setFont(new Font("Monospaced", Font.PLAIN, 14));
		scrollPane.setViewportView(tableResults);

		btnSearch = new JButton(new SearchButtonAction(this));
		btnSearch.setFont(new Font("Tahoma", Font.BOLD, 14));
		frmProkedex.getContentPane().add(btnSearch, "cell 2 3");

		menuBar = new JMenuBar();
		frmProkedex.setJMenuBar(menuBar);

		mnActions = new JMenu("Actions");
		mnActions.setHorizontalAlignment(SwingConstants.LEFT);
		menuBar.add(mnActions);

		mntmOpenResources = new JMenuItem(new OpenResourcesMenuAction());
		mnActions.add(mntmOpenResources);

		mntmUpdate = new JMenuItem(new UpdateSpawnsMenuAction(this));
		mnActions.add(mntmUpdate);

		// setup hotkey
		KeyStroke enterKey = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
		textFieldPokemon.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(enterKey, "search");
		textFieldPokemon.getActionMap().put("search", new EnterKeyAction(btnSearch));
	}

	public JFrame getFrame() {
		return frmProkedex;
	}

	public boolean getFilterRepel() {
		return chckbxRepel.isSelected();
	}

	public JTextField getTextFieldPokemon() {
		return textFieldPokemon;
	}

	public JTextField getTextFieldMap() {
		return textFieldMap;
	}

	public JTextField getTextFieldItem() {
		return textFieldItem;
	}

	public void setLabel(String string) {
		lblResult.setText(string);
	}

	public List<Spawn> getAllSpawns() {
		return allSpawns;
	}

	public JTable getTableResults() {
		return tableResults;
	}

	public SpellChecker getSpellChecker() {
		return spellChecker;
	}

	public void clickSearchButton() {
		this.btnSearch.doClick();
	}

	public boolean filterFishingOnly() {
		return this.chckbxFishingOnly.isSelected();
	}

	public boolean filterMemberOnly() {
		return chckbxMemberOnly.isSelected();
	}

	public void readDatabase() {
		allSpawns = new ArrayList<Spawn>();
		allSpawns.addAll(SpawnDumpReader.getAllLandSpawns());
		allSpawns.addAll(SpawnDumpReader.getAllSurfSpawns());
		this.spellChecker = new SpellChecker();

	}
}
