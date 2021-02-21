/**
 * A subset of IntSet which stores at least 2 numbers.
 *
 * @author Mark Browne
 * @version 1
 */
public class TreeSet implements IntSet
{
    private final IntSet left, right;
    
    /**
     * Constructor for the TreeSet class.
     * Add two IntSets to the TreeSet.
     * @param x First IntSet to be added to the TreeSet.
     * @param y Second IntSet to be added to the TreeSet.
     */
    public TreeSet(IntSet x, IntSet y)
    {
        left = x;
        right = y;
    }
    
    /**
     * Adds a number to the IntSet.
     * Returns a new TreeSet object that contains all numbers
     * of this IntSet, plus the number.
     * If the number is already in the IntSet, return the
     * IntSet.
     * @param x - Number to be added to the IntSet.
     * @return IntSet object with x in it.
     */
    public IntSet add(int x)
    {
        if (!contains(x)) {
            if ((x & 1) == 0) return new TreeSet(left.add(x/2), right);
            else return new TreeSet(left, right.add(x/2));
        }
        return this;
    }
    
    /**
     * Checks whether a number is in the IntSet or not.
     * @param x - Number to be checked.
     * @return True if the number is in the IntSet, otherwise False.
     */
    public boolean contains(int x)
    {
        if ((x & 1) == 0) return left.contains(x/2);
        else return right.contains(x/2);
    }
        
    /**
     * Computes the union of this IntSet and the other IntSet.
     * @param other - The other IntSet.
     * @return The union of this IntSet and the other IntSet.
     */
    public IntSet union(IntSet other)
    {
        if (other instanceof TreeSet) {
            TreeSet newOther = (TreeSet) other;
            return new TreeSet(left.union(newOther.left), 
                               right.union(newOther.right));
        }
        else return other.union(this);
    }
    
    
    /**
     * Returns the string representation of this IntSet.
     * @param path - String which records the path it took
     * in the Tree Set.
     * @return The string representation of this TreeSet.
     */
    public String toString(String path)
    {
        // l means it took the left branch.
        // r means it took the right branch.
        String leftStr = left.toString(path + "l");
        String rightStr = right.toString(path + "r");
        
        if (leftStr.isEmpty()) return rightStr;
        if (rightStr.isEmpty()) return leftStr;
        
        return leftStr + "," + rightStr;
    }              
}
