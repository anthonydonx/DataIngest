# Data Ingest Service

## Time 
- **Start**: 2025-04-15 
- **End**: 2025-04-15
- **Duration**: 1 day (4 hours) 

#### **The entire implementation was completed within 4 hours. Unfortunately, due to the demands of my current job.**
`
### Requirements

| TASK                    | STATUS    |
|-------------------------|-----------|
| Spring Batch processing | Done      |
| Liquibase               | Done      |
| REST API                | Partially |
| Authentication          | Pending   |
| Unit Test               | Pending   |



### API Endpoints
- **Swagger UI**: [http://localhost:8080/dataingest/swagger-ui/index.html](http://localhost:8080/dataingest/swagger-ui/index.html)

`curl -X 'GET' \
'http://localhost:8080/dataingest/transaction/v1/transactions?page=0&size=10&sort=id' \
-H 'accept: */*'`