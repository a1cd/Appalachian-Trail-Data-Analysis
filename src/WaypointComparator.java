import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Comparator;

/**
 * {@code WaypointComparator} class, for comparing two waypoints with a set of options
 * @author 24wilber
 * @version 4/29/2022
 */
public class WaypointComparator implements Comparator<Waypoint> {

    /**
     * if true, sorted ascending else descending.
     */
    public boolean ascending;

    /**
     * constructor for waypoints
     * @param sortElementID sort element
     * @param ascending if true, sorted ascending else descending.
     */
    public WaypointComparator(int sortElementID, boolean ascending) {
        this.ascending = ascending;
        SortElementID = sortElementID;
    }

    /**
     * sort element's id
     */
    public int SortElementID;
    /**
     * ignore
     */
    private static final int[] map = new int[]{0, 6,7,8};

    /**
     * compare two waypoints
     *
     * @param o1 the first waypoint to be compared.
     * @param o2 the second waypoint to be compared.
     *
     * @return comparison integer
     */
    @Override
    public int compare(Waypoint o1, Waypoint o2) {
        try {
            var method = Waypoint.class.getDeclaredMethods()[map[SortElementID]];
            boolean isComparable = false;
            var interfaces = method.getReturnType().getInterfaces();
            for (int i = 0; i < interfaces.length && !isComparable; i++) {
                var inter = interfaces[i];
                if (inter.equals(Comparable.class))
                    isComparable=true;
                else continue;
            }
            Comparable c1 = (Comparable) method.invoke(o1);
            Comparable c2 = (Comparable) method.invoke(o2);
            return ((this.ascending)?1:-1) * c1.compareTo(c2);
        } catch (Exception e) {
            System.err.println(e);
        }
        return -1;
    }
}
