//package ru.hometask.view;
//
//import com.vaadin.flow.component.button.Button;
//import com.vaadin.flow.component.button.ButtonVariant;
//import com.vaadin.flow.component.combobox.ComboBox;
//import com.vaadin.flow.component.dialog.Dialog;
//import com.vaadin.flow.component.formlayout.FormLayout;
//import com.vaadin.flow.component.grid.Grid;
//import com.vaadin.flow.component.notification.Notification;
//import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
//import com.vaadin.flow.component.orderedlayout.VerticalLayout;
//import com.vaadin.flow.component.textfield.PasswordField;
//import com.vaadin.flow.component.textfield.TextField;
//import com.vaadin.flow.data.binder.Binder;
//import com.vaadin.flow.data.binder.ValidationException;
//import com.vaadin.flow.router.PageTitle;
//import com.vaadin.flow.router.Route;
//import com.vaadin.flow.spring.annotation.SpringComponent;
//import jakarta.annotation.security.RolesAllowed;
//import org.springframework.beans.factory.annotation.Autowired;
//import ru.hometask.dto.OldEmployeeDto;
//import ru.hometask.dto.UpdateEmployeeDto;
//import ru.hometask.entities.IssuePoint;
//import ru.hometask.entities.Role;
//import ru.hometask.mappers.EmployeeMapper;
//import ru.hometask.services.EmployeeService;
//import ru.hometask.services.IssuePointService;
//import ru.hometask.services.RoleService;
//
//import java.util.List;
//import java.util.Objects;
//
//@PageTitle("Редактирование сотрудников")
//@Route(value = "employees")
//@RolesAllowed("ADMIN")
//public class EmployeeView extends VerticalLayout {
//
//    private final EmployeeService employeeService;
//    private final IssuePointService issuePointService;
//    private final RoleService roleService;
//    private final EmployeeMapper employeeMapper;
//
//    private final Grid<OldEmployeeDto> grid = new Grid<>();
//    private final Binder<UpdateEmployeeDto> binder = new Binder<>();
//    private final Dialog editDialog = new Dialog();
//
//    private final TextField fullNameField = new TextField("ФИО");
//    private final TextField loginField = new TextField("Логин");
//    private final PasswordField passwordField = new PasswordField("Новый пароль");
//    private final PasswordField confirmPasswordField = new PasswordField("Подтвердите пароль");
//    private final ComboBox<Role> roleComboBox = new ComboBox<>("Роль");
//    private final ComboBox<IssuePoint> issuePointComboBox = new ComboBox<>("Точка выдачи");
//
//    private final Button saveButton = new Button("Сохранить");
//    private final Button cancelButton = new Button("Отмена");
//
//    @Autowired
//    public EmployeeView(EmployeeService employeeService,
//                        IssuePointService issuePointService,
//                        RoleService roleService,
//                        EmployeeMapper employeeMapper) {
//        this.employeeService = employeeService;
//        this.issuePointService = issuePointService;
//        this.roleService = roleService;
//        this.employeeMapper = employeeMapper;
//
//        setSizeFull();
//        configureGrid();
//        configureDialog();
//        updateGrid();
//
//        add(grid);
//    }
//
//    private void configureGrid() {
//        grid.addColumn(OldEmployeeDto::getFullName).setHeader("ФИО").setSortable(true);
//        grid.addColumn(OldEmployeeDto::getLogin).setHeader("Логин").setSortable(true);
//
//        grid.addColumn(e -> {
//            if (e.getRoleId() != null) {
//                Role role = roleService.getRoleById(e.getRoleId());
//                return role != null ? role.getName() : "";
//            }
//            return "";
//        }).setHeader("Роль").setSortable(true);
//
//        grid.addColumn(e -> {
//            if (e.getIssuePointId() != null) {
//                IssuePoint issuePoint = issuePointService.getForUpdateIssuePoint(e.getIssuePointId());
//                return issuePoint != null ? issuePoint.getName() : "";
//            }
//            return "";
//        }).setHeader("Точка выдачи").setSortable(true);
//
//        grid.asSingleSelect().addValueChangeListener(event -> {
//            if (event.getValue() != null) {
//                openEditDialog(event.getValue());
//            }
//        });
//
//        // Добавляем обработчик двойного клика
//        grid.addItemDoubleClickListener(event -> {
//            if (event.getItem() != null) {
//                openEditDialog(event.getItem());
//            }
//        });
//    }
//
//    private void configureDialog() {
//        editDialog.setHeaderTitle("Редактирование сотрудника");
//        editDialog.setWidth("600px");
//
//        // Настройка ComboBox для ролей и точек выдачи
//        roleComboBox.setItems(roleService.getAllRoles());
//        roleComboBox.setItemLabelGenerator(Role::getName);
//
//        issuePointComboBox.setItems(issuePointService.getAllIssuePoint());
//        issuePointComboBox.setItemLabelGenerator(IssuePoint::getName);
//
//        // Настройка валидации пароля
//        passwordField.setPlaceholder("Оставьте пустым, чтобы не менять");
//        confirmPasswordField.setPlaceholder("Повторите новый пароль");
//
//        binder.forField(fullNameField)
//                .asRequired("ФИО обязательно")
//                .bind(UpdateEmployeeDto::getFullName, UpdateEmployeeDto::setFullName);
//
//        binder.forField(loginField)
//                .asRequired("Логин обязателен")
//                .bind(UpdateEmployeeDto::getLogin, UpdateEmployeeDto::setLogin);
//
//        binder.forField(roleComboBox)
//                .asRequired("Роль обязательна")
//                .withConverter(
//                        Role::getId,
//                        roleService::getRoleById)
//                .bind(UpdateEmployeeDto::getRoleId, UpdateEmployeeDto::setRoleId);
//
//        binder.forField(issuePointComboBox)
//                .asRequired("Точка выдачи обязательна")
//                .withConverter(
//                        IssuePoint::getId,
//                        issuePointService::getForUpdateIssuePoint)
//                .bind(UpdateEmployeeDto::getIssuePointId, UpdateEmployeeDto::setIssuePointId);
//
//        // Настройка кнопок
//        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
//        saveButton.addClickListener(event -> saveEmployee());
//
//        cancelButton.addClickListener(event -> {
//            editDialog.close();
//            grid.deselectAll();
//        });
//
//        HorizontalLayout buttonLayout = new HorizontalLayout(saveButton, cancelButton);
//        buttonLayout.setJustifyContentMode(JustifyContentMode.END);
//
//        FormLayout formLayout = new FormLayout();
//        formLayout.add(
//                fullNameField,
//                loginField,
//                passwordField,
//                confirmPasswordField,
//                roleComboBox,
//                issuePointComboBox
//        );
//        formLayout.setResponsiveSteps(
//                new FormLayout.ResponsiveStep("0", 1),
//                new FormLayout.ResponsiveStep("500px", 2)
//        );
//        formLayout.setColspan(issuePointComboBox, 2);
//
//        editDialog.add(formLayout, buttonLayout);
//    }
//
//    private void openEditDialog(OldEmployeeDto employee) {
//        UpdateEmployeeDto dto = new UpdateEmployeeDto();
//        dto.setId(employee.getId());
//        dto.setFullName(employee.getFullName());
//        dto.setLogin(employee.getLogin());
//        dto.setRoleId(employee.getRoleId());
//        dto.setIssuePointId(employee.getIssuePointId());
//
//        try {
//            binder.readBean(dto);
//
//            // Устанавливаем значения в ComboBox'ы
//            if (employee.getRoleId() != null) {
//                roleComboBox.setValue(roleService.getRoleById(employee.getRoleId()));
//            }
//            if (employee.getIssuePointId() != null) {
//                issuePointComboBox.setValue(issuePointService.getForUpdateIssuePoint(employee.getIssuePointId()));
//            }
//
//            // Очищаем поля паролей
//            passwordField.clear();
//            confirmPasswordField.clear();
//
//            editDialog.open();
//        } catch (Exception e) {
//            Notification.show("Ошибка при открытии формы: " + e.getMessage(), 3000, Notification.Position.MIDDLE);
//        }
//    }
//
//    private void saveEmployee() {
//        UpdateEmployeeDto dto = new UpdateEmployeeDto();
//        try {
//            binder.writeBean(dto);
//
//            // Проверка паролей
//            if (!passwordField.getValue().isEmpty()) {
//                if (!Objects.equals(passwordField.getValue(), confirmPasswordField.getValue())) {
//                    Notification.show("Пароли не совпадают", 3000, Notification.Position.MIDDLE);
//                    return;
//                }
//                dto.setPassword(passwordField.getValue());
//            }
//
//            employeeService.updateEmployee(dto);
//            Notification.show("Пользователь изменён", 3000, Notification.Position.MIDDLE);
//            updateGrid();
//            editDialog.close();
//        } catch (ValidationException e) {
//            Notification.show("Ошибка валидации: " + e.getMessage(), 3000, Notification.Position.MIDDLE);
//        } catch (Exception e) {
//            Notification.show("Ошибка изменения пользователя: " + e.getMessage(), 3000, Notification.Position.MIDDLE);
//        }
//    }
//
//    private void updateGrid() {
//        List<OldEmployeeDto> employees = employeeService.getAllEmployee();
//        grid.setItems(employees);
//    }
//}