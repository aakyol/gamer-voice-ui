package aa.apps.controller;

import aa.apps.audio.CustomAudioInputStream;
import aa.apps.resource.AppConstants;
import aa.apps.service.SpeechService;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.TargetDataLine;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class ListenerController {

    private static Logger LOG = Logger.getLogger(ListenerController.class.getName());

    public String callsignListenLoopback(final TargetDataLine line) throws IOException {
        File recorded = new File(AppConstants.MIC_FILENAME);
        CustomAudioInputStream ais = new CustomAudioInputStream(line);
        ais.setLength(44100 * 3);
        AudioSystem.write(ais, AudioFileFormat.Type.WAVE, recorded);
        final String result = SpeechService.speechToText().toLowerCase();
        recorded.delete();
        return result;
    }
}
