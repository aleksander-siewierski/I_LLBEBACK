package com.txs.notification.plugin.model;

import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;

@State(
        name = "ConfigProviderState",
        storages = {
                @Storage(id = "other", file = "$APP_CONFIG$/configProvider.xml")
        }
)
public class ConfigProvider implements PersistentStateComponent<ConfigProvider> {

    String serverUrl;
    Boolean notifications;

    public ConfigProvider() {
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public Boolean getNotifications() {
        return notifications;
    }

    public void setNotifications(Boolean notifications) {
        this.notifications = notifications;
    }

    public void loadState(ConfigProvider state) {
        XmlSerializerUtil.copyBean(state, this);
    }

    public ConfigProvider getState() {
        return this;
    }

}