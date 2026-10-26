package com.fly.demo.base.xml;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Data;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class Request {
    
    @XmlElement(name = "GetCapabilities")
    private Operation getCapabilities;
    
    @XmlElement(name = "GetMap")
    private Operation getMap;
    
    @XmlElement(name = "GetFeatureInfo")
    private Operation getFeatureInfo;
}