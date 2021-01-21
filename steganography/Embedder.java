package steganography; 

import java.awt.*; 
import java.awt.image.*; 
import java.io.*; 
import javax.imageio.ImageIO; 
// Embeds the data into the Image
public class Embedder {
    private String toEmbed; 
    private String vessel; 
    private String trgtFile; 
    private SecurityManager smgr;

    Embedder(String password, String toEmbed , String vessel , String trgtFile) throws Exception{
        File f1 = new File(toEmbed); 
        if(!f1.exists())
            throw new Exception(toEmbed + " doesn't exists"); 
        File f2 = new File(vessel); 
        if(!f2.exists())
            throw new Exception(vessel + " doesn't exists"); 
        smgr = new SecurityManager(password); 
        this.toEmbed = toEmbed; 
        this.vessel = vessel; 
        this.trgtFile = trgtFile; 
    }
    
    void embed() throws Exception{
        File fileVessel = new File(vessel); 
        BufferedImage buffVessel = ImageIO.read(fileVessel); 

        // to check the capacity of image
        int w , h , tot; 
        w = buffVessel.getWidth(); 
        h = buffVessel.getHeight(); 
        tot = w*h; 
        File fileToEmbed = new File(toEmbed); 
        if( tot < fileToEmbed.length() + HeaderManager.HEADER_LEN)
            throw new Exception("Embedding capacity of " + vessel + " is less than the size of " + toEmbed); 
        String hdr = HeaderManager.formHeader(fileToEmbed.getName(),fileToEmbed.length());

        WritableRaster wrstr = buffVessel.getRaster(); 

        FileInputStream srcFile = new FileInputStream(fileToEmbed); 

        int x , y; 
        int red , green , blue; 
        int arr[] , result[]; 
        int cnt = 0; 
        int data; 
        int flag = smgr.getPermutation(); 
        boolean keepEmbedding = true; 
        
        for(y = 0 ; y < h && keepEmbedding ; y++){
            for(x = 0 ; x < w ; x++){
                red = wrstr.getSample(x,y,0); // red band
                green = wrstr.getSample(x, y,1); // green band
                blue = wrstr.getSample(x,y,2); // blue band

                if(cnt < HeaderManager.HEADER_LEN){
                    data = hdr.charAt(cnt); 
                }
                else{
                    data = srcFile.read(); 
                    if(data == -1){
                        keepEmbedding = false; 
                        srcFile.close(); 
                        break; 
                    }
                    data = smgr.primaryCrypto(data); 
                }
                arr = ByteProcessor.slice(data , flag); 
                result = ByteProcessor.merge(red,green,blue,arr,flag); 

                wrstr.setSample(x,y,0,result[0]); 
                wrstr.setSample(x,y,1,result[1]); 
                wrstr.setSample(x,y,2,result[2]); 

                cnt++; 
                flag = (flag + 1)%3 + 1; 
                
            }
        }
        ImageIO.write(buffVessel, "PNG", new File(trgtFile));

    }
}

