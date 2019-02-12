import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.IOException;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;

public class WebCrawler {

  final int NUMBER_OF_LINKS = 100;
  private ArrayList<String> links;
  private Queue<String> linksToVisit;
  private int count;

  public WebCrawler() {
    this.links = new ArrayList();
    this.linksToVisit = new PriorityQueue<>();
    this.count = 0;
  }

  public void search(String link) {

    linksToVisit.add(link);
    links.add(link);

    while (!linksToVisit.isEmpty()) {

      String l = linksToVisit.poll();
      System.out.println(count + ". " + l);
      count++;

      Document document = null;
      try {
        document = Jsoup.connect(l).get();
        Elements pages = document.select("a[href]");

        for (Element page : pages) {
          String newLink = page.attr("abs:href");
          if (count + linksToVisit.size() < NUMBER_OF_LINKS) {

            if (!links.contains(newLink) && newLink.length() >= 0) {
              links.add(newLink);
              linksToVisit.add(newLink);
            }

          } else {
            break;
          }
        }
      } catch (IOException e) {

      }
    }
  }
}
