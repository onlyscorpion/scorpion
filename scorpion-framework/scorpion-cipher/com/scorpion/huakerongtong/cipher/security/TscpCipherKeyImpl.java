package com.scorpion.huakerongtong.cipher.security;

import java.security.Key;

import javax.crypto.Cipher;

import com.scorpion.huakerongtong.api.exception.ScorpionBaseException;

public class ScorpionCipherKeyImpl implements IScorpionCipherKey{

	@Override
	public byte[] encrypt(Key key, byte[] data) throws ScorpionBaseException {
		try{
			Cipher cipher = Cipher.getInstance(key.getAlgorithm());
			cipher.init(Cipher.ENCRYPT_MODE, key);
			return cipher.doFinal(data);
//			return data;
		}catch(Exception e){
			throw new ScorpionBaseException("scorpion-6020:encrypt exception!", e);
		}
	}

	@Override
	public byte[] decrypt(Key key, byte[] data) throws ScorpionBaseException {
		try{
			Cipher cipher = Cipher.getInstance(key.getAlgorithm());
			cipher.init(Cipher.DECRYPT_MODE, key);
			return cipher.doFinal(data);
//			return data;
		}catch(Exception e){
			throw new ScorpionBaseException("scorpion-6021:decrypt exception!", e);
		}
	}
}
