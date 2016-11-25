package view;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

import model.NeuralNetwork;

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
									8,
									8);
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
									8,
									8);
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
									8,
									8);
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
		
	}
	
}
