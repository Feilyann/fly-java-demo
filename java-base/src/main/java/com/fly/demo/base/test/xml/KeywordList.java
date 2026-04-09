package com.fly.demo.base.test.xml;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Data;

import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class KeywordList {

    @XmlElement(name = "Keyword")
    private List<String> keywords;
}
