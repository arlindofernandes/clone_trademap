package com.trademap.clone_trademap.service.impl;

import com.trademap.clone_trademap.modelo.AcaoFavorita;
import com.trademap.clone_trademap.repository.AcaoFavotitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AcaoFavoritaService {
    @Autowired
    AcaoFavotitaRepository acaoFavotitaRepository;
    public List<AcaoFavorita> listarSemDuplicidade() {
        return acaoFavotitaRepository.findAll().stream().distinct().collect(Collectors.toList());
    }
}
