
	
/////////////////////////////////////////////////////////
//stackHandler will perform operations on objects////////
//belonging to the node class and will contain///////////
//methods that set or get data in ADT structures.////////
/////////////////////////////////////////////////////////

public class Stack{

	//this is the root of the data structure.
	public Node head; 

	//constructor creates a starting node.
	public Stack(){

		//creates an initial node to build from.
		head = new Node();	
	}

	//constructs an empty stack by creating a node object and assigning it to 
	//'head' member objects 'next' member object.
	public void push(Object o){

		//temporary variable is instanciated with head variable.
		Node temp = head;

		//new node add is created
		Node add = new Node(o);

		//get last node in linked stack
		temp = returnLast();

		//set that last nodes linker node to node 'add' 
		temp.setNext(add);
	}


	//returns and deletes node at end of stack.
	public Object pop(){

		//temporary variable is instanciated with head variable.
		Node temp = head;
		Node temp2 = temp;

		//while loop iterates through stack
		while(temp.getNext() != null){

			//set temp2 to contain temp to preserve last element in stack
			temp2 = temp;
			temp = temp.getNext();
		}
		
		temp2.setNext(null);

		//returns last member in stack
		return temp.getObject();
	}

	//uses recursion to iterate through the stack from point of node in args
	//put head in args to return length of stack.
	public int recursiveCount(Node n){

		if(n == null){

			return 0;
		}else{

				return 1 + recursiveCount(n.getNext());		
		}
	}

	//returns true if stack is empty
	public boolean stackEmpty(){

		if(head.getNext() == null){

			return true;
		}else{

			return false;
		}
	}

	//returns last node in main list
	public Node returnLast(){

		Node temp = head;

		//while loop iterates through list
		while(temp.getNext() != null){

			temp = temp.getNext();
		}

		return temp;
	}



	////////////////////////////////////////////////////////////////
	//////The node class creates an object that can be linked///////
	//////to another object identicle to itself, it contains ///////
	//////////////////////an object.////////////////////////////////
	////////////////////////////////////////////////////////////////
	public class Node{

		//variables contain two parts two node structure, one to
		//contain data the other to contain a link to another node.
		private Object obj;
		private Node next;

		//sets next to null, this cinstructor takes no object args
		public Node(){
			
			obj = null;
			next = null;
		}

		//initializes object variable, sets next to null
		public Node(Object o){
			
			obj = o;
			next = null;
		}
				
		//sets node objects private string variable to args.
		void setObj(Object o){

			obj = o;
		}

		//returns this node objects private object 'obj'
		Object getObject(){

			return obj;
		}

		//sets this nodes private node object to node in args.
		void setNext(Node nextNode){

			next = nextNode;
		}

		//this function reads the value of this nodes private node objects string data.
		Node getNext(){

			return next;
		} 

	}//end node class
}//end stack class
