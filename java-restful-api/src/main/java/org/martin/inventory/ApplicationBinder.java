package org.martin.inventory;

import org.martin.inventory.model.ItemHistoryEntry;
import org.martin.inventory.repository.HistoryEntryRepository;
import org.martin.inventory.repository.ItemRepository;
import org.martin.inventory.repository.UserRepository;
import org.martin.inventory.repository.WarehouseRepository;
import org.martin.inventory.service.HistoryEntryManager;
import org.martin.inventory.service.ItemManager;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.martin.inventory.service.UserManager;
import org.martin.inventory.service.WarehouseManager;
import org.martin.inventory.utils.JWTUtil;

public class ApplicationBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(ItemManager.class).to(ItemManager.class);
        bind(UserManager.class).to(UserManager.class);
        bind(WarehouseManager.class).to(WarehouseManager.class);
        bind(HistoryEntryManager.class).to(HistoryEntryManager.class);
        bind(ItemRepository.class).to(ItemRepository.class);
        bind(UserRepository.class).to(UserRepository.class);
        bind(WarehouseRepository.class).to(WarehouseRepository.class);
        bind(HistoryEntryRepository.class).to(HistoryEntryRepository.class);
        bind(JWTUtil.class).to(JWTUtil.class);
    }
}

