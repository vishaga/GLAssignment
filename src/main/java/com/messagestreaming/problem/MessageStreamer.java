package com.messagestreaming.problem;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.LongAdder;

/***
 * <p>
 * Simple message streaming class. Each message has a key and a value. It holds
 * the messages in order that it comes in. Client(producer) can call offer
 * method to add new messages and the consumers will be calling the next method
 * in order to fetch the messages one by one.
 * 
 * @author gaurav.vishal
 *
 * @param <K>
 * @param <V>
 */
public class MessageStreamer<K, V> {

	private Node<K, V> head, tail;
	private MessagePosition<K, V> lastReturnedMessage;
	private int currentSize = 0;
	private LongAdder positionId;
	private Map<K, Node<K, V>> map;
	private Boolean isFirstNextCalss;
	private boolean ifOffered;
	private long previousFetchPositionId;

	public MessageStreamer() {
		map = new LinkedHashMap<>();
		isFirstNextCalss = true;
		positionId = new LongAdder();
		positionId.add(-1);
	}

	/***
	 * <p>
	 * Producer can call the offer method to add new message(s), one at a time.There
	 * will be a position id which is an incremental id which starts at 0 and unique
	 * positionId will be associated with every incoming message.
	 * 
	 * <p>
	 * If Client offer two messages with the same key, you de-duplicate the older
	 * one.
	 * <p>
	 * {@link MessageStreamer} holds maximum of 100 messages at a given moment. For
	 * example, if client offer 200 unique key messages than after 100 message
	 * onwards it will automatically start deleting older messages to make a room
	 * for new offered message.
	 * 
	 * @param message
	 */
	public void offer(Message<K, V> message) {
		this.ifOffered = true;
		// In-case of messages with the same key
		Node<K, V> tempNode1 = map.get(message.getKey());
		if (tempNode1 != null) {
			// de-duplicate the older
			tempNode1.replaceMessage(message);
		} else {
			// Increment positionId by 1.
			positionId.add(1);
			Node<K, V> node = new Node<>(new MessagePosition<>(message, positionId.longValue()));
			// offer called for the first time.
			if (head == null) {
				head = node;
				tail = node;
			} else {
				tail.setNext(node);
				node.setPrev(tail);
				// update tail point.
				tail = node;
			}
			currentSize++;
			// if counter is reaching above 100 than start remove older message and
			// decrement count.
			if (currentSize > 100) {
				map.remove(head.getMessagePosition().getMessage().getKey());
				head = head.getNext();
				currentSize--;
			}
			// put key and associated node to map, so that duplicate key finding cna be
			// easy.
			map.put(message.getKey(), node);
		}
	}

	/***
	 * <p>
	 * Returns {@link MessagePosition} whose {@link MessagePosition#getPositionId()}
	 * is immediate next greater positionId.
	 * <p>
	 * Returns previous returned {@link MessagePosition} if nothing offered between
	 * current and previous {@link MessageStreamer#next(long)} call.
	 * 
	 * @param positionId
	 * @return
	 */
	public MessagePosition<K, V> next(long positionId) {
		if (isFirstNextCalss) {
			isFirstNextCalss = false;
			if (positionId > 0) {
				return null;
			}
		}
		if (!this.ifOffered) {
			if (lastReturnedMessage == null) {
				return lastReturnedMessage;
			} else if (positionId == previousFetchPositionId) {
				return lastReturnedMessage;
			}
		}
		previousFetchPositionId = positionId;
		Node<K, V> node = getGreaterPositionIdNode(head, positionId);
		if (node == null) {
			return null;
		}
		lastReturnedMessage = node.getMessagePosition();
		map.remove(lastReturnedMessage.getMessage().getKey());
		head = node.getNext();
		node.setPrev(null);
		this.ifOffered = false;
		return lastReturnedMessage;
	}

	/***
	 * <p>
	 * Linear traversal to find next node greater than positionId.
	 * <p>
	 * Linear because the consumer should call the next(-1 or lower) for the first
	 * next call ever. Afterwards, it will call next with the position id returned
	 * by {@link MessagePosition} on the previous {@link MessageStreamer#next(long)}
	 * 
	 * @param head
	 * @param positionId
	 * @return
	 */
	private Node<K, V> getGreaterPositionIdNode(Node<K, V> head, long positionId) {
		Node<K, V> current = head;
		while (current != null && current.getMessagePosition().getPositionId() <= positionId) {
			current = current.getNext();
		}
		return current;
	}

}
