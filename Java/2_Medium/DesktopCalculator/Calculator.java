package calculator;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator extends JFrame {
    private String problem;
    private String answer;
    private final String[][] BUTTON_TEXTS = {
            {"7", "8", "9", "+"},
            {"4", "5", "6", "-"},
            {"1", "2", "3", "x"},
            {"0", ".", "/", "="},
    };
    private final String[] BUTTON_NAMES = {
            "Seven", "Eight", "Nine", "Add",
            "Four", "Five", "Six", "Subtract",
            "One", "Two", "Three", "Multiply",
            "Zero", "Point", "Divide", "Equals"
    };

    public Calculator() {
        JTextField equationTextField = createEquationTextField();
        JPanel buttonPanel = createButtons(equationTextField);
        JPanel mainPanel = createMainPanel(equationTextField, buttonPanel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Calculator");
        getContentPane().add(mainPanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createButtons(JTextField textField) {
        JPanel buttonPanel = new JPanel(new GridLayout(BUTTON_TEXTS.length, BUTTON_TEXTS[0].length));
        int buttonCounter = 0;
        for (int row = 0; row < BUTTON_TEXTS.length; row++) {
            for (int column = 0; column < BUTTON_TEXTS[row].length; column++) {
                JButton button = new JButton(BUTTON_TEXTS[row][column]);
                button.setName(BUTTON_NAMES[buttonCounter]);

                // Add action listener to each button
                // If button is "=", execute the calculate function
                if (BUTTON_TEXTS[row][column].equals("=")) {
                    button.addActionListener(e -> {
                        problem = textField.getText();
                        answer = calculate(problem);
                        textField.setText(answer);
                    });
                // Otherwise print the value of the button to the text instead
                } else {
                    button.addActionListener(e -> {
                        JButton clickedButton = (JButton) e.getSource();
                        String buttonText = clickedButton.getText();
                        String currentEquation = textField.getText();
                        textField.setText(currentEquation + buttonText);
                    });
                }
                buttonPanel.add(button);
                buttonCounter++;
            }
        }
        return buttonPanel;
    }

    private JPanel createMainPanel(JTextField field, JPanel buttonPanel) {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(field, BorderLayout.PAGE_START);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        return mainPanel;
    }

    private JTextField createEquationTextField() {
        JTextField textField = new JTextField(10);
        textField.setName("EquationTextField");
        return textField;
    }

    public String calculate(String problem) {
        String[] parts = problem.split("[+\\-x/]");

        // Parse operands
        int operand1 = Integer.parseInt(parts[0]);
        int operand2 = Integer.parseInt(parts[1]);

        // Find the operator
        char operator = problem.charAt(parts[0].length());

        int result;
        switch (operator) {
            case '+':
                result = operand1 + operand2;
                break;
            case '-':
                result = operand1 - operand2;
                break;
            case 'x':
                result = operand1 * operand2;
                break;
            case '/':
                result = operand1 / operand2;
                break;
            default:
                return "Error: Invalid operator";
        }
        return problem + "=" + result;
    }

}
