package com.cartrapido.main.service;

import com.cartrapido.main.domain.entity.Cart;
import com.cartrapido.main.domain.entity.Member;
import com.cartrapido.main.domain.entity.QnA;
import com.cartrapido.main.domain.repository.QnARepository;
import com.cartrapido.main.web.dto.CartDTO;
import com.cartrapido.main.web.dto.QnADTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class QnAService {

    @Autowired
    private QnARepository qnaRepository;

    //문의하기
    @Transactional
    public void qnaWrite(QnADTO qnaDTO) {
        qnaDTO.setLev(0);
        qnaRepository.save(qnaDTO.toEntity()).getSeq();

        // seq = ref
        int seq = qnaRepository.findByMaxId();
        QnA qna = qnaRepository.findAllBySeq(seq);
        qna.setRef(seq);
        qnaRepository.save(qna);
    }


    @Transactional
    public void qnaRefUpdate() {
        int seq = qnaRepository.findByMaxId();
        QnA qna = qnaRepository.findAllBySeq(seq);
        qna.setRef(seq);
        qnaRepository.save(qna);
    }

    //QnA list
    @Transactional
    public List<QnADTO> getQnAList(Pageable pageable) {

        List<QnA> qnaList = pagingQnAList(pageable).getContent();
        //List<QnA> qnaList = qnaRepository.findAll();

        List<QnADTO> qnaDTOList = new ArrayList<QnADTO>();

        for(QnA qna : qnaList){

            QnADTO qnaDTO = QnADTO.builder()
                    .seq(qna.getSeq())
                    .name(qna.getName())
                    .email(qna.getEmail())
                    .title(qna.getTitle())
                    .content(qna.getContent())
                    .ref(qna.getRef())
                    .lev(qna.getLev())
                    .createdDate(LocalDate.from(qna.getCreatedDate()))
                    .build();

            qnaDTOList.add(qnaDTO);
        }
        return qnaDTOList;
    }

    @Transactional
    public Page<QnA> pagingQnAList(Pageable pageable) {
        Page<QnA> pagingQnAList = qnaRepository.findAll(pageable);
        return pagingQnAList;
    }

    //MyQnA list
    @Transactional
    public List<QnADTO> getMyQnAList(String userEmail, Pageable pageable) {

        List<QnA> qnaList = pagingMyQnAList(userEmail, pageable).getContent();

        List<QnADTO> qnaDTOList = new ArrayList<QnADTO>();

        for(QnA qna : qnaList){

            QnADTO qnaDTO = QnADTO.builder()
                    .seq(qna.getSeq())
                    .name(qna.getName())
                    .email(qna.getEmail())
                    .title(qna.getTitle())
                    .content(qna.getContent())
                    .ref(qna.getRef())
                    .lev(qna.getLev())
                    .createdDate(LocalDate.from(qna.getCreatedDate()))
                    .build();

            qnaDTOList.add(qnaDTO);
        }
        return qnaDTOList;
    }

    @Transactional
    public Page<QnA> pagingMyQnAList(String userEmail, Pageable pageable) {
        Page<QnA> pagingMyQnAList = qnaRepository.findAllByEmail(userEmail, pageable);
        return pagingMyQnAList;
    }

    //문의글 보기
    @Transactional
    public QnADTO qnaView(int seq) {
        QnA qna = qnaRepository.findAllBySeq(seq);
        QnADTO qnaDTO = QnADTO.builder()
                .seq(qna.getSeq())
                .name(qna.getName())
                .email(qna.getEmail())
                .title(qna.getTitle())
                .content(qna.getContent())
                .ref(qna.getRef())
                .lev(qna.getLev())
                .build();
        return qnaDTO;
    }

    //문의글 수정
    @Transactional
    public void qnaUpdate(QnADTO qnaDTO) {
        QnA qna = qnaRepository.findAllBySeq(qnaDTO.getSeq());
        qna.setTitle(qnaDTO.getTitle());
        qna.setContent(qnaDTO.getContent());

        qnaRepository.save(qna);
    }

    //나의 문의글 삭제
    @Transactional
    public void qnaDelete(int seq) {
        QnA qna = qnaRepository.findAllBySeq(seq);
        int ref = qna.getRef();
        qnaRepository.deleteByRef(ref);
    }

    //나의 문의글 전체 삭제
    @Transactional
    public void qnaDeleteAll(String userEmail) {
        List<QnA> list = qnaRepository.deleteByEmail(userEmail);
        for(QnA qna : list){
            int ref = qna.getRef();
            qnaRepository.deleteByRef(ref);
        }
    }

    // 답변 등록
    @Transactional
    public void qnaAnswerWrite(QnADTO qnaAnswerDTO) {
        qnaRepository.save(qnaAnswerDTO.toEntity()).getSeq();
    }

    //QnA Search list
    @Transactional
    public List<QnADTO> qnaSearchList(Pageable pageable, String searchValue, String searchOption) {

        List<QnA> qnaList = pagingQnaSearchList(pageable, searchValue, searchOption).getContent();

        List<QnADTO> qnaDTOList = new ArrayList<QnADTO>();

        for(QnA qna : qnaList){

            QnADTO qnaDTO = QnADTO.builder()
                    .seq(qna.getSeq())
                    .name(qna.getName())
                    .email(qna.getEmail())
                    .title(qna.getTitle())
                    .content(qna.getContent())
                    .ref(qna.getRef())
                    .lev(qna.getLev())
                    .createdDate(LocalDate.from(qna.getCreatedDate()))
                    .build();

            qnaDTOList.add(qnaDTO);
        }
        return qnaDTOList;
    }

    @Transactional
    public Page<QnA> pagingQnaSearchList(Pageable pageable, String searchValue, String searchOption) {

        Page<QnA> qnaSearchList = null;
        if(searchOption.equals("titleSearch")){
            qnaSearchList = qnaRepository.findByTitleContaining(searchValue, pageable);
        }else{
            qnaSearchList=  qnaRepository.findByNameContaining(searchValue, pageable);
        }

        return qnaSearchList;
    }

    public Page<QnA> pageQnAList(Pageable pageable){
        Page<QnA> pageQnAList = qnaRepository.findAll(pageable);
        return pageQnAList;
    }
}
