curl -X POST "http://localhost:8080/api/invoices" \
-H "Content-Type: application/json" \
-H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0dXNlciIsImlhdCI6MTc0MTYxNTU1MiwiZXhwIjoxNzQxNzAxOTUyfQ.xjpu-KCSwTFJ8zf4H2TzrPP-LUUfdZSA4BGBiwXwKQG7gDsg83HcI7LHZQR9lMGFRPQSCXWa8k7xDMcrSfYiSQ" \
-d '{
"invoiceNumber": "INV-20230820-001",
"invoiceDate": "2023-08-20",
"amount": 9999.99
}'