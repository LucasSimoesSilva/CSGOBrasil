package com.sd.csgobrasil.repository;

import com.sd.csgobrasil.conn.ConnectionJdbc;
import com.sd.csgobrasil.entity.DTO.SkinWithState;
import com.sd.csgobrasil.entity.DTO.UserSkin;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserSkinRepository {

    private PreparedStatement st;
    private ResultSet rs;

    public List<SkinWithState> listSkinsWithStateFromUser(Long idUser) {
        String mySql =
                "SELECT s.id AS id_skin, s.nome, s.arma, s.preco, s.raridade, s.imagem, " +
                        "CASE " +
                        "WHEN EXISTS (SELECT 1 FROM movement WHERE id_skin = s.id) THEN " +
                        "CASE " +
                        "WHEN (SELECT estado_venda FROM movement WHERE id_skin = s.id LIMIT 1) = true THEN false " +
                        "ELSE true " +
                        "END " +
                        "ELSE false " +
                        "END AS is_in_movement, " +
                        "m.id_venda " +
                        "FROM skin s " +
                        "JOIN user_skins_user su ON s.id = su.skins_user_id " +
                        "LEFT JOIN movement m ON s.id = m.id_skin " +
                        "WHERE su.user_id = ?";
        List<SkinWithState> skins = new ArrayList<>();

        try (Connection conn = ConnectionJdbc.getConnection()) {
            st = conn.prepareStatement(mySql);
            st.setLong(1, idUser);
            rs = st.executeQuery();
            while (rs.next()) {
                SkinWithState s = new SkinWithState();
                s.setIdSkin(rs.getLong("id_skin"));
                s.setNome(rs.getString("nome"));
                s.setArma(rs.getString("arma"));
                s.setPreco(rs.getInt("preco"));
                s.setRaridade(rs.getString("raridade"));
                s.setImagem(rs.getString("imagem"));
                s.setInMovement(rs.getBoolean("is_in_movement"));
                s.setIdVenda(rs.getLong("id_venda"));
                skins.add(s);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar skins do usuário\n");
        }

        return skins;
    }

    public UserSkin addSkinFromUser(Long skinsPossuidasId, Long userId) {
        String sql = "INSERT into user_skins_user(skins_user_id,user_id) values(?,?)";

        try (Connection conn = ConnectionJdbc.getConnection()) {
            st = conn.prepareStatement(sql);
            st.setLong(1, skinsPossuidasId);
            st.setLong(2, userId);
            st.execute();
        } catch (SQLException e) {
            System.out.println("Erro ao adicionar skin para o usuário\n");
        }

        return new UserSkin(skinsPossuidasId, userId);
    }

    public void deleteSkinFromUser(Long skinsPossuidasId, Long userId) {
        String sql = "delete from user_skins_user where skins_user_id = ? and user_id= ?";

        try (Connection conn = ConnectionJdbc.getConnection()) {
            st = conn.prepareStatement(sql);
            st.setLong(1, skinsPossuidasId);
            st.setLong(2, userId);
            st.execute();
        } catch (SQLException e) {
            System.out.println("Erro ao retirar skin do usuário\n");
        }

    }

    public List<UserSkin> listSkinsFromUser(Long userId) {
        String sql = "SELECT * FROM user_skins_user WHERE user_id=?";
        List<UserSkin> userSkins = new ArrayList<>();

        try (Connection conn = ConnectionJdbc.getConnection()) {
            st = conn.prepareStatement(sql);
            st.setLong(1, userId);
            rs = st.executeQuery();
            while (rs.next()) {
                UserSkin userSkin = new UserSkin();
                userSkin.setIdUser(userId);
                userSkin.setIdSkin(rs.getLong("skins_user_id"));
                userSkins.add(userSkin);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar skins do usuário\n");
        }

        return userSkins;
    }
}
