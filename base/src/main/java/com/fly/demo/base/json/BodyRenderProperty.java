package com.fly.demo.base.json;

import lombok.Data;

/**
 * @project: creatar-globe-server
 * @package: com.creatar.globe.server.render.xmodel
 * @author: feilongyan
 * @time: 2024/8/29 0029
 * @description: TODO
 * @version: 1.0
 */
@Data
public class BodyRenderProperty {

    private String ImageFilePath;

    private BorderSymbol borderSymbol;

    private FaceSymbol faceSymbol;

    private boolean isDrawBorder;

    private boolean isDrawFace;

    private boolean isDrawTexture;

    private String rpType;

    private LegendX legend;
}
