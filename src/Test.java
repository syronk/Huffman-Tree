import java.io.File;
import java.io.IOException;

public class Test {
	public static void main(String[] args) throws Exception{
		File file = new File("C:\\Users\\katie\\eclipse-workspace\\Project3\\illiad.txt");
		File file2 = new File("C:\\Users\\katie\\eclipse-workspace\\Project3\\test.txt");
		HuffmanEncoder test = new HuffmanEncoder();
		HuffTree myTree = test.buildTree(file);
		String str = test.encodeFile(file2, myTree);
		System.out.println(str);
		String decode = test.decodeFile(str, myTree);
		System.out.println(decode);
		String freq = test.getFrequencies(file);
		System.out.println(freq);
		String codes = test.traverseHuffmanTree(myTree);
		//System.out.println(codes);
		
	}
}
