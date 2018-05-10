package de.demmer.dennis;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LyricSpider {


    private WebDriver driver;

    public LyricSpider(CrawlerEngine crawlerEngine) {
        driver = crawlerEngine.getDriver();
    }

    public List<Song> crawl(CrawlerEngine crawler, int numberOfSongs, boolean isTrainingIndex, boolean serialize) {
        List<Song> songs;

        if (serialize) {
            songs = deserializeSongs(isTrainingIndex);
        } else {
            songs = new ArrayList<>();
        }
        for (int i = 0; i < numberOfSongs; i++) {

            try {
                crawler.getDriver().navigate().to("https://www.lyrics.com/random.php");
                Song song = getSong();


                //TRAINING INDEX
                if (isTrainingIndex && song.getTrackID().length() > 0) {
                    //Wenn Genre vorhanden
                    if (!song.getGenre().isEmpty()) {
                        songs.add(song);
                        System.out.println("SONG " + i + " ADDED TO 'songsTrain.ser'. " + songs.size() + " IN LIST");
                        // serialisierung
                        if (serialize) {
                            serialize(songs, isTrainingIndex);
                        }
                    } else {
                        System.out.println("SONG " + i + " NOT ADDED. GENRE IS EMPTY");
                    }

                //CORPUS INDEX
                } else if (!isTrainingIndex && song.getTrackID().length() > 0) {
                    songs.add(song);
                    System.out.println("SONG " + i + " ADDED TO 'songs.ser'.  " + songs.size() + " IN LIST");
                    // serialisierung
                    if (serialize) {
                        serialize(songs, isTrainingIndex);
                    }
                } else {
                    System.out.println("SONG " + i + " NOT ADDED. SONG IS EMPTY");
                }

                //Songs sind auf der Seite teilweise unvollstaendig oder Fehlerhaft deshalb muessen eventuelle Exceptions aufgefangen werden
            } catch (NullPointerException e) {
                System.err.println("SONG " + i + " CONTAINS ERROR");
            } catch (IndexOutOfBoundsException e) {
                System.err.println("SONG " + i + " CONTAINS ERROR");

            }
        }

        return songs;
    }


    public Song getSong() {
        //suche nach elementen
        List<WebElement> lyricEle = driver.findElements(By.tagName("pre"));
        List<WebElement> artistEle = driver.findElements(By.className("artist-thumb"));
        List<WebElement> yearEle = driver.findElements(By.tagName("dd"));
        List<WebElement> genreEle = driver.findElements(By.className("small"));
        List<WebElement> songNameEle = driver.findElements(By.id("lyric-title-text"));
        List<WebElement> albumNameEle = driver.findElements(By.tagName("h3"));
        List<WebElement> trackIDEle = driver.findElements(By.id("cite-style-select"));

        //Eintraege initialisieren
        String songName = "";

        String albumName = "";

        String artist = "";

        String lyrics = "";

        String genre = "";

        String year = "";

        String trackID = "";

        List<String> styles = new ArrayList<>();


        //Wenn Eintraege vorhanden Werte zuweisen

        if (!songNameEle.isEmpty())
            songName = songNameEle.get(0).getText();

        if (!albumNameEle.isEmpty() && (albumNameEle.size() >= 4)) {
            if (albumNameEle.get(4).getText().equals("Promoted Songs")) {
            } else {
                albumName = albumNameEle.get(4).getText();
            }
        }

        if (!artistEle.isEmpty()) {
            if (!artistEle.get(0).getAttribute("data-author").isEmpty())
                artist = artistEle.get(0).getAttribute("data-author").toString();
        }

        if (!lyricEle.isEmpty())
            lyrics = lyricEle.get(0).getText();

        if (!yearEle.isEmpty() && !yearEle.get(0).getText().contains("Views"))
            year = yearEle.get(0).getText();


        if (!genreEle.isEmpty()) {
            genre = genreEle.get(0).getText();
            for (int i = 1; i < genreEle.size(); i++) {
                styles.add(genreEle.get(i).getText());
            }
        }

        if (!trackIDEle.isEmpty()) {
            trackID = trackIDEle.get(0).getAttribute("data-key");
        }
        return new Song(songName, albumName, artist, lyrics, genre, styles, year, trackID);
    }


    public void serialize(List<Song> songs, boolean isTrainingIndex) {
        try {
            String path;
            if (isTrainingIndex) {
                path = "songsTrain.ser";
            } else {
                path = "songs.ser";
            }
            FileOutputStream fout = new FileOutputStream(path);
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(songs);
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public List<Song> deserializeSongs(boolean isTrainingIndex) {
        try {
            String path;
            if (isTrainingIndex) {
                path = "songsTrain.ser";
            } else {
                path = "songs.ser";
            }
            FileInputStream streamIn = new FileInputStream(path);
            ObjectInputStream objectinputstream = new ObjectInputStream(streamIn);
            List<Song> songs = (List<Song>) objectinputstream.readObject();
            objectinputstream.close();
            System.out.println("Songs deserialized. " + songs.size() + " in List");
            return songs;
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Not deserialized");
        return null;
    }
}
