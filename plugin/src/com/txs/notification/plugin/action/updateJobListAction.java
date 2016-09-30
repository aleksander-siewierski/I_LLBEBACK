package com.txs.notification.plugin.action;

import com.google.gson.Gson;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.util.containers.HashMap;
import com.txs.notification.plugin.NotificationWrapper;
import com.txs.notification.plugin.model.ListEntry;

import javax.swing.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.TimerTask;
import java.net.URL;
import java.net.URLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by jarek on 29.09.16.
 */
public class updateJobListAction extends TimerTask{
    NotificationWrapper noti;
    private JList jobList;
    private Gson json;
    private HashMap<String, Boolean> statusMap;

    public updateJobListAction(JList jobList, HashMap<String, Boolean> statusMap) {
        this.jobList = jobList;
        this.statusMap = statusMap;
        noti = new NotificationWrapper();
        json = new Gson();
    }

    @Override
    public void run() {
        String url = PropertiesComponent.getInstance().getValue("IWillBeBack.serverUrl");
        if(url == null || url.equals("")){
            return;
        }

        try {
            DefaultListModel<ListEntry> listModel = new DefaultListModel<ListEntry>();

            String content = getUrlAsString(url+"/api/configuration/all/");
            ListEntry[] myList = (ListEntry[]) json.fromJson(content, ListEntry[].class);
            for(int i = 0; i < myList.length; i++){
                listModel.add(i, myList[i]);
                showNotification(myList[i]);
            }

            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    jobList.setModel(listModel);
                }
            });

        } catch (Exception e) {
            noti.error("Can't recive job data!");
        }

    }

    private void showNotification(ListEntry listEntry) {
        if (listEntry.getId() == null){
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
            noti.info(notificationText+" <font color='blue'><b>started!</b></font>");
        } else {
            noti.info(notificationText+" <font color='red'><b>finished!</b></font>");
        }
        statusMap.put(listEntry.getId(), listEntry.isBuilding());
    }

    public String getUrlAsString(String url) throws Exception {
            URL urlObj = new URL(url);
            URLConnection con = urlObj.openConnection();

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
