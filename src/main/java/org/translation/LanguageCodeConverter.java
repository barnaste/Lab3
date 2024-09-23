package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;

/**
 * This class provides the service of converting language codes to their names.
 */
public class LanguageCodeConverter {

    private List<String> countryNames = new ArrayList<String>();
    private List<String> languageCodes = new ArrayList<String>();

    /**
     * Default constructor which will load the language codes from "language-codes.txt"
     * in the resources folder.
     */
    public LanguageCodeConverter() {
        this("language-codes.txt");
    }

    /**
     * Overloaded constructor which allows us to specify the filename to load the language code data from.
     * @param filename the name of the file in the resources folder to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    public LanguageCodeConverter(String filename) {

        try {
            List<String> lines = Files.readAllLines(Paths.get(getClass()
                    .getClassLoader().getResource(filename).toURI()));

            // DONE Task: use lines to populate the instance variable
            //           tip: you might find it convenient to create an iterator using lines.iterator()

            Iterator<String> iterator = lines.iterator();
            while (iterator.hasNext()) {
                String line = iterator.next();
                String[] country = line.split(" ");
                countryNames.add(country[0]);
                languageCodes.add(country[1]);
            }

        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }

    }

    /**
     * Returns the name of the language for the given language code.
     * @param code the language code
     * @return the name of the language corresponding to the code
     */
    public String fromLanguageCode(String code) {
        // DONE Task: update this code to use your instance variable to return the correct value
        return countryNames.get(languageCodes.indexOf(code));
    }

    /**
     * Returns the code of the language for the given language name.
     * @param language the name of the language
     * @return the 2-letter code of the language
     */
    public String fromLanguage(String language) {
        // DONE Task: update this code to use your instance variable to return the correct value
        return languageCodes.get(countryNames.indexOf(language));
    }

    /**
     * Returns how many languages are included in this code converter.
     * @return how many languages are included in this code converter.
     */
    public int getNumLanguages() {
        // DONE Task: update this code to use your instance variable to return the correct value
        return countryNames.size();
    }
}
