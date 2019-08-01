package com.ideasStudio.website.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BackupsPaths {
	
	@Value("${tomcatPaths}")
	private String tomcatPaths;
	
	@Value("${backupsPaths}")
	private String backupsPaths;
	
	@Value("${backupPassword}")
	private String backupPassword;
	
	
	public String getBackUpPassword() {
		return backupPassword;
	}
	
	public void setPrivateKey(String backupPassword) {
		this.backupPassword = backupPassword;
	}
	
	public String getTomcatPaths() {
		return tomcatPaths;
	}
	public void setTomcatPaths(String tomcatPaths) {
		this.tomcatPaths = tomcatPaths;
	}
	public String getBackupsPaths() {
		return backupsPaths;
	}
	public void setBackupsPaths(String backupsPaths) {
		this.backupsPaths = backupsPaths;
	}
	@Override
	public String toString() {
		return "BackupsPaths [tomcatPaths=" + tomcatPaths + ", backupsPaths=" + backupsPaths + "]";
	}
}
