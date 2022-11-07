import org.apache.commons.lang3.*;
import java.util.*;

public class KeyGen {
    private byte[] k1;
    private byte[] k2;
    
    public KeyGen(byte[] rawKey){
        int[] p10 = {3, 5, 2, 7, 4, 10, 1, 9, 8, 6};
        int[] p8 = {6, 3, 7, 4, 8, 5, 10, 9};
        byte[] output = Utl.permutation(rawKey,p10);
        int splitSize = output.length/2;
        byte[] outputLeft = Arrays.copyOfRange(output, 0, splitSize);
        byte[] outputRight = Arrays.copyOfRange(output, splitSize, output.length);
        output = ArrayUtils.addAll(Utl.leftShift(outputLeft,1),Utl.leftShift(outputRight,1));
        this.k1 = Utl.permutation(output, p8);
        output = ArrayUtils.addAll(Utl.leftShift(outputLeft,2),Utl.leftShift(outputRight,2));
        this.k2 = Utl.permutation(output, p8);
        
    }
    
    public byte[] getK1() {
        return k1;
    }
    
    public byte[] getK2() {
        return k2;
    }  
}
