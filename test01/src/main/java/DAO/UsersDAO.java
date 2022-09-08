package DAO;

import context.DBContext;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

import model.Products;
import model.Users;
import org.zkoss.bind.annotation.Command;
import org.zkoss.util.media.AMedia;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.annotation.Listen;

import org.zkoss.zul.*;

public class UsersDAO extends Window  {
    String SQL;


    private String actionType = "Entry";

    public void login(String user, String pass) throws Exception {
        String cPass = null;
        SQL = "SELECT * FROM Users WHERE users_name='" + user + "'";
        Connection Conn = new DBContext().getConnection();
        Statement st = Conn.createStatement();
        ResultSet rs = st.executeQuery(SQL);
        while (rs.next()) {
            cPass = rs.getString("users_password").toUpperCase().toString().trim();
        }

        rs.close();
        Conn.close();
        if (pass.trim().toUpperCase().equals(cPass)) {
            Messagebox.show("Welcome " + user, "Login Successfully!", Messagebox.OK, Messagebox.ERROR);
            Executions.getCurrent().sendRedirect("ListProducts.zul");

        } else {
            Messagebox.show("User & Password Inccorect !!!", "Error Message", Messagebox.OK, Messagebox.ERROR);
        }

    }

    public void saveitem(String cid, String cname, String caddress) throws Exception {
        Connection conn = new DBContext().getConnection();
        if (actionType.equals("Edit")) {
            SQL = "UPDATE Users SET users_name=?, users_address=? WHERE users_id=? ";
            PreparedStatement ps = conn.prepareStatement(SQL);
            ps.setString(1, cname);
            ps.setString(2, caddress);
            ps.setString(3, cid);
            ps.executeUpdate();
            conn.close();
        } else {
            SQL = "\n" +
                    "INSERT INTO [Users]\n" +
                    "           ([users_id]\n" +
                    "           ,[users_name]\n" +
                    "           ,[users_password]\n" +
                    "           ,[users_email]\n" +
                    "           ,[users_phone]\n" +
                    "           ,[users_address]\n" +
                    "           ,[users_role])\n" +
                    "     VALUES\n" +
                    "           (?,?,?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(SQL);
            ps.setString(1, cid);
            ps.setString(2, cname);
            ps.setString(3, "passWordRAW@@123");
            ps.setString(4, "email@raw");
            ps.setString(5, "0123456789");
            ps.setString(6, caddress);
            ps.setBoolean(7, false);
            ps.executeUpdate();
            conn.close();
        }

        Messagebox.show("Data has been saved successfully..", "Data Saved", Messagebox.OK, Messagebox.INFORMATION);
        viewlist();
    }

    public void viewlist() throws Exception {
        Listbox lb = (Listbox) this.getFellow("lstData");
        lb.getItems().clear();
        SQL = "SELECT * FROM Users";
        Connection conn = new DBContext().getConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(SQL);

        while (rs.next()) {
            Listitem li = new Listitem();
            final String cidcust = rs.getString("users_id");

            /*CREATE BUTTON EDIT IN GRID*/
            Button btn = new Button();
            btn.setLabel("EDIT");
            btn.setWidth("60px");
            btn.setHeight("25px");


            btn.addEventListener(Events.ON_CLICK, new org.zkoss.zk.ui.event.EventListener() {
                public void onEvent(org.zkoss.zk.ui.event.Event event) throws Exception {
                    EditView(cidcust);
                }
            });
            Listcell lc = new Listcell();
            lc.appendChild(btn);

            /*CREATE BUTTON DELETE IN GRID*/
            Button btndel = new Button();
            btndel.setLabel("DELETE");
            btndel.setWidth("80px");
            btndel.setHeight("25px");
            btndel.setSclass("btn btn-danger");
            btndel.addEventListener(Events.ON_CLICK, new org.zkoss.zk.ui.event.EventListener() {
                public void onEvent(org.zkoss.zk.ui.event.Event event) throws Exception {
                    DeleteView(cidcust);
                }

            });
            Listcell lcdel = new Listcell();
            lcdel.appendChild(btndel);
            li.setValue(rs.getString("users_id"));
            li.appendChild(new Listcell(rs.getString("users_id")));
            li.appendChild(new Listcell(rs.getString("users_name")));
            li.appendChild(new Listcell(rs.getString("users_address")));
            li.appendChild(new Listcell(rs.getString("users_email")));
            if (rs.getBoolean("users_role") == true) {
                li.appendChild(new Listcell("Admin"));
            } else {
                li.appendChild(new Listcell("Users"));
            }
            li.appendChild(lc);
            li.appendChild(lcdel);
            lb.appendChild(li);
        }
        rs.close();
        st.close();
        conn.close();
    }

    public void rptUsers() throws Exception {
        Connection conn = new DBContext().getConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM Users");
        Map param = new HashMap();
        String namafile = "Userslist";

        String reportSrc = Sessions.getCurrent().getWebApp().getRealPath("content/rpt/rptUsers.jasper");
        String reportDest = Sessions.getCurrent().getWebApp().getRealPath("content/rpt/" + namafile);


        Iframe iframe = (Iframe) this.getFellow("rpt");
        File f = new File(reportDest);
        byte[] buffer = new byte[(int) f.length()];
        FileInputStream fs = new FileInputStream(f);
        fs.read(buffer);
        fs.close();
        ByteArrayInputStream is = new ByteArrayInputStream(buffer);
        AMedia amedia = new AMedia(namafile, "pdf", "application/pdf", is);

        iframe.setContent(amedia);
    }

    /*EDIT FORM CODE*/
    public void EditView(String cidcust) throws Exception {
        Textbox tidcust = (Textbox) this.getFellow("users_id");
        Textbox tnamecust = (Textbox) this.getFellow("users_name");
        Textbox taddress = (Textbox) this.getFellow("users_address");
        Textbox tphone = (Textbox) getFellow("users_phone");
        Textbox trole = (Textbox) getFellow("users_role");
        Connection conn = new DBContext().getConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM Users WHERE users_id='" + cidcust + "'");
        while (rs.next()) {
            tidcust.setValue(rs.getString("users_id"));
            tnamecust.setValue(rs.getString("users_name"));
            taddress.setValue(rs.getString("users_address"));
            tphone.setValue(rs.getString("users_phone"));
            if (rs.getBoolean("users_role") == true) {
                trole.setValue("Admin");
            } else {
                trole.setValue("Users");
            }
        }
        actionType = "Edit";
    }

    /*DELETE RECORD FORM CODE*/
    public void DeleteView(String cidcust) throws Exception {
        SQL = "DELETE FROM Users WHERE users_id = ?";

        Connection conn = new DBContext().getConnection();
        PreparedStatement prep = conn.prepareStatement(SQL);
        prep.setString(1, cidcust);
        prep.executeUpdate();
        conn.close();

        Messagebox.show("Data has been Delete successfully..", "Data Delete", Messagebox.OK, Messagebox.INFORMATION);

        viewlist();

    }

    /*CLEAR TEXT To ENTRY*/
    public void cleartext() throws Exception {
        Textbox tidcust = (Textbox) this.getFellow("users_id");
        Textbox tnamecust = (Textbox) this.getFellow("users_name");
        Textbox taddress = (Textbox) this.getFellow("users_address");
        Textbox tphone = (Textbox) this.getFellow("users_phone");
        Textbox trole = (Textbox) this.getFellow("users_role");
        trole.setValue("");
        tphone.setValue("");
        tidcust.setValue("");
        tnamecust.setValue("");
        taddress.setValue("");
        tidcust.setFocus(true);
        actionType = "Entry";
    }

    @Command
    public void show() throws Exception {
        Connection conn = new DBContext().getConnection();
        Listbox lb = (Listbox) this.getFellow("lstData");
        lb.getItems().clear();
        Textbox textbox = (Textbox) this.getFellow("txt");
        lb.getItems().clear();
        SQL = "SELECT * FROM USERS WHERE users_name= ?";
        PreparedStatement ps = conn.prepareStatement(SQL);
        ps.setString(1, textbox.getValue());
        ps.executeUpdate();
        conn.close();
    }

    public void SearchUsersByName() throws Exception {
        Listbox lb = (Listbox) this.getFellow("lstData");
        lb.getItems().clear();
        Textbox txt = (Textbox) this.getFellow("txt");
        SQL = "SELECT * FROM Users where users_name LIKE ?";
        Connection conn = new DBContext().getConnection();
        PreparedStatement st = conn.prepareStatement(SQL);
        st.setString(1, "%" + txt.getValue() + "%");
        ResultSet rs = st.executeQuery();

        while (rs.next()) {
            Listitem li = new Listitem();
            final String cidcust = rs.getString("users_id");

            /*CREATE BUTTON EDIT IN GRID*/
            Button btn = new Button();
            btn.setLabel("EDIT");
            btn.setWidth("60px");
            btn.setHeight("25px");


            btn.addEventListener(Events.ON_CLICK, new org.zkoss.zk.ui.event.EventListener() {
                public void onEvent(org.zkoss.zk.ui.event.Event event) throws Exception {
                    EditView(cidcust);
                }
            });
            Listcell lc = new Listcell();
            lc.appendChild(btn);

            /*CREATE BUTTON DELETE IN GRID*/
            Button btndel = new Button();
            btndel.setLabel("DELETE");
            btndel.setWidth("80px");
            btndel.setHeight("25px");
            btndel.setSclass("btn btn-danger");
            btndel.addEventListener(Events.ON_CLICK, new org.zkoss.zk.ui.event.EventListener() {
                public void onEvent(org.zkoss.zk.ui.event.Event event) throws Exception {
                    DeleteView(cidcust);
                }

            });
            Listcell lcdel = new Listcell();
            lcdel.appendChild(btndel);
            li.setValue(rs.getString("users_id"));
            li.appendChild(new Listcell(rs.getString("users_id")));
            li.appendChild(new Listcell(rs.getString("users_name")));
            li.appendChild(new Listcell(rs.getString("users_address")));
            li.appendChild(new Listcell(rs.getString("users_email")));
            if (rs.getBoolean("users_role") == true) {
                li.appendChild(new Listcell("Admin"));
            } else {
                li.appendChild(new Listcell("Users"));
            }
            li.appendChild(lc);
            li.appendChild(lcdel);
            lb.appendChild(li);
        }
        rs.close();
        st.close();
        conn.close();
    }

    @Listen("onChange = #countriesCombobox")
    public void getUsersByCBX() throws Exception {
        Listbox lb = (Listbox) this.getFellow("lstData");
        lb.getItems().clear();
        Combobox countriesCombobox = (Combobox)this.getFellow("countriesCombobox");
        SQL = "SELECT * FROM Users where users_role = ?";
        Connection conn = new DBContext().getConnection();
        PreparedStatement st = conn.prepareStatement(SQL);
        int role;
        if(countriesCombobox.getValue().equals("Admin")){
            role = 1;
        }else{
            role=2;
        }
        st.setInt(1, role);
        ResultSet rs = st.executeQuery();

        while (rs.next()) {
            Listitem li = new Listitem();
            final String cidcust = rs.getString("users_id");

            /*CREATE BUTTON EDIT IN GRID*/
            Button btn = new Button();
            btn.setLabel("EDIT");
            btn.setWidth("60px");
            btn.setHeight("25px");


            btn.addEventListener(Events.ON_CLICK, new org.zkoss.zk.ui.event.EventListener() {
                public void onEvent(org.zkoss.zk.ui.event.Event event) throws Exception {
                    EditView(cidcust);
                }
            });
            Listcell lc = new Listcell();
            lc.appendChild(btn);

            /*CREATE BUTTON DELETE IN GRID*/
            Button btndel = new Button();
            btndel.setLabel("DELETE");
            btndel.setWidth("80px");
            btndel.setHeight("25px");
            btndel.setSclass("btn btn-danger");
            btndel.addEventListener(Events.ON_CLICK, new org.zkoss.zk.ui.event.EventListener() {
                public void onEvent(org.zkoss.zk.ui.event.Event event) throws Exception {
                    DeleteView(cidcust);
                }

            });
            Listcell lcdel = new Listcell();
            lcdel.appendChild(btndel);
            li.setValue(rs.getString("users_id"));
            li.appendChild(new Listcell(rs.getString("users_id")));
            li.appendChild(new Listcell(rs.getString("users_name")));
            li.appendChild(new Listcell(rs.getString("users_address")));
            li.appendChild(new Listcell(rs.getString("users_email")));
            if (rs.getBoolean("users_role") == true) {
                li.appendChild(new Listcell("Admin"));
            } else {
                li.appendChild(new Listcell("Users"));
            }
            li.appendChild(lc);
            li.appendChild(lcdel);
            lb.appendChild(li);
        }
        rs.close();
        st.close();
        conn.close();
    }
    public static List<String> getCountries() {
        return Arrays.asList(new String[]{"China", "France", "Germany", "Italy", "Japan", "Korea", "Sweden",
                "Taiwan", "United Kingdom", "United States"});
    }

    private final ListModel<String> countriesModel
            = new ListModelList<String>(getCountries());

}
