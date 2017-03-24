package com.longyuzichen.core.util;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.SocketException;

/**
 * com.longyuzichen.core.util
 *
 * @author longyuzichen@126.com
 * @version V1.0
 * @desc FTP工具类
 * @date 2017-03-24 23:07
 */
public class FtpUtil {
    private static final Logger log = LoggerFactory.getLogger(FtpUtil.class);


    private FtpUtil() {
    }

    /**
     * @param IPAddress FTP服务器IP地址
     * @param port      FTP服务器端口
     * @param userName  ftp登陆用户名
     * @param password  ftp用户密码
     * @return
     * @desc 获取FTP链接
     */
    public static FTPClient getFtpConnect(String IPAddress, int port, String userName, String password) {

        FTPClient ftpClient = null;
        try {
            ftpClient = new FTPClient();
            ftpClient.connect(IPAddress, port); //连接FTP服务器
            ftpClient.login(userName, password); //登陆FTP服务器

            if (!FTPReply.isPositiveCompletion(ftpClient.getReply())) {
                log.debug("未连接到【{}】FTP服务器，用户名或者密码错误", IPAddress);
                ftpClient.disconnect();
            } else {
                log.debug("【{}】FTP服务器连接成功！", IPAddress);
            }
        } catch (SocketException e) {
            log.error("【" + IPAddress + "】FTP服务器IP地址可能错误，请正确配置。", e);
            e.printStackTrace();
        } catch (IOException e) {
            log.error("【" + IPAddress + "】FTP端口错误，请正确配置。", e);
            e.printStackTrace();
        }
        return ftpClient;
    }


    /**
     * @param ftpFilePath   ftp文件路径（文件全路径）
     * @param localFilePath 本地下载路径
     * @desc 从FTP服务器上下载文件
     */
    public static void downup(FTPClient ftpClient, String ftpFilePath, String localFilePath) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(ftpFilePath);
            ftpClient.setBufferSize(1024);
            //设置文件类型（二进制）
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.retrieveFile(localFilePath, fos);
        } catch (FileNotFoundException e) {
            log.error("FTP服务器上没有【" + ftpFilePath + "】文件！", e);
            e.printStackTrace();
        } catch (IOException e) {
            log.error("下载FTP服务器上【" + ftpFilePath + "】文件异常！", e);
            e.printStackTrace();
            try {
                throw new Exception("FTP客户端出错！", e);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                FtpUtil.closeFtpConnect(ftpClient);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * @param filePath    本地文件路径（全路径）
     * @param ftpPath     ftp服务器文件夹路径
     * @param ftpFileName ftp服务器文件名称
     * @desc 上传文件到FTP服务器
     */
    public static void upload(FTPClient ftpClient, String filePath, String ftpPath, String ftpFileName) {
        FileInputStream fis = null;
        try {
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            File file = new File(filePath);
            fis = new FileInputStream(file);
            //设置ftp服务器存储路径
            ftpClient.changeWorkingDirectory(ftpPath);
            ftpClient.setBufferSize(1024);
            //ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.storeFile(ftpFileName, fis);
        } catch (IOException e) {
            log.error("FTP客户端连接异常！", e);
            e.printStackTrace();
            try {
                throw new Exception("FTP客户端连接异常！", e);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        } catch (Exception e) {
            log.error("FTP客户端连接异常！", e);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                FtpUtil.closeFtpConnect(ftpClient);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    /**
     * @param ftpClient FTP客户端连接
     * @throws Exception
     * @desc FTP客户端连接关闭
     */
    public static void closeFtpConnect(FTPClient ftpClient) throws Exception {
        if (ftpClient != null) {
            ftpClient.disconnect();
        }
    }
}
