import javax.swing.*;
import java.awt.*;

public class Main {
    private static JTextField display;
    private static String firstNumber = "";
    private static String operator = "";
    private static String secondNumber = "";

    public static void main(String[] args) {
        JFrame frame = new JFrame("Raiko Calculator");
        frame.setSize(350, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(30, 30, 30));

        display = new JTextField();
        display.setEditable(false);
        display.setFont(new Font("Arial", Font.BOLD, 40));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setBackground(new Color(30, 30, 30));
        display.setForeground(Color.WHITE);
        display.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.add(display, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(5, 4, 5, 5));
        buttonPanel.setBackground(new Color(58, 58, 58));
        String[] buttons = {
                "C", "B", "%", "÷",
                "7", "8", "9", "×",
                "4", "5", "6", "-",
                "1", "2", "3", "+",
                "0", ".", "=", ""
        };

        for (String text : buttons) {
            if (text.isEmpty()) {
                buttonPanel.add(new JLabel());
                continue;
            }

            JButton btn = new JButton(text);
            btn.setFont(new Font("Arial", Font.BOLD, 22));
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);

            if (text.equals("C")) btn.setBackground(new Color(192, 57, 43));
            else if ("+-×÷".contains(text)) btn.setBackground(new Color(243, 156, 18));
            else if (text.equals("=")) btn.setBackground(new Color(39, 174, 96));
            else btn.setBackground(new Color(51, 51, 51));

            btn.addActionListener(e -> handleInput(text));
            buttonPanel.add(btn);
        }

        frame.add(buttonPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private static void handleInput(String input) {
        if (input.equals("C")) {
            firstNumber = "";
            secondNumber = "";
            operator = "";
            display.setText("");
        } else if (input.equals("B")) {
            // Handle Backspace
            if (!secondNumber.isEmpty()) {
                secondNumber = secondNumber.substring(0, secondNumber.length() - 1);
            } else if (!operator.isEmpty()) {
                operator = "";
            } else if (!firstNumber.isEmpty()) {
                firstNumber = firstNumber.substring(0, firstNumber.length() - 1);
            }
            display.setText(firstNumber + " " + operator + " " + secondNumber);
        } else if (input.equals("%")) {
            // Handle Percentage
            if (!secondNumber.isEmpty()) {
                try {
                    double num = Double.parseDouble(secondNumber) / 100;
                    secondNumber = String.valueOf(num);
                } catch (Exception e) {
                    display.setText("Error");
                }
            } else if (!firstNumber.isEmpty()) {
                try {
                    double num = Double.parseDouble(firstNumber) / 100;
                    firstNumber = String.valueOf(num);
                } catch (Exception e) {
                    display.setText("Error");
                }
            }
            display.setText(firstNumber + " " + operator + " " + secondNumber);
        } else if (input.equals("=")) {
            try {
                double num1 = Double.parseDouble(firstNumber);
                double num2 = Double.parseDouble(secondNumber);
                double result = 0;

                if (operator.equals("+")) result = num1 + num2;
                else if (operator.equals("-")) result = num1 - num2;
                else if (operator.equals("×")) result = num1 * num2;
                else if (operator.equals("÷")) result = num2 != 0 ? num1 / num2 : 0;

                display.setText("" + result);
                firstNumber = "" + result;
                secondNumber = "";
                operator = "";
            } catch (Exception e) {
                display.setText("Error");
            }
        } else if ("+-×÷".contains(input)) {
            if (!firstNumber.isEmpty()) {
                operator = input;
                display.setText(firstNumber + " " + operator + " ");
            }
        } else {
            if (operator.isEmpty()) firstNumber += input;
            else secondNumber += input;

            display.setText(firstNumber + " " + operator + " " + secondNumber);
        }
    }
}
