package steganography; 

public class Main{  
    public static void main(String args[]){
        try{
            Embedder emb = new Embedder("this is good","/home/laxman/Documents/projects/INFORMATION-EMBEDDER/data/secret.txt","/home/laxman/Documents/projects/INFORMATION-EMBEDDER/data/violet.png","/home/laxman/Documents/projects/INFORMATION-EMBEDDER/DataEmbedWithImage/DataPlusImage.png"); 
            emb.embed(); 
            Extractor ext = new Extractor("this is good", "/home/laxman/Documents/projects/INFORMATION-EMBEDDER/DataEmbedWithImage/DataPlusImage.png","/home/laxman/Documents/projects/INFORMATION-EMBEDDER/extractedData");
            ext.extract(); 
        }
        catch(Exception ex){
            System.out.println("Err: " + ex); 
        }
    }
}