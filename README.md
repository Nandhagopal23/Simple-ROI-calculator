# Invoicing ROI Simulator

This is a minimal, runnable scaffold for the Invoicing ROI Simulator so the repo can be built and run locally.

## Requirements

- Java 17

This repository includes a standard Maven build. If you don't have Maven installed system-wide, it's recommended to add the Maven Wrapper (`mvnw`) to the repo so developers can run the project without installing Maven.

## Run locally (examples)

If you have Maven installed:

```bash
mvn spring-boot:run
```

On Windows (PowerShell / CMD) with the wrapper (after `mvnw` files are added):

```powershell
mvnw.cmd spring-boot:run
```

On macOS / Linux with the wrapper:

```bash
./mvnw spring-boot:run
```

The app starts on http://localhost:8080 and serves a simple UI at `/index.html`.

## Project layout

- `src/main/java/com/example/invoicingroisimulator` - Spring Boot application and controller
- `src/main/resources/static/index.html` - minimal frontend

If you want me to expand features (save/load scenarios, CSV/HTML export, H2 persistence), tell me which features to prioritize and I'll implement them next.
