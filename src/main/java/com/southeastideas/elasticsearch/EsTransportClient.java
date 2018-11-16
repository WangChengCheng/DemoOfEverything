package com.southeastideas.elasticsearch;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * create es client
 *
 * Created by wangchengcheng on 2018-11-16
 */

public class EsTransportClient {
    private static final Logger LOG = LoggerFactory.getLogger(EsTransportClient.class);
    private static Settings settings;
    private TransportClient client = null;

    public EsTransportClient(String ipListStr, int port, String clusterName) {
        settings = Settings.builder().put("cluster.name", clusterName).build();
        try {
            String[] ipList = ipListStr.split(",");
            InetSocketTransportAddress[] addresses = new InetSocketTransportAddress[ipList.length];
            for (int i = 0; i < ipList.length; i++) {
                addresses[i] = new InetSocketTransportAddress(InetAddress.getByName(ipList[i]), port);
                LOG.info("put ip address to client, ip and port is " + ipList[i] + ":" + port);
            }
            client = new PreBuiltTransportClient(settings).addTransportAddresses(addresses);
        } catch (UnknownHostException e) {
            LOG.error("failed to getClient(). Exception=" + e.getMessage());
        }
    }

    public Client getClient() {
        return client;
    }

    public synchronized void closetClient() {
        LOG.debug("enter EsTransportClient.closetClient()");
        if (null != client) {
            client.close();
            client = null;
        }
        LOG.debug("success EsTransportClient.closetClient()");
    }

    @Override
    protected void finalize() throws Throwable {
        LOG.debug("enter EsTransportClient.finalize()");
        super.finalize();
        closetClient();
        LOG.debug("finish EsTransportClient.finalize()");
    }
}
