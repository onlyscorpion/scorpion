package com.scorpion.huakerongtong.common.mdb.client;

import com.scorpion.huakerongtong.common.mdb.client.exception.JedisException;

public abstract class TransactionBlock extends Transaction {
    public TransactionBlock(Client client) {
	super(client);
    }

    public TransactionBlock() {
    }

    public abstract void execute() throws JedisException;

    public void setClient(Client client) {
	    this.client = client;
    }
}
