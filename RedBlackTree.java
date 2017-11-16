/** Aman Anannd
 * Lab 2: Debugging with Eclipse and Red Black Tree) <br />
 * The {@code RedBlackTree} class of integers only <br />
 * Reference: <a href="https://en.wikipedia.org/wiki/Red%E2%80%93black_tree">
 *              https://en.wikipedia.org/wiki/Red%E2%80%93black_tree
 *            </a>
 */
public class RedBlackTree {

    /**
     * Root node of the red black tree
     */
    private Node root = null;

    /**
     * Size of the tree
     */
    private int size = 0;

    /**
     * Search the tree to find if the value is contained
     * @param value     {@code int} the value to be checked
     * @return          {@code boolean} If contains, return {@code true}, otherwise return {@code false}
     */
    public boolean contains(int value) {
        // TODO: Lab 2 Part 2-1 -- find an integer from the tree
        Node focusNode = root;
        while(focusNode.value!=value){
          if(value < focusNode.value){
            focusNode=focusNode.lChild;
          }else{
            focusNode=focusNode.rChild;
          }
          if(focusNode == null){
            return false;
          }
        }
        return true;
    }

    public boolean isLeftChild(Node check) {
        return check == check.parent.lChild;
    }

    public boolean isRightChild(Node check) {
        return check == check.parent.rChild;
    }

    public void insert_recurse(int value){
    		Node focusNode = root;
        while (focusNode != null && focusNode.value != null) {
            if (value < focusNode.value)
                focusNode = focusNode.lChild;
            else if (value > focusNode.value)
                focusNode = focusNode.rChild;
            else
                return;
        }
        Node newNode = new Node(value);
        if (root==null)
            root = newNode;
        else {
            Node parent = focusNode.parent;
            newNode.parent = parent;
            if (focusNode == parent.lChild)
                parent.lChild = newNode;
            else
               parent.rChild = newNode;
        }
        newNode.color = Node.RED;
        insert_repair_tree(newNode);
    }

    public void rotate_left(Node newNode){
//    	  System.out.println("In rotate left");
      Node parent=newNode.parent;
      Node grandParent=parent.parent;
      
      parent.rChild=newNode.lChild; // Case1 //
      if(newNode.lChild!=null) newNode.lChild.parent=parent; // Case 2 //
      newNode.parent=grandParent; // Case 3 //
      
      if (grandParent == null)
          root = newNode;
      else { // Case 4 //
	      if(isLeftChild(parent)) grandParent.lChild = newNode; 
	      else grandParent.rChild=newNode;
      }
      
      newNode.lChild=parent; // Case 5 //
      parent.parent=newNode; // Case 6 //
      
//	  System.out.println("Ending rotate left");

    }

    public void rotate_right(Node newNode){
//  	  System.out.println("In rotate right");

      Node parent=newNode.parent;
      Node grandParent=parent.parent;


      parent.lChild=newNode.rChild; // Case1 //
      if(newNode.rChild!=null) newNode.rChild.parent=parent; // Case 2 //
      newNode.parent=grandParent; // Case 3 //
      
      if (grandParent == null)
          root = newNode;
      else { // Case 4 //
	      if(isLeftChild(parent)) grandParent.lChild = newNode; 
	      else grandParent.rChild=newNode;
      }
      
      newNode.rChild=parent; // Case 5 //
      parent.parent=newNode; // Case 6 //
      
//	  System.out.println("Ending  rotate right");

    }
    public void insert_case4(Node newNode,Node uncle) {
    		Node parent = newNode.parent;

	    	if(parent.color==Node.RED && uncle.color == Node.BLACK) {
//	    		System.out.println("In insert 44444");
	    		if ( isRightChild(newNode) && isLeftChild(newNode.parent) ){
	  	          rotate_left(newNode);
	  	      	  newNode=newNode.lChild;
	    		}
	  	    else if ( isLeftChild(newNode) && isRightChild(newNode.parent) ){
	  	          rotate_right(newNode);
	  	          newNode=newNode.rChild;
	  	    }
//	      	System.out.println("Ending insert 4");
	    }
	  	insert_case5(newNode);
    }

    public void insert_case5(Node newNode){
//        System.out.println("In insert_case 5");
        //case 5
	    Node parent = newNode.parent;
	    Node grandParent=parent.parent;
	    parent.color = Node.BLACK;
	    grandParent.color = Node.RED;
        newNode.parent.color=Node.BLACK;
        newNode.parent.parent.color=Node.RED;

        if( isLeftChild(newNode) && isLeftChild(newNode.parent) )
          rotate_right(newNode.parent);
        else rotate_left(newNode.parent);
        
//  	    System.out.println("Ending insert 5");
  	    	
        }

    public void insert_repair_tree(Node newNode){

    	  //case 1
      if(newNode==root){
        newNode.color=Node.BLACK;
        }
      //case 3
      else if (newNode.parent.color == Node.RED){
    	  
        Node uncle;
        Node parent = newNode.parent;
        Node grandParent = parent.parent;
        
        if (isLeftChild(parent)) 
        		uncle = grandParent.rChild;
        else 
        		uncle = grandParent.lChild;
        
        if (parent.color==Node.RED && uncle.color==Node.RED){
          parent.color = Node.BLACK;
          uncle.color = Node.BLACK;
          grandParent.color = Node.RED;
          insert_repair_tree(grandParent);
          return;
        }
        //case 4 & 5
        insert_case4(newNode,uncle);
      }
    }
    /**
     * Insert an integer to the tree
     * @param data      {@code int} New element to be inserted
     */
    public void insert(int value) {
        // TODO: Lab 2 Part 2-2 -- insert an integer into the tree
        insert_recurse(value);
    }

    /**
     * Get the size of the tree
     * @return          {@code int} size of the tree
     */
    public int size() {
        return size;
    }

    public void inOrderTraverseTree(Node focusNode){
      if (focusNode != null) {
    	  	// Traverse the left node
		inOrderTraverseTree(focusNode.lChild);
	    System.out.print(focusNode + " "+ focusNode.color+ " ");
	    System.out.println(focusNode.color ? "BLACK":"RED");
		// Traverse the right node
		inOrderTraverseTree(focusNode.rChild);
      }
    }

    /**
     * Cast the tree into a string
     * @return          {@code String} Printed format of the tree
     */
    @Override public String toString() {
        // TODO: Lab 2 Part 2-3 -- print the tree, where each node contains both value and color
        // You can print it by in-order traversal
        if(root==null)
          return null;
        inOrderTraverseTree(root);
        return null;
    }

    /**
     * Main entry
     * @param args      {@code String[]} Command line arguments
     */
    public static void main(String[] args) {
        RedBlackTree rbt = new RedBlackTree();

        for (int i = 0; i < 10; i++)
            rbt.insert((int) (Math.random() * 200));

        assert rbt.root.color == RedBlackTree.Node.BLACK;
        System.out.println(rbt.root);           // This helps to figure out the tree structure
        System.out.println(rbt);

        //
        // rbt.insert(23);
        // rbt.insert(20);
        // rbt.insert(24);
        // rbt.insert(19);
        // rbt.insert(28);

        // System.out.println(rbt.contains(23));
        // System.out.println(rbt.contains(33));
        // System.out.println(rbt.contains(19));
        //
        // System.out.println(rbt.root);           // This helps to figure out the tree structure
        // System.out.println(rbt);
        // rbt.insert(4);rbt.insert(2);rbt.insert(8);rbt.insert(1);
        // rbt.insert(3);rbt.insert(6);rbt.insert(9);rbt.insert(0);
        // rbt.insert(5);rbt.insert(7);
        // System.out.println(rbt.root);           // This helps to figure out the tree structure
        // System.out.println(rbt);
    }


    /**
     * The {@code Node} class for {@code RedBlackTree}
     */
    private class Node {
        public static final boolean BLACK = true;
        public static final boolean RED = false;

        public Integer value;
        public boolean color = BLACK;
        public Node parent = null, lChild = null, rChild = null;

        public Node(Integer value) {             // By default, a new node is black with two NIL children
            this.value = value;
            if (value != null) {
                lChild = new Node(null);         // And the NIL children are both black
                lChild.parent = this;
                rChild = new Node(null);
                rChild.parent = this;
            }
        }

        /**
         * Print the tree node: red node wrapped by "<>"; black node by "[]"
         * @return          {@code String} The printed string of the tree node
         */
        @Override public String toString() {
            if (value == null)
                return "";
            return (color == RED) ? "<" + value + ">" : "[" + value + "]";
        }
    }
  }
