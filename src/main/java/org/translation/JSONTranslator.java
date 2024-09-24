package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * An implementation of the Translator interface which reads in the translation
 * data from a JSON file. The data is read in once each time an instance of this class is constructed.
 */
public class JSONTranslator implements Translator {

    private final JSONArray jsonArray;
    private final Hashtable<String, Integer> keyMap;
    private final List<String> blockList = new ArrayList<>(List.of("id", "alpha2", "alpha3"));

    /**
     * Constructs a JSONTranslator using data from the sample.json resources file.
     */
    public JSONTranslator() {
        this("sample.json");
    }

    /**
     * Constructs a JSONTranslator populated using data from the specified resources file.
     * @param filename the name of the file in resources to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    public JSONTranslator(String filename) {
        // read the file to get the data to populate things...
        try {
            String jsonString = Files.readString(Paths.get(getClass().getClassLoader().getResource(filename).toURI()));

            this.jsonArray = new JSONArray(jsonString);
            this.keyMap = new Hashtable<>();

            // Create a hashtable mapping the alpha3 country indicators to the country position in jsonArray
            for (int i = 0; i < this.jsonArray.length(); i++) {
                this.keyMap.put(this.jsonArray.getJSONObject(i).getString("alpha3"), i);
            }

        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<String> getCountryLanguages(String country) {
        JSONObject countryDict = this.jsonArray.getJSONObject(this.keyMap.get(country.toLowerCase()));
        // NOTE: keySet returns a set such that the original object responds to changes,
        // thus mutation causes undesirable effects. Instead, we make a copy.
        Set<String> langs = new HashSet<>(countryDict.keySet());
        langs.removeAll(this.blockList);
        return new ArrayList<String>(langs);
    }

    @Override
    public List<String> getCountries() {
        return new ArrayList<>(this.keyMap.keySet());
    }

    @Override
    public String translate(String country, String language) {
        return this.jsonArray.getJSONObject(this.keyMap.get(country.toLowerCase())).getString(language.toLowerCase());
    }
}
