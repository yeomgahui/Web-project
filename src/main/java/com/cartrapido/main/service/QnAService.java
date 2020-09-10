package com.cartrapido.main.service;

import com.cartrapido.main.domain.entity.QnA;
import com.cartrapido.main.domain.repository.QnARepository;
import com.cartrapido.main.web.dto.QnADTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        qnaDTO.setRef(qnaDTO.getSeq());
        qnaDTO.setLev(0);

        qnaRepository.save(qnaDTO.toEntity()).getSeq();
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



}
