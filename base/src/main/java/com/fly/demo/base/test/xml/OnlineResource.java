package com.fly.demo.base.test.xml;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import lombok.Data;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class OnlineResource {

    @XmlAttribute(name = "type", namespace = "http://www.w3.org/1999/xlink")
    private String type;

    @XmlAttribute(name = "href", namespace = "http://www.w3.org/1999/xlink")
    private String href;
}