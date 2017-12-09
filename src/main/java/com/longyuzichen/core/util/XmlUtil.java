package com.longyuzichen.core.util;

import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * com.longyuzichen.core.util
 *
 * @author longyuzichen@126.com
 * @version V1.0
 * @desc XML工具
 * @date 2017-03-24 23:23
 */
public class XmlUtil {
    private static final Logger log = LoggerFactory.getLogger(XmlUtil.class);

    public static Map<String, Object> saxStream2Map(InputStream inputStream) throws Exception {
        Element element = getSaxRootElement(inputStream);
        Map<String, Object> result = saxXml2map(element);
        return result;
    }

    /**
     * xml转换为map对象
     *
     * @param root
     * @return
     * @throws IOException
     * @throws JDOMException
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> saxXml2map(Element root) throws IOException, JDOMException {
        Map<String, Object> result = new HashMap<String, Object>();

        /* 获取子节点 */
        List<?> node = root.getChildren();

        if (node.size() == 1) {
            Element child = (Element) node.get(0);
            String key = child.getName();
            result = SaxChildValue(result, key, child);

        } else {

            Iterator iterator = node.iterator();
            List<Object> list = new ArrayList<Object>();
            while (iterator.hasNext()) {
                Element child = (Element) iterator.next();
                String name = child.getName();
                result = SaxChildValue(result, name, child);
            }
        }
        log.debug("xml转换Map对象结果 {}", result.toString());
        return result;
    }

    /**
     * 获取子节点结果集合
     *
     * @param result 返回的子节点集合
     * @param name   父节点名称
     * @param child  子节点集合
     * @return
     * @throws IOException
     * @throws JDOMException
     */
    public static Map<String, Object> SaxChildValue(Map<String, Object> result, String name, Element child) throws IOException, JDOMException {
        if (child.getChildren().size() > 0) {
            Map<String, Object> map = XmlUtil.saxXml2map(child);
            result.put(name, map);
        } else {
            String value = child.getText();
            result.put(name, value);
        }
        log.debug("xml子节点结果集合 {}", result.toString());
        return result;
    }

    /**
     * 获取根节点
     *
     * @param xmlpath
     * @return
     * @throws IOException
     * @throws JDOMException
     */
    public static Element getSaxRootElement(String xmlpath) throws IOException, JDOMException {
        FileInputStream fileInputStream = new FileInputStream(new File(xmlpath));
        //获取根节点
        Element root = getSaxRootElement(fileInputStream);
        return root;
    }

    /**
     * 获取根节点
     *
     * @param is
     * @return
     * @throws JDOMException
     * @throws IOException
     */
    public static Element getSaxRootElement(InputStream is) throws JDOMException, IOException {
        Document document = new SAXBuilder().build(is);
        Element root = document.getRootElement();
        log.debug("xml根节点{}", root.toString());
        return root;
    }

    /**
     * 获取根节点
     *
     * @param fis
     * @return
     * @throws JDOMException
     * @throws IOException
     */
    public static Element getSaxRootElement(FileInputStream fis) throws JDOMException, IOException {
        Element root = getSaxRootElement(fis);
        return root;
    }


    @SuppressWarnings("unchecked")
    public static Map<String, Object> dom2Map(org.dom4j.Document doc) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (doc == null)
            return map;
        org.dom4j.Element root = doc.getRootElement();
        map = dom2Map(root);
        return map;
    }

    @SuppressWarnings("unchecked")
    public static Map dom2Map(org.dom4j.Element e) {
        Map map = new HashMap();
        List list = e.elements();
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                org.dom4j.Element iter = (org.dom4j.Element) list.get(i);
                List mapList = new ArrayList();

                if (iter.elements().size() > 0) {
                    Map m = dom2Map(iter);
                    if (map.get(iter.getName()) != null) {
                        Object obj = map.get(iter.getName());
                        if (!obj.getClass().getName().equals("java.util.ArrayList")) {
                            mapList = new ArrayList();
                            mapList.add(obj);
                            mapList.add(m);
                        }
                        if (obj.getClass().getName().equals("java.util.ArrayList")) {
                            mapList = (List) obj;
                            mapList.add(m);
                        }
                        map.put(iter.getName(), mapList);
                    } else
                        map.put(iter.getName(), m);
                } else {
                    if (map.get(iter.getName()) != null) {
                        Object obj = map.get(iter.getName());
                        if (!obj.getClass().getName().equals("java.util.ArrayList")) {
                            mapList = new ArrayList();
                            mapList.add(obj);
                            mapList.add(iter.getText());
                        }
                        if (obj.getClass().getName().equals("java.util.ArrayList")) {
                            mapList = (List) obj;
                            mapList.add(iter.getText());
                        }
                        map.put(iter.getName(), mapList);
                    } else
                        map.put(iter.getName(), iter.getText());
                }
            }
        } else
            map.put(e.getName(), e.getText());
        return map;
    }

    public static org.dom4j.Document getRootDocument(File file) throws DocumentException {
        org.dom4j.Document document = new SAXReader().read(file);
        return document;
    }

    public static org.dom4j.Element getElement(File file) throws DocumentException {
        org.dom4j.Element element = getRootDocument(file).getRootElement();
        return element;
    }

}
