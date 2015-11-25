//Karina Banda
//172 Project 4
//TA: Jacob Margolis
//Point Class


public class Point implements Comparable<Point>{
String pointId;
double latitude, longitude,djikDistance;
boolean known;//if its known or not, to be used for dijkstras algorithm
Point parent;//parent in a bath, to be used for dijkstras algorithm

	public Point(String name,double lat,double lon){
		pointId=name;
		latitude=lat;
		longitude=lon;
		known=false;//always initialize to false
		djikDistance=Double.POSITIVE_INFINITY;//always initialize infinity
	}
	
	
	@Override
	public int compareTo(Point o) {//need to add a way for it to compare the Points to be able to add it in a TreeMap and have it be ordered by Point's ID
		return this.pointId.compareTo(o.pointId);
			

	}

}


