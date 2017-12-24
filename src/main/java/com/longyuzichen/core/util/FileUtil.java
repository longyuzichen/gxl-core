/**
 * Copyright [2017] guoxinlei(longyuzichen@126.com)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **/
package com.longyuzichen.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * com.longyuzichen.core.util
 *
 * @author longyuzichen@126.com
 * @version V1.0
 * @desc 文件工具类
 * @date 2017-03-24 22:50
 */
public class FileUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtil.class);
    protected static ResourceBundle labels = ResourceBundle.getBundle("com.longyuzichen.core.util.FileUtil", Locale.getDefault());

    private static final int BUFFER_SIZE = 1024;

    public static void copy(InputStream input, OutputStream output) throws IOException {
        byte[] byteSize = new byte[BUFFER_SIZE];
        int line;
        while ((line = input.read(byteSize)) != -1) {
            output.write(byteSize, 0, line);
        }
    }

    /**
     * 得到文件的类型。
     * 实际上就是得到文件名中最后一个“.”后面的部分。
     *
     * @param fileName 文件名
     * @return 文件名中的类型部分
     * @version V1.0
     */
    public static String getFileType(String fileName) {
        int point = fileName.lastIndexOf(".");
        int length = fileName.length();
        if (point == -1 || point == length - 1) {
            return "";
        } else {
            return fileName.substring(point + 1, length);
        }
    }

    /**
     * 清空指定目录中的文件。
     * 这个方法将尽可能删除所有的文件，但是只要有一个文件没有被删除都会返回false。
     * 另外这个方法不会迭代删除，即不会删除子目录及其内容。
     *
     * @param dir 要清空的目录
     * @return 目录下的所有文件都被成功删除时返回true，否则返回false.
     * @version V1.0
     */
    public static boolean emptyDirectory(File dir) {
        boolean result = true;
        File[] dirs = dir.listFiles();
        for (int i = 0; i < dirs.length; i++) {
            if (!(dirs[i].delete())) {
                result = false;
            }
        }
        return result;
    }

    /**
     * 创建指定的目录。
     * 如果指定的目录的父目录不存在则创建其目录书上所有需要的父目录。
     * <b>注意：可能会在返回false的时候创建部分父目录。</b>
     *
     * @param file 要创建的目录
     * @return 完全创建成功时返回true，否则返回false。
     * @version V1.0
     */
    public static boolean makeDirectory(File file) {
        File parent = file.getParentFile();
        if (parent != null) {
            return parent.mkdirs();
        }
        return false;
    }

    /**
     * @param from      源文件
     * @param to        目标文件
     * @param overwrite
     * @throws IOException
     * @desc 移动文件
     */
    public static void move(File from, File to, boolean overwrite) throws IOException {
        if (to.exists()) {
            if (overwrite) {
                if (!to.delete()) {
                    throw new IOException(
                            MessageFormat.format(labels.getString("deleteerror"),
                                    (Object[]) new String[]{to.toString()})
                    );
                }
            } else {
                throw new IOException(
                        MessageFormat.format(labels.getString("alreadyexistserror"),
                                (Object[]) new String[]{to.toString()})
                );
            }
        }

        if (from.renameTo(to)) return;

        InputStream in = null;
        OutputStream out = null;
        try {
            in = new FileInputStream(from);
            out = new FileOutputStream(to);
            copy(in, out);
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
            if (!from.delete()) {
                throw new IOException(
                        MessageFormat.format(labels.getString("deleteoriginalerror"),
                                (Object[]) new String[]{from.toString(), to.toString()})
                );
            }
        } finally {
            if (in != null) {
                in.close();
                in = null;
            }
            if (out != null) {
                out.flush();
                out.close();
                out = null;
            }
        }
    }

    /**
     * 文件分隔
     *
     * @param file 文件路徑
     * @param num  文件分割數量
     * @throws Exception
     */
    public static void splitFile(File file, int num) throws Exception {
        if (file.isFile()) {
            long l = file.length();
            //获取文件名称
            String fileName = file.getName().substring(0, file.getName().indexOf("."));

            //获取文件后缀名称
            String endName = file.getName().substring(file.getName().lastIndexOf("."));
            InputStream is = null;
            try {
                is = new FileInputStream(file);
                for (int i = 1; i <= num; i++) {
                    StringBuffer sb = new StringBuffer();
                    sb.append(file.getParent()).append("\\").append("_data").append(i).append(endName);
                    File file2 = new File(sb.toString());
                    //创建写文件的输出流
                    OutputStream out = new FileOutputStream(file2);
                    copy(is, out);
                    out.close();
                }
            } catch (FileNotFoundException e) {
                LOGGER.error("文件异常，找不到文件！", e);
            } catch (IOException e) {
                LOGGER.error("读取文件异常！", e);
            } finally {
                if (is != null) {
                    is.close();
                }
            }
        }

    }

    /**
     * 文件合并
     *
     * @param src 文件列表
     * @throws Exception
     */
    public static void joinFile(String... src) throws Exception {
        for (int i = 0; i < src.length; i++) {
            InputStream is = null;
            try {
                OutputStream out = null;
                StringBuilder sb = new StringBuilder();
                File file = new File(src[i]);
                String fileName = file.getName().substring(0, file.getName().indexOf("_"));
                String endName = file.getName().substring(file.getName().lastIndexOf("."));
                sb.append(file.getParent()).append("\\").append(fileName).append(endName);
                is = new FileInputStream(file);

                File file2 = new File(sb.toString());
                out = new FileOutputStream(file2, true);
                copy(is, out);
                out.flush();
                out.close();
            } catch (FileNotFoundException e) {
                LOGGER.error("文件异常，找不到文件！", e);
            } catch (IOException e) {
                LOGGER.error("读取文件异常！", e);
            } finally {
                if (is != null) {
                    is.close();
                }
            }
        }
    }
}
