package view;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.NeuralNetwork;

@SuppressWarnings("serial")
public class ViewNeuralNetwork extends JDialog{

	private List<NodeView> nodeList;
	private int columnNumber = 3;
	private final int NB_INPUT = 16;
	private final int NB_OUTPUT = 2;
	private final int NB_HIDDENNODES = 16;
	private float[] matrix;
	private float[][] inputAxiom;
	private float[][] outputAxiom;
	final JFXPanel fxPanel = new JFXPanel();
	private Group inputNodes;
	private Group hiddenNodes;
	private Group outputNodes;
	private Group rootNodes;
	
	public ViewNeuralNetwork(NeuralNetwork nn){
		this.add(fxPanel);
		this.setPreferredSize(new Dimension(500,500));
		this.nodeList = new ArrayList<NodeView>();
		this.rootNodes = new Group();
		
		for (int i = 0; i<columnNumber; i++){
			switch(i){
				case 0: // Input nodes
					for (int j = 0; j<NB_INPUT; j++){
						//this.inputNodes = new Group();
						NodeView node = new NodeView(i,j);
						node.setCenterX((this.getPreferredSize().getWidth())/4);
						node.setCenterY(((this.getPreferredSize().getHeight())/NB_INPUT)*j);
						nodeList.add(node);
						rootNodes.getChildren().add(node);
					}
					break;
				case 1: // Hidden nodes
					for (int j = 0; j<NB_HIDDENNODES; j++){
						//this.hiddenNodes = new Group();
						NodeView node = new NodeView(i,j);
						node.setCenterX(((this.getPreferredSize().getWidth())/4)*2);
						node.setCenterY(((this.getPreferredSize().getHeight())/NB_HIDDENNODES)*j);
						nodeList.add(node);
						rootNodes.getChildren().add(node);
					}
					break;
				case 2: // Output nodes
					for (int j = 0; j<NB_OUTPUT; j++){
						//this.outputNodes = new Group();
						NodeView node = new NodeView(i,j);
						node.setCenterX(((this.getPreferredSize().getWidth())/4)*3);
						node.setCenterY(((this.getPreferredSize().getHeight())/NB_OUTPUT)*j);
						nodeList.add(node);
						rootNodes.getChildren().add(node);
						
					}
					break;
			}
		}
		
		//TODO display nodes
		Platform.runLater(new Runnable() {
            @Override
            public void run() {
            	System.out.println("RUN RUNNABLE\n");
                initFX(fxPanel);
            }
        });
		
		//TODO javafx gen of links betweens nodes
	}
	
	private  void initFX(JFXPanel fxPanel) {
        // This method is invoked on JavaFX thread
		System.out.println("INIT FX\n");
		Stage stage = new Stage();
		stage.initModality(Modality.NONE);
		stage.setHeight(500);
		stage.setWidth(500);
        Scene scene = this.createScene();
        fxPanel.setScene(scene);
        stage.setScene(scene);
        stage.show();
    }
	
	 private Scene createScene() {
		 System.out.println("CREATE SCENE\n");
	        Scene scene = new Scene(this.rootNodes, 500, 500, Color.ALICEBLUE);
	        return (scene);
	    }
	
}
