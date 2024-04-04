package calculator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator extends JFrame {
    private String problem;
    private String answer;

    public Calculator() {
        initialization();

        // Adding Text Field
        JTextField equationTextField = getJTextField();
        add(equationTextField);

        // Adding JButton
        JButton solveButton = getButton(equationTextField);
        add(solveButton);

        setVisible(true);
    }

    private void initialization() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 400);
        setLayout(null);
        setVisible(true);
        setTitle("Calculator");
    }

    private JTextField getJTextField() {
        JTextField equationTextField = new JTextField();
        equationTextField.setBounds(50, 50, 200, 30); // Adjust the bounds as needed
        equationTextField.setName("EquationTextField");
        return equationTextField;
    }

    private JButton getButton(JTextField equationTextField) {
        JButton solveButton = new JButton("Solve");
        solveButton.setBounds(100, 100, 100, 30); // Adjust the bounds as needed
        solveButton.setName("Solve");

        solveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                problem = equationTextField.getText();
                answer = calculate(problem);
                equationTextField.setText(answer);
            }
        });
        return solveButton;
    }

    public String calculate(String problem) {
        String[] parts = problem.split("[+\\-*/]");

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
            case '*':
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
