# VoltPay Backend (Spring Boot + Java + H2)

Backend starter for the uploaded VoltPay electricity bill frontend. It demonstrates OOP and returns actual PDF files.

## Requirements
- Java 17+
- Maven 3.9+ (or use Maven support in your IDE)

## Run
From this folder:
```bash
mvn spring-boot:run
```
API runs at `http://localhost:8080`. H2 console: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:file:./data/voltpay`
- username: `sa`
- password: (blank)

## Endpoints
| Method | URL | Purpose |
|---|---|---|
| POST | `/api/customers` | Create residential/commercial customer |
| GET | `/api/customers` | List customers |
| GET | `/api/customers/{code}` | Get customer |
| POST | `/api/bills` | Create bill |
| GET | `/api/customers/{code}/bills` | List customer's bills |
| GET | `/api/bills/{id}` | Get bill |
| POST | `/api/bills/{id}/pay` | Mark demo bill paid |
| GET | `/api/bills/{id}/pdf` | Download real PDF bill |

## Example: create customer
```json
{
  "customerCode": "CUST1001",
  "name": "Aarav Sharma",
  "mobile": "9876501001",
  "address": "Jaipur, Rajasthan",
  "meterNumber": "MTR-R-1001",
  "category": "Residential"
}
```
Send as JSON to `POST http://localhost:8080/api/customers`.

For commercial customers, use `"category": "Commercial"`.

## Example: create bill
```json
{
  "customerCode": "CUST1001",
  "billingPeriod": "2026-10",
  "previousReading": 1240,
  "currentReading": 1385,
  "dueDate": "2026-11-15"
}
```
Send to `POST http://localhost:8080/api/bills`. The response contains the generated bill ID. Download it at `/api/bills/{id}/pdf`.

## OOP mapping
- Encapsulation: entity fields are private and accessed using methods.
- Inheritance: `ResidentialCustomer` and `CommercialCustomer` extend abstract `Customer`.
- Polymorphism: `getRatePerUnit()`, `getFixedCharge()`, and `getCategory()` are overridden by customer subclasses.
- Abstraction: `Customer` is abstract.
- Exception handling: custom `ResourceNotFoundException`, validation errors and global `ApiExceptionHandler`.

## Important limitations
This is a college-project backend starter, not production-ready billing software. The initial tariff rates are hard-coded to match the uploaded demo UI; tariff administration and secure login/OTP are not implemented server-side yet. Payment only changes a demo status; it does not process money. CORS is open for local development. Before deployment, add authentication/authorization, restrict CORS, protect personal data, and use official tariff rules. Do not use the frontend's demo credentials for real accounts.
