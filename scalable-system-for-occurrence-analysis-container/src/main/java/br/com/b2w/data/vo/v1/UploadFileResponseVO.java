package br.com.b2w.data.vo.v1;

import java.io.Serializable;

public class UploadFileResponseVO implements Serializable{
 
	private static final long serialVersionUID = 1L;

	private String wordToCount;
	private long ocurrence;
	private String processTime;
	private String fileDownloadUri;
	
	public UploadFileResponseVO() {
	}

	public UploadFileResponseVO(String wordToCount, long ocurrence, String processTime, String fileDownloadUri) {
		this.wordToCount = wordToCount;
		this.ocurrence = ocurrence;
		this.processTime = processTime;
		this.fileDownloadUri = fileDownloadUri;
		
	}

	public String getWordToCount() {
		return wordToCount;
	}

	public void setWordToCount(String wordToCount) {
		this.wordToCount = wordToCount;
	}

	public long getOcurrence() {
		return ocurrence;
	}

	public void setOcurrence(long ocurrence) {
		this.ocurrence = ocurrence;
	}

	public String getProcessTime() {
		return processTime;
	}

	public void setProcessTime(String processTime) {
		this.processTime = processTime;
	}

	public String getFileDownloadUri() {
		return fileDownloadUri;
	}

	public void setFileDownloadUri(String fileDownloadUri) {
		this.fileDownloadUri = fileDownloadUri;
	}

}