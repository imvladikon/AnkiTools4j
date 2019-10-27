package helpers;

import dto.SpeechAudioFormatInfo;

import java.util.Locale;

public class SynthetizerHelper {
	public static void createAudio(String path,
							String text,
							Locale cultureInfo,
							SpeechAudioFormatInfo audioFormat) {
		/*
		 * try (SpeechSynthesizer synth = new SpeechSynthesizer()) {
		 * synth.SetOutputToWaveFile(path, audioFormat);
		 * PromptBuilder builder = new PromptBuilder(cultureInfo);
		 * builder.AppendText(text);
		 * synth.Speak(builder);
		 * }
		 */
	}
}
