package com.fly.demo.base.test.xml;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Data;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class HTTP {
    
    @XmlElement(name = "Get")
    private OperationMethod get;
    
    @XmlElement(name = "Post")
    private OperationMethod post;
}