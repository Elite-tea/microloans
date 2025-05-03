package ru.hometask.exception;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import jakarta.annotation.security.PermitAll;
import ru.hometask.view.MainView;

@Route("access-denied")
@PageTitle("Доступ запрещен")
@PermitAll
public class AccessDeniedView extends VerticalLayout {

    public AccessDeniedView() {
        add(new H1("Доступ запрещен"),
                new Paragraph("У вас недостаточно прав для просмотра этой страницы"),
                new RouterLink("На главную", MainView.class));
    }
}