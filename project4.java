//Karina Banda
//172 Project 4
//TA: Jacob Margolis
//Main Class
import java.io.IOException;

import javax.swing.JFrame;


public class project4 {

	public static void main(String[] args) throws NumberFormatException, IOException {
		// TODO Auto-generated method stub

		String[] a = new String[args.length];//array with the command line arguments
		for (int i = 0; i < args.length; i++) {
		    a[i] = args[i];
		}
		
		if(a.length==1){//only file name was given
		Graph map=new Graph(a[0]);
		}
		
		else if(a.length==2){//only 2 arguments
				Graph map=new Graph(a[0],a[1]);
		}
		
		else if(a.length==3){
			Graph map=new Graph(a[0],a[1],a[2]);
		}
		
		else if(a.length==4){//directions without image
			Graph map=new Graph(a[0],a[1],a[2],a[3]);
	}
		
		else{//directions with image
			Graph map=new Graph(a[0],a[1],a[3],a[4],true);
		}
		

}
}
