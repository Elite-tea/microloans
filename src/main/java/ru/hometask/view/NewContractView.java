package ru.hometask.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import ru.hometask.dto.NewClientDto;
import ru.hometask.dto.NewContractDto;
import ru.hometask.entities.Client;
import ru.hometask.entities.Employee;
import ru.hometask.entities.IssuePoint;
import ru.hometask.entities.Status;
import ru.hometask.mappers.ClientMapper;
import ru.hometask.repositories.EmployeeRepository;
import ru.hometask.repositories.IssuePointRepository;
import ru.hometask.repositories.StatusRepository;
import ru.hometask.services.ClientService;
import ru.hometask.services.ContractService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;

@PageTitle("Новый контракт")
@Route("contracts/new")
@PermitAll
public class NewContractView extends VerticalLayout {

    private final ContractService contractService;
    private final ClientService clientService;
    private final EmployeeRepository employeeRepository;
    private final IssuePointRepository issuePointRepository;
    private final StatusRepository statusRepository;
    private final ClientMapper clientMapper;

    // Элементы формы
    private Checkbox newClientCheckbox;
    private ComboBox<Client> clientComboBox;
    private Div clientFormContainer;
    private TextField clientNameField;
    private TextField clientPhoneField;

    public NewContractView(ContractService contractService,
                              ClientService clientService,
                              EmployeeRepository employeeRepository,
                              IssuePointRepository issuePointRepository,
                              StatusRepository statusRepository,
                           ClientMapper clientMapper) {
        this.contractService = contractService;
        this.clientService = clientService;
        this.employeeRepository = employeeRepository;
        this.issuePointRepository = issuePointRepository;
        this.statusRepository = statusRepository;
        this.clientMapper = clientMapper;

        initView();
    }

    private void initView() {

        add(new H1("Новый договор"));
        setAlignItems(Alignment.CENTER);
        setSizeFull();

        // Чекбокс для выбора типа клиента
        newClientCheckbox = new Checkbox("Создать нового клиента");
        newClientCheckbox.addValueChangeListener(e -> toggleClientForm(e.getValue()));

        // Комбобокс для выбора существующего клиента
        clientComboBox = new ComboBox<>("Выберите клиента");
        clientComboBox.setItems(clientService.getAllClient());
        clientComboBox.setItemLabelGenerator(Client::getFullName);
        clientComboBox.setWidthFull();

        // Контейнер для формы нового клиента (изначально скрыт)
        clientFormContainer = new Div();
        clientFormContainer.setVisible(false);

        // Поля для нового клиента
        clientNameField = new TextField("ФИО клиента");
        clientNameField.setWidthFull();

        clientPhoneField = new TextField("Телефон клиента");
        clientPhoneField.setWidthFull();

        clientFormContainer.add(clientNameField, clientPhoneField);

        // Остальные поля формы договора
        BigDecimalField amountField = new BigDecimalField("Сумма");
        amountField.setValue(BigDecimal.valueOf(0));

        DatePicker issueDatePicker = new DatePicker("Дата заключения");
        issueDatePicker.setValue(LocalDate.now());

        ComboBox<Employee> employeeComboBox = new ComboBox<>("Сотрудник");
        employeeComboBox.setItems(employeeRepository.findAll());
        employeeComboBox.setItemLabelGenerator(Employee::getFullName);

        DatePicker termDatePicker = new DatePicker("Дата окончания");
        termDatePicker.setValue(LocalDate.now().plusMonths(1));

        ComboBox<IssuePoint> issuePointComboBox = new ComboBox<>("Точка выдачи");
        issuePointComboBox.setItems(issuePointRepository.findAll());
        issuePointComboBox.setItemLabelGenerator(IssuePoint::getAddress);

        ComboBox<Status> statusComboBox = new ComboBox<>("Статус");
        Status status = statusRepository.findById(1L).orElseThrow();
        statusComboBox.setItems(Collections.singletonList(status));
        statusComboBox.setItemLabelGenerator(Status::getName);

        // Кнопка сохранения
        Button saveButton = new Button("Создать договор", VaadinIcon.CHECK.create());
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        saveButton.addClickListener(e -> saveContract(
                newClientCheckbox.getValue(),
                clientComboBox.getValue(),
                clientNameField.getValue(),
                clientPhoneField.getValue(),
                amountField.getValue(),
                issueDatePicker.getValue().atStartOfDay(),
                employeeComboBox.getValue().getId(),
                termDatePicker.getValue().atStartOfDay(),
                issuePointComboBox.getValue().getId(),
                statusComboBox.getValue().getId()
        ));

        // Компоновка формы
        FormLayout formLayout = new FormLayout();
        formLayout.add(
                newClientCheckbox,
                clientComboBox,
                clientFormContainer,
                amountField,
                issueDatePicker,
                employeeComboBox,
                termDatePicker,
                issuePointComboBox,
                statusComboBox,
                saveButton
        );
        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("500px", 2)
        );

        add(formLayout);
    }

    private void toggleClientForm(boolean showNewClientForm) {
        clientComboBox.setVisible(!showNewClientForm);
        clientFormContainer.setVisible(showNewClientForm);

        if (showNewClientForm) {
            clientComboBox.clear();
        } else {
            clientNameField.clear();
            clientPhoneField.clear();
        }
    }

    private void saveContract(boolean isNewClient,
                              Client existingClient,
                              String newClientName,
                              String newClientPhone,
                              BigDecimal amount,
                              LocalDateTime dateOfIssue,
                              Long employeeId,
                              LocalDateTime dateTerm,
                              Long issuePointId,
                              Long statusId) {
        try {
            NewContractDto newContract = new NewContractDto();
            newContract.setAmount(amount);
            newContract.setDateOfIssue(dateOfIssue);
            newContract.setEmployeeId(employeeId);
            newContract.setDateTerm(dateTerm);
            newContract.setIssuePointId(issuePointId);
            newContract.setStatusId(statusId);

            if (isNewClient) {
                // Создаем нового клиента (без указания ID)
                NewClientDto newClientDto = new NewClientDto();
                newClientDto.setFullName(newClientName);
                newClientDto.setPhone(newClientPhone);

                // Сохраняем клиента и получаем его с уже сгенерированным ID
                Client createdClient = clientMapper.newUserMapping(newClientDto);
                createdClient = clientService.saveClient(createdClient);

                newContract.setClientId(createdClient.getId());
            } else {
                // Используем существующего клиента
                if (existingClient == null) {
                    throw new IllegalArgumentException("Не выбран клиент");
                }
                newContract.setClientId(existingClient.getId());
            }

            contractService.addContract(newContract);
            Notification.show("Договор успешно создан", 3000, Notification.Position.MIDDLE)
                    .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            UI.getCurrent().navigate(ContractsView.class);
        } catch (Exception ex) {
            Notification.show("Ошибка: " + ex.getMessage(), 5000, Notification.Position.MIDDLE)
                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
            ex.printStackTrace();
        }
    }
}