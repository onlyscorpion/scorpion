package com.scorpion.huakerongtong.cipher.security;

import java.security.PrivateKey;
import java.security.PublicKey;

public interface IScorpionAsymmetryKey {
	
	public PrivateKey getPrivateKey();
	
	public PublicKey getPublicKey();

}
