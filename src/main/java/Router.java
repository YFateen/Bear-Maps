import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * This class provides a shortestPath method for finding routes between two points
 * on the map. Start by using Dijkstra's, and if your code isn't fast enough for your
 * satisfaction (or the autograder), upgrade your implementation by switching it to A*.
 * Your code will probably not be fast enough to pass the autograder unless you use A*.
 * The difference between A* and Dijkstra's is only a couple of lines of code, and boils
 * down to the priority you use to order your vertices.
 */
public class Router {
    private static GraphBuildingHandler theGraphBuilder;
    PriorityQueue<Vertex> zeQueueYa;

    /**
     * Return a LinkedList of <code>Long</code>s representing the shortest path from st to dest,
     * where the longs are node IDs.
     */
    public static LinkedList<Long> shortestPath(GraphDB g,
        double stlon, double stlat, double destlon, double destlat) {
        GraphBuildingHandler theHandler = new GraphBuildingHandler(g);
        long startLocation = theHandler.getGraph().closest(stlon, stlat);
        long endingLocation
                = theHandler.getGraph().closest(destlon, destlat);
        LinkedList<Long> toReturn
                = theHandler.getGraph().recursiveQueue(theHandler, startLocation, endingLocation);
        return toReturn;
    }

}
