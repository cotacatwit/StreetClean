package streetCleaning;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

	private static final SimpleDateFormat FORMAT = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	
	public static Date parseDate(String date) {
		try {
			return FORMAT.parse(date);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static String ordinal(int i) {
	    int mod100 = i % 100;
	    int mod10 = i % 10;
	    if(mod10 == 1 && mod100 != 11) {
	        return i + "st";
	    } else if(mod10 == 2 && mod100 != 12) {
	        return i + "nd";
	    } else if(mod10 == 3 && mod100 != 13) {
	        return i + "rd";
	    } else {
	        return i + "th";
	    }
	}
}
