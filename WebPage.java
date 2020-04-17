/**
 *
 *
 *  This is one of the basic class where an item called WebPage is created. This class contains an ArrayList and
 *  other variables.
 *
 */

import java.util.ArrayList;

public class WebPage
{
    private String url;
    private int index = -1, rank;
    private String links = "";

    private ArrayList<String> keywords;

    /**
     * The default constructor for the class
     */
    public WebPage()
    {
        url = "";
        rank = -1;
        index++;
        keywords = new ArrayList<String>();
    }

    /**
     * Another constructor for the class
     *
     * @param newUrl
     *      New URL for the WebPage.
     *
     * @param newKeywords
     *      New set of keywords for the WebPage.
     *
     * @param newIndex
     *      New index for the WebPage.
     */
    public WebPage(String newUrl, ArrayList<String> newKeywords, int newIndex)
    {
        keywords = new ArrayList<String>();
        this.url = newUrl;

        this.keywords = newKeywords;

        index = newIndex;
        rank = -1;
    }

    /**
     * Returns an Arraylist with the keywords.
     *
     * @return
     *      An Arraylist with the keywords.
     */
    public ArrayList<String> getKeywords()
    {
        return this.keywords;
    }

    /**
     * Changes the keywords to the new set of keywords.
     *
     * @param a
     *      New keywords
     */
    public void setKeywords(ArrayList<String> a)
    {
        this.keywords = a;
    }

    /**
     * Returns a String with the url.
     *
     * @return
     *      A String with the url.
     */
    public String getUrl()
    {
        return url;
    }

    /**
     * Sets the url for a webpage.
     *
     * @param url
     *      The new URL for the webpage.
     */
    public void setUrl(String url)
    {
        this.url = url;
    }

    /**
     * Returns the index for a webpage.
     *
     * @return
     *      The integer with the index.
     */
    public int getIndex()
    {
        return index;
    }

    /**
     * Sets the index for a webpage.
     *
     * @param index
     *      The new index for the webpage.
     */
    public void setIndex(int index)
    {
        this.index = index;
    }

    /**
     * Returns the rank for a webpage.
     *
     * @return
     *      The integer with the rank.
     */
    public int getRank()
    {
        return rank;
    }

    /**
     * Sets the rank for a webpage.
     *
     * @param rank
     *      The new rank for the webpage.
     */
    public void setRank(int rank)
    {
        this.rank = rank;
    }

    public void setLinks(String newLinks)
    {
        links = newLinks;
    }

    public String getLinks()
    {
        return this.links;
    }

    /**
     * Overwrites the toString method to format the information of a WebPage to a table format.
     *
     * @return
     *      Information of the WebPage.
     */
    public String toString()
    {
        String key = this.getKeywords().toString();

        String ans = String.format(" \t%-2d\t | \t%-20s\t | \t%2s\t | %-20s | %s ", this.getIndex(), this.getUrl(),
                this.getRank(), "*", key);

        key = null;

        return ans;
    }
}