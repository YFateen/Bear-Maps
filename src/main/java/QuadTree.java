/**
 * Created by YFateen on 4/11/17.
 */

import java.util.Map;

public class QuadTree {

    public static final QuadTree finalTree = initializeTree();
    private int depthOfTree;
    private Node myNode;
    private QuadTree child1 = null;
    private QuadTree child2 = null;
    private QuadTree child3 = null;
    private QuadTree child4 = null;
    private QuadTree parent;

    QuadTree(QuadTree parent, String name, double rULLAT, double rULLON, double rLRLAT, double rLRLON, int depth) {
        this.myNode = new Node(name, rULLAT, rULLON, rLRLAT, rLRLON);
        this.parent = parent;
        this.depthOfTree = depth;
    }

    public static QuadTree getFinalTree() {
        return finalTree;
    }

    public static QuadTree initializeTree() {
        QuadTree peak = new QuadTree(null, "root", 37.892195547244356, -122.2998046875
                , 37.82280243352756, -122.2119140625, 0);
        peak.initializeSubQuads();
        peak.child1.myNode.setFileName("1");
        peak.child2.myNode.setFileName("2");
        peak.child3.myNode.setFileName("3");
        peak.child4.myNode.setFileName("4");
        buildTree(peak);
        return peak;

    }

    public static void buildTree(QuadTree input) {
        if (input.myNode.getFileName().length() == 6) {
            return;
        } else {
            input.child1.initializeSubQuads();
            input.child2.initializeSubQuads();
            input.child3.initializeSubQuads();
            input.child4.initializeSubQuads();
            buildTree(input.child1);
            buildTree(input.child2);
            buildTree(input.child3);
            buildTree(input.child4);
        }
    }

    public Map<String, Double> getNodeData() {
        return this.myNode.getParams();
    }

    public double getULLON() {
        return this.myNode.getULLON();
    }

    public double getULLAT() {
        return this.myNode.getULLAT();
    }

    public double getLRLON() {
        return this.myNode.getLRLON();
    }

    public double getLRLAT() {
        return this.myNode.getLRLAT();
    }

    public double getLONDPP() {
        return this.myNode.getLONDPP();
    }

    public int getDepthOfTree() {
        return this.depthOfTree;
    }

    public QuadTree getChild1() {
        return this.child1;
    }

    public QuadTree getChild2() {
        return this.child2;
    }

    public QuadTree getChild3() {
        return this.child3;
    }

    public QuadTree getChild4() {
        return this.child4;
    }

    public String getFileName() {
        return this.myNode.getFileName();
    }

    public void initializeQuadOne() {
        double upperLeftLong = myNode.getULLON();
        double upperLeftLat = myNode.getULLAT();
        double lowerRightLon = (myNode.getLRLON() + myNode.getULLON()) / 2D;
        double lowerRightLat = (myNode.getLRLAT() + myNode.getULLAT()) / 2D;

        String fileName = myNode.getFileName() + "1";
        int depth = 1 + this.depthOfTree;
        this.child1 = new QuadTree
                (this, fileName, upperLeftLat, upperLeftLong, lowerRightLat, lowerRightLon, depth);
    }

    public void initializeQuadTwo() {
        double upperLeftLong = child1.myNode.getLRLON();
        double upperLeftLat = myNode.getULLAT();
        double lowerRightLon = myNode.getLRLON();
        double lowerRightLat = child1.myNode.getLRLAT();

        String fileName = myNode.getFileName() + "2";
        int depth = 1 + this.depthOfTree;

        this.child2 = new QuadTree
                (this, fileName, upperLeftLat, upperLeftLong, lowerRightLat, lowerRightLon, depth);
    }

    public void initializeQuadThree() {
        double upperLeftLong = myNode.getULLON();
        double upperLeftLat = child1.myNode.getLRLAT();
        double lowerRightLon = child1.myNode.getLRLON();
        double lowerRightLat = (myNode.getLRLAT());

        String fileName = myNode.getFileName() + "3";
        int depth = 1 + this.depthOfTree;
        this.child3 = new QuadTree
                (this, fileName, upperLeftLat, upperLeftLong, lowerRightLat, lowerRightLon, depth);
    }

    public void initializeQuadFour() {
        double lowerRightLon = myNode.getLRLON();
        double lowerRightLat = myNode.getLRLAT();
        double upperLeftLong = child1.myNode.getLRLON();
        double upperLeftLat = child1.myNode.getLRLAT();

        String fileName = myNode.getFileName() + "4";
        int depth = 1 + this.depthOfTree;

        this.child4 = new QuadTree
                (this, fileName, upperLeftLat, upperLeftLong, lowerRightLat, lowerRightLon, depth);
    }

    public void initializeSubQuads() {
        initializeQuadOne();
        initializeQuadTwo();
        initializeQuadThree();
        initializeQuadFour();
    }

//    public static void main(String[] args) {
////            QuadTree fuckMe = new QuadTree("1", 37.892195547244356, -122.2998046875
////                    ,37.82280243352756, -122.2119140625   );
////            QuadTree testNode1 = fuckMe.initializeQuadOne();
////            QuadTree testNode2 = fuckMe.initializeQuadTwo();
////            QuadTree testNode3 = fuckMe.initializeQuadThree();
////            QuadTree testNode4 = fuckMe.initializeQuadFour();
//////
////            System.out.println(testNode1.fileName);
////            System.out.println(testNode2.fileName);
////            System.out.println(testNode3.fileName);
////            System.out.println(testNode4.fileName);
////            System.out.println("_ULLON is " + testNode._ULLON)
////            System.out.println("_ULLAT is " + testNode._ULLAT);
////            System.out.println("_LRLON is " + testNode._LRLON);
////            System.out.println("_LRLAT is " + testNode._LRLAT);
//
//        QuadTree pleaseLawd = QuadTree.initializeTree();
//
//        QuadTree slimShady = pleaseLawd.child4.child4.child4.child4.child4.child4.child4;
//        System.out.println("Huh my name is wuh? My name is huh? My name is " +
//                slimShady.myNode.fileName);
//        System.out.println("_ULLON is " + slimShady.myNode._ULLON);
//        System.out.println("_ULLAT is " + slimShady.myNode._ULLAT);
//        System.out.println("_LRLON is " + slimShady.myNode._LRLON);
//        System.out.println("_LRLAT is " + slimShady.myNode._LRLAT);
//
//        System.out.println("Depth of tree is: " + slimShady.depthOfTree);
//        System.out.println("Parent filename is: " + slimShady.parent.myNode.fileName);
//
//
//    }
}
