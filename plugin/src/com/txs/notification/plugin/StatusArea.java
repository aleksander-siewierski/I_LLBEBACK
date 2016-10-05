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
import com.txs.notification.plugin.tree.CheckBoxNodeEditor;
import com.txs.notification.plugin.tree.CheckBoxTreeCellRenderer;
import com.txs.notification.plugin.tree.CheckBoxTreeSelectionModel;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;

/**
 * Created by jarek on 29.09.16.
 */
public class StatusArea implements ToolWindowFactory {
    private JTree jobList;
    private JPanel pluginToolWindowContent;
    private JLabel urlValue;
    private JToolBar toolBar;
    private JButton settingsButton;
    private ToolWindow pluginToolWindow;
    private DefaultTreeModel listModel;
    private HashMap<String, Boolean> statusMap;

    public StatusArea() {
        ConfigProvider config = ServiceManager.getService(ConfigProvider.class);

        statusMap = new HashMap<String, Boolean>();
        statusMap.put("connectionOK", true);

        setupJobList();

        settingsButton.addActionListener(new ShowSettingsAction(urlValue, pluginToolWindowContent));

        if(config.getServerUrl() == null){
            rewriteSettings(config);
        }
        ApplicationManager.getApplication().executeOnPooledThread(new JobScheduleRunnableAction(jobList, urlValue, statusMap));
    }

    private void setupJobList() {
        jobList.setRootVisible(false);
        jobList.setCellRenderer(new CheckBoxTreeCellRenderer());
        jobList.setCellEditor(new CheckBoxNodeEditor(jobList));
        jobList.setEditable(true);
        jobList.setSelectionModel(new CheckBoxTreeSelectionModel());
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
        if(config.getServerUrl() == null){
            config.setServerUrl("");
        }
    }


}

