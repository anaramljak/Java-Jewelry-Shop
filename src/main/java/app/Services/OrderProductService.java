package app.Services;

import app.Persistance.Entities.*;
import app.Persistance.Repositories.OrderProductRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class OrderProductService {
    private final OrderProductRepository repository;

    public OrderProductService(OrderProductRepository repository) {
        this.repository = repository;
    }

    public OrderProduct save(OrderProduct orderProduct){
       return repository.save(orderProduct);
    }

    public List<OrderProduct> findByOrder(Orders order){
        return  repository.findByOrders(order);
    }
    public List<OrderProduct> findByProduct(Product product){
        return  repository.findByProduct(product);
    }
}
