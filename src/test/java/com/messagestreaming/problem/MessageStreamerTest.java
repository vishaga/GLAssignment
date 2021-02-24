package com.messagestreaming.problem;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Test.None;

/**
 * <p>
 * Provides sufficient test coverage for {@link MessageStreamer} class.
 * 
 * @author gaurav.vishal
 */
public class MessageStreamerTest {

	/**
	 * Interactive test that checks if {@link MessageStreamer#offer(Message)} called
	 * more than 100 times.
	 * 
	 * @exception None
	 * 
	 * @passCriteria should hold only last 100 messages if offer was called more
	 *               than 100 times.
	 * 
	 */
	@Test
	public void offer_case1() {
		long positionId = -1;
		MessageStreamer<String, String> messageStreamer = new MessageStreamer<>();
		for (int i = 0; i < 101; i++) {
			messageStreamer.offer(new Message<String, String>("Key-" + i, "Message-" + i));
		}
		MessagePosition<String, String> messagePosition = messageStreamer.next(positionId);
		Assert.assertNotNull(messagePosition);
		Assert.assertEquals(messagePosition.getPositionId(), 1);
	}

	/**
	 * Interactive test that checks when {@link MessageStreamer#offer(Message)}
	 * called with same key.
	 * 
	 * @exception None
	 * 
	 * @passCriteria Should override existing message with the new one in case of
	 *               duplicate key.
	 * 
	 */
	@Test
	public void offer_case2() {
		long positionId = -1;
		MessageStreamer<String, String> messageStreamer = new MessageStreamer<>();
		for (int i = 0; i < 101; i++) {
			messageStreamer.offer(new Message<String, String>("Key", "Message-" + i));
		}
		MessagePosition<String, String> messagePosition = messageStreamer.next(positionId);
		Assert.assertNotNull(messagePosition);
		Assert.assertEquals(messagePosition.getPositionId(), 0);
		Assert.assertEquals(messagePosition.getMessage().getValue(), "Message-100");
	}

	/**
	 * Interactive test that checks if {@link MessageStreamer#next(long)} called
	 * before {@link MessageStreamer#offer(Message)}.
	 * 
	 * @exception None
	 * 
	 * @passCriteria should return null.
	 * 
	 */
	@Test
	public void next_null_case1() {
		long positionId = -1;
		MessageStreamer<String, String> messageStreamer = new MessageStreamer<>();
		MessagePosition<String, String> messagePosition = messageStreamer.next(positionId);
		Assert.assertNull(messagePosition);
	}

	/**
	 * Interactive test that checks if {@link MessageStreamer#next(long)} for the
	 * first time but with positive positionId.
	 * 
	 * @exception None
	 * 
	 * @passCriteria should return null.
	 * 
	 */
	@Test
	public void next_null_case2() {
		long positionId = 1;
		MessageStreamer<String, String> messageStreamer = new MessageStreamer<>();
		messageStreamer.offer(new Message<>("Key", "MessageValue"));
		MessagePosition<String, String> messagePosition = messageStreamer.next(positionId);
		Assert.assertNull(messagePosition);
	}

	/**
	 * Interactive test that checks if {@link MessageStreamer#next(long)} called
	 * multiple times without client offering any more message.
	 * 
	 * @exception None
	 * 
	 * @passCriteria should return same {@link MessagePosition} instance if called
	 *               without offered any new messages.
	 * 
	 */
	@Test
	public void next_positive_case1() {
		long positionId = -1;
		MessageStreamer<String, String> messageStreamer = new MessageStreamer<>();
		messageStreamer.offer(new Message<>("Key", "MessageValue"));
		MessagePosition<String, String> previousMessagePosition = null;
		for (int i = 0; i < 5; i++) {
			MessagePosition<String, String> messagePosition = messageStreamer.next(positionId);
			if (previousMessagePosition != null) {
				Assert.assertEquals(previousMessagePosition, messagePosition);
			} else {
				previousMessagePosition = messagePosition;
			}
		}
	}

	/**
	 * Interactive test that checks if {@link MessageStreamer#next(long)} called
	 * with random positionId.
	 * 
	 * @exception None
	 * 
	 * @passCriteria should return null if no {@link MessagePosition} greater than
	 *               passed positionId else return the found one.
	 * 
	 */
	@Test
	public void next_positive_case2() {
		long positionId = -1;
		MessageStreamer<String, String> messageStreamer = new MessageStreamer<>();
		for (int i = 0; i < 5; i++) {
			messageStreamer.offer(new Message<>("Key-" + i, "MessageValue"));
		}
		MessagePosition<String, String> messagePosition = messageStreamer.next(positionId);
		assertNotNull(messagePosition);
		assertEquals("Key-0", messagePosition.getMessage().getKey());
		messagePosition = messageStreamer.next(3);
		assertNotNull(messagePosition);
		assertEquals("Key-4", messagePosition.getMessage().getKey());
		messagePosition = messageStreamer.next(3);
		assertNotNull(messagePosition);
		assertEquals("Key-4", messagePosition.getMessage().getKey());
		messagePosition = messageStreamer.next(7);
		assertNull(messagePosition);
	}

}
