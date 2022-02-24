package Proiect;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * 
 * CustomDate class utilized for handling variables of type Date
 * @author Nevezi-Strango David
 */
public class CustomDate implements Comparator{
	int day;
	int month;
	int year;
	private static final Map<String, Integer> monthStringToIntegerMap;
	static {
		Map<String, Integer> mstim = new HashMap<String, Integer>();
		mstim.put("january", 1);
		mstim.put("february", 2);
		mstim.put("march", 3);
		mstim.put("april", 4);
		mstim.put("may", 5);
		mstim.put("june", 6);
		mstim.put("july", 7);
		mstim.put("august", 8);
		mstim.put("september", 9);
		mstim.put("october", 10);
		mstim.put("november", 11);
		mstim.put("december", 12);
		monthStringToIntegerMap = Collections.unmodifiableMap(mstim);
	}
	/**
	 * Constructor of CustomDate
	 * @param date String form of the date
	 * @throws TaskException if invalid date has been typed in
	 */
	public CustomDate(String date) throws TaskException {
		if(date.matches("(0?[1-9]|[1-2][0-9]|3[01])/(1[0-2]|0?[0-9])/[12][0-9]{3}")) {
			StringTokenizer st = new StringTokenizer(date, "/");
			this.day = Integer.parseInt(st.nextToken());
			this.month = Integer.parseInt(st.nextToken());
			this.year = Integer.parseInt(st.nextToken());
		} else if (date.matches("([0-2][0-9]|3[01]) (January|February|March|April|May|June|July|August|September|October|November|December) [12][0-9]{3}")) {
			StringTokenizer st = new StringTokenizer(date, " ");
			this.day = Integer.parseInt(st.nextToken());
			String month = st.nextToken().toLowerCase();
			this.month = CustomDate.monthStringToIntegerMap.get(month);
			this.year = Integer.parseInt(st.nextToken());
		} else {
			//this.day = 1;
			//this.month = 1;
			//this.year = 1970;
			throw new TaskException(TaskException.INVALID_DEADLINE);
		}
	}
	/**
	 * Function that using the given format will transform the date into String
	 * @param fmt the wished format
	 * @return the date in String
	 */
	public String format(String fmt) {
		fmt = fmt.replaceAll("dd", String.valueOf(this.day));
		fmt = fmt.replaceAll("mm", String.valueOf(this.month));
		fmt = fmt.replaceAll("yyyy", String.valueOf(this.year));
		return fmt;
	}
	
	@Override
	public int compare(Object o1, Object o2) {
		CustomDate c1 = (CustomDate) o1;
		CustomDate c2 = (CustomDate) o2;
		if(c1.year - c2.year != 0) {
			return c1.year - c2.year;
		} else if(c1.month - c2.month != 0) {
			return c1.month - c2.month;
		} else {
			return c1.day - c2.day;
		}
	}
}
