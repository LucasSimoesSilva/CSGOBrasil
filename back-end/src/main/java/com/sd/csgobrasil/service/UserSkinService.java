package com.sd.csgobrasil.service;

import com.sd.csgobrasil.entity.DTO.SkinWithState;
import com.sd.csgobrasil.entity.DTO.UserSkin;
import com.sd.csgobrasil.entity.Skin;
import com.sd.csgobrasil.repository.UserSkinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserSkinService {

    @Autowired
    private UserSkinRepository repository;


    @Autowired
    private SkinService skinService;

    public List<SkinWithState> listSkinsWithStateFromUser(Long idUser) {
        return repository.listSkinsWithStateFromUser(idUser);
    }

    public UserSkin addSkinFromUser(Long skinsPossuidasId, Long userId) {
        return repository.addSkinFromUser(skinsPossuidasId, userId);
    }

    public void deleteSkinFromUser(Long skinsPossuidasId, Long userId) {
        repository.deleteSkinFromUser(skinsPossuidasId, userId);
    }

    public List<Skin> listSkinsFromUser(Long userId) {
        List<UserSkin> userSkins = repository.listSkinsFromUser(userId);
        List<Skin> skins = new ArrayList<>();
        for (UserSkin userSkin : userSkins) {
            Skin skinAdd = skinService.findBySkinId(userSkin.getIdSkin());
            skins.add(skinAdd);
        }

        return skins;
    }
}
