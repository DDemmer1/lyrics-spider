package de.demmer.dennis;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class LyricSpider {


    private WebDriver driver;
    private List<String> output;

    public LyricSpider(CrawlerEngine crawlerEngine) {
        driver = crawlerEngine.getDriver();

        output = new ArrayList<>();

    }


    public Song crawl() {

        //suche nach elementen
        List<WebElement> lyricEle = driver.findElements(By.tagName("pre"));
        List<WebElement> artistEle = driver.findElements(By.className("artist-thumb"));
        List<WebElement> yearEle = driver.findElements(By.tagName("dd"));
        List<WebElement> genreEle = driver.findElements(By.className("small"));
        List<WebElement> songNameEle = driver.findElements(By.id("lyric-title-text"));
        List<WebElement> albumNameEle = driver.findElements(By.tagName("h3"));
        List<WebElement> trackIDEle = driver.findElements(By.id("cite-style-select"));

        //TODO album sometimes incorrect

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

        if (!albumNameEle.isEmpty() && (albumNameEle.size()>=4)){
            if(albumNameEle.get(4).getText().equals("Promoted Songs")){
            }else{
                albumName = albumNameEle.get(4).getText();
            }

        }



        if (!artistEle.isEmpty()){
            if(!artistEle.get(0).getAttribute("data-author").isEmpty())
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


}
