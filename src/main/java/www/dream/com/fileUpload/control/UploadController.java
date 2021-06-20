package www.dream.com.fileUpload.control;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/uploadFiles/*")
public class UploadController {
	@GetMapping("uploadByAjax")
	public void uploadByAjax() {
		
	}
	@PostMapping("upload")
	public void uploadFilesByAjax(@RequestParam("uploadFile") MultipartFile[] uploadFiles) {
		String uploadFolder = "C:\\uploadedFiles";
		
		for(MultipartFile uf : uploadFiles) {
			String originalFilename = uf.getOriginalFilename();
			String pureFilename = originalFilename.substring(originalFilename.lastIndexOf("\\") + 1);
			File save = new File(uploadFolder, pureFilename);
		
			try {
				uf.transferTo(save);
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
		
		}
	}
	
}
