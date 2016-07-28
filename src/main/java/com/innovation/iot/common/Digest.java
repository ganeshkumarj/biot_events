package com.innovation.iot.common;

import java.util.Date;
import org.apache.commons.codec.digest.DigestUtils;
import com.innovation.iot.domain.MessageContent;

public class Digest {
	public String encrypt(MessageContent content) {
		String toHex = content.getContent() + ":" + content.getImage() + ":" + content.getInsertion();
		return DigestUtils.sha1Hex(toHex);
	}
	
	public static void main(String[] args) {
		MessageContent content1 = new MessageContent("Hi how are you", "gamification", new Date() );
		MessageContent content2 = new MessageContent("Hi how are you.", "gamification", new Date() );
		MessageContent content3 = new MessageContent("Hi how are you", "gamification", new Date() );
		Digest digest = new Digest();
		digest.encrypt(content1);
		digest.encrypt(content2);
		digest.encrypt(content3);
	}
}
