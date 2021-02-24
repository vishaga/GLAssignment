package com.messagestreaming.problem;

/***
 * <p>
 * class which hold actual key and its associated message.
 * 
 * @author gaurav.vishal
 *
 * @param <K>
 * @param <V>
 */
public class Message<K, V> {

	private K key;

	private V value;

	public Message(K key, V value) {
		this.key = key;
		this.value = value;
	}

	public K getKey() {
		return key;
	}

	public V getValue() {
		return value;
	}

}
