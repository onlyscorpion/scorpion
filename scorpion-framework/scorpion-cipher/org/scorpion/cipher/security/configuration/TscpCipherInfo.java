package org.scorpion.cipher.security.configuration;

public class ScorpionCipherInfo {
	
	private String name;
	private String type;
	private String algorithm;
	private String signAlgorithm;
	private Integer keyLength;
	private String cerPath;
	private String cerType;
	private String keyStorePath;
	private String keyStoreType;
	private String alias;
	private String password;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAlgorithm() {
		return algorithm;
	}
	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}
	public String getSignAlgorithm() {
		return signAlgorithm;
	}
	public void setSignAlgorithm(String signAlgorithm) {
		this.signAlgorithm = signAlgorithm;
	}
	public Integer getKeyLength() {
		return keyLength;
	}
	public void setKeyLength(Integer keyLength) {
		this.keyLength = keyLength;
	}
	public String getCerPath() {
		return cerPath;
	}
	public void setCerPath(String cerPath) {
		this.cerPath = cerPath;
	}
	public String getCerType() {
		return cerType;
	}
	public void setCerType(String cerType) {
		this.cerType = cerType;
	}
	public String getKeyStorePath() {
		return keyStorePath;
	}
	public void setKeyStorePath(String keyStorePath) {
		this.keyStorePath = keyStorePath;
	}
	public String getKeyStoreType() {
		return keyStoreType;
	}
	public void setKeyStoreType(String keyStoreType) {
		this.keyStoreType = keyStoreType;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean validate(){
		return true;
	}
}
