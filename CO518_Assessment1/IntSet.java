/**
 * Interface which implements sets, which are a collection
 * of distinct elements.
 *
 * @author Mark Browne
 * @version 1
 */
public interface IntSet
{
    /**
     * Adds the number to the IntSet.
     * Returns an IntSet object that contains all numbers
     * of this IntSet, plus the number.
     * @param x - Number to be added.
     * @return IntSet object with x in it.
     */
    IntSet add(int x);
    
    /**
     * Checks whether the number is in the IntSet or not.
     * @param x - Number to be checked.
     * @return True if the number is in the IntSet, otherwise False.
     */
    boolean contains(int x);
    
    /**
     * Computes the union of this IntSet and the other IntSet.
     * @param other - The other IntSet to compute with.
     * @return The union of this IntSet and the other IntSet.
     */
    IntSet union(IntSet other);
    
    /**
     * @param path String which is used to traverse the tree.
     * @return String representation of the IntSet.
     */
    String toString(String path);
    
    default String makeString()
    {
        return "{" + toString("") + "}";
    }
}
