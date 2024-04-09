package calculator;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator extends JFrame {
    private String problem;
    private String answer;

    private static final char additionSymbol = '\u002B';
    private static final char subtractionSymbol = '-';
    private static final char multiplicationSymbol = '\u00D7';
    private static final char divisionSymbol = '\u00F7';

    private final String[][] BUTTON_TEXTS = {
            {"7", "8", "9", String.valueOf(additionSymbol)},
            {"4", "5", "6", String.valueOf(subtractionSymbol)},
            {"1", "2", "3", String.valueOf(multiplicationSymbol)},
            {"0", ".", "=", String.valueOf(divisionSymbol)},
            {"C", "Del"}
    };

    private final String[] BUTTON_NAMES = {
            "Seven", "Eight", "Nine", "Add",
            "Four", "Five", "Six", "Subtract",
            "One", "Two", "Three", "Multiply",
            "Zero", "Dot", "Equals", "Divide",
            "Clear", "Delete"
    };

    public Calculator() {
        JLabel equationTextField = createEquationTextField();
        JLabel resultTextField = createResultTextField();
        JPanel buttonPanel = createButtons(equationTextField, resultTextField);
        JPanel mainPanel = createMainPanel(equationTextField, resultTextField, buttonPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Calculator");
        getContentPane().add(mainPanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createButtons(JLabel equationTextField, JLabel resultTextField) {
        JPanel buttonPanel = new JPanel(new GridLayout(BUTTON_TEXTS.length, BUTTON_TEXTS[0].length));
        int buttonCounter = 0;
        for (int row = 0; row < BUTTON_TEXTS.length; row++) {
            for (int column = 0; column < BUTTON_TEXTS[row].length; column++) {
                JButton button = new JButton(BUTTON_TEXTS[row][column]);
                button.setName(BUTTON_NAMES[buttonCounter]);
                // Add action listener to each button
                switch (BUTTON_TEXTS[row][column]) {
                    // If button is "=", execute the calculate function
                    case "=" -> button.addActionListener(e -> {
                        String problem = equationTextField.getText();
                        if (checkEquation(problem)) {
                            ArrayList<String> infixExpression = infixParse(problem);
                            ArrayList<String> postfixExpression = infixToPostfixConvertor(infixExpression);
                            answer = calculatePostfixExpression(postfixExpression);
                            resultTextField.setText(answer);
                        } else {
                            equationTextField.setForeground(Color.RED.darker());
                        }
                    });
                    // else if button is "C", clear the text
                    case "C" -> button.addActionListener(e -> equationTextField.setText(""));
                    // else if button is "Del", delete the previous character
                    case "Del" -> button.addActionListener(e -> {
                        String currentText = equationTextField.getText();
                        String newText = currentText.substring(0, currentText.length() - 1);
                        equationTextField.setText(newText);
                    });
                    // For operator buttons, including formatting logic
                    case "\u002B", "-", "\u00D7", "\u00F7" -> button.addActionListener(e -> {
                        JButton clickedButton = (JButton) e.getSource();
                        String currentEquation = equationTextField.getText() + clickedButton.getText();
                        if (currentEquation.length() != 1) {
                            String newEquation = formatEquation(currentEquation);
                            equationTextField.setForeground(Color.BLACK);
                            equationTextField.setText(newEquation);
                        }
                    });
                    // otherwise print the value of the button to the text instead
                    default -> button.addActionListener(e -> {
                        equationTextField.setForeground(Color.BLACK);
                        JButton clickedButton = (JButton) e.getSource();
                        String buttonText = clickedButton.getText();
                        String currentEquation = equationTextField.getText();
                        equationTextField.setText(currentEquation + buttonText);
                    });
                }
                buttonPanel.add(button);
                buttonCounter++;
            }
        }
        return buttonPanel;
    }

    private String formatEquation(String problem) {
        String newEquation = formatOperator(problem);
        newEquation = formatZeros(newEquation);
        return newEquation;
    }

    // If 2x operators are typed in sequentially, replace the first operator with the second operator
    private String formatOperator(String problem) {
        if (problem.length() > 2) {
            char lastChar = problem.charAt(problem.length() - 1);
            char secondToLastChar = problem.charAt(problem.length() - 2);
            if (lastChar == additionSymbol ||
                    lastChar == subtractionSymbol ||
                    lastChar == multiplicationSymbol ||
                    lastChar == divisionSymbol) {
                if (secondToLastChar == additionSymbol ||
                        secondToLastChar == subtractionSymbol ||
                        secondToLastChar == multiplicationSymbol ||
                        secondToLastChar == divisionSymbol) {
                    StringBuilder newEquation = new StringBuilder(problem);
                    newEquation.deleteCharAt(problem.length() - 2);
                    return newEquation.toString();
                }
            }
        }
        return problem;
    }

    // Adds 0 if the number entered is .0 or 0. as an example.
    private String formatZeros(String problem) {
        if (problem.contains(".")) {
            int indexOfDot = problem.lastIndexOf(".");
            StringBuilder newEquation = new StringBuilder(problem);
            if (indexOfDot == 0) {
                newEquation.insert(indexOfDot, '0');
            } else if (indexOfDot == problem.length() - 2) {
                newEquation.insert(problem.length() - 1, '0');
            }
            return newEquation.toString();
        }
        return problem;
    }

    private JPanel createMainPanel(JLabel equationTextField, JLabel resultTextField, JPanel buttonPanel) {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(equationTextField, BorderLayout.PAGE_START);
        mainPanel.add(resultTextField, BorderLayout.PAGE_END);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        return mainPanel;
    }

    private JLabel createEquationTextField() {
        JLabel equationTextField = new JLabel();
        equationTextField.setName("EquationLabel");
        return equationTextField;
    }

    private JLabel createResultTextField() {
        JLabel resultTextField = new JLabel();
        resultTextField.setName("ResultLabel");
        return resultTextField;
    }

    private boolean checkEquation(String problem) {
        if (problem.length() < 3) {
            return false;
        }
        char[] problemAsCharArr = problem.toCharArray();
        if (!Character.isDigit(problemAsCharArr[0])) {
            return false;
        }
        int lastIndex = problemAsCharArr.length - 1;
        if (!Character.isDigit(problemAsCharArr[lastIndex])) {
            return false;
        }
        if (problemAsCharArr[lastIndex - 1] == '\u00F7' && problemAsCharArr[lastIndex] == '0') {
            return false;
        }
        return true;

    }

    private ArrayList<String> infixParse(String data) {
        ArrayList<String> resultArr = new ArrayList<>();
        char[] dataInChar = data.toCharArray();
        StringBuilder tempNumber = new StringBuilder();
        for (char c : dataInChar) {
            if (precedenceOfOperatorSign(String.valueOf(c)) == 0) {
                tempNumber.append(c);
            } else {
                resultArr.add(tempNumber.toString());
                resultArr.add(String.valueOf(c));
                tempNumber = new StringBuilder();
            }
        }
        if (!tempNumber.isEmpty()) {
            resultArr.add(tempNumber.toString());
        }
        return resultArr;
    }

    private ArrayList<String> infixToPostfixConvertor(ArrayList<String> expression) {
        Stack<String> stackStr = new Stack<>();
        ArrayList<String> resultStrArr = new ArrayList<>();
        for (int i = 0; i < expression.size(); i++) {
            String currentStr = expression.get(i);
            int precedenceOperator = precedenceOfOperatorSign(currentStr);
            int precedenceOfStackOperator = stackStr.isEmpty() ? 0 : precedenceOfOperatorSign(stackStr.peek());
            if (precedenceOperator != 0) {
                if (stackStr.isEmpty()) {
                    stackStr.push(currentStr);
                } else if (precedenceOperator > precedenceOfStackOperator) {
                    stackStr.push(currentStr);
                } else if (precedenceOperator < precedenceOfStackOperator) {
                    resultStrArr.add(String.valueOf(stackStr.pop()));
                    i--;
                } else {
                    resultStrArr.add(String.valueOf(stackStr.pop()));
                    stackStr.push(currentStr);
                }
            } else {
                resultStrArr.add(currentStr);
            }
        }
        while (!stackStr.isEmpty()) {
            resultStrArr.add(String.valueOf(stackStr.pop()));
        }
        return resultStrArr;
    }

    private int precedenceOfOperatorSign(String str) {
        switch (str.toCharArray()[0]) {
            case subtractionSymbol, additionSymbol -> {
                return 1;
            }
            case multiplicationSymbol, divisionSymbol -> {
                return 2;
            }
            default -> {
                return 0;
            }
        }
    }

    private String calculatePostfixExpression(ArrayList<String> expression) {
        Stack<Double> stackDouble = new Stack<>();
        for (String currentStr : expression
        ) {
            if (precedenceOfOperatorSign(currentStr) == 0) {
                stackDouble.push(Double.parseDouble(currentStr));
            } else {
                double number1 = stackDouble.pop();
                double number2 = stackDouble.pop();
                double result = 0;
                switch (currentStr.toCharArray()[0]) {
                    case additionSymbol -> result = number2 + number1;
                    case subtractionSymbol -> result = number2 - number1;
                    case multiplicationSymbol -> result = number2 * number1;
                    case divisionSymbol -> result = number2 / number1;
                }
                stackDouble.push(result);
            }
        }
        double resultNumber = stackDouble.pop();
        if (resultNumber % 1 == 0) {
            return String.valueOf((int) resultNumber);
        }
        return String.valueOf(resultNumber);
    }
}
