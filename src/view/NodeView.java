package view;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class NodeView extends JLabel{

	private int columnIndex;
	private int verticalIndex;
	
	public NodeView(int columnIndex, int verticalIndex){
		this.columnIndex = columnIndex;
		this.verticalIndex = verticalIndex;
		
		//TODO javafx gen of nodes
	}
	
}
