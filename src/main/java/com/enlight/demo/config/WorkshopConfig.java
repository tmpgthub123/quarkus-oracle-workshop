package com.enlight.demo.config;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 * Small config holder used by the workshop code to avoid hardcoded values.
 */
@ApplicationScoped
public class WorkshopConfig {

    @ConfigProperty(name = "demo.db-bootstrap.enabled", defaultValue = "true")
    boolean dbBootstrapEnabled;

    public boolean dbBootstrapEnabled() {
        return dbBootstrapEnabled;
    }
}
