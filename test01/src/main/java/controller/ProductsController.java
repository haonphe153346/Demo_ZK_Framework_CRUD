package controller;

import DAO.UsersDAO;
import Respository.ProductsRespository;
import model.Products;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zul.*;

import java.util.Arrays;
import java.util.List;

public class ProductsController extends SelectorComposer<Window> {
    ProductsRespository productsRespository = new ProductsRespository();
    Combobox countriesCombobox;

    public void getAllProductToBlock() {
        productsRespository.getAllProductToBlock();
    }


    private ListModel<Products> allContributor
            = new ListModelList<Products>(productsRespository.getAllProduct());

    public ListModel<Products> getAllProduct() {
        return allContributor;
    }

    public static List<String> getCountries() {
        return Arrays.asList(new String[]{"Admin", "Users"});
    }

    public ListModel<String> combobox() {
        return countriesModel;
    }

    private final ListModel<String> countriesModel
            = new ListModelList<String>(getCountries());
    UsersDAO usersDAO = new UsersDAO();

    @Listen("onChange = #countriesCombobox")
    public void changeCountry() throws Exception {
        usersDAO.getUsersByCBX();
    }
}
