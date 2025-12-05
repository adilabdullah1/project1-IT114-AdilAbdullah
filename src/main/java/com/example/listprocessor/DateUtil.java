package com.example.listprocessor;

import java.util.*;

public class DateUtil {
    public static int get_current_year() {
        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        return cal.get(Calendar.YEAR);
    }
}
