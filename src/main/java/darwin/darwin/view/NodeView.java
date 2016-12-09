package darwin.darwin.view;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;

@SuppressWarnings("restriction")
public class NodeView extends Circle{

	private int columnIndex;
	private int verticalIndex;
	
	public NodeView(int columnIndex, int verticalIndex){
		this.setColumnIndex(columnIndex);
		this.setVerticalIndex(verticalIndex);
		
		//TODO define javafx circle properties
		this.setRadius(5);
		this.setStrokeType(StrokeType.OUTSIDE);
		this.setStroke(Color.web("green", 1));
		this.setStrokeWidth(1);
	}

	public int getColumnIndex() {
		return columnIndex;
	}

	public void setColumnIndex(int columnIndex) {
		this.columnIndex = columnIndex;
	}

	public int getVerticalIndex() {
		return verticalIndex;
	}

	public void setVerticalIndex(int verticalIndex) {
		this.verticalIndex = verticalIndex;
	}
	
}
