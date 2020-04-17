/**
 *
 *
 *  This is IndexComparator class that extends the Comparator class and helps sort based on the Index.
 *
 */

import java.util.Comparator;

public class IndexComparator implements Comparator
{
    /**
     *  The compareTo method that does the comparing
     *
     * @param a
     *      Object 1
     *
     * @param b
     *      Object 2
     *
     * @return
     *      Returns which one is greater
     */
    public int compare(Object a, Object b)
    {
        WebPage webPage1 = (WebPage) a;
        WebPage webPage2 = (WebPage) b;

        if(webPage1.getIndex() < webPage2.getIndex())
        {
            return -1;
        }

        if(webPage1.getIndex() > webPage2.getIndex())
        {
            return 1;
        }

        return 0;
    }
}