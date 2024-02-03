package org.wheel.sqlparser.sdk.cube;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.wheel.sqlparser.api.enums.DateTypeEnum;

import java.util.Date;

@Slf4j
@Getter
public class CubeDateRangeParserGenerateFactory {

    DateTypeEnum dateTypeEnum;

    Date startTime;

    Date endTime;

    private CubeDateRangeParserGenerateFactory(DateTypeEnum dateTypeEnum, Date startTime, Date endTime) {
        this.dateTypeEnum = dateTypeEnum;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static CubeDateRangeParserGenerateFactory getInstance(DateTypeEnum dateTypeEnum, Date startTime, Date endTime) {
        return new CubeDateRangeParserGenerateFactory(dateTypeEnum, startTime, endTime);
    }


    public void parserSq() {
        if (dateTypeEnum == DateTypeEnum.YEAR) {
            this.startTime = DateUtil.offset(this.startTime, DateField.DAY_OF_YEAR, -1);
            this.endTime = DateUtil.offset(this.endTime, DateField.DAY_OF_YEAR, -1);
        } else if (dateTypeEnum == DateTypeEnum.MONTH) {
            this.startTime = DateUtil.offsetMonth(this.startTime, -1);
            this.endTime = DateUtil.offsetMonth(this.endTime, -1);
        } else if (dateTypeEnum == DateTypeEnum.DAY) {
            this.startTime = DateUtil.offsetDay(this.startTime, -1);
            this.endTime = DateUtil.offsetDay(this.endTime, -1);
        }
    }

    public void parserTq() {
        this.startTime = DateUtil.offset(this.startTime, DateField.DAY_OF_YEAR, -1);
        this.endTime = DateUtil.offset(this.endTime, DateField.DAY_OF_YEAR, -1);
    }
}
