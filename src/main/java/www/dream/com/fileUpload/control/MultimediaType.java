package www.dream.com.fileUpload.control;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public enum MultimediaType {
	image, video, audio, others;

	public static MultimediaType identifyMultimediaType(File file) {
		String contentType = null;
		try {

			contentType = Files.probeContentType(file.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (contentType == null || contentType.startsWith("text/plain"))
			return MultimediaType.others;
		if (contentType.startsWith("image"))
			return MultimediaType.image;
		if (contentType.startsWith("audio"))
			return MultimediaType.audio;
		if (contentType.startsWith("video"))
			return MultimediaType.video;
		
		//새로운 국제 표준이 만들어졌는지?
		System.out.println(contentType);
		return null;
	}

}
