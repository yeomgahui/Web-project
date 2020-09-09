package com.cartrapido.main.service;

import com.cartrapido.main.domain.entity.Product;
import com.cartrapido.main.domain.entity.QnA;
import com.cartrapido.main.domain.repository.ProductRepository;
import com.cartrapido.main.domain.repository.QnARepository;
import com.cartrapido.main.web.dto.MemberRequestDTO;
import com.cartrapido.main.web.dto.ProductDTO;
import com.cartrapido.main.web.dto.QnADTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class QnAService {

    @Autowired
    private QnARepository qnaRepository;

    @Transactional
    public void qnaWrite(QnADTO qnaDTO) {
        qnaDTO.setRef(qnaDTO.getSeq());
        qnaDTO.setLev(0);

        qnaRepository.save(qnaDTO.toEntity()).getSeq();
    }

    @Transactional
    public List<QnADTO> getQnAList() {
        List<QnA> qnaList = qnaRepository.findAll();
        List<QnADTO> qnaDTOList = new ArrayList<>();

        for(QnA qna : qnaList){
            QnADTO qnaDTO = QnADTO.builder()
                    .seq(qna.getSeq())
                    .name(qna.getName())
                    .email(qna.getEmail())
                    .title(qna.getTitle())
                    .content(qna.getContent())
                    .ref(qna.getRef())
                    .lev(qna.getLev())
                    .build();
            qnaDTOList.add(qnaDTO);
        }
        return qnaDTOList;
    }



}
