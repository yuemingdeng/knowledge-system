package com.example.utils;

import java.io.*;
import java.nio.file.*;
import java.util.zip.*;

/**
 * 文件处理
 */
public class FileUtils {

    /**
     * 读取文件内容
     *
     * @param filePath 文件路径
     * @return 文件内容
     */
    public static String readFile(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }

    /**
     * 写入文件内容
     *
     * @param filePath 文件路径
     * @param content  文件内容
     */
    public static void writeFile(String filePath, String content) throws IOException {
        Files.write(Paths.get(filePath), content.getBytes());
    }

    /**
     * 复制文件
     *
     * @param sourcePath 源文件路径
     * @param targetPath 目标文件路径
     */
    public static void copyFile(String sourcePath, String targetPath) throws IOException {
        Files.copy(Paths.get(sourcePath), Paths.get(targetPath), StandardCopyOption.REPLACE_EXISTING);
    }

    /**
     * 移动文件
     *
     * @param sourcePath 源文件路径
     * @param targetPath 目标文件路径
     */
    public static void moveFile(String sourcePath, String targetPath) throws IOException {
        Files.move(Paths.get(sourcePath), Paths.get(targetPath), StandardCopyOption.REPLACE_EXISTING);
    }

    /**
     * 删除文件或目录
     *
     * @param path 文件或目录路径
     */
    public static void deleteFile(String path) throws IOException {
        Files.deleteIfExists(Paths.get(path));
    }

    /**
     * 压缩文件或目录为 ZIP 文件
     *
     * @param sourcePath 源文件或目录路径
     * @param zipPath    ZIP 文件路径
     */
    public static void zip(String sourcePath, String zipPath) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(zipPath);
             ZipOutputStream zipOut = new ZipOutputStream(fos)) {
            File fileToZip = new File(sourcePath);
            zipFile(fileToZip, fileToZip.getName(), zipOut);
        }
    }

    private static void zipFile(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException {
        if (fileToZip.isHidden()) {
            return;
        }
        if (fileToZip.isDirectory()) {
            if (fileName.endsWith("/")) {
                zipOut.putNextEntry(new ZipEntry(fileName));
                zipOut.closeEntry();
            } else {
                zipOut.putNextEntry(new ZipEntry(fileName + "/"));
                zipOut.closeEntry();
            }
            File[] children = fileToZip.listFiles();
            for (File childFile : children) {
                zipFile(childFile, fileName + "/" + childFile.getName(), zipOut);
            }
            return;
        }
        try (FileInputStream fis = new FileInputStream(fileToZip)) {
            ZipEntry zipEntry = new ZipEntry(fileName);
            zipOut.putNextEntry(zipEntry);
            byte[] bytes = new byte[1024];
            int length;
            while ((length = fis.read(bytes)) >= 0) {
                zipOut.write(bytes, 0, length);
            }
        }
    }

    /**
     * 解压 ZIP 文件
     *
     * @param zipPath  ZIP 文件路径
     * @param destPath 解压目标路径
     */
    public static void unzip(String zipPath, String destPath) throws IOException {
        File destDir = new File(destPath);
        if (!destDir.exists()) {
            destDir.mkdir();
        }
        try (ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipPath))) {
            ZipEntry entry = zipIn.getNextEntry();
            while (entry != null) {
                File filePath = new File(destDir, entry.getName());
                if (!entry.isDirectory()) {
                    extractFile(zipIn, filePath);
                } else {
                    filePath.mkdirs();
                }
                zipIn.closeEntry();
                entry = zipIn.getNextEntry();
            }
        }
    }

    private static void extractFile(ZipInputStream zipIn, File filePath) throws IOException {
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath))) {
            byte[] bytes = new byte[1024];
            int length;
            while ((length = zipIn.read(bytes)) != -1) {
                bos.write(bytes, 0, length);
            }
        }
    }

    /**
     * 压缩文件为 GZIP 文件
     *
     * @param sourcePath 源文件路径
     * @param gzipPath   GZIP 文件路径
     */
    public static void gzip(String sourcePath, String gzipPath) throws IOException {
        try (FileInputStream fis = new FileInputStream(sourcePath);
             FileOutputStream fos = new FileOutputStream(gzipPath);
             GZIPOutputStream gzipOut = new GZIPOutputStream(fos)) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = fis.read(buffer)) > 0) {
                gzipOut.write(buffer, 0, len);
            }
        }
    }

    /**
     * 解压 GZIP 文件
     *
     * @param gzipPath  GZIP 文件路径
     * @param destPath  解压目标路径
     */
    public static void gunzip(String gzipPath, String destPath) throws IOException {
        try (GZIPInputStream gzipIn = new GZIPInputStream(new FileInputStream(gzipPath));
             FileOutputStream fos = new FileOutputStream(destPath)) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = gzipIn.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
        }
    }

    /**
     * 根据文件扩展名判断文件类型
     *
     * @param fileName 文件名
     * @return 文件类型
     */
    public static String getFileType(String fileName) {
        if (fileName == null || fileName.lastIndexOf(".") == -1) {
            return "Unknown";
        }
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        switch (extension) {
            case "txt":
                return "Text File";
            case "pdf":
                return "PDF Document";
            case "jpg":
            case "jpeg":
            case "png":
            case "gif":
                return "Image File";
            case "zip":
                return "ZIP Archive";
            case "gz":
                return "GZIP Archive";
            default:
                return "Unknown";
        }
    }

    public static void main(String[] args) {
        try {
            // 文件读写
            String filePath = "test.txt";
            writeFile(filePath, "Hello, World!");
            System.out.println("File content: " + readFile(filePath));

            // 文件复制
            String copyPath = "test_copy.txt";
            copyFile(filePath, copyPath);
            System.out.println("Copied file content: " + readFile(copyPath));

            // 文件移动
            String movePath = "test_moved.txt";
            moveFile(copyPath, movePath);
            System.out.println("Moved file content: " + readFile(movePath));

            // 文件删除
            deleteFile(movePath);
            System.out.println("File deleted: " + !Files.exists(Paths.get(movePath)));

            // 文件压缩与解压
            String zipPath = "test.zip";
            zip(filePath, zipPath);
            System.out.println("File zipped: " + Files.exists(Paths.get(zipPath)));

            String unzipPath = "unzipped";
            unzip(zipPath, unzipPath);
            System.out.println("File unzipped: " + Files.exists(Paths.get(unzipPath + "/test.txt")));

            // 文件类型判断
            System.out.println("File type: " + getFileType(filePath)); // Text File
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
