package com.example.application.views.newPerson;

import com.example.application.model.Employee;
import com.example.application.model.Position;
import com.example.application.service.EmployeeService;
import com.example.application.service.PositionService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.PermitAll;

import org.springframework.beans.factory.annotation.Autowired;

@PageTitle("Neu")
@Route(value = "neu", layout = MainLayout.class)
@PermitAll
@Uses(Icon.class)
public class NeuView extends Div {

	private static final long serialVersionUID = -1106906730712117718L;
	private TextField firstName = new TextField("Name");
	private TextField lastName = new TextField("Nachname");
	private EmailField email = new EmailField("Email");
	private ComboBox<Position> position = new ComboBox<>("Beruf");

	private Button cancel = new Button("Löschen");
	private Button save = new Button("Speichern");

	private Binder<Employee> binder = new BeanValidationBinder<>(Employee.class);

	@Autowired
	public EmployeeService employeeService;

	@Autowired
	public PositionService positionService;

	public NeuView(EmployeeService employeeService, PositionService positionService) {
		this.employeeService = employeeService;
		this.positionService = positionService;

		addClassName("neu-view");
		
		binder.bindInstanceFields(this);

		add(createTitle());
		add(createFormLayout());
		add(createButtonLayout());

		clearForm();
		cofigureComboBox();

		cancel.addThemeVariants(ButtonVariant.LUMO_ERROR);
		cancel.addClickListener(e -> clearForm());
		cancel.setWidthFull();

		save.setWidthFull();
		save.addClickListener(event -> {
			employeeService.save(binder.getBean());
			setNotification(
					binder.getBean().getFirstName() + " " + binder.getBean().getLastName() + "\terfolgreich gespeichert!",
					NotificationVariant.LUMO_SUCCESS);
			clearForm();
		});

	}

	private void clearForm() {
		binder.setBean(new Employee());

	}

	private Notification setNotification(String text, NotificationVariant notificationVariant) {
		Notification notification = Notification.show(text);
		notification.addThemeVariants(notificationVariant);
		notification.setPosition(Notification.Position.BOTTOM_CENTER);

		return notification;
	}

	private void cofigureComboBox() {
		position.setItems(positionService.findAllPositions());
		position.setItemLabelGenerator(Position::getPosition_name);
	}

	private Component createTitle() {
		return new H3("Neue Mitarbeiter");
	}

	private Component createFormLayout() {
		FormLayout formLayout = new FormLayout();
		email.setPlaceholder("max.mustermann@gmail.com");
		binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));
		formLayout.add(firstName, lastName, email, position);
		return formLayout;
	}

	private Component createButtonLayout() {
		HorizontalLayout buttonLayout = new HorizontalLayout();
		buttonLayout.addClassName("button-layout");
		save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		buttonLayout.add(save);
		buttonLayout.add(cancel);
		return buttonLayout;
	}

}
