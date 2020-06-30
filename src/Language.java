
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Language {

    private String name;
    private List<Double> proportion;
    private Map<Character, Double> letters = new HashMap();

    public Language(String name, StringBuilder text){
        this.name = name;
        proportion = setProportion(setLetters(text.toString()));
        proportion = normalize(proportion);
    }

    public List<Double>  getProportion(){
        return proportion;
    }
    public String getName(){
        return name;
    }


    private int setLetters(String text){

        int sumOfLetters = 0;

        for (int i = 0; i < 26; i++) {
            letters.put((char)(97+i), 0.0);
        }

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);

            if (letters.containsKey(c)){
                sumOfLetters++;
                double count = letters.get(c);
                letters.replace(c, count, count + 1);
            }
        }
        return sumOfLetters;
    }
    private List<Double> normalize(List<Double> prop){
        double vectorLength = 0.0;
        List<Double> normalizedProportionOfLetters = new ArrayList<>();

        for (Double d: proportion){
            vectorLength += d*d;
        }
        vectorLength = Math.sqrt(vectorLength);

        for(Double d : proportion){
            normalizedProportionOfLetters.add(d/vectorLength);
        }
        return normalizedProportionOfLetters;
    }

    private List<Double>  setProportion(int sumOfLetters){

        List<Double> prop = new ArrayList<>();

        for (int i = 0; i < letters.size() ; i++) {
            prop.add((letters.get((char)(97 + i))) / sumOfLetters);
        }

        return prop;
    }

}
