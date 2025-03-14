package steganography; 

class ByteProcessor{
    static int[] slice(int x, int flag){

        if(flag == 1){
            int arr[] = { (x & 0xE0) >> 5 , (x & 0x1C) >> 2 , x & 0x3}; 
            return arr; 
        }
        else if(flag == 2){
            int arr[] = {(x & 0xE0) >> 5 , (x & 0x18) >> 3 , x & 0x7}; 
            return arr; 
        }
        else if(flag == 3){
            int arr[] = {(x & 0xC0) >> 6 , (x & 0x38) >> 3 , x & 0x7}; 
            return arr; 
        }
        return null; 
    }

    static int[] merge(int r , int g , int b , int arr[] ,  int flag){
        int result[] = new int[3];
    
        //flag 1: 011, 101, 00
        if(flag == 1)
        {
           result[0]= (r & (~0x7)) | arr[0];
           result[1]= (g & (~0x7)) | arr[1];
           result[2]= (b & (~0x3)) | arr[2];
        }
        //flag 2: 011, 10, 100
        else if(flag == 2)
        {
           result[0]= (r & (~0x7)) | arr[0];
           result[1]= (g & (~0x3)) | arr[1];
           result[2]= (b & (~0x7)) | arr[2];
        }
        //flag 3: 01, 110, 100
        else if(flag == 3)
        {
           result[0]= (r & (~0x3)) | arr[0];
           result[1]= (g & (~0x7)) | arr[1];
           result[2]= (b & (~0x7)) | arr[2];
        }
        return result;

    }

    static int[] extract (int r, int g, int b, int flag){
        int result[] = new int[3];
        
        //flag 1: 011, 101, 00
        if(flag == 1)
        {
            result[0]= r & (0x7);
            result[1]= g & (0x7);
            result[2]= b & (0x3);
        }
        //flag 2: 011, 10, 100
        else if(flag == 2)
        {
            result[0]= r & (0x7);
            result[1]= g & (0x3);
            result[2]= b & (0x7);
        }
        //flag 3: 01, 110, 100
        else if(flag == 3)
        {
            result[0]= r & (0x3);
            result[1]= g & (0x7);
            result[2]= b & (0x7);
        }

        return result;
    }
    
        
    static int combine(int arr[] , int flag){

            if(flag == 1) 
                return arr[0] << 5 | (arr[1] << 2) | arr[2]; 
            else if(flag == 2) 
                return arr[0] << 5 | (arr[1] << 3) | arr[2]; 
            else if(flag == 3)
                return arr[0] << 6 | (arr[1] << 3) | arr[2]; 
            return 0; 
                
    }

}