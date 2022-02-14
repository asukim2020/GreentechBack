package kr.co.greetech.back.business.file;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.greetech.back.dto.MeasureFileDto;
import kr.co.greetech.back.dto.QMeasureFileDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static kr.co.greetech.back.entity.QMeasureFile.measureFile;

@Repository
public class FileQueryRepository {

    private final JPAQueryFactory queryFactory;
    public FileQueryRepository(EntityManager em) {
        queryFactory = new JPAQueryFactory(em);
    }

    public List<MeasureFileDto> selectAll(
            Long companyId,
            Long dataLoggerId
    ) {
        return queryFactory
                .select(new QMeasureFileDto(
                        measureFile.createdTime,
                        measureFile.url,
                        measureFile.type
                ))
                .from(measureFile)
                .where(
                        companyIdEq(companyId),
                        dataLoggerIdEq(dataLoggerId)
                )
                .limit(100)
                .fetch();
    }

    public List<MeasureFileDto> select(
            Long companyId,
            Long dataLoggerId,
            MeasureFileType type
    ) {
        return queryFactory
                .select(new QMeasureFileDto(
                        measureFile.createdTime,
                        measureFile.url,
                        measureFile.type
                ))
                .from(measureFile)
                .where(
                        companyIdEq(companyId),
                        dataLoggerIdEq(dataLoggerId),
                        measureFileType(type)
                )
                .limit(100)
                .fetch();
    }

    private BooleanExpression companyIdEq(Long companyId) {
        return measureFile.company.id.eq(companyId);
    }

    private BooleanExpression dataLoggerIdEq(Long dataLoggerId) {
        return measureFile.dataLogger.id.eq(dataLoggerId);
    }

    private BooleanExpression measureFileType(MeasureFileType type) {
        return measureFile.type.eq(type);
    }
}
