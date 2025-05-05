package ru.hometask;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.AppShellSettings;


public class AppConfig implements AppShellConfigurator {
    @Override
    public void configurePage(AppShellSettings settings) {
        // Настройки по умолчанию (Аннотации хз почему не работают)
        settings.setPageTitle("Сервис микрозаймов");
    }
}