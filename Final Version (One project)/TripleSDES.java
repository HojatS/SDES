import org.apache.commons.lang3.*;
import java.util.*;

public class TripleSDES {
    
    public static void main(String[] args){
        
        byte[] input = {1,1,0,1,0,1,1,1};
        byte[] k1 = {1,0,0,0,1,0,1,1,1,0};
        byte[] k2 = {0,1,1,0,1,0,1,1,1,0};
        
        //encrypt
        byte[] a = encrypt(k1,decrypt(k2,encrypt(k1,input)));
        
        //decrypt
        //byte[] a = decrypt(k1,encrypt(k2,decrypt(k1,input)));
        
        //System.out.println("RESULT:");
        //Utl.print(a);
        displayTable();
        
    }
    
    //encrypt
    public static byte[] encrypt(byte[] rawKey, byte[] plainText){
        byte[] cipher = new byte[plainText.length];
        
        //IP (initial permutation)
        int[] IP = {2, 6, 3, 1, 4, 8, 5, 7};
        byte[] IPResult = Utl.permutation(plainText, IP);
        
        //Generate Keys
        KeyGen keyGen = new KeyGen(rawKey);
        byte[] K1 = keyGen.getK1();
        byte[] K2 = keyGen.getK2();
        
        //System.out.println("K1:");
        //Utl.print(K1);
        //System.out.println("K2:");
        //Utl.print(K2);
        
        //fK round 1
        cipher = fK(IPResult, K1);
        
        //Swap = left shift by 4
        
        cipher = Utl.leftShift(cipher, cipher.length/2);
        
        //fk round 2
        cipher = fK(cipher, K2);
        
        
        //IP-1 (inverse)
        int[] inverseIP = {4, 1, 3, 5, 7, 2, 8, 6};
        cipher = Utl.permutation(cipher, inverseIP);
        
        return cipher;
    }
    
    //decrypt
    public static byte[] decrypt(byte[] rawKey, byte[] cipherText){
        byte[] plaintext = new byte[cipherText.length];
        
        //IP (initial permutation)
        int[] IP = {2, 6, 3, 1, 4, 8, 5, 7};
        byte[] IPResult = Utl.permutation(cipherText, IP);
        
        //generate keys
        KeyGen keyGen = new KeyGen(rawKey);
        byte[] K1 = keyGen.getK1();
        byte[] K2 = keyGen.getK2();
        
        //System.out.println("K1:");
        //Utl.print(K1);
        //System.out.println("K2:");
        //Utl.print(K2);
        
        //fK round 2
        plaintext = fK(IPResult, K2);
 
        //Swap = left shift by half length
        plaintext = Utl.leftShift(plaintext, plaintext.length/2); 
        
        //fk round 1
        plaintext = fK(plaintext, K1);
        
        //IP-1 (inverse)
        int[] inverseIP = {4, 1, 3, 5, 7, 2, 8, 6};
        plaintext = Utl.permutation(plaintext, inverseIP);
        
        return plaintext;
        
    }
    
    //fK function
    public static byte[] fK(byte[] input, byte[] key){
        byte[] output = new byte[input.length];
        
        //split 
        int splitSize = input.length/2;
        byte[] IPLeft = Arrays.copyOfRange(input, 0, splitSize);
        byte[] IPRight = Arrays.copyOfRange(input, splitSize, input.length);
        
        output = ArrayUtils.addAll(Utl.XOR(IPLeft,functionF(IPRight,key)),IPRight);
        
        return output;
    }
    
    //F Function
    public static byte[] functionF(byte[] byteArray, byte[] key){
        byte[] result = new byte[byteArray.length];
        
        //EP (expansion Pbox)
        int[] EP = {4, 1, 2, 3, 2, 3, 4, 1};
        result = Utl.permutation(byteArray, EP);
        
        //XOR
        result = Utl.XOR(result, key);
        
        //split
        byte[] resultLeft = Arrays.copyOfRange(result, 0, result.length/2);
        byte[] resultRight = Arrays.copyOfRange(result, result.length/2, result.length);
        
        //S-boxes
        int[][] S0 = { {1,0,3,2} , {3,2,1,0} , {0,2,1,3} , {3,1,3,2} } ;
        int[][] S1 = { {0,1,2,3},  {2,0,1,3}, {3,0,1,0}, {2,1,0,3}} ;
        
        byte[] row0array = {resultLeft[0], resultLeft[3]};
        byte[] column0array = {resultLeft[1], resultLeft[2]};
        
        byte[] row1array = {resultRight[0], resultRight[3]};
        byte[] column1array = {resultRight[1], resultRight[2]};
        
        int row0 = Utl.toDec(row0array);
        int column0 = Utl.toDec(column0array);
        
        int row1 = Utl.toDec(row1array);
        int column1 = Utl.toDec(column1array);
        
        resultLeft = Utl.toByteArray(S0[row0][column0],2);
        resultRight = Utl.toByteArray(S1[row1][column1],2);
        
        result = ArrayUtils.addAll(resultLeft, resultRight);
        
        //P4
        int[] P4 = {2, 4, 3, 1};
        result = Utl.permutation(result, P4);
        
        return result;
        
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
        
        byte[] answera = encrypt(rk1, decrypt(rk2,encrypt(rk1,pt1)));
        byte[] answerb = encrypt(rk3, decrypt(rk4,encrypt(rk3,pt2)));
        byte[] answerc = encrypt(rk5, decrypt(rk6,encrypt(rk5,pt3)));
        byte[] answerd = encrypt(rk7, decrypt(rk8,encrypt(rk7,pt4)));

        byte[] answere = decrypt(rk9,encrypt(rk10,decrypt(rk9,ct1)));
        byte[] answerf = decrypt(rk11,encrypt(rk12,decrypt(rk11,ct2)));
        byte[] answerg = decrypt(rk13,encrypt(rk14,decrypt(rk13,ct3)));
        byte[] answerh = decrypt(rk15,encrypt(rk16,decrypt(rk15,ct4)));
        answerList.add(answera);
        answerList.add(answerb);
        answerList.add(answerc);
        answerList.add(answerd);

        answerList2.add(answere);
        answerList2.add(answerf);
        answerList2.add(answerg);
        answerList2.add(answerh);

        System.out.println("\t\tRawkey 1\t\t\t\t\t\t\t\tRawkey 2\t\t\t\t\tPlaintext Text" +
                "\t\t\t\t\tCipher Text");

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


