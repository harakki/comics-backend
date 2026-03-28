@ApplicationModule(
        allowedDependencies = {
                "shared",
                "catalog :: api",
                "analytics :: api"
        }
)
package dev.harakki.comics.recommendations;

import org.springframework.modulith.ApplicationModule;
