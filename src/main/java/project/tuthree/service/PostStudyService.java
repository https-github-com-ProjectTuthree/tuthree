package project.tuthree.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.tuthree.domain.post.PostStudy;
import project.tuthree.domain.room.StudyRoom;
import project.tuthree.dto.post.PoststudyDTO;
import project.tuthree.mapper.PostStudyMapper;
import project.tuthree.repository.PostStudyRepository;
import project.tuthree.repository.PostStudyRepository.StudyListDTO;
import project.tuthree.repository.StudyRoomRepository;
import project.tuthree.repository.UserFileRepository;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;
import static project.tuthree.exception.ExceptionSupplierImpl.wrap;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostStudyService {

    private final PostStudyRepository postStudyRepository;
    private final PostStudyMapper postStudyMapper;
    private final StudyRoomRepository studyRoomRepository;
    private final UserFileRepository userFileRepository;

    /** 선생님, 학생 아이디로 스터디룸 보고서 전체 조회 */
    public List<StudyListDTO> findPostByStudyRoom(String teacherId, String studentId) {
        StudyRoom studyRoom = studyRoomRepository.findStudyRoomById(teacherId, studentId, true, false);
        List<PostStudy> postStudy = postStudyRepository.findPostByStudyRoom(studyRoom);
        return postStudy.stream()
                .map(m -> wrap(() -> new StudyListDTO(m.getId(), userFileRepository.unixToDate(m.getDate()), m.getNumber(), m.getStart(), m.getEnd(), m.getDetail())))
                .collect(Collectors.toList());
    }

    /** 수업 보고서 등록하기 */
    public Long registerPost(String teacherId, String studentId, PoststudyDTO poststudyDTO) {
        StudyRoom studyRoom = studyRoomRepository.findStudyRoomById(teacherId, studentId, true, false);
        poststudyDTO.updateDTO(studyRoom);
        return postStudyRepository.registerPost(postStudyMapper.toEntity(poststudyDTO));
    }

    /** 특정 날짜 수업 보고서 조회 */
    public List<postStudyListDTO> findPostStudyByDate(String teacherId, String studentId, String date) throws ParseException {
        List<PostStudy> postByDate = postStudyRepository.findPostByDate(teacherId, studentId, date);
        return postByDate.stream()
                .map(m -> wrap(() -> new postStudyListDTO(m.getId(), userFileRepository.unixToDate(m.getDate()),
                        String.valueOf(m.getNumber()) + "회차", m.getStart(), m.getEnd(), m.getDetail())))
                .collect(Collectors.toList());
    }

    /** 특정 수업 보고서 조회 */
    public StudyListDTO findPostById(Long id) throws ParseException {
        PostStudy postStudy = postStudyRepository.findPost(id);
        return new StudyListDTO(postStudy.getId(), userFileRepository.unixToDate(postStudy.getDate()), postStudy.getNumber(),
                postStudy.getStart(), postStudy.getEnd(), postStudy.getDetail());
    }

    /** 수업 보고서 수정 */
    public Long updatePost(Long id, PoststudyDTO poststudyDTO) {
        return postStudyRepository.updatePost(id, postStudyMapper.toEntity(poststudyDTO));
    }

    @Getter
    @AllArgsConstructor
    public static class postStudyListDTO {
        Long id;
        String date;
        String number;
        String start;
        String end;
        String detail;
    }
}
