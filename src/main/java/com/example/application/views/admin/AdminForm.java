package com.example.application.views.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.application.model.Employee;
import com.example.application.model.Position;
import com.example.application.service.EmployeeService;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.theme.lumo.LumoUtility;

public class AdminForm extends VerticalLayout {

	private static final long serialVersionUID = -9215273042030439573L;
	private TextField firstName = new TextField("Name");
	private TextField lastName = new TextField("Nachname");
	private TextField email = new TextField("Email");
	private ComboBox<Position> position = new ComboBox<>("Beruf");

	public Binder<Employee> binder = new BeanValidationBinder<>(Employee.class);

	private Employee employee;

	private Dialog dialog = new Dialog();
	private Button deleteBtn = new Button("Löschen", e -> dialog.open());
	private Button saveBtn = new Button("Speichern");

	@Autowired
	public EmployeeService employeeService;

	public AdminForm(List<Position> positions, EmployeeService employeeService) {
		this.employeeService = employeeService;

		// Für alle Felder
		binder.bindInstanceFields(this);

		addClassName("filter-layout");
		addClassNames(LumoUtility.Padding.Horizontal.LARGE, LumoUtility.Padding.Vertical.MEDIUM,
				LumoUtility.BoxSizing.BORDER);

		VerticalLayout dialogLayout = createDialogLayout(dialog);
		dialog.add(dialogLayout);

		setFieldsEnabledFalse();

		binder.addStatusChangeListener(e -> saveBtn.setEnabled(binder.isValid()));

		position.setItems(positions);
		position.setItemLabelGenerator(Position::getPosition_name);

		deleteBtn.setWidthFull();
		deleteBtn.addThemeVariants(ButtonVariant.LUMO_ERROR);

		saveBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		saveBtn.setWidthFull();
		saveBtn.addClickListener(event -> save());

		Div actions = new Div(saveBtn, deleteBtn);
		actions.addClassName(LumoUtility.Gap.SMALL);
		actions.addClassName("actions");

		add(firstName, lastName, email, position, actions);

	}

	private void save() {
		// TODO Auto-generated method stub
		try {
			binder.writeBean(employee);
			fireEvent(new SaveEvent(this, employee));
			setNotification("Erfolgreich geändert!", NotificationVariant.LUMO_SUCCESS);
		} catch (ValidationException e) {
			// TODO Auto-generated catch block

		}
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
		binder.readBean(employee);
	}

	public void setFieldsEnabledFalse() {
		firstName.setEnabled(false);
		lastName.setEnabled(false);
		email.setEnabled(false);
		position.setEnabled(false);
		saveBtn.setEnabled(false);
		deleteBtn.setEnabled(false);
	}

	public void setFieldsEnabledTrue() {
		firstName.setEnabled(true);
		lastName.setEnabled(true);
		email.setEnabled(true);
		position.setEnabled(true);
		saveBtn.setEnabled(true);
		deleteBtn.setEnabled(true);
	}

	private VerticalLayout createDialogLayout(Dialog dialog) {
		H2 headline = new H2("Sind Sie sicher?");
		headline.getStyle().set("margin", "var(--lumo-space-m) 0 0 0").set("font-size", "1.5em").set("font-weight",
				"bold");

		Button cancelBtn = new Button("Nein", e -> dialog.close());
		Button deleteBtn = new Button("Ok", event -> {
			fireEvent(new DeleteEvent(this, employee));
			dialog.close();
		});

		cancelBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		cancelBtn.setWidthFull();
		deleteBtn.addThemeVariants(ButtonVariant.LUMO_ERROR);
		deleteBtn.setWidthFull();
		HorizontalLayout buttonLayout = new HorizontalLayout(cancelBtn, deleteBtn);
		buttonLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);

		VerticalLayout dialogLayout = new VerticalLayout(headline, buttonLayout);
		dialogLayout.setPadding(false);
		dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
		dialogLayout.getStyle().set("width", "300px").set("max-width", "100%");

		return dialogLayout;
	}

	private Notification setNotification(String text, NotificationVariant notificationVariant) {
		Notification notification = Notification.show(text);
		notification.addThemeVariants(notificationVariant);
		notification.setPosition(Notification.Position.BOTTOM_CENTER);

		return notification;
	}

	// Events
	public static abstract class EmployeeFormEvent extends ComponentEvent<AdminForm> {
		/**
		 * 
		 */
		private static final long serialVersionUID = -90900133729363425L;
		private Employee employee;

		protected EmployeeFormEvent(AdminForm source, Employee employee) {
			super(source, false);
			this.employee = employee;
		}

		public Employee getContact() {
			return employee;
		}
	}

	public static class SaveEvent extends EmployeeFormEvent {
		/**
		 * 
		 */
		private static final long serialVersionUID = 48265347731996280L;

		SaveEvent(AdminForm source, Employee employee) {
			super(source, employee);
		}
	}

	public static class DeleteEvent extends EmployeeFormEvent {
		/**
		 * 
		 */
		private static final long serialVersionUID = 7679821479309460673L;

		DeleteEvent(AdminForm source, Employee employee) {
			super(source, employee);
		}

	}

	public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
			ComponentEventListener<T> listener) {
		return getEventBus().addListener(eventType, listener);
	}

}
