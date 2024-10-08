package org.translation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Main class for this program.
 * Complete the code according to the "to do" notes.<br/>
 * The system will:<br/>
 * - prompt the user to pick a country name from a list<br/>
 * - prompt the user to pick the language they want it translated to from a list<br/>
 * - output the translation<br/>
 * - at any time, the user can type quit to quit the program<br/>
 */
public class Main {

    /**
     * This is the main entry point of our Translation System!<br/>
     * A class implementing the Translator interface is created and passed into a call to runProgram.
     * @param args not used by the program
     */
    public static void main(String[] args) {
        Translator translator = new JSONTranslator();

        runProgram(translator);
    }

    /**
     * This is the method which we will use to test your overall program, since
     * it allows us to pass in whatever translator object that we want!
     * See the class Javadoc for a summary of what the program will do.
     * @param translator the Translator implementation to use in the program
     */
    public static void runProgram(Translator translator) {
        // exit string is quit
        final String end = "quit";
        while (true) {
            String country = promptForCountry(translator);
            if (country.equals(end)) {
                break;
            }
            CountryCodeConverter countryConvert = new CountryCodeConverter();
            String countryCode = countryConvert.fromCountry(country);

            String language = promptForLanguage(translator, countryCode);
            if (language.equals(end)) {
                break;
            }
            LanguageCodeConverter languageConvert = new LanguageCodeConverter();
            String languageCode = languageConvert.fromLanguage(language);

            System.out.println(country + " in " + language + " is " + translator.translate(countryCode, languageCode));
            System.out.println("Press enter to continue or quit to exit.");
            Scanner s = new Scanner(System.in);
            String textTyped = s.nextLine();

            if (textTyped.equals(end)) {
                break;
            }
        }
    }

    // Note: CheckStyle is configured so that we don't need javadoc for private methods
    private static String promptForCountry(Translator translator) {
        List<String> countries = translator.getCountries();
        ArrayList<String> countryNames = new ArrayList<>();
        CountryCodeConverter converter = new CountryCodeConverter();
        for (String country : countries) {
            countryNames.add(converter.fromCountryCode(country));
        }

        Collections.sort(countryNames);
        for (String country : countryNames) {
            System.out.println(country);
        }

        System.out.println("select a country from above:");

        Scanner s = new Scanner(System.in);
        return s.nextLine();

    }

    // Note: CheckStyle is configured so that we don't need javadoc for private methods
    private static String promptForLanguage(Translator translator, String country) {
        List<String> codes = translator.getCountryLanguages(country);
        ArrayList<String> languages = new ArrayList<>();
        LanguageCodeConverter converter = new LanguageCodeConverter();
        for (String code : codes) {
            languages.add(converter.fromLanguageCode(code));
        }

        Collections.sort(languages);
        for (String language : languages) {
            System.out.println(language);
        }

        System.out.println("select a language from above:");

        Scanner s = new Scanner(System.in);
        return s.nextLine();
    }
}
