package com.enlight.demo.db;

import com.enlight.demo.config.WorkshopConfig;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;

import javax.sql.DataSource;

/**
 * Executes workshop SQL scripts during application startup.
 * Keeps setup deterministic for each local run without requiring manual SQL typing.
 */
@ApplicationScoped
public class BookSchemaInitializer {

    private static final String BOOK_SCHEMA_SCRIPT = "sql/01_book_schema.sql";
    private static final String BOOK_DATA_SCRIPT = "sql/02_book_data.sql";

    @Inject
    DataSource dataSource;

    @Inject
    SqlScriptExecutor scriptExecutor;

    @Inject
    WorkshopConfig workshopConfig;

    void onStart(@Observes StartupEvent event) {
        if (!workshopConfig.dbBootstrapEnabled()) {
            return;
        }

        scriptExecutor.runScripts(
                dataSource,
                BOOK_SCHEMA_SCRIPT,
                BOOK_DATA_SCRIPT
        );
    }
}
