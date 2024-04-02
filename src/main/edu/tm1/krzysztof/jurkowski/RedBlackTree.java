package src.main.edu.tm1.krzysztof.jurkowski;

/**
 * This class represents a Red-Black Tree data structure.
 * It supports insertion, deletion, and retrieval operations.
 */
public class RedBlackTree {
	private Node root;

	/**
	 * Inserts a new node with the specified key and value into the Red-Black Tree.
	 * If a node with the same key already exists, its value will be updated.
	 *
	 * @param key   the key of the node to be inserted
	 * @param value the value of the node to be inserted
	 */
	public void insert(int key, int value) {
		Node newNode = new Node(key, value);
		if (root == null) {
			root = newNode;
			root.color = Node.Color.BLACK;
			return;
		}
		Node parent = null;
		Node current = root;
		while (current != null) {
			parent = current;
			if (key < current.key) {
				current = current.left;
			} else if (key > current.key) {
				current = current.right;
			} else {
				current.value = value;
				return;
			}
		}
		newNode.parent = parent;
		if (key < parent.key) {
			parent.left = newNode;
		} else {
			parent.right = newNode;
		}
		fixInsertion(newNode);
	}

	/**
	 * Fixes the insertion of a node in the Red-Black Tree by performing necessary
	 * rotations and color adjustments.
	 * This method is called after a node is inserted into the tree.
	 *
	 * @param node the node that was inserted
	 */
	private void fixInsertion(Node node) {
		while (node.parent != null && node.parent.color == Node.Color.RED) {
			if (node.parent == node.parent.parent.left) {
				Node uncle = node.parent.parent.right;
				if (uncle != null && uncle.color == Node.Color.RED) {
					node.parent.color = Node.Color.BLACK;
					uncle.color = Node.Color.BLACK;
					node.parent.parent.color = Node.Color.RED;
					node = node.parent.parent;
				} else {
					if (node == node.parent.right) {
						node = node.parent;
						rotateLeft(node);
					}
					node.parent.color = Node.Color.BLACK;
					node.parent.parent.color = Node.Color.RED;
					rotateRight(node.parent.parent);
				}
			} else {
				Node uncle = node.parent.parent.left;
				if (uncle != null && uncle.color == Node.Color.RED) {
					node.parent.color = Node.Color.BLACK;
					uncle.color = Node.Color.BLACK;
					node.parent.parent.color = Node.Color.RED;
					node = node.parent.parent;
				} else {
					if (node == node.parent.left) {
						node = node.parent;
						rotateRight(node);
					}
					node.parent.color = Node.Color.BLACK;
					node.parent.parent.color = Node.Color.RED;
					rotateLeft(node.parent.parent);
				}
			}
		}
		root.color = Node.Color.BLACK;
	}

	/**
	 * Rotates the given node to the left in the Red-Black Tree.
	 * 
	 * @param node the node to be rotated
	 */
	private void rotateLeft(Node node) {
		Node rightChild = node.right;
		node.right = rightChild.left;
		if (rightChild.left != null) {
			rightChild.left.parent = node;
		}
		rightChild.parent = node.parent;
		if (node.parent == null) {
			root = rightChild;
		} else if (node == node.parent.left) {
			node.parent.left = rightChild;
		} else {
			node.parent.right = rightChild;
		}
		rightChild.left = node;
		node.parent = rightChild;
	}

	/**
	 * Rotates the given node to the right in the Red-Black Tree.
	 * 
	 * @param node the node to be rotated
	 */
	private void rotateRight(Node node) {
		Node leftChild = node.left;
		node.left = leftChild.right;
		if (leftChild.right != null) {
			leftChild.right.parent = node;
		}
		leftChild.parent = node.parent;
		if (node.parent == null) {
			root = leftChild;
		} else if (node == node.parent.right) {
			node.parent.right = leftChild;
		} else {
			node.parent.left = leftChild;
		}
		leftChild.right = node;
		node.parent = leftChild;
	}

	/**
	 * Retrieves the value associated with the specified key in the Red-Black Tree.
	 * 
	 * @param key the key to search for
	 * @return the value associated with the key, or -1 if the key is not found
	 */
	public int get(int key) {
		Node node = root;
		while (node != null) {
			if (key < node.key) {
				node = node.left;
			} else if (key > node.key) {
				node = node.right;
			} else {
				return node.value;
			}
		}
		return -1;
	}

	/**
	 * Removes a node with the specified key from the Red-Black Tree.
	 * 
	 * @param key the key of the node to be removed
	 * @return the value of the removed node, or -1 if the node was not found
	 */
	public int remove(int key) {
		Node node = search(key);
		if (node == null) {
			return -1;
		}
		int value = node.value;
		deleteNode(node);
		return value;
	}

	/**
	 * Searches for a node with the given key in the Red-Black Tree.
	 *
	 * @param key the key to search for
	 * @return the node with the given key, or null if not found
	 */
	private Node search(int key) {
		Node node = root;
		while (node != null) {
			if (key < node.key) {
				node = node.left;
			} else if (key > node.key) {
				node = node.right;
			} else {
				return node;
			}
		}
		return null;
	}

	/**
	 * Deletes a node from the Red-Black Tree.
	 * 
	 * @param node the node to be deleted
	 */
	private void deleteNode(Node node) {
		if (node.left == null && node.right == null) {
			// Case 1: Node is a leaf node
			if (node == root) {
				root = null;
			} else if (node == node.parent.left) {
				node.parent.left = null;
			} else {
				node.parent.right = null;
			}
		} else if (node.left != null && node.right != null) {
			// Case 2: Node has two children
			Node successor = findSuccessor(node);
			node.key = successor.key;
			node.value = successor.value;
			deleteNode(successor);
		} else {
			// Case 3: Node has one child
			Node child = (node.left != null) ? node.left : node.right;
			if (node == root) {
				root = child;
			} else if (node == node.parent.left) {
				node.parent.left = child;
			} else {
				node.parent.right = child;
			}
			if (child != null) {
				child.parent = node.parent;
			}
		}
	}

	/**
	 * Finds the successor of a given node in the Red-Black Tree.
	 *
	 * @param node the node for which to find the successor
	 * @return the successor node, or null if no successor exists
	 */
	private Node findSuccessor(Node node) {
		if (node.right != null) {
			Node successor = node.right;
			while (successor.left != null) {
				successor = successor.left;
			}
			return successor;
		} else {
			Node parent = node.parent;
			while (parent != null && node == parent.right) {
				node = parent;
				parent = parent.parent;
			}
			return parent;
		}
	}

	/**
	 * Returns the height of the tree.
	 * 
	 * @return the height of the tree
	 */
	public int height() {
		return height(root);
	}

	/**
	 * Calculates the height of a given node in the Red-Black Tree.
	 * The height of a node is defined as the number of edges in the longest path
	 * from the node to a leaf node.
	 *
	 * @param node the node for which the height needs to be calculated
	 * @return the height of the node, -1 if the node is null
	 */
	private int height(Node node) {
		if (node == null) {
			return -1;
		} else {
			return 1 + Math.max(height(node.left), height(node.right));
		}
	}
}