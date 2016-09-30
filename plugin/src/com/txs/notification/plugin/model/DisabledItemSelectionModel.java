package com.txs.notification.plugin.model;

import javax.swing.*;

/**
 * Created by jarek on 30.09.16.
 */
public class DisabledItemSelectionModel extends DefaultListSelectionModel {

    @Override
    public void setSelectionInterval(int index0, int index1) {
        super.setSelectionInterval(-1, -1);
    }
}
