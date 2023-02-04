# clojeil

Simple RabbitMQ-handled two-sided pub/sub communication demo 

## Installation

Clone the repo from https://github.com/danorel/clojeil

## Usage

1. Setup RabbitMQ locally using Docker.

```shell
docker run -d -p 5672:5672 -p 15672:15672 --hostname rabbitmq --name rabbitmq -e RABBITMQ_DEFAULT_USER=guest -e RABBITMQ_DEFAULT_PASS=pass rabbitmq:3-management
```

2. Run RabbitMQ publisher node:
```shell
lein publisher
```

3. Run RabbitMQ consumer node:
```shell
lein consumer
```
