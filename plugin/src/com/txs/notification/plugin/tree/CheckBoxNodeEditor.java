package com.txs.notification.plugin.tree;

import com.intellij.openapi.components.ServiceManager;
import com.txs.notification.plugin.model.ConfigProvider;
import com.txs.notification.plugin.model.EntryNode;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellEditor;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.util.EventObject;

/**
 * Created by jarek on 05.10.16.
 */
public class CheckBoxNodeEditor extends AbstractCellEditor implements TreeCellEditor {

    CheckBoxTreeCellRenderer renderer = new CheckBoxTreeCellRenderer();

    ChangeEvent changeEvent = null;

    JTree tree;

    public CheckBoxNodeEditor(JTree tree) {
        this.tree = tree;
    }

    public Object getCellEditorValue() {
        ConfigProvider config = ServiceManager.getService(ConfigProvider.class);
        EntryCheckBox checkbox = renderer.getLeafRenderer();
        EntryNode checkBoxNode = checkbox.getEntry();
        checkBoxNode.setSelected(!checkBoxNode.isSelected());
        config.getNotificationStatus().put(checkBoxNode.getId(), checkBoxNode.isSelected());
        return checkBoxNode;
    }

    public boolean isCellEditable(EventObject event) {
        boolean returnValue = false;
        if (event instanceof MouseEvent) {
            MouseEvent mouseEvent = (MouseEvent) event;
            TreePath path = tree.getPathForLocation(mouseEvent.getX(),
                    mouseEvent.getY());
            if (path != null) {
                Object node = path.getLastPathComponent();
                if ((node != null) && (node instanceof DefaultMutableTreeNode)) {
                    DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) node;
                    returnValue = ((treeNode.isLeaf()) && (treeNode instanceof EntryNode));
                }
            }
        }
        return returnValue;
    }

    public Component getTreeCellEditorComponent(JTree tree, Object value,
                                                boolean selected, boolean expanded, boolean leaf, int row) {

        Component editor = renderer.getTreeCellRendererComponent(tree, value,
                true, expanded, leaf, row, true);

        // editor always selected / focused
        ItemListener itemListener = new ItemListener() {
            public void itemStateChanged(ItemEvent itemEvent) {
                if (stopCellEditing()) {
                    fireEditingStopped();
                }
            }
        };
        if (editor instanceof JCheckBox) {
            ((JCheckBox) editor).addItemListener(itemListener);
        }

        return editor;
    }
}
