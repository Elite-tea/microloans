package ru.hometask.view;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.security.AuthenticationContext;
import jakarta.annotation.security.PermitAll;
import org.springframework.security.core.GrantedAuthority;
import ru.hometask.config.EmployeeUserDetails;
import ru.hometask.dto.AdminReportDto;
import ru.hometask.exception.AccessDeniedView;
import ru.hometask.services.ReportService;

import java.util.List;

@PageTitle("Сводный отчет")
@Route("admin/report")
@PermitAll
public class AdminReportView extends VerticalLayout implements BeforeEnterObserver {

    private final AuthenticationContext authContext;
    private final ReportService reportService;

    /**
     * Конструктор с внедрением зависимостей.
     *
     * @param reportService сервис для работы с отчетом
     * @param authContext сервис для идентификации
     */
    public AdminReportView(AuthenticationContext authContext,
                           ReportService reportService) {
        this.authContext=authContext;
        this.reportService=reportService;
        initView();
        refreshGridData();
    }

    // Компоненты UI
    private Grid<AdminReportDto> grid;

    /**
     * Инициализация представления.
     */
    private void initView() {
        configurePageLayout();
        initializeGrid();
        add(grid);
    }

    /**
     * Настройка макета страницы.
     */
    private void configurePageLayout() {
        add(new H1("Сводный отчет по точкам"));
        setAlignItems(Alignment.CENTER);
        setSizeFull();
    }

    /**
     * Настройка колонок таблицы.
     */
    private void configureGridColumns() {
        grid.addColumn(AdminReportDto::getIssuePointName)
                .setHeader("Точка выдачи")
                .setAutoWidth(true)
                .setSortable(true);

        grid.addColumn(AdminReportDto::getAllAmount)
                .setHeader("Сумма по договорам")
                .setAutoWidth(true)
                .setSortable(true);

        grid.addColumn(AdminReportDto::getStatusName)
                .setHeader("Статус")
                .setAutoWidth(true)
                .setSortable(true);

        grid.addColumn(AdminReportDto::getAllCostContract)
                .setHeader("Количество контрактов")
                .setAutoWidth(true)
                .setSortable(true);
    }

    /**
     * Инициализация и настройка таблицы договоров.
     */
    private void initializeGrid() {
        grid = new Grid<>(AdminReportDto.class, false);
        configureGridColumns();
    }

    /**
     * Обновление данных в таблице.
     */
    private void refreshGridData() {
        List<AdminReportDto> contracts = reportService.getReportActive();
        grid.setItems(contracts);
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
