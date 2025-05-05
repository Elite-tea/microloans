package ru.hometask.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import ru.hometask.dto.OldContractDto;
import ru.hometask.mappers.ContractMapper;
import ru.hometask.services.ContractService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Представление для работы с договорами.
 * Отображает список договоров и предоставляет функционал для их просмотра, редактирования и закрытия.
 */
@PageTitle("Договора")
@Route("contracts")
@PermitAll
public class ContractsView extends VerticalLayout {

    // Константы для статусов договоров
    private static final String CLOSED_STATUS = "CLOSE";
    private static final int CLOSED_STATUS_ID = 2;

    // Сервисы и зависимости
    private final transient ContractService contractService;
    private final transient ContractMapper contractMapper;

    // Компоненты UI
    private Grid<OldContractDto> grid;
    private Binder<OldContractDto> binder;

    /**
     * Конструктор с внедрением зависимостей.
     *
     * @param contractService сервис для работы с договорами
     * @param contractMapper маппер для преобразования DTO
     */
    public ContractsView(ContractService contractService, ContractMapper contractMapper) {
        this.contractService = contractService;
        this.contractMapper = contractMapper;
        initView();
    }

    /**
     * Инициализация представления.
     */
    private void initView() {
        configurePageLayout();
        initializeGrid();
        refreshGridData();
        add(grid);
    }

    /**
     * Настройка макета страницы.
     */
    private void configurePageLayout() {
        add(new H1("Работа с договорами"));
        setAlignItems(Alignment.CENTER);
        setSizeFull();
    }

    /**
     * Инициализация и настройка таблицы договоров.
     */
    private void initializeGrid() {
        grid = new Grid<>(OldContractDto.class, false);
        configureGridColumns();
        grid.addItemClickListener(event -> showContractDetails(event.getItem()));
    }

    /**
     * Настройка колонок таблицы.
     */
    private void configureGridColumns() {
        grid.addColumn(OldContractDto::getId)
                .setHeader("Номер договора")
                .setAutoWidth(true)
                .setSortable(true);

        grid.addColumn(OldContractDto::getAmount)
                .setHeader("Сумма")
                .setAutoWidth(true)
                .setSortable(true);

        grid.addColumn(dto -> dto.getClient().getFullName())
                .setHeader("Клиент")
                .setAutoWidth(true)
                .setSortable(true);

        grid.addColumn(dto -> dto.getEmployee().getFullName())
                .setHeader("Сотрудник")
                .setAutoWidth(true)
                .setSortable(true);

        grid.addColumn(OldContractDto::getDateOfIssue)
                .setHeader("Дата выдачи")
                .setAutoWidth(true)
                .setSortable(true);

        grid.addColumn(OldContractDto::getDateTerm)
                .setHeader("Срок погашения")
                .setAutoWidth(true)
                .setSortable(true);

        grid.addColumn(dto -> dto.getIssuePoint().getName())
                .setHeader("Точка выдачи")
                .setAutoWidth(true)
                .setSortable(true);

        grid.addColumn(dto -> dto.getStatus().getName())
                .setHeader("Статус договора")
                .setAutoWidth(true)
                .setSortable(true);
    }

    /**
     * Обновление данных в таблице.
     */
    private void refreshGridData() {
        List<OldContractDto> contracts = contractService.getAllContract();
        grid.setItems(contracts);
    }

    /**
     * Отображение деталей договора в диалоговом окне.
     *
     * @param contract DTO договора для отображения
     */
    private void showContractDetails(OldContractDto contract) {
        Dialog dialog = createDialog();
        binder = new Binder<>(OldContractDto.class);

        VerticalLayout dialogLayout = new VerticalLayout();
        dialogLayout.setPadding(true);
        dialogLayout.setSpacing(true);

        H2 header = new H2("Договор №" + contract.getId());

        // Создание компонентов диалога
        FormLayout editForm = createEditForm(contract, dialog);
        Span statusSpan = createStatusSpan(contract.getStatus().getName());
        HorizontalLayout statusRow = createStatusRow(statusSpan);
        HorizontalLayout buttonsLayout = createActionButtonsLayout(contract, dialog, statusSpan);

        dialogLayout.add(header, editForm, statusRow, buttonsLayout);
        dialog.add(dialogLayout);
        dialog.open();
    }

    /**
     * Создание формы редактирования договора.
     *
     * @param contract DTO договора
     * @param dialog диалоговое окно
     * @return настроенная форма
     */
    private FormLayout createEditForm(OldContractDto contract, Dialog dialog) {
        FormLayout form = new FormLayout();
        form.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1));

        // Создание полей формы
        BigDecimalField amountField = new BigDecimalField("Сумма");
        TextField clientField = new TextField("Клиент");
        TextField employeeField = new TextField("Сотрудник");
        TextField dateOfIssueField = new TextField("Дата выдачи");
        DateTimePicker dateTermField = new DateTimePicker("Срок погашения");
        TextField issuePointField = new TextField("Точка выдачи");

        // Настройка биндера для привязки данных
        configureBinder(amountField, clientField, employeeField, dateOfIssueField, dateTermField, issuePointField);

        // Кнопка сохранения
        Button saveButton = createSaveButton(contract, dialog);

        // Настройка доступности полей в зависимости от статуса
        configureFieldsAccessibility(contract, amountField, clientField, employeeField,
                dateOfIssueField, dateTermField, issuePointField);

        // Добавление компонентов в форму
        if (isContractClosed(contract)) {
            form.add(amountField, clientField, employeeField,
                    dateOfIssueField, dateTermField, issuePointField);
        } else {
            form.add(amountField, clientField, employeeField,
                    dateOfIssueField, dateTermField, issuePointField,
                    saveButton);
        }

        binder.readBean(contract);

        return form;
    }

    /**
     * Проверка, закрыт ли договор.
     */
    private boolean isContractClosed(OldContractDto contract) {
        return contract.getStatus().getId() == CLOSED_STATUS_ID;
    }

    /**
     * Настройка биндера для привязки данных.
     */
    private void configureBinder(BigDecimalField amountField, TextField clientField,
                                 TextField employeeField, TextField dateOfIssueField,
                                 DateTimePicker dateTermField, TextField issuePointField) {
        binder.forField(amountField)
                .bind(OldContractDto::getAmount,
                        (dto, value) -> dto.setAmount(value != null ? value : BigDecimal.ZERO));

        binder.forField(clientField)
                .bind(dto -> dto.getClient().getFullName(), null); // Только для чтения

        binder.forField(employeeField)
                .bind(dto -> dto.getEmployee().getFullName(), null); // Только для чтения

        binder.forField(dateOfIssueField)
                .bind(dto -> dto.getDateOfIssue().toString(), null); // Только для чтения

        binder.forField(dateTermField)
                .bind(OldContractDto::getDateTerm,
                        (dto, value) -> dto.setDateTerm(value != null ? value : LocalDateTime.now().plusMonths(1)));

        binder.forField(issuePointField)
                .bind(dto -> dto.getIssuePoint().getName(), null); // Только для чтения
    }

    /**
     * Создание кнопки сохранения.
     */
    private Button createSaveButton(OldContractDto contract, Dialog dialog) {
        Button saveButton = new Button("Сохранить", VaadinIcon.CHECK.create());
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        saveButton.addClickListener(e -> saveContractChanges(contract, dialog));
        return saveButton;
    }

    /**
     * Сохранение изменений договора.
     */
    private void saveContractChanges(OldContractDto contract, Dialog dialog) {
        try {
            binder.writeBean(contract);
            contractService.updateContract(contractMapper.oldContractToUpdateMapping(contract));
            grid.getDataProvider().refreshItem(contract);
            showNotification("Договор успешно обновлен", NotificationVariant.LUMO_SUCCESS);
            dialog.close();
        } catch (ValidationException ex) {
            showNotification("Ошибка валидации", NotificationVariant.LUMO_ERROR);
        }
    }

    /**
     * Настройка доступности полей формы.
     */
    private void configureFieldsAccessibility(OldContractDto contract, BigDecimalField amountField,
                                              TextField clientField, TextField employeeField,
                                              TextField dateOfIssueField, DateTimePicker dateTermField,
                                              TextField issuePointField) {
        boolean isClosed = isContractClosed(contract);

        amountField.setReadOnly(isClosed);
        clientField.setReadOnly(true); // Всегда только для чтения
        employeeField.setReadOnly(true); // Всегда только для чтения
        dateOfIssueField.setReadOnly(true); // Всегда только для чтения
        dateTermField.setReadOnly(isClosed);
        issuePointField.setReadOnly(true); // Всегда только для чтения
    }

    /**
     * Создание диалогового окна.
     */
    private Dialog createDialog() {
        Dialog dialog = new Dialog();
        dialog.setCloseOnEsc(true);
        dialog.setCloseOnOutsideClick(true);
        dialog.setWidth("600px");
        return dialog;
    }

    /**
     * Создание строки со статусом договора.
     */
    private HorizontalLayout createStatusRow(Span statusSpan) {
        HorizontalLayout statusRow = new HorizontalLayout(
                new Span("Статус:"),
                statusSpan
        );
        statusRow.setSpacing(true);
        return statusRow;
    }

    /**
     * Создание элемента для отображения статуса.
     */
    private Span createStatusSpan(String statusText) {
        Span statusSpan = new Span(statusText);
        statusSpan.getElement().getStyle().set("font-weight", "bold");
        return statusSpan;
    }

    /**
     * Создание панели с кнопками действий.
     */
    private HorizontalLayout createActionButtonsLayout(OldContractDto contract, Dialog dialog, Span statusSpan) {
        HorizontalLayout buttonsLayout = new HorizontalLayout();
        buttonsLayout.setSpacing(true);

        buttonsLayout.add(
                createDownloadButton(contract),
                createCloseContractButton(contract, dialog, statusSpan)
        );

        return buttonsLayout;
    }

    /**
     * Создание кнопки скачивания договора.
     */
    private Button createDownloadButton(OldContractDto contract) {
        Button downloadBtn = new Button("Скачать бланк договора", VaadinIcon.DOWNLOAD.create());
        downloadBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        downloadBtn.addClickListener(e -> downloadContract(contract));
        return downloadBtn;
    }

    /**
     * Создание кнопки закрытия договора.
     */
    private Button createCloseContractButton(OldContractDto contract, Dialog dialog, Span statusSpan) {
        Button closeContractBtn = new Button("Закрыть договор", VaadinIcon.LOCK.create());
        closeContractBtn.addThemeVariants(ButtonVariant.LUMO_ERROR);
        configureCloseButtonState(closeContractBtn, contract);
        closeContractBtn.addClickListener(e ->
                confirmCloseContract(contract, dialog, statusSpan, closeContractBtn));
        return closeContractBtn;
    }

    /**
     * Настройка состояния кнопки закрытия договора.
     */
    private void configureCloseButtonState(Button button, OldContractDto contract) {
        boolean isClosed = CLOSED_STATUS.equalsIgnoreCase(contract.getStatus().getName())
                || contract.getStatus().getId() == CLOSED_STATUS_ID;
        button.setEnabled(!isClosed);
    }

    /**
     * Скачивание договора.
     */
    private void downloadContract(OldContractDto contract) {
        try {
            String url = "/excel/export/" + contract.getId();
            UI.getCurrent().getPage().open(url, "_blank");
            showNotification("Документ готов к скачиванию", NotificationVariant.LUMO_SUCCESS);
        } catch (Exception ex) {
            showNotification("Ошибка при скачивании", NotificationVariant.LUMO_ERROR);
        }
    }

    /**
     * Подтверждение закрытия договора.
     */
    private void confirmCloseContract(OldContractDto contract, Dialog dialog,
                                      Span statusSpan, Button closeButton) {
        ConfirmDialog confirmDialog = new ConfirmDialog();
        confirmDialog.setHeader("Подтверждение закрытия договора");
        confirmDialog.setText("Вы точно хотите закрыть договор №" + contract.getId() + "?");
        confirmDialog.setConfirmText("Да");
        confirmDialog.setCancelable(true);
        confirmDialog.setCancelText("Нет");

        confirmDialog.addConfirmListener(event ->
                handleContractClosing(contract, dialog, statusSpan, closeButton));
        confirmDialog.addCancelListener(event ->
                showNotification("Договор не был закрыт", NotificationVariant.LUMO_CONTRAST));

        confirmDialog.open();
    }

    /**
     * Обработка закрытия договора.
     */
    private void handleContractClosing(OldContractDto contract, Dialog dialog,
                                       Span statusSpan, Button closeButton) {
        OldContractDto updatedContract = contractService.closeStatus(contract);
        statusSpan.setText(updatedContract.getStatus().getName());
        refreshGridData();
        closeButton.setEnabled(false);
        showNotification("Договор успешно закрыт", NotificationVariant.LUMO_SUCCESS);
        dialog.close();
    }

    /**
     * Отображение уведомления.
     */
    private void showNotification(String message, NotificationVariant variant) {
        Notification.show(message, 3000, Notification.Position.MIDDLE)
                .addThemeVariants(variant);
    }
}