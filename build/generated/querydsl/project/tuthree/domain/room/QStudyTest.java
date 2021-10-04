package project.tuthree.domain.room;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QStudyTest is a Querydsl query type for StudyTest
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QStudyTest extends EntityPathBase<StudyTest> {

    private static final long serialVersionUID = -1659922580L;

    public static final QStudyTest studyTest = new QStudyTest("studyTest");

    public final NumberPath<Long> file_id = createNumber("file_id", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> room_id = createNumber("room_id", Long.class);

    public QStudyTest(String variable) {
        super(StudyTest.class, forVariable(variable));
    }

    public QStudyTest(Path<? extends StudyTest> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStudyTest(PathMetadata metadata) {
        super(StudyTest.class, metadata);
    }

}

