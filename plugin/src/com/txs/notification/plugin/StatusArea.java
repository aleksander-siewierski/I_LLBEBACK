package com.txs.notification.plugin;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.txs.notification.plugin.action.clearButtonAction;
import com.txs.notification.plugin.action.setUrlButtonAction;
import com.txs.notification.plugin.model.ListEntry;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * Created by jarek on 29.09.16.
 */
public class StatusArea implements ToolWindowFactory {
    private JList jobList;
    private JPanel pluginToolWindowContent;
    private JButton setUrlButton;
    private JLabel urlValue;
    private JButton clearButton;
    private ToolWindow pluginToolWindow;
    private DefaultListModel listModel;

    public StatusArea() {
        jobList.clearSelection();
        listModel = new DefaultListModel< ListEntry>();
        jobList.setModel(listModel);
        String url = PropertiesComponent.getInstance().getValue("IWillBeBack.serverUrl");
        urlValue.setText(url);

        setUrlButton.addActionListener(new setUrlButtonAction(urlValue, pluginToolWindowContent));
        clearButton.addActionListener(new clearButtonAction(urlValue, pluginToolWindowContent));

        ApplicationManager.getApplication().executeOnPooledThread(new InstallRunnable(listModel));

    }

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        pluginToolWindow = toolWindow;
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();

        Content content = contentFactory.createContent(pluginToolWindowContent, "", false);
        toolWindow.getContentManager().addContent(content);
    }


}

class InstallRunnable implements Runnable {
    private DefaultListModel listModel;

    public InstallRunnable(DefaultListModel listModel) {
        this.listModel = listModel;
    }

    @Override
    public void run() {
        NotificationWrapper vo = new NotificationWrapper();
        vo.info("Runnable job test!");

    }
}
