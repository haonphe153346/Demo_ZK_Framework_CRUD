package RespositoryInterface;

import model.Products;
import org.zkoss.zk.ui.Component;

import java.util.List;

public interface IProducts {
    List<Products> getAllProduct();

    void DeleteProduct();

    void getAllProductToBlock();

    void cbxChange() throws Exception;
}
