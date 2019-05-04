package aa.apps.service;

import aa.apps.audio.CustomAudioInputStream;
import aa.apps.provider.AudioRecordLineProvider;
import aa.apps.resource.AppConstants;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.TargetDataLine;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Logger;

public class ListenerService {

    private static Logger LOG = Logger.getLogger(ListenerService.class.getName());

    private static Thread mainThread;

    public static void initListenerService() {

        if(Objects.isNull(mainThread) ) {
            mainThread = new Thread(() -> { //Might throw InterruptedException

                TargetDataLine line = AudioRecordLineProvider.provideAudioLine();

                if(Objects.nonNull(line)) {

                    line.start();

                    while (!mainThread.currentThread().isInterrupted()) {
                        try {
                            if(callsignListenLoopback(line).contains(AppConstants.UI_CALLSIGN)) {
                                LOG.info("Callsign detected.");
                            }
                        } catch (IOException ioEx) {
                            LOG.severe("IOException during recording microphone.");
                            LOG.severe(ioEx.getStackTrace().toString());
                        }
                    }

                    line.stop();
                    line.close();
                }
                else {
                    LOG.severe("Provided audio recording line is invalid, exiting thread...");
                }
            });
        }
    }

    public static boolean startListener() {
        try {
            mainThread.start();
        } catch (Exception ex) { //TODO: Handle specific exceptions once
                                 //TODO: listener thread has functionality
            return false;
        }
        return true;
    }

    public static boolean stopListener() {
        try {
            mainThread.interrupt();
        } catch (Exception ex) { //TODO: Handle specific exceptions once
                                 //TODO: listener thread has functionality
            return false;
        }
        return true;
    }
}
