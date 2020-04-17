/**
 *
 *
 *  This is a main class where Search Engine is managed. The system consists of different WebPages running together
 *  that are saved into a Webgraph with links between them. With the Search Engine, you can add and remove links and
 *  pages and sort based on Rank, Index, and URL.
 *
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class SearchEngine
{
    public static final String PAGES_FILE = "pages.txt";
    public static final String LINKS_FILE = "links.txt";

    private static WebGraph web = new WebGraph();

    /**
     * This is the main method that runs the entire project.
     */
    public static void main(String[] args)
    {
        boolean keepRunning = true;
        Scanner sc = new Scanner(System.in);

        System.out.println("Loading WebGraph Data ...\n");

        web = WebGraph.buildFromFiles(PAGES_FILE, LINKS_FILE);

        web.updatePageRanks();

        System.out.println("Success!\n");

        while (keepRunning)
        {
            System.out.println("Menu:\n" +
                    "(AP) - Add a new page to the graph.\n" +
                    "(RP) - Remove a page from the graph.\n" +
                    "(AL) - Add a link between pages in the graph.\n" +
                    "(RL) - Remove a link between pages in the graph.\n" +
                    "(P) - Print the graph.\n" +
                    "(S) - Search for pages with a keyword.\n" +
                    "(Q) - Quit.");

            System.out.print("\nPlease select an option: ");
            String option = ((sc.nextLine()).toUpperCase());

            if (option.equals("AP"))
            {
                try {
                    System.out.println("Please enter a URL: ");
                    String URL = sc.nextLine();

                    System.out.println("Please enter keywords (space-separated): ");
                    String keywords = sc.nextLine();

                    ArrayList<String> kwords = new ArrayList<>();
                    String[] stringKwords = (keywords.split(" "));

                    for (int i = 0; i < stringKwords.length; i++) {
                        kwords.add(stringKwords[i]);
                    }

                    web.addPage(URL, kwords);

                    System.out.println(URL + "has been successfully added to the WebGraph");
                }

                catch(IllegalArgumentException i)
                {
                    System.out.println("The page you are trying to add already exists in the WebGraph or does not exist.");
                }
            }

            if (option.equals("RP"))
            {
                System.out.println("Please enter a URL: ");
                String URL = sc.nextLine();

                web.removePage(URL);

                System.out.println(URL + "has been removed to the WebGraph");
            }

            if (option.equals("AL"))
            {
                try
                {
                    System.out.println("Enter a source URL: ");
                    String source = sc.nextLine();

                    System.out.println("Enter a destination URL: ");
                    String destination = sc.nextLine();

                    web.addLink(source, destination);

                    System.out.println("Link successfully added from " + source + " to " + destination + "!");
                }

                catch(Exception e)
                {
                    System.out.println("There was an error in adding the link. Please try again");
                }

            }

            if (option.equals("RL"))
            {
                System.out.println("Enter a source URL: ");
                String source = sc.nextLine();

                System.out.println("Enter a destination URL: ");
                String destination = sc.nextLine();

                web.removeLink(source, destination);

                System.out.println("Link removed from " + source + " to " + destination + "!");
            }

            else
            {
                if (option.equals("P"))
                {
                    System.out.println("(I) Sort based on index (ASC)\n" +
                            "(U) Sort based on URL (ASC)\n" +
                            "(R) Sort based on rank (DSC)");

                    System.out.print("\nPlease select an option: ");
                    char printLetter = ((sc.next()).toUpperCase()).charAt(0);

                    switch (printLetter) {
                        case ('I'):
                        {
                            Collections.sort(web.getPages(), new IndexComparator());//Collections.reverseOrder(new PrecipitationComparator()));

                            web.printTable();
                        }
                        break;

                        case ('U'):
                        {
                            Collections.sort(web.getPages(), new URLComparator());

                            web.printTable();
                        }
                        break;

                        case ('R'):
                        {
                            Collections.sort(web.getPages(), new RankComparator());

                            web.printTable();
                        }
                        break;
                    }
                }

                if (option.equals("S"))
                {
                    System.out.print("\nSearch keyword: ");
                    String keyword = (sc.next());

                    ArrayList<WebPage> toPrint = new ArrayList();

                    for(int i = 0; i < web.getPages().size(); i++)
                    {
                        if(web.getPages().get(i).getKeywords().contains(keyword))
                        {
                            toPrint.add(web.getPages().get(i));
                        }
                    }

                    System.out.println("Rank \t PageRank \t URL\n" +
                            "---------------------------------------------------------------------");

                    Collections.sort(web.getPages(), new RankComparator());

                    for(int i = 0; i < web.getPages().size(); i++)
                    {
                        System.out.println("\t" + (i+1) + "\t|\t" + toPrint.get(i).getRank() + "\t|\t" + toPrint.get(i).getUrl());
                    }
                }

                if (option.equals("Q"))
                {
                    System.out.println("GoodBye.");

                    keepRunning = false;
                }
            }
        }
    }
}