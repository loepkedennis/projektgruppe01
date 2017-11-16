package org.projektmanagement.utils;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class CSVExporter 
{
	/*
	 * FileChooser: https://docs.oracle.com/javase/8/javafx/api/javafx/stage/FileChooser.html
	 * DEFAULT Trennzeichen: ";"
	 * 
	 * 
	 *
	 * Man kann eigene Trennzeichen nutzen:
	 * //Eigener Separator + quote: CSVExporter.writeLine(writer, Arrays.asList("a", "b", "c,c"), '|', '\'');
	 */
	private static final char DEFAULT_TRENNZEICHEN = ';';

    public static void writeLine(Writer w, List<String> values) throws IOException {
        writeLine(w, values, DEFAULT_TRENNZEICHEN, ' ');
    }
    
    public static void writeLine(Writer w, List<String> values, char separators) throws IOException {
        writeLine(w, values, separators, ' ');
    }

    private static String folgeCVSformat(String strValue) {

        String result = strValue;
        if (result.contains("\"")) {
            result = result.replace("\"", "\"\"");
        }
        return result;

    }

    public static void writeLine(Writer w, List<String> values, char chTrennzeichen, char chCustomQuote) throws IOException {

        boolean first = true;

        if (chTrennzeichen == ' ') {
        	chTrennzeichen = DEFAULT_TRENNZEICHEN;
        }

        StringBuilder sb = new StringBuilder();
        for (String value : values) {
            if (!first) {
                sb.append(chTrennzeichen);
            }
            if (chCustomQuote == ' ') {
                sb.append(folgeCVSformat(value));
            } else {
                sb.append(chCustomQuote).append(folgeCVSformat(value)).append(chCustomQuote);
            }

            first = false;
        }
        sb.append("\n");
        w.append(sb.toString());
    }
    
    public String setStrPfad(Stage stage)
    { //Setzt Speicherpfad für die CSV-Datei fest
    	FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Speicherordner wählen");
		File file = fileChooser.showSaveDialog(stage);
		
		if(file != null)
		{
			return file.getAbsolutePath();
		}else
		{
			return "";
		}
    }
}
