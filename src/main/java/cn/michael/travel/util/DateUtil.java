package cn.michael.travel.util;

import org.springframework.stereotype.Component;

import java.security.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
@Component
public class DateUtil {

    public Date parseString2Date(String dateString) throws ParseException {
        if (dateString == null) {
            return null;
        }
        return parseString2Date(dateString, "yyyy-MM-dd");
    }

    public Date parseString2Date(String dateString, String pattern) throws ParseException {
        if (dateString == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.parse(dateString);
    }

    public String parseDate2String(Date date) throws ParseException {
        if (date == null) {
            return null;
        }
        return parseDate2String(date, "yyyy-MM-dd");
    }

    public String parseDate2String(Date date, String pattern) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    public String getFirstDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        int firstDay = cal.getMinimum(Calendar.DATE);
        cal.set(Calendar.DAY_OF_MONTH, firstDay);
        SimpleDateFormat sdf = new SimpleDateFormat("MM.dd");
        return sdf.format(cal.getTime());
    }

    public String getLastDayOfMonth1(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        int lastDay = cal.getActualMaximum(Calendar.DATE);
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        SimpleDateFormat sdf = new SimpleDateFormat("MM.dd");
        return sdf.format(cal.getTime());
    }

    public String getStrMonth(int month) {
        String[] strs = {"一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二"};
        if (month > 0 && month < 13) {
            return strs[month - 1];
        } else {
            return "一";
        }
    }

    public String parseTimestampToStr(Timestamp timestamp, String timeFromat) {
        SimpleDateFormat df = new SimpleDateFormat(timeFromat);
        return df.format(timestamp);
    }

    public String timestampToDate(long time, String timeFormat) {
        if (time < 10000000000L) {
            time = time * 1000;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(timeFormat);
        return sdf.format(new Date(Long.parseLong(String.valueOf(time))));
    }

    public List<String> getYearMonths() throws Exception {
        ArrayList<String> result = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();
        min.set(min.get(Calendar.YEAR), Calendar.JANUARY, 1);
        max.setTime(new Date());
        max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);
        while (max.after(min)) {
            result.add(sdf.format(max.getTime()));
            max.add(Calendar.MONTH, -1);
        }
        return result;
    }

    public String[] getDaysByYearMonth(String yearMonth) throws ParseException {
        Date date = new SimpleDateFormat("yyyyMM").parse(yearMonth);
        Calendar a = Calendar.getInstance();
        a.setTime(date);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        String[] days = new String[maxDate];
        for (int i = 0; i < maxDate; i++) {
            String day = (i + 1) < 10 ? "0" + (i + 1) : (i + 1) + "";
            days[i] = yearMonth + day;
        }
        return days;
    }

    public boolean comparingDate(String targer, Date sourceDate) throws Exception {
        Date date1 = new SimpleDateFormat("HH:mm:ss").parse(targer);
        String t = new SimpleDateFormat("HH:mm:ss").format(sourceDate);
        Date date2 = new SimpleDateFormat("HH:mm:ss").parse(t);
        return date1.after(date2);
    }

    public boolean isWeekend(String time) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(parseString2Date(time, "yyyyMMdd"));
        int i = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        return i == 0 || (i == 5);
    }
}
