/**
 * A set which only holds one number.
 *
 * @author Mark Browne
 * @version 1
 */
public class SingletonSet implements IntSet
{
    private static final IntSet[] instances = new IntSet[8];    // Sets holding a number (1-7) will be reused
    private final int number;                                   // Number stored in set

    /**
     * Private constructor for the SingletonSet class
     * @param n - Number to be stored in the set
     */
    private SingletonSet(int n)
    {
        number = n;
    }
    
    /**
     * Get an instance of this SingletonSet class.
     */
    public static IntSet singleton(int n)
    {
        SingletonSet s = new SingletonSet(n);
        if (n >= 0 && n <= 7) {
            if (instances[n] == null) instances[n] = s;
            return instances[n];
        }
        else return s;

    }

    /**
     * Adds the number to the set.
     * Transforms the singleton set to a tree set.
     * @param x - Number to be added.
     * @return New created set.
     */
    public IntSet add(int x)
    {
        boolean isThisEven = ((number & 1) == 0);
        boolean isXEven = ((x & 1) == 0);
        
        IntSet emptyObj = EmptySet.empty();
        IntSet singleThis = SingletonSet.singleton(number/2);
        IntSet singleX = SingletonSet.singleton(x/2);
        
        if (x == number) return this;

        if (isThisEven) {
            if (isXEven) return new TreeSet(singleThis, emptyObj).add(x);
            else return new TreeSet(singleThis, singleX);
        }
        else {
            if (!isXEven) return new TreeSet(emptyObj, singleThis).add(x);
            else return new TreeSet(singleX, singleThis);
        }
    }

    /**
     * Checks whether the given number is in the set.
     * @param x - Number to be checked.
     * @return True if number is in set, otherwise False.
     */
    public boolean contains(int x)
    {
        return number == x;
    }
    
    public IntSet union(IntSet other)
    {
        if (other.contains(number)) return other;
        else return other.add(number);
    }
    
    /**
     * Returns the string representation of this IntSet.
     * @param path - String which records the path it took
     * in the Tree Set.
     * @return The string representation of this TreeSet.
     */
    public String toString(String path)
    {
        int ans = number;
        if (!path.isEmpty()) { 
            char[] paths = path.toCharArray();        
            for (int i = paths.length - 1; i >= 0; i--) {
                ans *= 2;
                if (paths[i] == 'r') ans++;
            }
        }
        return String.valueOf(ans);
    }
}
