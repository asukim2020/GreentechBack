package kr.co.greetech.back.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.Generated;

/**
 * kr.co.greetech.back.dto.QMeasureDataDto is a Querydsl Projection type for MeasureDataDto
 */
@Generated("com.querydsl.codegen.ProjectionSerializer")
public class QMeasureDataDto extends ConstructorExpression<MeasureDataDto> {

    private static final long serialVersionUID = -727917509L;

    public QMeasureDataDto(com.querydsl.core.types.Expression<String> data, com.querydsl.core.types.Expression<java.time.LocalDateTime> time) {
        super(MeasureDataDto.class, new Class<?>[]{String.class, java.time.LocalDateTime.class}, data, time);
    }

}

