import java.util.Comparator;

/**
 * Created by YFateen on 4/16/17.
 */
public class QuadtreeCompararator implements Comparator<QuadTree> {

    @Override
    public int compare(QuadTree o1, QuadTree o2) {
        if (o1.getULLAT() == o2.getULLAT()) {
            if (o1.getULLON() < o2.getULLON()) {
                return -1;
            }
            if (o1.getULLON() > o2.getULLON()) {
                return 1;
            }
        }
        if (o1.getULLAT() < o2.getULLAT()) {
            return 1;
        } else {
            return -1;
        }
    }
}
