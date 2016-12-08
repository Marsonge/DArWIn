package darwin.darwin.model.grid;

import java.util.*;



// Heavily inspired of http://stackoverflow.com/a/5532726 
// http://www.redblobgames.com/maps/terrain-from-noise/#demo
// http://stackoverflow.com/questions/2755750/diamond-square-algorithm
// https://bitbucket.org/sjl/ymir/src/d9aabdb0ab1057dbd88c495dd2515a7c407022bd/src/ymir/rendering.wisp?at=default&fileviewer=file-view-default
// http://stevelosh.com/blog/2016/06/diamond-square/

public class NoiseGrid {
	
    private double[][] data;
    float roughness;
    private Random randomSeed;
    private int seed;
    private int size;


    public NoiseGrid(Random rSeed, float roughness, int size) {
        this.roughness = roughness / size;
        this.data = new double[size][size];
        if(rSeed != null){
        	this.seed = 0;
        	this.randomSeed = rSeed;
        }
        else{
        	Random r = new Random();
        	this.seed = Math.abs(r.nextInt());
            this.randomSeed = new Random(this.seed);
        }
        this.size = size;
    }
    
    public void initialise() {
    	//an initial seed value for the corners of the data
    	final double SEED = randomSeed.nextInt(Math.round(2*roughness));
    	//seed the data
    	data[0][0] = data[0][size-1] = data[size-1][0] = 
    	  data[size-1][size-1] = SEED;
    	
    	for(int sideLength = size-1;sideLength >= 2;sideLength /=2, roughness/= 2.0){ //On réduit le carré de recherche et l'entropie plus on avance
    	  int halfSide = sideLength/2;

    	  //Etape carrée : on génère la valeur du centre du carré x,y,x+sideLength,y+sideLength
    	  for(int x=0;x<size-1;x+=sideLength){
    	    for(int y=0;y<size-1;y+=sideLength){
    	      //Calcul du moyen de chaque coin
    	      double avg = data[x][y] + //top left
    	      data[x+sideLength][y] +//top right
    	      data[x][y+sideLength] + //lower left
    	      data[x+sideLength][y+sideLength];//lower right
    	      avg /= 4.0;

    	      //On change légèrement la valeur du centre grâce à l'entropie
    	      data[x+halfSide][y+halfSide] = avg + (randomSeed.nextDouble()*2*roughness) - roughness; //La valeur finale est entre -roughness,roughness
    	    }
    	  }

    	  //Etape diamant
    	  //On calcule le centre des diamants construits à l'étape précédente
  	      //NOTE: utilisez x-1 pour que les valeurs wrappent around, x pour que ça ne soit pas le cas
    	  for(int x=0;x<size;x+=halfSide){
    	    //NOTE: utilisez y-1 pour que les valeurs wrappent around, y pour que ça ne soit pas le cas
    	    for(int y=(x+halfSide)%sideLength;y<size;y+=sideLength){
    	      //x, y est le centre du diamant
    	      //note : le modulo et l'ajout de la taille sont nécessaires pour utiliser les coins de l'autre côté, si on wrap around
    	      
	    	  double avg = 
	                data[(x-halfSide+size-1)%(size-1)][y] + //left of center
	                data[(x+halfSide)%(size-1)][y] + //right of center
	                data[x][(y+halfSide)%(size-1)] + //below center
	                data[x][(y-halfSide+size-1)%(size-1)]; //above center
    	      avg /= 4.0;
    	      
    	      avg = avg + (randomSeed.nextDouble()*2*roughness) - roughness; //Comme plus haut, entre -roughness et roughness

    	      data[x][y] = avg;

    	      //Décommentez ceci si vous voulez que le monde soit "rond", ou wrap around
    	      if(x == 0)  data[size-1][y] = avg;
    	      if(y == 0)  data[x][size-1] = avg;
    	    }
    	  }
    	}
    }
 


        
    public double[][] getNoiseGrid(){
    	return data;
    }

	public int getSeed() {
		return this.seed;
	}
    
}
