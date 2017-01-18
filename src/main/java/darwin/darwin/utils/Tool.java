package darwin.darwin.utils;


public enum Tool {
	BRUSH("Brush"),
	RECTANGLE("Rectangle"),
	FILLBUCKET("Fill Bucket"),
	LARGEBRUSH("Large Brush");
	
	private String s;
	private Tool(String s){
		this.s = s;
	}
	
	public String getName(){
		return s;
	}

}
