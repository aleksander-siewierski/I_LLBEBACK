package com.txs.notification.plugin.tree;

import com.txs.notification.plugin.model.EntryNode;

import javax.swing.*;

/**
 * Created by jarek on 05.10.16.
 */
public class EntryCheckBox extends JCheckBox {
    EntryNode entry;

    public EntryNode getEntry() {
        return entry;
    }

    public void setEntry(EntryNode entry) {
        this.entry = entry;
    }
}
