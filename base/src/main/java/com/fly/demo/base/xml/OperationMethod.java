package com.fly.demo.base.xml;

import jakarta.xml.bind.annotation.*;
import lombok.Data;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class OperationMethod {
    
    @XmlElement(name = "OnlineResource")
    private OnlineResource onlineResource;
}