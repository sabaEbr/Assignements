package com.GO.frame.util;

public class Time {
    public static void delay(){
        try {
            Thread.sleep(200); // Introduce a time delay to not step over next buttons
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String printDifference(long startTime, long endTime){

        //milliseconds
        long different = endTime - startTime;


        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        //long hoursInMilli = minutesInMilli * 60;
        //long daysInMilli = hoursInMilli * 24;

        //long elapsedDays = different / daysInMilli;
        //different = different % daysInMilli;

        //long elapsedHours = different / hoursInMilli;
        //different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;

        // System.out.printf("%d days, %d hours, %d minutes, %d seconds%n", elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds);
        return elapsedSeconds < 10 ? String.format("%2d:0%d", elapsedMinutes, elapsedSeconds) : String.format("%2d:%d", elapsedMinutes, elapsedSeconds);

    }

}
