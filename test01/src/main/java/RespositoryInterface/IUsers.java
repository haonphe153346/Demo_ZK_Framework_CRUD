package RespositoryInterface;

import model.Users;

import java.util.List;

public interface IUsers {
    List<Users> getAllUsers();
    void login(String username, String password) throws Exception;
    void saveitem(String cid, String cname, String caddress) throws Exception;
    void viewlist() throws Exception;
    void rptUsers() throws Exception;
    void EditView(String cidcust) throws Exception;
    void DeleteView(String cidcust) throws Exception;
    void cleartext() throws Exception;
}

