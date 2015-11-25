//Karina Banda
//172 Project 4
//TA: Jacob Margolis
//Graph Class

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;
import java.util.TreeMap;

import javax.swing.JFrame;


public class Graph {
//command line argument information, all set to false initially 
static boolean show=false;
static boolean directions=false;	
String starting,ending;//for directins
boolean median=false;
static String filename; 

	private static TreeMap<Point, ArrayList<Edge>> graph; //I will be implementing an adjacency list through a tree map with the keys being the Vertex, and the value being a list of edges it has. 
	//The list is also of the entire edge instead of just the other Vertex it connects to just so save time on finding the weight of the path since the edge class contains that info
	
	public static TreeMap<Edge,Edge> edges;
	static Point[] entryset;//to keep track of all the individual points in once place for reference
	static ArrayList<Edge> edgeset;
	
	 public Graph(String filename) throws NumberFormatException, IOException { //only filename
		 graph=new TreeMap<Point,ArrayList<Edge>>();//initialize the tree map for the graph
		 edges=new TreeMap<Edge, Edge>(); //initialize the map to the list of edges
		 
		 createFromFile(filename);
	 }
	 


	 public Graph(String filename, String string2) throws NumberFormatException, IOException {//filename and image
		 graph=new TreeMap<Point,ArrayList<Edge>>();//initialize the tree map for the graph
		 edges=new TreeMap<Edge, Edge>(); //initialize the map to the list of edges
		 
		 if(string2.contains("s"))//if it is to show
			 show=true;
		
		 createFromFile(filename);
		 if(string2.contains("m"))//meridianmap
			 MST();
	}



	public Graph(String filename, String direct, String s, String e) throws NumberFormatException, IOException {//filename, print directions
		 graph=new TreeMap<Point,ArrayList<Edge>>();//initialize the tree map for the graph
		 edges=new TreeMap<Edge, Edge>(); //initialize the map to the list of edges
		 
		 createFromFile(filename);
		 Djikstras(s,e);
	}



	public Graph(String filename, String string2, String s, String e,boolean b) throws NumberFormatException, IOException {//filename, print directions, and show them
		directions=true;
		show=true;
		graph=new TreeMap<Point,ArrayList<Edge>>();//initialize the tree map for the graph
		 edges=new TreeMap<Edge, Edge>(); //initialize the map to the list of edges
		 this.filename=filename;
		 createFromFile(filename);
		 Djikstras(s,e);
	}



	public Graph(String filename, String string2, String string3) throws NumberFormatException, IOException {//show & meridian
		graph=new TreeMap<Point,ArrayList<Edge>>();//initialize the tree map for the graph
		 edges=new TreeMap<Edge, Edge>(); //initialize the map to the list of edges
		 		 
		 show=true;
		 createFromFile(filename);

		 MST();
		 
	}



	public static void createFromFile(String filename) throws NumberFormatException, IOException{
		 String line; //need a string variable to keep record of which line we are on while reading the file
		 
			BufferedReader reader= new BufferedReader(new FileReader(filename));
			
			
			while((line=reader.readLine())!=null){//while it has not reached the end of the file
				String[] temp=line.split("\t");//split it by tab spaces
				String IorR=temp[0]; //is it an intersection or a road? 
				
			
				if(IorR.contains("i")){//if the line is of intersection type; as indicated by the i at the beginning of the line
					Point newpoint=new Point(temp[1],Double.parseDouble(temp[2]),Double.parseDouble(temp[3])); //make a point with the information from that line
					//add the point to the graph's map
					
					graph.put(newpoint,new ArrayList<Edge>());//there's still not edges so the list of edges for each vertex is still set to null			
					
				}
				
				else{//if the line is of type road
					if(entryset==null){
						entryset = graph.keySet().toArray(new Point[graph.size()]);//copy all the keys to an array of points to be used in a binary search
					}

					//all of these are undirected maps
					Point a=findPointByName(temp[2]);
					Point b=findPointByName(temp[3]);
					graph.get(a).add(new Edge(temp[1],a,b));//on a, edge is from a to b
					graph.get(b).add(new Edge(temp[1],b,a));//on b, edge goes from b to a
					
					edges.put(new Edge(temp[1],a,b), new Edge(temp[1],a,b));//to keep track of the edges to be used in prims and for the drawing class
					
				}	
			}
			
			edgeset=new ArrayList<Edge>(edges.keySet());//copy all the keys to an arraylist of edges so that its sorted
	
			
			if(show==true && directions==false){//if -show was one of the command line arguments
			//To Draw it out
			JFrame f=new JFrame("Map");//new Jframe
			
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			draw m=new draw(edges,filename);//my drawing class, sending the list of edges and also filename to know the scaling to which to draw at
			
			f.add(m);//adding it to the Jframe
			
			f.setSize(800,800);//setting the size of my JFrame
			
			f.setVisible(true); //visibility is enabled
			}
	 }

	 public static Point findPointByName(String name){//returns the index where the point is stored from the array entryset given the name of the point
		 int lowerbound=0;//starting lowerbound is index 0
		 int upperbound=entryset.length-1; //starting upper bound isthe element in the array
		 
		 while(lowerbound<=upperbound){
			 int comparisonindex=(lowerbound+upperbound)/2;//the mid index for the binary search
			 
			 if(name.compareTo(entryset[comparisonindex].pointId)==0)//if its the same
				 return entryset[comparisonindex];//returns the index where it is located
			 else if(name.compareTo(entryset[comparisonindex].pointId)<0){//if the name is smaller compared to the element 
				 upperbound=comparisonindex-1;//new upperbound
			 }
			 else{//it is larger
				 lowerbound=comparisonindex+1;//new lowerbound
			 }
				 
		 }

		return null;

		 
	 }
 
	 public static void Djikstras(String start, String end){//references the psuedocode from 9.31 of Weiss
		 TreeMap<Point,Point> unknown=new TreeMap<Point,Point>(djikComparator);//the vertices that are being explored are kept in a tree map to know which vertex's distance is the lowest for the next iteration of the algorithm, and <point,point> just for easier access  
		 //Tree is sorted by the distance in djikstra's algorithm that it currently holds
		 
		 Point firstpoint=findPointByName(start);
		 firstpoint.djikDistance=0;//set the starting vertex distance to 0
		 unknown.put(firstpoint,firstpoint);//the first and only Vertex in the sorted Tree is the starting vertex
		
		 Map<Point, Point> known=new HashMap<Point,Point>(); //all known vertices will be stored here <vertex,vertex path>
		 
		 while(unknown.isEmpty()==false){//while there are still vertices to be explored
			 Point first=unknown.pollFirstEntry().getKey();//remove it from the list of vertices to explore
			 known.put(first, first.parent); //insert it into the known vertices
			
			 if (first.pointId.compareTo(end)==0)//the end has been reached at its shortest distance since the end is now known
				 break;
			 
			 for (int i = 0; i<graph.get(first).size(); i++) {//iterate over the arraylist of the vertex's edges
				 Point vertex=graph.get(first).get(i).point2; //the connecting vertex
				
				 if(known.containsKey(vertex)==false){ //the vertex is not known yet
					 if(unknown.containsKey(vertex)==false){//if it has not been included in the unknown vertices yet
						 unknown.put(vertex, vertex);
					 }
					 
					double newdistance=first.djikDistance+graph.get(first).get(i).distance; //current distance plus cost of edge
					
					 if(newdistance<unknown.get(vertex).djikDistance){//if the new distance is smaller than the vertex's current distance
						 Point updated=unknown.get(vertex);
						 
						 updated.djikDistance=newdistance;//update to new distance
						 updated.parent=first; //new parent in the path
						 
						 unknown.remove(vertex);//delete the current instance of the vertex, so u can reinsert it but with the updated distance and it be properly sorted in the TreeMap
						 unknown.put(updated,updated); 
					 }
				 }
				 
			 }	
		}
		 
		 Point endpoint=findPointByName(end);
		 if(known.containsKey(endpoint)){
		 Stack<Point> inorder=new Stack<Point>();
		 inorder.push(endpoint);
		 System.out.print("Total Distance: "+endpoint.djikDistance+" miles. Path: ");
		 while(endpoint!=firstpoint){
			 inorder.push(endpoint.parent);
			 endpoint=endpoint.parent;
		 }
		 
			if(show==true){//if -show was one of the command line arguments

			//To Draw it out
			JFrame f=new JFrame("Map");//new Jframe

			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			draw m=new draw(edges,filename,inorder);//my drawing class, sending the list of edges and also filename to know the scaling to which to draw at
			
			f.add(m);//adding it to the Jframe
			
			f.setSize(800,800);//setting the size of my JFrame
			
			f.setVisible(true); //visibility is enabled
			
			}
		 
		 System.out.print(inorder.pop().pointId+" ");
		 while(inorder.empty()==false){
			 System.out.print(inorder.pop().pointId+" ");
		 }}
		 else{
			 System.out.println(start+" and "+end+" do not have any connected path");
		 }
		 
		 System.out.println();
	 }
	 
	    public static Comparator<Point> djikComparator = new Comparator<Point>(){//Comparator used in Djikstras method to compare the distance of the vertices in Djikstra's algorithm

			@Override
			public int compare(Point o1, Point o2) {
				return Double.compare(o1.djikDistance, o2.djikDistance);
				
			}

	    };
	    

	    
	    public static void MST(){//referred p394 of Weiss, MST, pick edge such as both vertices are not in known
	    	ArrayList<Point> knownpoints=new ArrayList<Point>();
	    	ArrayList<Edge> finaledges=new ArrayList<Edge>();
	    	
			while(!edgeset.isEmpty()){//while there are still edges to be explored
				Edge currentedge=edgeset.get(0); //get the head of the edges list, the head will be the smallest distance edge not looked at yet
				edgeset.remove(0);//remove the head
				
				if(!(knownpoints.contains(currentedge.point1) && knownpoints.contains(currentedge.point2))){//if both vertices of the edge are not known
					finaledges.add(currentedge);//add the current edge to the final list for MST
					knownpoints.add(currentedge.point1);//add both points to the known pile of points
					knownpoints.add(currentedge.point2); //add both points to the known pile of points
				}
				
				else if(!(knownpoints.contains(currentedge.point1))){//if first vertex is the only one not known
					finaledges.add(currentedge);//add the current edge to the final list for MST
					knownpoints.add(currentedge.point1);//add the first point to the known pile of points
				}
				
				else if(!(knownpoints.contains(currentedge.point2))){//if second vertex is the only one not known
					finaledges.add(currentedge);//add the current edge to the final list for MST
					knownpoints.add(currentedge.point2); //add the second point to the known pile of points
				}
				
				if(knownpoints.size()==entryset.length)//all vertices have been accounted for
					break;
			}
			 
			System.out.print("MST total edges: "+finaledges.size()+ " MST Edges: ");
			for(int i=0;i<finaledges.size();i++){
				System.out.print(finaledges.get(i).edgeId+"	");//print out the name of the edge
			}
			
			System.out.println();
			 
		 }
	    
	    
	   
}
