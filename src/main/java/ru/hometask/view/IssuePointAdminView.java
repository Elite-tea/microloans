package ru.hometask.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.security.AuthenticationContext;
import jakarta.annotation.security.PermitAll;
import org.springframework.security.core.GrantedAuthority;
import ru.hometask.config.EmployeeUserDetails;
import ru.hometask.dto.OldIssuePointDto;
import ru.hometask.exception.AccessDeniedView;
import ru.hometask.mappers.IssuePointMapper;
import ru.hometask.services.IssuePointService;

import java.util.List;

@PageTitle("Сотрудники")
@Route("admin/issue-point")
@PermitAll
public class IssuePointAdminView extends VerticalLayout implements BeforeEnterObserver {
    private final AuthenticationContext authContext;
    private final IssuePointService issuePointService;
    private final IssuePointMapper issuePointMapper;

    private Grid<OldIssuePointDto> grid;
    private Binder<OldIssuePointDto> binder;

    public IssuePointAdminView (AuthenticationContext authContext,
                                IssuePointService issuePointService,
                                IssuePointMapper issuePointMapper) {
        this.authContext = authContext;
        this.issuePointService = issuePointService;
        this.issuePointMapper = issuePointMapper;
        initView();
    }

    private void initView() {
        configurePageLayout();
        initializeGrid();
        refreshGridData();
        add(grid);
    }

    private void configurePageLayout() {
        add(new H1("Работа с точками выдачи"));
        setAlignItems(Alignment.CENTER);
        setSizeFull();
    }

    private void initializeGrid() {
        grid = new Grid<>(OldIssuePointDto.class, false);
        configureGridColumns();
        grid.addItemClickListener(event -> showEmployeeDetails(event.getItem()));
    }

    private void showEmployeeDetails(OldIssuePointDto oldIssuePointDto) {
        Dialog dialog = createDialog();
        binder = new Binder<>(OldIssuePointDto.class);

        VerticalLayout dialogLayout = new VerticalLayout();
        dialogLayout.setPadding(true);
        dialogLayout.setSpacing(true);

        H2 header = new H2("Сотрудник " + oldIssuePointDto.getId());
        FormLayout editForm = createEditForm(oldIssuePointDto, dialog);

        dialogLayout.add(header, editForm);
        dialog.add(dialogLayout);
        dialog.open();
    }

    private FormLayout createEditForm(OldIssuePointDto oldIssuePointDto, Dialog dialog) {
        FormLayout form = new FormLayout();
        form.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1));

        // Поле табельного номера
        TextField idField = new TextField("Номер точки");
        idField.setReadOnly(true);
        idField.setValue(oldIssuePointDto.getId() != null ? oldIssuePointDto.getId().toString() : "N/A");

        // Поля только для чтения
        TextField pointName = new TextField("Название");
        pointName.setValue(oldIssuePointDto.getName() != null ? oldIssuePointDto.getName() : "N/A");

        TextField issuePointField = new TextField("Адрес");
        issuePointField.setReadOnly(true);
        issuePointField.setValue(oldIssuePointDto.getAddress() != null ? oldIssuePointDto.getAddress() : "N/A");


        binder.forField(pointName)
                .asRequired("Название обязательно для заполнения")
                .bind(OldIssuePointDto::getName, OldIssuePointDto::setName);

        // Кнопки
        Button saveButton = new Button("Сохранить", VaadinIcon.CHECK.create());
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        saveButton.addClickListener(e -> saveEmployeeChanges(oldIssuePointDto, dialog));

        Button cancelButton = new Button("Отмена", e -> dialog.close());
        cancelButton.addThemeVariants(ButtonVariant.LUMO_ERROR);

        HorizontalLayout buttonsLayout = new HorizontalLayout(saveButton, cancelButton);
        buttonsLayout.setSpacing(true);

        form.add(idField, pointName, issuePointField, buttonsLayout);
        binder.readBean(oldIssuePointDto);

        return form;
    }

    private void saveEmployeeChanges(OldIssuePointDto oldIssuePointDto, Dialog dialog) {
        try {
            if (binder.writeBeanIfValid(oldIssuePointDto)) {
                issuePointService.updateIssuePoint(oldIssuePointDto);
                refreshGridData();
                showNotification("Данные точки обновлены", NotificationVariant.LUMO_SUCCESS);
                dialog.close();
            }
        } catch (Exception ex) {
            showNotification("Ошибка при сохранении: " + ex.getMessage(),
                    NotificationVariant.LUMO_ERROR);
        }
    }

    private void showNotification(String message, NotificationVariant variant) {
        Notification.show(message, 3000, Notification.Position.MIDDLE)
                .addThemeVariants(variant);
    }

    private Dialog createDialog() {
        Dialog dialog = new Dialog();
        dialog.setCloseOnEsc(true);
        dialog.setCloseOnOutsideClick(true);
        dialog.setWidth("600px");
        return dialog;
    }

    private void refreshGridData() {
        List<OldIssuePointDto> oldIssuePointDto = issuePointMapper.oldIssuePointListMapper(issuePointService.getAllIssuePoint());
        grid.setItems(oldIssuePointDto);
    }

    private void configureGridColumns() {
        grid.addColumn(OldIssuePointDto::getId)
                .setHeader("Номер точки")
                .setAutoWidth(true)
                .setSortable(true);

        grid.addColumn(OldIssuePointDto::getName)
                .setHeader("Название")
                .setAutoWidth(true)
                .setSortable(true);

        grid.addColumn(OldIssuePointDto::getAddress)
                .setHeader("Адрес")
                .setAutoWidth(true)
                .setSortable(true);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        authContext.getAuthenticatedUser(EmployeeUserDetails.class).ifPresent(user -> {
            if (user.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .noneMatch("ADMIN"::equals)) {
                event.rerouteTo(AccessDeniedView.class);
            }
        });
    }

}
