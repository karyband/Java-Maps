Karina Banda
kbanda2@u.rochester.edu
172 Project 4
May/2015

Java Maps- Shortest paths, Dijkstras, and Drawing Maps

=== FILES ===
draw.java -Class that implements the jpanel and draws the map onto the jpanel
Edge.java -edge class, also calculates distance within the class
Graph.java -Graph class, implements the graph object, dijkstras, and calls on the draw if need be
Point.java- point class
project4.java- Main class, runs the program
monroe.txt - Input data for the monroe country
nys.txt - Input data for new york state
ur_campus.txt - Input data for the University of Rochester campus
README.txt
OUTPUT.txt - Example of interactions in the command line
3 screenshots- showing the output of the 3 map drawings (uofr_output_screenshot, monroe_output_screenshot, nys_output_screenshot


=== Details on implementation ===

After reading the command line arguments and seeing how many there are, the 
command line arguments get passed in when constructing the graph object 
as there are several types of constructors for the graph to accommodate which 
my main class (project4 class) takes care of distributing them to the right one. 

In the graph object, after reading in which filename we are looking at to build 
the graph, the filename gets sent to createFromFile() method to populate the 
graph with the information from the text file using a BufferedReader that reads 
from the text file one line at a time until it reaches the end of the file. 
It parses each line by the tab spaces and using the first element of the line 
gives information of whether its a road or an intersection. If it is an 
intersection, it builds the appropriate Point object for the line and inserts 
it into a TreeMap which will work as the adjacency list for the graph. I am 
using a TreeMap because I able to sort it by the ID of the points. The keys are 
the points and the values of the points is an ArrayList of the Edges it is 
included in. After all the lines with intersections are done, I make the set of 
Keys from the TreeMap into an array and store it in a Points[] array that will 
be already sorted since the TreeMap was sorted. Since it is sorted by ID, it 
allows my findPointByName() method to be able to retrieve an instance of a Point 
by the given String ID in logN time using binary search. This comes in handy when 
I only have the string name of a point such as when names of intersections are 
given for directions. 


When the line is an edge, the appropriate Edge object gets created but also within 
the Edge object the distance gets calculated between the vertices of the edge and 
this will serve as the weight of the edge. To calculate the distance I used the 
Haversine formula for calculating distances between latitude and longitude coordinates. 
Within the Haversine formula, I also used 3961miles as the radius of the Earth so 
that the distance is also in miles. Each edge also gets inserted into a HashMap to 
keep track of all the edges. After all the Edges have been inserted, if there was a 
command line argument to show the map, a JFrame is created and the filename and edges 
Map get sent to the Draw class to create the map image. Based on the filename, the 
scaling gets chosen. The edges get drawn through connecting and drawing a line between 
its vertices but to get the points from which to draw from the lat/lon coordinates 
need to get converted into cartesian points. Cartesian points though are still 3D, 
so to translate it into 2D, they get converted into x,y coordinates through the 
perspective projection formula and then these final points get scaled to actually 
show within the window based on which file it is. I had a difficult time with finding 
out how to transform the final points to be able to show on the map, especially with 
the larger ones. In the end, I found what was best for each file type individually 
through trial and error and that is why it is unable to make a map for a file that is 
not one of the given 3 files. The runtime of drawing the map is O(N) where N is the 
the number of edges since it has to draw each one out at a time, therefore where 
there are larger sets, the runtime will drastically get slower. 

If directions need to be given and the map shown, then the drawing is left until after 
the path for the directions is calculated so that this information can get sent 
simultaneously to be drawn. Although it is following the same steps as drawing out
all the edges, for some reason, it does not draw the direction path although I set 
it to draw in a different color. Also I would like to note that it was never specified 
that the maps had to be rotated so that its completely leveled.

In Dijkstras implementation though, I started out by using another TreeMap of Points 
that are unknown but are being explored but this time the map is ordered by the 
djikDistance of the point which keeps track of the points distance as calculated 
by the algorithm. This way I can take the top one easily which will be the smallest 
distance point but also be able to look through the points and update a point if the 
distance has been updated. Also if all edges connected to the starting point and the 
ending point is not to be found in the known Points map listing, then that means that 
there is no path between the two and there for my program will say as such. Since I 
followed the example from Weiss book at 9.30 and didnt have to search for smallest 
vertex, but still have to transverse through all connected edges and the worst case 
scenario is that will go through all, the runtime of this part is O(ElogV) but that 
is proportional to the number of Edges, as the data gets larger the runtime also 
drastically slows. 

I used TreeMaps or Maps where I didn't really have to, like when the keys and 
values are exactly the same, but it proved very convenient to be able call 
map.get(object) and get the instance of that object stored in the object as it may 
have different values for some of the variables. 



UPDATE May 5, 2015:
In this latest submission I was able to include MST. Instead of trying to follow the 
pseudocode from the book as I was struggling to do, I opted to try a different approach. 
The Edges got inserted into the TreeMap instead of a HashMap now and are sorted by its 
distance. Once all the edges are read in, the set of keys get copied into an arraylist 
which means that the arraylist should already be sorted. From here the front of the 
arraylist gets taken out and ifboth of its vertices are known, then it does not get 
added to the final list of edges for the spanning tree. On the other hand if both or 
just one of the vertices are still not known, then it gets added as a knew edge to 
the spanning tree and then the vertices that were unknown before are now known 
and added to the known Points list. Since it could potentially have to run through 
all the edges in the list although it is sorted from least to greatest because there 
could be a vertex that is only connected by the last possible edge which means that the 
running time is O(numEdges). Still, since the edges are sorted, it performed really 
well in the 3 tests with the given data and in less than a minute. Unfortunately since 
the line drawing of the directions list was not working, I could still not add this
spanning tree to show on the map. 






