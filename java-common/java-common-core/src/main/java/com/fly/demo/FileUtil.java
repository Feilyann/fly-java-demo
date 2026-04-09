package com.fly.demo;

import cn.hutool.core.util.StrUtil;
import com.creatar.common.core.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.compressors.CompressorException;
import org.apache.commons.compress.compressors.CompressorStreamFactory;
import org.apache.commons.compress.utils.IOUtils;

import java.io.*;
import java.lang.IllegalArgumentException;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Slf4j
public class FileUtil {

    public static String extractFileName(String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            return "";
        }
        int lastSlashIndex = filePath.lastIndexOf('/');
        if (lastSlashIndex == -1) {
            // 尝试 Windows 路径分隔符
            lastSlashIndex = filePath.lastIndexOf('\\');
        }
        return lastSlashIndex == -1 ? filePath : filePath.substring(lastSlashIndex + 1);
    }

    public static String readFileToStr(String filePath) throws FileNotFoundException, UnsupportedEncodingException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException();
        }
        String charset = CharsetUtil.getEncoding(file);
        if (StrUtil.isBlank(charset)) {
            charset = "UTF-8";
        }
        return readFileToStr(filePath, charset);
    }

    public static String readFileToStr(String filePath, String charset) throws UnsupportedEncodingException, FileNotFoundException {
        byte[] bytes = readFileToBytes(filePath);
        if (bytes == null) {
            return null;
        }
        return new String(bytes, charset);
    }

    public static InputStream readFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            return null;
        }
        try {
            return Files.newInputStream(file.toPath());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessException(e.getMessage());
        }
    }

    public static byte[] readFileToBytes(String filePath) throws FileNotFoundException {
        filePath = filePath.replaceAll("\\\\", "/");
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException("文件不存在: " + filePath);
        }
        try (FileInputStream fis = new FileInputStream(filePath);
             ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            byte[] buf = new byte[8192];
            int len;
            while ((len = fis.read(buf)) != -1) {
                bos.write(buf, 0, len);
            }
            return bos.toByteArray();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public static byte[] readFileToBytes(InputStream inputStream) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }
            return baos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Deprecated
    public static void searchAllFileUrls(String path, List<String> urls) {
        File file = new File(path);
        if (!file.exists()) {
            return;
        }
        File[] subFiles = file.listFiles();
        if (subFiles == null) {
            return;
        }
        for (File subFile : subFiles) {
            if (subFile.isFile()) {
                urls.add(subFile.getPath());
                continue;
            }
            searchAllFileUrls(subFile.getPath(), urls);
        }
    }

    public static List<String> searchAllFileUrls(String path) {
        List<String> urls;
        File file = new File(path);
        if (!file.exists()) {
            return Collections.emptyList();
        }
        File[] subFiles = file.listFiles();
        if (subFiles == null) {
            return Collections.emptyList();
        }
        urls = new ArrayList<>();
        for (File subFile : subFiles) {
            if (subFile.isFile()) {
                urls.add(subFile.getPath());
                continue;
            }
            urls.addAll(searchAllFileUrls(subFile.getPath()));
        }
        return urls;
    }

    public static String readToString(InputStream inputStream) throws IOException {
        return readToString(inputStream, "UTF-8");
    }

    public static String readToString(InputStream inputStream, String charset) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, charset))) {
            StringBuilder text = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line).append(System.lineSeparator());
            }
            // 移除最后多余的换行符
            int length = text.length();
            if (length > 0) {
                text.setLength(length - System.lineSeparator().length());
            }
            return text.toString();
        }
    }

    public static String readToString(InputStreamReader inputStreamReader) throws IOException {
        try (BufferedReader br = new BufferedReader(inputStreamReader)) {
            StringBuilder text = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line).append(System.lineSeparator());
            }
            // 移除最后多余的换行符
            int length = text.length();
            if (length > 0) {
                text.setLength(length - System.lineSeparator().length());
            }
            return text.toString();
        }
    }

    public static void writeBytesToFile(String filePath, byte[] bytes) {
        write(filePath, new ByteArrayInputStream(bytes));
    }

    public static void writeStrToFile(String filePath, String content) {
        write(filePath, new ByteArrayInputStream(content.getBytes()));
    }

    public static void write(String path, InputStream inputStream) {
        if (inputStream == null) {
            return;
        }
        try {
            File localFile = new File(path);
            Files.createDirectories(localFile.getParentFile().toPath());
            try (OutputStream os = Files.newOutputStream(localFile.toPath());
                 BufferedOutputStream bos = new BufferedOutputStream(os)) {
                byte[] bytes = new byte[8192];
                int i;
                while ((i = inputStream.read(bytes)) != -1) {
                    bos.write(bytes, 0, i);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveToPath(InputStream inputStream, String filePath) throws IOException {
        File localFile = new File(filePath);
        Files.createDirectories(localFile.getParentFile().toPath());
        try (OutputStream os = Files.newOutputStream(localFile.toPath());
             BufferedOutputStream bos = new BufferedOutputStream(os)) {
            byte[] bytes = new byte[8192];
            int i;
            while ((i = inputStream.read(bytes)) != -1) {
                bos.write(bytes, 0, i);
            }
        }
    }

    public static void saveToPath(InputStream inputStream, String parentPath, String name) throws IOException {
        saveToPath(inputStream, parentPath + "/" + name);
    }

    public static void copy(String source, String destination) {
        source = source.replaceAll("\\\\", "/");
        destination = destination.replaceAll("\\\\", "/");
        if (source.equals(destination)) {
            return;
        }
        cn.hutool.core.io.FileUtil.copy(source, destination, true);
    }

    public static void move(String source, String destination) {
        source = source.replaceAll("\\\\", "/");
        destination = destination.replaceAll("\\\\", "/");
        if (source.equals(destination)) {
            return;
        }
        cn.hutool.core.io.FileUtil.move(new File(source).toPath(), new File(destination).toPath(), true);
    }

    public static void deleteFile(String filePath) throws IOException {
        if (new File(filePath).exists()) {
            Files.walkFileTree(Paths.get(filePath),
                    new SimpleFileVisitor<Path>() {
                        @Override
                        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                            Files.delete(file);
                            return FileVisitResult.CONTINUE;
                        }

                        @Override
                        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                            Files.delete(dir);
                            return FileVisitResult.CONTINUE;
                        }
                    });

        }
    }

    public static void deleteEmptyDir(String dirPath) throws IOException {
        File file = new File(dirPath);
        if (!file.exists() || file.isFile()) {
            return;
        }
        String[] list = file.list();
        if (list == null || list.length > 0) {
            return;
        }
        deleteFile(dirPath);
    }

    public static boolean exsit(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }

    public static void merge(List<String> fromList, String to) throws IOException {
        log.info("start merge ......");
        long startTime = System.currentTimeMillis();

        // 使用 try-with-resources 确保资源正确关闭
        try (FileOutputStream fops = new FileOutputStream(to);
             FileChannel outChannel = fops.getChannel()) {

            long start = 0;
            for (String from : fromList) {
                log.info("merge: {}", from);
                File fromFile = new File(from);
                if (!fromFile.exists()) {
                    throw new FileNotFoundException("源文件不存在: " + from);
                }
                try (FileInputStream fips = new FileInputStream(fromFile);
                     FileChannel inChannel = fips.getChannel()) {
                    outChannel.transferFrom(inChannel, start, fromFile.length());
                    start += fromFile.length();
                }
            }
        }

        long endTime = System.currentTimeMillis();
        log.info("merge success, spend time: {}ms", (endTime - startTime));
    }

    public static String uncompressV2(String zipPath, String targetDir) throws IOException {
        zipPath = zipPath.replaceAll("\\\\", "/");
        targetDir = targetDir.replaceAll("\\\\", "/");
        log.info("start uncompress ......");
        long startTime = System.currentTimeMillis();
        File zipFile = new File(zipPath);
        if (!zipFile.exists()) {
            throw new IOException("需要解压的文件不存在");
        }
        String zipName = zipFile.getName().replaceAll("\\..*", "");
        if (!targetDir.endsWith("/")) {
            targetDir = targetDir + "/" + zipName;
        }
        Path destPath = Paths.get(targetDir);
        Files.createDirectories(destPath);

        int entryCount = 0;
        try (InputStream inputStream = Files.newInputStream(zipFile.toPath());
             ZipInputStream zipInputStream = new ZipInputStream(inputStream, Charset.forName("GBK"))) {
            ZipEntry zipEntry;
            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                // 安全验证：防止 Zip Bomb
                validateEntry(zipEntry.getName(), zipEntry.getSize());
                entryCount++;
                if (entryCount > MAX_ENTRIES) {
                    throw new IOException("压缩包内文件数量超过限制: " + MAX_ENTRIES);
                }

                String entryName = zipEntry.getName().replaceAll(zipName + "/", "");
                Path targetPath = destPath.resolve(entryName).normalize();

                // 安全验证：防止路径遍历攻击
                validatePath(targetPath, destPath);

                String outPath = targetPath.toString().replace("\\\\", "/");
                if (zipEntry.isDirectory()) {
                    Files.createDirectories(targetPath);
                } else {
                    Files.createDirectories(targetPath.getParent());
                    log.info("uncompress file: {}", outPath);
                    writeFileSecure(targetPath, zipInputStream);
                }
            }
        }
        long endTime = System.currentTimeMillis();
        log.info("uncompress success, spend time: {}", (endTime - startTime));
        String zipFileName = zipFile.getName();
        return targetDir + zipFileName.substring(0, zipFileName.lastIndexOf("."));
    }

    private static void writeFileSecure(Path filePath, ZipInputStream zipInputStream) throws IOException {
        try (BufferedOutputStream bos = new BufferedOutputStream(Files.newOutputStream(filePath))) {
            byte[] bytes = new byte[BUFFER_SIZE];
            int len;
            while ((len = zipInputStream.read(bytes)) != -1) {
                bos.write(bytes, 0, len);
            }
        }
    }

    private static final int BUFFER_SIZE = 4096;
    private static final long MAX_SIZE = 1024L * 1024 * 1024; // 1GB
    private static final int MAX_ENTRIES = 100_000;

    /**
     * 通用解压方法（自动检测格式）
     * @param source 压缩文件路径
     * @param targetDir 解压目录
     */
    public static void decompress(String source, String targetDir) throws IOException {
        source = source.replaceAll("\\\\", "/");
        targetDir = targetDir.replaceAll("\\\\", "/");
        log.info("start uncompress ......");
        File sourceFile = new File(source);
        if (!sourceFile.exists()) {
            throw new IOException("需要解压的文件不存在");
        }
        File dirFile = new File(targetDir);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        Path sourcePath = Paths.get(source);
        String fileName = sourcePath.getFileName().toString().toLowerCase();
        if (fileName.endsWith(".zip")) {
            decompressZip(source, targetDir);
        } else if (fileName.endsWith(".tar.gz") || fileName.endsWith(".tgz")) {
            decompressTarGz(source, targetDir);
        } else if (fileName.endsWith(".tar.bz2")) {
            decompressTarBz2(source, targetDir);
        } else if (fileName.endsWith(".gz")) {
            decompressGz(source, targetDir);
        } else {
            throw new IllegalArgumentException("Unsupported compression format: " + fileName);
        }
    }

    /**
     * 解压ZIP文件
     */
    public static void decompressZip(String zipFilePath, String destDir) throws IOException {
        Path destPath = Paths.get(destDir);
        int entryCount = 0;
        File zipFile = new File(zipFilePath);
        try (ZipInputStream zis = new ZipInputStream(Files.newInputStream(zipFile.toPath()), CharsetUtil.getCharset(zipFile))) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                validateEntry(entry.getName(), entry.getSize());
                entryCount++;
                Path targetPath = destPath.resolve(entry.getName()).normalize();
                validatePath(targetPath, destPath);

                if (entry.isDirectory()) {
                    Files.createDirectories(targetPath);
                } else {
                    Files.createDirectories(targetPath.getParent());
                    try (OutputStream fos = Files.newOutputStream(targetPath)) {
                        IOUtils.copy(zis, fos, BUFFER_SIZE);
                    }
                }
                zis.closeEntry();
            }
            if (entryCount > MAX_ENTRIES) {
                throw new IOException("Too many entries in zip file");
            }
        }
    }

    /**
     * 解压TAR.GZ文件
     */
    public static void decompressTarGz(String tarGzFile, String destDir) throws IOException {
        decompressTar(tarGzFile, destDir, CompressorStreamFactory.GZIP);
    }

    /**
     * 解压TAR.BZ2文件
     */
    public static void decompressTarBz2(String tarBz2File, String destDir) throws IOException {
        decompressTar(tarBz2File, destDir, CompressorStreamFactory.BZIP2);
    }

    private static void decompressTar(String tarFile, String destDir, String compressorType)
            throws IOException {
        Path destPath = Paths.get(destDir);
        int entryCount = 0;
        try (InputStream fi = Files.newInputStream(Paths.get(tarFile));
             InputStream ci = new CompressorStreamFactory().createCompressorInputStream(compressorType, fi);
             ArchiveInputStream ais = new ArchiveStreamFactory().createArchiveInputStream(ci)) {
            ArchiveEntry entry;
            while ((entry = ais.getNextEntry()) != null) {
                validateEntry(entry.getName(), entry.getSize());
                entryCount++;

                Path targetPath = destPath.resolve(entry.getName()).normalize();
                validatePath(targetPath, destPath);

                if (entry.isDirectory()) {
                    Files.createDirectories(targetPath);
                } else {
                    Files.createDirectories(targetPath.getParent());
                    try (OutputStream fos = Files.newOutputStream(targetPath)) {
                        IOUtils.copy(ais, fos, BUFFER_SIZE);
                    }
                }
            }

            if (entryCount > MAX_ENTRIES) {
                throw new IOException("Too many entries in archive");
            }
        } catch (ArchiveException | CompressorException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 解压GZ文件
     */
    public static void decompressGz(String gzFile, String destDir) throws IOException {
        Path destPath = Paths.get(destDir);
        String fileName = Paths.get(gzFile).getFileName().toString();
        String outputFile = fileName.replaceAll("\\.gz$", "");
        try (InputStream fi = Files.newInputStream(Paths.get(gzFile));
             InputStream ci = new CompressorStreamFactory().createCompressorInputStream("gz", fi);
             OutputStream fo = Files.newOutputStream(destPath.resolve(outputFile))) {
            IOUtils.copy(ci, fo, BUFFER_SIZE);
        } catch (CompressorException e) {
            throw new IOException("GZ decompression failed", e);
        }
    }

    private static void validateEntry(String entryName, long size) throws IOException {
        if (size > MAX_SIZE) {
            throw new IOException("Entry " + entryName + " exceeds maximum allowed size");
        }
    }

    private static void validatePath(Path targetPath, Path destDir) throws IOException {
        if (!targetPath.startsWith(destDir)) {
            throw new IOException("Entry is outside of the target directory");
        }
    }

    public static void rename(String source, String target) {
        File sourceFile = new File(source);
        if (!sourceFile.exists()) {
            throw new BusinessException("文件不存在: {}", source);
        }
        File targetFile = new File(target);
        if (!sourceFile.renameTo(targetFile)) {
            throw new BusinessException("文件重命名失败: {} -> {}", source, target);
        }
    }

}
