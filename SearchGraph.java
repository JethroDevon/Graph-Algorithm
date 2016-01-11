import java.util.ArrayList; 

/////////////////////////////////////////////////////////
//   This class will create a graph that can be used   //	
//  to run Depth First Search and Breadth First Search // 
//	operations on and store a data object on each node //
// 		the nodes are called SGN as an abreviation 	   //
// 					for Search Graph Node.     		   //	
/////////////////////////////////////////////////////////
//													   //
//		     by Jethro Holcroft 01/01/2016			   //
//													   //
/////////////////////////////////////////////////////////

public class SearchGraph{

	//stores loaded matrix in boolean form
	boolean[][] ajacencyMatrix;

	//stores the ajacency matrix with a list of nodes that each own a record of what nodes
	//they are connected to and if they have been visited
	ArrayList<SGN> SG = new ArrayList<SG>();

	//names for Search Graph Nodes, presently jsut alphabetical characters but could be 
	//a more dynamic and usefull function
	char[] alphamabet = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};

	//stores the stream of data from a text file
	BufferedReader br;

	//this function loads a matrix from a text file
	void loadMatrix(String _path){

		//loads data from text file at path
		br = new BufferedReader(new FileReader(_path));

		//stores the temporary char in the string array that contains the ajacency matrix
		char bin = 0;

		//stores eachline of each string in the text file
		String line = "";

		//loops through text file converting '0' or '1' into true or false
		while(line = br.readLine()){

			for(int i = 0; i < line.length(); i++){

				bin = line.charAt(i);

				//initialisees an X by Y grid of boolean states to create an ajacency matrix
				if(bin == '1'){

					ajacencyMatrix[x][y] = true;
				}else{

					ajacencyMatrix[x][y] = false;
				}
			}
		}

		//debug information, lets user know that loadmatrix was called
		System.out.println("ajacency matrix loaded.");
	}

	//this function adds the connections to each SGN by pushing a dynamically created
	//SGN onto connectioins
	void connectMatrix(){

		//nested loops initialise each new SGN nodes 'connections' ArrayList
		//with nodes that are connected using the boolean array 'ajacencyMatrix'
		for(int x = 0; x < ajacencyMatrix.size(); x++ ){

			//creates a new SGN node as a node on the array
			SG.add(new SGN());

			//sets the name, in this case in the form of an alphabetical character
			SG.get(SG.size() -1).setName(alphamabet[x]);

			//this would be a good place to assign a nodes object with a function that
			//could take object arguments
			//SG.get(SG.size() -1).setObject(magicallyCreatePossibleObjects());

			for(int y = 0; y < ajacencyMatrix.size(); y++){

				//if true then there is a connection for other objects
				if(ajacencyMatrix[x][y] == true){

					//create objects with same name, will set same objects in a later version of this code
					SG.get(SG.size() -1).connections.add(alphamabet[y]);
				}
			}
		}

		//debug info, lets uer know that function has been called in console
		System.out.println("matrix connected");
	}

	//shows all elements in ajacency matrix
	void showAjacencyMatrix(){

		for(int x = 0; x < ajacencyMatrix.size(); x++ ){		
			for(int y = 0; y < ajacencyMatrix.size(); y++){

				System.out.print(ajacencyMatrix[x][y]);
			}
		}

		System.out.println();
	}

	//shows all connections in SGN array list SG
	void showSGN(){

		for(int k = 0; k < SG.size() -1; k++){

			System.out.print(SG.get(k).getName() + " is connected to: ");

			for(int f = 0; f < SG.get(k).connections.size(); f++){

				System.out.print(SG.get(k).connections.get(f).getName() + " ");
			}
		}
	}

	///////////////////////////////////////////////////////////////////////////
	//////This node class contains a list of nodes that it is connected to/////
	////an object to store data on and an alphabetical name to identify it.	 //
	///////////////////////////////////////////////////////////////////////////
	public class SGN{

		private char name;
		private Object obj;

		//stores all connections in the form of characters
		private ArrayList<char> connections = new ArrayList<char>();

		//visited default set to false
		private boolean visited = false;

		//connections to other nodes are added in load matrix
		void addConnection(SGN _con){

			connections.add(_con);
		}

		//return next unvisited node from connections using names position alpha numerically
		char getNext(){

			//temporary node returns itself if there are no nodes to visit
			//this will be used to say that there are no ajacent nodes as 
			//returning no ajacent nodes is not an option
			char temp = name;

			//gets next unvisited connection
			for(int x = 0; x < connections.size() -1; x++){

				if(!connections.get(x).getVisited && connections.get(x).getName() < temp.getName()){

					temp = connections.get(x).getName();
				}
			}

			//returns next connection in list or self if there is no more connections
			return temp;
		}

		//getters and setters fot visited, name and object.
		void setVisited(){

			visited = true;
		}

		boolean getVisited(){

			return visited;
		}

		char getName(){

			return name;
		}

		void setName(char _n){

			name = _n;
		}

		Object getObject(){

			return obj;
		}

		void setObject(Object _o){

			obj = _o;
		}

	}//end SGN class
}//end SearchGraph class