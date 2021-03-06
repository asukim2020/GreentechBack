package kr.co.greetech.back.business.measuredata.repository;

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
            LocalDateTime from,
            LocalDateTime to
    ) {
        long limit = 1000L;
        long count = queryFactory
                .selectFrom(measureData)
                .where(
                        dataLoggerEq(dataLoggerId),
                        crateTimeBetween(from, to)
                )
                .orderBy(measureData.createdTime.desc())
                .fetchCount();

        return queryFactory
                .select(
                        new QMeasureDataDto(
                                measureData.data,
                                measureData.createdTime
                        )
                )
                .from(measureData)
                .where(
                        dataLoggerEq(dataLoggerId),
                        crateTimeBetween(from, to),
                        Mod(count, limit)
                ).orderBy(measureData.createdTime.desc())
                .limit(limit)
                .fetch();
    }

    public List<MeasureDataDto> download(
            Long dataLoggerId,
            LocalDateTime from,
            LocalDateTime to,
            Long offset
    ) {
        long limit = 1000L;
        return queryFactory
                .select(
                        new QMeasureDataDto(
                                measureData.data,
                                measureData.createdTime
                        )
                )
                .from(measureData)
                .where(
                        dataLoggerEq(dataLoggerId),
                        crateTimeBetween(from, to)
                )
                .orderBy(measureData.createdTime.desc())
                .offset(offset)
                .limit(limit)
                .fetch();
    }

    public List<MeasureDataDto> last(
            Long dataLoggerId,
            int count
    ) {
        if (count > 1000) {
            count = 1000;
        }
        return queryFactory
                .select(new QMeasureDataDto(
                        measureData.data,
                        measureData.createdTime
                ))
                .from(measureData)
                .where(
                        dataLoggerEq(dataLoggerId)
                ).orderBy(measureData.createdTime.desc())
                .limit(count)
                .fetch();
    }

    public Long getGroupCount(
            Long dataLoggerId
    ) {
        return queryFactory
                .selectFrom(measureData)
                .where(
                        dataLoggerEq(dataLoggerId)
                )
                .fetchCount();
    }

    // TODO: - test ?????? => ?????? ???
    public List<MeasureDataDto> searchAll(
            Long dataLoggerId
    ) {
        return queryFactory
                .select(new QMeasureDataDto(
                        measureData.data,
                        measureData.createdTime
                ))
                .from(measureData)
                .where(
                        dataLoggerEq(dataLoggerId)
                ).orderBy(measureData.createdTime.desc())
                .limit(1000)
                .fetch();
    }

    private BooleanExpression dataLoggerEq(Long dataLoggerId) {
        return measureData.dataLogger.id.eq(dataLoggerId);
    }

    private BooleanExpression crateTimeBetween(LocalDateTime start, LocalDateTime end) {
        return measureData.createdTime.between(start, end);
    }

    private BooleanExpression Mod(Long count, Long limit) {
        if (count > limit) {
            Long modCount = count / limit;
            return measureData.groupCount.mod(modCount).eq(0L);
        } else {
            return null;
        }
    }
}
