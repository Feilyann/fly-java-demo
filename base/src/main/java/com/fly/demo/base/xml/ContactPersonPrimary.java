package com.fly.demo.base.xml;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Data;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class ContactPersonPrimary {
    
    @XmlElement(name = "ContactPerson")
    private String contactPerson;
    
    @XmlElement(name = "ContactOrganization")
    private String contactOrganization;

}