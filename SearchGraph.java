import java.util.ArrayList; 

/////////////////////////////////////////////////////////
//   This class will create a graph that can be used   //	
//  to run Depth First Search and Breadth First Search // 
//	operations on and store a data object on each node // 
/////////////////////////////////////////////////////////
//													   //
//		     by Jethro Holcroft 01/01/2016			   //
//													   //
/////////////////////////////////////////////////////////

public class SearchGraph{

	//stores loaded matrix
	boolean[][] ajacencyMatrix;

	//stores the stream of data from a text file
	BufferedReader br;

	//this function loads a matrix from a text file
	void loadMatrix(String _path){

		//loads data from text file at path
		br = new BufferedReader(new FileReader(_path));

		//stores the temporary char in the string array that contains the ajacency matrix
		char bin = 0;

		//loops through text file converting '0' or '1' into true or false
		for(){
			for(){

				//initialisees an X by Y grid of boolean states to create an ajacency matrix
				if(bin == '1'){

					ajacencyMatrix[x][y] = true;
				}else{

					ajacencyMatrix[x][y] = false;
				}
			}
		}
	}

	//this function adds the connections to each SearchGraphnode by pushing a dynamically created
	//SearchGraphNode onto connectioins
	void connectMatrix(){}







	///////////////////////////////////////////////////////////////////////////
	//////This node class contains a list of nodes that it is connected to/////
	////an object to store data on and an alphabetical name to identify it.	 //
	///////////////////////////////////////////////////////////////////////////
	public class SearchGraphNode{

		private char name;
		private Object obj;

		//stores all connections in the form of characters
		private ArrayList<SearchGraphNode> connections = new ArrayList<cSearchGraphNode>();

		//visited default set to false
		private boolean visited = false;

		//connections to other nodes are added in load matrix
		void addConnection(SearchGraphNode _con){

			connections.add(_con);
		}

		//return next unvisited node from connections using names position alpha numerically
		//the argument is the present node
		SearchGraphNode getNext(){

			//temporary node returns itself if there are no nodes to visit
			//this will be used to say that there are no ajacent nodes as 
			//returning no ajacent nodes is not an option
			DSFNode temp = this;

			//gets next unvisited connection
			for(connections: x){

				if(!x.getVisited && x.getName() < temp.getName()){

					temp = x;
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

	}//end SearchGraphNode class
}//end SearchGraph class