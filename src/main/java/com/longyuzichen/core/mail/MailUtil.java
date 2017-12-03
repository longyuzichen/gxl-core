package com.longyuzichen.core.mail;


import com.sun.mail.util.MailSSLSocketFactory;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.Properties;

/**
 * com.longyuzichen.core.mail
 *
 * @author longyuzichen@126.com
 * @version V1.0
 * @desc
 * @date 2017-05-07 21:58
 */
public class MailUtil {
//    enum MimeType {
//        HTML_GBK, HTML_UTF8, PLAIN
//    }

    private static final Properties props = new Properties();
    public static final String MIMETYPE = "text/html;charset=utf-8";
    public static final int PORT = 25;
    private static Session session = null;

    private static String host = "";
    private static String from = "";
    private static String user = "";
    private static String password = "";
    private static int prot = PORT;
    private static String protocol = "smtp";
    private static int port = 25;
    private static boolean auth = true;
    private static boolean sslEnable = false;
    private static boolean isDebug = false;

    public MailUtil(String host, String from, String user, String password) {
        this(host, from, user, password, PORT);
    }

    public MailUtil(String host, String from, String user, String password, int prot) {
        this(host, from, user, password, "smtp", prot, false, false);
    }

    public MailUtil(String host, String from, String user, String password, boolean isDebug) {
        this(host, from, user, password, "smtp", PORT, false, isDebug);
    }

    public MailUtil(String host, String from, String user, String password, String protocol, int port,
                    boolean sslEnable, boolean isDebug) {
        this.user = user;
        this.host = host;
        this.from = from;
        this.password = password;
        this.protocol = protocol;
        this.port = port;
        this.sslEnable = sslEnable;
        this.isDebug = isDebug;
        init();
    }

    private static void init() {
        props.put("mail.smtp.host", host);//smtp服务器
        props.put("mail.smtp.auth", auth);//是否需要用户名密码鉴权
        props.put("mail.transport.protocol", protocol);//协议名称
        props.put("mail.smtp.socketFactory.port", port); //服务器端口
        props.put("mail.smtp.starttls.enable", sslEnable); //如果是ssl端口，需要加这个属性。
        if (sslEnable) {
            MailSSLSocketFactory sf = null;
            try {
                sf = new MailSSLSocketFactory();
            } catch (GeneralSecurityException e) {
                e.printStackTrace();
            }
            sf.setTrustAllHosts(true);
        }
        session = !auth ? Session.getDefaultInstance(props) : Session.getDefaultInstance(props, authenticator);
        session.setDebug(isDebug);
    }

    public static void sendText(String to, String subject, String text) throws MessagingException {
        sendText(to, null, null, subject, text);
    }

    public static void sendText(String to, String cc, String bcc, String subject, String text) throws MessagingException {
        sendText(to, cc, bcc, subject, text, null);
    }

    public static void sendText(String to, String cc, String bcc, String subject, String text, String[] fileNames) throws MessagingException {
        sendText(to, cc, bcc, subject, text, fileNames, MIMETYPE);
    }

    public static void sendText(String to, String subject, String text, String[] fileNames, String mimetype) throws MessagingException {
        sendText(to, null, null, subject, text, fileNames, mimetype);
    }

    /**
     * @param to        收件人邮件地址
     * @param cc        抄送人邮箱地址
     * @param bcc       密送人邮箱地址（网易可能会识别为垃圾邮件，直接抛出异常）
     * @param subject   主题
     * @param text      内容
     * @param fileNames 附件名称
     * @param mimetype  类型
     * @throws MessagingException
     */
    private static void sendText(String to, String cc, String bcc, String subject, String text, String[] fileNames, String mimetype) throws MessagingException {
        MimeMessage mimeMessage = send(to, cc, bcc, subject, text, null, null, fileNames, mimetype);
        Transport.send(mimeMessage);
    }

    public static void sendHtml(String to, String subject, String text) throws MessagingException {
        sendHtml(to, null, subject, text, null, null);
    }

    public static void sendHtml(String to, String cc, String subject, String text, String cid, String cidFile) throws MessagingException {
        sendHtml(to, cc, null, subject, text, cid, cidFile, null);
    }


    public static void sendHtml(String to, String subject, String text, String cid, String cidFile, String[] fileNames, String mimetype) throws MessagingException {
        sendHtml(to, null, null, subject, text, cid, cidFile, fileNames, mimetype);
    }

    public static void sendHtml(String to, String cc, String bcc, String subject, String text, String cid, String cidFile) throws MessagingException {
        sendHtml(to, cc, bcc, subject, text, cid, cidFile, null, MIMETYPE);
    }

    public static void sendHtml(String to, String cc, String bcc, String subject, String text, String cid, String cidFile, String[] fileNames) throws MessagingException {
        sendHtml(to, cc, bcc, subject, text, cid, cidFile, fileNames, MIMETYPE);
    }

    private static void sendHtml(String to, String cc, String bcc, String subject, String text, String cid, String cidFile, String[] fileNames, String mimetype) throws MessagingException {
        MimeMessage mimeMessage = send(to, cc, bcc, subject, text, cid, cidFile, fileNames, mimetype);
        Transport.send(mimeMessage);
    }


    private static MimeMessage send(String to, String cc, String bcc, String subject, String text, String cid, String cidFile, String[] fileNames, String mimetype) throws MessagingException {
        MimeMessage mimeMessage = createMimeMessage(to, cc, bcc);
        mimeMessage.setSubject(subject, "UTF-8");
        mimeMessage.setSentDate(new Date());
        Multipart multipart = sendTextMuilt(text, cid, cidFile, fileNames, mimetype);
        mimeMessage.setContent(multipart);
        mimeMessage.saveChanges(); // 保存邮件
        return mimeMessage;
    }


    private static Multipart sendTextMuilt(String text, String cid, String cidFile, String[] fileNames, String mimetype) throws MessagingException {
        //创建邮件内容
        Multipart multipart = new MimeMultipart();
        //创建邮件内容
        MimeBodyPart body = new MimeBodyPart();
        body.setContent(text, (mimetype != null && !"".equals(mimetype) ? mimetype : MIMETYPE));
        multipart.addBodyPart(body);//发件内容
        if (null != cid && null != cidFile && !"".equals(cidFile)) {
            body = new MimeBodyPart();
            DataSource fds = new FileDataSource(cidFile);
            body.setDataHandler(new DataHandler(fds));
            body.setContentID(cid);
            multipart.addBodyPart(body);
        }

        //设置附件
        if (null != fileNames && fileNames.length > 0) {
            for (String file : fileNames) {
                if (file != null && !"".equals(file)) {  // 添加附件的内容
                    File attachment = new File(file);
                    BodyPart attachmentBodyPart = new MimeBodyPart();
                    DataSource source = new FileDataSource(attachment);
                    attachmentBodyPart.setDataHandler(new DataHandler(source));
                    // 网上流传的解决文件名乱码的方法，其实用MimeUtility.encodeWord就可以很方便的搞定
                    // 这里很重要，通过下面的Base64编码的转换可以保证你的中文附件标题名在发送时不会变成乱码
                    //sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
                    //messageBodyPart.setFileName("=?GBK?B?" + enc.encode(attachment.getName().getBytes()) + "?=");
                    //MimeUtility.encodeWord可以避免文件名乱码
                    try {
                        attachmentBodyPart.setFileName(MimeUtility.encodeWord(attachment.getName()));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    multipart.addBodyPart(attachmentBodyPart);
                }
            }
        }

        return multipart;
    }


    /**
     * 创建MimeMessage信息
     *
     * @param to
     * @throws MessagingException
     * @rturn
     */
    private static MimeMessage createMimeMessage(String to, String cc, String bcc) throws MessagingException {
        MimeMessage mimeMessage = new MimeMessage(session);
        mimeMessage.setFrom(new InternetAddress(from)); //设置发送者
        mimeMessage.setRecipients(javax.mail.Message.RecipientType.TO, to); //设置收件人邮箱
        if (null != cc && cc.length() > 0) {
            mimeMessage.setRecipients(Message.RecipientType.CC, cc);
        }
        if (null != bcc && bcc.length() > 0) {
            mimeMessage.setRecipients(Message.RecipientType.BCC, bcc);
        }
        return mimeMessage;
    }


    /**
     * 邮箱验证器
     */
    private static Authenticator authenticator = new Authenticator() {
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(user, password);
        }
    };


}
