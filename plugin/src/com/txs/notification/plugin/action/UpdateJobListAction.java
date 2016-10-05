package com.txs.notification.plugin.action;

import com.google.gson.Gson;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.util.containers.HashMap;
import com.txs.notification.plugin.NotificationWrapper;
import com.txs.notification.plugin.model.ConfigProvider;
import com.txs.notification.plugin.model.EntryNode;
import com.txs.notification.plugin.model.ServerNode;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.net.HttpURLConnection;
import java.util.TimerTask;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by jarek on 29.09.16.
 */
public class UpdateJobListAction extends TimerTask{
    NotificationWrapper noti;
    private JTree jobList;
    private JLabel urlValue;
    private Gson json;
    private HashMap<String, Boolean> statusMap;

    public UpdateJobListAction(JTree jobList, JLabel urlValue, HashMap<String, Boolean> statusMap) {
        this.jobList = jobList;
        this.urlValue = urlValue;
        this.statusMap = statusMap;
        noti = new NotificationWrapper();
        json = new Gson();
    }

    @Override
    public void run() {
        ConfigProvider config = ServiceManager.getService(ConfigProvider.class);
        String url = config.getServerUrl();
        if(url == null || url.equals("")){
            return;
        }
        try {
            DefaultMutableTreeNode root = new DefaultMutableTreeNode();

            String content = getUrlAsString(url+"/api/configuration/all/");

            EntryNode[] myList = (EntryNode[]) json.fromJson(content, EntryNode[].class);
            for(int i = 0; i < myList.length; i++){
                if(config.getNotificationStatus().get(myList[i].getId()) == null){
                    config.getNotificationStatus().put(myList[i].getId(), true);
                }
                myList[i].setSelected(config.getNotificationStatus().get(myList[i].getId()));
                ServerNode server = getServerNode(root, myList[i]);
                server.add(myList[i]);
                if (config.getNotifications()) {
                    showNotification(myList[i], config.getNotificationStatus().get(myList[i].getId()));
                }
            }

            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    DefaultTreeModel model = new DefaultTreeModel(root);
                    jobList.setModel(model);
                    TreePath path = new TreePath(root);
                    for(int i = 0; i < root.getChildCount(); i++){
                        TreePath nodePath = path.pathByAddingChild(root.getChildAt(i));
                        jobList.expandPath(nodePath);
                    }
                }
            });
            statusMap.put("connectionOK", true);
        } catch (Exception e) {
            if(statusMap.get("connectionOK").equals(true)){
                noti.error("<b>W</b>hat a <b>T</b>errible <b>F</b>ailure?!");
                statusMap.put("connectionOK", false);
            }
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                if(statusMap.get("connectionOK").equals(true)){
                    urlValue.setIcon(new ImageIcon(getClass().getResource("/ok.png")));
                } else {
                    urlValue.setIcon(new ImageIcon(getClass().getResource("/error.png")));
                }
            }
        });
    }

    private ServerNode getServerNode(DefaultMutableTreeNode root, EntryNode entry){
        int count = root.getChildCount();
        for (int i = 0; i < count; i++){
            ServerNode testNode = (ServerNode)root.getChildAt(i);
            if(testNode.getServerName() != null && testNode.getServerName().equals(entry.getServerName())){
                return testNode;
            }
        }
        ServerNode server = new ServerNode();
        server.setServerName(entry.getServerName());

        root.add(server);
        return server;
    }

    private void showNotification(EntryNode listEntry, boolean show) {
        if (listEntry.getId() == null || !show){
            return;
        }
        if(statusMap.get(listEntry.getId()) == null){
            statusMap.put(listEntry.getId(), listEntry.isBuilding());
            return;
        }
        if(statusMap.get(listEntry.getId()).equals(listEntry.isBuilding())){
            return;
        }
        String notificationText = "Job <b>"+ listEntry.getShortJobName()+"</b> on server "+ listEntry.getServerName();
        if(listEntry.isBuilding()){
            noti.info(notificationText+" <font color='"+listEntry.getProgressColor()+"'><b>started!</b></font>");
        } else {
            noti.info(notificationText+" <font color='"+listEntry.getProgressColor()+"'><b>finished!</b></font>");
        }
        statusMap.put(listEntry.getId(), listEntry.isBuilding());
    }

    public String getUrlAsString(String url) throws Exception {
            URL urlObj = new URL(url);
            HttpURLConnection.setFollowRedirects(false);
            HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
            con.setRequestMethod("GET");

            con.setConnectTimeout(2000); //set timeout to 5 seconds

            con.setDoOutput(true);
            con.connect();

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

            StringBuilder response = new StringBuilder();
            String inputLine;

            String newLine = System.getProperty("line.separator");
            while ((inputLine = in.readLine()) != null)
            {
                response.append(inputLine + newLine);
            }

            in.close();

            return response.toString();
    }
}
