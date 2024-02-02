WITH InfoNow AS (SELECT COALESCE(SUM(CASE WHEN IndexCode = '2011_MQM_HII_E001' THEN IndexValue ELSE 0 END),
                                 0) AS R_2011_MQM_HII_E001
                 FROM StatisticsAdditional
                 WHERE DateMonthKey BETWEEN 202401 AND 202401
                   AND pt_dt >= '202401'
                   AND pt_dt < '202402'),
     InfoYear AS (SELECT COALESCE(SUM(CASE WHEN IndexCode = '2011_MQM_HII_E001' THEN IndexValue ELSE 0 END),
                                  0) AS R_2011_MQM_HII_E001
                  FROM StatisticsAdditional
                  WHERE DateMonthKey BETWEEN 202301 AND 202301
                    AND pt_dt >= '202301'
                    AND pt_dt < '202302'),
     InfoLast AS (SELECT COALESCE(SUM(CASE WHEN IndexCode = '2011_MQM_HII_E001' THEN IndexValue ELSE 0 END),
                                  0) AS R_2011_MQM_HII_E001
                  FROM StatisticsAdditional
                  WHERE DateMonthKey BETWEEN 202312 AND 202312
                    AND pt_dt >= '202312'
                    AND pt_dt < '202401')
SELECT
-- 当前日期时间段 '202401' 和 '202402'
InfoNow.R_2011_MQM_HII_E001                                             AS Now_R_2011_MQM_HII_E001,
-- 上年日期时间段 '202301' 和 '202302'
InfoYear.R_2011_MQM_HII_E001                                            AS Year_R_2011_MQM_HII_E001,
-- 上期日期时间段 '202312' 和 '202401'
InfoLast.R_2011_MQM_HII_E001                                            AS Last_R_2011_MQM_HII_E001,
-- 求同比
CASE
    WHEN COALESCE(InfoYear.R_2011_MQM_HII_E001, 0) = 0 THEN 0
    ELSE CAST((CAST(InfoNow.R_2011_MQM_HII_E001 AS DECIMAL(20, 4)) - InfoYear.R_2011_MQM_HII_E001) /
              InfoYear.R_2011_MQM_HII_E001 * 100 AS DECIMAL(20, 4)) END AS TB_R_2011_MQM_HII_E001,
-- 求环比
CASE
    WHEN COALESCE(InfoLast.R_2011_MQM_HII_E001, 0) = 0 THEN 0
    ELSE CAST((CAST(InfoNow.R_2011_MQM_HII_E001 AS DECIMAL(20, 4)) - InfoLast.R_2011_MQM_HII_E001) /
              InfoLast.R_2011_MQM_HII_E001 * 100 AS DECIMAL(20, 4)) END AS HB_R_2011_MQM_HII_E001
FROM InfoNow,
     InfoYear,
     InfoLast