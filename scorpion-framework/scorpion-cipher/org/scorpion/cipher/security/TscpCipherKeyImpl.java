package org.scorpion.cipher.security;

import java.security.Key;

import javax.crypto.Cipher;

import org.scorpion.api.exception.TscpBaseException;

public class TscpCipherKeyImpl implements ITscpCipherKey{

	@Override
	public byte[] encrypt(Key key, byte[] data) throws TscpBaseException {
		try{
			Cipher cipher = Cipher.getInstance(key.getAlgorithm());
			cipher.init(Cipher.ENCRYPT_MODE, key);
			return cipher.doFinal(data);
//			return data;
		}catch(Exception e){
			throw new TscpBaseException("TSCP-6020:encrypt exception!", e);
		}
	}

	@Override
	public byte[] decrypt(Key key, byte[] data) throws TscpBaseException {
		try{
			Cipher cipher = Cipher.getInstance(key.getAlgorithm());
			cipher.init(Cipher.DECRYPT_MODE, key);
			return cipher.doFinal(data);
//			return data;
		}catch(Exception e){
			throw new TscpBaseException("TSCP-6021:decrypt exception!", e);
		}
	}
}
