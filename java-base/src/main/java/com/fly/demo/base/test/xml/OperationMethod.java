package com.fly.demo.base.test.xml;

import jakarta.xml.bind.annotation.*;
import lombok.Data;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class OperationMethod {
    
    @XmlElement(name = "OnlineResource")
    private OnlineResource onlineResource;
}