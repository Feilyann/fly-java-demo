package com.fly.demo.util;

import com.creatar.common.core.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

/**
 * @project: creatar-common
 * @package: com.creatar.common.core.util
 * @author: feilongyan
 * @time: 2024 2024/8/9 0009 14:04
 * @description: 字符编码工具类
 * @version: 1.0
 */
@Slf4j
public class CharsetUtil {

    public static String getEncoding(File file) {
        String charset = "GBK";
        byte[] first3Bytes = new byte[3];
        try(InputStream is = Files.newInputStream(file.toPath()); InputStream istmp = Files.newInputStream(file.toPath());) {
            boolean checked = false;
            int read = is.read(first3Bytes, 0, 3);
            if (read == -1)
                return charset;
            if (first3Bytes[0] == (byte) 0xFF && first3Bytes[1] == (byte) 0xFE) {
                charset = "UTF-16LE";
                checked = true;
            } else if (first3Bytes[0] == (byte) 0xFE
                    && first3Bytes[1] == (byte) 0xFF) {
                charset = "UTF-16BE";
                checked = true;
            } else if (first3Bytes[0] == (byte) 0xEF
                    && first3Bytes[1] == (byte) 0xBB
                    && first3Bytes[2] == (byte) 0xBF) {
                charset = "UTF-8";
                checked = true;
            }else if (first3Bytes[0] == (byte) 0xA
                    && first3Bytes[1] == (byte) 0x5B
                    && first3Bytes[2] == (byte) 0x30) {
                charset = "UTF-8";
                checked = true;
            }else if (first3Bytes[0] == (byte) 0xD
                    && first3Bytes[1] == (byte) 0xA
                    && first3Bytes[2] == (byte) 0x5B) {
                charset = "GBK";
                checked = true;
            }else if (first3Bytes[0] == (byte) 0x5B
                    && first3Bytes[1] == (byte) 0x54
                    && first3Bytes[2] == (byte) 0x49) {
                charset = "windows-1251";
                checked = true;
            }
            if (!checked) {
                while ((read = istmp.read()) != -1) {
                    if (read >= 0xF0)
                        break;
                    if (0x80 <= read && read <= 0xBF)
                        break;
                    if (0xC0 <= read && read <= 0xDF) {
                        read = istmp.read();
                        if (0x80 > read || read > 0xBF) {
                            break;
                        }
                    } else if (0xE0 <= read) {
                        read = istmp.read();
                        if (0x80 <= read && read <= 0xBF) {
                            read = istmp.read();
                            if (0x80 <= read && read <= 0xBF) {
                                charset = "UTF-8";
                                break;
                            } else
                                break;
                        } else
                            break;
                    }
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
        return charset;
    }

    public static Charset getCharset(File file) {
        Charset charset = Charset.forName("GBK");
        byte[] first3Bytes = new byte[3];
        try(InputStream is = Files.newInputStream(file.toPath()); InputStream istmp = Files.newInputStream(file.toPath());) {
            boolean checked = false;
            int read = is.read(first3Bytes, 0, 3);
            if (read == -1)
                return charset;
            if (first3Bytes[0] == (byte) 0xFF && first3Bytes[1] == (byte) 0xFE) {
                charset = StandardCharsets.UTF_16LE;
                checked = true;
            } else if (first3Bytes[0] == (byte) 0xFE
                    && first3Bytes[1] == (byte) 0xFF) {
                charset = StandardCharsets.UTF_16BE;
                checked = true;
            } else if (first3Bytes[0] == (byte) 0xEF
                    && first3Bytes[1] == (byte) 0xBB
                    && first3Bytes[2] == (byte) 0xBF) {
                charset = StandardCharsets.UTF_8;
                checked = true;
            }else if (first3Bytes[0] == (byte) 0xA
                    && first3Bytes[1] == (byte) 0x5B
                    && first3Bytes[2] == (byte) 0x30) {
                charset = StandardCharsets.UTF_8;
                checked = true;
            }else if (first3Bytes[0] == (byte) 0xD
                    && first3Bytes[1] == (byte) 0xA
                    && first3Bytes[2] == (byte) 0x5B) {
                checked = true;
            }else if (first3Bytes[0] == (byte) 0x5B
                    && first3Bytes[1] == (byte) 0x54
                    && first3Bytes[2] == (byte) 0x49) {
                charset = Charset.forName("windows-1251"); //MS1251
                checked = true;
            }
            if (!checked) {
                while ((read = istmp.read()) != -1) {
                    if (read >= 0xF0)
                        break;
                    if (0x80 <= read && read <= 0xBF)
                        break;
                    if (0xC0 <= read && read <= 0xDF) {
                        read = istmp.read();
                        if (0x80 > read || read > 0xBF) {
                            break;
                        }
                    } else if (0xE0 <= read) {
                        read = istmp.read();
                        if (0x80 <= read && read <= 0xBF) {
                            read = istmp.read();
                            if (0x80 <= read && read <= 0xBF) {
                                charset = StandardCharsets.UTF_8;
                                break;
                            } else
                                break;
                        } else
                            break;
                    }
                }
            }
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), e);
        }
        return charset;
    }
}
