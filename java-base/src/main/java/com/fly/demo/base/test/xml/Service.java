package com.fly.demo.base.test.xml;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Data;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class Service {
    
    @XmlElement(name = "Name")
    private String name;
    
    @XmlElement(name = "Title")
    private String title;
    
    @XmlElement(name = "Abstract")
    private String abstractText;
    
    @XmlElement(name = "KeywordList")
    private KeywordList keywordList;
    
    @XmlElement(name = "OnlineResource")
    private OnlineResource onlineResource;
    
    @XmlElement(name = "ContactInformation")
    private ContactInformation contactInformation;
    
    @XmlElement(name = "Fees")
    private String fees;
    
    @XmlElement(name = "AccessConstraints")
    private String accessConstraints;
    
}