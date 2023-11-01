package org.wheel.sqlparser.sdk.cube;


/**
 * @author cheng
 * @version 1.0
 * @description:
 * @date 2023/10/31 23:02
 */
public class CubeParserFactory {

    CubeRequest request;

    private CubeParserFactory() {
    }

    private CubeParserFactory(CubeRequest request) {
        this.request = request;
    }

    public static CubeParserFactory getInstance(CubeRequest request) {
        return new CubeParserFactory(request);
    }


}
