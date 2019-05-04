package aa.apps.provider;

import javax.sound.sampled.*;
import java.util.logging.Logger;

public class AudioRecordLineProvider {

    private static Logger LOG = Logger.getLogger(AudioRecordLineProvider.class.getName());

    public static TargetDataLine provideAudioLine() {
        AudioFormat format = new AudioFormat(
                AudioFormat.Encoding.PCM_SIGNED,
                44100,
                16,
                2,
                4,
                44100,
                false
        );

        TargetDataLine line = null;
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
        if (!AudioSystem.isLineSupported(info)) {
            LOG.severe("Line is not supported.");
            return null;
        }

        try {
            line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format);
        } catch (LineUnavailableException ex) {
            LOG.severe("Line is not available.");
            return null;
        }

        return line;
    }
}
