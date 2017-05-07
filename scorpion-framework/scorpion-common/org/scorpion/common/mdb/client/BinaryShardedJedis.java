package org.scorpion.common.mdb.client;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.scorpion.common.mdb.client.BinaryClient.LIST_POSITION;
import org.scorpion.common.mdb.client.util.Hashing;
import org.scorpion.common.mdb.client.util.Sharded;

public class BinaryShardedJedis extends Sharded<TscpRDClient, JedisShardInfo>
	implements BinaryJedisCommands {
    public BinaryShardedJedis(List<JedisShardInfo> shards) {
	super(shards);
    }

    public BinaryShardedJedis(List<JedisShardInfo> shards, Hashing algo) {
	super(shards, algo);
    }

    public BinaryShardedJedis(List<JedisShardInfo> shards, Pattern keyTagPattern) {
	super(shards, keyTagPattern);
    }

    public BinaryShardedJedis(List<JedisShardInfo> shards, Hashing algo,
	    Pattern keyTagPattern) {
	super(shards, algo, keyTagPattern);
    }

    public void disconnect() {
    for (TscpRDClient jedis : getAllShards()) {
        jedis.quit();
        jedis.disconnect();
    }
    }

    protected TscpRDClient create(JedisShardInfo shard) {
	return new TscpRDClient(shard);
    }

    public String set(byte[] key, byte[] value) {
	TscpRDClient j = getShard(key);
	return j.set(key, value);
    }

    public byte[] get(byte[] key) {
	TscpRDClient j = getShard(key);
	return j.get(key);
    }

    public Boolean exists(byte[] key) {
	TscpRDClient j = getShard(key);
	return j.exists(key);
    }

    public String type(byte[] key) {
	TscpRDClient j = getShard(key);
	return j.type(key);
    }

    public Long expire(byte[] key, int seconds) {
	TscpRDClient j = getShard(key);
	return j.expire(key, seconds);
    }

    public Long expireAt(byte[] key, long unixTime) {
	TscpRDClient j = getShard(key);
	return j.expireAt(key, unixTime);
    }

    public Long ttl(byte[] key) {
	TscpRDClient j = getShard(key);
	return j.ttl(key);
    }

    public byte[] getSet(byte[] key, byte[] value) {
	TscpRDClient j = getShard(key);
	return j.getSet(key, value);
    }

    public Long setnx(byte[] key, byte[] value) {
	TscpRDClient j = getShard(key);
	return j.setnx(key, value);
    }

    public String setex(byte[] key, int seconds, byte[] value) {
	TscpRDClient j = getShard(key);
	return j.setex(key, seconds, value);
    }

    public Long decrBy(byte[] key, long integer) {
	TscpRDClient j = getShard(key);
	return j.decrBy(key, integer);
    }

    public Long decr(byte[] key) {
	TscpRDClient j = getShard(key);
	return j.decr(key);
    }

    public Long del(byte[] key) {
        TscpRDClient j = getShard(key);
        return j.del(key);
    }
    
    public Long incrBy(byte[] key, long integer) {
	TscpRDClient j = getShard(key);
	return j.incrBy(key, integer);
    }

    public Long incr(byte[] key) {
	TscpRDClient j = getShard(key);
	return j.incr(key);
    }

    public Long append(byte[] key, byte[] value) {
	TscpRDClient j = getShard(key);
	return j.append(key, value);
    }

    public byte[] substr(byte[] key, int start, int end) {
	TscpRDClient j = getShard(key);
	return j.substr(key, start, end);
    }

    public Long hset(byte[] key, byte[] field, byte[] value) {
	TscpRDClient j = getShard(key);
	return j.hset(key, field, value);
    }

    public byte[] hget(byte[] key, byte[] field) {
	TscpRDClient j = getShard(key);
	return j.hget(key, field);
    }

    public Long hsetnx(byte[] key, byte[] field, byte[] value) {
	TscpRDClient j = getShard(key);
	return j.hsetnx(key, field, value);
    }

    public String hmset(byte[] key, Map<byte[], byte[]> hash) {
	TscpRDClient j = getShard(key);
	return j.hmset(key, hash);
    }

    public List<byte[]> hmget(byte[] key, byte[]... fields) {
	TscpRDClient j = getShard(key);
	return j.hmget(key, fields);
    }

    public Long hincrBy(byte[] key, byte[] field, long value) {
	TscpRDClient j = getShard(key);
	return j.hincrBy(key, field, value);
    }

    public Boolean hexists(byte[] key, byte[] field) {
	TscpRDClient j = getShard(key);
	return j.hexists(key, field);
    }

    public Long hdel(byte[] key, byte[]... fields) {
	TscpRDClient j = getShard(key);
	return j.hdel(key, fields);
    }

    public Long hlen(byte[] key) {
	TscpRDClient j = getShard(key);
	return j.hlen(key);
    }

    public Set<byte[]> hkeys(byte[] key) {
	TscpRDClient j = getShard(key);
	return j.hkeys(key);
    }

    public Collection<byte[]> hvals(byte[] key) {
	TscpRDClient j = getShard(key);
	return j.hvals(key);
    }

    public Map<byte[], byte[]> hgetAll(byte[] key) {
	TscpRDClient j = getShard(key);
	return j.hgetAll(key);
    }

    public Long rpush(byte[] key, byte[]... strings) {
	TscpRDClient j = getShard(key);
	return j.rpush(key, strings);
    }

    public Long lpush(byte[] key, byte[]... strings) {
	TscpRDClient j = getShard(key);
	return j.lpush(key, strings);
    }

    public Long strlen(final byte[] key) {
    TscpRDClient j = getShard(key);
    return j.strlen(key);
    }

    public Long lpushx(byte[] key, byte[]... string) {
        TscpRDClient j = getShard(key);
        return j.lpushx(key, string);
    }

    public Long persist(final byte[] key) {
        TscpRDClient j = getShard(key);
    	return j.persist(key);
    }

    public Long rpushx(byte[] key, byte[]... string) {
        TscpRDClient j = getShard(key);
        return j.rpushx(key, string);
    }

    public Long llen(byte[] key) {
	TscpRDClient j = getShard(key);
	return j.llen(key);
    }

    public List<byte[]> lrange(byte[] key, long start, long end) {
	TscpRDClient j = getShard(key);
	return j.lrange(key, start, end);
    }

    public String ltrim(byte[] key, long start, long end) {
	TscpRDClient j = getShard(key);
	return j.ltrim(key, start, end);
    }

    public byte[] lindex(byte[] key, long index) {
	TscpRDClient j = getShard(key);
	return j.lindex(key, index);
    }

    public String lset(byte[] key, long index, byte[] value) {
	TscpRDClient j = getShard(key);
	return j.lset(key, index, value);
    }

    public Long lrem(byte[] key, long count, byte[] value) {
	TscpRDClient j = getShard(key);
	return j.lrem(key, count, value);
    }

    public byte[] lpop(byte[] key) {
	TscpRDClient j = getShard(key);
	return j.lpop(key);
    }

    public byte[] rpop(byte[] key) {
	TscpRDClient j = getShard(key);
	return j.rpop(key);
    }

    public Long sadd(byte[] key, byte[]... members) {
	TscpRDClient j = getShard(key);
	return j.sadd(key, members);
    }

    public Set<byte[]> smembers(byte[] key) {
	TscpRDClient j = getShard(key);
	return j.smembers(key);
    }

    public Long srem(byte[] key, byte[]... members) {
	TscpRDClient j = getShard(key);
	return j.srem(key, members);
    }

    public byte[] spop(byte[] key) {
	TscpRDClient j = getShard(key);
	return j.spop(key);
    }

    public Long scard(byte[] key) {
	TscpRDClient j = getShard(key);
	return j.scard(key);
    }

    public Boolean sismember(byte[] key, byte[] member) {
	TscpRDClient j = getShard(key);
	return j.sismember(key, member);
    }

    public byte[] srandmember(byte[] key) {
	TscpRDClient j = getShard(key);
	return j.srandmember(key);
    }

    public Long zadd(byte[] key, double score, byte[] member) {
	TscpRDClient j = getShard(key);
	return j.zadd(key, score, member);
    }

    public Long zadd(byte[] key, Map<Double, byte[]> scoreMembers) {
	TscpRDClient j = getShard(key);
	return j.zadd(key, scoreMembers);
    }

    public Set<byte[]> zrange(byte[] key, long start, long end) {
	TscpRDClient j = getShard(key);
	return j.zrange(key, start, end);
    }

    public Long zrem(byte[] key, byte[]... members) {
	TscpRDClient j = getShard(key);
	return j.zrem(key, members);
    }

    public Double zincrby(byte[] key, double score, byte[] member) {
	TscpRDClient j = getShard(key);
	return j.zincrby(key, score, member);
    }

    public Long zrank(byte[] key, byte[] member) {
	TscpRDClient j = getShard(key);
	return j.zrank(key, member);
    }

    public Long zrevrank(byte[] key, byte[] member) {
	TscpRDClient j = getShard(key);
	return j.zrevrank(key, member);
    }

    public Set<byte[]> zrevrange(byte[] key, long start, long end) {
	TscpRDClient j = getShard(key);
	return j.zrevrange(key, start, end);
    }

    public Set<Tuple> zrangeWithScores(byte[] key, long start, long end) {
	TscpRDClient j = getShard(key);
	return j.zrangeWithScores(key, start, end);
    }

    public Set<Tuple> zrevrangeWithScores(byte[] key, long start, long end) {
	TscpRDClient j = getShard(key);
	return j.zrevrangeWithScores(key, start, end);
    }

    public Long zcard(byte[] key) {
	TscpRDClient j = getShard(key);
	return j.zcard(key);
    }

    public Double zscore(byte[] key, byte[] member) {
	TscpRDClient j = getShard(key);
	return j.zscore(key, member);
    }

    public List<byte[]> sort(byte[] key) {
	TscpRDClient j = getShard(key);
	return j.sort(key);
    }

    public List<byte[]> sort(byte[] key, SortingParams sortingParameters) {
	TscpRDClient j = getShard(key);
	return j.sort(key, sortingParameters);
    }

    public Long zcount(byte[] key, double min, double max) {
	TscpRDClient j = getShard(key);
	return j.zcount(key, min, max);
    }
    
    public Long zcount(byte[] key, byte[] min, byte[] max) {
	TscpRDClient j = getShard(key);
	return j.zcount(key, min, max);
    }

    public Set<byte[]> zrangeByScore(byte[] key, double min, double max) {
	TscpRDClient j = getShard(key);
	return j.zrangeByScore(key, min, max);
    }

    public Set<byte[]> zrangeByScore(byte[] key, double min, double max,
	    int offset, int count) {
	TscpRDClient j = getShard(key);
	return j.zrangeByScore(key, min, max, offset, count);
    }

    public Set<Tuple> zrangeByScoreWithScores(byte[] key, double min, double max) {
	TscpRDClient j = getShard(key);
	return j.zrangeByScoreWithScores(key, min, max);
    }

    public Set<Tuple> zrangeByScoreWithScores(byte[] key, double min,
	    double max, int offset, int count) {
	TscpRDClient j = getShard(key);
	return j.zrangeByScoreWithScores(key, min, max, offset, count);
    }

    public Set<byte[]> zrangeByScore(byte[] key, byte[] min, byte[] max) {
        TscpRDClient j = getShard(key);
        return j.zrangeByScore(key, min, max);
    }

    public Set<Tuple> zrangeByScoreWithScores(byte[] key, byte[] min, byte[] max) {
	TscpRDClient j = getShard(key);
	return j.zrangeByScoreWithScores(key, min, max);
    }

    public Set<Tuple> zrangeByScoreWithScores(byte[] key, byte[] min,
    		byte[] max, int offset, int count) {
	TscpRDClient j = getShard(key);
	return j.zrangeByScoreWithScores(key, min, max, offset, count);
    }

    public Set<byte[]> zrangeByScore(byte[] key, byte[] min, byte[] max, int offset, int count) {
        TscpRDClient j = getShard(key);
        return j.zrangeByScore(key, min, max, offset, count);
    }

    public Set<byte[]> zrevrangeByScore(byte[] key, double max, double min) {
	TscpRDClient j = getShard(key);
	return j.zrevrangeByScore(key, max, min);
    }

    public Set<byte[]> zrevrangeByScore(byte[] key, double max, double min,
	    int offset, int count) {
	TscpRDClient j = getShard(key);
	return j.zrevrangeByScore(key, max, min, offset, count);
    }

    public Set<Tuple> zrevrangeByScoreWithScores(byte[] key, double max,
	    double min) {
	TscpRDClient j = getShard(key);
	return j.zrevrangeByScoreWithScores(key, max, min);
    }

    public Set<Tuple> zrevrangeByScoreWithScores(byte[] key, double max,
	    double min, int offset, int count) {
	TscpRDClient j = getShard(key);
	return j.zrevrangeByScoreWithScores(key, max, min, offset, count);
    }
    
    public Set<byte[]> zrevrangeByScore(byte[] key, byte[] max, byte[] min) {
	TscpRDClient j = getShard(key);
	return j.zrevrangeByScore(key, max, min);
    }

    public Set<byte[]> zrevrangeByScore(byte[] key, byte[] max, byte[] min,
	    int offset, int count) {
	TscpRDClient j = getShard(key);
	return j.zrevrangeByScore(key, max, min, offset, count);
    }

    public Set<Tuple> zrevrangeByScoreWithScores(byte[] key, byte[] max,
    		byte[] min) {
	TscpRDClient j = getShard(key);
	return j.zrevrangeByScoreWithScores(key, max, min);
    }

    public Set<Tuple> zrevrangeByScoreWithScores(byte[] key, byte[] max,
    		byte[] min, int offset, int count) {
	TscpRDClient j = getShard(key);
	return j.zrevrangeByScoreWithScores(key, max, min, offset, count);
    }

    public Long zremrangeByRank(byte[] key, long start, long end) {
	TscpRDClient j = getShard(key);
	return j.zremrangeByRank(key, start, end);
    }

    public Long zremrangeByScore(byte[] key, double start, double end) {
	TscpRDClient j = getShard(key);
	return j.zremrangeByScore(key, start, end);
    }

    public Long zremrangeByScore(byte[] key, byte[] start, byte[] end) {
	TscpRDClient j = getShard(key);
	return j.zremrangeByScore(key, start, end);
    }

    public Long linsert(byte[] key, LIST_POSITION where, byte[] pivot,
	    byte[] value) {
	TscpRDClient j = getShard(key);
	return j.linsert(key, where, pivot, value);
    }

    @Deprecated
    public List<Object> pipelined(ShardedJedisPipeline shardedJedisPipeline) {
	shardedJedisPipeline.setShardedJedis(this);
	shardedJedisPipeline.execute();
	return shardedJedisPipeline.getResults();
    }

    public ShardedJedisPipeline pipelined() {
	ShardedJedisPipeline pipeline = new ShardedJedisPipeline();
	pipeline.setShardedJedis(this);
	return pipeline;
    }

    public Long objectRefcount(byte[] key) {
	TscpRDClient j = getShard(key);
	return j.objectRefcount(key);
    }

    public byte[] objectEncoding(byte[] key) {
	TscpRDClient j = getShard(key);
	return j.objectEncoding(key);
    }

    public Long objectIdletime(byte[] key) {
	TscpRDClient j = getShard(key);
	return j.objectIdletime(key);
    }

    public Boolean setbit(byte[] key, long offset, boolean value) {
    TscpRDClient j = getShard(key);
    return j.setbit(key, offset, value);
    }

    public Boolean setbit(byte[] key, long offset, byte[] value) {
    TscpRDClient j = getShard(key);
    return j.setbit(key, offset, value);
    }

    public Boolean getbit(byte[] key, long offset) {
    TscpRDClient j = getShard(key);
    return j.getbit(key, offset);
    }

    public Long setrange(byte[] key, long offset, byte[] value) {
    TscpRDClient j = getShard(key);
    return j.setrange(key, offset, value);
    }

    public byte[] getrange(byte[] key, long startOffset, long endOffset) {
    TscpRDClient j = getShard(key);
    return j.getrange(key, startOffset, endOffset);
    }

    public Long move(byte[] key, int dbIndex) {
        TscpRDClient j = getShard(key);
        return j.move(key, dbIndex);
    }

    public byte[] echo(byte[] arg) {
        TscpRDClient j = getShard(arg);
        return j.echo(arg);
    }

    public List<byte[]> brpop(byte[] arg) {
        TscpRDClient j = getShard(arg);
        return j.brpop(arg);
    }

    public List<byte[]> blpop(byte[] arg) {
        TscpRDClient j = getShard(arg);
        return j.blpop(arg);
    }

    public Long bitcount(byte[] key) {
        TscpRDClient j = getShard(key);
        return j.bitcount(key);
    }

    public Long bitcount(byte[] key, long start, long end) {
        TscpRDClient j = getShard(key);
        return j.bitcount(key, start, end);
    }
}