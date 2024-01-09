package transit;

import java.util.ArrayList;

/**
 * This class contains methods which perform various operations on a layered linked
 * list to simulate transit
 * 
 * @author Ishaan Ivaturi
 * @author Prince Rawal
 */
public class Transit {
	private TNode trainZero; // a reference to the zero node in the train layer

	/* 
	 * Default constructor used by the driver and Autolab. 
	 * DO NOT use in your code.
	 * DO NOT remove from this file
	 */ 
	public Transit() { trainZero = null; }

	/* 
	 * Default constructor used by the driver and Autolab. 
	 * DO NOT use in your code.
	 * DO NOT remove from this file
	 */
	public Transit(TNode tz) { trainZero = tz; }
	
	/*
	 * Getter method for trainZero
	 *
	 * DO NOT remove from this file.
	 */
	public TNode getTrainZero () {
		return trainZero;
	}

	private TNode getNodeHead(TNode head, int targetLocation) {

		TNode curr = head;

		while (curr != null) {
			if(curr.getLocation() == targetLocation) {
				return curr;
			}
			else {
				curr = curr.getNext();
			}
		}
		return null;
	}

	/**
	 * Makes a layered linked list representing the given arrays of train stations, bus
	 * stops, and walking locations. Each layer begins with a location of 0, even though
	 * the arrays don't contain the value 0. Store the zero node in the train layer in
	 * the instance variable trainZero.
	 * 
	 * @param trainStations Int array listing all the train stations
	 * @param busStops Int array listing all the bus stops
	 * @param locations Int array listing all the walking locations (always increments by 1)
	 */
	public void makeList(int[] trainStations, int[] busStops, int[] locations) {

		trainZero = new TNode(0);
		trainZero.setNext(new TNode());
		TNode ptr = trainZero.getNext();

		TNode busZero = new TNode(0);
		busZero.setNext(new TNode());
		TNode ptr2 = busZero.getNext();

		TNode walkZero = new TNode(0);
		walkZero.setNext(new TNode());
		TNode ptr3 = walkZero.getNext();

		//train stations
		for(int i = 0; i < trainStations.length; i++) {
			ptr.setLocation(trainStations[i]);
			if(i != trainStations.length - 1) {
				ptr.setNext(new TNode());
				ptr = ptr.getNext();
			}
		}

		//bus stations
		for(int i = 0; i < busStops.length; i++) {
			ptr2.setLocation(busStops[i]);
			TNode t = getNodeHead(trainZero, busStops[i]);
			if(t != null) {
				t.setDown(ptr2);
			}
			if(i != busStops.length - 1) {
				ptr2.setNext(new TNode());
				ptr2 = ptr2.getNext();

			}
		}
		trainZero.setDown(busZero);

		//walking stations
		for(int i = 0; i < locations.length; i++) {
			ptr3.setLocation(locations[i]);
			TNode t2 = getNodeHead(busZero, locations[i]);
			if(t2 != null) {
				t2.setDown(ptr3);
			}
			if(i != locations.length - 1) {
				ptr3.setNext(new TNode());
				ptr3 = ptr3.getNext();
			}
		}
		busZero.setDown(walkZero);

	}
	
	/**
	 * Modifies the layered list to remove the given train station but NOT its associated
	 * bus stop or walking location. Do nothing if the train station doesn't exist
	 * 
	 * @param station The location of the train station to remove
	 */
	public void removeTrainStation(int station) {
		boolean remove = true;
	    TNode curr = trainZero;
		TNode prev = null;

		while(curr != null && curr.getLocation() != station) {
			prev = curr;
			if(curr.getNext() == null && curr.getLocation() != station) {
				remove = false;
			}
			curr = curr.getNext();
		}

		if(remove) {
			if(curr == trainZero) {
				trainZero = trainZero.getNext();
			}
			else {
				prev.setNext(curr.getNext());
			}
		}
	}

	/**
	 * Modifies the layered list to add a new bus stop at the specified location. Do nothing
	 * if there is no corresponding walking location.
	 * 
	 * @param busStop The location of the bus stop to add
	 */
	public void addBusStop(int busStop) {
	
		TNode busLayer = trainZero.getDown();
		TNode curr = busLayer;
		TNode prev = null;

		TNode walkLayer = trainZero.getDown().getDown();
		TNode temp = walkLayer;
		TNode walkLocate = new TNode();

		boolean check = true;
		TNode currMainWalk = walkLayer;
		TNode currMain = busLayer;
		TNode boundCheck = null;

		while(currMainWalk != null) {
			if(currMainWalk.getNext() == null) {
				boundCheck = currMainWalk;
			}
			currMainWalk = currMainWalk.getNext();
		}	

		if(busStop > boundCheck.getLocation()) {
			check = false;
		}	

		while(currMain != null) {
			if(currMain.getLocation() == busStop || currMain.getDown() == null) {
				check = false;
			}
			currMain = currMain.getNext();
		}

		while(curr != null && curr.getLocation() < busStop) {
			prev = curr;
			curr = curr.getNext();

		}

		while(temp != null) {
			if(temp.getLocation() == busStop) {
				walkLocate = temp;
				break;
			}
			temp = temp.getNext();
		}

		if(check) {
			if(prev == null && prev.getNext() == null) {
				TNode test = new TNode();
				test.setLocation(busStop);
				test.setNext(curr);
				test.setDown(walkLocate);
				prev.setNext(test);
			}
			else {
				TNode test2 = new TNode();
				test2.setLocation(busStop);
				test2.setNext(curr);
				test2.setDown(walkLocate);
				prev.setNext(test2);
			}
		}
	
	}
	
	/**
	 * Determines the optimal path to get to a given destination in the walking layer, and 
	 * collects all the nodes which are visited in this path into an arraylist. 
	 * 
	 * @param destination An int representing the destination
	 * @return
	 */
	public ArrayList<TNode> bestPath(int destination) {

		int walkCounter = 0;

	    ArrayList<TNode> list = new ArrayList<>();
		TNode trainLayer = trainZero;
		TNode busLayer = null;
		TNode walkLayer = null;
		TNode walkTestLayer = trainZero.getDown().getDown();

		TNode currMain = walkTestLayer;
		TNode currT = trainLayer;
		TNode currB = busLayer;
		TNode currW = walkLayer;
		
		while(currMain != null) {
			currMain = currMain.getNext();
			walkCounter++;
		}

		if(destination > walkCounter) {
			destination = walkCounter;
		}
		

		while(currT != null && currT.getLocation() <= destination) {
			list.add(currT);
			currB = currT.getDown();
			currT = currT.getNext();
		}
		while(currB != null && currB.getLocation() <= destination) {
			list.add(currB);
			currW = currB.getDown();
			currB = currB.getNext();
		}
		while(currW != null && currW.getLocation() <= destination) {
			list.add(currW);
			currW = currW.getNext();
		}

	    return list;
	}

	/**
	 * Returns a deep copy of the given layered list, which contains exactly the same
	 * locations and connections, but every node is a NEW node.
	 * 
	 * @return A reference to the train zero node of a deep copy
	 */
	public TNode duplicate() {

		TNode newTrainLayer = new TNode();
		newTrainLayer = trainZero;

		TNode newBusLayer = new TNode();
		newBusLayer = trainZero.getDown();

		TNode newWalkLayer = new TNode();
		newWalkLayer = newBusLayer.getDown();

		TNode newTrainZero = new TNode(0);
		TNode newBusZero = new TNode(0);
		TNode newWalkZero = new TNode(0);


		TNode curr = newTrainLayer.getNext();
		TNode ptrD = new TNode();
		newTrainZero.setNext(ptrD);
		while(curr != null) {
			ptrD.setLocation(curr.getLocation());
			if(curr.getNext() != null) {
				ptrD.setNext(new TNode());
				ptrD = ptrD.getNext();
			}
			curr = curr.getNext();
		}

		TNode curr2 = newBusLayer.getNext();
		TNode ptr2D = new TNode();
		newBusZero.setNext(ptr2D);
		while(curr2 != null) {
			ptr2D.setLocation(curr2.getLocation());
			TNode temp = getNodeHead(newTrainZero, curr2.getLocation());
			if(temp != null) {
				temp.setDown(ptr2D);
			}
			if(curr2.getNext() != null) {
				ptr2D.setNext(new TNode());
				ptr2D = ptr2D.getNext();
			}
			curr2 = curr2.getNext();
		}
		newTrainZero.setDown(newBusZero);


		TNode curr3 = newWalkLayer.getNext();
		TNode ptr3D = new TNode();
		newWalkZero.setNext(ptr3D);
		while(curr3 != null) {
			ptr3D.setLocation(curr3.getLocation());
			TNode temp = getNodeHead(newBusZero, curr3.getLocation());
			if(temp != null) {
				temp.setDown(ptr3D);
			}
			if(curr3.getNext() != null) {
				ptr3D.setNext(new TNode());
				ptr3D = ptr3D.getNext();
			}
			curr3 = curr3.getNext();
		}
		newBusZero.setDown(newWalkZero);

	    return newTrainZero;
	}

	/**
	 * Modifies the given layered list to add a scooter layer in between the bus and
	 * walking layer.
	 * 
	 * @param scooterStops An int array representing where the scooter stops are located
	 */
	public void addScooter(int[] scooterStops) {

		TNode newBusLayer = trainZero.getDown();

		TNode newWalkLayer = newBusLayer.getDown();

		TNode scooterZero = new TNode(0);
		TNode ptrS = new TNode();
		scooterZero.setNext(ptrS);


		//scooter stations
		for(int i = 0; i < scooterStops.length; i++) {
			ptrS.setLocation(scooterStops[i]);
			TNode t = getNodeHead(newBusLayer, scooterStops[i]);
			if(t != null) {
				t.setDown(ptrS);
			}
			if(i != scooterStops.length - 1) {
				ptrS.setNext(new TNode());
				ptrS = ptrS.getNext();
			}
		}
		newBusLayer.setDown(scooterZero);

		//walking stations
		TNode curr3 = newWalkLayer;
		while(curr3 != null) {
			TNode temp = getNodeHead(scooterZero, curr3.getLocation());
			if(temp != null) {
				temp.setDown(curr3);
			}
			curr3 = curr3.getNext();
		}
		scooterZero.setDown(newWalkLayer);

	}

	/**
	 * Used by the driver to display the layered linked list. 
	 * DO NOT edit.
	 */
	public void printList() {
		// Traverse the starts of the layers, then the layers within
		for (TNode vertPtr = trainZero; vertPtr != null; vertPtr = vertPtr.getDown()) {
			for (TNode horizPtr = vertPtr; horizPtr != null; horizPtr = horizPtr.getNext()) {
				// Output the location, then prepare for the arrow to the next
				StdOut.print(horizPtr.getLocation());
				if (horizPtr.getNext() == null) break;
				
				// Spacing is determined by the numbers in the walking layer
				for (int i = horizPtr.getLocation()+1; i < horizPtr.getNext().getLocation(); i++) {
					StdOut.print("--");
					int numLen = String.valueOf(i).length();
					for (int j = 0; j < numLen; j++) StdOut.print("-");
				}
				StdOut.print("->");
			}

			// Prepare for vertical lines
			if (vertPtr.getDown() == null) break;
			StdOut.println();
			
			TNode downPtr = vertPtr.getDown();
			// Reset horizPtr, and output a | under each number
			for (TNode horizPtr = vertPtr; horizPtr != null; horizPtr = horizPtr.getNext()) {
				while (downPtr.getLocation() < horizPtr.getLocation()) downPtr = downPtr.getNext();
				if (downPtr.getLocation() == horizPtr.getLocation() && horizPtr.getDown() == downPtr) StdOut.print("|");
				else StdOut.print(" ");
				int numLen = String.valueOf(horizPtr.getLocation()).length();
				for (int j = 0; j < numLen-1; j++) StdOut.print(" ");
				
				if (horizPtr.getNext() == null) break;
				
				for (int i = horizPtr.getLocation()+1; i <= horizPtr.getNext().getLocation(); i++) {
					StdOut.print("  ");

					if (i != horizPtr.getNext().getLocation()) {
						numLen = String.valueOf(i).length();
						for (int j = 0; j < numLen; j++) StdOut.print(" ");
					}
				}
			}
			StdOut.println();
		}
		StdOut.println();
	}
	
	/**
	 * Used by the driver to display best path. 
	 * DO NOT edit.
	 */
	public void printBestPath(int destination) {
		ArrayList<TNode> path = bestPath(destination);
		for (TNode vertPtr = trainZero; vertPtr != null; vertPtr = vertPtr.getDown()) {
			for (TNode horizPtr = vertPtr; horizPtr != null; horizPtr = horizPtr.getNext()) {
				// ONLY print the number if this node is in the path, otherwise spaces
				if (path.contains(horizPtr)) StdOut.print(horizPtr.getLocation());
				else {
					int numLen = String.valueOf(horizPtr.getLocation()).length();
					for (int i = 0; i < numLen; i++) StdOut.print(" ");
				}
				if (horizPtr.getNext() == null) break;
				
				// ONLY print the edge if both ends are in the path, otherwise spaces
				String separator = (path.contains(horizPtr) && path.contains(horizPtr.getNext())) ? ">" : " ";
				for (int i = horizPtr.getLocation()+1; i < horizPtr.getNext().getLocation(); i++) {
					StdOut.print(separator + separator);
					
					int numLen = String.valueOf(i).length();
					for (int j = 0; j < numLen; j++) StdOut.print(separator);
				}

				StdOut.print(separator + separator);
			}
			
			if (vertPtr.getDown() == null) break;
			StdOut.println();

			for (TNode horizPtr = vertPtr; horizPtr != null; horizPtr = horizPtr.getNext()) {
				// ONLY print the vertical edge if both ends are in the path, otherwise space
				StdOut.print((path.contains(horizPtr) && path.contains(horizPtr.getDown())) ? "V" : " ");
				int numLen = String.valueOf(horizPtr.getLocation()).length();
				for (int j = 0; j < numLen-1; j++) StdOut.print(" ");
				
				if (horizPtr.getNext() == null) break;
				
				for (int i = horizPtr.getLocation()+1; i <= horizPtr.getNext().getLocation(); i++) {
					StdOut.print("  ");

					if (i != horizPtr.getNext().getLocation()) {
						numLen = String.valueOf(i).length();
						for (int j = 0; j < numLen; j++) StdOut.print(" ");
					}
				}
			}
			StdOut.println();
		}
		StdOut.println();
	}
}
