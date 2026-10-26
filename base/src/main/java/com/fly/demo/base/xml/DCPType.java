package com.fly.demo.base.xml;

import jakarta.xml.bind.annotation.*;
import lombok.Data;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class DCPType {
    
    @XmlElement(name = "HTTP")
    private HTTP http;
}