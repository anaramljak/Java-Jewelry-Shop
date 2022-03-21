package app.Services;

import app.Persistance.Entities.OrderPers;
import app.Persistance.Entities.OrderProduct;
import app.Persistance.Entities.Orders;
import app.Persistance.Repositories.OrderPersRepository;
import app.Persistance.Repositories.OrderProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderPersService {
    private final OrderPersRepository repository;

    public OrderPersService(OrderPersRepository repository) {
        this.repository = repository;
    }

    public OrderPers save(OrderPers orderPers){
        return repository.save(orderPers);
    }

    public List<OrderPers> findByOrder(Orders order){
        return  repository.findByOrders(order);
    }
}
