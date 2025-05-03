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
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.security.AuthenticationContext;
import jakarta.annotation.security.PermitAll;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.hometask.config.EmployeeUserDetails;
import ru.hometask.dto.UpdateEmployeeDto;
import ru.hometask.entities.IssuePoint;
import ru.hometask.entities.PowerOfAttorney;
import ru.hometask.entities.Role;
import ru.hometask.exception.AccessDeniedView;
import ru.hometask.mappers.EmployeeMapper;
import ru.hometask.services.EmployeeService;
import ru.hometask.services.IssuePointService;
import ru.hometask.services.PowerOfAttorneyService;
import ru.hometask.services.RoleService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@PageTitle("Сотрудники")
@Route("admin/employees")
@PermitAll
public class EmployeeUpdateView extends VerticalLayout implements BeforeEnterObserver {

    private final transient EmployeeService employeeService;
    private final transient PowerOfAttorneyService powerOfAttorneyService;
    private final transient IssuePointService issuePointService;
    private final transient RoleService roleService;
    private final transient EmployeeMapper employeeMapper;
    private final transient PasswordEncoder passwordEncoder;
    private final AuthenticationContext authContext;

    private Grid<UpdateEmployeeDto> grid;
    private Binder<UpdateEmployeeDto> binder;

    private Map<Long, String> poaMap;
    private Map<Long, String> issuePointMap;
    private Map<Long, String> roleMap;

    public EmployeeUpdateView(EmployeeService employeeService,
                              PowerOfAttorneyService powerOfAttorneyService,
                              IssuePointService issuePointService,
                              RoleService roleService,
                              EmployeeMapper employeeMapper,
                              PasswordEncoder passwordEncoder,
                              AuthenticationContext authContext) {
        this.employeeService = employeeService;
        this.powerOfAttorneyService = powerOfAttorneyService;
        this.issuePointService = issuePointService;
        this.roleService = roleService;
        this.employeeMapper = employeeMapper;
        this.passwordEncoder = passwordEncoder;
        this.authContext = authContext;

        loadReferenceData();
        initView();
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

    private void loadReferenceData() {
        this.poaMap = powerOfAttorneyService.getAllPowerOfAttorneys().stream()
                .collect(Collectors.toMap(
                        PowerOfAttorney::getId,
                        PowerOfAttorney::getNumber,
                        (existing, replacement) -> existing
                ));

        this.issuePointMap = issuePointService.getAllIssuePoint().stream()
                .collect(Collectors.toMap(
                        IssuePoint::getId,
                        IssuePoint::getName,
                        (existing, replacement) -> existing
                ));

        this.roleMap = roleService.getAllRoles().stream()
                .collect(Collectors.toMap(
                        Role::getId,
                        Role::getName,
                        (existing, replacement) -> existing
                ));
    }

    private void initView() {
        configurePageLayout();
        initializeGrid();
        refreshGridData();
        add(grid);
    }

    private void configurePageLayout() {
        add(new H1("Работа с сотрудниками"));
        setAlignItems(Alignment.CENTER);
        setSizeFull();
    }

    private void initializeGrid() {
        grid = new Grid<>(UpdateEmployeeDto.class, false);
        configureGridColumns();
        grid.addItemClickListener(event -> showEmployeeDetails(event.getItem()));
    }

    private void configureGridColumns() {
        grid.addColumn(UpdateEmployeeDto::getId)
                .setHeader("Табельный номер")
                .setAutoWidth(true)
                .setSortable(true);

        grid.addColumn(UpdateEmployeeDto::getLogin)
                .setHeader("Логин")
                .setAutoWidth(true)
                .setSortable(true);

        grid.addColumn(UpdateEmployeeDto::getFullName)
                .setHeader("Фамилия Имя")
                .setAutoWidth(true)
                .setSortable(true);

        grid.addColumn(dto -> getPoaDisplayValue(dto.getPowerOfAttorneyId()))
                .setHeader("Доверенность №")
                .setAutoWidth(true)
                .setSortable(true);

        grid.addColumn(dto -> getIssuePointDisplayValue(dto.getIssuePointId()))
                .setHeader("Точка выдачи")
                .setAutoWidth(true)
                .setSortable(true);

        grid.addColumn(dto -> getRoleDisplayValue(dto.getRoleId()))
                .setHeader("Роль")
                .setAutoWidth(true)
                .setSortable(true);
    }

    private String getPoaDisplayValue(Long poaId) {
        return poaId != null ? poaMap.getOrDefault(poaId, "N/A") : "N/A";
    }

    private String getIssuePointDisplayValue(Long issuePointId) {
        return issuePointId != null ? issuePointMap.getOrDefault(issuePointId, "N/A") : "N/A";
    }

    private String getRoleDisplayValue(Long roleId) {
        return roleId != null ? roleMap.getOrDefault(roleId, "N/A") : "N/A";
    }

    private void refreshGridData() {
        List<UpdateEmployeeDto> employees = employeeMapper.toUpdateEmployeeDtoList(employeeService.getAllEmployee());
        grid.setItems(employees);
    }

    private void showEmployeeDetails(UpdateEmployeeDto employee) {
        Dialog dialog = createDialog();
        binder = new Binder<>(UpdateEmployeeDto.class);

        VerticalLayout dialogLayout = new VerticalLayout();
        dialogLayout.setPadding(true);
        dialogLayout.setSpacing(true);

        H2 header = new H2("Сотрудник " + employee.getId());
        FormLayout editForm = createEditForm(employee, dialog);

        dialogLayout.add(header, editForm);
        dialog.add(dialogLayout);
        dialog.open();
    }

    private FormLayout createEditForm(UpdateEmployeeDto employee, Dialog dialog) {
        FormLayout form = new FormLayout();
        form.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1));

        // Поле табельного номера
        TextField idField = new TextField("Табельный номер");
        idField.setReadOnly(true);
        idField.setValue(employee.getId() != null ? employee.getId().toString() : "N/A");

        // Основные поля
        TextField loginField = new TextField("Логин");
        TextField fullNameField = new TextField("Фамилия Имя");

        // Поля только для чтения
        TextField poaField = new TextField("Доверенность №");
        poaField.setReadOnly(true);
        poaField.setValue(getPoaDisplayValue(employee.getPowerOfAttorneyId()));

        TextField issuePointField = new TextField("Точка выдачи");
        issuePointField.setReadOnly(true);
        issuePointField.setValue(getIssuePointDisplayValue(employee.getIssuePointId()));

        TextField roleField = new TextField("Роль");
        roleField.setReadOnly(true);
        roleField.setValue(getRoleDisplayValue(employee.getRoleId()));

        // Поля для пароля
        PasswordField passwordField = new PasswordField("Новый пароль");
        PasswordField confirmPasswordField = new PasswordField("Подтвердите пароль");

        // Валидация и биндинг
        binder.forField(loginField)
                .asRequired("Логин обязателен для заполнения")
                .bind(UpdateEmployeeDto::getLogin, UpdateEmployeeDto::setLogin);

        binder.forField(fullNameField)
                .asRequired("ФИО обязательно для заполнения")
                .bind(UpdateEmployeeDto::getFullName, UpdateEmployeeDto::setFullName);

        binder.forField(passwordField)
                .withValidator(password -> password.isEmpty() || password.length() >= 6,
                        "Пароль должен содержать минимум 6 символов")
                .bind(UpdateEmployeeDto::getPassword, UpdateEmployeeDto::setPassword);

        binder.forField(confirmPasswordField)
                .withValidator(confirmPassword ->
                                confirmPassword.isEmpty() ||
                                        confirmPassword.equals(passwordField.getValue()),
                        "Пароли не совпадают")
                .bind(dto -> "", (dto, value) -> {});

        // Кнопки
        Button saveButton = new Button("Сохранить", VaadinIcon.CHECK.create());
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        saveButton.addClickListener(e -> saveEmployeeChanges(employee, dialog, passwordField));

        Button cancelButton = new Button("Отмена", e -> dialog.close());
        cancelButton.addThemeVariants(ButtonVariant.LUMO_ERROR);

        HorizontalLayout buttonsLayout = new HorizontalLayout(saveButton, cancelButton);
        buttonsLayout.setSpacing(true);

        form.add(idField, loginField, fullNameField, poaField, issuePointField, roleField,
                passwordField, confirmPasswordField, buttonsLayout);
        binder.readBean(employee);

        return form;
    }

    private void saveEmployeeChanges(UpdateEmployeeDto employee, Dialog dialog, PasswordField passwordField) {
        try {
            if (binder.writeBeanIfValid(employee)) {
                // Шифруем пароль, если он был изменен
                if (!passwordField.isEmpty()) {
                    employee.setPassword(passwordEncoder.encode(passwordField.getValue()));
                }

                employeeService.updateEmployee(employee);
                refreshGridData();
                showNotification("Данные сотрудника обновлены", NotificationVariant.LUMO_SUCCESS);
                dialog.close();
            }
        } catch (Exception ex) {
            showNotification("Ошибка при сохранении: " + ex.getMessage(),
                    NotificationVariant.LUMO_ERROR);
        }
    }

    private Dialog createDialog() {
        Dialog dialog = new Dialog();
        dialog.setCloseOnEsc(true);
        dialog.setCloseOnOutsideClick(true);
        dialog.setWidth("600px");
        return dialog;
    }

    private void showNotification(String message, NotificationVariant variant) {
        Notification.show(message, 3000, Notification.Position.MIDDLE)
                .addThemeVariants(variant);
    }
}