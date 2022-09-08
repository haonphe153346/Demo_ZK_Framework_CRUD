package Respository;

import DAO.UsersDAO;
import RespositoryInterface.IUsers;
import model.Users;

import java.awt.*;
import java.util.List;

public class UsersRespository implements IUsers {

    UsersDAO context = new UsersDAO();


    @Override
    public List<Users> getAllUsers() {
        return null;
    }

    @Override
    public void login(String username, String password) throws Exception {
        context.login(username,password);
    }

    @Override
    public void saveitem(String cid, String cname, String caddress) throws Exception {
        context.saveitem(cid,cname,caddress);
    }

    @Override
    public void viewlist() throws Exception {
        context.viewlist();
    }

    @Override
    public void rptUsers() throws Exception {
        context.rptUsers();
    }

    @Override
    public void EditView(String cidcust) throws Exception {
        context.EditView(cidcust);
    }

    @Override
    public void DeleteView(String cidcust) throws Exception {
        context.DeleteView(cidcust);
    }

    @Override
    public void cleartext() throws Exception {
        context.cleartext();
    }
}
