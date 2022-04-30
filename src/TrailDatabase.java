import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.System.out;

/**
 * @author 24wilber
 * @version 4/29/2022
 * the trail database that acts as the sorting, starting point, and storage for Waypoints.
 */
public class TrailDatabase {
    private List<Waypoint> myPoints;
    private SearchTerm searchTerm;
    private boolean ASC;
    /**
     * an enum for the search terms
     */
    enum SearchTerm {
//        /**
//         * Sort by type
//         */
//        TY,
        /**
         * Sort by name
         */
        NA,
        /**
         * Sort by distance to Springer
         */
        DS,
        /**
         * Sort by distance to Katahdin
         */
        DK,
        /**
         * Sort by elevation
         */
        EL
    }

    /**
     * asks the user for the search term and the sort direction.
     */
    public void getSearchTerm() {
        var A = true;
        var B = true;
        Scanner in = new Scanner(System.in);
        while (A) {
            out.print(
                    """
                            + Menu of search terms to use for sorting waypoints +
                                 NA: by name
                                 DS: by distance to Springer
                                 DK: by distance to Katahdin
                                 EL: by elevation
                            Enter your preferred sort by term or 'Q' to quit:\s"""
            );
            var term = in.nextLine();
            try {
                searchTerm = SearchTerm.valueOf(term.toUpperCase().trim());
            } catch (IllegalArgumentException e) {
                if (term.equals("Q")) throw new RuntimeException();
                continue;
            }
            A=!A;
        }
        while (B) {
            out.print(
                    "Enter 'A' to sort in ascending order or 'D' to sort in descending order: "
            );
            var sortDirection = in.nextLine();

            if (sortDirection.equalsIgnoreCase("a")) {
                ASC = true;
            } else if (sortDirection.equalsIgnoreCase("d")) {
                ASC = false;
            } else continue;
             B=!B;
        }
    }

    /**
     * the main constructor for trail database that populates the database
     */
    public TrailDatabase() {
        this.myPoints = new ArrayList<>();
        populateDatabase();
    }

    /**
     * populates the database with {@code apptrailDB.txt}
     */
    public void populateDatabase() {
        Scanner scanner;
        try {
            scanner = new Scanner(new File("apptrailDB.txt"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        scanner.useDelimiter("\t");
        while (scanner.hasNext()) {
            var line = scanner.nextLine();
            var tabCount = 0;
            for (Character character : line.toCharArray())
                if (character.equals('\t')) tabCount++;
            var constructors = Waypoint.class.getConstructors();
            var constructor = constructors[0];
            for (var constructorValue : constructors) {
                var paramCount = constructorValue.getParameterCount();
                if (paramCount == tabCount) constructor = constructorValue;
            }
            var lineSplit = line.split("\t");
            Object[] args = new Object[constructor.getParameterCount()];
            Parameter[] parameters = constructor.getParameters();
            for (int i = 0; i < parameters.length; i++) {
                Parameter param = parameters[i];
                var type = param.getType();
                if (int.class.equals(type) || Integer.class.equals(type))
                    args[i] = Integer.parseInt(lineSplit[i]);
                else if (double.class.equals(type) || Double.class.equals(type))
                    args[i] = (Double.parseDouble(lineSplit[i]));
                else if (String.class.equals(type))
                    args[i] = (lineSplit[i]);
                else {
                    System.err.println("we have a problem, there was an argument that we cannot handle");
                }
            }
            Waypoint newWaypoint;
            try {
                newWaypoint = (Waypoint) constructor.newInstance(args);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            myPoints.add(newWaypoint);
        }
    }

    /**
     * prints each line in the database
     */
    public void printDB() {
        for (Waypoint w: myPoints) out.println(w);
    }

    /**
     * sorts the list of {@code myPoints}
     * @param waypointComparator the waypont comparator to use to compare objects
     */
    public void sort(WaypointComparator waypointComparator) {
        this.myPoints = sort(this.myPoints, waypointComparator);
    }

    /**
     * Mergesort sorting
     * @param list the list to use to sort
     * @param waypointComparator the comparitor to use to sort
     *
     * @return a sorted list
     */
    public static List<Waypoint> sort(List<Waypoint> list, WaypointComparator waypointComparator) {
        if (list.size()>2) {
            int startSize = list.size() / 2;
            var list1 = sort(list.subList(0, list.size() - startSize), waypointComparator);
            var list2 = sort(list.subList(list.size() - startSize, list.size()), waypointComparator);
            var combinedList = new ArrayList<Waypoint>(list1.size() + list2.size());
            int pos1 = 0, pos2 = 0;
            while (pos1 < list1.size() || pos2 < list2.size()) {
                int compare;
                if (pos1 >= list1.size())
                    compare = 1;
                else if (pos2 >= list2.size())
                    compare = -1;
                else
                    compare = waypointComparator.compare(list1.get(pos1), list2.get(pos2));
                if (compare <= 0) {
                    combinedList.add(list1.get(pos1));
                    pos1++;
                }
                if (compare >= 0) {
                    combinedList.add(list2.get(pos2));
                    pos2++;
                }
            }
            return combinedList;
        } else if (list.size()==2) {
            var combinedList = new ArrayList<Waypoint>(list.size());
            int compare = waypointComparator.compare(list.get(0), list.get(1));
            if (compare > 0) {
                combinedList.add(list.get(0));
            } else if (compare < 0) {
                combinedList.add(list.get(1));
            } else {
                combinedList.add(list.get(0));
                combinedList.add(list.get(1));
            }
            return combinedList;
        }
        return list;
    }

    /**
     * @param args string args
     */
    public static void main(String[] args) {
        TrailDatabase db = new TrailDatabase();
        out.println("*** Welcome to the Appalachian Trail Database ***");
        while (true)
            try {
                db.getSearchTerm();
                var waypointComparator = new WaypointComparator(db.searchTerm.ordinal(), db.ASC);
                db.sort(waypointComparator);
                db.printDB();
            } catch (RuntimeException e) {
                break;
            }
    }
}
