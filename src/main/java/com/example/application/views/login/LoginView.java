package com.example.application.views.login;

import com.example.application.security.AuthenticatedUser;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.internal.RouteUtil;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@AnonymousAllowed
@PageTitle("Login")
@Route(value = "login")
public class LoginView extends LoginOverlay implements BeforeEnterObserver {

	private static final long serialVersionUID = -1370960994436472023L;
	private final AuthenticatedUser authenticatedUser;

	public LoginView(AuthenticatedUser authenticatedUser) {
		this.authenticatedUser = authenticatedUser;
		setAction(RouteUtil.getRoutePath(VaadinService.getCurrent().getContext(), getClass()));

		LoginI18n i18n = LoginI18n.createDefault();

		LoginI18n.Form i18nForm = i18n.getForm();
		i18nForm.setTitle("Anmelden");
		i18nForm.setUsername("Benutzername");
		i18nForm.setPassword("Passwort");
		i18nForm.setSubmit("Einloggen");
		i18n.setForm(i18nForm);

		setI18n(i18n);

		setTitle("Demo");
		setDescription("Benutzertname: admin, Passwort: admin");

		setForgotPasswordButtonVisible(false);
		setOpened(true);
	}

	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		if (authenticatedUser.get().isPresent()) {
			// Already logged in
			setOpened(false);
			event.forwardTo("");
		}

		setError(event.getLocation().getQueryParameters().getParameters().containsKey("error"));
	}
}
