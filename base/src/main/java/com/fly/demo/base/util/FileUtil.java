package com.fly.demo.base.util;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class FileUtil {

    public static void writeBytes(String filePath, byte[] bytes) {
        write(filePath, new ByteArrayInputStream(bytes));
    }

    public static void writeJson(String filePath, String json) {
        write(filePath, new ByteArrayInputStream(json.getBytes()));
    }

    public static void write(String path, InputStream inputStream) {
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        try {
            File localFile = new File(path);
            if (!localFile.getParentFile().exists()){
                localFile.getParentFile().mkdirs();
            }
            if (!localFile.exists()) {
                localFile.createNewFile();
            }
            fos = new FileOutputStream(localFile);
            bos = new BufferedOutputStream(fos);

            byte[] bytes = new byte[1024 * 1024];
            int i;
            while ((i = inputStream.read(bytes)) != -1){
                bos.write(bytes, 0, i);
                bos.flush();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally{
            try {
                if (bos != null){
                    bos.close();
                }
                if (fos != null){
                    fos.close();
                }
                if (inputStream != null){
                    inputStream.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static String readJson(String filePath) {
        byte[] bytes = readBytes(filePath);
        if (bytes == null) {
            return null;
        }
        return new String(bytes);
    }

    public static byte[] readBytes(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            return null;
        }
        FileInputStream fis = null;
        try(ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            fis = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }
            return baos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void deleteFile(String filePath) {
        try {
            Files.walkFileTree(Paths.get(filePath),
                    new SimpleFileVisitor<Path>() {
                        @Override
                        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                            System.out.println(file);
                            Files.delete(file);
                            return FileVisitResult.CONTINUE;
                        }
                        @Override
                        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                            System.out.println(dir);
                            Files.delete(dir);
                            return FileVisitResult.CONTINUE;
                        }
                    });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
