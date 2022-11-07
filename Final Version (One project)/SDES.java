import org.apache.commons.lang3.*;
import java.util.*;

public class SDES {
    
    public static void main(String[] args){
        
        byte[] input = {1,0,1,0,1,0,1,0};
        byte[] rk = {1,1,1,0,0,0,1,1,1,0};
        
        //encrypt
        byte[] a = encrypt(rk, input);
        
        //decrypt
        //byte[] a = decrypt(rk, input);
        
        System.out.println("RESULT:");
        Utl.print(a);
        
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
    
}


