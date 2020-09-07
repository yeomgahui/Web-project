package com.cartrapido.main.memberControl.service;

import com.cartrapido.main.memberControl.dao.BlackList;
import com.cartrapido.main.memberControl.dao.BlackListRepository;
import com.cartrapido.main.memberControl.dto.BlackListResponseDTO;
import com.cartrapido.main.memberControl.dto.BlackListSaveDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BlackListService {

    private final BlackListRepository blackListRepository;

    @Transactional
    public void save(BlackListSaveDTO blackListSaveDTO){
        blackListRepository.save(blackListSaveDTO.toEntity());
    }


    @Transactional(readOnly = true)
    public List<BlackListResponseDTO> findAllDesc(){
        return blackListRepository.findAll().stream()
                .map(BlackListResponseDTO::new)
                .collect(Collectors.toList());
    }
    @Transactional
    public void delete(Long id){
        BlackList blackList = blackListRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당하는 신고내역이 없습니다."));
        blackListRepository.delete(blackList);
    }
}
