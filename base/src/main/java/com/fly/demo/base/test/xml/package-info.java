@XmlSchema(
        xmlns = {
                // 不指定前缀，让 JAXB 使用默认命名空间
                @XmlNs(namespaceURI = "http://www.opengis.net/wms", prefix = ""),
                @XmlNs(prefix = "xlink", namespaceURI = "http://www.w3.org/1999/xlink"),
                @XmlNs(prefix = "xsi", namespaceURI = "http://www.w3.org/2001/XMLSchema-instance")
        },
        elementFormDefault = XmlNsForm.QUALIFIED,
        namespace = "http://www.opengis.net/wms"  // 设置默认命名空间
)
package com.fly.demo.base.test.xml;

import jakarta.xml.bind.annotation.XmlNs;
import jakarta.xml.bind.annotation.XmlNsForm;
import jakarta.xml.bind.annotation.XmlSchema;