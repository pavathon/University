import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
/**
 * The test class TreeSetTest.
 *
 * @author  Mark Browne
 * @version 1
 */
public class TreeSetTest
{
    private final IntSet small, otherSmall, large, random;
    
    /**
     * Default constructor for test class TreeSetTest
     */
    public TreeSetTest()
    {
        small = EmptySet.empty().add(1).add(2);
        otherSmall = EmptySet.empty().add(3).add(4);
        large = EmptySet.empty().add(1).add(2).add(3).add(4)
                                .add(5).add(6).add(7).add(8)
                                .add(9).add(10);
        random = EmptySet.empty().add(2).add(5).add(6).add(8)
                                 .add(9);
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
    }
       
    /**
     * Checks whether 2 is in the small IntSet.
     * Should return true.
     */
    @Test
    public void checkContains1()
    {
        assertTrue(small.contains(2));
    }
    
    /**
     * Checks whether 5 is in the large IntSet.
     * Should return true.
     */
    @Test
    public void checkContains2()
    {
        assertTrue(large.contains(5));
    }

    /**
     * Checks whether 3 is in the small IntSet.
     * Should return true.
     */
    @Test
    public void checkNotContains1()
    {
        assertFalse(small.contains(3));
    }

    /**
     * Checks whether 4 is in the small IntSet.
     * Should return true.
     */
    @Test
    public void checkNotContains2()
    {
        assertFalse(small.contains(0));
    }

    /**
     * Checks whether 20 is in the large IntSet.
     * Should return true.
     */
    @Test
    public void checkNotContains3()
    {
        assertFalse(large.contains(20));
    }

    /**
     * Checks whether 0 is in the small IntSet.
     * Should return true.
     */
    @Test
    public void checkNotContains4()
    {
        assertFalse(small.contains(0));
    }

    /**
     * Checks whether the union of the small and otherSmall set outputs the correct string representation.
     */
    @Test
    public void checkUnion1()
    {
        assertEquals(small.union(otherSmall).makeString(), "{4,2,1,3}");
    }

    /**
     * Checks whether the union of the small and large set outputs the correct string representation.
     * Should output the large set.
     */
    @Test
    public void checkUnion2()
    {
        assertEquals(small.union(large).makeString(), large.makeString());
    }

    /**
     * Checks whether the union of the otherSmall and large set outputs the correct string representation.
     * Should output the large set.
     */
    @Test
    public void checkUnion3()
    {
        assertEquals(otherSmall.union(large).makeString(), large.makeString());
    }

    /**
     * Checks whether the union of the random and large set outputs the correct string representation.
     * Should output the large set.
     */
    @Test
    public void checkUnion4()
    {
        assertEquals(random.union(large).makeString(), large.makeString());
    }

    /**
     * Checks whether small set outputs the correct string representation.
     */
    @Test
    public void checkToString1()
    {
        assertEquals(small.makeString(), "{2,1}");
    }

    /**
     * Checks whether large set outputs the correct string representation.
     */
    @Test
    public void checkToString2()
    {
        assertEquals(large.makeString(), "{8,4,2,10,6,1,9,5,3,7}");
    }

    /**
     * Checks whether random set outputs the correct string representation.
     */
    @Test
    public void checkToString3()
    {
        assertEquals(random.makeString(), "{8,2,6,9,5}");
    }    
}
