/**
 *
 *
 *  This is the WebGraph class that contains ArrayLists and two-dimensional arrays with all the WebPages.
 *
 */

import java.io.*;
import java.util.*;

public class WebGraph
{
    private ArrayList<WebPage> pages;

    private static final int MAX_PAGES = 40;

    private int [][] edges = new int[MAX_PAGES][MAX_PAGES];

    private WebPage temp = new WebPage();

    private int count = 0;

    /**
     * The default constructor
     */
    public WebGraph()
    {
        pages = new ArrayList<WebPage>();

        for(int i = 0; i < MAX_PAGES; i++)
        {
            for(int j = 0; j < MAX_PAGES; j++)
            {
                edges[i][j] = -1;
            }
        }
    }

    /**
     * Builds a graph based on the links and pages file given.
     *
     * @param pagesFilePath
     *      The name of the pages file.
     *
     * @param linksFilePath
     *      Name of the links file.
     *
     * @return
     *      Returns the webgraph made from the pages.
     *
     * @throws IllegalArgumentException
     *      Throws an error when a null file or incorrect names are given.
     */
    public static WebGraph buildFromFiles(String pagesFilePath, String linksFilePath) throws IllegalArgumentException
    {
        WebGraph a = new WebGraph();

        ArrayList<String> kwords = new ArrayList<>();

        String[] stringKwords;

        String currentLine, from ,to;

        try
        {
            File pageFile = new File(pagesFilePath);

            Scanner pageSc = new Scanner(pageFile);

            while(pageSc.hasNextLine())
            {
                currentLine = pageSc.nextLine().trim();

                stringKwords = (currentLine.substring(currentLine.indexOf(" ")).split(" "));

                for(int i = 0; i < stringKwords.length; i++)
                {
                    kwords.add(stringKwords[i]);
                }

                a.addPage((currentLine.substring(0, currentLine.indexOf(" "))), kwords);
            }
        }

        catch(FileNotFoundException f)
        {
            System.out.println("The page file cannot be found. Please try again.");
        }

        try
        {
            FileInputStream linkFile = new FileInputStream(linksFilePath);

            Scanner linkSc = new Scanner(linkFile);

            while(linkSc.hasNextLine())
            {
                currentLine = linkSc.nextLine().trim();
                from = currentLine.substring(0, currentLine.indexOf(" "));
                to = currentLine.substring(currentLine.indexOf(" ") + 1);

                a.addLink(from, to);
            }

            a.updatePageRanks();
        }

        catch(FileNotFoundException f)
        {
            System.out.println("The page file cannot be found. Please try again.");
        }

        return a;
    }

    /**
     * Returns the arraylist of WebPages.
     *
     * @return
     *      Returns the arraylist.
     */
    public ArrayList<WebPage> getPages()
    {
        return pages;
    }

    /**
     * Adds a WebPage to the current WebGraph.
     *
     * @param url
     *      Url of the webpage to create
     *
     * @param keywords
     *      Keywords of thw webpage to create
     *
     * @throws IllegalArgumentException
     *      Throws an exception when the url or the keywords are incorrect.
     */
    public void addPage(String url, ArrayList<String> keywords) throws IllegalArgumentException
    {
        for(int i = 0; i < this.pages.size(); i++)
        {
            if(this.pages.get(i).getUrl().equals(url))
            {
                throw new IllegalArgumentException();
            }
        }

        if(url == null || keywords == null)
        {
            throw new IllegalArgumentException();
        }

        else
        {
            temp = new WebPage(url, keywords, count);

            this.pages.add(temp);

            count++;
        }

        this.updatePageRanks();
    }

    /**
     * Creates links between pages.
     *
     * @param source
     *      The source of the link.
     *
     * @param destination
     *      The link of the destination.
     *
     * @throws IllegalArgumentException
     *      throws an exception when the links for destination or the source are invalid
     */
    public void addLink(String source, String destination) throws IllegalArgumentException
    {
        int sourceIndex = -1;
        int destinationIndex = -1;

        try {
            if (source == null || destination == null) {
                throw new IllegalArgumentException();
            }

            for (int i = 0; i < pages.size(); i++) {
                if (this.pages.get(i).getUrl().equals(source)) {
                    sourceIndex = i;
                }
            }

            for (int i = 0; i < pages.size(); i++) {
                if (this.pages.get(i).getUrl().equals(destination)) {
                    destinationIndex = i;
                }
            }

            this.edges[sourceIndex][destinationIndex] = 1;


            for (int i = 0; i < count; i++) {
                for (int j = 0; j < count; j++) {
                    if (this.edges[i][j] == -1) {
                        this.edges[i][j] = 0;
                    }
                }
            }

            this.updatePageRanks();
        }

        catch(IllegalArgumentException i)
        {
            System.out.println("The links that you are trying to add do not exist.");
        }
    }

    /**
     * Removes a page from the current webgraph.
     *
     * @param url
     *      Removes the webpage with this url.
     */
    public void removePage(String url)
    {
        int index = -1;
        for(int i = 0; i < this.getPages().size(); i++)
        {
            if(this.getPages().get(i).getUrl().equals(url))
            {
                index = i;
            }
        }

        for(int i = index; i < this.getPages().size(); i++)
        {
            this.getPages().get(i).setIndex(this.getPages().get(i).getIndex() - 1);
        }

        this.getPages().remove(index);

        for(int i = 0; i < this.getPages().size(); i++)
        {
            for(int j = i; j < this.getPages().size() - 1; j++)
            {
                this.edges[index][j] = this.edges[index + 1][j];
            }

            for(int j = i; j < this.getPages().size() - 1; j++)
            {
                this.edges[j][index] = this.edges[j][index + 1];
            }
        }

        this.updatePageRanks();
    }

    /**
     * Removes the link between two webpages.
     *
     * @param source
     *      The source of the link to be removed.
     *
     * @param destination
     *      The destination of the link to be removed.
     */
    public void removeLink(String source, String destination)
    {
        int sourceIndex = -1;
        int destinationIndex = -1;

        if(source == null || destination == null)
        {
            throw new IllegalArgumentException();
        }

        for(int i = 0; i < pages.size(); i++)
        {
            if (this.pages.get(i).getUrl().equals(source)) {
                sourceIndex = i;
            }
        }

        for(int i = 0; i < pages.size(); i++)
        {
            if (this.pages.get(i).getUrl().equals(destination)) {
                destinationIndex = i;
            }
        }

        this.edges[sourceIndex][destinationIndex] = 0;

        this.updatePageRanks();
    }

    /**
     * Updates the page ranks of every WebPage.
     */
    public void updatePageRanks()
    {
        int rank;

        for(int i = 0; i < this.pages.size(); i++) // i is the column number
        {
            rank = 0;

            this.pages.get(i).setRank(rank);

            for(int j = 0; j < this.pages.size(); j++) // j is the row number
            {
                if(this.edges[j][i] == 1)
                {
                    rank++;
                }

                this.pages.get(i).setRank(rank);
            }
        }
    }

    /**
     * Prints the WebGraph as a table.
     */
    public void printTable()
    {
        String links = "";

        System.out.println("Index\t\t\t\tURL\t\t\t\tPageRank\t\tLinks\t\t\t\tKeywords\n" +
                "----------------------------------------------------------------------------------------------------");

        for (int i = 0; i < this.getPages().size(); i++)
        {
            for(int j = 0; j < this.getPages().size(); j++)
            {
                if(edges[i][j] == 1)
                {
                    links += j + ", ";
                }
            }

            System.out.println((this.getPages().get(i).toString()).replace("*", links));

            links = "";
        }
    }
}
