package model;

import java.util.*;

@SuppressWarnings("javadoc")
public class MyLinkedList<E> extends AbstractList<E> {

	Node<E> head;
	Node<E> tail;
	int size;
	
	public MyLinkedList() {
		this.size = 0;
		this.head = new Node<E>();
		this.tail = new Node<E>();
		this.head.next = this.tail;
		this.tail.previous = this.head;
	}
	
	public boolean add(E element) {
		if(element == null) {
			return false;
		}
		Node<E> addNode = new Node<E>(element);
		Node<E> previous = tail.previous;
		previous.next = addNode;
		addNode.previous = previous;
		addNode.next = tail;
		tail.previous = addNode;
		
		size++;
		
		return true;
	}
	
	public E get(int index) {
		if(index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException("Invalid index input");
		}
		Node<E> target = head;
		for(int i = 0; i <= index; i++) {
			target = target.next;
		}
		E value = target.data;
		
		return value;
	}
	
	public int size() {
		return size;
	}
	
	public E remove(int index) {
		if (index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException("Invalid index input");
		}
		Node<E> removeNode = head;
		for (int i = 0; i <= index; i++) {
			removeNode = removeNode.next;
		}
		Node<E> previous = removeNode.previous;
		Node<E> next = removeNode.next;
		previous.next = next;
		next.previous = previous;
		size--;
		E value = removeNode.data;
		return value;	
	}
	
	public E set(int index, E element) 
	{
		if (index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException("Invalid index input!!");
		}
		if (element == null) {
			throw new NullPointerException("Invalid element input!!");
		}
		Node<E> setNode = head;
		for (int i = 0; i <= index; i++) {
			setNode = setNode.next;
		}
		setNode.data = element;
		return element;
	}   
}


