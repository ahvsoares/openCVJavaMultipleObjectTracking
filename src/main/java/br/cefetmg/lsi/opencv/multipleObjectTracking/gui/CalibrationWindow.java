package br.cefetmg.lsi.opencv.multipleObjectTracking.gui;

import java.awt.EventQueue;

import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.MaskFormatter;

public class CalibrationWindow implements ChangeListener, DocumentListener {

	private JFrame frame;
	private JTextField txtMinSValue;
	private JTextField txtMaxHValue;
	private JTextField txtMaxSValue;
	private JTextField txtMinVValue;
	private JTextField txtMaxVValue;
	private JTextField txtMinHValue;
	private JSlider sliderMinHValue;
	private JSlider sliderMaxHValue;
	private JSlider sliderMinSValue;
	private JSlider sliderMaxSValue;
	private JSlider sliderMinVValue;
	private JSlider sliderMaxVValue;
	private boolean changingTxtField;
	private boolean changingSlider;

	public static final int MIN_HSV_VALUES = 0;
	public static final int MAX_HSV_VALUES = 256;
	
	private int minHValue;
	private int maxHValue;
	private int minSValue;
	private int maxSValue;
	private int minVValue;
	private int maxVValue;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CalibrationWindow window = new CalibrationWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CalibrationWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 506, 201);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblMinimunHueValue = new JLabel("Minimum \"hue\" value:");
		lblMinimunHueValue.setBounds(12, 12, 212, 15);
		frame.getContentPane().add(lblMinimunHueValue);

		JLabel lblMaximumHueValue = new JLabel("Maximum \"hue\" value:");
		lblMaximumHueValue.setBounds(12, 39, 212, 15);
		frame.getContentPane().add(lblMaximumHueValue);

		JLabel lblMinimumSaturationValue = new JLabel(
				"Minimum \"saturation\" value:");
		lblMinimumSaturationValue.setBounds(12, 66, 212, 15);
		frame.getContentPane().add(lblMinimumSaturationValue);

		JLabel lblMaximumSaturationValue = new JLabel(
				"Maximum \"saturation\" value:");
		lblMaximumSaturationValue.setBounds(12, 93, 212, 15);
		frame.getContentPane().add(lblMaximumSaturationValue);

		JLabel lblMinimumvalueValue = new JLabel("Minimum \"value\" value:");
		lblMinimumvalueValue.setBounds(12, 120, 212, 15);
		frame.getContentPane().add(lblMinimumvalueValue);

		JLabel lblMaximumvalueValue = new JLabel("Maximum \"value\" value:");
		lblMaximumvalueValue.setBounds(12, 147, 212, 15);
		frame.getContentPane().add(lblMaximumvalueValue);

		sliderMinHValue = createSlider();
		sliderMinHValue.setValue(MIN_HSV_VALUES);
		sliderMinHValue.setBounds(222, 10, 200, 16);
		frame.getContentPane().add(sliderMinHValue);

		txtMinHValue = createTextField();
		txtMinHValue.setText(Integer.toString(MIN_HSV_VALUES));
		txtMinHValue.setBounds(434, 8, 44, 19);
		frame.getContentPane().add(txtMinHValue);

		sliderMaxHValue = createSlider();
		sliderMaxHValue.setValue(MAX_HSV_VALUES);
		sliderMaxHValue.setBounds(222, 37, 200, 16);
		frame.getContentPane().add(sliderMaxHValue);

		txtMaxHValue = createTextField();
		txtMaxHValue.setText(Integer.toString(MAX_HSV_VALUES));
		txtMaxHValue.setBounds(434, 35, 44, 19);
		frame.getContentPane().add(txtMaxHValue);

		sliderMinSValue = createSlider();
		sliderMinSValue.setValue(MIN_HSV_VALUES);
		sliderMinSValue.setBounds(222, 66, 200, 16);
		frame.getContentPane().add(sliderMinSValue);

		txtMinSValue = createTextField();
		txtMinSValue.setText(Integer.toString(MIN_HSV_VALUES));
		txtMinSValue.setBounds(434, 64, 44, 19);
		frame.getContentPane().add(txtMinSValue);

		sliderMaxSValue = createSlider();
		sliderMaxSValue.setValue(MAX_HSV_VALUES);
		sliderMaxSValue.setBounds(222, 95, 200, 16);
		frame.getContentPane().add(sliderMaxSValue);

		txtMaxSValue = createTextField();
		txtMaxSValue.setText(Integer.toString(MAX_HSV_VALUES));
		txtMaxSValue.setBounds(434, 93, 44, 19);
		frame.getContentPane().add(txtMaxSValue);

		sliderMinVValue = createSlider();
		sliderMinVValue.setValue(MIN_HSV_VALUES);
		sliderMinVValue.setBounds(222, 118, 200, 16);
		frame.getContentPane().add(sliderMinVValue);

		txtMinVValue = createTextField();
		txtMinVValue.setText(Integer.toString(MIN_HSV_VALUES));
		txtMinVValue.setBounds(434, 116, 44, 19);
		frame.getContentPane().add(txtMinVValue);

		sliderMaxVValue = createSlider();
		sliderMaxVValue.setValue(MAX_HSV_VALUES);
		sliderMaxVValue.setBounds(222, 145, 200, 16);
		frame.getContentPane().add(sliderMaxVValue);

		txtMaxVValue = createTextField();
		txtMaxVValue.setText(Integer.toString(MAX_HSV_VALUES));
		txtMaxVValue.setBounds(434, 143, 44, 19);
		frame.getContentPane().add(txtMaxVValue);
	}

	public void stateChanged(ChangeEvent e) {
		changingSlider = true;

		if (!changingTxtField) {
			JSlider source = (JSlider) e.getSource();
			int valueInt = source.getValue();
			String value = Integer.toString(valueInt);

			if ((txtMinHValue != null) && (source == sliderMinHValue)) {
				minHValue = valueInt;
				txtMinHValue.setText(value);
			} else {

				if ((txtMaxHValue != null) && (source == sliderMaxHValue)) {
					maxHValue = valueInt;
					txtMaxHValue.setText(value);
				} else {

					if ((txtMinSValue != null) && (source == sliderMinSValue)) {
						minSValue = valueInt;
						txtMinSValue.setText(value);
					} else {

						if ((txtMaxSValue != null) && (source == sliderMaxSValue)) {
							maxSValue = valueInt;
							txtMaxSValue.setText(value);
						} else {

							if ((txtMinVValue != null) && (source == sliderMinVValue)) {
								minVValue = valueInt;
								txtMinVValue.setText(value);
							} else {

								if ((txtMaxVValue != null) && (source == sliderMaxVValue)) {
									maxVValue = valueInt;
									txtMaxVValue.setText(value);
								}

							}

						}

					}

				}

			}

		}

		changingSlider = false;
	}

	public void insertUpdate(DocumentEvent e) {
		updateSliders();
	}

	public void removeUpdate(DocumentEvent e) {
		updateSliders();
	}

	public void changedUpdate(DocumentEvent e) {
		updateSliders();
	}

	private void updateSliders() {
		changingTxtField = true;

		if (!changingSlider) {

			if ((txtMinHValue != null) && (!txtMinHValue.getText().isEmpty())) {
				minHValue = Integer.parseInt(txtMinHValue.getText().trim());
				sliderMinHValue.setValue(minHValue);
			}

			if ((txtMaxHValue != null) && (!txtMaxHValue.getText().isEmpty())) {
				maxHValue = Integer.parseInt(txtMaxHValue.getText().trim());
				sliderMaxHValue.setValue(maxHValue);
			}

			if ((txtMinSValue != null) && (!txtMinSValue.getText().isEmpty())) {
				minSValue = Integer.parseInt(txtMinSValue.getText().trim());
				sliderMinSValue.setValue(minSValue);
			}

			if ((txtMaxSValue != null) && (!txtMaxSValue.getText().isEmpty())) {
				maxSValue = Integer.parseInt(txtMaxSValue.getText().trim());
				sliderMaxSValue.setValue(maxSValue);
			}

			if ((txtMinVValue != null) && (!txtMinVValue.getText().isEmpty())) {
				minVValue = Integer.parseInt(txtMinVValue.getText().trim());
				sliderMinVValue.setValue(minVValue);
			}

			if ((txtMaxVValue != null) && (!txtMaxVValue.getText().isEmpty())) {
				maxVValue = Integer.parseInt(txtMaxVValue.getText().trim());
				sliderMaxVValue.setValue(maxVValue);
			}

		}

		changingTxtField = false;
	}

	private JSlider createSlider() {
		JSlider slider = new JSlider();
		slider.setMinimum(MIN_HSV_VALUES);
		slider.setMaximum(MAX_HSV_VALUES);
		slider.setMinorTickSpacing(1);
		slider.setMajorTickSpacing(1);
		slider.addChangeListener(this);

		return slider;
	}

	private JTextField createTextField() {
		JTextField textField = creatLimitedJTextField(3, "0,1,2,3,4,5,6,7,8,9")/*new JTextField()*/;
		textField.setColumns(10);
		textField.getDocument().addDocumentListener(this);

		return textField;

	}

	private JTextField creatLimitedJTextField(int tamanho, String caracteres) {

		try {
			// defino a variável que vai guardar a quantidade de caracteres
			String quantidade = "";

			// defino um método de repetição para repetir o numero de vezes do tamanho
			for (int i = 0; i < tamanho; i++) {
				// defino asterisco para aceitar qualquer coisa e crio a máscara
				quantidade = quantidade + "*";
			}

			// **********... de acordo com o tamanho informado
			// defino que a mascara possui essa
			// quantidade de elementos que foi informado em tamanho e
			// foi colocada com * dentro de quantidade
			MaskFormatter nome = new MaskFormatter(quantidade);

			// defino que o parâmetro caracter recebido pelo
			// método contém os caracteres válidos
			nome.setValidCharacters(caracteres);

			// retorno a mascara que foi criada
			return new JFormattedTextField(nome);
		} catch (Exception e) {
			// mensagem se acontecer erro
			JOptionPane.showMessageDialog(null, "Ocorreu um erro");

			// retorno um campo de texto comum
			return new JTextField();
		}// fim do catch

	}
	
	public JFrame getFrame() {
		return frame;
	}

	public int getMinHValue() {
		return minHValue;
	}

	public int getMaxHValue() {
		return maxHValue;
	}

	public int getMinSValue() {
		return minSValue;
	}

	public int getMaxSValue() {
		return maxSValue;
	}

	public int getMinVValue() {
		return minVValue;
	}

	public int getMaxVValue() {
		return maxVValue;
	}
}
