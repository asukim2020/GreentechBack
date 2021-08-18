package kr.co.greetech.back.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.greetech.back.dto.MeasureDataDto;
import kr.co.greetech.back.dto.QMeasureDataDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static kr.co.greetech.back.entity.QMeasureData.*;

@Repository
public class MeasureDataQueryRepository {

    private final JPAQueryFactory queryFactory;

    public MeasureDataQueryRepository(EntityManager em) {
        queryFactory = new JPAQueryFactory(em);
    }

    public List<MeasureDataDto> search(
            Long dataLoggerId,
            LocalDateTime start,
            LocalDateTime end
    ) {
        return queryFactory
                .select(new QMeasureDataDto(
                        measureData.data
                ))
                .from(measureData)
                .where(
                        dataLoggerEq(dataLoggerId),
                        timeGoe(start),
                        timeLoe(end)
                ).orderBy(measureData.createdDate.asc())
                .limit(1000)
                .fetch();
    }

    private BooleanExpression dataLoggerEq(Long dataLoggerId) {
        return measureData.dataLogger.id.eq(dataLoggerId);
    }

    private BooleanExpression timeGoe(LocalDateTime start) {
        return measureData.createdDate.goe(start);
    }

    private Predicate timeLoe(LocalDateTime end) {
        return measureData.createdDate.loe(end);
    }
}
