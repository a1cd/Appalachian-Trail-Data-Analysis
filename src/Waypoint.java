import java.lang.reflect.Parameter;

/**
 * waypoint class
 * @author 24wilber
 * @version 4/29/2022
 */
public class Waypoint implements Comparable<Waypoint> {

    private String type;
    private String name, state;
    private double toSpringer, toKatahdin;
    private int elevation;

    /**
     * waypoint constructor
     * @param type the type of the Waypoint
     * @param name the name of the Waypoint
     * @param state the state of the Waypoint
     * @param lat the lat of the Waypoint
     * @param lon the lon of the Waypoint
     * @param toSpringer the distance to springer from the Waypoint
     * @param toKatahdin the distance to Katahdin from the Waypoint
     * @param elevation the elevation of the Waypoint
     */
    public Waypoint(String type, String name, String state, double lat, double lon, double toSpringer, double toKatahdin, int elevation) {
        this(type, name, state, toSpringer, toKatahdin,elevation);
//        this.type = type;
//        this.name = name;
//        this.state = state;
//        this.toSpringer = toSpringer;
//        this.toKatahdin = toKatahdin;
//        this.elevation = elevation;
    }
//    /**
//     * waypoint type
//     */
//    public enum WaypointType {
//        FEATURE,
//        SHELTER ,
//        TOWN,
//        HOSTEL,
//        LODGE,
//        HUT;
//    }

    /**
     *
     * waypoint constructor
     * @param type the type of the Waypoint
     * @param name the name of the Waypoint
     * @param state the state of the Waypoint
     * @param toSpringer the distance to springer from the Waypoint
     * @param toKatahdin the distance to Katahdin from the Waypoint
     * @param elevation the elevation of the Waypoint
     */
    public Waypoint(String type, String name, String state, double toSpringer, double toKatahdin, int elevation) {
        this.type = type;
        this.name = name;
        this.state = state;
        this.toSpringer = toSpringer;
        this.toKatahdin = toKatahdin;
        this.elevation = elevation;
    }

    /**
     * type get method
     * @return the type
     */
    public String getType() {
        return type;
    }
    /**
     * state get method
     * @return the state
     */
    public String getName() {
        return name;
    }//1
    /**
     * state get method
     * @return the state
     */
    public String getState() {
        return state;
    }
    /**
     * ToKatahdin get method
     * @return the distance to Springer
     */
    public double getToSpringer() {
        return toSpringer;
    }//2
    /**
     * ToKatahdin get method
     * @return the distance to katahdin
     */
    public double getToKatahdin() {
        return toKatahdin;
    }//3
    /**
     * elevation get method
     * @return the elevation
     */
    public int getElevation() {
        return elevation;
    }//4

    /**
     * toString method
     * @return string representation of the object
     */
    @Override
    public String toString() {
        return "Waypoint{" +
                "type=" + this.getType() +
                ", name='" + getName() + '\'' +
                ", state='" + getState() + '\'' +
                ", toSpringer=" + getToSpringer() +
                ", toKatahdin=" + getToKatahdin() +
                ", elevation=" + getElevation() +
                '}';
    }

    /**
     * compare to method
     * @param o the object to be compared.
     *
     * @return the comparison
     */
    @Override
    public int compareTo(Waypoint o) {
        return -1*this.name.compareTo(o.name);
    }
}
