package de.demmer.dennis;

import java.io.File;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class SongXMLBuilder {


    public void buildXML(List<Song> songList) {

        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // root elements
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("songs");
            doc.appendChild(rootElement);


            for (Song s: songList){

            // song elements
            Element song = doc.createElement("song");
            rootElement.appendChild(song);

            // set song attribute
             song.setAttribute("id", s.getTrackID());

            // songname elements
            Element firstname = doc.createElement("name");
            firstname.appendChild(doc.createTextNode(s.getSongName()));
            song.appendChild(firstname);

            // album elements
            Element albumName = doc.createElement("album");
            albumName.appendChild(doc.createTextNode(s.getAlbumName()));
            song.appendChild(albumName);

            // artist elements
            Element artist = doc.createElement("artist");
            artist.appendChild(doc.createTextNode(s.getArtist()));
            song.appendChild(artist);

            // lyrics elements
            Element lyrics = doc.createElement("lyrics");
            lyrics.appendChild(doc.createTextNode(s.getLyrics()));
            song.appendChild(lyrics);


            // genre elements
            Element genre = doc.createElement("genre");
            genre.appendChild(doc.createTextNode(s.getGenre()));
            song.appendChild(genre);



            // styles elements
            Element styles = doc.createElement("styles");
            for (String songStyle: s.getStyles()) {
                Element style = doc.createElement("style");
                styles.appendChild(style);
                style.appendChild(doc.createTextNode(songStyle));
            }

            song.appendChild(styles);

            // year elements
            Element year = doc.createElement("year");
            year.appendChild(doc.createTextNode(s.getYear()));
            song.appendChild(year);


            }

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("songs.xml"));

            // Output to console for testing
            // StreamResult result = new StreamResult(System.out);

            transformer.transform(source, result);

            System.out.println("File saved!");

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }

    }

}
