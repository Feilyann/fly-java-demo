package com.fly.demo.base.test.xml;

import jakarta.xml.bind.annotation.*;
import lombok.Data;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class LegendURL {
    
    @XmlAttribute(name = "width")
    private int width;
    
    @XmlAttribute(name = "height")
    private int height;
    
    @XmlElement(name = "Format")
    private String format;
    
    @XmlElement(name = "OnlineResource")
    private OnlineResource onlineResource;
}