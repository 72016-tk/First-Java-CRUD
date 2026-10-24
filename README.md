# First Java CRUD

A small browser-based inventory application created for student **72016** as a first Java CRUD exercise.

The application supports:

- Create — add a product.
- Read — list and search products.
- Update — edit a product.
- Delete — remove a product after confirmation.
- Validation — reject blank names/categories and negative values.
- Persistence — store data locally in an H2 database.

## Requirements

- Java 17 or newer
- Maven 3.6.3 or newer

Check the installation with `java -version` and `mvn -version` before starting.

## Run locally

```bash
mvn spring-boot:run
```

Open <http://localhost:8080>. Stop the application with `Ctrl+C`.

Run the automated CRUD test with:

```bash
mvn test
```

The local database is created in `data/` and is intentionally ignored by Git.

## Project structure

```text
src/main/java/       Java application, entity, repository and controller
src/main/resources/  HTML templates, CSS and application configuration
src/test/java/       Automated CRUD test
doc/                 Assignment screenshots and evidence
```

The request flow is intentionally simple: `ProductController` receives browser requests, `ProductRepository` reads or writes `Product` entities, and Thymeleaf templates render the result. H2 stores the records locally, so no separate database server is needed.

## GitHub assignment steps

1. Create a course GitHub username that visibly contains `72016`.
2. Create a **public**, empty repository named `First-Java-CRUD`.
3. Initialize and publish this project:

   ```bash
   git init
   git add .
   git commit -m "Create product CRUD application"
   git branch -M main
   git remote add origin https://github.com/YOUR-72016-USERNAME/First-Java-CRUD.git
   git push -u origin main
   ```

4. Ask your partner to follow [`CONTRIBUTING.md`](CONTRIBUTING.md) and open a Pull Request.
5. Review and merge the partner's Pull Request.
6. Make a change to the partner's repository through the same fork/branch/Pull Request workflow.
7. Add the evidence listed in [`doc/README.md`](doc/README.md), commit it, and push it.
8. Confirm the repository is public in an incognito/private browser window.
9. Submit only the repository URL in Platon.

## Suggested partner change

A safe, visible partner contribution is to add a `description` field to `Product`, display it in the table, and add it to the form. A smaller alternative is to change the page subtitle or add another sample product.

## Technology

Spring Boot, Spring MVC, Thymeleaf, Spring Data JPA, H2 and Bean Validation.
