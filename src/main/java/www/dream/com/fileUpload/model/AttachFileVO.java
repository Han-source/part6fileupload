package www.dream.com.fileUpload.model;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.web.util.UriComponentsBuilder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import lombok.Data;
import www.dream.com.fileUpload.control.MultimediaType;

@Data
public class AttachFileVO {
	public static final String THUMBNAIL_FILE_PREFIX = "thumb_";
	public static final String UUID_PURE_SEP = "_";

	//사용자가 업로드한 순수 파일 이름
	@Expose
	private String pureFileName;
	@Expose
	private String pureSaveFileName;
	@Expose
	private String pureThumbnailFileName;
	//서버에서 저장된 폴더 이름
	@Expose
	private String savedFolderPath;
	//UUID
	@Expose
	private String uuid;
	//MultimediaType	
	@Expose
	private MultimediaType multimediaType;
	
	public static String fillterPureFileName(String pureSaveFileName) {
		return pureSaveFileName.substring(pureSaveFileName.indexOf(UUID_PURE_SEP) + 1);
	}
	
	public String getPureSaveFileName() {
		pureSaveFileName = uuid + UUID_PURE_SEP + pureFileName;
		return pureSaveFileName;
	}
	
	public String getOriginalFileCallPath() {
		UriComponentsBuilder builder = UriComponentsBuilder.fromPath("");
		builder.queryParam("fileName", savedFolderPath + File.separator + pureSaveFileName);
		return builder.toUriString();
	}
	
	public String getFileCallPath() {
		UriComponentsBuilder builder = UriComponentsBuilder.fromPath("");
		builder.queryParam("fileName", savedFolderPath + File.separator + pureThumbnailFileName);
		return builder.toUriString();
	}
	
	public String getJson() {
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		String ret = new Gson().toJson(this); 
		try {
			ret = URLEncoder.encode(ret, "UTF-8");
			System.out.println(ret);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
}