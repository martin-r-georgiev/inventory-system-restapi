package org.martin.inventory;

import org.martin.inventory.repository.ItemRepository;
import org.martin.inventory.service.ItemManager;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class ApplicationBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(ItemManager.class).to(ItemManager.class);
        bind(ItemRepository.class).to(ItemRepository.class);
    }
}

