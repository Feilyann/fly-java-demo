package com.fly.demo.base.json;

import lombok.Data;

import java.util.Map;

/**
 * @project: creatar-globe-server
 * @package: com.creatar.globe.server.render
 * @author: feilongyan
 * @time: 2024/8/29 0029
 * @description: TODO
 * @version: 1.0
 */
@Data
public class LegendX {

    private String legendType;

    private boolean useLegend;

    private String valueField;

    private Map<String, Object> uniqueValueLegend;

}
