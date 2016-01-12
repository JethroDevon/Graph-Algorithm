import java.util.ArrayList; 
import java.io.*;

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

	//stores loaded matrix in form of chars representing binary, must make program more modular
	char[][] ajacencyMatrix  = new char[][]{ 
  { '0', '0', '0', '0', '0', '0', '0', '0' },
  { '0', '0', '0', '0', '0', '0', '0', '0' },
  { '0', '0', '0', '0', '0', '0', '0', '0' },
  { '0', '0', '0', '0', '0', '0', '0', '0' },
  { '0', '0', '0', '0', '0', '0', '0', '0' },
  { '0', '0', '0', '0', '0', '0', '0', '0' },
  { '0', '0', '0', '0', '0', '0', '0', '0' },
  { '0', '0', '0', '0', '0', '0', '0', '0' }
};

	//stores the ajacency matrix with a list of nodes that each own a record of what nodes
	//they are connected to and if they have been visited
	ArrayList<SGN> SG = new ArrayList<SGN>();

	//names for Search Graph Nodes, presently jsut alphabetical characters but could be 
	//a more dynamic and usefull function
	String[] alphamabet = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X", "Y", "Z"};

	ArrayList<String> discovered = new ArrayList<String>();

	//stores the stream of data from a text file
	BufferedReader br;

	//for keeping track of how many rows and cols their is in ajacencyMatrix, ajacency matrix are always squares
	//however this is more stable if there is a read error loading up the 2d array
	int nodeX =0, nodeY =0;

	//this function loads a matrix from a text file, returns true or false for debugging
	boolean loadMatrix(String _path){

		//will be loading from text file therefore try catch block contains this code
		try{

			//loads data from text file at path
			br = new BufferedReader(new FileReader(_path));

			//stores eachline of each string in the text file
			String line = "";		

			//loops through text file converting '0' or '1' into true or false
			while((line = br.readLine()) != null){

				nodeX = 0;

				for(char c: line.toCharArray()){

					if(c == '1'){

						ajacencyMatrix[nodeY][nodeX] = '1';
					}
					nodeX++;			
				}
				
				nodeY++;
			}

			System.out.println("nodeX is: " + nodeX + " and nodeY is : "+nodeY);

		}catch(Exception e){

			//failed, let user know
			System.out.println("Load failed...");
			e.printStackTrace(System.out);
			return false;
		}	

		return true;
	}

	//this function adds the connections to each SGN by pushing a dynamically created
	//SGN onto connectioins
	void connectMatrix(){

		//nested loops initialise each new SGN nodes 'connections' ArrayList
		//with nodes that are connected using the boolean array 'ajacencyMatrix'
		for(int x = 0; x < nodeX - 1; x++ ){

			//creates a new SGN node as a node on the array
			SG.add(new SGN());

			//sets the name, in this case in the form of an alphabetical character
			SG.get(SG.size() -1).setName(alphamabet[x]);

			//this would be a good place to assign a nodes object with a function that
			//could take object arguments
			//SG.get(SG.size() -1).setObject(magicallyCreatePossibleObjects());

			for(int y = 0; y <= nodeY -1; y++){

				//if true then there is a connection for other objects
				if(ajacencyMatrix[x][y] == '1'){

					//create objects with same name, will set same objects in a later version of this code
					SG.get(SG.size() -1).connections.add(alphamabet[y]);
				}
			}
		}

		//debug info, lets uer know that function has been called in console
		System.out.println("matrix connected");
	}

	//returns SGN node if name is equal to args
	SGN getByName(String _name){

		for(int f = 0; f < SG.size(); f++){

				if(SG.get(f).getName() == _name){

					return  SG.get(f);
				}
		}

		System.out.println("crash!");

		//else return null
		return null;
	}

	//shows all elements in ajacency matrix
	void showAjacencyMatrix(){

		for(int x = 0; x < nodeX; x++ ){		
			for(int y = 0; y < nodeY; y++){

				System.out.print(ajacencyMatrix[x][y]);
			}
			System.out.println();
		}
	}

	//shows all connections in SGN array list SG
	void showSGN(){

		for(int k = 0; k < SG.size(); k++){

			System.out.print(SG.get(k).getName() + " is connected to: ");

			for(int f = 0; f < SG.get(k).connections.size(); f++){

				System.out.print(SG.get(k).connections.get(f) + " ");
			}

				System.out.println();
		}
	}

	//recursive depth first search
	void recursiveDFS(SGN _graph){

		//label first vertex as discovered
		_graph.setVisited();	
		
		//for all edges belonging to _graph
		for(int v = 0; v < _graph.connections.size() -1; v++){

			//if the connection has not been visited
			if(!getByName(_graph.connections.get(v)).getVisited()){

				//recursivley call function again
				recursiveDFS(getByName(_graph.connections.get(v)));
			}
		}	
	}

	//iterative depth first search
	void iterativeDFS(SGN _graph){

		//create new stack object
		Stack s = new Stack();

		s.push(_graph);

		while(!s.stackEmpty()){

			SGN temp = (SGN)s.pop();

			if(!temp.getVisited()){

				temp.setVisited();

				for(int v = 0; v < temp.connections.size() ; v++){

					s.push(getByName(temp.connections.get(v)));
				}
			}
		}
	}


	//iterative breadth first search
	void iteativeBFS(SGN _graph){

		Queue q = new Queue();

		q.enqueue(_graph);

		while(!q.queueEmpty()){

			SGN temp = (SGN)q.dequeue();

			if(!temp.getVisited()){

				temp.setVisited();

				for(int v = 0; v < temp.connections.size() ; v++){

					q.enqueue(getByName(temp.connections.get(v)));
				}
			}
		}
	}

	///////////////////////////////////////////////////////////////////////////
	//////This node class contains a list of nodes that it is connected to/////
	////an object to store data on and an alphabetical name to identify it.	 //
	///////////////////////////////////////////////////////////////////////////
	public class SGN{
 
		private String name;
		private Object obj;

		//stores all connections in the form of characters
		private ArrayList<String> connections = new ArrayList<String>();

		//return next unvisited node from connections using names position alpha numerically
		SGN getNext(SGN _g){

			//gets next unvisited connection
			for(int x = 0; x < connections.size() -1; x++){
				
				//if the node has not been visited yet...
				if(!getByName(connections.get(x)).getVisited()){

					//then initialise temp SGN node with that to be returned as next node to visit
					_g = getByName(connections.get(x));
					break;
				} 
			}

			//returns next connection in list or self if there is no more connections
			return _g;
		}

		//getters and setters fot visited, name and object.
		void setVisited(){

			discovered.add(name);
			System.out.println(getName() + ", ");
		}

		boolean getVisited(){

			//serches array to see if the node has been discovered yet
			for(int d = 0; d < discovered.size() -1; d++){

				if(discovered.get(d) == name){
					return true;
				}
			}
			
			return false;
			
		}

		String getName(){

			return name;
		}

		void setName(String _n){

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