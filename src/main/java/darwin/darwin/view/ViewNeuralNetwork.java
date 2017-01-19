package darwin.darwin.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import org.apache.commons.lang3.math.NumberUtils;

import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxIGraphModel;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;

import darwin.darwin.controler.WorldControler;
import darwin.darwin.model.NeuralNetwork;
import darwin.darwin.utils.Utils;

/**
 * Vue pour le r�seau neuronal d'une cr�ature Attention, cette classe utilise la
 * librairie JGraphX.
 * 
 * @author lulu
 *
 */

@SuppressWarnings("serial")
public class ViewNeuralNetwork extends JDialog {

	private final int COLUMN_NUMBER = 3;
	private final int NB_INPUT = NeuralNetwork.getNbInput();
	private final int NB_OUTPUT = NeuralNetwork.getNbOutput();
	private final int NB_HIDDENNODES = NeuralNetwork.getNbHiddennodes();
	private static final int NODE_SIZE = 25;
	private List<Object> inputNodeList;
	private List<Object> hiddenNodeList;
	private List<Object> outputNodeList;
	private Map<Object, Double> edgesValuesMap; // mxGraph object 'edge' and its value
	private String[] nodesTitles; // facultative strings to be associated with nodes
	private double[] input;
	private double[] matrix;
	private double[] output;
	public final ViewNeuralNetwork self = this;
	private boolean editable = false;

	public ViewNeuralNetwork(WorldControler wc) {
		int height = (int) (java.awt.Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 80);
		int width = (int) (java.awt.Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2 + 150);
		this.setPreferredSize(new Dimension(width, height));
		this.inputNodeList = new ArrayList<Object>();
		this.hiddenNodeList = new ArrayList<Object>();
		this.outputNodeList = new ArrayList<Object>();
		this.edgesValuesMap = new HashMap<Object, Double>();

		DecimalFormat df = new DecimalFormat("#.##");
		NeuralNetwork nn = wc.getCurrentCreature().getNeuralNetwork();
		long idCreature = wc.getCurrentCreature().getId();
		double[][] inputAxiom = nn.getInputAxiom();
		double[][] outputAxiom = nn.getOutputAxiom();
		input = nn.getInput();
		matrix = nn.getMatrix();
		output = nn.getOutput();
		this.nodesTitles = NeuralNetwork.getNodesTitles();

		// graph settings
		mxGraph graph = new mxGraph();
		graph.setCellsResizable(false);
		graph.setCellsEditable(false);
		graph.setCellsMovable(false);
		graph.setEdgeLabelsMovable(false);
		graph.setAllowDanglingEdges(false);
		graph.setConnectableEdges(false);
		graph.setCellsSelectable(false);

		Object parent = graph.getDefaultParent();
		mxIGraphModel model = graph.getModel();
		// mxStylesheet stylesheet = new mxStylesheet();

		// Nodes creation
		model.beginUpdate();
		try {
			for (int i = 0; i < COLUMN_NUMBER; i++) {
				switch (i) {
				case 0: // Input nodes
					for (int j = 0; j < NB_INPUT; j++) {
						;
						Object node = graph.insertVertex(parent, null, df.format(input[j]),
								((this.getPreferredSize().getWidth()) / 7) + 25,
								(((this.getPreferredSize().getHeight()) / NB_INPUT) - 2) * j, NODE_SIZE, NODE_SIZE);
						inputNodeList.add(node);
					}
					break;
				case 1: // Hidden nodes
					for (int j = 0; j < NB_HIDDENNODES; j++) {
						// Nodes creation
						Object node = graph.insertVertex(parent, null, df.format(matrix[j]),
								((((this.getPreferredSize().getWidth()) / 7)) + 25) * 3,
								(((this.getPreferredSize().getHeight()) / NB_HIDDENNODES) - 2) * j, NODE_SIZE,
								NODE_SIZE);
						hiddenNodeList.add(node);
					}
					break;
				case 2: // Output nodes
					for (int j = 0; j < NB_OUTPUT; j++) {
						Object node = graph.insertVertex(parent, null, df.format(output[j]),
								((((this.getPreferredSize().getWidth()) / 7)) + 25) * 5,
								(((this.getPreferredSize().getHeight()) / NB_OUTPUT) - 2) * j, NODE_SIZE, NODE_SIZE);
						outputNodeList.add(node);
					}
					break;
				}
			}

			// Edges creation (inputAxiom)
			for (Object hidden : hiddenNodeList) {
				for (Object input : inputNodeList) {
					Object edge = graph.insertEdge(parent, null, null, input, hidden);
					double value = inputAxiom[hiddenNodeList.indexOf(hidden)][inputNodeList.indexOf(input)];
					this.edgesValuesMap.put(edge, value);

					((mxCell) edge).setStyle(mxConstants.STYLE_STROKECOLOR + "=" + this.getEdgeColor(value) + ";"
							+ mxConstants.STYLE_FONTCOLOR + "=#000000");
				}
			}
			// (outputAxiom)
			for (Object output : outputNodeList) {
				for (Object hidden : hiddenNodeList) {
					Object edge = graph.insertEdge(parent, null, null, hidden, output);
					double value = outputAxiom[outputNodeList.indexOf(output)][hiddenNodeList.indexOf(hidden)];
					this.edgesValuesMap.put(edge, value);

					((mxCell) edge).setStyle(mxConstants.STYLE_STROKECOLOR + "=" + this.getEdgeColor(value) + ";"
							+ mxConstants.STYLE_FONTCOLOR + "=#000000");
				}
			}
		} finally {
			model.endUpdate();
		}

		// apply settings
		mxGraphComponent graphComponent = new mxGraphComponent(graph);

		// Affichage labels input
		int i = 0;
		for (; i < this.NB_INPUT; i++) {
			if (this.nodesTitles.length > i) {
				JLabel label = new JLabel(this.nodesTitles[i], JLabel.CENTER);
				label.setVerticalAlignment(SwingConstants.CENTER);

				label.setLayout(new BoxLayout(label, BoxLayout.PAGE_AXIS));
				label.setBounds(10, (((int) this.getPreferredSize().getHeight() / NB_INPUT) * i) - i, 120, 15);

				label.setBackground(Color.LIGHT_GRAY); // marche pas ?
				label.setBorder(BorderFactory.createLineBorder(Color.black));

				label.setVisible(true);
				this.getLayeredPane().add(label, new Integer(1));
			} else {
				break;
			}
		}
		// Affichage labels output
		for (int j = 0; j <= this.NB_OUTPUT; j++) {
			if (this.nodesTitles.length > i) {
				System.out.println("Loop if entered :" + i);
				System.out.println("node title " + this.nodesTitles[i]);
				JLabel label2 = new JLabel(this.nodesTitles[i], JLabel.CENTER);
				label2.setVerticalAlignment(SwingConstants.CENTER);

				label2.setLayout(new BoxLayout(label2, BoxLayout.PAGE_AXIS));
				label2.setBounds((((((int) this.getPreferredSize().getWidth()) / 7)) + 25) * 5,
						((((int) this.getPreferredSize().getHeight()) / NB_OUTPUT) * j) + 2 * NODE_SIZE, 80, 15);
				label2.setBackground(Color.LIGHT_GRAY);
				label2.setBorder(BorderFactory.createLineBorder(Color.black));

				label2.setVisible(true);
				this.getLayeredPane().add(label2, new Integer(1));

				i++;
			} else {
				break;
			}
		}

		this.add(graphComponent, BorderLayout.CENTER);

		/**
		 * Classe interne MouseAdapter
		 */
		graphComponent.getGraphControl().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				mxCell cell = (mxCell) graphComponent.getCellAt(e.getX(), e.getY());
				mxGraph graph = graphComponent.getGraph();
				mxIGraphModel model = graph.getModel();

				// CLIC DROIT : USE CASE MODIFICATION DE LA VALEUR D'UN AXIOME
				if (SwingUtilities.isRightMouseButton(e)) {

					if (self.editable){
						// Si la cellule cliquee est bien une arrete
						if (cell != null && cell.isEdge()) {
							String value = JOptionPane.showInputDialog("Value ?");
							if (NumberUtils.isParsable(value)) { // on verifie que la chaine entrée est un nombre
								double dValue = Double.valueOf(value);
								dValue = Utils.borderVarDouble(dValue, -1, 1, 0); // valeur remise entre -1 et 1
	
								// mise a jour de la valeur affichee sur le graph
								graph.cellLabelChanged(cell, value, false);
								// mise a jour de la valeur dans la map cell/valeur
								self.edgesValuesMap.put(cell, dValue);
	
								cell.setValue(value);
								// verification de la position de la source et
								// cible de l'arrete dans les listes de nodes
								Object source = cell.getSource();
								Object target = cell.getTarget();
								int j = inputNodeList.indexOf(source);
								if (j != -1) {
									int i = hiddenNodeList.indexOf(target);
									inputAxiom[i][j] = dValue;
								} else {
									j = hiddenNodeList.indexOf(source);
									int i = outputNodeList.indexOf(target);
									outputAxiom[i][j] = dValue;
								}
								// updates model
								wc.updateModelNeuralNetwork(idCreature, inputAxiom, outputAxiom);
	
							} else {
								// string entree n'est pas un chiffre
								// TODO
							}
						} else if (cell != null && cell.isVertex()){
							// Si clic droit sur un node
							// on veut pouvoir mettre a jour la valeur de toutes ses edges
							
							String value = JOptionPane.showInputDialog("Value ?");
							if (NumberUtils.isParsable(value)) { // on verifie que la chaine entrée est un nombre
								double dValue = Double.valueOf(value);
								dValue = Utils.borderVarDouble(dValue, -1, 1, 0); // valeur remise entre -1 et 1
	
								Collection<Object> edges = self.edgesValuesMap.keySet();
								for (Object edge : edges) {
									Object source = ((mxCell) edge).getSource();
									if (source.equals(cell)) {
										// si notre cell est source d'une edge dans la map
										// on update sa valeur
	
										graph.cellLabelChanged(edge, value, false);
										self.edgesValuesMap.put(edge, dValue);
										((mxCell) edge).setValue(value);
	
										Object target = ((mxCell) edge).getTarget();
										int j = inputNodeList.indexOf(source);
										if (j != -1) {
											int i = hiddenNodeList.indexOf(target);
											inputAxiom[i][j] = dValue;
										} else {
											j = hiddenNodeList.indexOf(source);
											int i = outputNodeList.indexOf(target);
											outputAxiom[i][j] = dValue;
										}
									}
								}
								// updates model
								wc.updateModelNeuralNetwork(idCreature, inputAxiom, outputAxiom);
							}
						}
					}

				} else { // SINON : USE CASE CLIC POUR AFFICHER AXIOMES D'UN NODE

					String styleCurrentCellFill = mxConstants.STYLE_FILLCOLOR + "=#f47142;"
							+ mxConstants.STYLE_FONTCOLOR + "=#000000"; // orange
					String styleDefault = mxConstants.STYLE_FILLCOLOR + "=#C3D9FF;" + mxConstants.STYLE_FONTCOLOR
							+ "=#000000;" + mxConstants.STYLE_OPACITY + "=25"; // default light blue

					graph.getModel().beginUpdate();
					try {
						// On remet � z�ro l'affichage avant toute op�ration
						resetDisplay(model);
						if (cell != null && !cell.isEdge()) { // si on clique sur un node
							self.editable = true;
							model.setStyle(cell, styleCurrentCellFill); // on change la couleur en orange

							// mise � jour des edges affich�es : on efface les
							// edges non connect�es � la cellule cliqu�e
							mxCell[] cellArray = { cell }; // transforme la cellule cliquee en array ...
							Object[] edges = graph.getAllEdges(cellArray); // ... pour le bien de cette methode

							Iterator<Entry<Object, Double>> it = edgesValuesMap.entrySet().iterator();
							while (it.hasNext()) { // parcours de toutes les  edges
								Entry<Object, Double> pair = it.next();
								mxCell edge = ((mxCell) pair.getKey());
								if (!(Arrays.asList(edges).contains(edge))) {
									edge.setVisible(false);
								}
								edge.setValue(null);
							}

							// Puis on affiche les valeurs des edges qui nous interessent
							for (int i = 0; i < edges.length; i++) { // Recuperation de toutes les aretes connectees a la cellule
								((mxCell) edges[i]).setValue(df.format(edgesValuesMap.get(edges[i]))); // arrondi a 2 decimales
							}

							// Enfin, mise a jour du style des autres nodes : on
							// baisse l'opacita et on remet la couleur par
							// defaut
							for (Object c : inputNodeList) {
								if (!c.equals(cell)) {
									model.setStyle(c, styleDefault);
									// model.setStyle(c, fillOpacity);
								}
							}
							for (Object c : hiddenNodeList) {
								if (!c.equals(cell)) {
									model.setStyle(c, styleDefault);
									// model.setStyle(c, fillOpacity);
								}
							}
							for (Object c : outputNodeList) {
								if (!c.equals(cell)) {
									model.setStyle(c, styleDefault);
									// model.setStyle(c, fillOpacity);
								}
							}
						}
					} finally {
						graph.getModel().endUpdate();
					}
				}
			}

		});
		graphComponent.getGraphControl().addMouseMotionListener(new MouseAdapter() {

			@Override
			public void mouseMoved(MouseEvent e) {
				// Je laisse le code ici au cas où.
				// Permet de faire un event sur le mouseEntered sur un node.

				// mxCell cell = (mxCell) graphComponent.getCellAt(e.getX(),
				// e.getY());
				// mxGraph graph = graphComponent.getGraph();
				//
				// // si on passe la souris sur un node
				// if (cell != null && !cell.isEdge()) {
				// System.out.println("tooltip?");
				// } else {
				//
				// }
			}
		});
		resetDisplay(model);
	}

	/**
	 * Fonction de remise � z�ro de l'affichage du neural network
	 */
	private void resetDisplay(mxIGraphModel model) {
		this.editable = false;

		String styleDefault = mxConstants.STYLE_FILLCOLOR + "=#C3D9FF;"
		+ mxConstants.STYLE_FONTCOLOR + "=#000000"; // default light blue

		for (Object c : inputNodeList) {
			model.setStyle(c, styleDefault);
		}
		for (Object c : hiddenNodeList) {
			model.setStyle(c, styleDefault);
		}
		for (Object c : outputNodeList) {
			model.setStyle(c, styleDefault);
		}
		Iterator<Entry<Object, Double>> it = edgesValuesMap.entrySet().iterator();
		while (it.hasNext()) {
			Entry<Object, Double> pair = it.next();
			((mxCell) pair.getKey()).setValue(null);
			((mxCell) pair.getKey()).setVisible(true);
		}

	}

	/**
	 * Fonction de g�n�ration de la couleur de l'ar�te Les couleurs g�n�r�es
	 * sont entre le bleu (vers 1) et le rouge (vers -1) (gris pour alentours de
	 * 0.)
	 * 
	 * @param value
	 *            poids de l'ar�te
	 * @return valeur en hexadecimal de la couleur
	 */
	private String getEdgeColor(double value) {

		float floatValue = (float) value;

		float r = (-floatValue) / 2f + 0.5f;
		float g = (1 - (Math.abs(floatValue))) * 0.5f;
		float b = floatValue / 2f + 0.5f;

		Color color = new Color(r, g, b);
		String hex = "#" + Integer.toHexString(color.getRGB()).substring(2);

		return hex;
	}
}
