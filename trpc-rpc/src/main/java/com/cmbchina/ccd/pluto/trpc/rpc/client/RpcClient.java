package com.cmbchina.ccd.pluto.trpc.rpc.client;

import com.cmbchina.ccd.pluto.trpc.common.definition.ClientDefinition;
import com.cmbchina.ccd.pluto.trpc.common.strategy.loadbalance.LbStrategy;
import com.cmbchina.ccd.pluto.trpc.rpc.client.connection.RpcConnection;
import com.google.common.collect.Lists;
import com.google.common.net.HostAndPort;
import lombok.Getter;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by z674095 on 2019/3/28.
 */
public class RpcClient {

    private ReadWriteLock rwLock = new ReentrantReadWriteLock();

    private List<RpcConnection> rpcConnections = Lists.newArrayList();

    @Getter
    private String group;

    @Getter
    private String serviceName;

    private LbStrategy lbStrategy;

    private ClientDefinition clientDefinition;

    public RpcClient(String group, String serviceName, LbStrategy lbStrategy, ClientDefinition clientDefinition) {
        this.group = group;
        this.serviceName = serviceName;
        this.lbStrategy = lbStrategy;
        this.clientDefinition = clientDefinition;
    }

    public void add(String hostAndPort) {
        add(HostAndPort.fromString(hostAndPort));
    }

    public void add(HostAndPort hostAndPort) {
        rwLock.writeLock().lock();
        try {
            RpcConnection rpcConnection = new RpcConnection(this, clientDefinition, hostAndPort);
            Iterator<RpcConnection> iterator = rpcConnections.iterator();
            while (iterator.hasNext()) {
                if (iterator.next().equals(rpcConnection)) {
                    return;
                }
            }
            rpcConnections.add(rpcConnection);
        } finally {
            rwLock.writeLock().unlock();
        }
    }

    public void remove(HostAndPort hostAndPort) {
        rwLock.writeLock().lock();
        try {
            Iterator<RpcConnection> iterator = rpcConnections.iterator();
            while (iterator.hasNext()) {
                RpcConnection rpcConnection = iterator.next();
                if (rpcConnection.getHostAndPort().equals(hostAndPort)) {
                    iterator.remove();
                    rpcConnection.close();
                }
            }
        } finally {
            rwLock.writeLock().unlock();
        }
    }

    public RpcConnection get() {
        rwLock.readLock().lock();
        try {
            return lbStrategy.getResource(rpcConnections);
        } finally {
            rwLock.readLock().unlock();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RpcClient rpcClient = (RpcClient) o;

        if (!group.equals(rpcClient.group)) return false;
        return serviceName.equals(rpcClient.serviceName);

    }

    @Override
    public int hashCode() {
        int result = group.hashCode();
        result = 31 * result + serviceName.hashCode();
        return result;
    }

    public long getTimeout() {
        return this.clientDefinition.getTimeoutInMillis();
    }

}
