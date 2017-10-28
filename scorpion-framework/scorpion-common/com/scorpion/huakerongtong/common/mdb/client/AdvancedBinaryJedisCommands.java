package com.scorpion.huakerongtong.common.mdb.client;


import java.util.List;

/**
 * 
 * @author zcl
 *
 */
public interface AdvancedBinaryJedisCommands {

    List<byte[]> configGet(byte[] pattern);

    byte[] configSet(byte[] parameter, byte[] value);

    String slowlogReset();

    Long slowlogLen();

    List<byte[]> slowlogGetBinary();

    List<byte[]> slowlogGetBinary(long entries);

    Long objectRefcount(byte[] key);

    byte[] objectEncoding(byte[] key);

    Long objectIdletime(byte[] key);
}
