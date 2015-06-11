package com.mmdi.projet.pivo.webapp.vaadin;

import com.vaadin.server.Page;
import com.vaadin.shared.communication.PushMode;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;

public abstract class PushHelper {

    public static void push(final UI ui) {

        if (ui != null && ui.getPushConfiguration() != null && ui.getPushConfiguration().getPushMode() != null
                        && !ui.getPushConfiguration().getPushMode().equals(PushMode.DISABLED)) {
            ui.access(new Runnable() {
                @Override
                public void run() {
                    ui.push();
                }
            });
        }
    }

    public static void pushWithNotification(final UI ui, final String notificationTitle, final String notificationText) {
        if (ui != null && ui.getPushConfiguration() != null && ui.getPushConfiguration().getPushMode() != null
                        && !ui.getPushConfiguration().getPushMode().equals(PushMode.DISABLED)) {
            ui.access(new Runnable() {
                @Override
                public void run() {
                    Notification notification = new Notification(notificationTitle, notificationText, Type.TRAY_NOTIFICATION);
                    notification.setDelayMsec(3000);
                    notification.show(Page.getCurrent());

                    ui.push();
                }
            });
        }
    }

}
