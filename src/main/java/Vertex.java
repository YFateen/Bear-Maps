import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by YFateen on 4/19/17.
 */
public class Vertex {
    private LinkedList<Long> nodesVisited = new LinkedList<>();
    private ArrayList<Long> adjacentVertices;

    private double priority = 1000.0;
    private double distanceTraveled = 0.0;

    private long vertexID;
    private double longitude;
    private double lattitude;

    private boolean toShare = false;
    private boolean haveVisited = false;

    private Vertex parentVertex = null;

    public Vertex(long id, double lon, double lat) {
        this.vertexID = id;
        this.lattitude = lat;
        this.longitude = lon;
        adjacentVertices = new ArrayList<>();
    }

    public long getVertexID() {
        return this.vertexID;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public double getLattitude() {
        return this.lattitude;
    }

    public ArrayList<Long> getAdjacentVertices() {
        return this.adjacentVertices;
    }

    public void addNeighbor(long v) {
        this.adjacentVertices.add(v);
    }

    public double getPriority() {
        return this.priority;
    }

    public void setPriority(double input) {
        this.priority = input;
    }

    public void setShareBoolean() {
        this.toShare = true;
    }

    public boolean getShareBoolean() {
        return this.toShare;
    }

    public void addVisitedNode(Long l) {
        nodesVisited.addLast(l);
    }

    public boolean visitedBefore() {
        return this.haveVisited;
    }

    public void visitingNow() {
        this.haveVisited = true;
    }

    public double getDistanceTraveled() {
        return this.distanceTraveled;
    }

    public void setDistanceTraveled(double d) {
        this.distanceTraveled = d;
    }

    public void addDistance(double d) {
        this.distanceTraveled += d;
    }

    public Vertex getParentVertex() {
        return this.parentVertex;
    }

    public void setParentVertex(Vertex v) {
        this.parentVertex = v;
    }

    public LinkedList<Long> finalReturn(Vertex v) {
        LinkedList<Long> toReturn = new LinkedList<>();
        while (v.parentVertex != null) {
            toReturn.addFirst(v.getVertexID());
            v = v.parentVertex;
        }
        return toReturn;
    }
}
