@ApplicationModule(
        allowedDependencies = {
                "shared",
                "catalog :: api",
                "analytics :: api",
                "library :: api"
        }
)
package dev.harakki.comics.recommendations;

import org.springframework.modulith.ApplicationModule;
