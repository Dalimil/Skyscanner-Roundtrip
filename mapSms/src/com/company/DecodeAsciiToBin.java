package com.company;


public class DecodeAsciiToBin {

    private String key = new String("ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz:;");

    public DecodeAsciiToBin() {
    }

    public String decode(String ascii_string) {
        StringBuilder sb = new StringBuilder();
        int dec;
        String bin_string_temp;
        for (int i = 0; i<ascii_string.length(); i++) {
            dec = key.indexOf(ascii_string.charAt(i));
            bin_string_temp = Integer.toString(dec,2);

            for (int j = 0; j<(6-bin_string_temp.length()); j++) {
                sb.append('0');
            }

            sb.append(bin_string_temp);
        }
        sb.append("0000");  // for 80x80 picture add 0000 at the end of the bit string
        return sb.toString();
    }
}
