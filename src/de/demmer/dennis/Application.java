package de.demmer.dennis;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dennis on 05.05.2018.
 */
public class Application {

    //TODO for machine learning corpus
    //TODO clear instrumentals
    //TODO clear no artists
    public static void main(String[] args) {


        CrawlerEngine crawler = new CrawlerEngine();
        crawler.getDriver().get("https://www.lyrics.com/random.php");
//        crawler.getDriver().get("https://www.lyrics.com/lyric/29480264");

        LyricSpider lyricSpider = new LyricSpider(crawler);

        List<Song> songs = new ArrayList<>();


        for (int i = 0; i < 50; i++) {

            try {
                crawler.getDriver().navigate().to("https://www.lyrics.com/random.php");
                Song song = lyricSpider.crawl();

                if (song.getTrackID().length() > 0) {
                    songs.add(song);
                    System.out.println("SONG "+ i +" ADDED");
                } else {
                    System.out.println("SONG "+ i +" EMPTY");
                }

            } catch (NullPointerException e) {
                System.out.println("NPE catched");
            }
            catch (IndexOutOfBoundsException e) {
                System.out.println("IOOBE catched");
            }
        }


        crawler.stop();

        SongXMLBuilder xmlBuilder = new SongXMLBuilder();

        xmlBuilder.buildXML(songs);

    }

}
