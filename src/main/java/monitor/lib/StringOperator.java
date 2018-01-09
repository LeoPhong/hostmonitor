package monitor.lib;

public class StringOperator {
    public static String removeSpace(String str) {
        StringBuffer str_buffer = new StringBuffer();
        boolean is_space = false;
        for(int i=0; i<str.length(); i++) {
            if(str.charAt(i) == ' ' && is_space == true) {
                continue;
            }
            if(str.charAt(i) == ' ' && is_space == false) {
                is_space = true;
                str_buffer.append(str.charAt(i));
            }
            if(str.charAt(i) != ' ') {
                is_space = false;
                str_buffer.append(str.charAt(i));
            }
        }
        return str_buffer.toString();
    }
}