package com.fly.demo.base.xml;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Data;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class ContactInformation {
    
    @XmlElement(name = "ContactPersonPrimary")
    private ContactPersonPrimary contactPersonPrimary;
    
    @XmlElement(name = "ContactPosition")
    private String contactPosition;
    
    @XmlElement(name = "ContactAddress")
    private ContactAddress contactAddress;
    
    @XmlElement(name = "ContactVoiceTelephone")
    private String contactVoiceTelephone;
    
    @XmlElement(name = "ContactFacsimileTelephone")
    private String contactFacsimileTelephone;
    
    @XmlElement(name = "ContactElectronicMailAddress")
    private String contactElectronicMailAddress;
}