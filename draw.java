//Karina Banda
//172 Project 4
//TA: Jacob Margolis
//Draw Class

import java.awt.geom.Line2D;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Map;
import java.util.Stack;

import javax.swing.JPanel;


public class draw extends JPanel {
Graphics2D y; //need 2D because I will be using doubles and not integers
static Map<Edge,Edge> alledges;
static String filename; //scaling will change with each different file
static int scale=0;
static int padding=10;
static double miny;
static double minx;
Stack<Point> directions=null;

	public draw(Map<Edge, Edge> edges, String file) {
		directions=new Stack<Point>();
		if(file.compareTo("ur_campus.txt")==0){//change the scale according to which file it is
			scale=20000;
			minx=37.53106878;
			miny=171.1686316;
		}
		else if(file.compareTo("monroe.txt")==0){//change the scale according to which file it is
			scale=270;
			padding=200;
			minx=37.03106878;
			miny=172.0686316;
		}
		
		else if(file.compareTo("nys.txt")==0){//change the scale according to which file it is
			scale=28;
			padding=200;
			minx=35.03106878;
			miny=174.0686316;
		}
		
		else{
			System.out.println("Sorry, I can't handle that");
		}
		
		alledges=edges;
		filename=file;
	}



	@SuppressWarnings("unchecked")
	public draw(Map<Edge, Edge> edges, String file, Stack<Point> inorder) {
		directions=new Stack<Point>();
		directions=(Stack<Point>) inorder.clone();

		if(file.compareTo("ur_campus.txt")==0){//change the scale according to which file it is
			scale=20000;
			minx=37.53106878;
			miny=171.1686316;
		}
		else if(file.compareTo("monroe.txt")==0){//change the scale according to which file it is
			scale=270;
			padding=200;
			minx=37.03106878;
			miny=172.0686316;
		}
		
		else if(file.compareTo("nys.txt")==0){//change the scale according to which file it is
			scale=28;
			padding=200;
			minx=35.03106878;
			miny=174.0686316;
		}
		
		else{
			System.out.println("Sorry, I can't handle that");
		}
		
		alledges=edges;
		filename=file;
		
		
	}



	public void paintComponent(Graphics x){
		y=(Graphics2D) x;
		
		this.setBackground(Color.WHITE);//setting the background as white
		
	
		for(java.util.Map.Entry<Edge, Edge> entry : alledges.entrySet()){
		    Edge current=entry.getValue();
		    add(current.point1.latitude,current.point1.longitude,current.point2.latitude,current.point2.longitude,Color.BLACK);//draw every edge
		}
		
		
		if(directions.isEmpty()==false){//if the direction needs to be mapped too
			
			while(!directions.isEmpty()){
				Point p1=directions.pop();
				if(!directions.isEmpty()){
				Point p2=directions.peek();
				y.setStroke(new BasicStroke(10));
				add(p1.latitude,p1.longitude,p2.latitude,p2.longitude,Color.RED);
				
				}
			}
			
		}
		
		
		
	}
	
	
	
		public void add(double x1, double y1, double x2, double y2, Color colour){

		    double latitude = x1 * Math.PI / 180;//convert to radians
		    double longitude = y1 * Math.PI / 180;

		    
		    double realx = getscaledX(latitude,longitude);
		    double realy = getscaledY(latitude,longitude);

		    
		    double latitude2 = x2 * Math.PI / 180;
		    double longitude2 = y2 * Math.PI / 180;
		    double realx2 = getscaledX(latitude2,longitude2);
		    double realy2 = getscaledY(latitude2,longitude2);
		    
		   
			y.setColor(colour);
			
			y.draw(new Line2D.Double(realx, realy, realx2, realy2));
			

		}
		
		
		public double getscaledX(double lat,double lon){//how to convert from long/lat to cartesian coordinates: https://www.physicsforums.com/threads/converting-latitude-longitude-to-cartesian-coords.21892/
		    
			double x11 = 3961 * Math.sin(lat) * Math.cos(lon);
			double y11 = 3961 * Math.sin(lat) * Math.sin(lon);
			double z = 3961 * Math.cos(lat);
			    
			double perspectivex1 = (x11 * 200 / (200 + z)); //perspective projection: http://www.cse.psu.edu/~rtc12/CSE486/lecture12.pdf
			double perspectivex=padding+(perspectivex1-minx)*scale;
			    
			
			return perspectivex;
		}
		
		public double getscaledY(double lat,double lon){//how to convert from long/lat to cartesian coordinates: https://www.physicsforums.com/threads/converting-latitude-longitude-to-cartesian-coords.21892/
		
			double x11 = 3961 * Math.sin(lat) * Math.cos(lon);
			double y11 = 3961 * Math.sin(lat) * Math.sin(lon);
			double z = 3961 * Math.cos(lat);
			    
			//perspective projection: http://www.cse.psu.edu/~rtc12/CSE486/lecture12.pdf
			double perspectivey1 = (y11 * 200 / (200 + z));
			double perspectivey=padding+(perspectivey1+miny)*scale;

			
			return perspectivey;
		}



}
