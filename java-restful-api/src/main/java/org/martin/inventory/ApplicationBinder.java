package org.martin.inventory;

import org.martin.inventory.repository.IRepository;
import org.martin.inventory.repository.ItemRepository;
import org.martin.inventory.repository.UserRepository;
import org.martin.inventory.service.ItemManager;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.martin.inventory.service.UserManager;
import org.martin.inventory.utils.JWTUtil;

public class ApplicationBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(ItemManager.class).to(ItemManager.class);
        bind(UserManager.class).to(UserManager.class);
        bind(ItemRepository.class).to(ItemRepository.class);
        bind(UserRepository.class).to(UserRepository.class);
        bind(JWTUtil.class).to(JWTUtil.class);
    }
}
