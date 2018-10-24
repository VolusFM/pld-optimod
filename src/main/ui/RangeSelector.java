package main.ui;

import java.util.stream.IntStream;

import javax.swing.JOptionPane;

/**
 * Helper class to encapsulate a modal integer selector between a given range
 */
public abstract class RangeSelector {

	public static int getIntegerInRange(int min, int max) {
		return (Integer) JOptionPane.showInputDialog(null, "Please select the delivery men count", "Range selector", JOptionPane.PLAIN_MESSAGE, null,  IntStream.rangeClosed(min, max).boxed().toArray(), 1);
	}

}
