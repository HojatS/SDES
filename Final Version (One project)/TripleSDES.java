import org.apache.commons.lang3.*;
import java.util.*;

public class TripleSDES {
    
    public static void main(String[] args){
        byte[] input = {1,1,1,0,0,1,1,0};
        byte[] k1 = {1,0,0,0,1,0,1,1,1,0};
        byte[] k2 = {0,1,1,0,1,0,1,1,1,0};
        //encrypt
        byte[] a = encrypt(k1,k2,input);
        //decrypt
        //byte[] a = decrypt(k1,k2,input);
        System.out.println("RESULT:");
        Utl.print(a);
        //displayTable();
    }
    
    //encrypt
    public static byte[] encrypt(byte[] rawKey1,byte[] rawKey2, byte[] plainText){
        byte[] cipher = new byte[plainText.length];
        cipher = SDES.encrypt(rawKey1,SDES.decrypt(rawKey2,SDES.encrypt(rawKey1,plainText)));
        return cipher;
    }
    
    //decrypt
    public static byte[] decrypt(byte[] rawKey1,byte[] rawKey2, byte[] cipherText){
        byte[] plaintext = new byte[cipherText.length];
        plaintext = SDES.decrypt(rawKey1,SDES.encrypt(rawKey2,SDES.decrypt(rawKey1,cipherText)));
        return plaintext;
    }
    

    public static void displayTable(){
        ArrayList<byte[]> rawKeyList1 = new ArrayList<byte[]>();
        ArrayList<byte[]> rawKeyList12 = new ArrayList<byte[]>();
        ArrayList<byte[]> rawKeyList2 = new ArrayList<byte[]>();
        ArrayList<byte[]> rawKeyList22 = new ArrayList<byte[]>();

        ArrayList<byte[]> plainTextList = new ArrayList<byte[]>();
        ArrayList<byte[]> cypherTextList = new ArrayList<byte[]>();

        ArrayList<byte[]> answerList = new ArrayList<byte[]>();
        ArrayList<byte[]> answerList2 = new ArrayList<byte[]>();



        byte[] rk1 = {0,0,0,0,0,0,0,0,0,0};
        byte[] rk2 = {0,0,0,0,0,0,0,0,0,0};
        byte[] pt1 = {0,0,0,0,0,0,0,0};
        rawKeyList1.add(rk1);
        rawKeyList1.add(rk2);
        plainTextList.add(pt1);

        byte[] rk3 = {1,0,0,0,1,0,1,1,1,0};
        byte[] rk4 = {0,1,1,0,1,0,1,1,1,0};
        byte[] pt2 = {1,1,0,1,0,1,1,1};
        rawKeyList1.add(rk3);
        rawKeyList1.add(rk4);
        plainTextList.add(pt2);

        byte[] rk5 = {1,0,0,0,1,0,1,1,1,0};
        byte[] rk6 = {0,1,1,0,1,0,1,1,1,0};
        byte[] pt3 = {1,0,1,0,1,0,1,0};
        rawKeyList12.add(rk5);
        rawKeyList12.add(rk6);
        plainTextList.add(pt3);

        byte[] rk7 = {1,1,1,1,1,1,1,1,1,1};
        byte[] rk8 = {1,1,1,1,1,1,1,1,1,1};
        byte[] pt4 = {1,0,1,0,1,0,1,0};
        rawKeyList12.add(rk7);
        rawKeyList12.add(rk8);
        plainTextList.add(pt4);


        byte[] rk9 = {1,0,0,0,1,0,1,1,1,0};
        byte[] rk10 = {0,1,1,0,1,0,1,1,1,0};
        byte[] ct1 = {1,1,1,0,0,1,1,0};
        rawKeyList2.add(rk9);
        rawKeyList2.add(rk10);
        cypherTextList.add(ct1);

        byte[] rk11 = {1,0,1,1,1,0,1,1,1,1};
        byte[] rk12 = {0,1,1,0,1,0,1,1,1,0};
        byte[] ct2 = {0,1,0,1,0,0,0,0};
        rawKeyList2.add(rk11);
        rawKeyList2.add(rk12);
        cypherTextList.add(ct2);

        byte[] rk13 = {0,0,0,0,0,0,0,0,0,0};
        byte[] rk14 = {0,0,0,0,0,0,0,0,0,0};
        byte[] ct3 = {1,0,0,0,0,0,0,0};
        rawKeyList22.add(rk13);
        rawKeyList22.add(rk14);
        cypherTextList.add(ct3);

        byte[] rk15 = {1,1,1,1,1,1,1,1,1,1};
        byte[] rk16 = {1,1,1,1,1,1,1,1,1,1};
        byte[] ct4 = {1,0,0,1,0,0,1,0};
        rawKeyList22.add(rk15);
        rawKeyList22.add(rk16);
        cypherTextList.add(ct4);
        
        byte[] answera = encrypt(rk1, rk2,pt1);
        byte[] answerb = encrypt(rk3,rk4,pt2);
        byte[] answerc = encrypt(rk5,rk6,pt3);
        byte[] answerd = encrypt(rk7,rk8,pt4);

        byte[] answere = decrypt(rk9,rk10,ct1);
        byte[] answerf = decrypt(rk11,rk12,ct2);
        byte[] answerg = decrypt(rk13,rk14,ct3);
        byte[] answerh = decrypt(rk15,rk16,ct4);
        answerList.add(answera);
        answerList.add(answerb);
        answerList.add(answerc);
        answerList.add(answerd);

        answerList2.add(answere);
        answerList2.add(answerf);
        answerList2.add(answerg);
        answerList2.add(answerh);

        System.out.println("\t\tRawkey 1\t\t\t\tRawkey 2\t\t\tPlaintext Text" +
                "\t\t\tCipher Text");

        for(int i = 0; i < answerList.size(); i++){
            printArrayList(rawKeyList1.get(i),rawKeyList2.get(i), plainTextList.get(i),answerList.get(i));
        }
        System.out.println();
        for(int i = 0; i < answerList2.size(); i++){
            printArrayList(rawKeyList12.get(i),rawKeyList22.get(i),answerList2.get(i),cypherTextList.get(i));
        }
    }
    
    public static void printArrayList(byte[] rawKey1, byte [] rawKey2, byte[] plaintext, byte[] answer){
        System.out.println(Arrays.toString(rawKey1) + "\t\t" + Arrays.toString(rawKey2) +  "\t\t" +
                Arrays.toString(plaintext) + "\t\t" + Arrays.toString(answer));
    }
    
}



