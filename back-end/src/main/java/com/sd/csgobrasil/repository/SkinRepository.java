package com.sd.csgobrasil.repository;

import com.sd.csgobrasil.conn.ConnectionJdbc;
import com.sd.csgobrasil.entity.Skin;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SkinRepository {

    private List<Skin> skinsCadastradas;

    private PreparedStatement st;
    private ResultSet rs;
    private String sql;

    public SkinRepository() {
        this.skinsCadastradas = new ArrayList<>();
    }

    public List<Skin> listSkins() {
        skinsCadastradas.clear();
        Skin skin;
        sql = "SELECT * FROM skin";

        try (Connection conn = ConnectionJdbc.getConnection()) {
            st = conn.prepareStatement(sql);
            rs = st.executeQuery();
            while (rs.next()) {
                skin = getSkin();
                skinsCadastradas.add(skin);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar skins\n" + e);
        }

        return skinsCadastradas;
    }

    public Skin getSkinInfo(Skin skin) {
        sql = "SELECT * FROM skin WHERE skin.id = %s".formatted(skin.getId());

        try (Connection conn = ConnectionJdbc.getConnection()) {
            st = conn.prepareStatement(sql);
            rs = st.executeQuery();
            Skin s = getSkin();

            if (s.getId() != null) {
                return s;
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar informações da Skin\n" + e);
        }
        return null;
    }

    public Skin addSkin(Skin skin) {
        sql = "INSERT INTO skin(nome,arma,preco,raridade,imagem) values(?,?,?,?,?)";
        Long id = 0L;


        try (Connection conn = ConnectionJdbc.getConnection()) {
            st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setString(1, skin.getNome());
            st.setString(2, skin.getArma());
            st.setInt(3, skin.getPreco());
            st.setString(4, skin.getRaridade());
            st.setString(5, skin.getImagem());
            st.execute();

            ResultSet key = st.getGeneratedKeys();

            if (key.next()) {
                id = key.getLong(1);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao inserir skin\n" + e);
        }
        return findById(id);
    }

    public Skin updateSkin(Long id, Skin skin) {
        sql = "UPDATE skin SET nome=?, arma=?, preco=?, raridade=?, imagem=? WHERE id=?";

        try (Connection conn = ConnectionJdbc.getConnection()) {
            st = conn.prepareStatement(sql);
            st.setString(1, skin.getNome());
            st.setString(2, skin.getArma());
            st.setInt(3, skin.getPreco());
            st.setString(4, skin.getRaridade());
            st.setString(5,skin.getImagem());
            st.setLong(6, id);
            st.execute();
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar skin" + e);
        }

        return findById(id);
    }

    public Skin findById(Long id) {
        sql = "SELECT * FROM skin WHERE skin.id = ?";
        Skin skin = null;
        try (Connection conn = ConnectionJdbc.getConnection()) {
            st = conn.prepareStatement(sql);
            st.setLong(1, id);
            rs = st.executeQuery();
            if (rs.next()) {
                skin = getSkin();
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar skin\n" + e);
        }

        return skin;
    }

    public void deleteSkin(Long id) {
        String deleteSql = "DELETE FROM skin WHERE id=?";

        try (Connection conn = ConnectionJdbc.getConnection()) {
            if (findById(id) != null) {
                st = conn.prepareStatement(deleteSql);
                st.setLong(1, id);
                st.execute();
                System.out.println("Skin removida");
            } else {
                System.out.println("Skin não encontrada.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao deletar skin\n" + e);
        }
    }

    private Skin getSkin() throws SQLException {
        Skin skin;
        skin = new Skin();
        skin.setId(rs.getLong("id"));
        skin.setNome(rs.getString("nome"));
        skin.setArma(rs.getString("arma"));
        skin.setPreco(rs.getInt("preco"));
        skin.setRaridade(rs.getString("raridade"));
        skin.setImagem(rs.getString("imagem"));
        return skin;
    }

}
