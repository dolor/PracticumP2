package week3.stem;

import java.awt.Container;
import java.util.Map;
import java.util.Observable;

import javax.swing.*;

public class UitslagJFrame extends JFrame implements java.util.Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9049956254475106839L;
	JTextArea textField;

	public UitslagJFrame(Uitslag uitslag) {
		System.out.println("Initialized!");
		uitslag.addObserver(this);
		this.init();
	}

	public void init() {
		this.setBounds(10, 250, 300, 250);
		//this.setSize(300, 250);
		Container c = getContentPane();
		textField = new JTextArea();
		textField.setBounds(0, 0, 300, 250);
		c.add(textField);
	}

	@Override
	public void update(Observable o, Object arg) {
		@SuppressWarnings("unchecked")
		Map<String, Integer> stemmen = (Map<String, Integer>) arg;
		clearTextField();
		for (Map.Entry<String, Integer> e : stemmen.entrySet()) {
			String line = String.format("%-15s%d", e.getKey(), e.getValue());
			System.out.println(line);
			addLineToTextField(line);
		}
	}

	public void clearTextField() {
		textField.setText("");
	}

	public void addLineToTextField(String line) {
		String oldText = textField.getText();
		textField.setText(oldText + "\n" + line);
	}
}
