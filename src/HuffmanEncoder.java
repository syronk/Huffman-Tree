import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Arrays;

public class HuffmanEncoder implements HuffmanCoding {

	@Override
	//returns formatted frequencies string
	public String getFrequencies(File inputFile) throws FileNotFoundException {
		//creates array for the ascii values
		int[] count = new int[128];
		BufferedReader reader = null;
			//reads file
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile), Charset.forName("UTF-8")));
		
			int c;
			
				try {
					//if the character exists add 1 to count at that ascii value 
					while((c = reader.read()) != -1) {
					  count[c]++;		  
					}
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			
			String frequency = "";
		for(int i =32; i < 128; i++) {
			if(count[i] != 0) {
				frequency +=Character.toString((char) i) + " "+ count[i] + "\n";
			}
		}
		return frequency;
	}

	@Override
	//returns a HuffTree built from the given file
	public HuffTree buildTree(File inputFile) throws Exception {
		int[] freq = null;
			//gets an array of frequencies with index corresponding to ascii values
			freq = getFrequenciesInt(inputFile);
		
    	char[] code = new char[128];
    	for(int i =0; i < 128; i++) {
			if(freq[i] != 0) {
				code[i] = (char)i;
			}
		}
    	 code = Arrays.copyOfRange(code, 32, 127);
    	 freq = Arrays.copyOfRange(freq, 32, 127);
    	

        // build Huffman Tree using given codes/frequencies
        HuffTree hTree = new HuffTree(freq, code);
        return hTree;
	}

	@Override
	public String encodeFile(File inputFile, HuffTree huffTree) throws FileNotFoundException {
		// create empty string to hold code
		StringBuilder str = new StringBuilder();

		String input = null;
		
			try {
				input = readFile(inputFile);
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		
        // iterate through given string
        for (int x = 0; x < input.length(); x++) {
        	
        	str.append(huffTree.codeArray[input.charAt(x)]);
        }
        
        return str.toString();
	}

	//takes an encoded string and outputs a decoded string
	@Override
	public String decodeFile(String code, HuffTree huffTree) {
		StringBuilder decodedStr = new StringBuilder();
        HuffNode currRoot = huffTree.root;
 		HuffNode myNode = currRoot;
 		
         // iterate through string
 		for (int i = 0; i < code.length(); i++) {
 			//when the node is a leaf if is the desired value
 			if(myNode.leftTree == null && myNode.rightTree == null) {
        	  decodedStr.append((char)myNode.value);
        	  //resets the node to the root to search for the next value
        	  myNode = currRoot;
        	  //when the leaf is found it contains the value wanted and dosesn't read another value so it dosen't increment 
        	  i--;
          }
 			//if the value is 0 go left
          else if(code.substring(i, i+1).equals("0")) {
         	  myNode = myNode.leftTree;
          }
 			//else if the value is 1 go right
          else if(code.substring(i, i+1).equals("1")) {
         	 myNode = myNode.rightTree;
         	 
          }
        }
 		//the last node will be the desired leaf but won't print in the for loop so it is added here
 		decodedStr.append((char)myNode.value);
        return decodedStr.toString();
	}

	@Override
	//prints out the encoded values for each char in ascii order
	public String traverseHuffmanTree(HuffTree huffTree) {
		String str = "";
		for(int i =32; i < 128; i++) {
			if(encode(Character.toString((char) i), huffTree).length() > 0){
				str +=Character.toString((char) i) + " "+ encode(Character.toString((char) i), huffTree) + "\n";
			}
		}
		
		return str;
	}
	
	public String readFile(File inputFile) throws IOException {
		@SuppressWarnings("resource")
		FileReader fileReader = new FileReader(inputFile);
		   
		  String fileContents = "";

		  int i ;

		  while((i =  fileReader.read())!=-1){
		   char ch = (char)i;

		   fileContents = fileContents + ch; 
		  }

		  return fileContents;
		 
	}
	
	//returns the array of frequencies
	public int[] getFrequenciesInt(File inputFile) throws Exception {
			int[] count = new int[128];
			
			@SuppressWarnings("resource")
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile), Charset.forName("UTF-8")));
				int c;
				while((c = reader.read()) != -1) {
				 
				  count[c]++;		  
				}
				
			return count;
		}
	
	public String encode(String input, HuffTree hTree){
         // create empty string to hold code
         StringBuilder str = new StringBuilder();
         
         // iterate through given string
         for (int x = 0; x < input.length(); x++) {
            
        	if(hTree.codeArray[input.charAt(x)] != null) {
        	 str.append(hTree.codeArray[input.charAt(x)]);
        	}
         }
         return str.toString();
     }

 

}
