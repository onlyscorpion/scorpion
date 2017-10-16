package org.scorpion.common.mdb.client;

import org.apache.commons.pool.BasePoolableObjectFactory;

/**
 * PoolableObjectFactory custom impl.
 */
class JedisFactory extends BasePoolableObjectFactory {
    private final String host;
    private final int port;
    private final int timeout;
    private final String password;
    private final int database;

    public JedisFactory(final String host, final int port,
            final int timeout, final String password, final int database) {
        super();
        this.host = host;
        this.port = port;
        this.timeout = timeout;
        this.password = password;
        this.database = database;
    }

    public Object makeObject() throws Exception {
        final ScorpionRDClient jedis = new ScorpionRDClient(this.host, this.port, this.timeout);

        jedis.connect();
        if (null != this.password) {
            jedis.auth(this.password);
        }
        if( database != 0 ) {
            jedis.select(database);
        }
        
        return jedis;
    }
    
    @Override
    public void activateObject(Object obj) throws Exception {
		if (obj instanceof ScorpionRDClient) {
            final ScorpionRDClient jedis = (ScorpionRDClient)obj;
            if (jedis.getDB() != database) {
            	jedis.select(database);
            }
		}
    }

    public void destroyObject(final Object obj) throws Exception {
        if (obj instanceof ScorpionRDClient) {
            final ScorpionRDClient jedis = (ScorpionRDClient) obj;
            if (jedis.isConnected()) {
                try {
                    try {
                        jedis.quit();
                    } catch (Exception e) {
                    }
                    jedis.disconnect();
                } catch (Exception e) {

                }
            }
        }
    }

    public boolean validateObject(final Object obj) {
        if (obj instanceof ScorpionRDClient) {
            final ScorpionRDClient jedis = (ScorpionRDClient) obj;
            try {
                return jedis.isConnected() && jedis.ping().equals("PONG");
            } catch (final Exception e) {
                return false;
            }
        } else {
            return false;
        }
    }
}