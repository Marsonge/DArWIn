package darwin.darwin.view;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

import darwin.darwin.model.NeuralNetwork;

/**
 * Vue pour le r�seau neuronal d'une cr�ature
 * Attention, cette classe utilise la librairie JGraphX.
 * @author lulu
 *
 */

@SuppressWarnings("serial")
public class ViewNeuralNetwork extends JDialog{

	private List<NodeView> nodeList;
	private int columnNumber = 3;
	private final int NB_INPUT = NeuralNetwork.getNbInput();
	private final int NB_OUTPUT = NeuralNetwork.getNbOutput();
	private final int NB_HIDDENNODES = NeuralNetwork.getNbHiddennodes();
	
	@SuppressWarnings("restriction")
	public ViewNeuralNetwork(NeuralNetwork nn){
		
		this.setPreferredSize(new Dimension(500,500));
		this.nodeList = new ArrayList<NodeView>();
		
		for (int i = 0; i<columnNumber; i++){
			switch(i){
				case 0: // Input nodes
					for (int j = 0; j<NB_INPUT; j++){
						NodeView node = new NodeView(i,j);
						node.setCenterX((this.getPreferredSize().getWidth())/4);
						node.setCenterY(((this.getPreferredSize().getHeight())/NB_INPUT)*j);
						nodeList.add(node);
					}
					break;
				case 1: // Hidden nodes
					for (int j = 0; j<NB_HIDDENNODES; j++){
						NodeView node = new NodeView(i,j);
						node.setCenterX(((this.getPreferredSize().getWidth())/4)*2);
						node.setCenterY(((this.getPreferredSize().getHeight())/NB_HIDDENNODES)*j);
						nodeList.add(node);
					}
					break;
				case 2: // Output nodes
					for (int j = 0; j<NB_OUTPUT; j++){
						NodeView node = new NodeView(i,j);
						node.setCenterX(((this.getPreferredSize().getWidth())/4)*3);
						node.setCenterY(((this.getPreferredSize().getHeight())/NB_OUTPUT)*j);
						nodeList.add(node);
						
					}
					break;
			}
		}
		
		//TODO display nodes
		
		mxGraph graph = new mxGraph();
		Object parent = graph.getDefaultParent();
		
		graph.getModel().beginUpdate();
		try {
			Object v1 = graph.insertVertex(parent, null, "Hello", 20, 20, 80, 30);
		    Object v2 = graph.insertVertex(parent, null, "World!", 240, 150, 80, 30);
		    graph.insertEdge(parent, null, "Edge", v1, v2);
		} finally {
			graph.getModel().endUpdate();
		}
		
		mxGraphComponent graphComponent = new mxGraphComponent(graph);
	    getContentPane().add(graphComponent);
	    this.add(graphComponent);

		
		//TODO javafx gen of links betweens nodes
	}
	
}
