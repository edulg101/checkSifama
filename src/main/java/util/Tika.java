package util;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.microsoft.ooxml.OOXMLParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static util.AppConstants.SPREADSHEETPATH;

public class Tika {

    public static String readexcel(String fileName) throws TikaException, SAXException, IOException {

        //detecting the file type
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        FileInputStream inputstream = new FileInputStream(new File(fileName));
        ParseContext pcontext = new ParseContext();

        //OOXml parser
        OOXMLParser msofficeparser = new OOXMLParser();
        msofficeparser.parse(inputstream, handler, metadata, pcontext);
        inputstream.close();
        return handler.toString();

    }

    public static List<String[]> getFromExcel() throws IOException, TikaException, SAXException {

        String fullText = readexcel(SPREADSHEETPATH);


        String[] lineArray = fullText.split("\n");
        List<String> lines = new ArrayList<>(Arrays.asList(lineArray));
        lines.remove(0);
        lines.remove(0);

        int i = -1;
        int j = -1;

        List<String[]> tros = new ArrayList<>();

        for (String line : lines) {
            i++;
            String[] wordsArray = line.split("\t");
            List<String> words = new ArrayList<>(Arrays.asList(wordsArray));
            words.remove(0);
            String[] x = new String[3];
            for (String word : words) {
                j++;
                x[j] = word;
            }
            tros.add(x);
            j = -1;
        }
        return tros;
    }
}