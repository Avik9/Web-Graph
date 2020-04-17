/**
 *
 *
 *  This is URLComparator class that extends the Comparator class and helps sort based on the URL.
 *
 */

import java.util.Comparator;

public class URLComparator implements Comparator
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

        return webPage1.getUrl().compareTo(webPage2.getUrl());
    }
}