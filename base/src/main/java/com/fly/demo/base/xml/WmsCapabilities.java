package com.fly.demo.base.xml;

import jakarta.xml.bind.annotation.*;
import lombok.Data;

@XmlRootElement(name = "WMS_Capabilities", namespace = "http://www.opengis.net/wms")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class WmsCapabilities {

//    @XmlAttribute
//    private final String xmlns = "http://www.opengis.net/wms";
//
//    @XmlAttribute(name = "xmln:xlink")
//    private final String xmlns_xlink = "http://www.w3.org/1999/xlink";
//
//    @XmlAttribute(name = "xmlns:xsi")
//    private final String xmlns_xsi = "http://www.w3.org/2001/XMLSchema-instance";

    @XmlAttribute
    private final String version = "1.3.0";

    @XmlAttribute(name = "xsi:schemaLocation")
    private final String xsi_schemaLocation = "http://www.opengis.net/wms http://schemas.opengis.net/wms/1.3.0/capabilities_1_3_0.xsd";

    @XmlAttribute(name = "updateSequence")
    private String updateSequence;
    
    @XmlElement(name = "Service")
    private Service service;
    
    @XmlElement(name = "Capability")
    private Capability capability;
}