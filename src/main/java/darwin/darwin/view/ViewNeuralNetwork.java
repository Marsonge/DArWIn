package darwin.darwin.view;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.JDialog;

import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxIGraphModel;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;

import darwin.darwin.model.NeuralNetwork;


/**
 * Vue pour le réseau neuronal d'une créature
 * Attention, cette classe utilise la librairie JGraphX.
 * @author lulu
 *
 */

@SuppressWarnings("serial")
public class ViewNeuralNetwork extends JDialog{

	private final int COLUMN_NUMBER = 3;
	private final int NB_INPUT = 16;
	private final int NB_OUTPUT = 2;
	private final int NB_HIDDENNODES = 16;
	private final int NODE_SIZE = 15;
	private List<Object> inputNodeList;
	private List<Object> hiddenNodeList;
	private List<Object> outputNodeList;
	private Map<Object, Float> edgesValuesMap;
	private NeuralNetwork nn;
	
	public ViewNeuralNetwork(NeuralNetwork nn){
		this.nn = nn;
		this.setPreferredSize(new Dimension(700,850));
		this.inputNodeList = new ArrayList<Object>();
		this.hiddenNodeList = new ArrayList<Object>();
		this.outputNodeList = new ArrayList<Object>();
		this.edgesValuesMap = new HashMap<Object, Float>();
		
		float[][] inputAxiom = nn.getInputAxiom();
		float[][] outputAxiom = nn.getOutputAxiom();
		
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
		
		for (int i = 0; i<COLUMN_NUMBER; i++){
			switch(i){
				case 0: // Input nodes
					for (int j = 0; j<NB_INPUT; j++){;
						graph.getModel().beginUpdate();
						try {
							Object node = graph.insertVertex(parent,
									null,
									null,
									(this.getPreferredSize().getWidth())/4,
									((this.getPreferredSize().getHeight())/NB_INPUT)*j,
									NODE_SIZE,
									NODE_SIZE);
							inputNodeList.add(node);
						} finally {
							graph.getModel().endUpdate();
						}
					}
					break;
				case 1: // Hidden nodes
					for (int j = 0; j<NB_HIDDENNODES; j++){
						
						graph.getModel().beginUpdate();
						try {
							// Nodes creation
							Object node = graph.insertVertex(parent,
									null,
									null,
									((this.getPreferredSize().getWidth())/4)*2,
									((this.getPreferredSize().getHeight())/NB_HIDDENNODES)*j,
									NODE_SIZE,
									NODE_SIZE);
							hiddenNodeList.add(node);
						} finally {
							graph.getModel().endUpdate();
						}
					}
					break;
				case 2: // Output nodes
					for (int j = 0; j<NB_OUTPUT; j++){
						graph.getModel().beginUpdate();
						try {
							Object node = graph.insertVertex(parent,
									null,
									null,
									((this.getPreferredSize().getWidth())/4)*3,
									((this.getPreferredSize().getHeight())/NB_OUTPUT)*j,
									NODE_SIZE,
									NODE_SIZE);
							outputNodeList.add(node);
						} finally {
							graph.getModel().endUpdate();
						}
						
					}
					break;
			}
		}
		
		// Edges creation (inputAxiom)
		for (Object hidden : hiddenNodeList) {
			for (Object input : inputNodeList){
				graph.getModel().beginUpdate();
				try {
					Object edge = graph.insertEdge(parent, null, null, hidden, input);
					float value = inputAxiom[hiddenNodeList.indexOf(hidden)][inputNodeList.indexOf(input)];
					this.edgesValuesMap.put(edge, value);
				} finally {
					graph.getModel().endUpdate();
				}
			}
		}
		// (outputAxiom)
		for (Object output : outputNodeList) {
			for (Object hidden : hiddenNodeList){
				graph.getModel().beginUpdate();
				try {
					Object edge = graph.insertEdge(parent, null, null, output, hidden);
					float value = outputAxiom[outputNodeList.indexOf(output)][hiddenNodeList.indexOf(hidden)];
					this.edgesValuesMap.put(edge, value);
				} finally {
					graph.getModel().endUpdate();
				}
			}
		}
		
		mxGraphComponent graphComponent = new mxGraphComponent(graph);
	    getContentPane().add(graphComponent);
	    this.add(graphComponent);
	    
	    /**
	     * Classe interne MouseAdapter
	     */
	    graphComponent.getGraphControl().addMouseListener(new MouseAdapter() 
	    {
	    @Override
	        public void mouseClicked(MouseEvent e) 
	        {    
	    		System.out.println("MOUSE CLICKED");
	    		mxCell cell = (mxCell) graphComponent.getCellAt(e.getX(), e.getY());
    			mxGraph graph = graphComponent.getGraph();
    			mxIGraphModel model = graph.getModel();

    			String styleCurrentCellFill = mxConstants.STYLE_FILLCOLOR + "=#f47142"; // orange
    			String styleCurrentCellStroke = mxConstants.STYLE_STROKECOLOR + "=#f47142"; // orange
    			String styleDefault = mxConstants.STYLE_FILLCOLOR + "=#C3D9FF"; // default light blue
	    		//String fillOpacity = mxConstants.STYLE_FILL_OPACITY + "=20"; // ne fonctionne pas avec la version 3.1.2 de JGraphX
	    		String styleOpacity = mxConstants.STYLE_OPACITY + "=25";
	    		String fontColor = mxConstants.STYLE_FONTCOLOR+"=#ffd000";
	            DecimalFormat df = new DecimalFormat("#.##");
	            
	            //TODO
	            // - afficher les infos en couleur differente (lisible)
	            // - (facultatif) bouger le code vers une methode "mouseOver" plutôt que "mouseClicked"
    			
	            graph.getModel().beginUpdate();
	            try {
	            	// On remet à zéro l'affichage avant toute opération
	            	resetDisplay(model);
		    		if (cell != null && !cell.isEdge()){ // si on clique sur un node
			    		model.setStyle(cell, styleCurrentCellFill); // set color of selected cell
			    		
			    		// mise à jour des edges affichées : on efface les edges non connectées à la cellule cliquée
			            mxCell[] cellArray = {cell}; // transforme la cellule cliquée en array ...
			            Object[] edges = graph.getAllEdges(cellArray); // ... pour le bien de cette méthode
			            
			            Iterator<Entry<Object, Float>> it = edgesValuesMap.entrySet().iterator();
			            while (it.hasNext()) { // parcours de toutes les edges
			                Map.Entry pair = (Map.Entry)it.next();
			                mxCell edge = ((mxCell) pair.getKey());
			                if (!(Arrays.asList(edges).contains(edge))) {
			                	edge.setVisible(false);
			                }
			                edge.setValue(null);
			            }
			            
			            // Puis on affiche les valeurs des edges qui nous interessent
			            for (int i = 0; i<edges.length; i++){ // Récupération de toutes les aretes connectées a la cellule
			            	((mxCell) edges[i]).setValue(df.format(edgesValuesMap.get(edges[i]))); // arrondi à 2 décimales
			            }
			    		
			    		// Enfin, mise à jour du style des autres nodes : on baisse l'opacité et on remet la couleur par défaut
			            for (Object c : inputNodeList){
			            	if (!c.equals(cell)) {
			            		model.setStyle(c, styleDefault);
			            		//model.setStyle(c, fillOpacity);
			            		model.setStyle(c, styleOpacity);
			            	}
			            }
			            for (Object c : hiddenNodeList){
			            	if (!c.equals(cell)) {
				            	model.setStyle(c, styleDefault);
			            		//model.setStyle(c, fillOpacity);
			            		model.setStyle(c, styleOpacity);
			            	}
			            }
			            for (Object c : outputNodeList){
			            	if (!c.equals(cell)) {
			            		model.setStyle(c, styleDefault);
			            		//model.setStyle(c, fillOpacity);
			            		model.setStyle(c, styleOpacity);
			            	}
			            }
		    		}
	            } finally {
	            	graph.getModel().endUpdate();
	            }
	        
	        }
	    });
	    
	}
	
	/**
	 * Fonction de remise à zéro de l'affichage du neural network
	 */
	private void resetDisplay(mxIGraphModel model){
		
		String styleDefault = mxConstants.STYLE_FILLCOLOR + "=#C3D9FF"; // default light blue
		
		for (Object c : inputNodeList){
	    		model.setStyle(c, styleDefault);
	    }
	    for (Object c : hiddenNodeList){
	        	model.setStyle(c, styleDefault);
	    }
	    for (Object c : outputNodeList){
	    		model.setStyle(c, styleDefault);
	    }
	    Iterator<Entry<Object, Float>> it = edgesValuesMap.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        ((mxCell) pair.getKey()).setValue(null);
	        ((mxCell) pair.getKey()).setVisible(true);
	    }
    }
}
