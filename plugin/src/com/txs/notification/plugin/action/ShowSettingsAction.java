package com.txs.notification.plugin.action;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.ui.Messages;
import com.txs.notification.plugin.NotificationWrapper;
import com.txs.notification.plugin.Settings;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by jarek on 30.09.16.
 */
public class ShowSettingsAction implements ActionListener {
    private JLabel urlValue;
    private JPanel pluginToolWindowContent;

    public ShowSettingsAction(JLabel urlValue, JPanel pluginToolWindowContent) {
        this.pluginToolWindowContent = pluginToolWindowContent;
        this.urlValue = urlValue;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Settings dialog = new Settings();
        dialog.pack();
        dialog.setLocationRelativeTo(pluginToolWindowContent);
        dialog.setVisible(true);
    }
}