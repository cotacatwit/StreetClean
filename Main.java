package streetCleaning;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Main {
	
	public static void main(String[] args) {
		
		/* Get current date */
		Date now = new Date();
		
		/* Simulate date for testing purposes */
		//now = Utils.parseDate("08/04/2019 00:00:00");
		
		Calendar calendar = Calendar.getInstance();	
		calendar.setTime(now);
		
		/* Check that its between April and November */
		int monthNumber = calendar.get(Calendar.MONTH);
		if (monthNumber < Calendar.APRIL || monthNumber > Calendar.NOVEMBER) {
			System.out.println("Month is not between April and November...");
			return;
		}
		
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);	
		boolean isSunday = (dayOfWeek == Calendar.SUNDAY);
		
		/* Not a Sunday, so exit */
		if (!isSunday) {
			System.out.println("Tomorrow is not a Monday...");
			return;
		}
		
		/* Get tomorrow's date */
		Calendar tomorrow = Calendar.getInstance();
		tomorrow.setTime(calendar.getTime());
		tomorrow.add(Calendar.DAY_OF_YEAR, 1);
		
		/* Get which nth Monday tomorrow is */
		int number = tomorrow.get(Calendar.DAY_OF_WEEK_IN_MONTH);
		
		if (number >= 5) {
			System.out.println("Tomorrow is a Monday, but it's not the "
				+ "1st, 2nd, 3rd or 4th Monday of the month...");
			return;
		}
		
		/* Determine which side is being cleaned */
		boolean evenSide = (number % 2 == 0);
		StringBuilder sb = new StringBuilder();
		
		sb.append("Tomorrow is the ");
		sb.append(Utils.ordinal(number));
		sb.append(" Monday of the month; ");
		
		if (evenSide) {
			sb.append("move your car to the OPPOSITE side of the street!");
		} else {
			sb.append("move your car to YOUR side of the street!");
		}
		
		String messageText = sb.toString();
		
		List<String> telephones = new ArrayList<String>();
		telephones.add("2077524463");
		
		final String username = "changeme";
		final String password = "changeme";
		
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.transport.protocol", "smtp");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");

		final PasswordAuthentication authentication = new PasswordAuthentication(username, password);
		Session session = Session.getInstance(properties, new Authenticator() {
		    @Override
		    protected PasswordAuthentication getPasswordAuthentication() { 
		    	return authentication; 
		    }
		});
		
		try {
			
			Transport transport = session.getTransport();
			transport.connect();
			
			for (String telephone : telephones){
				System.out.println("Sending MMS to " + telephone);
				String toAddress = String.format("%s@mms.att.net", telephone);	
				Message message = new MimeMessage(session);
				message.setHeader("From", Constants.MSG_FROM_ADDRESS);
				message.setHeader("Reply-To", Constants.MSG_FROM_ADDRESS);
				message.setText(messageText);
				transport.sendMessage(message, InternetAddress.parse(toAddress));
			}
			
			transport.close();
			
		} catch (Exception e){
			e.printStackTrace();
		}
		
	}

}
