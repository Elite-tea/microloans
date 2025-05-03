//package ru.hometask.view;
//
//import com.vaadin.flow.component.UI;
//import com.vaadin.flow.component.button.Button;
//import com.vaadin.flow.component.button.ButtonVariant;
//import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
//import com.vaadin.flow.component.datetimepicker.DateTimePicker;
//import com.vaadin.flow.component.dialog.Dialog;
//import com.vaadin.flow.component.formlayout.FormLayout;
//import com.vaadin.flow.component.grid.Grid;
//import com.vaadin.flow.component.html.H1;
//import com.vaadin.flow.component.html.H2;
//import com.vaadin.flow.component.html.Span;
//import com.vaadin.flow.component.icon.VaadinIcon;
//import com.vaadin.flow.component.notification.Notification;
//import com.vaadin.flow.component.notification.NotificationVariant;
//import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
//import com.vaadin.flow.component.orderedlayout.VerticalLayout;
//import com.vaadin.flow.component.textfield.BigDecimalField;
//import com.vaadin.flow.component.textfield.IntegerField;
//import com.vaadin.flow.component.textfield.TextField;
//import com.vaadin.flow.data.binder.Binder;
//import com.vaadin.flow.data.binder.ValidationException;
//import com.vaadin.flow.router.PageTitle;
//import com.vaadin.flow.router.Route;
//import com.vaadin.flow.spring.annotation.SpringComponent;
//import jakarta.annotation.security.RolesAllowed;
//import lombok.Data;
//import ru.hometask.dto.OldContractDto;
//import ru.hometask.dto.UpdateEmployeeDto;
//import ru.hometask.mappers.ContractMapper;
//import ru.hometask.services.*;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.util.List;
//
//@SpringComponent
//@PageTitle("Сотрудники")
//@Route(value = "employees")
//@RolesAllowed("ADMIN")
//@Data
//public class EmployeeUpdateView extends VerticalLayout {
//
//    // Компоненты UI
//    private Grid<UpdateEmployeeDto> grid;
//    private Binder<UpdateEmployeeDto> binder;
//
//    private final PowerOfAttorneyService powerOfAttorneyService;
//    private final IssuePointService issuePointService;
//    private final RoleService roleService;
//    private final EmployeeService employeeService;
//
//    /**
//     * Инициализация представления.
//     */
//    private void initView() {
//        configurePageLayout();
//        initializeGrid();
//        refreshGridData();
//        add(grid);
//    }
//
//    /**
//     * Настройка макета страницы.
//     */
//    private void configurePageLayout() {
//        add(new H1("Работа с сотрудниками"));
//        setAlignItems(Alignment.CENTER);
//        setSizeFull();
//    }
//
//    /**
//     * Инициализация и настройка таблицы сотрудников.
//     */
//    private void initializeGrid() {
//        grid = new Grid<>(UpdateEmployeeDto.class, false);
//        configureGridColumns();
//        grid.addItemClickListener(event -> showEmployeeDetails(event.getItem()));
//    }
//
//    /**
//     * Настройка колонок таблицы.
//     */
//    private void configureGridColumns() {
//        grid.addColumn(UpdateEmployeeDto::getId)
//                .setHeader("Табельный номер")
//                .setAutoWidth(true)
//                .setSortable(true);
//
//        grid.addColumn(UpdateEmployeeDto::getLogin)
//                .setHeader("Логин")
//                .setAutoWidth(true)
//                .setSortable(true);
//
//        grid.addColumn(UpdateEmployeeDto::getFullName)
//                .setHeader("Фамилия Имя")
//                .setAutoWidth(true)
//                .setSortable(true);
//
//        grid.addColumn(dto -> powerOfAttorneyService.getPowerOfAttorneyById(dto.getPowerOfAttorneyId()).getNumber())
//                .setHeader("Доверенность №")
//                .setAutoWidth(true)
//                .setSortable(true);
//
//        grid.addColumn(dto -> issuePointService.getIssuePoint(dto.getIssuePointId()).getName())
//                .setHeader("Точка выдачи")
//                .setAutoWidth(true)
//                .setSortable(true);
//
//        grid.addColumn(dto -> roleService.getRoleById(dto.getRoleId()).getName())
//                .setHeader("Роль")
//                .setAutoWidth(true)
//                .setSortable(true);
//    }
//
//    /**
//     * Обновление данных в таблице.
//     */
//    private void refreshGridData() {
//        List<UpdateEmployeeDto> contracts = employeeService.getAllEmployee();
//        grid.setItems(contracts);
//    }
//
//    /**
//     * Отображение деталей договора в диалоговом окне.
//     *
//     * @param employee DTO договора для отображения
//     */
//    private void showEmployeeDetails(UpdateEmployeeDto employee) {
//        Dialog dialog = createDialog();
//        binder = new Binder<>(UpdateEmployeeDto.class);
//
//        VerticalLayout dialogLayout = new VerticalLayout();
//        dialogLayout.setPadding(true);
//        dialogLayout.setSpacing(true);
//
//        H2 header = new H2("Договор №" + employee.getId());
//
//        // Создание компонентов диалога
//        FormLayout editForm = createEditForm(employee, dialog);
//        Span statusSpan = createStatusSpan(employee.getStatus().getName());
//        HorizontalLayout statusRow = createStatusRow(statusSpan);
//        HorizontalLayout buttonsLayout = createActionButtonsLayout(employee, dialog, statusSpan);
//
//        dialogLayout.add(header, editForm, statusRow, buttonsLayout);
//        dialog.add(dialogLayout);
//        dialog.open();
//    }
//
//    /**
//     * Создание формы редактирования договора.
//     *
//     * @param contract DTO договора
//     * @param dialog диалоговое окно
//     * @return настроенная форма
//     */
//    private FormLayout createEditForm(UpdateEmployeeDto contract, Dialog dialog) {
//        FormLayout form = new FormLayout();
//        form.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1));
//
//        // Создание полей формы
//        IntegerField amountField = new IntegerField("Табельный номер");
//        TextField clientField = new TextField("Логин");
//        TextField employeeField = new TextField("Фамилия Имя");
//        TextField dateOfIssueField = new TextField("Доверенность №");
//        TextField dateTermField = new TextField("Точка выдачи");
//        TextField issuePointField = new TextField("Роль");
//
//        // Настройка биндера для привязки данных
//        configureBinder(amountField, clientField, employeeField, dateOfIssueField, dateTermField, issuePointField);
//
//        // Кнопка сохранения
//        Button saveButton = createSaveButton(contract, dialog);
//
//        // Настройка доступности полей в зависимости от статуса
//        configureFieldsAccessibility(contract, amountField, clientField, employeeField,
//                dateOfIssueField, dateTermField, issuePointField);
//
//        // Добавление компонентов в форму
//        if (isContractClosed(contract)) {
//            form.add(amountField, clientField, employeeField,
//                    dateOfIssueField, dateTermField, issuePointField);
//        } else {
//            form.add(amountField, clientField, employeeField,
//                    dateOfIssueField, dateTermField, issuePointField,
//                    saveButton);
//        }
//
//        binder.readBean(contract);
//
//        return form;
//    }
//
//    /**
//     * Настройка биндера для привязки данных.
//     */
//    private void configureBinder(IntegerField amountField, TextField clientField,
//                                 TextField employeeField, TextField dateOfIssueField,
//                                 DateTimePicker dateTermField, TextField issuePointField) {
//        binder.forField(amountField)
//                .bind(UpdateEmployeeDto::getAmount,
//                        (dto, value) -> dto.setAmount(value != null ? value : BigDecimal.ZERO));
//
//        binder.forField(clientField)
//                .bind(dto -> dto.getClient().getFullName(), null); // Только для чтения
//
//        binder.forField(employeeField)
//                .bind(dto -> dto.getEmployee().getFullName(), null); // Только для чтения
//
//        binder.forField(dateOfIssueField)
//                .bind(dto -> dto.getDateOfIssue().toString(), null); // Только для чтения
//
//        binder.forField(dateTermField)
//                .bind(UpdateEmployeeDto::getDateTerm,
//                        (dto, value) -> dto.setDateTerm(value != null ? value : LocalDateTime.now().plusMonths(1)));
//
//        binder.forField(issuePointField)
//                .bind(dto -> dto.getIssuePoint().getName(), null); // Только для чтения
//    }
//
//    /**
//     * Создание диалогового окна.
//     */
//    private Dialog createDialog() {
//        Dialog dialog = new Dialog();
//        dialog.setCloseOnEsc(true);
//        dialog.setCloseOnOutsideClick(true);
//        dialog.setWidth("600px");
//        return dialog;
//    }
//
//}
