package com.txs.notification.plugin.action;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.ui.Messages;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by jarek on 29.09.16.
 */
public class clearButtonAction implements ActionListener {
    private JLabel urlValue;
    private JPanel pluginToolWindowContent;

    public clearButtonAction(JLabel urlValue, JPanel pluginToolWindowContent) {
        this.urlValue = urlValue;
        this.pluginToolWindowContent = pluginToolWindowContent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
            PropertiesComponent.getInstance().setValue("IWillBeBack.serverUrl", "");
            urlValue.setText("");
    }
}
