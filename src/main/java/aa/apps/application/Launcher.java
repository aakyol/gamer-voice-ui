package aa.apps.application;

import aa.apps.service.ListenerService;

public class Launcher {

    public static void main(String[] args) throws InterruptedException {
        ListenerService.initListenerService();
        ListenerService.startListener();
    }
}
