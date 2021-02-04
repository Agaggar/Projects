package facebook;

public class BinarySearchTree {
	//Custom Binary Search Tree class, used to sort friends alphabetically.
	
	private Node<Person> root;
	
	/*
	 * TreeInsert Method, following pseudocode from textbook.
	 * Creates a Node<Person> z from Person p to add to BinarySearchTree T.
	 * Y is always the parent of x as x moves down the tree, as compared by
	 * the indicated letter of x's name and z's name.
	 * Once x becomes null, z's parent is y, as y is the parent of where z would go.
	 * z goes to the right or left of y depending on the Ascii comparison of 
	 * the indicated letter of x's name and y's name.
	 */
	public void treeInsert(BinarySearchTree T, Person p) {
		Node<Person> z = new Node<Person>(p);
		Node<Person> y = null;
		Node<Person> x = T.getRoot();
		int letter = 0;
		
		while (x != null && letter < (z.getData().getName().length() - 1)) {
			y = x;
			if (z.getData().toAscii(letter) == x.getData().toAscii(letter))
				letter++;
			if (z.getData().toAscii(letter) < x.getData().toAscii(letter)) {
				letter = 0;
				x = x.getLeft();
			}
			else {
				letter = 0;
				x = x.getRight();
			}
		}
		if (letter == (z.getData().getName().length() - 1))
			x = x.getRight();
		
		letter = 0;
		boolean sameLetter = true;
		z.setParent(y);
		if (y == null)
			T.setRoot(z);
		else {
			while (sameLetter == true) {
				if (z.getData().toAscii(letter) == y.getData().toAscii(letter))
					letter++;
				if (z.getData().toAscii(letter) < y.getData().toAscii(letter)) {
					y.setLeft(z);
					sameLetter = false;
				}
				else {
					y.setRight(z);
					sameLetter = false;
				}
			}
		}
	}
	
	/*
	 * Inorder-Tree-Walk method as specified by pseudocode.
	 * First prints the left node, then the root, then the right node, as it
	 * moves up the tree to the root. Because of how tree-insert is specified, this
	 * traversal prints out all the Nodes in alphabetical order
	 */
	public void inorderTreeWalk(Node<Person> x) {
		if (x != null) {
			this.inorderTreeWalk(x.getLeft());
			sop(x.getData() + ", ");
			this.inorderTreeWalk(x.getRight());
		}
	}
	
	//Appropriate setter and getter methods
	public Node<Person> getRoot() {
		return root;
	}

	public void setRoot(Node<Person> root) {
		this.root = root;
	}
	
	//A helper method used to make printing information easier and faster.
  	public static void sop(Object x) {
  		System.out.print(x);
  	}
}

