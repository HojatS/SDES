
public class SDESCracking
{
    public static void main(String[] args) {
        String msg = "1011011001111001001011101111110000111110100000000001110111010001111011111101101100010011000000101101011010101000101111100011101011010111100011101001010111101100101110000010010101110001110111011111010101010100001100011000011010101111011111010011110111001001011100101101001000011011111011000010010001011101100011011110000000110010111111010000011100011111111000010111010100001100001010011001010101010000110101101111111010010110001001000001111000000011110000011110110010010101010100001000011010000100011010101100000010111000000010101110100001000111010010010101110111010010111100011111010101111011101111000101001010001101100101100111001110111001100101100011111001100000110100001001100010000100011100000000001001010011101011100101000111011100010001111101011111100000010111110101010000000100110110111111000000111110111010100110000010110000111010001111000101011111101011101101010010100010111100011100000001010101110111111101101100101010011100111011110101011011";
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
        for(int i=0; i<size; i++) {
            keys[i] = Utl.toByteArray(i, 10);
        }


        byte[] plaintext = new byte[bytes.length];
        byte[][] plaintext8bits = new byte[bytes.length/8][8];

        //try decrypting using every key and produce results
        for(int i=0; i<keys.length; i++){
            byte[] rawkey = keys[i];

            for(int x=0; x<bytes.length/8; x++){
                plaintext8bits[x] = SDES.decrypt(rawkey,input[x]);
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

            if(text.contains(" AND ")){
                System.out.println("Decrypted Text:");
                System.out.println(text);
                System.out.println("Key:");
                Utl.print(keys[i]);
                break;
            }

        }
    }
}
