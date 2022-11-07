
public class TripleSDESCracking
{
    public static void main(String[] args){
        String msg ="00011111100111111110011111101100111000000011001011110010101010110001011101001101000000110011010111111110000000001010111111000001010010111001111001010101100000110111100011111101011100100100010101000011001100101000000101111011000010011010111100010001001000100001111100100000001000000001101101000000001010111010000001000010011100101111001101111011001001010001100010100000";
        msgDecode(msg);
    }
    
    public static void msgDecode(String msg){
        char[] ch = msg.toCharArray();
        byte[] bytes = new byte[msg.length()];
        
        //Convert msg to Byte Array
        for(int i=0; i<bytes.length; i++){
            if(ch[i] == '0'){bytes[i]=0;}
            else {bytes[i]=1;}
        }
        
        //Divide array to 8 bit sections
        byte[][] input = new byte[bytes.length/8][8];
        for(int i=0; i<input.length; i++){
            int k = 0;
            for(int j=i*8; j<8*(i+1); j++){
                input[i][k]=bytes[j];
                k++;
            }
        }
        
        
        //generate every possible rawkey
        int size = (int)Math.pow(2,10);
        byte[][] keys = new byte[size][10];
        for(int i=0; i<size; i++)
            keys[i] = Utl.toByteArray(i,10);
            
            
        byte[] plaintext = new byte[bytes.length];
        byte[][] plaintext8bits = new byte[bytes.length/8][8];
        
        //try decrypting using every key and produce results
        for(int i=0; i<keys.length; i++){
            for (int ii=0; ii<keys.length; ii++){
                byte[] k1 = keys[i];
                byte[] k2 = keys[ii];
                
                for(int x=0; x<bytes.length/8; x++){
                    plaintext8bits[x] = SDES.decrypt(k1,SDES.encrypt(k2,SDES.decrypt(k1,input[x])));
                }
                
                //concatenate all 8bit decryptions to form plaintext
                for(int a=0; a<plaintext8bits.length; a++){
                    int b=0;
                    for(int c=a*8; c<8*(a+1); c++){
                        plaintext[c] = plaintext8bits[a][b];
                        b++;
                    }
                }
                
                //convert to string and check for special characters
                String text = CASCII.toString(plaintext);
                
                if(text.contains(" THE ")){
                    System.out.println("Decrypted Text:");
                    System.out.println(text);
                    System.out.println("Key 1:");
                    Utl.print(keys[i]);
                    System.out.println("Key 2:");
                    Utl.print(keys[ii]);
                }
            }
        }
    }
}
