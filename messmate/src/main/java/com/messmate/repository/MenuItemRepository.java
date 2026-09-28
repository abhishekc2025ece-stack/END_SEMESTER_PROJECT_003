package com.messmate.repository;

import com.messmate.entity.MenuItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuItemRepository
        extends JpaRepository<MenuItemEntity, Long> {

}
