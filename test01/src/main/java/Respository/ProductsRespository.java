package Respository;

import DAO.ProductsDAO;
import DAO.UsersDAO;
import RespositoryInterface.IProducts;
import model.Products;

import java.util.List;

public class ProductsRespository implements IProducts {
    ProductsDAO context = new ProductsDAO();
    UsersDAO context2 = new UsersDAO();
    @Override
    public List<Products> getAllProduct() {
        return context.GetAllProduct();
    }

    @Override
    public void DeleteProduct() {

    }

    @Override
    public void getAllProductToBlock() {
        context.getAllProductToBlock();
    }

    @Override
    public void cbxChange() throws Exception {
        context2.getUsersByCBX();
    }


}
