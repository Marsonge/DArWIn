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

	//private List<NodeView> nodeList;
	private final int COLUMN_NUMBER = 3;
	private final int NB_INPUT = 16;
	private final int NB_OUTPUT = 2;
	private final int NB_HIDDENNODES = 16;
	private List<Object> inputNodeList;
	private List<Object> hiddenNodeList;
	private List<Object> outputNodeList;
	
	public ViewNeuralNetwork(NeuralNetwork nn){
		
		this.setPreferredSize(new Dimension(500,500));
		this.inputNodeList = new ArrayList<Object>();
		this.hiddenNodeList = new ArrayList<Object>();
		this.outputNodeList = new ArrayList<Object>();
		
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
		
		mxGraphComponent graphComponent = new mxGraphComponent(graph);
	    getContentPane().add(graphComponent);
	    this.add(graphComponent);
		
	}
	
}
