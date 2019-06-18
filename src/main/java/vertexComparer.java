import java.util.Comparator;

/**
 * Created by YFateen on 4/19/17.
 */
public class vertexComparer implements Comparator<Vertex> {
    @Override
    public int compare(Vertex o1, Vertex o2) {
        if (o1.getPriority() < o2.getPriority()) {
            return -1;
        }
        if (o1.getPriority() > o2.getPriority()) {
            return 1;
        } else {
            return 0;
        }
    }
}
