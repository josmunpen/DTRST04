package utilities;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang.math.RandomUtils;

public class TickerGenerator {
	public static String generateTicker(){
	List<String> alpha = new ArrayList<String>();
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
	
	List<String> nums=new ArrayList<String>();
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
	
	int day1=Calendar.getInstance().getTime().getDate();
	String day=String.valueOf(day1);
	int year1=Calendar.getInstance().getTime().getYear();
	String year=String.valueOf(year1);
	int month1=Calendar.getInstance().getTime().getMonth();
	String month=String.valueOf(month1);
	
	String ticker="";
	ticker =year.substring(1);
	ticker =ticker + month;
	ticker = ticker + day;
	ticker=ticker + "-";
	for(Integer i=0;i<6;i++){
		Integer selector=new Random().nextInt(26);
	if(selector>14){
		Integer letra=new Random().nextInt(26);
		String a=alpha.get(letra);
		ticker=ticker + a;
	}else{
		Integer numero=new Random().nextInt(9);
		String b=nums.get(numero);
		ticker= ticker + b;
	}
	}
	return ticker;
	}
	
	public static void main(String[] args) {
		System.out.println(TickerGenerator.generateTicker());

	}
	}