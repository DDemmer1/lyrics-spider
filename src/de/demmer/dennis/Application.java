package de.demmer.dennis;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dennis on 05.05.2018.
 */
public class Application {

//TODO check year
    public static void main(String[] args) {
        CrawlerEngine crawler = new CrawlerEngine();


        crawler.getDriver().get("https://www.lyrics.com/random.php");
//        crawler.getDriver().get("https://www.lyrics.com/lyric/31502775");

        LyricSpider lyricSpider = new LyricSpider(crawler);

        List<Song> songs = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            crawler.getDriver().navigate().to("https://www.lyrics.com/random.php");
            Song song = lyricSpider.crawl();

            if (song.getTrackID().length() > 0) {
                songs.add(song);
                System.out.println("SONG ADDED");
            } else {
                System.out.println("SONG EMPTY");
            }
        }
//        for (Song song : songs) {
//            System.out.println(song);
//        }


        crawler.stop();

        SongXMLBuilder xmlBuilder = new SongXMLBuilder();

        xmlBuilder.buildXML(songs);

    }

}
