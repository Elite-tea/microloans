package ru.hometask.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.security.AuthenticationContext;
import jakarta.annotation.security.PermitAll;
import org.springframework.security.core.GrantedAuthority;
import ru.hometask.config.EmployeeUserDetails;

@PageTitle(value = "Главная")
@Route("") // Главное представление
@PermitAll
public class MainView extends VerticalLayout{

    public MainView(AuthenticationContext authContext) {

        // Заголовок
        add(new H1("Система микрозаймов"));
        // Кнопка для перехода к ContractsView
        Button issuePointUpdateButton = new Button("Редактировать точки выдачи", event ->
                UI.getCurrent().navigate(IssuePointAdminView.class));


        Button adminReportButton = new Button("Сводный отчет", event ->
                UI.getCurrent().navigate(AdminReportView.class));

        Button updateEmployeeButton = new Button("Редактировать сотрудников", event ->
                UI.getCurrent().navigate(EmployeeUpdateView.class));

        Button clientButton = new Button("Создать новый договор", event ->
                UI.getCurrent().navigate(NewContractView.class));

        Button reportContractButton = new Button("Работа с договорами", event ->
                UI.getCurrent().navigate(ContractsView.class));

        // Дополнительная стилизация кнопки
        issuePointUpdateButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        updateEmployeeButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        adminReportButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        clientButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        reportContractButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        setAlignItems(Alignment.CENTER);

        Tabs tabs = new Tabs();
        Tab tab1 = new Tab("Выдачи/Клиенты");
        tabs.add(tab1);
        authContext.getAuthenticatedUser(EmployeeUserDetails.class).ifPresent(user -> {
            if (user.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .noneMatch("USER"::equals)) {

                    Tab tab2 = new Tab("Панель администратора");
                    tabs.add(tab2);
            }
        });


        Div content = new Div();

        HorizontalLayout buttonsLayout = new HorizontalLayout();
        buttonsLayout.setSpacing(true); // Добавляет отступ между элементами
        buttonsLayout.add(clientButton, reportContractButton);

        content.add(buttonsLayout);

// Переключаем контент при выборе вкладки
        tabs.addSelectedChangeListener(event -> {
            content.removeAll();
            if (event.getSelectedTab() == tab1) {
                content.add(buttonsLayout);
            } else {
                HorizontalLayout buttonsLayoutAdmin = new HorizontalLayout();
                buttonsLayoutAdmin.setSpacing(true); // Добавляет отступ между элементами
                buttonsLayoutAdmin.add(issuePointUpdateButton, updateEmployeeButton, adminReportButton);
                content.add(buttonsLayoutAdmin);
            }
        });

        add(tabs, content);
}
}