package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class SpeechAudioFormatInfo {
    //Not implemented yet
    int samplesPerSecond;
    AudioBitsPerSample bitsPerSample;
    AudioChannel channel;
}
