package com.txs.notification.plugin;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.components.ServiceManager;
import com.txs.notification.plugin.model.ConfigProvider;

import javax.swing.*;
import java.awt.event.*;

public class Settings extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField serverUrlInput;
    private JCheckBox notificationsCheckBox;

    public Settings() {
        setupElements();
        ConfigProvider config = ServiceManager.getService(ConfigProvider.class);
        serverUrlInput.setText(config.getServerUrl());
        notificationsCheckBox.setSelected(config.getNotifications());
    }

    private void setupElements(){
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        ConfigProvider config = ServiceManager.getService(ConfigProvider.class);
        config.setServerUrl(serverUrlInput.getText());
        config.setNotifications(notificationsCheckBox.isSelected());
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
