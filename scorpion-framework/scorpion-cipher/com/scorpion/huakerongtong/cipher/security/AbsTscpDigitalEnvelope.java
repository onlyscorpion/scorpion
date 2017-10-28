package com.scorpion.huakerongtong.cipher.security;

public abstract class AbsScorpionDigitalEnvelope implements IScorpionCipherDigitalEnvelope, IScorpionSignature, IScorpionTimestamp{
	
	public static AbsScorpionDigitalEnvelope getInstance(){
		return new ScorpionDigitalEnvelopeImpl();
	}
	
	public abstract void setAppId(String appId);

	public abstract void setSecretLevel(int secretLevel);

	public abstract void setSenderCert(byte[] cert);
	
	public abstract void setReceiverCert(byte[] cert);

	public abstract void setBusinessType(int businessType);
	
	public abstract void setTimestampIP(String ip);
	
	public abstract void setTimestampPort(int port);
	
	public abstract void setArithflag(int arithflag);
	
	public abstract void setVersion(String version);
	
	public abstract void setRandflag(int randflag);
	
	public abstract void setCertReq(int certReq);

}
