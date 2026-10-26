package com.fly.demo.base.xml;

import jakarta.xml.bind.annotation.*;
import lombok.Data;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class EXGeographicBoundingBox {
    
    @XmlElement(name = "westBoundLongitude")
    private double westBoundLongitude;
    
    @XmlElement(name = "eastBoundLongitude")
    private double eastBoundLongitude;
    
    @XmlElement(name = "southBoundLatitude")
    private double southBoundLatitude;
    
    @XmlElement(name = "northBoundLatitude")
    private double northBoundLatitude;
}