package com.txs.notification.plugin.model;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Created by jarek on 04.10.16.
 */
public class ServerNode extends DefaultMutableTreeNode {
    private String serverName;

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    @Override
    public String toString() {
        return "<html><font color='gray'>"+getServerName()+"</font></html>";
    }
}
