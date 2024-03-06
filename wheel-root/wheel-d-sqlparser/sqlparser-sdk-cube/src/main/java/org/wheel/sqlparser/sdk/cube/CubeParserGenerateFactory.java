package org.wheel.sqlparser.sdk.cube;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CubeParserGenerateFactory {
//
//    CubeRequest cubeRequest;
//    List<CubeParserResultFieldBO> resultFields;
//    Map<String, CubeParserDomainBO> domainMap;
//
//
//    private CubeParserGenerateFactory(CubeRequest cubeRequest) {
//
//        this.cubeRequest = cubeRequest;
//    }
//
//    public static CubeParserGenerateFactory getInstance(CubeRequest cubeRequest) {
//        return new CubeParserGenerateFactory(cubeRequest);
//    }
//
//
//    public void generate() {
//        this.cubeRequest.getFields().forEach(field -> {
//            if (StrUtil.isBlank(field.getDataSetCode())) {
//                return;
//            }
//            //当前时间范围
//            StatisticalMethodEnum statisticalMethodEnum = StatisticalMethodEnum.getEnum(field.getStatisticalMethod());
//            if (statisticalMethodEnum == StatisticalMethodEnum.TQ) {
//                resultFields.add(putTQ(field));
//            } else if (statisticalMethodEnum == StatisticalMethodEnum.TB) {
//                resultFields.add(putTB(field));
//            } else if (statisticalMethodEnum == StatisticalMethodEnum.SQ) {
//                resultFields.add(putSQ(field));
//            } else if (statisticalMethodEnum == StatisticalMethodEnum.HB) {
//                resultFields.add(putHB(field));
//            } else if (statisticalMethodEnum == StatisticalMethodEnum.MEDIAN) {
//                resultFields.add(putMedian(field));
//            } else {
//                resultFields.add(putBase(field));
//            }
//        });
//    }
//
//
//    public CubeParserBO getCubeParserBO() {
//        return new CubeParserBO()
//                .setResultFields(this.resultFields)
//                .setComputeDomains(new ArrayList<>(domainMap.values()));
//    }
//
//
//    public static String getBaseDomainAlise(String dataSetCode) {
//        return "base_" + dataSetCode;
//    }
//
//    public static String getTqDomainAlise(String dataSetCode) {
//        return "tq_" + dataSetCode;
//    }
//
//    public static String getSqDomainAlise(String dataSetCode) {
//        return "sq_" + dataSetCode;
//    }
//
//    public CubeParserDomainBO getCubeParserDomain(String alise) {
//        return getCubeParserDomain(alise, null);
//    }
//
//    public CubeParserDomainBO getCubeParserDomain(String alise, StatisticalMethodEnum statisticalMethodEnum) {
//        CubeParserDomainBO cubeParserDomain = domainMap.get(alise);
//        if (cubeParserDomain == null) {
//            DateTypeEnum dateTypeEnum = DateTypeEnum.getEnum(this.cubeRequest.getDateType());
//            if (dateTypeEnum == DateTypeEnum.EMPTY) {
//                throw new SqlParserCubeException(SqlParserCubeExceptionEnum.PARAM_ERROR, "dateType is null");
//            }
//            CubeDateRangeParserGenerateFactory instance = CubeDateRangeParserGenerateFactory.getInstance(dateTypeEnum, this.cubeRequest.getStartTime(), this.cubeRequest.getEndTime());
//            if (statisticalMethodEnum == StatisticalMethodEnum.TQ) {
//                instance.parserTq();
//            } else if (statisticalMethodEnum == StatisticalMethodEnum.SQ) {
//                instance.parserSq();
//            }
//            cubeParserDomain = new CubeParserDomainBO()
//                    .setAlise(alise)
//                    .setDataType(this.cubeRequest.getDateType())
//                    .setDateFieldKey(this.cubeRequest.getDateFieldKey())
//                    .setStartTime(instance.getStartTime())
//                    .setEndTime(instance.getEndTime());
//            domainMap.put(alise, cubeParserDomain);
//        }
//        return cubeParserDomain;
//    }
//
//    public CubeParserResultFieldBO putBase(CubeFieldRequest field) {
//        String alise = getBaseDomainAlise(field.getDataSetCode());
//        CubeParserDomainBO cubeParserDomain = getCubeParserDomain(alise);
//        cubeParserDomain.getFields().add(new CubeParserDomainFieldBO()
//                .setAlise(field.getFieldKey())
//                .setFieldKey(field.getFieldKey())
//                .setDataSetCode(field.getDataSetCode())
//                .setStatisticalMethod(field.getStatisticalMethod())
//        );
//        return new CubeParserResultFieldBO()
//                .setAlise("base_" + field.getFieldKey())
//                .setSqlSnippet(alise + "." + field.getFieldKey());
//    }
//
//    public CubeParserResultFieldBO putMedian(CubeFieldRequest field) {
//        String alise = getBaseDomainAlise(field.getDataSetCode());
//        CubeParserDomainBO cubeParserDomain = getCubeParserDomain(alise);
//        cubeParserDomain.getFields().add(new CubeParserDomainFieldBO()
//                .setAlise(field.getFieldKey())
//                .setFieldKey(field.getFieldKey())
//                .setDataSetCode(field.getDataSetCode())
//                .setStatisticalMethod(StatisticalMethodEnum.CUSTOM.getCode())
//        );
//        return new CubeParserResultFieldBO()
//                .setAlise("base_" + field.getFieldKey())
//                .setSqlSnippet(alise + "." + field.getFieldKey());
//    }
//
//    public CubeParserResultFieldBO putTQ(CubeFieldRequest field) {
//        String alise = getTqDomainAlise(field.getDataSetCode());
//        CubeParserDomainBO cubeParserDomain = getCubeParserDomain(alise, StatisticalMethodEnum.TQ);
//        cubeParserDomain.getFields().add(new CubeParserDomainFieldBO()
//                .setAlise(field.getFieldKey())
//                .setFieldKey(field.getFieldKey())
//                .setDataSetCode(field.getDataSetCode())
//                .setStatisticalMethod(StatisticalMethodEnum.CUSTOM.getCode())
//        );
//        return new CubeParserResultFieldBO()
//                .setAlise("tq_" + field.getFieldKey())
//                .setSqlSnippet(alise + "." + field.getFieldKey());
//    }
//
//
//    public CubeParserResultFieldBO putSQ(CubeFieldRequest field) {
//        String alise = getTqDomainAlise(field.getDataSetCode());
//        CubeParserDomainBO cubeParserDomain = getCubeParserDomain(alise, StatisticalMethodEnum.SQ);
//        cubeParserDomain.getFields().add(new CubeParserDomainFieldBO()
//                .setAlise(field.getFieldKey())
//                .setFieldKey(field.getFieldKey())
//                .setDataSetCode(field.getDataSetCode())
//                .setStatisticalMethod(StatisticalMethodEnum.CUSTOM.getCode())
//        );
//        return new CubeParserResultFieldBO()
//                .setAlise("sq_" + field.getFieldKey())
//                .setSqlSnippet(alise + "." + field.getFieldKey());
//    }
//
//
//    public CubeParserResultFieldBO putTB(CubeFieldRequest field) {
//        String tqDomainAlise = getTqDomainAlise(field.getDataSetCode());
//        CubeParserDomainBO cubeParserDomain = getCubeParserDomain(tqDomainAlise, StatisticalMethodEnum.TQ);
//        CubeParserDomainFieldBO tqDomainField = cubeParserDomain.getFields().stream().filter(toField -> StrUtil.equals(toField.getAlise(), field.getFieldKey())).findAny().orElse(null);
//        if (tqDomainField == null) {
//            putTQ(field);
//        }
//        String baseDomainAlise = getBaseDomainAlise(field.getDataSetCode());
//        String tqDomainFieldAlise = tqDomainAlise + "." + field.getFieldKey();
//        String baseDomainFieldAlise = baseDomainAlise + "." + field.getFieldKey();
//        return new CubeParserResultFieldBO()
//                .setAlise("tb_" + field.getFieldKey())
//                .setSqlSnippet(StrUtil.format("({}-{})/{}", baseDomainFieldAlise, tqDomainFieldAlise, tqDomainFieldAlise));
//    }
//
//    public CubeParserResultFieldBO putHB(CubeFieldRequest field) {
//        String sqDomainAlise = getSqDomainAlise(field.getDataSetCode());
//        CubeParserDomainBO cubeParserDomain = getCubeParserDomain(sqDomainAlise, StatisticalMethodEnum.SQ);
//        CubeParserDomainFieldBO tqDomainField = cubeParserDomain.getFields().stream().filter(toField -> StrUtil.equals(toField.getAlise(), field.getFieldKey())).findAny().orElse(null);
//        if (tqDomainField == null) {
//            putTQ(field);
//        }
//        String baseDomainAlise = getBaseDomainAlise(field.getDataSetCode());
//        String hqDomainFieldAlise = sqDomainAlise + "." + field.getFieldKey();
//        String baseDomainFieldAlise = baseDomainAlise + "." + field.getFieldKey();
//        return new CubeParserResultFieldBO()
//                .setAlise("hb_" + field.getFieldKey())
//                .setSqlSnippet(StrUtil.format("({}-{})/{}", baseDomainFieldAlise, hqDomainFieldAlise, hqDomainFieldAlise));
//    }

}
