package org.vaadin.spring.tutorial;

import org.vaadin.guice.annotation.UIScope;

@UIScope
public class Greeter {
    public String sayHello() {
        return "Hello from bean " + toString();
    }
}