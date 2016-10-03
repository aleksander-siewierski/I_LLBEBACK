package com.txs.notification.plugin.action;

import com.google.gson.Gson;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.util.containers.HashMap;
import com.txs.notification.plugin.NotificationWrapper;
import com.txs.notification.plugin.model.ListEntry;

import javax.swing.*;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.util.TimerTask;
import java.net.URL;
import java.net.URLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by jarek on 29.09.16.
 */
public class UpdateJobListAction extends TimerTask{
    NotificationWrapper noti;
    private JList jobList;
    private JLabel urlValue;
    private Gson json;
    private HashMap<String, Boolean> statusMap;

    public UpdateJobListAction(JList jobList, JLabel urlValue, HashMap<String, Boolean> statusMap) {
        this.jobList = jobList;
        this.urlValue = urlValue;
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
            //noti.info(content);
            ListEntry[] myList = (ListEntry[]) json.fromJson(content, ListEntry[].class);
            for(int i = 0; i < myList.length; i++){
                listModel.add(i, myList[i]);
                if (PropertiesComponent.getInstance().getBoolean("IWillBeBack.notifications")) {
                    showNotification(myList[i]);
                }
            }

            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    jobList.setModel(listModel);
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
