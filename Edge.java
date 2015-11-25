//Karina Banda
//172 Project 4
//TA: Jacob Margolis
//Edge Class

public class Edge implements  Comparable<Edge>{
String edgeId;
Point point1,point2;
double distance; //this will be the edge's calculated weight

	public Edge(String name,Point p1,Point p2){
		edgeId=name;
		point1=p1;
		point2=p2;
		calculateWeight();
		
		
	}
	
	
	public void calculateWeight(){//calculate the distance between two points using the Haversine formula for calculating distance between lat and long points, as referenced from:http://andrew.hedges.name/experiments/haversine/
		double difflat = (point2.latitude - point1.latitude)*Math.PI/ 180; //difference between the latitude in radians
		double difflon = (point2.longitude - point1.longitude)*Math.PI/ 180; //difference between the longitude in radians
		
		
		double a = Math.pow(Math.sin(difflat/2),2) + Math.cos(point1.latitude*Math.PI/ 180) * Math.cos(point2.latitude*Math.PI/ 180) * Math.pow(Math.sin(difflon/2),2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
		distance = 3961 * c; //multiply by 3961 to get the distance in miles

	
	}
	
	@Override
	public int compareTo(Edge o) {//need to add a way for it to compare the Edges to be able to add it in a edges TreeMap and have it be ordered by edges distance
		return new Double(this.distance).compareTo(new Double(o.distance));
			

	}
	


}
