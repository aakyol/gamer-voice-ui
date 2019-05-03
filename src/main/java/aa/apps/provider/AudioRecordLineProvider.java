package aa.apps.provider;

import javax.sound.sampled.*;
import java.util.logging.Logger;

public class AudioRecordLineProvider {

    private static Logger LOG = Logger.getLogger(AudioRecordLineProvider.class.getName());

    public static TargetDataLine provideAudioLine(final float sampleBitRate) {
        AudioFormat format = new AudioFormat(
                sampleBitRate,
                8,
                1,
                true,
                true
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
