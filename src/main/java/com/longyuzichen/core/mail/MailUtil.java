package com.longyuzichen.core.mail;


import com.sun.mail.util.MailSSLSocketFactory;
import com.sun.org.apache.xpath.internal.operations.Mult;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
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
    enum MimeType {
        HTML_GBK, HTML_UTF8, PLAIN
    }

    private static final Properties props = new Properties();
    private static final String MIMETYPE = "text/html;charset=utf-8";
    private static Session session = null;

    private static String host = "";
    private static String from = "";
    private static String user = "";
    private static String password = "";
    private static int prot = 25;
    private static String protocol = "smtp";
    private static int port = 25;
    private static boolean auth = true;
    private static boolean sslEnable = false;
    private static boolean isDebug = false;

    public MailUtil(String host, String from, String user, String password) {
        this(host, from, user, password, 25);
    }

    public MailUtil(String host, String from, String user, String password, int prot) {
        this(host, from, user, password, "smtp", prot, false,false);
    }

    public MailUtil(String host, String from, String user, String password, boolean isDebug) {
        this(host, from, user, password, "smtp", 25, false,  isDebug);
    }

    public MailUtil(String host, String from, String user, String password, String protocol, int port,
                    boolean sslEnable, boolean isDebug) {
        this.user = user;
        this.host = host;
        this.from = from;
        this.password = password;
        this.protocol = protocol;
        this.port = port;
       // this.auth = auth;
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
        sendText(to, cc, bcc, subject, text, fileNames, "text/plain;charset=utf-8");
    }

   /* public static void sendText(String to, String cc, String bcc, String subject, String text, String[] fileNames, String mimetype) throws MessagingException {
        sendText(to, cc, bcc, subject, text, fileNames, mimetype);
    }*/

    public static void sendText(String to, String subject, String text, String[] fileNames, String mimetype) throws MessagingException {
        sendText(to, null, null, subject, text, fileNames, mimetype);
    }

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
        //mimeMessage.setText(text,"UTF-8");
        Multipart multipart = sendTextMuilt(text, cid, cidFile, fileNames, mimetype);
        mimeMessage.setContent(multipart);
        return mimeMessage;
    }


    private static Multipart sendTextMuilt(String text, String cid, String cidFile, String[] fileNames, String mimetype) throws MessagingException {
        //创建邮件内容
        Multipart multipart = new MimeMultipart();
        //创建邮件内容
        MimeBodyPart body = new MimeBodyPart();
        //设置邮件内容
        body.setContent(text, (mimetype != null && !"".equals(mimetype) ? mimetype : MIMETYPE));
        multipart.addBodyPart(body);//发件内容

        if (null != cid && null != cidFile) {
            body = new MimeBodyPart();
            DataSource fds = new FileDataSource(cidFile);
            body.setDataHandler(new DataHandler(fds));
            //body.setHeader("Content-ID", cid);
            body.setContentID(cid);
        }

        //设置附件
        if (null != fileNames && fileNames.length > 0) {
            for (String file : fileNames) {
                MimeBodyPart attache = new MimeBodyPart();
                attache.setDataHandler(new DataHandler(new FileDataSource(new File(file))));
                multipart.addBodyPart(attache);
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
        //设置发送者
        mimeMessage.setFrom(new InternetAddress(from));
        //设置收件人邮箱
        mimeMessage.setRecipients(javax.mail.Message.RecipientType.TO, to);
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
