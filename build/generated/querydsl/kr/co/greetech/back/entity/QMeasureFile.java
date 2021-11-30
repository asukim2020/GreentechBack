package kr.co.greetech.back.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMeasureFile is a Querydsl query type for MeasureFile
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QMeasureFile extends EntityPathBase<MeasureFile> {

    private static final long serialVersionUID = 145800164L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMeasureFile measureFile = new QMeasureFile("measureFile");

    public final kr.co.greetech.back.Auditing.QCreateTimeEntity _super = new kr.co.greetech.back.Auditing.QCreateTimeEntity(this);

    public final QCompany company;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdTime = _super.createdTime;

    public final QDataLogger dataLogger;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<kr.co.greetech.back.business.file.MeasureFileType> type = createEnum("type", kr.co.greetech.back.business.file.MeasureFileType.class);

    public final StringPath url = createString("url");

    public QMeasureFile(String variable) {
        this(MeasureFile.class, forVariable(variable), INITS);
    }

    public QMeasureFile(Path<? extends MeasureFile> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMeasureFile(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMeasureFile(PathMetadata metadata, PathInits inits) {
        this(MeasureFile.class, metadata, inits);
    }

    public QMeasureFile(Class<? extends MeasureFile> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.company = inits.isInitialized("company") ? new QCompany(forProperty("company")) : null;
        this.dataLogger = inits.isInitialized("dataLogger") ? new QDataLogger(forProperty("dataLogger"), inits.get("dataLogger")) : null;
    }

}

