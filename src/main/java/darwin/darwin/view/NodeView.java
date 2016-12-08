package darwin.darwin.view;

import javax.swing.JLabel;
import javax.swing.JPanel;

import javafx.embed.swing.JFXPanel;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;

@SuppressWarnings("serial")
public class NodeView extends Circle{

	private int columnIndex;
	private int verticalIndex;
	
	public NodeView(int columnIndex, int verticalIndex){
		this.columnIndex = columnIndex;
		this.verticalIndex = verticalIndex;
		
		//TODO define javafx circle properties
		this.setRadius(5);
		this.setStrokeType(StrokeType.OUTSIDE);
		this.setStroke(Color.web("green", 1));
		this.setStrokeWidth(1);
	}
	
}
