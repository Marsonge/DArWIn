package view;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;

import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxIGraphModel;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;

import model.NeuralNetwork;

/**
 * Vue pour le r�seau neuronal d'une cr�ature
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
	
	public ViewNeuralNetwork(NeuralNetwork nn){
		
		this.setPreferredSize(new Dimension(700,850));
		this.inputNodeList = new ArrayList<Object>();
		this.hiddenNodeList = new ArrayList<Object>();
		this.outputNodeList = new ArrayList<Object>();
		
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
					graph.insertEdge(parent, null, null, hidden, input);
				} finally {
					graph.getModel().endUpdate();
				}
			}
		}
		
		for (Object output : outputNodeList) {
			for (Object hidden : hiddenNodeList){
				graph.getModel().beginUpdate();
				try {
					graph.insertEdge(parent, null, null, hidden, output);
				} finally {
					graph.getModel().endUpdate();
				}
			}
		}
		
		mxGraphComponent graphComponent = new mxGraphComponent(graph);
	    getContentPane().add(graphComponent);
	    this.add(graphComponent);
	    
	    //TODO
	    // NE FONCTIONNE PAS COMME IL FAUT
	    // Sans la 2e condition, marche, mais ne differencie pas les nodes des edges.
	    graphComponent.getGraphControl().addMouseListener(new MouseAdapter() 
	    {
	    @Override
	        public void mouseClicked(MouseEvent e) 
	        {    
	    	
	    		mxCell cell = (mxCell) graphComponent.getCellAt(e.getX(), e.getY());
    			mxGraph graph = graphComponent.getGraph();
    			mxIGraphModel model = graph.getModel();

    			String styleCurrentCell = mxConstants.STYLE_FILLCOLOR + "=#f47142"; // orange
    			String styleDefault = mxConstants.STYLE_FILLCOLOR + "=#C3D9FF"; // default light blue
	    		String fillOpacity = mxConstants.STYLE_FILL_OPACITY + "=20";
	    		String styleOpacity = mxConstants.STYLE_OPACITY + "=20";
    			
	    		if (cell != null && !cell.isEdge()){
		    		model.setStyle(cell, styleCurrentCell); // set color of selected cell
		    		
		            for (Object c : inputNodeList){
		            	if (!c.equals(cell)) {
		            		model.setStyle(c, styleDefault);
		            		model.setStyle(c, fillOpacity);
		            		model.setStyle(c, styleOpacity);
		            	}
		            }
		            for (Object c : hiddenNodeList){
		            	if (!c.equals(cell)) {
			            	model.setStyle(c, styleDefault);
		            		model.setStyle(c, fillOpacity);
		            		model.setStyle(c, styleOpacity);
		            	}
		            }
		            for (Object c : outputNodeList){
		            	if (!c.equals(cell)) {
		            		model.setStyle(c, styleDefault);
		            		model.setStyle(c, fillOpacity);
		            		model.setStyle(c, styleOpacity);
		            	}
		            }
	    		} else {
	    			// si on clique n'importe o�, ailleurs que sur une cellule
	    			for (Object c : inputNodeList){
		            		model.setStyle(c, styleDefault);
		            }
		            for (Object c : hiddenNodeList){
			            	model.setStyle(c, styleDefault);
		            }
		            for (Object c : outputNodeList){
		            		model.setStyle(c, styleDefault);
		            }
	    		}
	    	
	    		
	        }
	    });
	    
	    
	    
	    
		
	}
	
}
