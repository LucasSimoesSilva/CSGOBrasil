package com.sd.csgobrasil.service;

import com.sd.csgobrasil.entity.Skin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sd.csgobrasil.repository.SkinRepository;

import java.util.List;

@Service
public class SkinService {
    @Autowired
    private SkinRepository repository;

    public List<Skin> listSkins() {
        return repository.listSkins();
    }

    public Skin getSkinInfo(Skin skin) {
        return repository.getSkinInfo(skin);
    }

    public Skin addSkin(Skin skin) {
        return repository.addSkin(skin);
    }

    public Skin updateSkin(Long id, Skin skin) {
        return repository.updateSkin(id, skin);
    }

    public Skin findBySkinId(Long id) {
        return repository.findById(id);
    }

    public void deleteSkin(Long id){
        repository.deleteSkin(id);
    }
}
