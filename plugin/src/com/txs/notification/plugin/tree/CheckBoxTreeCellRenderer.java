package com.txs.notification.plugin.tree;

import com.txs.notification.plugin.model.EntryNode;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

/**
 * Created by jarek on 05.10.16.
 */
public class CheckBoxTreeCellRenderer extends DefaultTreeCellRenderer {
    private Icon serverIcon = new ImageIcon(getClass().getResource("/server.png"));
    private Icon jobIcon = new ImageIcon(getClass().getResource("/job.png"));
    private EntryCheckBox leafRenderer = new EntryCheckBox();
    Color selectionBorderColor, selectionForeground, selectionBackground,
            textForeground, textBackground;

    public EntryCheckBox getLeafRenderer() {
        return leafRenderer;
    }

    public CheckBoxTreeCellRenderer(){
        Font fontValue;
        fontValue = UIManager.getFont("Tree.font");
        if (fontValue != null) {
            leafRenderer.setFont(fontValue);
        }
        Boolean booleanValue = (Boolean) UIManager
                .get("Tree.drawsFocusBorderAroundIcon");
        leafRenderer.setFocusPainted((booleanValue != null)
                && (booleanValue.booleanValue()));

        selectionBorderColor = UIManager.getColor("Tree.selectionBorderColor");
        selectionForeground = UIManager.getColor("Tree.selectionForeground");
        selectionBackground = UIManager.getColor("Tree.selectionBackground");
        textForeground = UIManager.getColor("Tree.textForeground");
        textBackground = UIManager.getColor("Tree.textBackground");
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean isLeaf, int row, boolean focused) {
        Component returnValue;
        Component component = super.getTreeCellRendererComponent(tree, value,
                selected, expanded, isLeaf, row, focused);
        if(isLeaf){
            setIcon(jobIcon);
        } else {
            setIcon(serverIcon);
        }
        if (isLeaf) {
            String stringValue = tree.convertValueToText(value, selected,
                    expanded, isLeaf, row, false);
            leafRenderer.setText(stringValue);
            leafRenderer.setSelected(false);

            leafRenderer.setEnabled(tree.isEnabled());

            if (selected) {
                leafRenderer.setForeground(selectionForeground);
                leafRenderer.setBackground(selectionBackground);
            } else {
                leafRenderer.setForeground(textForeground);
                leafRenderer.setBackground(textBackground);
            }

            if ((value != null) && (value instanceof DefaultMutableTreeNode)) {
                if (value instanceof EntryNode) {
                    EntryNode node = (EntryNode) value;
                    leafRenderer.setText(node.getText());
                    leafRenderer.setSelected(node.isSelected());
                    leafRenderer.setEntry((EntryNode) value);
                }
            }
            returnValue = leafRenderer;
        } else {
            returnValue = component;
        }
        return returnValue;
    }

}
