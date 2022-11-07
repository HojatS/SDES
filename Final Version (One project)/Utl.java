public class Utl {

    //permutation method 
    public static byte[] permutation(byte[] bytes, int[] p) {
        byte[] result = new byte[p.length];
        for (int n=0;n<p.length;n++)
            result[n] = bytes[p[n]-1];
        return result;
    }
        

    //XOR
    public static byte[] XOR(byte[] array1, byte[] array2) {
        byte[] xor = new byte[array1.length];
        for (int i = 0; i < xor.length; i++) 
            xor[i] = (byte) (array1[i] ^ array2[i]);
        return xor;
    }
    
    
    //left shift
    public static byte[] leftShift(byte[] array, int count){
        for (int i=0; i < count ; i++){
            int j;
            byte first;
            first=array[0];
            for(j=0; j < array.length-1; j++){
                array[j] = array[j+1];
            }
            array[j] = first;
        }
        return array;
    }
    
    // convert integer to byte array of certain length
    public static byte[] toByteArray(int x, int len) {
        String binString = String.format("%" + len + "s", Integer.toBinaryString(x)).replaceAll(" ", "0");
        char[] ch = binString.toCharArray();
        byte[] bytes = new byte[binString.length()];
        for(int i=0; i<bytes.length; i++){
            if(ch[i] == '0'){bytes[i]=0;}
            else {bytes[i]=1;}
            }
        return bytes;
    }

    //convert byte array to decimal number
    public static int toDec(byte[] bytes){
        int temp=0;
        int base = 1;
        for(int i=bytes.length-1; i>=0; i--){
            int n = bytes[i];
            temp = temp + (n*base);
            base = base * 2;
        }
        return temp;
    }
    
    public static void print(byte[] array){
        for(int i=0; i<array.length; i++)     
            System.out.print(array[i]);
        System.out.println();
    }
    
}