package com.fly.demo.base.xml;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Data;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class ContactAddress {
    
    @XmlElement(name = "AddressType")
    private String addressType;
    
    @XmlElement(name = "Address")
    private String address;
    
    @XmlElement(name = "City")
    private String city;
    
    @XmlElement(name = "StateOrProvince")
    private String stateOrProvince;
    
    @XmlElement(name = "PostCode")
    private String postCode;
    
    @XmlElement(name = "Country")
    private String country;
}