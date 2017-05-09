package com.longyuzichen.core.mail;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * com.longyuzichen.core.mail
 *
 * @author longyuzichen@126.com
 * @version V1.0
 * @desc
 * @date 2017-05-08 20:56
 */

public class MailUtilTest {
    private String text = "text";

    private String html = "<p>你好</p><br/>" +
            "<img src=\"cid:memememe\">";

    private String html_1 = "<p>我不好</p><h1>大声说出你错了</h1>";

    @Test
    public void sendText() throws Exception {
        util.sendText("longyuzichen@126.com","tre65",text);

    }

    @Test
    public void sendText1() throws Exception {
        util.sendText("longyuzichen@126.com","523974882@qq.com","longyuzichen@qq.com","ert1",text+1);

    }

    @Test
    public void sendText2() throws Exception {
        util.sendText("longyuzichen@126.com","523974882@qq.com","","rejy2",text+2,new String[]{"f:\\Koala.jpg"});

    }

    @Test
    public void sendText3() throws Exception {
        util.sendText("longyuzichen@126.com","3",text+3,new String[]{"f:\\Koala.jpg"},"text/plan");

    }



    @Test
    public void sendHtml() throws Exception {
        util.sendHtml("longyuzichen@126.com","r",html_1);
    }

    @Test
    public void sendHtml1() throws Exception {
        util.sendHtml( "longyuzichen@126.com", "longyuzichen@163.com", "请假申请", html,  "memememe", "f:\\Koala.jpg");
    }

    @Test
    public void sendHtml2() throws Exception {
        util.sendHtml("longyuzichen@126.com",  "cesi1fdsg", html,  "memememe","f:\\Koala.jpg", new String[]{"f:\\Koala.jpg"}, "");
    }

    @Test
    public void sendHtml3() throws Exception {
       util. sendHtml("longyuzichen@126.com", "longyuzichen@qq.com", "", "cshi3dsg", html,  "memememe","f:\\Koala.jpg");
    }

    @Test
    public void sendHtml4() throws Exception {
        util.sendHtml("longyuzichen@126.com", null, "", "wo xiang qusi le ", html, "memememe","f:\\Koala.jpg", null);
    }



    MailUtil util = null;

    @Before
    public void before() throws Exception{
        //util = new MailUtil("smtp.163.com","xxx@163.com","xxx@163.com","");
        // util = new MailUtil("smtp.163.com","xxx@163.com","xxx@163.com","",25);

        //util = new MailUtil("smtp.163.com","xxx@163.com","xxx@163.com","","smtp",25,true,true);
       util = new MailUtil("smtp.163.com","xxx@163.com","xxx@163.com","",true);

    }



}