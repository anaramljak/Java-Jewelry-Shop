package app.Services;

import app.Persistance.Entities.Orders;
import app.Persistance.Entities.Product;
import app.Persistance.Repositories.OrdersRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrdersService {
    private final OrdersRepository repository;

    public OrdersService(OrdersRepository ordersRepository) {
        this.repository = ordersRepository;
    }
    public List<Orders> getOrders() {

        return repository.findAll();
    }
    @Transactional
    public Optional<Orders> getOrderById(UUID id){
        return repository.findById(id);
    }
    @Transactional
    public void save(Orders orders){
        repository.save(orders);
    }

    public Page<Orders> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return repository.findAll(pageable);

    }
}
