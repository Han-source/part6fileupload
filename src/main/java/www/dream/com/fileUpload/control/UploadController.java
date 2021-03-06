package www.dream.com.fileUpload.control;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import org.jcodec.api.FrameGrab;
import org.jcodec.common.model.Picture;
import org.jcodec.scale.AWTUtil;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;

import net.coobird.thumbnailator.Thumbnailator;
import www.dream.com.fileUpload.model.AttachFileVO;
import www.dream.com.framework.util.FileUtil;
import www.dream.com.framework.util.StringUtil;

@Controller
@RequestMapping("/uploadFiles/*")
public class UploadController {
	private static final String UPLOAD_FOLDER = "C:\\uploadedFiles";
	@GetMapping("uploadByAjax")
	public void uploadByAjax() {
		
	}
	@PostMapping(value = "upload", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<List<String>> uploadFilesByAjax(@RequestParam("uploadFile") MultipartFile[] uploadFiles) {
		List<AttachFileVO> listAttachFileVO = new ArrayList<>();
		File uploadPath = new File(UPLOAD_FOLDER, getFolderName());
		if (! uploadPath.exists()) {
			//????????? ?????? ????????? ????????? ??? ????????? ????????? ??????.
			uploadPath.mkdirs();
		}
		//????????? ?????? ????????? ??? ????????????
		for(MultipartFile uf : uploadFiles) {
			AttachFileVO attachFileVO = new AttachFileVO();
			attachFileVO.setSavedFolderPath(uploadPath.getAbsolutePath());
			
			String originalFilename = uf.getOriginalFilename();
			String pureFilename = originalFilename.substring(originalFilename.lastIndexOf("\\") + 1);
		
			attachFileVO.setPureFileName(pureFilename);
			//UUID : ?????? ???????????? ????????? ????????? ????????? ???????????? ?????? ????????? ??? ??????.
			String uuid = UUID.randomUUID().toString();
			attachFileVO.setUuid(uuid);

			String pureSaveFileName = attachFileVO.getPureSaveFileName();
			
			//uploadPath????????? 
			File save = new File(uploadPath, pureSaveFileName);
			try {
				uf.transferTo(save);
				makeThumnail(uploadPath, save, pureSaveFileName, attachFileVO);
				attachFileVO.setMultimediaType(MultimediaType.identifyMultimediaType(save));
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
			//buildAuxInfo : ???????????? ???????????? ??????
			listAttachFileVO.add(attachFileVO);
		}
		List<String> ret = listAttachFileVO.stream().map(vo -> vo.getJson()).collect(Collectors.toList());
		
		return new ResponseEntity<>(ret, HttpStatus.OK);
	}
	
	@GetMapping("display")
	@ResponseBody
	public ResponseEntity<byte[]> getThumbnailFile(@RequestParam("fileName") String fileName) {
		File file = new File(fileName);
		ResponseEntity<byte[]> res = null;
		HttpHeaders headers = new HttpHeaders();
		try {
			headers.add("Content-Type", Files.probeContentType(file.toPath()));
			res = new ResponseEntity<byte[]>(FileCopyUtils.copyToByteArray(file), headers, HttpStatus.OK);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	/**
	 * @param fileName : C:\\uploadedFiles\2021\06\22\myfile.txt  Full File Path ???????????? ?????? ??? ??????.
	 * @return
	 */
	@GetMapping(value = "download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ResponseBody
	public ResponseEntity<Resource> downloadFile(@RequestHeader("User-Agent") String userAgent, @RequestParam("fileName") String fileName) {
		Resource resource = new FileSystemResource(fileName);
		if (!resource.exists()) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		String resourceFilename = AttachFileVO.fillterPureFileName(resource.getFilename());
		HttpHeaders headers = new HttpHeaders();
		try {
			String downloadFileName = null;
			if (userAgent.contains("Trident") || userAgent.contains("Edge")) {
				downloadFileName = URLEncoder.encode(resourceFilename, "URF-8");
			} else {
				downloadFileName = new String(resourceFilename.getBytes("UTF-8"),"ISO-8859-1");
			}
			headers.add("Content-Disposition","attachment; filename=" + downloadFileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(resource, headers, HttpStatus.OK);
	}
	
	@PostMapping(value = "deleteFile")
	@ResponseBody
	public ResponseEntity<String> cancelAttach(AttachFileVO attachVO) {
		try {
			File pureSaveFileName = new File(attachVO.getSavedFolderPath(), attachVO.getPureSaveFileName());
			pureSaveFileName.delete();
			if (StringUtil.hasInfo(attachVO.getPureThumbnailFileName())) {
				new File(attachVO.getSavedFolderPath(), attachVO.getPureThumbnailFileName()).delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>("????????????", HttpStatus.OK);
	}
	
	//?????? ????????? ?????? ????????? ??????.
	private String getFolderName() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(new Date()).replace('-', File.separatorChar);
	}

	private void makeThumnail(File uploadPath, File uploadedFile, String pureSaveFileName, AttachFileVO attachFileVO) {
		MultimediaType multimediaType = MultimediaType.identifyMultimediaType(uploadedFile);
		if (multimediaType == MultimediaType.image) {
			String pureThumbnailFileName =  AttachFileVO.THUMBNAIL_FILE_PREFIX + pureSaveFileName;
			attachFileVO.setPureThumbnailFileName(pureThumbnailFileName);
			File thumbnailFile = new File(uploadPath, pureThumbnailFileName);
			try {
				Thumbnailator.createThumbnail(uploadedFile, thumbnailFile, 100, 100);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (multimediaType == MultimediaType.video) {
			pureSaveFileName = FileUtil.truncateExt(pureSaveFileName);
			String pureThumbnailFileName =  AttachFileVO.THUMBNAIL_FILE_PREFIX + pureSaveFileName + ".png";
			attachFileVO.setPureThumbnailFileName(pureThumbnailFileName);
			File thumbnailFile = new File(uploadPath, pureThumbnailFileName);
			try {
				int frameNumber = 0;
				//video ???????????? ????????? ????????????  ???????????? ????????????
				Picture picture = FrameGrab.getFrameFromFile(uploadedFile, frameNumber);
				BufferedImage bufferedImage = AWTUtil.toBufferedImage(picture);
				ByteArrayOutputStream os = new ByteArrayOutputStream(); 
				ImageIO.write(bufferedImage, "png", os);
				InputStream is = new ByteArrayInputStream(os.toByteArray());
				FileOutputStream fileOutputStream = new FileOutputStream(thumbnailFile);
				//????????? ???????????? Thumbnail??? ????????? 
				Thumbnailator.createThumbnail(is, fileOutputStream, 100, 100);
				fileOutputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
