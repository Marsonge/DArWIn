package darwin.darwin.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.commons.lang3.StringUtils;

import darwin.darwin.controler.WorldControler;
import darwin.darwin.model.Creature;
import darwin.darwin.utils.Utils;

/**
 * 
 * ViewPanel
 * 
 * Side panel with options and stats tab
 * 
 *
 */
public class SidePanel extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;
	public static final Color black = new Color(0, 0, 0);
	public static final Color defaultButtonColor = new Color(220, 220, 220);
	private static final String HELPFILE = "help.html";
	private JLabel nbTime;
	private JLabel nbAlive;
	private JLabel nbDead;
	
	private int time;
	private int alive;
	private int dead;

	private JLabel nbSpeed;
	private JLabel nbEnergy;

	private WorldControler wc;
	private MainView parent;
	private SidePanel self = this;
	
	JFileChooser fileChooserForExport = new JFileChooser();
	private DecimalFormat df = new DecimalFormat("0.00");
	
	private TabOptions tabOptions; 
	private TabImportExport tabImportExport; 
	
	public TabOptions getTabOptions(){
		return tabOptions;
	}
	
	public TabImportExport getTabImportExport(){
		return tabImportExport;
	}

	/**
	 * Disable a button
	 * 
	 * @param button
	 */
	public void disable(JButton button) {

		button.setBorderPainted(false);
		button.setFocusPainted(false);
		button.setEnabled(false);
		button.setFocusable(false);
	}

	/**
	 * Disable a slider
	 * 
	 * @param slider
	 */
	public void disable(JSlider slider) {
		slider.setFocusable(false);
		slider.setEnabled(false);
	}

	/**
	 * Disable a label
	 * 
	 * @param label
	 */
	public void disable(JLabel label) {
		label.setFocusable(false);
		label.setEnabled(false);
	}

	/**
	 * SidePanel constructor, will build the side panel
	 * 
	 * @throws IOException
	 */
	public SidePanel(MainView mv) throws IOException {

		this.parent = mv;
		
		// Set size and color of panel
		this.setPreferredSize(new Dimension(300, 771));
		this.setBackground(black);

		JTabbedPane tabbedPane = new JTabbedPane();
		
		// Stats tab
		JPanel tabStats = new JPanel();
		tabStats.setPreferredSize(new Dimension(280, 660));

		// Help tab
		JPanel tabHelp = new JPanel();
		tabHelp.setPreferredSize(new Dimension(260, 1360));
		JScrollPane helpScrollable = new JScrollPane(tabHelp, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		helpScrollable.setPreferredSize(new Dimension(280, 660));
		tabHelp.setLayout(new BorderLayout());

		tabOptions = new TabOptions();
		tabImportExport = new TabImportExport(parent, self);
		
		// Add tabs to tabbedPane
		tabbedPane.addTab("Options", null, tabOptions);
		tabbedPane.addTab("Stats", null, tabStats);
		//TODO : Get this back
		tabbedPane.addTab("Import/Export", null, tabImportExport);
		tabbedPane.addTab("Help", null, helpScrollable);

		time = 0;
		alive = 90;
		dead = 0;

		JPanel titres = new JPanel(new GridLayout(7, 2));
		JPanel values = new JPanel(new GridLayout(7, 2));
		JPanel ligne = new JPanel(new FlowLayout(4));

		JLabel lbTime = new JLabel("Time:");
		JLabel lbAlive = new JLabel("Alive creatures:");
		JLabel lbDead = new JLabel("Death count:");

		nbTime = new JLabel(Integer.toString(Math.round(time / 10)));
		nbAlive = new JLabel(Integer.toString(alive));
		nbDead = new JLabel(Integer.toString(dead));

		// Modifs stats current creature
		JLabel lbSpeed = new JLabel("Speed:");
		JLabel lbEnergy = new JLabel("Energy:");
		nbSpeed = new JLabel("No creature selected");
		nbEnergy = new JLabel("No creature selected");

		titres.add(lbTime);
		titres.add(lbAlive);
		titres.add(lbDead);
		titres.add(lbSpeed);
		titres.add(lbEnergy);
		values.add(nbTime);
		values.add(nbAlive);
		values.add(nbDead);
		values.add(nbSpeed);
		values.add(nbEnergy);

		ligne.add(titres);
		ligne.add(values);

		tabStats.add(ligne);

		// Add neural network view button to Stats tab
		JButton viewNnButton = new JButton("View creature's neural network");
		viewNnButton.addActionListener(e -> {
			wc = parent.getWorldControler();

			// NeuralNetwork view is created with current creature's NN.
			if (wc.getCurrentCreature() != null) {
				ViewNeuralNetwork nnView = new ViewNeuralNetwork(wc.getCurrentCreature().getNeuralNetwork());
				nnView.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				nnView.pack();
				nnView.setVisible(true);
			}
		});
		tabStats.add(viewNnButton);

		// Tab Help

		tabHelp.setLayout(new BoxLayout(tabHelp, BoxLayout.PAGE_AXIS));
		Scanner sc = new Scanner(Utils.getResource(HELPFILE).openStream());
		StringBuilder str = new StringBuilder();
		while (sc.hasNextLine()) {

			str.append(sc.nextLine());

		}
		JLabel labelHelp = new JLabel(str.toString());
		labelHelp.setAlignmentX(Component.CENTER_ALIGNMENT);
		tabHelp.add(labelHelp);
		sc.close();

		JButton websiteButton = new JButton("Go to website");
		websiteButton.setAlignmentX(Component.CENTER_ALIGNMENT);

		websiteButton.addActionListener(e -> {

			URI website = null;
			try {
				website = new URI("https://marsonge.github.io/DArWIn");
			} catch (URISyntaxException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			try {
				Desktop.getDesktop().browse(website);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		});

		tabHelp.add(websiteButton);
		
		// Add tabbedPane to viewPanel
		this.add(tabbedPane);
	}

	

	public void tick() {
		time++;
		nbTime.setText(Integer.toString(Math.round(time / 10)));
		this.revalidate();
		this.repaint();
	}

	public void updateNbCreature(int nbAlive, int nbDead) {
		this.nbAlive.setText(Integer.toString(nbAlive));
		this.nbDead.setText(Integer.toString(nbDead));
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

	}
	
	public void updateCurrentCreature(Creature currentCreature) {
		if (currentCreature == null) {
			this.nbSpeed.setText("No creature selected");
			this.nbEnergy.setText("No creature selected");
		} else {
			this.nbSpeed.setText(df.format(currentCreature.getSpeed()));
			this.nbEnergy
					.setText(currentCreature.getEnergy() != 0 ? Integer.toString(currentCreature.getEnergy()) : "DEAD");
		}

	}

}
