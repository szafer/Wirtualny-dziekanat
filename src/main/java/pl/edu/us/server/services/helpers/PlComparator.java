package pl.edu.us.server.services.helpers;

import java.util.Comparator;

public class PlComparator<X extends Object> implements Comparator<X> {

    public static final PlComparator<Object> INSTANCE = new PlComparator<Object>();

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public int compare(Object o1, Object o2) {
        if (o1 == null || o2 == null) {
            if (o1 == null && o2 == null) {
                return 0;
            } else {
                return (o1 == null) ? -1 : 1;
            }
        }
        if (o1 instanceof String) {
            return compareStrings(o1.toString(), o2.toString());
        }
        if (o1 instanceof Comparable) {
            return ((Comparable) o1).compareTo(o2);
        }
        return compareStrings(o1.toString(), o2.toString());

    }

    protected int compareStrings(String s1, String s2) {
        return s1.compareToIgnoreCase(s2);
    }

}
