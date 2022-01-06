package kr.co.greetech.back.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDataLogger is a Querydsl query type for DataLogger
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QDataLogger extends EntityPathBase<DataLogger> {

    private static final long serialVersionUID = 442120368L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDataLogger dataLogger = new QDataLogger("dataLogger");

    public final kr.co.greetech.back.Auditing.QBaseTimeEntity _super = new kr.co.greetech.back.Auditing.QBaseTimeEntity(this);

    public final StringPath channelName = createString("channelName");

    public final QCompany company;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdTime = _super.createdTime;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath ip = createString("ip");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedTime = _super.lastModifiedTime;

    public final StringPath modelName = createString("modelName");

    public final EnumPath<kr.co.greetech.back.business.datalogger.type.DataLoggerType> type = createEnum("type", kr.co.greetech.back.business.datalogger.type.DataLoggerType.class);

    public final StringPath unit = createString("unit");

    public QDataLogger(String variable) {
        this(DataLogger.class, forVariable(variable), INITS);
    }

    public QDataLogger(Path<? extends DataLogger> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDataLogger(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDataLogger(PathMetadata metadata, PathInits inits) {
        this(DataLogger.class, metadata, inits);
    }

    public QDataLogger(Class<? extends DataLogger> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.company = inits.isInitialized("company") ? new QCompany(forProperty("company")) : null;
    }

}

