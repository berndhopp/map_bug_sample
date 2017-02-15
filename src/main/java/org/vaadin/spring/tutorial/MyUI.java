package org.vaadin.spring.tutorial;

import com.google.inject.Inject;
import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import org.vaadin.guice.annotation.GuiceUI;
import org.vaadin.guice.annotation.GuiceVaadinConfiguration;
import org.vaadin.guice.server.GuiceVaadinServlet;

import javax.servlet.annotation.WebServlet;

@Theme("valo")
@GuiceUI(viewContainer = Content.class)
@Push
public class MyUI extends UI {

    @Inject
    private Content content;

    @Override
    protected void init(VaadinRequest request) {
        final VerticalLayout root = new VerticalLayout();
        root.setSizeFull();
        root.setMargin(true);
        root.setSpacing(true);
        setContent(root);

        final CssLayout navigationBar = new CssLayout();
        navigationBar.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        navigationBar.addComponent(createNavigationButton("UI Scoped View",
                UIScopedView.VIEW_NAME));
        navigationBar.addComponent(createNavigationButton("View Scoped View",
                ViewScopedView.VIEW_NAME));
        root.addComponent(navigationBar);

        content.setSizeFull();
        root.addComponent(content);
        root.setExpandRatio(content, 1.0f);
    }

    private Button createNavigationButton(String caption,
            final String viewName) {
        Button button = new Button(caption);
        button.addStyleName(ValoTheme.BUTTON_SMALL);
        // If you didn't choose Java 8 when creating the project, convert this
        // to an anonymous listener class
        button.addClickListener(
                event -> getUI().getNavigator().navigateTo(viewName));
        return button;
    }

    @WebServlet(urlPatterns = "/*", name = "MapServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    @GuiceVaadinConfiguration(basePackages = "org.vaadin.spring.tutorial")
    public static class MapServlet extends GuiceVaadinServlet {
    }
}

