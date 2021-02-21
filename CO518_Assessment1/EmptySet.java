/**
 * A subset of IntSet which is empty.
 * It stores no numbers.
 *
 * @author Mark Browne
 * @version 1
 */
public class EmptySet implements IntSet
{
    private static EmptySet onlyInstance = null;    // Singleton class

    /**
     * Private constructor for the EmptySet class
     */
    private EmptySet()
    {
    }

    /**
     * @return Single instance of EmptySet class
     */
    public static IntSet empty()
    {
        if (onlyInstance == null) onlyInstance = new EmptySet();
        return onlyInstance;
    }

    /**
     * Adds the number to the empty set.
     * @param x - Number to be added.
     * @return Singleton set with the number.
     */
    public IntSet add(int x)
    {
        return SingletonSet.singleton(x);
    }

    /**
     * Checks whether the number is in the empty set.
     * @param x - Number to be checked.
     * @return False, as empty set never contains any numbers.
     */
    public boolean contains(int x)
    {
        return false;
    }

    /**
     * Joins this set with another IntSet.
     * @param other - The other IntSet to compute with.
     * @return Union of the two IntSets
     */
    public IntSet union(IntSet other)
    {
        return other;
    }

    /**
     * @param path String which is used to traverse the tree.
     * @return String representation of the set.
     */
    public String toString(String path)
    {
        return "";
    }
}
