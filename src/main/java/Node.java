import java.util.HashMap;
import java.util.Map;

/**
 * Created by YFateen on 4/16/17.
 */
public class Node {
    private String fileName;
    private double _ULLAT;
    private double _ULLON;
    private double _LRLAT;
    private double _LRLON;
    private double _LONDPP;

    Node(String s, double ULLAT, double ULLON, double LRLAT, double LRLON) {
        this.fileName = s;
        this._LRLAT = LRLAT;
        this._ULLON = ULLON;
        this._ULLAT = ULLAT;
        this._LRLON = LRLON;
        this._LONDPP = (_LRLON - _ULLON) / 256;
    }

    public Map<String, Double> getParams() {
        Map<String, Double> toReturn = new HashMap<String, Double>();
        toReturn.put("ULLAT", this._ULLAT);
        toReturn.put("ULLON", this._ULLON);
        toReturn.put("LRLAT", this._LRLAT);
        toReturn.put("LRLON", this._LRLON);
        toReturn.put("LONDPP", this._LONDPP);
        return toReturn;
    }

    public double getULLAT() {
        return this._ULLAT;
    }

    public double getULLON() {
        return this._ULLON;
    }

    public double getLRLAT() {
        return this._LRLAT;
    }

    public double getLRLON() {
        return this._LRLON;
    }

    public double getLONDPP() {
        return this._LONDPP;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String s) {
        this.fileName = s;
    }
}
