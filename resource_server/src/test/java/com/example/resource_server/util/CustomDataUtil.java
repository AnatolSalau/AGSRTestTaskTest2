package com.example.resource_server.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomDataUtil {
      public static final String DATE_PATTERN = "dd-mm-yyyy";

      public static Date parseDataFromString(String strDate, String pattern) {
            try {
                  Date date = new SimpleDateFormat(pattern)
                        .parse(strDate);
                  return date;
            } catch (ParseException e) {
                  e.printStackTrace();
            }
            return null;
      }
}
