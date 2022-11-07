import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class Cracking {
    private static final String encoded_msg = "CRYPTOGRAPHY";
    private static final String key = "0111001101";

    public static void main(String[] args) throws IOException {

        /**
         * Encryption and Decryption block for task 1 of part 3
         */
        String str = "";
        System.out.println("-------- QUESTION 1 --------");
        byte[] encryptAnswer = sdesEncode(encoded_msg, key);

        System.out.println("\nEncrypted text is: ");
        for (byte output : encryptAnswer) {
            System.out.print(output);
            str = str.concat(Byte.toString(output));
        }

        byte[] decryptAnswer = decrypt(str, key);
        String decryptedWord = CASCII.toString(decryptAnswer);
        System.out.println("\nDecrypted answer is: " + decryptedWord);




    }

    /**
     * SDES encoding method
     */
    public static byte[] sdesEncode(String encoded_msg, String key) {
        //Converts plainText to CASCII array
        byte[] plainText = CASCII.Convert(encoded_msg);
        byte[] output = new byte[plainText.length];

        System.out.println("The CASCII encoded byte is: ");
        for (byte i : plainText) {
            System.out.print(i);
        }

        for (int i = 0; i < plainText.length; ) {
            int j = i + 8;
            byte[] temp = SDES.encrypt(toByteArr(key), Arrays.copyOfRange(plainText, i, j));

            for (int x = 0, y = i; x < temp.length; x++, y++) {
                output[y] = temp[x];
            }
            i += 8;
        }
        return output;

    }

    /**
     * Decryption method
     */
    public static byte[] decrypt(String encoded_msg, String key) {
        byte[] output = new byte[encoded_msg.length()];

        for (int i = 0; i < encoded_msg.length(); ) {
            int j = i + 8;
            byte[] temp = SDES.decrypt(toByteArr(key), Arrays.copyOfRange(toByteArr(encoded_msg), i, j));

            for (int x = 0, y = i; x < temp.length; x++, y++) {
                output[y] = temp[x];
            }
            i += 8;
        }
        return output;

    }


    /**
     * Method to convert string to byte
     */
    public static byte[] toByteArr(String str) {
        //byte [] result = new byte[str.length()];
        byte[] result = str.getBytes();

        for (int i = 0; i < result.length; i++) {
            result[i] = (byte) (result[i] - 48);
        }
        return result;
    }

    
}
