# University Placement Portal REST API

A Java 21 / Spring Boot 3.5 REST API for managing placement-season students, company drives,
applications, interview status changes, and placement statistics. Persistence uses Spring Data JPA,
Hibernate, and MySQL 8.

For the complete endpoint reference and ready-to-copy Postman request bodies, see the
[REST API and Postman guide](./docs/API_GUIDE.md).

## Requirements

* JDK 21 or later
* Maven
* MySQL 8 on `localhost:3306`, or Docker Compose

## Run

Configure the MySQL URL, username, and password in `src/main/resources/application.properties`.
Hibernate creates or updates the schema from the JPA entities. On an empty database, the
`PlacementDataInitializer` loads the sample students, companies, and applications through Spring
Data JPA repositories.

Start the HTTP API:

```bash
mvn spring-boot:run
```

The base URL is `http://localhost:8080`. Verify it using `GET /api/students`. The API returns JSON;
invalid requests and business-rule failures also return JSON error objects. Tests use an embedded
H2 database and can run without MySQL:

```bash
mvn test
```

## Postman endpoints

| Method | Path | Purpose |
| --- | --- | --- |
| `POST` | `/api/students` | Register a student |
| `GET` | `/api/students` | List students |
| `GET` | `/api/students/{rollNo}` | Get a student |
| `POST` | `/api/students/{rollNo}/opt-out` | Opt a student out |
| `POST` | `/api/companies` | Register a company drive |
| `GET` | `/api/companies` | List companies |
| `GET` | `/api/companies/{companyId}` | Get a company |
| `GET` | `/api/companies/eligible?rollNo=CS21003` | Find eligible drives |
| `POST` | `/api/applications` | Apply a student to a company |
| `GET` | `/api/applications` | List all applications |
| `GET` | `/api/applications?rollNo=CS21003` | Filter applications by student |
| `GET` | `/api/applications?companyId=6` | Filter applications by company |
| `GET` | `/api/applications/{appId}` | Get application details |
| `PATCH` | `/api/applications/{appId}/status` | Advance or reject an application |
| `GET` | `/api/statistics` | Get overall placement statistics |
| `GET` | `/api/statistics/drives` | Get company drive summaries |

Set `Content-Type: application/json` for POST and PATCH requests.

### Example requests

Register a student:

```http
POST http://localhost:8080/api/students
Content-Type: application/json
```

```json
{
  "rollNo": "CS24001",
  "name": "Asha Rao",
  "cgpa": 8.75,
  "activeBacklogs": 0
}
```

Register a company:

```http
POST http://localhost:8080/api/companies
Content-Type: application/json
```

```json
{
  "name": "Acme",
  "minCgpa": 7.5,
  "maxBacklogs": 0,
  "packageLpa": 12.0
}
```

Apply to a drive:

```http
POST http://localhost:8080/api/applications
Content-Type: application/json
```

```json
{
  "rollNo": "CS24001",
  "companyId": 1
}
```

Update the interview status:

```http
PATCH http://localhost:8080/api/applications/1/status
Content-Type: application/json
```

```json
{
  "status": "SHORTLISTED"
}
```

Allowed status progression is `APPLIED -> SHORTLISTED -> INTERVIEWING -> OFFERED`, with `REJECTED`
available from each non-final stage. Releasing an offer also updates the student's placement status
and rejects their pending applications for lower-package drives.

## Project layers

```text
controller/  REST endpoints and JSON exception responses
api/         Validated request/response records
service/     Business rules and transactions
repository/  Spring Data JPA repositories and query projections
entity/      JPA entity mappings
config/      JPA-backed initial sample data
exception/   Business-rule exceptions
```

