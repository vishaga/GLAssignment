package com.messagestreaming.problem;

/***
 * <p>
 * Singly linked list class which hold {@link MessagePosition} and node's next
 * and previous pointer.
 * 
 * @author gaurav.vishal
 *
 * @param <K> Message Key
 * @param <V> Message Value
 */
public class Node<K, V> {

	private MessagePosition<K, V> messagePosition;
	private Node<K, V> next;
	private Node<K, V> prev;

	public Node(MessagePosition<K, V> messagePosition) {
		this.messagePosition = messagePosition;
	}

	public MessagePosition<K, V> getMessagePosition() {
		return messagePosition;
	}

	public Node<K, V> getNext() {
		return next;
	}

	public void setNext(Node<K, V> next) {
		this.next = next;
	}

	public Node<K, V> getPrev() {
		return prev;
	}

	public void setPrev(Node<K, V> prev) {
		this.prev = prev;
	}

	/***
	 * Replace method is called when client is trying to offer a message with
	 * existing/duplicate message key.
	 * 
	 * <p>
	 * 6. If you offer two messages with the same key, you deduplicate the older
	 * one.
	 * 
	 * @param message
	 */
	public void replaceMessage(Message<K, V> message) {
		this.messagePosition.replaceMessage(message);
	}
}
