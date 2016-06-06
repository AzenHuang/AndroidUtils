package com.github.azenhuang.util;

import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * File Utils
 * <ul>
 * Read or write file
 * <li>{@link #readFile(String, String)} read file</li>
 * <li>{@link #readFileToList(String, String)} read file to string list</li>
 * <li>{@link #writeFile(String, String, boolean)} write file from String</li>
 * <li>{@link #writeFile(String, String)} write file from String</li>
 * <li>{@link #writeFile(String, Collection, boolean)} write file from String List</li>
 * <li>{@link #writeFile(String, Collection)} write file from String List</li>
 * <li>{@link #writeFile(String, InputStream)} write file</li>
 * <li>{@link #writeFile(String, InputStream, boolean)} write file</li>
 * <li>{@link #writeFile(File, InputStream)} write file</li>
 * <li>{@link #writeFile(File, InputStream, boolean)} write file</li>
 * </ul>
 * <ul>
 * Operate file
 * <li>{@link #copyFileRecursively(File, File)} or {@link #copyFileRecursively(String, String)}</li>
 * <li>{@link #deleteFileRecursively(File)} or {@link #deleteFileRecursively( String)}</li>
 * <li>{@link #moveFileRecursively(File, File)} or {@link #moveFileRecursively(String, String)}</li>
 * <li>{@link #getFileExtension(String)}</li>
 * <li>{@link #getFileName(String)}</li>
 * <li>{@link #getFileNameWithoutExtension(String)}</li>
 * <li>{@link #getFileSize(String)}</li>
 * <li>{@link #deleteFileRecursively(String)}</li>
 * <li>{@link #isFileExist(String)}</li>
 * <li>{@link #isFolderExist(String)}</li>
 * <li>{@link #makeFolders(String)}</li>
 * <li>{@link #makeDirs(String)}</li>
 * </ul>
 * 
 */
public class FileUtils {

    public final static String FILE_EXTENSION_SEPARATOR = ".";

    private FileUtils() {
        throw new AssertionError();
    }

    /**
     * read file
     * 
     * @param filePath
     * @param charsetName The name of a supported {@link java.nio.charset.Charset </code>charset<code>}
     * @return if file not exist, return null, else return content of file
     * @throws RuntimeException if an error occurs while operator BufferedReader
     */
    public static StringBuilder readFile(String filePath, String charsetName) throws IOException{
        File file = new File(filePath);
        StringBuilder fileContent = new StringBuilder();
        if (TextUtils.isEmpty(fileContent) || !file.isFile()) {
            return null;
        }

        BufferedReader reader = null;
        try {
            InputStreamReader is = new InputStreamReader(new FileInputStream(file), charsetName);
            reader = new BufferedReader(is);
            String line = null;
            while ((line = reader.readLine()) != null) {
//                if (!fileContent.toString().equals("")) {
//                    fileContent.append("\r\n");
//                }
                fileContent.append(line);
            }
            return fileContent;
        } catch (IOException e) {
            throw e;
        } finally {
            IOUtils.close(reader);
        }
    }

    /**
     * read file to string list, a element of list is a line
     *
     * @param filePath
     * @param charsetName The name of a supported {@link java.nio.charset.Charset </code>charset<code>}
     * @return if file not exist, return null, else return content of file
     * @throws RuntimeException if an error occurs while operator BufferedReader
     */
    public static List<String> readFileToList(String filePath, String charsetName) throws IOException {
        File file = new File(filePath);
        List<String> fileContent = new ArrayList<String>();
        if (file == null || !file.isFile()) {
            return null;
        }

        BufferedReader reader = null;
        try {
            InputStreamReader is = new InputStreamReader(new FileInputStream(file), charsetName);
            reader = new BufferedReader(is);
            String line = null;
            while ((line = reader.readLine()) != null) {
                fileContent.add(line);
            }
            return fileContent;
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred. ", e);
        } finally {
            IOUtils.close(reader);
        }
    }


    /**
     * write file, the string will be written to the begin of the file
     *
     * @param filePath
     * @param content
     * @return
     */
    public static boolean writeFile(String filePath, String content) throws IOException {
        return writeFile(filePath, content, false);
    }

    /**
     * write file
     *
     * @param filePath
     * @param content
     * @param append is append, if true, write to the end of file, else clear content of file and write into it
     * @return return false if content is empty, true otherwise
     * @throws RuntimeException if an error occurs while operator FileWriter
     */
    public static boolean writeFile(String filePath, String content, boolean append) throws IOException {
        if (StringUtils.isEmpty(content)) {
            return false;
        }

        FileWriter fileWriter = null;
        try {
            makeDirs(filePath);
            fileWriter = new FileWriter(filePath, append);
            fileWriter.write(content);
            return true;
        } catch (IOException e) {
            throw e;
        } finally {
            IOUtils.close(fileWriter);
        }
    }


    /**
     * write file, the string list will be written to the begin of the file
     *
     * @param filePath
     * @param contentCollection
     * @return
     */
    public static boolean writeFile(String filePath, Collection<String> contentCollection) throws IOException {
        return writeFile(filePath, contentCollection, false);
    }

    /**
     * write file
     *
     * @param filePath
     * @param contentCollection
     * @param append is append, if true, write to the end of file, else clear content of file and write into it
     * @return return false if contentCollection is empty, true otherwise
     * @throws RuntimeException if an error occurs while operator FileWriter
     */
    public static boolean writeFile(String filePath, Collection<String> contentCollection, boolean append) throws IOException {
        return writeFile(filePath, contentCollection, append, "\r\n");
    }


    /**
     * write file
     *
     * @param filePath
     * @param contentCollection
     * @param append is append, if true, write to the end of file, else clear content of file and write into it
     * @param separator
     * @return return false if contentCollection is empty, true otherwise
     * @throws RuntimeException if an error occurs while operator FileWriter
     */
    public static boolean writeFile(String filePath, Collection<String> contentCollection, boolean append, String separator) throws IOException {
        if (CollectionUtils.isEmpty(contentCollection)) {
            return false;
        }

        FileWriter fileWriter = null;
        try {
            makeDirs(filePath);
            fileWriter = new FileWriter(filePath, append);
            boolean isFirst = true;
            for (String line : contentCollection) {
                if (isFirst){
                    isFirst = false;
                }else {
                    fileWriter.write(separator);
                    fileWriter.write(line);
                }
            }
            return true;
        } catch (IOException e) {
            throw e;
        } finally {
            IOUtils.close(fileWriter);
        }
    }



    /**
     * write file, the bytes will be written to the begin of the file
     *
     * @param filePath
     * @param stream
     * @return
     * @see {@link #writeFile(String, InputStream, boolean)}
     */
    public static boolean writeFile(String filePath, InputStream stream) throws IOException {
        return writeFile(filePath, stream, false);
    }

    /**
     * write file
     *
     * @param stream the input stream
     * @param append if <code>true</code>, then bytes will be written to the end of the file rather than the beginning
     * @return return true
     * @throws RuntimeException if an error occurs while operator FileOutputStream
     */
    public static boolean writeFile(String filePath, InputStream stream, boolean append) throws IOException {
        return writeFile(filePath != null ? new File(filePath) : null, stream, append);
    }

    /**
     * write file, the bytes will be written to the begin of the file
     *
     * @param file
     * @param stream
     * @return
     * @see {@link #writeFile(File, InputStream, boolean)}
     */
    public static boolean writeFile(File file, InputStream stream) throws IOException {
        return writeFile(file, stream, false);
    }

    /**
     * write file
     *
     * @param file the file to be opened for writing.
     * @param stream the input stream
     * @param append if <code>true</code>, then bytes will be written to the end of the file rather than the beginning
     * @return return true
     * @throws RuntimeException if an error occurs while operator FileOutputStream
     */
    public static boolean writeFile(File file, InputStream stream, boolean append) throws IOException {
        OutputStream o = null;
        try {
            makeDirs(file.getAbsolutePath());
            o = new FileOutputStream(file, append);
            byte data[] = new byte[1024];
            int length = -1;
            while ((length = stream.read(data)) != -1) {
                o.write(data, 0, length);
            }
            o.flush();
            return true;
        } catch (FileNotFoundException e) {
            throw e;
        } catch (IOException e) {
            throw e;
        } finally {
            IOUtils.close(o);
            IOUtils.close(stream);
        }
    }




    /**
     * delete file or directory
     * <ul>
     * <li>if path is null or empty, return true</li>
     * <li>if path not exist, return true</li>
     * <li>if path exist, delete recursion. return true</li>
     * <ul>
     *
     * @param path
     * @return
     */
    public static boolean deleteFileRecursively(String path) {
        if (StringUtils.isBlank(path)) {
            return true;
        }
        return deleteFileRecursively(new File(path));
    }

    /**
     * delete file or directory
     * <ul>
     * <li>if file not exist, return true</li>
     *  <li>if file type isFile, return {@link File#delete}</li>
     * <li>if file exist, delete recursion. return true</li>
     * <ul>
     *
     * @param file
     * @return
     */
    public static boolean deleteFileRecursively(File file) {
        if (!file.exists()) {
            return true;
        }
        boolean isChildrenDeleted = true;
        if (!file.isDirectory()) {
            for (File child : file.listFiles()) {
                if (!(isChildrenDeleted = isChildrenDeleted && deleteFileRecursively(child))){
                    break;
                }
            }
        }

        //File.delete()用于删除“某个文件或者空目录”
        // 目录此时为空，可以删除
        return isChildrenDeleted && file.delete();
    }


    /**
     * 使用文件通道的方式递归复制文件
     *
     * @param sourcePath      源文件路径
     * @param destinationPath 复制到的新文件路径
     */
    public static boolean copyFileRecursively(String sourcePath, String destinationPath) throws IOException {
        if (TextUtils.isEmpty(sourcePath) || TextUtils.isEmpty(destinationPath)) {
            return false;
        }
        return copyFileRecursively(new File(sourcePath), new File(destinationPath));
    }


    /**
     * 使用文件通道的方式递归复制文件
     *
     * @param source      源文件
     * @param destination 复制到的新文件
     */
    public static boolean copyFileRecursively(File source, File destination) throws IOException {
        if (source == null || destination == null) {
            return false;
        }

        if (source.isDirectory()) {
            boolean hasChildrenCopied = true;
            if (!destination.exists()) {
                destination.mkdirs();
            }
            for (File file : source.listFiles()) {
                if (!(hasChildrenCopied = hasChildrenCopied && copyFileRecursively(file, new File(destination, file.getName())))) {
                    break;
                }
            }
            return hasChildrenCopied;
        } else {
            FileInputStream fi = null;
            FileOutputStream fo = null;
            FileChannel in = null;
            FileChannel out = null;
            try {
                fi = new FileInputStream(source);
                fo = new FileOutputStream(destination);
                in = fi.getChannel();//得到对应的文件通道
                out = fo.getChannel();//得到对应的文件通道
                in.transferTo(0, in.size(), out);//连接两个通道，并且从in通道读取，然后写入out通道
            } catch (IOException e) {
                throw e;
            } finally {
                IOUtils.close(fi);
                IOUtils.close(fo);
                IOUtils.close(in);
                IOUtils.close(out);
            }
            return true;
        }
    }

    /**
     * move file
     *
     * @param sourceFilePath
     * @param destFilePath
     */
    public static boolean moveFileRecursively(String sourceFilePath, String destFilePath) throws IOException {
        if (TextUtils.isEmpty(sourceFilePath) || TextUtils.isEmpty(destFilePath)) {
            return false;
        }
        return  moveFileRecursively(new File(sourceFilePath), new File(destFilePath));
    }

    /**
     * move file
     *
     * @param srcFile
     * @param destFile
     */
    public static boolean moveFileRecursively(File srcFile, File destFile) throws IOException {
        if (srcFile.renameTo(destFile)){
            return true;
        }else {
            return copyFileRecursively(srcFile, destFile)
                    && deleteFileRecursively(srcFile);
        }
    }

//    /**
//     * 递归删除文件
//     * @param filePath      文件路径
//     */
//    public static boolean delete(String filePath) throws IOException {
//        if (TextUtils.isEmpty(filePath)) {
//            return false;
//        }
//        return delete(new File(filePath));
//    }
//
//    /**
//     * 递归删除文件
//     * @param file      文件
//     */
//    public static boolean delete(File file) {
//        if (file ==null) {
//            return false;
//        }
//
//        if (file.isDirectory()) {
//            //递归删除目录中的子目录下
//            for (File child : file.listFiles()) {
//                if (!delete(child)) {
//                    return false;
//                }
//            }
//        }
//        //File.delete()用于删除“某个文件或者空目录”
//        // 目录此时为空，可以删除
//        return file.delete();
//    }

    /**
     * get file size
     * <ul>
     * <li>if path is null or empty, return -1</li>
     * <li>if path exist and it is a file, return file size, else return -1</li>
     * <ul>
     * 
     * @param path
     * @return returns the length of this file in bytes. returns -1 if the file does not exist.
     */
    public static long getFileSize(String path) {
        if (StringUtils.isBlank(path)) {
            return -1;
        }

        File file = new File(path);
        return (file.exists() && file.isFile() ? file.length() : -1);
    }



    public static String formatFileSize(long fileLen) {// 转换文件大小
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileLen < 1024) {
            fileSizeString = df.format((double) fileLen) + "B";
        } else if (fileLen < 1048576) {
            fileSizeString = df.format((double) fileLen / 1024) + "K";
        } else if (fileLen < 1073741824) {
            fileSizeString = df.format((double) fileLen / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileLen / 1073741824) + "G";
        }
        return fileSizeString;
    }

    /**
     * get file name from path, not include suffix
     *
     * <pre>
     *      getFileNameWithoutExtension(null)               =   null
     *      getFileNameWithoutExtension("")                 =   ""
     *      getFileNameWithoutExtension("   ")              =   "   "
     *      getFileNameWithoutExtension("abc")              =   "abc"
     *      getFileNameWithoutExtension("a.mp3")            =   "a"
     *      getFileNameWithoutExtension("a.b.rmvb")         =   "a.b"
     *      getFileNameWithoutExtension("c:\\")              =   ""
     *      getFileNameWithoutExtension("c:\\a")             =   "a"
     *      getFileNameWithoutExtension("c:\\a.b")           =   "a"
     *      getFileNameWithoutExtension("c:a.txt\\a")        =   "a"
     *      getFileNameWithoutExtension("/home/admin")      =   "admin"
     *      getFileNameWithoutExtension("/home/admin/a.txt/b.mp3")  =   "b"
     * </pre>
     *
     * @param filePath
     * @return file name from path, not include suffix
     * @see
     */
    public static String getFileNameWithoutExtension(String filePath) {
        if (StringUtils.isEmpty(filePath)) {
            return filePath;
        }

        int extenPosi = filePath.lastIndexOf(FILE_EXTENSION_SEPARATOR);
        int filePosi = filePath.lastIndexOf(File.separator);
        if (filePosi == -1) {
            return (extenPosi == -1 ? filePath : filePath.substring(0, extenPosi));
        }
        if (extenPosi == -1) {
            return filePath.substring(filePosi + 1);
        }
        return (filePosi < extenPosi ? filePath.substring(filePosi + 1, extenPosi) : filePath.substring(filePosi + 1));
    }

    /**
     * get file name from path, include suffix
     *
     * <pre>
     *      getFileName(null)               =   null
     *      getFileName("")                 =   ""
     *      getFileName("   ")              =   "   "
     *      getFileName("a.mp3")            =   "a.mp3"
     *      getFileName("a.b.rmvb")         =   "a.b.rmvb"
     *      getFileName("abc")              =   "abc"
     *      getFileName("c:\\")              =   ""
     *      getFileName("c:\\a")             =   "a"
     *      getFileName("c:\\a.b")           =   "a.b"
     *      getFileName("c:a.txt\\a")        =   "a"
     *      getFileName("/home/admin")      =   "admin"
     *      getFileName("/home/admin/a.txt/b.mp3")  =   "b.mp3"
     * </pre>
     *
     * @param filePath
     * @return file name from path, include suffix
     */
    public static String getFileName(String filePath) {
        if (StringUtils.isEmpty(filePath)) {
            return filePath;
        }

        int filePosi = filePath.lastIndexOf(File.separator);
        return (filePosi == -1) ? filePath : filePath.substring(filePosi + 1);
    }

    /**
     * get folder name from path
     *
     * <pre>
     *      getFolderName(null)               =   null
     *      getFolderName("")                 =   ""
     *      getFolderName("   ")              =   ""
     *      getFolderName("a.mp3")            =   ""
     *      getFolderName("a.b.rmvb")         =   ""
     *      getFolderName("abc")              =   ""
     *      getFolderName("c:\\")              =   "c:"
     *      getFolderName("c:\\a")             =   "c:"
     *      getFolderName("c:\\a.b")           =   "c:"
     *      getFolderName("c:a.txt\\a")        =   "c:a.txt"
     *      getFolderName("c:a\\b\\c\\d.txt")    =   "c:a\\b\\c"
     *      getFolderName("/home/admin")      =   "/home"
     *      getFolderName("/home/admin/a.txt/b.mp3")  =   "/home/admin/a.txt"
     * </pre>
     *
     * @param filePath
     * @return
     */
    public static String getFolderName(String filePath) {

        if (StringUtils.isEmpty(filePath)) {
            return filePath;
        }

        int filePosi = filePath.lastIndexOf(File.separator);
        return (filePosi == -1) ? "" : filePath.substring(0, filePosi);
    }

    /**
     * get suffix of file from path
     *
     * <pre>
     *      getFileExtension(null)               =   ""
     *      getFileExtension("")                 =   ""
     *      getFileExtension("   ")              =   "   "
     *      getFileExtension("a.mp3")            =   "mp3"
     *      getFileExtension("a.b.rmvb")         =   "rmvb"
     *      getFileExtension("abc")              =   ""
     *      getFileExtension("c:\\")              =   ""
     *      getFileExtension("c:\\a")             =   ""
     *      getFileExtension("c:\\a.b")           =   "b"
     *      getFileExtension("c:a.txt\\a")        =   ""
     *      getFileExtension("/home/admin")      =   ""
     *      getFileExtension("/home/admin/a.txt/b")  =   ""
     *      getFileExtension("/home/admin/a.txt/b.mp3")  =   "mp3"
     * </pre>
     *
     * @param filePath
     * @return
     */
    public static String getFileExtension(String filePath) {
        if (StringUtils.isBlank(filePath)) {
            return filePath;
        }

        int extenPosi = filePath.lastIndexOf(FILE_EXTENSION_SEPARATOR);
        int filePosi = filePath.lastIndexOf(File.separator);
        if (extenPosi == -1) {
            return "";
        }
        return (filePosi >= extenPosi) ? "" : filePath.substring(extenPosi + 1);
    }

    /**
     * Creates the directory named by the trailing filename of this file, including the complete directory path required
     * to create this directory. <br/>
     * <br/>
     * <ul>
     * <strong>Attentions:</strong>
     * <li>makeDirs("C:\\Users\\Trinea") can only create users folder</li>
     * <li>makeFolder("C:\\Users\\Trinea\\") can create Trinea folder</li>
     * </ul>
     *
     * @param filePath
     * @return true if the necessary directories have been created or the target directory already exists, false one of
     *         the directories can not be created.
     *         <ul>
     *         <li>if {@link FileUtils#getFolderName(String)} return null, return false</li>
     *         <li>if target directory already exists, return true</li>
     *         <li>return {@link File#mkdirs()}</li>
     *         </ul>
     */
    public static boolean makeDirs(String filePath) {
        String folderName = getFolderName(filePath);
        if (TextUtils.isEmpty(folderName)) {
            return false;
        }

        File folder = new File(folderName);
        return (folder.exists() && folder.isDirectory()) ? true : folder.mkdirs();
    }

    /**
     * @param filePath
     * @return
     * @see #makeDirs(String)
     */
    public static boolean makeFolders(String filePath) {
        return makeDirs(filePath);
    }

    /**
     * Indicates if this file represents a file on the underlying file system.
     *
     * @param filePath
     * @return
     */
    public static boolean isFileExist(String filePath) {
        if (StringUtils.isBlank(filePath)) {
            return false;
        }

        File file = new File(filePath);
        return (file.exists() && file.isFile());
    }

    /**
     * Indicates if this file represents a directory on the underlying file system.
     *
     * @param directoryPath
     * @return
     */
    public static boolean isFolderExist(String directoryPath) {
        if (StringUtils.isBlank(directoryPath)) {
            return false;
        }

        File dire = new File(directoryPath);
        return (dire.exists() && dire.isDirectory());
    }


}
