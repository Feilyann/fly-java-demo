package com.fly.demo.base.xml;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import java.io.StringReader;
import java.io.StringWriter;

public class JAXBUtils {

    /**
     * 将 Java 对象转换为 XML 字符串
     * @param obj Java 对象
     * @return XML 字符串
     */
    public static String toXml(Object obj) throws JAXBException {
        return toXml(obj, true);
    }

    public static String toXml(Object obj, boolean formatted) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(obj.getClass());
        Marshaller marshaller = context.createMarshaller();
        
        // 设置格式化输出
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, formatted);
        
        // 不生成 XML 声明（可选）
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
        
        // 设置编码
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        
        StringWriter writer = new StringWriter();
        marshaller.marshal(obj, writer);
        return writer.toString();
    }

    /**
     * 将 XML 字符串转换为 Java 对象
     * @param xml XML 字符串
     * @param clazz 目标类
     * @return 转换后的对象
     */
    public static <T> T fromXml(String xml, Class<T> clazz) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(clazz);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        StringReader reader = new StringReader(xml);
        return clazz.cast(unmarshaller.unmarshal(reader));
    }
}