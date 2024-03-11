# crypto_recommendation_service

## Installation

To set up and run this project, follow these steps:

### Prerequisites

- Ensure you have [Docker](https://www.docker.com/) installed on your machine.

### Steps

1. **Clone the Repository**: Clone this repository to your local machine.
   git clone [URL_OF_THE_REPOSITORY]
   
2. **Open Terminal in Repository Directory**: Navigate to the cloned repository's directory in your terminal.

3. **Build the Docker Image**:
Run the following command to build the Docker image. This might take a few minutes to complete.
   docker-compose build

4. **Run the Application**:
Start the application using Docker Compose.
  docker-compose up

After completing these steps, the application should be running on your local machine.

## Making Requests

With the application running, you can make requests to it using tools like [Postman](https://www.postman.com/). Refer to the Swagger documentation for detailed API endpoints and specifications.

For example, to get metrics for Bitcoin (BTC), you can send a request to:

http://localhost:8020/api/crypto/metrics/BTC or http://localhost:8020/api/crypto/metrics/highest-range?date=2022-01-01T00:02:03Z for crypto metrics for certain dates

## Swagger Documentation

This project uses Swagger for its API documentation. To view the documentation:

1. Download the `swagger_doc.yaml` file from this repository.
2. Go to [Swagger Editor](https://editor.swagger.io/).
3. In the Swagger Editor, click on **File** > **Import file** and upload the `swagger_doc.yaml` file.

Alternatively, if you have a running instance of Swagger UI set up, you can point it to the URL of the `swagger_doc.yaml` file in this repository.


