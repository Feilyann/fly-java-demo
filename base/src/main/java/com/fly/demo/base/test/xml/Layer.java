package com.fly.demo.base.test.xml;

import jakarta.xml.bind.annotation.*;
import lombok.Data;

import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class Layer {
    
    @XmlElement(name = "Title")
    private String title;
    
    @XmlElement(name = "Abstract")
    private String abstractText;
    
    @XmlElement(name = "CRS")
    private List<String> crsList;
    
    @XmlElement(name = "EX_GeographicBoundingBox")
    private EXGeographicBoundingBox exGeographicBoundingBox;
    
    @XmlElement(name = "BoundingBox")
    private List<BoundingBox> boundingBoxes;
    
    @XmlElement(name = "Layer")
    private List<SubLayer> subLayers;
    
}