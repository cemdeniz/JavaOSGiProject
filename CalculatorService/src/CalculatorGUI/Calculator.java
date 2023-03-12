package CalculatorGUI;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.util.Locale;
import java.util.ResourceBundle;
import java.math.BigInteger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import tranlatorservice.service.TranslatorService;

public class Calculator extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// new OSGi Service
	TranslatorService translatorService = new TranslatorService();

	// final variables
	private final BigInteger unDesillion = new BigInteger("1000000000000000000000000000000000000");

	// UI Components
	private JTextField number1Field;
	private JTextField number2Field;
	private JTextField resultField;
	private JButton addButton;
	private JButton subtractButton;
	private JButton multiplyButton;
	private JButton divideButton;
	private JButton clearButton;
	private JLabel number1Label = new JLabel();
	private JLabel number2Label = new JLabel();
	private JLabel resultLabel = new JLabel();
	private JLabel languageLabel = new JLabel();
	private JLabel operationsLabel = new JLabel();

	// global variables
	private String number1;
	private String number2;
	private String result = "";
	private Locale currentLocale;

	public Calculator() {
		// Setting up default UI
		super("Calculator");
		setSize(1107, 215);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new FlowLayout());
		//Default language setting
		currentLocale = new Locale("tr", "TR");
		createUI();
	}

	private void createUI() {
		// Language selection
		languageLabel = new JLabel(getLocalizedString(currentLocale, "language"));
		add(languageLabel);

		JButton enButton = new JButton("EN");
		enButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// currentLocale = Locale.ENGLISH; This doesn't work for other computers
				currentLocale = new Locale("en", "US");
				updateUI(currentLocale);
			}
		});
		add(enButton);

		JButton trButton = new JButton("TR");
		trButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currentLocale = new Locale("tr", "TR");
				updateUI(currentLocale);
			}
		});
		add(trButton);

		// Input fields
		number1Label = new JLabel(getLocalizedString(currentLocale, "number1"));
		add(number1Label);

		number1Field = new JTextField(15);
		add(number1Field);

		number2Label = new JLabel(getLocalizedString(currentLocale, "number2"));
		add(number2Label);

		number2Field = new JTextField(15);
		add(number2Field);

		// Result field
		resultLabel = new JLabel(getLocalizedString(currentLocale, "result"));
		add(resultLabel);

		resultField = new JTextField(30);
		resultField.setEditable(false);
		add(resultField);

		operationsLabel = new JLabel(getLocalizedString(currentLocale, "operations"));
		add(operationsLabel);

		// Operation buttons
		addButton = new JButton(getLocalizedString(currentLocale, "addition"));
		addButton.addActionListener(this);
		add(addButton);

		subtractButton = new JButton(getLocalizedString(currentLocale, "subtraction"));
		subtractButton.addActionListener(this);
		add(subtractButton);

		multiplyButton = new JButton(getLocalizedString(currentLocale, "multiply"));
		multiplyButton.addActionListener(this);
		add(multiplyButton);

		divideButton = new JButton(getLocalizedString(currentLocale, "divide"));
		divideButton.addActionListener(this);
		add(divideButton);

		clearButton = new JButton(getLocalizedString(currentLocale, "clear"));
		clearButton.addActionListener(this);
		add(clearButton);

	}

	private String getLocalizedString(Locale currentLocale, String key) {
		// Getting the title,button and field names from /resources/.properties file
		return ResourceBundle.getBundle("Calculator", currentLocale).getString(key);
	}

	private void updateUI(Locale currentLocale) {
		// updating Fields
		number1Field.setText("");
		number2Field.setText("");
		resultField.setText("");

		// Updating UI when language button clicked
		setTitle(getLocalizedString(currentLocale, "title"));
		languageLabel.setText(getLocalizedString(currentLocale, "language"));
		number1Label.setText(getLocalizedString(currentLocale, "number1"));
		number2Label.setText(getLocalizedString(currentLocale, "number2"));
		resultLabel.setText(getLocalizedString(currentLocale, "result"));
		operationsLabel.setText(getLocalizedString(currentLocale, "operations"));
		addButton.setText(getLocalizedString(currentLocale, "addition"));
		subtractButton.setText(getLocalizedString(currentLocale, "subtraction"));
		multiplyButton.setText(getLocalizedString(currentLocale, "multiply"));
		divideButton.setText(getLocalizedString(currentLocale, "divide"));
		clearButton.setText(getLocalizedString(currentLocale, "clear"));
	}

	private String getNumber1(Locale currentLocale) {
		// Checks if the first input string is in our HashMaps with current locale, (if true) returns the
		// String

		String number1 = number1Field.getText().toLowerCase();
		if (!isNull(number1) && !isEmpty(number1)) {
			String[] wordArray = number1.split(" ");

			for (String word : wordArray) {
				if ((currentLocale.getLanguage().equals("tr")
						&& !translatorService.validateNumber(word, currentLocale.getLanguage())
						&& !(word.equals("eksi")))) {

					number1 = null;

				} else if ((currentLocale.getLanguage().equals("en")
						&& !translatorService.validateNumber(word, currentLocale.getLanguage())
						&& !(word.equals("minus")))) {

					number1 = null;
				}
			}
		} else {
			number1 = null;
		}

		return number1;

	}

	private String getNumber2(Locale currentLocale) {
		// Checks if the second input string is in our HashMaps with current locale, (if true) returns the
		// String
		String number2 = number2Field.getText().toLowerCase();
		if (!isNull(number2) && !isEmpty(number2)) {
			String[] word2Array = number2.split(" ");

			for (String word2 : word2Array) {
				if ((currentLocale.getLanguage().equals("tr")
						&& !translatorService.validateNumber(word2, currentLocale.getLanguage())
						&& !(word2.equals("eksi")))) {

					number2 = null;

				} else if ((currentLocale.getLanguage().equals("en")
						&& !translatorService.validateNumber(word2, currentLocale.getLanguage())
						&& !(word2.equals("minus")))) {

					number2 = null;

				}
			}
		} else {
			number2 = null;
		}

		return number2;
	}

	private void setResult(String result) {

		// Setting the result in UI,
		// with replaceAll erasing extra " " (space) between words.
		resultField.setText(eraseElement(result).replaceAll("\\s+", " "));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// 4 operation button listener

		String actionCommand = e.getActionCommand();
		BigInteger num1 = new BigInteger("0");
		BigInteger num2 = new BigInteger("0");
		BigInteger mathResult = new BigInteger("0");
		number1 = getNumber1(currentLocale);
		number2 = getNumber2(currentLocale);

		switch (actionCommand) {
		case "Addition":
		case "Topla":

			// addition method
			additionTopla(number1, number2, num1, num2, mathResult);
			break;

		case "Subtraction":
		case "Çıkar":

			// subtraction method
			subtractionCikar(number1, number2, num1, num2, mathResult);
			break;

		case "Multiplication":
		case "Çarp":

			// multiply method
			multiplyCarp(number1, number2, num1, num2, mathResult);
			break;

		case "Division":
		case "Böl":

			// division method
			divisionBol(number1, number2, num1, num2, mathResult);
			break;
		case "Clear":
		case "Temizle":

			// Clearing inputs
			result = "";
			updateUI(currentLocale);
			break;

		}
		setResult(result);
	}

	private void showWarning(Locale currentLocale) {
		// Clear result
		result = "";
		setResult(result);
		// Throws a warning
		if (currentLocale.getLanguage().equals("tr")) {
			JOptionPane.showMessageDialog(null,
					"-Alanlara boş veya yanlış değer girmeyiniz\n" + "-Sayılar yazı şeklinde girilmeli.\n"
							+ "-Sonuç Undesilyondan büyük olamaz.\n" + "-Girdiğiniz yazı dil ile uyumlu olmalı.\n",
					"Hata Aldınız!", JOptionPane.ERROR_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(null,
					"Please don't leave number fields empty.\n" + "Please enter numbers in String form.\n"
							+ "Result can't be higher than Undesillion\n" + "Your entry should match with language.\n",
					"Error Message!", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void additionTopla(String number1, String number2, BigInteger num1, BigInteger num2,
			BigInteger mathResult) {
		// Calls translatorService.wordToNumber for converting the words into numbers to
		// do math operation.
		// After operation calls translatorService.numberToWord to convert result into
		// words to show result.
		if (!isNull(number1)) {
			num1 = translatorService.wordToNumber(number1, currentLocale.getLanguage());
		}

		if (!isNull(number2)) {
			num2 = translatorService.wordToNumber(number2, currentLocale.getLanguage());
		}
		if (!isNull(number1) && !isNull(number2)) {

			// calculating the math operation
			mathResult = num1.add(num2);
			// If result reaches to unDecillion(10^36) throw error
			if (mathResult.compareTo(unDesillion) < 0) {
				// sending result to return as Words
				result = translatorService.numberToWord(mathResult, currentLocale.getLanguage());
			} else {

				showWarning(currentLocale);
			}

		} else {

			showWarning(currentLocale);
		}

	}

	private void subtractionCikar(String number1, String number2, BigInteger num1, BigInteger num2,
			BigInteger mathResult) {

		if (!isNull(number1)) {
			num1 = translatorService.wordToNumber(number1, currentLocale.getLanguage());
		}
		if (!isNull(number2)) {
			num2 = translatorService.wordToNumber(number2, currentLocale.getLanguage());
		}
		if (!isNull(number1) && !isNull(number2)) {

			// calculating the math operation
			mathResult = num1.subtract(num2);
			// If result reaches to unDecillion(10^36) throw error
			if (mathResult.compareTo(unDesillion) < 0) {
				// sending result to return as Words
				result = translatorService.numberToWord(mathResult, currentLocale.getLanguage());
			} else {
				showWarning(currentLocale);
			}
		} else {

			showWarning(currentLocale);
		}
	}

	private void multiplyCarp(String number1, String number2, BigInteger num1, BigInteger num2, BigInteger mathResult) {

		if (!isNull(number1)) {
			num1 = translatorService.wordToNumber(number1, currentLocale.getLanguage());
		}
		if (!isNull(number2)) {
			num2 = translatorService.wordToNumber(number2, currentLocale.getLanguage());
		}
		if (!isNull(number1) && !isNull(number2)) {
			// calculating the math operation
			mathResult = num1.multiply(num2);
			// If result reaches to unDecillion(10^36) throw error
			if (mathResult.compareTo(unDesillion) < 0) {
				// sending result to return as Words
				result = translatorService.numberToWord(mathResult, currentLocale.getLanguage());
			} else {
				showWarning(currentLocale);
			}

		} else {
			showWarning(currentLocale);
		}

	}

	private void divisionBol(String number1, String number2, BigInteger num1, BigInteger num2, BigInteger mathResult) {
		if (!isNull(number1)) {
			num1 = translatorService.wordToNumber(number1, currentLocale.getLanguage());
		}
		if (!isNull(number2)) {
			num2 = translatorService.wordToNumber(number2, currentLocale.getLanguage());
		}

		if (!isNull(number1) && !isNull(number2)) {
			// If num2 is 0 in division, result is undefined: number/0 = "undefined"
			if (!isEmpty(isUndefined(num2, currentLocale))) {
				result = isUndefined(num2, currentLocale);
			} else if (!isNull(number1) && !isNull(number2) && isEmpty(isUndefined(num2, currentLocale))) {
				// calculating the math operation
				mathResult = num1.divide(num2);

				// If result reaches to unDecillion(10^36) throw error
				if (mathResult.compareTo(unDesillion) < 0) {
					// sending result to return as Words
					result = translatorService.numberToWord(mathResult, currentLocale.getLanguage());
				} else {
					showWarning(currentLocale);
				}
			} else {
				showWarning(currentLocale);
			}

		} else {
			showWarning(currentLocale);
		}

	}

	private boolean isNull(String number) {
		// cheking if the number is null
		return number == null ? true : false;
	}

	private boolean isEmpty(String number) {
		// cheking if the number is empty
		return number.equals("") ? true : false;
	}

	private String isUndefined(BigInteger number, Locale currentLocale) {
		// cheking if the number is undefined
		String result = "";
		if (number.compareTo(BigInteger.ZERO) == 0) {
			result = currentLocale.getLanguage().equals("tr") ? "Tanımsız" : "Undefined";
		}
		return result;
	}

	private String eraseElement(String result) {
		// Removing the "bir" right before "yüz". This way TR result is more readable
		// for some cases.
		StringBuilder words = new StringBuilder();

		int i = 0;
		String[] resultArray = result.split(" ");
		for (String word : resultArray) {
			if (resultArray[i].equals("bir") && i != (resultArray.length - 1) && resultArray[i + 1].equals("yüz")) {

				// if this condition true we replace "bir" with ""
				resultArray[i] = "";
				// words.append(" ");

			} else {
				words.append(resultArray[i]);
				words.append(" ");
			}

			i++;
		}

		return words.toString();

	}

}
