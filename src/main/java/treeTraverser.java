import java.util.Map;

/**
 * Created by YFateen on 4/16/17.
 */
public class treeTraverser {

    private double userLRLON;
    private double userULLON;
    private double userULLAT;
    private double userLRLAT;
    private double userWIDTH;
    private double queryLONDPP;

    treeTraverser(Map<String, Double> params) {
        this.userLRLON = params.get("lrlon");
        this.userULLON = params.get("ullon");
        this.userULLAT = params.get("ullat");
        this.userLRLAT = params.get("lrlat");
        this.userWIDTH = params.get("w");
        this.queryLONDPP = (userLRLON - userULLON) / userWIDTH;
    }

    public boolean intersectsTiles2(double nullat, double nullon, double nlrlat, double nlrlon) {
        if (userLRLAT > nullat || userULLAT < nlrlat) {
            return false;
        }
        if (userLRLON < nullon || userULLON > nlrlon) {
            return false;
        }
        return true;
    }

    public boolean lonDPPsmallerThanOrIsLeaf(double input, int depth) {
        if (depth == 7) {
            return true;
        }
        if (input <= queryLONDPP) {
            return true;
        }
        return false;
    }
}
