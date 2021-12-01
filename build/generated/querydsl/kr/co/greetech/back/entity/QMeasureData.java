package kr.co.greetech.back.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMeasureData is a Querydsl query type for MeasureData
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QMeasureData extends EntityPathBase<MeasureData> {

    private static final long serialVersionUID = 145733138L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMeasureData measureData = new QMeasureData("measureData");

    public final DateTimePath<java.time.LocalDateTime> createdTime = createDateTime("createdTime", java.time.LocalDateTime.class);

    public final StringPath data = createString("data");

    public final QDataLogger dataLogger;

    public final NumberPath<Long> groupCount = createNumber("groupCount", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QMeasureData(String variable) {
        this(MeasureData.class, forVariable(variable), INITS);
    }

    public QMeasureData(Path<? extends MeasureData> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMeasureData(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMeasureData(PathMetadata metadata, PathInits inits) {
        this(MeasureData.class, metadata, inits);
    }

    public QMeasureData(Class<? extends MeasureData> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.dataLogger = inits.isInitialized("dataLogger") ? new QDataLogger(forProperty("dataLogger"), inits.get("dataLogger")) : null;
    }

}

