package com.txs.notification.plugin;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.intellij.util.containers.HashMap;
import com.txs.notification.plugin.action.JobScheduleRunnableAction;
import com.txs.notification.plugin.action.ShowSettingsAction;
import com.txs.notification.plugin.model.ConfigProvider;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;

/**
 * Created by jarek on 29.09.16.
 */
public class StatusArea implements ToolWindowFactory {
    private JTree jobList;
    private JPanel pluginToolWindowContent;
    private JButton setUrlButton;
    private JLabel urlValue;
    private JButton clearButton;
    private JToolBar toolBar;
    private JButton settingsButton;
    private ToolWindow pluginToolWindow;
    private DefaultTreeModel listModel;
    private HashMap<String, Boolean> statusMap;

    public StatusArea() {
        statusMap = new HashMap<String, Boolean>();
        statusMap.put("connectionOK", true);

        jobList.setRootVisible(false);

        settingsButton.addActionListener(new ShowSettingsAction(urlValue, pluginToolWindowContent));

        ConfigProvider config = ServiceManager.getService(ConfigProvider.class);
        if(config.getServerUrl() == null){
            rewriteSettings(config);
        }
        ApplicationManager.getApplication().executeOnPooledThread(new JobScheduleRunnableAction(jobList, urlValue, statusMap));
    }

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        pluginToolWindow = toolWindow;
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(pluginToolWindowContent, "", false);
        toolWindow.getContentManager().addContent(content);
    }

    public void rewriteSettings(ConfigProvider config){
        config.setServerUrl(PropertiesComponent.getInstance().getValue("IWillBeBack.serverUrl", ""));
        config.setNotifications(PropertiesComponent.getInstance().getBoolean("IWillBeBack.notifications"));
    }


}

