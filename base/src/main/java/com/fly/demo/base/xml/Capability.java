package com.fly.demo.base.xml;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Data;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class Capability {
    
    @XmlElement(name = "Request")
    private Request request;
    
    @XmlElement(name = "Exception")
    private ExceptionType exception;
    
    @XmlElement(name = "Layer")
    private Layer layer;
    
}