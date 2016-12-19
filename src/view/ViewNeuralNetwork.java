package view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JPanel;

import model.NeuralNetwork;

public class ViewNeuralNetwork extends JDialog{

	private List<NodeView> nodeList;
	private int columnNumber = 3;
	private final int NB_INPUT = 16;
	private final int NB_OUTPUT = 2;
	private final int NB_HIDDENNODES = 16;
	private float[] matrix;
	private float[][] inputAxiom;
	private float[][] outputAxiom;
	
	public ViewNeuralNetwork(NeuralNetwork nn){
		this.nodeList = new ArrayList<NodeView>();
		
		for (int i = 0; i<columnNumber; i++){
			switch(i){
				case 0: // Input nodes
					for (int j = 0; j<NB_INPUT; j++){
						NodeView node = new NodeView(i,j);
						nodeList.add(node);
						this.add(node);
					}
					break;
				case 1: // Hidden nodes
					for (int j = 0; j<NB_HIDDENNODES; j++){
						NodeView node = new NodeView(i,j);
						nodeList.add(node);
						this.add(node);
					}
					break;
				case 2: // Output nodes
					for (int j = 0; j<NB_OUTPUT; j++){
						NodeView node = new NodeView(i,j);
						nodeList.add(node);
						this.add(node);
					}
					break;
			}
		}
		
		//TODO javafx gen of links betweens nodes
	}
	
}
