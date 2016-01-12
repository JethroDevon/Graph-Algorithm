class SGTest{

	public static void main(String [] args){

		SearchGraph sg = new SearchGraph();

		//loads matrix from text file, in this case its ajacency matrix 1.txt
		if(sg.loadMatrix("ajacencyMatrix1.txt")){

			//this function applies the loaded matrix to an array of nodes with initialised connections
			sg.connectMatrix();

			System.out.println("prints out ajacency matrix in binary form\n");

			//this shows the loaded ajacency matrix from the text file
			sg.showAjacencyMatrix();

			System.out.println("shows the connections of each node\n");

			//this converts the ajacency matrix int to text, this explains which nodes are connected to which other nodes
			sg.showSGN();

			System.out.println("showing recursive depth first search output\n");

			//recursivley perform a depth first search on first node in node list
			sg.recursiveDFS(sg.SG.get(0));

			System.out.println("Showing iterative depth first search output\n");

			//iterativley perform the depth first search algorithm on the first node in the node list
			sg.iterativeDFS(sg.SG.get(0));


		}else{

			System.out.println("Program closing, load failed, hope lost.");
		}
	}
}