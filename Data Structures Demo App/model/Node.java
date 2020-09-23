package model;

@SuppressWarnings("javadoc")
public class Node<E> {
	Node<E> previous;
	Node<E> next;
	E data;
	
	public Node() {
		this.data = null;
		this.previous = null;
		this.next = null;
	}
	
	public Node(E e) {
		this.data = e;
		this.previous = null;
		this.next = null;
	}
}
