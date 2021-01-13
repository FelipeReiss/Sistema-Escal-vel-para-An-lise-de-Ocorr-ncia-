package br.com.b2w.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.b2w.data.vo.v1.UploadFileResponseVO;
import br.com.b2w.services.FileStorageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "File Upload/Download/Search Endpoint", 
description = "Users can download last file uploaded or upload a new file and search words on it",
tags = "File Upload/Download/Search Endpoint")
@RestController
@RequestMapping("/api/file/v1")
public class FileController {
	
	private static final Logger logger = LoggerFactory.getLogger(FileController.class);
	
	@Autowired
	private FileStorageService fileStorageService;
	
	@ApiOperation(value = "Users can upload files (optional) and search strings on it (optional)")
	@PostMapping("/searchInFile")
	public UploadFileResponseVO uploadFile(@RequestParam(value="file", required=false) MultipartFile file, 
										  @RequestParam(value="wordToCount", defaultValue="") String wordToCount) {
		
		// Adding a file name and link variables in case of generic methods in future
		String fileName = "lastFileUploaded.csv"; 
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
															.path("/api/file/v1/downloadFile/")
															.path(fileName)
															.toUriString();

		Map.Entry<Long,String> ocurrenceAndTime = fileStorageService.processFile(file, wordToCount, fileName, System.currentTimeMillis());

		return new UploadFileResponseVO(wordToCount, 
										ocurrenceAndTime.getKey(),
										ocurrenceAndTime.getValue(),
										fileDownloadUri);
	}
	
	// Download is to any file extension in case of generic methods in future
	@ApiOperation(value = "Users can download the previously uploaded file")
	@GetMapping("/downloadFile/{fileName:.+}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
		
		Resource resource = fileStorageService.loadFileAsResource(fileName);
		
		String contentType = null;
		
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (Exception e) {
			logger.info("Could not determine file type!");
		}
		
		if (contentType == null) {
			contentType = "application/octet-stream";
		}

		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}

}
