package de.demmer.dennis;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dennis on 05.05.2018.
 */
public class Application {
    public static void main(String[] args) {
        CrawlerEngine crawler = new CrawlerEngine();
        crawler.getDriver().get("https://www.lyrics.com/random.php");

        LyricSpider lyricSpider = new LyricSpider(crawler);
        //TODO check serialization if no .ser exists
        List<Song> songs = lyricSpider.crawl(crawler,500,true,true);

        crawler.stop();

        //XML zum debuggen oder weiterverarbeiten
//        new SongXMLBuilder().buildXML(songs);

    }

}
