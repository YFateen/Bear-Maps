import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Graph for storing all of the intersection (vertex) and road (edge) information.
 * Uses your GraphBuildingHandler to convert the XML files into a graph. Your
 * code must include the vertices, adjacent, distance, closest, lat, and lon
 * methods. You'll also need to include instance variables and methods for
 * modifying the graph (e.g. addNode and addEdge).
 *
 * @author Alan Yao, Josh Hug
 */
public class GraphDB {
    /**
     * Your instance variables for storing the graph. You should consider
     * creating helper classes, e.g. Node, Edge, etc.
     */
    private Map<Long, Vertex> vertexMap = new HashMap<>();
    private Map<Long, Double> priorityMap = new HashMap<>();
    private Map<Long, Double> distanceMap = new HashMap<>();

    /**
     * Example constructor shows how to create and start an XML parser.
     * You do not need to modify this constructor, but you're welcome to do so.
     *
     * @param dbPath Path to the XML file to be parsed.
     */
    public GraphDB(String dbPath) {
        try {
            File inputFile = new File(dbPath);
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            GraphBuildingHandler gbh = new GraphBuildingHandler(this);
            saxParser.parse(inputFile, gbh);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        clean();
    }

    /**
     * Remove nodes with no connections from the graph.
     * While this does not guarantee that any two nodes in the remaining graph are connected,
     * we can reasonably assume this since typically roads are connected.
     */

    private void clean() {

        for (Iterator<Vertex> i = vertexMap.values().iterator(); i.hasNext(); ) {
            Vertex element = i.next();
            if (!element.getShareBoolean()) {
                i.remove();
            }
        }
    }

    /**
     * Returns an iterable of all vertex IDs in the graph.
     */
    Iterable<Long> vertices() {
        return vertexMap.keySet();
    }

    /**
     * Returns ids of all vertices adjacent to v.
     */
    Iterable<Long> adjacent(long v) {
        return vertexMap.get(v).getAdjacentVertices();
    }

    /**
     * Returns the Euclidean distance between vertices v and w, where Euclidean distance
     * is defined as sqrt( (lonV - lonV)^2 + (latV - latV)^2 ).
     */
    double distance(long v, long w) {
        return Math.sqrt((Math.pow(vertexMap.get(v).getLongitude()
                - vertexMap.get(w).getLongitude(), 2))
                + (Math.pow(vertexMap.get(v).getLattitude()
                - vertexMap.get(w).getLattitude(), 2)));
    }

    /**
     * Returns the vertex id closest to the given longitude and latitude.
     */
    long closest(double lon, double lat) {
        Iterable<Long> aliBabaAndTheFortyKeys = vertices();
        double closestVal = 9001; // Over 9000
        long toReturn = 0;
        double tempDistance;
        for (Long x : aliBabaAndTheFortyKeys) {
            tempDistance = Math.sqrt((Math.pow(vertexMap.get(x).getLongitude() - lon, 2))
                    + (Math.pow(vertexMap.get(x).getLattitude() - lat, 2)));
            if (tempDistance < closestVal) {
                closestVal = tempDistance;
                toReturn = x;
            }
        }
        return toReturn;
    }

    /**
     * Longitude of vertex v.
     */
    double lon(long v) {
        return vertexMap.get(v).getLongitude();
    }

    /**
     * Latitude of vertex v.
     */
    double lat(long v) {
        return vertexMap.get(v).getLattitude();
    }

    /**
     * Adds a vertex v to the following database.
     */
    public void addVertex(Vertex v) {
        vertexMap.put(v.getVertexID(), v);

    }

    /**
     * Connects each vertex in the list in the order they are listed.
     */
    public void connect(ArrayList<Long> theList) {
        if (theList.size() == 0) {
            return;
        }
        int count = theList.size() - 2;
        Long vertex1 = theList.get(theList.size() - 1);
        while (count != -1) {
            Long vertex2 = theList.get(count);
            vertexMap.get(vertex2).addNeighbor(vertex1);
            vertexMap.get(vertex1).addNeighbor(vertex2);
            vertexMap.get(vertex1).setShareBoolean();
            vertexMap.get(vertex2).setShareBoolean();

            vertex1 = vertex2;
            count--;
        }
    }

    /**
     * Returns the shortest path from point A to point B.
     */
    public LinkedList<Long> recursiveQueue(GraphBuildingHandler gbh,
                                           long startingVertexID, long endingVertexID) {
        Comparator vertexComparator = new vertexComparer();
        PriorityQueue<Vertex> theQueue = new PriorityQueue<>(vertexComparator);

        theQueue.add(vertexMap.get(startingVertexID));
        Vertex tempVertex = vertexMap.get(startingVertexID);
        tempVertex.addVisitedNode(tempVertex.getVertexID());

        while (!theQueue.isEmpty()
                && tempVertex.getVertexID() != endingVertexID) {
            tempVertex = theQueue.remove();
            ArrayList<Long> adjacentNodes
                    = vertexMap.get(tempVertex.getVertexID()).getAdjacentVertices();

            for (Long currentVertexID : adjacentNodes) {
                if (!(tempVertex.getParentVertex() == null)) {
                    if (currentVertexID != tempVertex.getParentVertex().getVertexID()) {
                        Vertex currentVertex = vertexMap.get(currentVertexID);
                        double priority = distance(currentVertexID, tempVertex.getVertexID())
                                + distance(currentVertexID, endingVertexID)
                                + tempVertex.getDistanceTraveled();

                        if (!currentVertex.visitedBefore()) {
                            currentVertex.visitingNow();
                            currentVertex.addDistance(tempVertex.getDistanceTraveled()
                                    + distance(tempVertex.getVertexID(), currentVertexID));
                            currentVertex.setPriority(priority);
                            currentVertex.setParentVertex(tempVertex);
                            theQueue.add(currentVertex);
                        } else {
                            if ((currentVertex.getDistanceTraveled()
                                    > (distance(currentVertexID, tempVertex.getVertexID())
                                            + tempVertex.getDistanceTraveled()))) {

                                currentVertex.setPriority(priority);
                                currentVertex.setDistanceTraveled(tempVertex.getDistanceTraveled()
                                        + distance(tempVertex.getVertexID(), currentVertexID));
                                currentVertex.setParentVertex(tempVertex);
                                theQueue.add(currentVertex);
                            }
                        }
                    }
                } else {
                    double priority = distance(currentVertexID, tempVertex.getVertexID())
                            + distance(currentVertexID, endingVertexID)
                            + tempVertex.getDistanceTraveled();
                    vertexMap.get(currentVertexID).addDistance(tempVertex.getDistanceTraveled()
                            + distance(tempVertex.getVertexID(), currentVertexID));
                    vertexMap.get(currentVertexID).setParentVertex(tempVertex);
                    vertexMap.get(currentVertexID).setPriority(priority);
                    theQueue.add(vertexMap.get(currentVertexID));
                }
            }
        }
        LinkedList<Long> toReturn = tempVertex.finalReturn(tempVertex);
        toReturn.addFirst(startingVertexID);
        return toReturn;
    }
}
