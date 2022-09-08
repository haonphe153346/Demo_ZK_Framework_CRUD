package controller;

import Respository.UsersRespository;

import org.zkoss.zk.ui.Sessions;


import org.zkoss.zul.Window;
import javax.servlet.http.HttpSession;

public class UsersController extends Window {
    UsersRespository usersRespository = new UsersRespository();

    public void Login(String user, String passwd) throws Exception {
        usersRespository.login(user,passwd);
    }

    public void viewlist() throws Exception {
        usersRespository.viewlist();
    }

    public void testSession(){
        HttpSession session = (HttpSession)Sessions.getCurrent();
        session.setAttribute("aa","a");
    }
    
}
