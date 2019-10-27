
package models;

import lombok.Builder;
import lombok.Data;
import dto.AudioBitsPerSample;
import dto.AudioChannel;
import dto.SpeechAudioFormatInfo;

import java.util.Locale;

@Data
@Builder
public class MediaInfo {
	Locale cultureInfo;
	String field;
	String extension = ".wav";
	SpeechAudioFormatInfo audioFormat =
			new SpeechAudioFormatInfo(8000, AudioBitsPerSample.SIXTEEN, AudioChannel.MONO);

	public Locale getCultureInfo() {
		return this.cultureInfo == null ? Locale.getDefault() : this.cultureInfo;
	}

	public String getUserStringLocale() {
		return System.getProperty("user.language") + "-" + System.getProperty("user.country");
	}
}
