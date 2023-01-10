package com.example.application.views.admin;

import com.example.application.model.Employee;
import com.example.application.service.EmployeeService;
import com.example.application.service.PositionService;
import com.example.application.views.MainLayout;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.theme.lumo.LumoUtility;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.util.stream.Stream;

import javax.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle("Admin")
@Route(value = "admin", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@PermitAll
@Uses(Icon.class)
public class AdminView extends VerticalLayout {

	private static final long serialVersionUID = 8245749523397682900L;

	private final TextField searchField = new TextField("Suchen");

	private Grid<Employee> grid = new Grid<Employee>(Employee.class, false);

	private AdminForm form;

	@Autowired
	public EmployeeService employeeService;

	@Autowired
	public PositionService positionService;

	public AdminView(EmployeeService employeeService, PositionService positionService) {
		this.employeeService = employeeService;
		this.positionService = positionService;

		setSizeFull();
		addClassNames("admin-view");

		add(configureAdminForm());

	}

	private Component configureAdminForm() {
		form = new AdminForm(positionService.findAllPositions(), employeeService);
		VerticalLayout layout = new VerticalLayout(form, configureToCsvButton(), searchLayout(), createGrid());
		layout.setSizeFull();
		layout.setPadding(false);
		layout.setSpacing(false);

		form.setFieldsEnabledFalse();

		form.addListener(AdminForm.SaveEvent.class, this::saveEmployee);
		form.addListener(AdminForm.DeleteEvent.class, this::deleteEmployee);

		return layout;

	}

	private HorizontalLayout searchLayout() {
		HorizontalLayout horizontalLayout = new HorizontalLayout();
		searchField.setPlaceholder("Suchen Sie nach Namen oder Nachnamen");
		searchField.setClearButtonVisible(true);
		searchField.setWidth("600px");
		searchField.setValueChangeMode(ValueChangeMode.LAZY);
		searchField.addValueChangeListener(event -> updateGrid());

		horizontalLayout.add(searchField);

		return horizontalLayout;

	}

	private Component configureToCsvButton() {
		StreamResource streamResaurce = new StreamResource("employee.csv", () -> {
			try {
				Stream<Employee> employees = grid.getGenericDataView().getItems();
				StringWriter output = new StringWriter();
				var toCsv = new StatefulBeanToCsvBuilder<Employee>(output)
						.withIgnoreField(Employee.class, Employee.class.getDeclaredField("id")).build();
				toCsv.write(employees);
				var contents = output.toString();
				return new ByteArrayInputStream(contents.getBytes());
			} catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException | IllegalArgumentException
					| NoSuchFieldException | SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}

		});

		Anchor toCsvBtn = new Anchor(streamResaurce, "Exportieren");
		return new HorizontalLayout(toCsvBtn);
	}

	private Component createGrid() {
		grid.addColumn("firstName").setAutoWidth(true).setHeader("Name");
		grid.addColumn("lastName").setAutoWidth(true).setHeader("Nachname");
		grid.addColumn("email").setAutoWidth(true).setHeader("Email");
		grid.addColumn(position -> position.getPosition().getPosition_name()).setAutoWidth(true).setHeader("Beruf");
		grid.addThemeVariants(GridVariant.LUMO_COMPACT);
		grid.addClassNames(LumoUtility.Border.TOP, LumoUtility.BorderColor.CONTRAST_10);
		updateGrid();
		addEmployee();

		grid.asSingleSelect().addValueChangeListener(event -> editEmployee(event.getValue()));

		return new VerticalLayout(grid);
	}

	private void saveEmployee(AdminForm.SaveEvent event) {
		employeeService.save(event.getContact());
		updateGrid();
		form.setFieldsEnabledFalse();

	}

	private void deleteEmployee(AdminForm.DeleteEvent event) {
		employeeService.delete(event.getContact());
		updateGrid();
		form.setFieldsEnabledFalse();

	}

	public void editEmployee(Employee employee) {
		form.setEmployee(employee);
		form.setFieldsEnabledTrue();

	}

	private void addEmployee() {
		grid.asSingleSelect().clear();
		editEmployee(new Employee());
		updateGrid();

	}

	private void updateGrid() {
		grid.setItems(employeeService.findAllEmployees(searchField.getValue()));
	}

}
