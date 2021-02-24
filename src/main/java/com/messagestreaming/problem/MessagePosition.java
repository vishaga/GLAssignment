package com.messagestreaming.problem;

/***
 * <p>
 * Class holds Actual Message and its associated positionId.
 * 
 * @author gaurav.vishal
 *
 * @param <K>
 * @param <V>
 */
public class MessagePosition<K, V> {

	private Message<K, V> message;

	private long positionId;

	public MessagePosition(Message<K, V> message, long positionId) {
		super();
		this.message = message;
		this.positionId = positionId;
	}

	public Message<K, V> getMessage() {
		return message;
	}

	/***
	 * Replace method is called when client is trying to offer a message with
	 * existing key.
	 * 
	 * <p>
	 * 6. If you offer two messages with the same key, you deduplicate the older
	 * one.
	 * 
	 * @param message
	 */
	public void replaceMessage(Message<K, V> message) {
		this.message = message;
	}

	public long getPositionId() {
		return positionId;
	}

}
