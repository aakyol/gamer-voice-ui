package aa.apps.audio;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.TargetDataLine;

public class CustomAudioInputStream extends AudioInputStream {

    public CustomAudioInputStream(TargetDataLine line) {
        super(line);
    }

    public void setLength(final long length) {
        this.frameLength = length;
    }
}
