package kr.co.greetech.back.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.Generated;

/**
 * kr.co.greetech.back.dto.QMeasureFileDto is a Querydsl Projection type for MeasureFileDto
 */
@Generated("com.querydsl.codegen.ProjectionSerializer")
public class QMeasureFileDto extends ConstructorExpression<MeasureFileDto> {

    private static final long serialVersionUID = 1268854057L;

    public QMeasureFileDto(com.querydsl.core.types.Expression<java.time.LocalDateTime> time, com.querydsl.core.types.Expression<String> url, com.querydsl.core.types.Expression<kr.co.greetech.back.business.file.MeasureFileType> type) {
        super(MeasureFileDto.class, new Class<?>[]{java.time.LocalDateTime.class, String.class, kr.co.greetech.back.business.file.MeasureFileType.class}, time, url, type);
    }

}

