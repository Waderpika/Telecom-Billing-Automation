# Selenium Automation Framework

A production-style end-to-end test automation framework built with Java, Selenium WebDriver, Maven, TestNG, and Rest Assured — covering UI and API testing for an e-commerce web application.

# Selenium Automation Framework

## Overview
A production-style end-to-end test automation framework built with **Java 17, Selenium WebDriver, Maven, TestNG, and Rest Assured** — covering UI and API testing for an e-commerce web application.

## Features
- 🚀 UI automation with Selenium WebDriver
- 🧪 API testing with Rest Assured
- 📊 TestNG integration for structured test execution
- 📑 ExtentReports for rich HTML reporting
- 🔄 Maven build management
- ⚙️ Jenkinsfile for CI/CD pipeline setup

## Tech Stack
- Java 17
- Maven 3.9.16
- Selenium WebDriver 4.18.1
- TestNG 7.9.0
- Rest Assured 5.4.0
- ExtentReports 5.1.1

## Example Test Cases
- ✅ Login (valid/invalid credentials)
- ✅ Cart operations (add/remove items)
- ✅ Checkout flow validation
- ✅ API tests for user management

---

## Tech Stack

| Tool | Purpose |
|---|---|
| Java 11 | Core language |
| Selenium WebDriver 4.x | Browser automation |
| Maven + Surefire Plugin | Build tool + parallel test execution |
| TestNG | Test framework (annotations, parallel config, listeners) |
| Rest Assured | REST API test automation |
| WebDriverManager | Automatic browser driver management |
| Page Object Model (POM) | UI abstraction pattern |
| Jenkins + Jenkinsfile | CI/CD pipeline |
| Git | Version control |

---

## Project Structure

```
selenium-automation-framework/
├── src/
│   ├── main/java/com/ayush/framework/
│   │   ├── base/
│   │   │   ├── BaseTest.java        # Test setup/teardown (per-method)
│   │   │   └── BasePage.java        # Common WebDriver helpers + waits
│   │   ├── pages/
│   │   │   ├── LoginPage.java       # Login screen POM
│   │   │   ├── InventoryPage.java   # Product listing POM
│   │   │   ├── CartPage.java        # Shopping cart POM
│   │   │   └── CheckoutPage.java    # Checkout flow POM
│   │   ├── utils/
│   │   │   ├── ConfigReader.java    # Singleton config.properties reader
│   │   │   ├── DriverManager.java   # ThreadLocal WebDriver (parallel-safe)
│   │   │   └── ScreenshotUtil.java  # Screenshot capture on failure
│   │   └── listeners/
│   │       └── TestListener.java    # TestNG listener (screenshot + logging)
│   └── test/
│       ├── java/com/ayush/tests/
│       │   ├── LoginTest.java        # Login scenarios (4 tests)
│       │   ├── InventoryTest.java    # Product listing + cart (4 tests)
│       │   ├── CheckoutTest.java     # E2E checkout flow (3 tests)
│       │   └── ApiTest.java          # REST API tests via Rest Assured (5 tests)
│       └── resources/
│           ├── testng.xml            # Suite config + parallel execution
│           └── config.properties     # Base URL, credentials, timeouts
├── Jenkinsfile                        # CI/CD pipeline definition
├── pom.xml                            # Maven dependencies + Surefire config
└── README.md
```

---

## Key Design Decisions

**ThreadLocal WebDriver** — `DriverManager` uses `ThreadLocal<WebDriver>` so each parallel test thread gets its own isolated browser instance, preventing test interference during parallel runs.

**Per-method setup/teardown** — `@BeforeMethod` / `@AfterMethod` in `BaseTest` gives each test a clean browser state, critical for reliable parallel execution.

**Config externalised** — All environment config (URL, credentials, timeouts, browser) lives in `config.properties`, not hardcoded — easy to swap environments without touching test code.

**Page Objects return page objects** — Methods like `clickLoginAndExpectSuccess()` return the next page object, enabling fluent method chaining in tests and making the flow readable.

---

## Test Coverage

| Test Class | Scenarios Covered |
|---|---|
| LoginTest | Valid login, invalid password, empty username, locked-out user |
| InventoryTest | Page load, add single item, add multiple items, sort by price |
| CheckoutTest | Full E2E flow, cart validation, checkout form validation |
| ApiTest | GET list, GET single, GET 404, POST create, PUT update |

---

## How to Run

### Prerequisites
- Java 11+
- Maven 3.6+
- Chrome browser installed (ChromeDriver managed automatically)

### Run all tests (headless)
```bash
mvn clean test
```

### Run specific test class
```bash
mvn test -Dtest=LoginTest
```

### Run with headed browser (visible)
```bash
mvn test -Dheadless=false
```

### Run with parallel execution (3 threads)
Parallel config is set in `testng.xml` and `pom.xml` — just run:
```bash
mvn clean test
```

---

## CI/CD

The `Jenkinsfile` defines a pipeline with stages:
1. **Checkout** — clone repo
2. **Build** — `mvn clean compile`
3. **Run Tests** — `mvn test` in headless mode
4. **Publish Results** — HTML report archived in Jenkins

To set up in Jenkins: New Item → Pipeline → point to this repo → Jenkins picks up the Jenkinsfile automatically.

---

## Author

Built as part of an SDET automation portfolio.
Skills demonstrated: Java, Selenium WebDriver, Maven, TestNG, Rest Assured, POM design pattern, parallel execution, CI/CD with Jenkins.

