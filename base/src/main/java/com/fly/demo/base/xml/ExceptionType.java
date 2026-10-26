package com.fly.demo.base.xml;

import jakarta.xml.bind.annotation.*;
import lombok.Data;

import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class ExceptionType {
    
    @XmlElement(name = "Format")
    private List<String> formats;
    
}