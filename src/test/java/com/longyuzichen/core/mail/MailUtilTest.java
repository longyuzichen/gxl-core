package com.longyuzichen.core.mail;

import org.junit.Before;
import org.junit.Test;

/**
 * com.longyuzichen.core.mail
 *
 * @author longyuzichen@126.com
 * @version V1.0
 * @desc
 * @date 2017-05-08 20:56
 */

public class MailUtilTest {
    private String text = "你好啊，在干嘛呢";

    private String html = "<p>你好</p>你在干嘛呢，不去工作吗？<br/>" +
            "<img src=\"cid:memememe\">";

    private String html_1 = "<p>我不好</p><h1>大声说出你错了</h1>";

    @Test
    public void sendText() throws Exception {
        util.sendText("longyuzichen@126.com", "tre65", text);
    }

    @Test
    public void sendText1() throws Exception {
        util.sendText("longyuzichen@126.com", "5239xxxxx@qq.com", "longyuzichen@163.com", "ert1", text + 1);

    }

    @Test
    public void sendText2() throws Exception {
        util.sendText("longyuzichen@126.com", "5239xxxxx@qq.com", "", "rejy2", text + 2, new String[]{"f:\\Koala.jpg"});

    }

    @Test
    public void sendText3() throws Exception {
        util.sendText("longyuzichen@126.com", "请求信息", text + 3, new String[]{"f:\\Koala.jpg"}, "text/plan;charset=utf-8");
    }

    @Test
    public void sendHtml() throws Exception {
        util.sendHtml("longyuzichen@163.com", "r", html_1);
    }

    @Test
    public void sendHtml1() throws Exception {
        util.sendHtml("longyuzichen@126.com", "longyuzichen@163.com", "请假申请", html, "memememe", "f:\\Koala.jpg");
    }

    @Test
    public void sendHtml2() throws Exception {
        util.sendHtml("longyuzichen@126.com", "cesi1fdsg", html, "memememe", "f:\\Koala.jpg", new String[]{"f:\\Koala.jpg"}, "");
    }

    @Test
    public void sendHtml3() throws Exception {
        util.sendHtml("longyuzichen@126.com", "longyuzichen@qq.com", "", "cshi3dsg", html, "memememe", "f:\\Koala.jpg");
    }

    @Test
    public void sendHtml4() throws Exception {
        util.sendHtml("longyuzichen@163.com", null, "", "wo xiang qusi le ", html, "memememe","" , new String[]{"f:\\Koala.jpg"});
    }

    MailUtil util = null;

    @Before
    public void before() throws Exception {
    }

}