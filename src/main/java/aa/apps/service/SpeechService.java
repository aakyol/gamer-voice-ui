package aa.apps.service;

import com.google.api.gax.core.CredentialsProvider;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.speech.v1.*;
import com.google.protobuf.ByteString;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;

public class SpeechService {

    private static Logger LOG = Logger.getLogger(SpeechService.class.getName());

    public static List<SpeechRecognitionResult> speechToText(
            final int sampleBitRate) {
        try {
            CredentialsProvider credentialsProvider = FixedCredentialsProvider.create(
                    ServiceAccountCredentials.fromStream(
                            new FileInputStream("gcp-files\\gamer-voice-ui.json")
                    )
            );
            SpeechSettings settings = SpeechSettings
                    .newBuilder()
                    .setCredentialsProvider(credentialsProvider)
                    .build();
            SpeechClient speechClient = SpeechClient.create(settings);


            String fileName = "rec.wav";

            // Reads the audio file into memory
            Path path = Paths.get(fileName);
            byte[] data = Files.readAllBytes(path);
            ByteString audioBytes = ByteString.copyFrom(data);

            RecognitionConfig config = RecognitionConfig.newBuilder()
                    .setEncoding(RecognitionConfig.AudioEncoding.LINEAR16)
                    .setSampleRateHertz(sampleBitRate)
                    .setLanguageCode("en-UK")
                    .build();
            RecognitionAudio audio = RecognitionAudio.newBuilder()
                    .setContent(audioBytes)
                    .build();

            RecognizeResponse response = speechClient.recognize(config, audio);
            List<SpeechRecognitionResult> results = response.getResultsList();

            for (SpeechRecognitionResult result : results) {
                SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);
                LOG.info("Transcription: " + alternative.getTranscript());
            }
            return results;
        } catch (IOException ioEx) {
            LOG.severe("IOException thrown during SpeechClient creation.");
            return null;
        }
    }
}
