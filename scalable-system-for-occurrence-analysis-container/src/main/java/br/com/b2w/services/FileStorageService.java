package br.com.b2w.services;

import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Scanner;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.b2w.config.FileStorageConfig;
import br.com.b2w.exception.FileStorageException;
import br.com.b2w.exception.MyFileNotFoundException;

@Service
public class FileStorageService {

	private final Path fileStorageLocation;
	
	@Autowired
	public FileStorageService(FileStorageConfig fileStorageConfig) {
		
		this.fileStorageLocation = Paths.get(fileStorageConfig.getUploadDir())
				.toAbsolutePath().normalize();
		try {
			Files.createDirectories(this.fileStorageLocation);
		} catch (Exception e) {
			throw new FileStorageException("Could not create the directory where the uploaded files will be stored", e);
		}
	}
	
	public Map.Entry<Long,String> processFile(MultipartFile file, String wordToCount, String fileName, Long uploadedTimeMillis) {
		long ocurrence = 0;
		String timeToProcess = "";
		
		Path targetLocation = this.fileStorageLocation.resolve(fileName);
		
		if (file != null && !file.isEmpty()) {
			if (!file.getContentType().equals("text/csv")) {
				throw new FileStorageException("Sorry, file isn't supported!");
			} else {
				try {
					Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
				} catch (Exception e) {
					throw new FileStorageException("Could not store the file. Please try again!", e);
				}
			}
		}
		
		// If parameter is blank will not count and will return 0
		if (!wordToCount.isEmpty() && !wordToCount.isBlank()) {
			ocurrence = wordCount(targetLocation.toString(), wordToCount);
		}
		
		timeToProcess = formatTime(System.currentTimeMillis() - uploadedTimeMillis);
		
		return Map.entry(ocurrence, timeToProcess);
	}
	
	private String formatTime(long totalTimeMillis) {
		Date date = new Date(totalTimeMillis);
		DateFormat formatter = new SimpleDateFormat("HH:mm:ss.SSS");
		formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
		
		return formatter.format(date);
	}

	public static long wordCount(String filePath, String wordToCount) {
	    long ocurrence = 0;
	    int wordPosition = 0;
	    
        try {
        	Scanner reader = new Scanner(new FileInputStream(filePath), StandardCharsets.ISO_8859_1);

        	while (reader.hasNextLine()) {				
        		StringBuilder lineStringBuilder = new StringBuilder(reader.nextLine());
        		
        		wordPosition = lineStringBuilder.indexOf(wordToCount);
        		
        		while(wordPosition != -1) {
        			wordPosition = lineStringBuilder.indexOf(wordToCount);
        			lineStringBuilder.delete(0 , wordPosition + wordToCount.length());
        			
        			ocurrence++;
        			
        			wordPosition = lineStringBuilder.indexOf(wordToCount);
        		}
        	}
        	
        	reader.close();
        	
        	return ocurrence;
        
        } catch (Exception e) {
        	throw new FileStorageException("Could not read the file. Please, try upload a new file again!", e);
        }
    }
	
	public Resource loadFileAsResource(String fileName) {
		try {
			Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
			Resource resource = new UrlResource(filePath.toUri());
			if(resource.exists()) {
				return resource;
			} else {
				throw new MyFileNotFoundException("File not found " + fileName);
			}
		} catch (Exception e) {
			throw new MyFileNotFoundException("File not found " + fileName, e);
		}
		
	}

}
