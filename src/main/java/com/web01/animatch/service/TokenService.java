package com.web01.animatch.service;

import java.nio.ByteBuffer;
import java.security.SecureRandom;

import org.apache.commons.codec.binary.Base64;

/**
 * トークンサービスクラス
 * @author Tsuji
 * @version 1.0
 */
public class TokenService {

 /**
  * 自動認証用トークン生成
  * @return 自動認証用トークン
  */
 public String generateAutoAuthToken() {
  ByteBuffer buf = ByteBuffer.allocate(16);

  byte[] randomBytes = new byte[16];
  SecureRandom secureRandom = new SecureRandom();
  secureRandom.nextBytes(randomBytes);
  buf.put(randomBytes);

  return Base64.encodeBase64URLSafeString(buf.array());
 }
}
