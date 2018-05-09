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

    //TODO deserialize first

    public static void main(String[] args) {


        CrawlerEngine crawler = new CrawlerEngine();
        crawler.getDriver().get("https://www.lyrics.com/random.php");
//        crawler.getDriver().get("https://www.lyrics.com/lyric/29480264");

        LyricSpider lyricSpider = new LyricSpider(crawler);

        List<Song> songs = new ArrayList<>();


        for (int i = 0; i < 5000; i++) {

            try {
                crawler.getDriver().navigate().to("https://www.lyrics.com/random.php");
                Song song = lyricSpider.crawl();

                if (song.getTrackID().length() > 0 && !song.getGenre().isEmpty()) {
//                if (song.getTrackID().length() > 0) {
                    songs.add(song);
                    lyricSpider.serialize(songs);
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
        System.out.println(songs.size() + " with genres found");

        crawler.stop();






        new SongXMLBuilder().buildXML(songs);

    }

}
