package com.fly.demo.base.xml;

import jakarta.xml.bind.annotation.*;
import lombok.Data;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class Style {
    
    @XmlElement(name = "Name")
    private String name;
    
    @XmlElement(name = "Title")
    private String title;
    
    @XmlElement(name = "Abstract")
    private String abstractText;
    
    @XmlElement(name = "LegendURL")
    private LegendURL legendURL;
}