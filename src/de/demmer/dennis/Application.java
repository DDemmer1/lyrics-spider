package de.demmer.dennis;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dennis on 05.05.2018.
 */
public class Application {


    public static void main(String[] args) {
        CrawlerEngine crawler = new CrawlerEngine();


        crawler.getDriver().get("https://www.lyrics.com/random.php");
//        crawler.getDriver().get("https://www.lyrics.com/lyric/945545/Tool/Flood");

        LyricSpider lyricSpider = new LyricSpider(crawler);

        List<Song> songs = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            crawler.getDriver().navigate().to("https://www.lyrics.com/random.php");
            songs.add(lyricSpider.crawl());
            System.out.println("SONG ADDED");
        }


        for (Song song : songs) {
            System.out.println(song);
        }


        crawler.stop();

    }

}
