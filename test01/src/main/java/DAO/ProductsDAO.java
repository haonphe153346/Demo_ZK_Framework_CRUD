package DAO;

import context.DBContext;
import model.Products;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductsDAO extends Window {
    Div divProduct;

    public List<Products> GetAllProduct() {
        List<Products> list = new ArrayList<Products>();
        try {
            Connection conn = new DBContext().getConnection();
            String sql = "select * from [Product]";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Products p = new Products(rs.getInt(1), rs.getString(2), rs.getFloat(3), rs.getInt(4), rs.getString(5), rs.getInt(6));
                list.add(p);
            }
            getAllProductToBlock();
            return list;
        } catch (Exception e) {
        }
        return null;
    }

    public void getAllProductToBlock(){
        List<Products> list = new ArrayList<Products>();
        try {
            Connection conn = new DBContext().getConnection();
            String sql = "select * from [Product]";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Products p = new Products(rs.getInt(1), rs.getString(2), rs.getFloat(3), rs.getInt(4), rs.getString(5), rs.getInt(6));
                list.add(p);
                //Image
                Div divImage = new Div();
                Image image = new Image();
                image.setSrc("/assets/image/"+rs.getString(5));
                divImage.appendChild(image);
                //Name
                Div divName = new Div();
                divName.setClass("mb-5");
                Label labelName = new Label();
                labelName.setValue(rs.getString(2));
                divName.appendChild(labelName);
                //Avaiable now
                Div divAvaiable = new Div();
                divAvaiable.setClass("mb-5");
                Label labelAvaiable = new Label();
                labelAvaiable.setValue("Avaiable now");
                divAvaiable.appendChild(labelAvaiable);
                //Price
                Div divPrice = new Div();
                divPrice.setClass("mb-5");
                Label labelPrice = new Label();
                labelPrice.setClass("text-primary font-weight-bold");
                labelPrice.setValue(String.valueOf(rs.getFloat(3)));
                divPrice.appendChild(labelPrice);
                //Button buy
                Div divButton = new Div();
                Button buyButton = new Button();
                buyButton.setSclass("btn btn-primary");
                buyButton.setType("button");
                buyButton.setLabel("BUY");
                divButton.appendChild(buyButton);
                //Append Child
                divProduct.appendChild(divImage);
                divProduct.appendChild(divName);
                divProduct.appendChild(divAvaiable);
                divProduct.appendChild(divPrice);
                divProduct.appendChild(divButton);

            }

        } catch (Exception e) {
        }

    }

    public static void main(String[] args) {
        ProductsDAO p = new ProductsDAO();
        List<Products> ps = p.GetAllProduct();
        System.out.println(ps.get(0).getProduct_name());
    }
}
