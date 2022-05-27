package com.nttdata.bootcamp.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GetDate {
	public static String getCurrentTimeUsingDate() {
	    Date date = new Date();
	    String strDateFormat = "dd-MM-yyyy";
	    DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
	    String formattedDate= dateFormat.format(date);
	    return formattedDate;
	}
}
