package com.txs.notification.plugin.action;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.ui.Messages;
import com.txs.notification.plugin.model.ListEntry;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by jarek on 29.09.16.
 */
public class ClearButtonAction implements ActionListener {
    private JLabel urlValue;
    private JList jobList;
    private JPanel pluginToolWindowContent;

    public ClearButtonAction(JLabel urlValue, JList jobList, JPanel pluginToolWindowContent) {
        this.urlValue = urlValue;
        this.jobList = jobList;
        this.pluginToolWindowContent = pluginToolWindowContent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
            PropertiesComponent.getInstance().setValue("IWillBeBack.serverUrl", "");
            urlValue.setText("");
            jobList.setModel(new DefaultListModel<ListEntry>());
            urlValue.setIcon(new ImageIcon(getClass().getResource("/icon13.png")));
    }
}
