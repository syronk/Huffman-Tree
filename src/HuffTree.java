
import java.util.PriorityQueue;

public class HuffTree {
        // fields
        private int size = 0;
        public HuffNode root = new HuffNode();
        private PriorityQueue<HuffNode> huffQueue = new PriorityQueue<HuffNode>();
        public String[] codeArray= new String[128];

        // constructor
        public HuffTree(int[] freq, char[] code) {
            // get the counts
            this.size = freq.length;

            // throw exception if arrays are different sizes
            if (freq.length != code.length) {
            throw new UnsupportedOperationException("Error: Character and code length mismatch.");
            }

            // build huffQueue from frequencies given
            for (int i = 0; i < this.size; i++) {
            	if(freq[i] !=0) {
            		//adds HuffNodes to the priority queue 
                huffQueue.offer(new HuffNode(code[i], freq[i], null, null, null));
            	}
            }

            // build huffman tree from queue
            createTree();

            // build code table from huffman tree
            createTable(this.root, "");
        }

		private void createTree() {
            // while elements remain in huffQueue, add to tree
            while (huffQueue.size() > 1) {
                // pop off two minimum elements in huffQueue
                HuffNode tempL = huffQueue.poll();
                HuffNode tempR = huffQueue.poll();
                
                // create root for two minimum elements and build tree
                HuffNode parent = new HuffNode(0, tempL.weight+tempR.weight, tempL, tempR, null);
                tempL.parent = parent;
                tempR.parent = parent;
                
                // add new tree back in huffQueue
                huffQueue.offer(parent);
                this.size++;
            }
            
            // set HuffTree root to remaining element in huffQueue
            this.root = huffQueue.peek();
        }

        private void createTable(HuffNode curr, String str) {
            // if iterator is null, return
            if (curr == null) return;
            
            // else if leaf, display path and value
            if (curr.leftTree == null && curr.rightTree == null) {
                
                codeArray[curr.value] = str;
                
            }

            // add 0 if moving to left child
            str += "0";
            // recursively call in pre-order
            createTable(curr.leftTree, str);
            
            // adjust path and add 1 before moving to right child
            str = str.substring(0, str.length()-1);
            str += "1";
            createTable(curr.rightTree, str);
        }
        
        //returns size of HuffTree
        public int getSize() { 
        	return this.size; 
        	}

       
}