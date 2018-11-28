
package utilities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class TickerGenerator {

	public static String generateTicker() {
		final List<String> alpha = new ArrayList<String>();
		alpha.add("A");
		alpha.add("B");
		alpha.add("C");
		alpha.add("D");
		alpha.add("E");
		alpha.add("F");
		alpha.add("G");
		alpha.add("H");
		alpha.add("I");
		alpha.add("J");
		alpha.add("K");
		alpha.add("L");
		alpha.add("M");
		alpha.add("N");
		alpha.add("O");
		alpha.add("P");
		alpha.add("Q");
		alpha.add("R");
		alpha.add("S");
		alpha.add("T");
		alpha.add("U");
		alpha.add("V");
		alpha.add("W");
		alpha.add("X");
		alpha.add("Y");
		alpha.add("Z");

		final List<String> nums = new ArrayList<String>();
		nums.add("0");
		nums.add("1");
		nums.add("2");
		nums.add("3");
		nums.add("4");
		nums.add("5");
		nums.add("6");
		nums.add("7");
		nums.add("8");
		nums.add("9");

		final int day1 = Calendar.getInstance().getTime().getDate();
		final String day = String.valueOf(day1);
		final int year1 = Calendar.getInstance().getTime().getYear();
		final String year = String.valueOf(year1);
		final int month1 = Calendar.getInstance().getTime().getMonth();
		final String month = String.valueOf(month1);

		String ticker = "";
		ticker = year.substring(1);
		ticker = ticker + month;
		ticker = ticker + day;
		ticker = ticker + "-";
		for (Integer i = 0; i < 6; i++) {
			final Integer selector = new Random().nextInt(26);
			if (selector > 14) {
				final Integer letra = new Random().nextInt(26);
				final String a = alpha.get(letra);
				ticker = ticker + a;
			} else {
				final Integer numero = new Random().nextInt(9);
				final String b = nums.get(numero);
				ticker = ticker + b;
			}
		}
		return ticker;
	}

	public static void main(final String[] args) {
		System.out.println(TickerGenerator.generateTicker());

	}
}
