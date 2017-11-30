package org.vaadin.addons.selection_range_extension.demo;

import javax.servlet.annotation.WebServlet;

import org.vaadin.addons.selection_range_extension.SelectionRangeExtension;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Theme("demo")
@Title("SelectionRangeExtension Add-on Demo")
@SuppressWarnings("serial")
public class DemoUI extends UI {

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = DemoUI.class)
    public static class Servlet extends VaadinServlet {
    }

    @Override
    protected void init(VaadinRequest request) {
        TextField f = new TextField();
        TextArea f1 = new TextArea();

        SelectionRangeExtension.extend(f,
                new SelectionRangeExtension.SelectionRangeListener() {

                    @Override
                    public void selectionChanged(int start, int end) {
                        System.out.println("1: " + start + ", " + end);
                    }

                }, true);
        SelectionRangeExtension s = SelectionRangeExtension.extend(f1,
                new SelectionRangeExtension.SelectionRangeListener() {

                    @Override
                    public void selectionChanged(int start, int end) {
                        System.out.println("2: " + start + ", " + end);
                    }

                }, false);

        final VerticalLayout layout = new VerticalLayout();

        layout.setSizeFull();
        layout.setMargin(true);
        layout.setSpacing(true);

        layout.addComponents(f, f1);
        setContent(layout);
    }
}
