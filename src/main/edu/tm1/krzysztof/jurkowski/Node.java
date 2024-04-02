package src.main.edu.tm1.krzysztof.jurkowski;

/**
 * Represents a node in a binary search tree.
 */
public class Node {
	public int key;
	public int value;
	public Node left, right, parent;

	/**
	 * The `Color` class represents a color in the RGB color model.
	 * It encapsulates the red, green, and blue components of a color.
	 * The values for the red, green, and blue components range from 0 to 255.
	 */
	public enum Color {
		RED, BLACK
	};

	public Color color;

	/**
	 * Constructs a new Node with the specified key and value.
	 *
	 * @param key   the key associated with the node
	 * @param value the value associated with the node
	 */
	public Node(int key, int value) {
		this.key = key;
		this.value = value;
	}
}