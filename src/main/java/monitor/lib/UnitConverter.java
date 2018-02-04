package monitor.lib;

public class UnitConverter {
    public static String convertUnit(double little_unit) {
        if(little_unit < 1000L) {
            return String.format("%.2f", little_unit) + "B";
        }
        else if(little_unit < 1000L*1000L) {
            return String.format("%.2f", little_unit/1024.0f) + "KB";
        }
        else if(little_unit < 1000L*1000L*1000L) {
            return String.format( "%.2f", little_unit/1024.0f/1024.0f) + "MB";
        }
        else if(little_unit < 1000L*1000L*1000L*1000L) {
            return String.format("%.2f", little_unit/1024.0f/1024.0f/1024.0f) + "GB";
        }
        else {
            return String.format("%.2f", little_unit/1024.0f/1024.0f/1024.0f/1024.0f) + "TB";
        }
    }

    public static String convertUnit(long little_unit) {
        if(little_unit < 1000L) {
            return String.valueOf(little_unit) + "B";
        }
        else if(little_unit < (1000L*1000L)) {
            return String.format("%.2f", little_unit/1024.0f) + "KB";
        }
        else if(little_unit < (1000L*1000L*1000L)) {
            return String.format( "%.2f", little_unit/1024.0f/1024.0f) + "MB";
        }
        else if(little_unit < (1000L*1000L*1000L*1000L)) {
            return String.format( "%.2f", little_unit/1024.0f/1024.0f/1024.0f) + "GB";
        }
        else {
            return String.format("%.2f", little_unit/1024.0f/1024.0f/1024.0f/1024.0f) + "TB";
        }
    }

    public static String convertTempUnit(double temp) {
        return String.format("%.2f", temp)+"℃";
    }
}