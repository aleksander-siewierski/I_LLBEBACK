package com.txs.notification.plugin;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * Created by jarek on 29.09.16.
 */
public class StatusArea implements ToolWindowFactory {
    private JList jobList;
    private JToolBar toolBar;
    private JPanel pluginToolWindowContent;
    private ToolWindow pluginToolWindow;

    public StatusArea() {
        jobList.clearSelection();
        DefaultListModel listModel = (DefaultListModel) jobList.getModel();
        listModel.removeAllElements();
    }

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        pluginToolWindow = toolWindow;
        toolWindow.setIcon(new ImageIcon(getClass().getResource("/icon.png")));
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(pluginToolWindowContent, "", false);
        toolWindow.getContentManager().addContent(content);
    }
}
