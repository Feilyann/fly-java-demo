package com.fly.demo.base.test.xml;

import jakarta.xml.bind.annotation.*;
import lombok.Data;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class BoundingBox {
    
    @XmlAttribute(name = "CRS")
    private String crs;
    
    @XmlAttribute(name = "minx")
    private double minx;
    
    @XmlAttribute(name = "miny")
    private double miny;
    
    @XmlAttribute(name = "maxx")
    private double maxx;
    
    @XmlAttribute(name = "maxy")
    private double maxy;
}