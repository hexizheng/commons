package com.jscn.commons.core.ftp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.enterprisedt.net.ftp.FTPClient;
import com.enterprisedt.net.ftp.FTPConnectMode;
import com.enterprisedt.net.ftp.FTPException;
import com.enterprisedt.net.ftp.FTPTransferType;
import com.jscn.commons.core.exceptions.SystemException;

public class FtpOperater {

	private static int BUFFER_SIZE=100000;
	private static final Logger log = LoggerFactory
			.getLogger(FtpOperater.class);

	private FTPClient aftp;

	public void connect(String hostname, String uid, String pwd, String path) {
		try {
			aftp = new FTPClient(hostname);
			// aftp.setRemoteHost(hostname);
			aftp.login(uid, pwd);
			aftp.setType(FTPTransferType.BINARY);
			aftp.setTransferBufferSize(BUFFER_SIZE);
			if (path.length() != 0)
				aftp.chdir(path);
		} catch (IOException e) {
			log.error("连接主机:" + hostname + "失败!", e);
			throw new SystemException("连接主机:" + hostname + "失败!", e);
		} catch (FTPException e) {
			log.error("无法与主机:" + hostname + "连接!错误", e);
			throw new SystemException("无法与主机:" + hostname + "连接!错误", e);
		}
	}

	/**
	 * 返回本地没有的服务器上的文件列表
	 * @param localDir 本地路径
	 */
	public List<String> getNewFileNames(String localDir){
		List<String> list = new ArrayList<String>();
		File file = new File(localDir);
		String[] localFileList = file.list();
		
		String[] remoteFileList = dir();
		HashSet<String> set = new HashSet<String>(Arrays.asList(remoteFileList));
		set.removeAll(Arrays.asList(localFileList));
		String[] a = new String[set.size()];
		return  Arrays.asList(set.toArray(a));
//		return list;
	}
	
//	private boolean isExit(String str,String[] strArray){
//		for(String tempStr : strArray){
//			if(str.equals(tempStr)){
//				return true;
//			}
//		}
//		return false;
//	}
	/**
	 * 取远程文件到本地
	 * @param localDir
	 * @param filename
	 */
	public void downFile(String localDir, String filename) {

		try {
			aftp.get(localDir+File.separator+filename, filename);
		} catch (FTPException e) {
			log.error("获取文件时出错", e);
			throw new SystemException("获取文件时出错", e);
		}
		catch (IOException e) {
			log.error("获取文件时出错", e);
			throw new SystemException("获取文件时出错", e);
		}
	}
	
	public String[] dir() {
		try {
			return aftp.dir();
		} catch (FTPException e) {
			log.error("获取文件列表时出错", e);
			throw new SystemException("获取文件列表时出错", e);
		}
		catch (IOException e) {
			log.error("获取文件列表时出错", e);
			throw new SystemException("获取文件列表时出错", e);
		}
	}
	

	// dir,将一个目录下的文件全部上传到ftp服务器上的当前目录.
	public boolean ftpUpload(String localdir) {
		boolean ret = true;
		File[] file = (new File(localdir)).listFiles();
		for (int i = 0; i < file.length; i++) {
			if (file[i].isFile()) {
				try {
					aftp.put(localdir + file[i].getName(), file[i].getName(),
							true);
				} catch (IOException e) {
					ret = false;
					e.printStackTrace();
				} catch (FTPException e) {
					ret = false;
					e.printStackTrace();
				}
			}
		}
		return ret;
	}

	// dir,删除ftp服务器上目录内的文件.目录不能被删除
	public boolean ftpDeldir(String dir) {

		boolean ret = true;
		try {
			String[] s = null;
			if (dir.equals("")) {
				s = aftp.dir();
			} else {
				s = aftp.dir(dir);
			}
			for (int i = 0; i < s.length; i++) {
				// log.info(s[i]);
				aftp.delete(s[i]);
			}
		} catch (IOException e) {
			ret = false;
			log.error("删除FTP目录下文件失败:" + e);
		} catch (FTPException e) {
			ret = false;
			log.error("删除FTP目录下文件失败:" + e);
		}
		return ret;
	}

	public boolean stop() {
		boolean ret = true;
		try {
			aftp.quit();
		} catch (IOException e) {
			ret = false;
			log.error("关闭FTP服务器主机连接,错误原因:" + e);
		} catch (FTPException e) {
			ret = false;
			log.error("关闭FTP服务器主机连接,错误原因:" + e);
		}
		return ret;
	}

	public static void main(String[] args) {
		FtpOperater f = new FtpOperater();
		f.connect("192.168.1.89", "hexizheng", "hexizheng", "");
		// f.ftpDownload("L2RefSTEPFlag_20060407.flg");
		// f.ftpDeldir("");
//		System.out.println(f.ftpDownload("test.txt"));
//		System.out.println(f.dir());
		
//		for(String s : f.dir()){
//			System.out.println(s);
//		}
//		for(String s : new File("C:/ftp").list()){
//			System.out.println(s);
//		}
		
//		f.getNewFile("D:/ftp");
	}

}
