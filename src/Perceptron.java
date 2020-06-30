import java.util.ArrayList;
import java.util.List;

public class Perceptron {

    private int threshold = 0, learning = 1;
    private String name;
    List<Double> weights = new ArrayList<>();

    public Perceptron(String name){
        this.name = name;

        for (int i = 0; i < 26; i++) {
            weights.add(1.0);
        }
    }

    public String getName() {
        return name;
    }

    void trainingMethod(Language language){
        List<Double> list = language.getProportion();
        double s;

        s = calculate(list, weights);

        if(language.getName().equals(name)){
           while (s >= threshold) {
               weights = changeValues(weights, list, 0, 1);
               s = calculate(list, weights);
           }
        }else{
            while (s < threshold) {
                weights = changeValues(weights, list, 1, 0);
                s = calculate(list, weights);
            }
        }
    }

    Double calculate(List<Double> list, List<Double> values){
        Double s = 0.0;

        for (int i = 0; i < list.size(); i++) {
            s += values.get(i) * list.get(i);
        }
        return s;
    }

    private List<Double> changeValues(List<Double> values, List<Double> irisData, int rightValue, int faultyValue){

        List<Double> newValues = new ArrayList<>();

        for (int i = 0; i < values.size(); i++) {
            newValues.add(values.get(i) + learning * (rightValue - faultyValue) * irisData.get(i));
        }
        return newValues;
    }
}
