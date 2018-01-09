package monitor.lib;

public class DataTypeConverter {
    public static String byteArray2Hex(byte[] byte_array) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (byte_array == null || byte_array.length <= 0) {
            return null;
        }
        for (int i = 0; i < byte_array.length; i++) {
            String char_value = Integer.toHexString(byte_array[i] & 0xFF);
            if (char_value.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(char_value);
        }
        return stringBuilder.toString();
    }

    public static byte[] hex2ByteArray(String hex_str) {
        if (hex_str == null || hex_str.equals("")) {
            return null;
        }
        hex_str = hex_str.toUpperCase();
        int byte_length = hex_str.length() / 2;
        char[] hex_chars = hex_str.toCharArray();
        byte[] byte_array = new byte[byte_length];
        for (int i = 0; i < byte_length; i++) {
            int index = i * 2;
            byte_array[i] = (byte) (charToByte(hex_chars[index]) << 4 | charToByte(hex_chars[index + 1]));
        }
        return byte_array;
    }

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }
}