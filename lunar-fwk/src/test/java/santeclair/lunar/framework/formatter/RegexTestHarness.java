package santeclair.lunar.framework.formatter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Cette classe permet de faire des tests de regex au travers d'un main.
 * 
 * @author cgillet
 * 
 */
public class RegexTestHarness {

    public static void main(String[] args) {
        while (true) {
            try {
                System.out.println("Enter your regex: ");
                String saisie = new BufferedReader(new InputStreamReader(System.in)).readLine();

                System.out.println("Enter input string to search: ");
                String saisie2 = new BufferedReader(new InputStreamReader(System.in)).readLine();

                Matcher matcher = Pattern.compile(saisie).matcher(saisie2);

                boolean found = false;
                while (matcher.find()) {
                    System.out.println("matcher.groupCount() : " + matcher.groupCount());
                    System.out.format("I found the text \"%s\" starting at " + "index %d and ending at index %d.%n", matcher.group(),
                                    matcher.start(), matcher.end());
                    for (int i = 0; i < matcher.groupCount(); i++) {
                        System.out.println("matcher.group(" + i + ") : " + matcher.group(i));
                    }
                    found = true;
                }
                if (!found) {
                    System.out.format("No match found.%n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
