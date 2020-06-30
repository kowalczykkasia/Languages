import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main extends JFrame {

    Main() {

        FileVisitor.open("Languages");
        List<Language> list = FileVisitor.getListOfLanguages();
        List<Perceptron> perceptrons = new ArrayList<>();

        for (String name : FileVisitor.getNamesOfLanguages()) {
            perceptrons.add(training(new Perceptron(name), list));
        }

        FileVisitor.open("Testing");
        List<Language> tesingList = FileVisitor.getListOfLanguages();
        double count = 0;
        for (Language language : tesingList) {
            if (language.getName().equals(checkLanguage(perceptrons, language))) count++;
            else System.out.println(language.getName() + " : " + checkLanguage(perceptrons, language));
        }

        System.out.println(count + " of " + tesingList.size() + " correct");
        double similarity = count / tesingList.size() * 100;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        JPanel jPanel = new JPanel();
        jPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        JTextArea jTextArea = new JTextArea();
        jTextArea.setBounds(10, 10, 250, 200);
        JScrollPane jScrollPane = new JScrollPane(jTextArea);
        add(jScrollPane, BorderLayout.CENTER);

        Button button = new Button("Check");
        JLabel jLabel = new JLabel("");
        add(jLabel, BorderLayout.AFTER_LINE_ENDS);
        add(new JLabel(similarity + "%"), BorderLayout.BEFORE_FIRST_LINE);

        button.addActionListener(e -> {
            Language language = new Language("", new StringBuilder(jTextArea.getText()));

            JFrame result = new JFrame();
            result.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            result.setBounds(50, 50, 100, 100);
            result.setVisible(true);

            JLabel response = new JLabel(checkLanguage(perceptrons, language));
            result.add(response);

        });

        add(button, BorderLayout.PAGE_END);
        setVisible(true);
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> new Main());
    }

    private static Perceptron training(Perceptron perceptron, List<Language> list) {

        List<Double> tmpList = Arrays.asList(0.0);
        int i = 0;
        while (!tmpList.equals(perceptron.weights)) {
            tmpList = perceptron.weights;
            for (Language language : list) {
                perceptron.trainingMethod(language);
            }
        }
        return perceptron;
    }

    private static String checkLanguage(List<Perceptron> perceptrons, Language language) {

        String name = "Unidentified";
        double min = 0.0;

        for (Perceptron perceptron : perceptrons) {
            double calculated = perceptron.calculate(language.getProportion(), perceptron.weights);

            if (calculated < min) {
                min = calculated;
                name = perceptron.getName();
            }
        }
        return name;
    }
}