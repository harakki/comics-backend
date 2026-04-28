@ApplicationModule(
        allowedDependencies = {
                "shared",
                "catalog :: api",
                "collections :: api",
                "content :: api",
                "library :: api",
                "recommendations :: api"
        }
)
package dev.harakki.comics.analytics;

import org.springframework.modulith.ApplicationModule;
