package com.fly.demo.util;

import cn.hutool.core.util.StrUtil;
import com.creatar.common.core.exception.BusinessException;
import com.creatar.common.core.util.CharsetUtil;
import com.github.junrar.Archive;
import com.github.junrar.exception.RarException;
import com.github.junrar.rarfile.FileHeader;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZFile;
import org.apache.commons.compress.compressors.CompressorException;
import org.apache.commons.compress.compressors.CompressorStreamFactory;
import org.apache.commons.compress.utils.IOUtils;

import java.io.*;
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
        String charset = com.creatar.common.core.util.CharsetUtil.getEncoding(file);
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

    // 64KB 缓冲区，减少大文件解压时的 I/O 系统调用次数
    private static final int BUFFER_SIZE = 65536;
    private static final long MAX_SIZE = 1024L * 1024 * 1024; // 1GB
    private static final int DEFAULT_MAX_ENTRIES = 100_000;
    private static final int MAX_ENTRIES = initMaxEntries();

    private static int initMaxEntries() {
        String property = System.getProperty("creatar.decompress.maxEntries");
        if (property != null && !property.isEmpty()) {
            try {
                int value = Integer.parseInt(property);
                if (value > 0) {
                    return value;
                }
                log.warn("creatar.decompress.maxEntries 值无效: {}, 使用默认值 {}", property, DEFAULT_MAX_ENTRIES);
            } catch (NumberFormatException e) {
                log.warn("creatar.decompress.maxEntries 格式错误: {}, 使用默认值 {}", property, DEFAULT_MAX_ENTRIES);
            }
        }
        return DEFAULT_MAX_ENTRIES;
    }

    /**
     * 通用解压方法（自动检测格式）
     * <p>
     * 支持格式：
     * <ul>
     *   <li>多文件压缩包：zip、jar、war、ear、7z、rar、tar、tar.gz/.tgz、tar.bz2/.tbz2、tar.xz/.txz、tar.lz4、tar.zst/.tar.zstd</li>
     *   <li>单文件压缩：gz、bz2、xz、lz4、lzma、snappy、zst/zstd、Z</li>
     * </ul>
     * </p>
     *
     * @param source   压缩文件路径
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

        // 复合压缩归档格式（先检查，避免被简单扩展名误匹配）
        if (fileName.endsWith(".tar.gz") || fileName.endsWith(".tgz")) {
            decompressTarGz(source, targetDir);
        } else if (fileName.endsWith(".tar.bz2") || fileName.endsWith(".tbz2")) {
            decompressTarBz2(source, targetDir);
        } else if (fileName.endsWith(".tar.xz") || fileName.endsWith(".txz")) {
            decompressTarXz(source, targetDir);
        } else if (fileName.endsWith(".tar.lz4")) {
            decompressTarLz4(source, targetDir);
        } else if (fileName.endsWith(".tar.zst") || fileName.endsWith(".tar.zstd") || fileName.endsWith(".tzst")) {
            decompressTarZstd(source, targetDir);
        } else if (fileName.endsWith(".tar")) {
            decompressTarPlain(source, targetDir);
        } else if (fileName.endsWith(".zip") || fileName.endsWith(".jar")
                || fileName.endsWith(".war") || fileName.endsWith(".ear")) {
            decompressZip(source, targetDir);
        } else if (fileName.endsWith(".7z")) {
            decompress7z(source, targetDir);
        } else if (fileName.endsWith(".rar")) {
            decompressRar(source, targetDir);
        } else if (isCompressedSingleFile(fileName)) {
            decompressSingleFile(source, targetDir);
        } else {
            throw new IllegalArgumentException("Unsupported compression format: " + fileName);
        }
    }

    /**
     * 判断文件扩展名是否属于支持的压缩格式
     * <p>
     * 与 {@link #decompress(String, String)} 支持的格式保持一致
     * </p>
     *
     * @param fileName 文件名（含扩展名）
     * @return true 如果该格式支持解压
     */
    public static boolean isSupportedFormat(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return false;
        }
        String lower = fileName.toLowerCase();
        // 复合压缩归档格式
        if (lower.endsWith(".tar.gz") || lower.endsWith(".tgz")
                || lower.endsWith(".tar.bz2") || lower.endsWith(".tbz2")
                || lower.endsWith(".tar.xz") || lower.endsWith(".txz")
                || lower.endsWith(".tar.lz4")
                || lower.endsWith(".tar.zst") || lower.endsWith(".tar.zstd") || lower.endsWith(".tzst")
                || lower.endsWith(".tar")) {
            return true;
        }
        // 归档格式
        if (lower.endsWith(".zip") || lower.endsWith(".jar")
                || lower.endsWith(".war") || lower.endsWith(".ear")
                || lower.endsWith(".7z")
                || lower.endsWith(".rar")) {
            return true;
        }
        // 单文件压缩格式
        return isCompressedSingleFile(lower);
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
             ArchiveInputStream ais = new ArchiveStreamFactory().createArchiveInputStream("tar", ci)) {
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
        decompressSingleFile(gzFile, destDir);
    }

    /**
     * 解压 TAR.XZ 文件
     */
    public static void decompressTarXz(String tarXzFile, String destDir) throws IOException {
        decompressTar(tarXzFile, destDir, CompressorStreamFactory.XZ);
    }

    /**
     * 解压 TAR.LZ4 文件
     */
    public static void decompressTarLz4(String tarLz4File, String destDir) throws IOException {
        decompressTar(tarLz4File, destDir, CompressorStreamFactory.LZ4_FRAMED);
    }

    /**
     * 解压 TAR.ZST 文件
     */
    public static void decompressTarZstd(String tarZstFile, String destDir) throws IOException {
        decompressTar(tarZstFile, destDir, CompressorStreamFactory.ZSTANDARD);
    }

    /**
     * 解压未压缩的 TAR 文件
     */
    public static void decompressTarPlain(String tarFile, String destDir) throws IOException {
        Path destPath = Paths.get(destDir);
        int entryCount = 0;
        try (InputStream fi = Files.newInputStream(Paths.get(tarFile));
             ArchiveInputStream ais = new ArchiveStreamFactory()
                     .createArchiveInputStream("tar", fi))
        {
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
        } catch (ArchiveException e) {
            throw new IOException("TAR decompression failed", e);
        }
    }

    /**
     * 解压 7Z 文件
     */
    public static void decompress7z(String sevenZFile, String destDir) throws IOException {
        Path destPath = Paths.get(destDir);
        int entryCount = 0;
        try (SevenZFile sevenZ = new SevenZFile(new File(sevenZFile))) {
            byte[] buf = new byte[BUFFER_SIZE];
            SevenZArchiveEntry entry;
            while ((entry = sevenZ.getNextEntry()) != null) {
                validateEntry(entry.getName(), entry.getSize());
                entryCount++;
                Path targetPath = destPath.resolve(entry.getName()).normalize();
                validatePath(targetPath, destPath);
                if (entry.isDirectory()) {
                    Files.createDirectories(targetPath);
                } else {
                    Files.createDirectories(targetPath.getParent());
                    try (OutputStream fos = Files.newOutputStream(targetPath)) {
                        int len;
                        while ((len = sevenZ.read(buf)) != -1) {
                            fos.write(buf, 0, len);
                        }
                    }
                }
            }
            if (entryCount > MAX_ENTRIES) {
                throw new IOException("Too many entries in 7z archive");
            }
        }
    }

    /**
     * 解压 RAR 文件
     */
    public static void decompressRar(String rarFile, String destDir) throws IOException {
        Path destPath = Paths.get(destDir);
        int entryCount = 0;
        try (Archive archive = new Archive(new File(rarFile))) {
            for (FileHeader fh : archive.getFileHeaders()) {
                String entryName = fh.getFileNameString();
                entryCount++;
                if (entryCount > MAX_ENTRIES) {
                    throw new IOException("Too many entries in rar archive");
                }
                Path targetPath = destPath.resolve(entryName).normalize();
                validatePath(targetPath, destPath);
                if (fh.isDirectory()) {
                    Files.createDirectories(targetPath);
                } else {
                    Files.createDirectories(targetPath.getParent());
                    try (OutputStream fos = Files.newOutputStream(targetPath)) {
                        archive.extractFile(fh, fos);
                    }
                }
            }
        } catch (RarException e) {
            throw new IOException("RAR decompression failed: " + rarFile, e);
        }
    }

    /**
     * 解压单文件压缩格式（自动检测：gz、bz2、xz、lz4、lzma、snappy、zstd、Z 等）
     */
    public static void decompressSingleFile(String compressedFile, String destDir) throws IOException {
        Path destPath = Paths.get(destDir);
        String fileName = Paths.get(compressedFile).getFileName().toString();
        String outputName = stripCompressionExtension(fileName);
        Path outputPath = destPath.resolve(outputName);
        try (InputStream fi = new BufferedInputStream(Files.newInputStream(Paths.get(compressedFile)));
             InputStream ci = new CompressorStreamFactory().createCompressorInputStream(fi);
             OutputStream fo = Files.newOutputStream(outputPath)) {
            IOUtils.copy(ci, fo, BUFFER_SIZE);
        } catch (CompressorException e) {
            throw new IOException("Decompression failed, unsupported or corrupted file: " + compressedFile, e);
        }
    }

    private static final String[] COMPRESSED_EXTENSIONS = {
            ".gz", ".bz2", ".xz", ".lz4", ".lzma", ".sz", ".snappy", ".zst", ".zstd", ".Z"
    };

    private static boolean isCompressedSingleFile(String fileName) {
        for (String ext : COMPRESSED_EXTENSIONS) {
            if (fileName.endsWith(ext)) {
                return true;
            }
        }
        return false;
    }

    private static String stripCompressionExtension(String fileName) {
        String lower = fileName.toLowerCase();
        for (String ext : COMPRESSED_EXTENSIONS) {
            if (lower.endsWith(ext)) {
                return fileName.substring(0, fileName.length() - ext.length());
            }
        }
        return fileName + ".decompressed";
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
