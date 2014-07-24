package com.jscn.commons.core.utils;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @ClassName: FileUtil
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author dujinxin
 * @date 2011-12-15 下午5:36:04
 * 
 */
public class FileUtil {
    final static Logger LOG = LoggerFactory.getLogger(JsonUtil.class);

    /**
     * 获取文件扩展名
     * @param fileName
     * @return
     */
    public static String getFileExtName(String fileName) {
        String[] fileNames = fileName.split("\\p{Punct}");
        String file_ext_name = fileNames[fileNames.length - 1];
        return file_ext_name;
    }

    /**
     * 写文件
     * 
     * @param fileName
     * @param context
     * @param encoding
     * @return
     */
    public static boolean writeText(String fileName, String context, String encoding) {
        try {
            byte[] bs = context.getBytes(encoding);
            writeByte(fileName, bs);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 按字节读取文件
     * 
     * @param fileName
     * @return
     */
    public static byte[] readByte(String fileName) {
        try {
            FileInputStream fis = new FileInputStream(fileName);
            byte[] r = new byte[fis.available()];
            fis.read(r);
            fis.close();
            return r;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 按字节读取文件
     * 
     * @param f
     * @return
     */
    public static byte[] readByte(File f) {
        try {
            FileInputStream fis = new FileInputStream(f);
            byte[] r = readByte(fis);
            fis.close();
            return r;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 按字节读取文件
     * 
     * @param is
     * @return
     */
    public static byte[] readByte(InputStream is) {
        try {
            byte[] r = new byte[is.available()];
            is.read(r);
            return r;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 按字节写文件
     * 
     * @param fileName
     * @param b
     * @return
     */
    public static boolean writeByte(String fileName, byte[] b) {
        try {
            BufferedOutputStream fos = new BufferedOutputStream(new FileOutputStream(fileName));
            fos.write(b);
            fos.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 按字节写文件
     * 
     * @param f
     * @param b
     * @return
     */
    public static boolean writeByte(File f, byte[] b) {
        try {
            BufferedOutputStream fos = new BufferedOutputStream(new FileOutputStream(f));
            fos.write(b);
            fos.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 读取文件
     * 
     * @param f
     * @param encoding
     * @return
     */
    public static String readText(File f, String encoding) {
        try {
            InputStream is = new FileInputStream(f);
            String str = readText(is, encoding);
            is.close();
            return str;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 读取文件
     * 
     * @param is
     * @param encoding
     * @return
     */
    public static String readText(InputStream is, String encoding) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is, encoding));
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
            br.close();
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 读取文件
     * 
     * @param fileName
     * @param encoding
     * @return
     */
    public static String readText(String fileName, String encoding) {
        try {
            InputStream is = new FileInputStream(fileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(is, encoding));
            StringBuffer sb = new StringBuffer();
            int c = br.read();
            if ((!(encoding.equalsIgnoreCase("utf-8"))) || (c != 65279)) {
                sb.append((char) c);
            }
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
            br.close();
            is.close();
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 远程读取文件
     * 
     * @param urlPath
     * @param encoding
     * @return
     */
    public static String readURLText(String urlPath, String encoding) {
        try {
            URL url = new URL(urlPath);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(url.openStream(), encoding));
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = in.readLine()) != null) {
                sb.append(line + "\n");
            }
            in.close();
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 删除文件
     * 
     * @param path
     * @return
     */
    public static boolean delete(String path) {
        File file = new File(path);
        return delete(file);
    }

    /**
     * 删除文件
     * 
     * @param file
     * @return
     */
    public static boolean delete(File file) {
        if (!(file.exists())) {
            LOG.warn("文件或文件夹不存在");
            return false;
        }
        if (file.isFile()) {
            return file.delete();
        }
        return deleteDir(file);
    }

    private static boolean deleteDir(File dir) {
        try {
            return ((deleteFromDir(dir)) && (dir.delete()));
        } catch (Exception e) {
            LOG.warn("删除文件操作出错!");
        }
        return false;
    }

    /**
     * 删除文件
     * 
     * @param dirPath
     * @return
     */
    public static boolean deleteFromDir(String dirPath) {
        File file = new File(dirPath);
        return deleteFromDir(file);
    }

    /**
     * 删除文件
     * 
     * @param dir
     * @return
     */
    public static boolean deleteFromDir(File dir) {
        if (!dir.exists()) {
            LOG.warn("文件夹不存在!");
            return false;
        }
        if (!(dir.isDirectory())) {
            LOG.warn(dir + "不是文件夹!");
            return false;
        }
        File[] tempList = dir.listFiles();
        for (int i = 0; i < tempList.length; i++) {
            if (!(delete(tempList[i]))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 给文件重命名
     * 
     * @param path
     * @return
     */
    public static boolean mkdir(String path) {
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdir();
        }
        return true;
    }

    /**
     * 复制文件
     * 
     * @param oldPath
     * @param newPath
     * @param filter
     * @return
     */
    public static boolean copy(String oldPath, String newPath, FileFilter filter) {
        File oldFile = new File(oldPath);
        File[] oldFiles = oldFile.listFiles(filter);
        boolean flag = true;
        if (oldFiles != null) {
            for (int i = 0; i < oldFiles.length; ++i) {
                if (!(copy(oldFiles[i], newPath + "/" + oldFiles[i].getName()))) {
                    flag = false;
                }
            }
        }
        return flag;
    }

    /**
     * 复制文件
     * 
     * @param oldPath
     * @param newPath
     * @return
     */
    public static boolean copy(String oldPath, String newPath) {
        File oldFile = new File(oldPath);
        return copy(oldFile, newPath);
    }

    /**
     * 复制文件
     * 
     * @param oldFile
     * @param newPath
     * @param filter
     * @return
     */
    public static boolean copy(File oldFile, String newPath) {
        if (!(oldFile.exists())) {
            LOG.warn("文件或者文件夹不存在" + oldFile);
            return false;
        }
        if (oldFile.isFile()) {
            return copyFile(oldFile, newPath);
        }
        return copyDir(oldFile, newPath);
    }

    private static boolean copyFile(File oldFile, String newPath) {
        if (!(oldFile.exists())) {
            LOG.warn("文件不存在:" + oldFile);
            return false;
        }
        if (!(oldFile.isFile())) {
            LOG.warn(oldFile + "不是文件!");
            return false;
        }
        try {
            int byteread = 0;
            InputStream inStream = new FileInputStream(oldFile);
            FileOutputStream fs = new FileOutputStream(newPath);
            byte[] buffer = new byte[1024];
            while ((byteread = inStream.read(buffer)) != -1) {
                fs.write(buffer, 0, byteread);
            }
            fs.close();
            inStream.close();
        } catch (Exception e) {
            LOG.warn("复制单个文件" + oldFile.getPath() + "操作出错,错误原因:" + e.getMessage());
            return false;
        }
        return true;
    }

    private static boolean copyDir(File oldDir, String newPath) {
        if (!(oldDir.exists())) {
            LOG.warn("文件夹不存在:" + oldDir);
            return false;
        }
        if (!(oldDir.isDirectory())) {
            LOG.warn(oldDir + "不是文件夹!");
            return false;
        }
        try {
            new File(newPath).mkdirs();
            File[] files = oldDir.listFiles();
            File temp = null;
            for (int i = 0; i < files.length; i++) {
                temp = files[i];
                if (temp.isFile()) {
                    if (!(copyFile(temp, newPath + "/" + temp.getName()))) {
                        return false;
                    }
                } else if ((temp.isDirectory())
                        && (!(copyDir(temp, newPath + "/" + temp.getName())))) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            LOG.warn("复制整个文件夹内容操作出错.错误原因:" + e.getMessage());
        }
        return false;
    }

    /**
     * 将文件流写入自定文件
     * 
     * @param file
     * @param newPath
     * @return
     */
    public static boolean writerFile(File file, String newPath) {
        try {
            FileOutputStream fos = new FileOutputStream(newPath);
            FileInputStream fis = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = fis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
            fis.close();
            fos.close();
            return true;
        } catch (Exception e) {
            LOG.warn("写入文件失败!" + e.getMessage());
        }
        return false;
    }

    /**
     * 移动文件
     * 
     * @param oldPath
     * @param newPath
     * @return
     */
    public static boolean move(String oldPath, String newPath) {
        return ((copy(oldPath, newPath)) && (delete(oldPath)));
    }

    /**
     * 移动文件
     * 
     * @param oldFile
     * @param newPath
     * @return
     */
    public static boolean move(File oldFile, String newPath) {
        return ((copy(oldFile, newPath)) && (delete(oldFile)));
    }

    /**
     * 序列化
     * 
     * @param obj
     * @param fileName
     * @return
     */
    public static void serialize(Serializable obj, String fileName) {
        try {
            FileOutputStream f = new FileOutputStream(fileName);
            ObjectOutputStream s = new ObjectOutputStream(f);
            s.writeObject(obj);
            s.flush();
            s.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 序列化
     * 
     * @param obj
     * @return
     */
    public static byte[] serialize(Serializable obj) {
        try {
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            ObjectOutputStream s = new ObjectOutputStream(b);
            s.writeObject(obj);
            s.flush();
            s.close();
            return b.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 反序列化
     * 
     * @param fileName
     * @return
     */
    public static Object unserialize(String fileName) {
        try {
            FileInputStream in = new FileInputStream(fileName);
            ObjectInputStream s = new ObjectInputStream(in);
            Object o = s.readObject();
            s.close();
            return o;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 反序列化
     * 
     * @param bs
     * @return
     */
    public static Object unserialize(byte[] bs) {
        try {
            ByteArrayInputStream in = new ByteArrayInputStream(bs);
            ObjectInputStream s = new ObjectInputStream(in);
            Object o = s.readObject();
            s.close();
            return o;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
