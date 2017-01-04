package darwin.darwin.model;

/**
 * modèle de données pour un noeud du réseau neuronal
 * 
 * @author lucie
 *
 */
public class Node {

	private double value;
	private String purpose;

	/**
	 * constructor
	 */
	public Node(int value, String purpose) {
		this.value = value;
		this.purpose = purpose;
	}

	/**
	 * getters / setters
	 */
	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

}
