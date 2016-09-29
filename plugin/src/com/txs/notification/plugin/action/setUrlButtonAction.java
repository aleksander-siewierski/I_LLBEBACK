package com.txs.notification.plugin.action;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.ui.Messages;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by jarek on 29.09.16.
 */
public class setUrlButtonAction implements ActionListener {
    private JLabel urlValue;
    private JPanel pluginToolWindowContent;

    public setUrlButtonAction(JLabel urlValue, JPanel pluginToolWindowContent) {
        this.urlValue = urlValue;
        this.pluginToolWindowContent = pluginToolWindowContent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String txt= Messages.showInputDialog(pluginToolWindowContent, "Enter server url (without http://)", "", Messages.getQuestionIcon());
        if(txt == null || txt.equals("")){
            return;
        }
        PropertiesComponent.getInstance().setValue("IWillBeBack.serverUrl", txt);
        urlValue.setText(txt);
    }
}
