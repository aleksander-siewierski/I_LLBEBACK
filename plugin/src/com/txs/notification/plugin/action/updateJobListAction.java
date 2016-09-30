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
    private DefaultListModel listModel;
    private Gson json;
    private HashMap<String, Boolean> statusMap;

    public updateJobListAction(DefaultListModel listModel, HashMap<String, Boolean> statusMap) {
        this.listModel = listModel;
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

            String content = getUrlAsString(url+"/api/configuration/all/");
            ListEntry[] myList = (ListEntry[]) json.fromJson(content, ListEntry[].class);
            if (myList.length > 0){
                listModel.removeAllElements();
            }
            for(int i = 0; i < myList.length; i++){
                listModel.add(i, myList[i]);
                if (myList[i].getId() == null){
                    continue;
                }
                if(statusMap.get(myList[i].getId()) == null){
                    statusMap.put(myList[i].getId(), myList[i].isBuilding());
                    continue;
                }
                if(statusMap.get(myList[i].getId()).equals(myList[i].isBuilding())){
                    continue;
                }
                String notificationText = "Job <b>"+myList[i].getShortJobName()+"</b> on server "+myList[i].getServerName();
                if(myList[i].isBuilding()){
                    noti.info(notificationText+" <font color='blue'><b>started!</b></font>");
                } else {
                    noti.info(notificationText+" <font color='red'><b>finished!</b></font>");
                }
                statusMap.put(myList[i].getId(), myList[i].isBuilding());
            }
            

        } catch (Exception e) {
            noti.error("Can't recive job data!");
        }

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
