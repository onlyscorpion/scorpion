package com.scorpion.huakerongtong.cipher.security;

import java.util.ArrayList;
import java.util.List;

import com.scorpion.huakerongtong.api.exception.ScorpionBaseException;

import ts.client.TsaClient;

import com.sdt.as.AsymDecData;
import com.sdt.as.AsymDecDataException;
import com.sdt.as.AsymEncData;
import com.sdt.as.AsymEncDataException;
import com.sdt.as.Sign;
import com.sdt.as.SignException;
import com.sdt.as.VerifySign;
import com.sdt.as.VerifySignException;

public class ScorpionDigitalEnvelopeImpl extends AbsScorpionDigitalEnvelope {
	
	private final static int BUF_SIZE = 16 * 1024;	//16KB
	
	private String appId;
	
	/**
	 * 加密数据的密级，默认值0
	 */
	private int secretLevel = 0;
	
	/**
	 * 发送方证书
	 */
	private byte[] senderCert;
	
	/**
	 * 接收方证书
	 */
	private byte[] receiverCert;
	
	/**
	 * 签名业务的类型，默认值为0
	 */
	private int businessType = 0;
	
	/**
	 * 时间戳服务器IP
	 */
	private String timestampIP = "127.0.0.1";

	/**
	 * 时间戳服务器端口号
	 */
	private int timestampPort = 7777;
	
	public int arithflag = 1;
	
	public String version = "1";
	
	public int randflag = 1;
	
	public int certReq = 1;

	
	@Override
	public byte[] encrypt(byte[] data) throws ScorpionBaseException {
		AsymEncData aed = null;
		try {
			//构造加密类
			aed = new AsymEncData(appId, secretLevel, data.length);
			
			//设置加密证书
			if(receiverCert != null){
				aed.setEncCert(1, receiverCert);
			}
			
			if(senderCert != null){
				aed.setEncCert(0, senderCert);
			}
			
			//加载加密数据
			byte []buf = new byte[BUF_SIZE];
			List<Object> list = new ArrayList<Object>();
			int count = (data.length - 1)/BUF_SIZE + 1;
			int length = 0;
			for(int i = 0 ; i < count ; i++){
				if(i != count - 1 || data.length % BUF_SIZE == 0){
					System.arraycopy(data, i * BUF_SIZE, buf, 0, BUF_SIZE);
					byte[] bytes = aed.updateData(buf);
					list.add(bytes);
					length += bytes.length;
				}else{
					buf = new byte[data.length % BUF_SIZE];
					System.arraycopy(data, i * BUF_SIZE, buf, 0, data.length % BUF_SIZE);
					byte[] bytes = aed.updateData(buf);
					list.add(bytes);
					length += bytes.length;
				}
			}
			
			//计算最后加密结果
			byte[] bytes = aed.finalData();
			list.add(bytes);
			length += bytes.length;
			
			//合并最后加密数据
			byte[] enDate = new byte[length];
			int pos = 0;
			for(Object obj : list){
				byte[] temp = (byte[])obj;
				if(pos == 0){
					System.arraycopy(temp, 0, enDate, 0, temp.length);
					pos += temp.length;
				}else{
					System.arraycopy(temp, 0, enDate, pos, temp.length);
					pos += temp.length;
				}
			}
			
			aed.freeResource();
			aed = null;
			
			return enDate;
		} catch (AsymEncDataException e) {
			throw new ScorpionBaseException(e);
		} finally{
			if(aed != null){
				try {
					aed.freeResource();
				} catch (AsymEncDataException e) {
					throw new ScorpionBaseException(e);
				}
			}
		}
	}

	@Override
	public byte[] decrypt(byte[] data) throws ScorpionBaseException {
		AsymDecData add = null;
		try {
			//构造解密类
			add = new AsymDecData(appId);
			
			//加载解密数据
			byte []buf = new byte[BUF_SIZE];
			List<Object> list = new ArrayList<Object>();
			int count = (data.length - 1)/BUF_SIZE + 1;
			int length = 0;
			for(int i = 0 ; i < count ; i++){
				if(i != count - 1 || data.length % BUF_SIZE == 0){
					System.arraycopy(data, i * BUF_SIZE, buf, 0, BUF_SIZE);
					byte[] bytes = add.updateData(buf);
					list.add(bytes);
					length += bytes.length;
				}else{
					buf = new byte[data.length % BUF_SIZE];
					System.arraycopy(data, i * BUF_SIZE, buf, 0, data.length % BUF_SIZE);
					byte[] bytes = add.updateData(buf);
					list.add(bytes);
					length += bytes.length;
				}
			}
			
			//计算最后加密结果
			byte[] bytes = add.finalData();
			list.add(bytes);
			length += bytes.length;
			
			//合并最后加密数据
			byte[] deDate = new byte[length];
			int pos = 0;
			for(Object obj : list){
				byte[] temp = (byte[])obj;
				if(pos == 0){
					System.arraycopy(temp, 0, deDate, 0, temp.length);
					pos += temp.length;
				}else{
					System.arraycopy(temp, 0, deDate, pos, temp.length);
					pos += temp.length;
				}
			}
			
			add.freeResource();
			add = null;
			
			return deDate;
		} catch (AsymDecDataException e) {
			throw new ScorpionBaseException(e);
		} finally{
			if(add != null){
				try {
					add.freeResource();
				} catch (AsymDecDataException e) {
					throw new ScorpionBaseException(e);
				}
			}
		}
	}

	@Override
	public byte[] sign(byte[] data) throws ScorpionBaseException {
		Sign sign = null;
		try{
			sign = new Sign(appId, businessType);
			byte []buf = new byte[BUF_SIZE];
			int count = (data.length - 1)/BUF_SIZE + 1;
			for(int i = 0 ; i < count ; i++){
				if(i != count - 1 || data.length % BUF_SIZE == 0){
					System.arraycopy(data, i * BUF_SIZE, buf, 0, BUF_SIZE);
				}else{
					buf = new byte[data.length % BUF_SIZE];
					System.arraycopy(data, i * BUF_SIZE, buf, 0, data.length % BUF_SIZE);
				}
				
				if(!sign.updateData(buf)){
					throw new SignException("底层加载签名数据异常");
				}
			}
			
			return sign.finalData();
		}catch (SignException e) {
			throw new ScorpionBaseException(e);
		} finally {
			try {
				if (sign != null) {
					sign.freeResource();
				}
			} catch (SignException e) {
				throw new ScorpionBaseException(e);
			}
		}
	}

	@Override
	public boolean verifySign(byte[] data, byte[] sign) throws ScorpionBaseException {
		VerifySign verify = null;
		try{
			verify = new VerifySign(appId, sign);
			byte []buf = new byte[BUF_SIZE];
			int count = (data.length - 1) / BUF_SIZE + 1;
			for(int i = 0 ; i < count ; i++){
				if(i != count - 1 || data.length % BUF_SIZE == 0){
					System.arraycopy(data, i * BUF_SIZE, buf, 0, BUF_SIZE);
				}else{
					buf = new byte[data.length % BUF_SIZE];
					System.arraycopy(data, i * BUF_SIZE, buf, 0, data.length % BUF_SIZE);
				}
				
				if(!verify.updateData(buf)){
					throw new VerifySignException("底层加载签名数据异常");
				}
			}
			
			return verify.finalData();
		}catch (VerifySignException e) {
			throw new ScorpionBaseException(e);
		} finally {
			try {
				if (verify != null) {
					verify.freeResource();
				}
			} catch (VerifySignException e) {
				throw new ScorpionBaseException(e);
			}
		}
	}
	
	@Override
	public byte[] timestamp(byte[] data) throws ScorpionBaseException{
		TsaClient tsa = new TsaClient();
		//以下set方法中的参数请应用支持可配置，便于项目实际部署时进行必要的调整
		tsa.setType("1");				// 设置请求类型1请求、2验证
		tsa.setData(data);				// 也可以调用setData设置原文数据
		tsa.setArithflag(arithflag);			// 设置摘要算法, 软接口不支持类型1,
		tsa.setIp(timestampIP);			// 设置tsa ip地址, 软库使用127.0.0.1
		tsa.setPort(timestampPort);		// 设置tsa端口
		tsa.setVersion(version); 			// 设置版本号
		tsa.setRandflag(randflag);  			// 设置随机数标志
		tsa.setCertReq(certReq);  			// 设置响应是否返回证书

		try {
			if (!tsa.dofinal()) {
				throw new ScorpionBaseException("底层时间戳返回失败");
			}
			
			//获取时间戳数据
			return tsa.getTsrData(); 
		} catch (Exception e) {
			throw new ScorpionBaseException(e);
		}
	}

	@Override
	public boolean verifyTimestamp(byte[] data, byte[] timestamp) throws ScorpionBaseException{
		TsaClient tsa = new TsaClient();
		//以下set方法中的参数请应用支持可配置，便于项目实际部署时进行必要的调整
		tsa.setTsrData(timestamp);
		tsa.setData(data);
		tsa.setType("2");// 设置请求类型1请求、2验证
		tsa.setArithflag(arithflag);// 设置摘要算法, 软接口不支持类型1, 同申请时间戳时保持一致
		tsa.setIp(timestampIP);// 设置tsa ip地址, 软库使用127.0.0.1
		tsa.setPort(timestampPort);// 设置tsa端口
		
		try {
			return tsa.dofinal();
		} catch (Exception e) {
			throw new ScorpionBaseException(e);
		}
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public int getSecretLevel() {
		return secretLevel;
	}

	public void setSecretLevel(int secretLevel) {
		this.secretLevel = secretLevel;
	}

	public byte[] getSenderCert() {
		return senderCert;
	}

	public void setSenderCert(byte[] senderCert) {
		this.senderCert = senderCert;
	}

	public byte[] getReceiverCert() {
		return receiverCert;
	}

	public void setReceiverCert(byte[] receiverCert) {
		this.receiverCert = receiverCert;
	}

	public int getBusinessType() {
		return businessType;
	}

	public void setBusinessType(int businessType) {
		this.businessType = businessType;
	}

	public String getTimestampIP() {
		return timestampIP;
	}

	public void setTimestampIP(String timestampIP) {
		this.timestampIP = timestampIP;
	}

	public int getTimestampPort() {
		return timestampPort;
	}

	public void setTimestampPort(int timestampPort) {
		this.timestampPort = timestampPort;
	}

	public int getArithflag() {
		return arithflag;
	}

	public void setArithflag(int arithflag) {
		this.arithflag = arithflag;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public int getRandflag() {
		return randflag;
	}

	public void setRandflag(int randflag) {
		this.randflag = randflag;
	}

	public int getCertReq() {
		return certReq;
	}

	public void setCertReq(int certReq) {
		this.certReq = certReq;
	}

}
