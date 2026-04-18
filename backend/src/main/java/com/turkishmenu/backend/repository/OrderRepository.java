package com.turkishmenu.backend.repository;

import com.turkishmenu.backend.model.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findAllByOrderByCreatedAtDesc();
    List<OrderEntity> findByArchivedFalseOrderByCreatedAtDesc();
}
