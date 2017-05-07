package org.scorpion.common.mdb.client;

import java.net.URI;

import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.commons.pool.impl.GenericObjectPool.Config;
import org.scorpion.common.mdb.client.util.Pool;

public class JedisPool extends Pool<TscpRDClient> {

    public JedisPool(final Config poolConfig, final String host) {
        this(poolConfig, host, Protocol.DEFAULT_PORT, Protocol.DEFAULT_TIMEOUT, null, Protocol.DEFAULT_DATABASE);
    }

    public JedisPool(String host, int port) {
        this(new Config(), host, port, Protocol.DEFAULT_TIMEOUT, null, Protocol.DEFAULT_DATABASE);
    }

    public JedisPool(final String host) {
	URI uri = URI.create(host);
	if (uri.getScheme() != null && uri.getScheme().equals("redis")) {
	    String h = uri.getHost();
	    int port = uri.getPort();
	    String password = uri.getUserInfo().split(":", 2)[1];
	    int database = Integer.parseInt(uri.getPath().split("/", 2)[1]);
	    this.internalPool = new GenericObjectPool(new JedisFactory(h, port,
		    Protocol.DEFAULT_TIMEOUT, password, database), new Config());
	} else {
	    this.internalPool = new GenericObjectPool(new JedisFactory(host,
		    Protocol.DEFAULT_PORT, Protocol.DEFAULT_TIMEOUT, null,
		    Protocol.DEFAULT_DATABASE), new Config());
	}
    }

    public JedisPool(final URI uri) {
	String h = uri.getHost();
	int port = uri.getPort();
	String password = uri.getUserInfo().split(":", 2)[1];
	int database = Integer.parseInt(uri.getPath().split("/", 2)[1]);
	this.internalPool = new GenericObjectPool(new JedisFactory(h, port,
		Protocol.DEFAULT_TIMEOUT, password, database), new Config());
    }
    
    public JedisPool(final Config poolConfig, final String host, int port,
            int timeout, final String password) {
        this(poolConfig, host, port, timeout, password, Protocol.DEFAULT_DATABASE);
    }

    public JedisPool(final Config poolConfig, final String host, final int port) {
        this(poolConfig, host, port, Protocol.DEFAULT_TIMEOUT, null, Protocol.DEFAULT_DATABASE);
    }

    public JedisPool(final Config poolConfig, final String host, final int port, final int timeout) {
        this(poolConfig, host, port, timeout, null, Protocol.DEFAULT_DATABASE);
    }

    public JedisPool(final Config poolConfig, final String host, int port, int timeout, final String password,
                     final int database) {
        super(poolConfig, new JedisFactory(host, port, timeout, password, database));
    }


    public void returnBrokenResource(final BinaryJedis resource) {
    	returnBrokenResourceObject(resource);
    }
    
    public void returnResource(final BinaryJedis resource) {
    	returnResourceObject(resource);
    }   
}
