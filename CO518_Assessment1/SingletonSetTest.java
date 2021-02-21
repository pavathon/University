import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class SingletonSetTest.
 *
 * @author  Mark Browne
 * @version 1
 */
public class SingletonSetTest
{
    private final IntSet ss1, ss2;
    
    /**
     * Default constructor for test class SingletonSetTest
     */
    public SingletonSetTest()
    {
        ss1 = SingletonSet.singleton(5);
        ss2 = SingletonSet.singleton(20);
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
     * Checks whether 5 is in set 1.
     * Should return true.
     */
    @Test
    public void checkContains1()
    {
        assertTrue(ss1.contains(5));
    }

    /**
     * Checks whether 20 is in set 2.
     * Should return false.
     */
    @Test
    public void checkContains2()
    {
        assertTrue(ss2.contains(20));
    }

    /**
     * Checks whether 4 is in set 1.
     * Should return false.
     */
    @Test
    public void checkNotContains1()
    {
        assertFalse(ss1.contains(4));
    }

    /**
     * Checks whether 0 is in set 1.
     * Should return false.
     */
    @Test
    public void checkNotContains2()
    {
        assertFalse(ss1.contains(0));
    }

    /**
     * Checks whether 19 is in set 2.
     * Should return false.
     */
    @Test
    public void checkNotContains3()
    {
        assertFalse(ss2.contains(19));
    }

    /**
     * Checks whether 0 is in set 2.
     * Should return false.
     */
    @Test
    public void checkNotContains4()
    {
        assertFalse(ss2.contains(0));
    }

    /**
     * Checks whether set 1 outputs the correct string representation.
     */
    @Test
    public void checkToString1()
    {
        assertEquals(ss1.makeString(), "{5}");
    }

    /**
     * Checks whether set 2 outputs the correct string representation.
     */
    @Test
    public void checkToString2()
    {
        assertEquals(ss2.makeString(), "{20}");
    }

    /**
     * Checks whether the union of set 1 and 2 outputs the correct string representation.
     */
    @Test
    public void checkToString3()
    {
        assertEquals(ss1.union(ss2).makeString(), "{20,5}");
    }
}
