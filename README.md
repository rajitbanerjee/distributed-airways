# Distributed Airways

## Requirements

- [Docker](https://www.docker.com/products/docker-desktop)
- [JDK 8](https://www.oracle.com/ie/java/technologies/javase/javase8-archive-downloads.html) and [Apache Maven](https://maven.apache.org)
- [Node.js](https://nodejs.org/en/) [_Note_: This is only required if starting the development server for the `ui/` module. Not necessary when running the services with `docker-compose`.]
- [kubectl](https://kubernetes.io/docs/tasks/tools/) for Kubernetes deployment
- [minikube](https://minikube.sigs.k8s.io/docs/start/) for Kubernetes deployment

## Getting Started

The application can be run with either Docker Compose or Kubernetes (K8s).

### docker-compose

#### TLDR;

- Clean build and start:
  ```bash
  time ./clean-start.sh && sleep 25 && open http://localhost:1337
  ```
- [Optional] Quicker subsequent build alternative:
  ```bash
  docker-compose down && docker-compose up -d && sleep 25 && open http://localhost:1337
  ```

#### Running the services

- Compile and run all services in Docker (detached mode) using:

  ```bash
  ./clean-start.sh
  ```

  This is equivalent to running:

  ```bash
  mvn clean install
  docker-compose down --remove-orphans
  docker-compose build --no-cache
  docker-compose up -d
  ```

- To view the logs for any particular container, use:
  ```bash
  docker-compose logs $CONTAINER_NAME
  ```
- [Optional] To run the basic CLI client, use:
  ```bash
  mvn exec:java -pl cli-client
  ```
- To view the frontend, navigate to http://localhost:1337.

### Kubernetes

NGINX Ingress is not included by default so the following command must be run once:

```bash
minikube addons enable ingress
```

#### Running the cluster

- First make sure `minikube` is running:

  ```bash
  minikube start
  ```

- Spin up services (takes around 2-5 minutes):

  ```bash
  kubectl apply -f k8s
  ```

- For easy monitoring, use the Kubernetes dashboard:

  ```bash
  minikube dashboard
  ```

- On another terminal, enable minikube tunnelling to make service accessible on localhost:

  ```bash
  minikube tunnel
  ```

Frontend: [http://localhost/](http://localhost/)<br/>
Backend: [http://localhost/api/graphql/](http://localhost/api/graphql/)

#### Clean up

```bash
kubectl delete deployments --all
kubectl delete services --all
kubectl delete pods --all
kubectl delete daemonset --all
kubectl delete pvc -all
kubectl delete pv -all
```

#### FAQ

**Where are the images coming from?**

Images are uploaded to [Docker Hub](https://hub.docker.com/u/distributedairways) and pulled when the cluster is created.

After making changes to any module, the `docker.sh` script (found at the root of individual modules) can be executed to build and push the image to Docker Hub.

Docker Buildx is used to create multi-platform images (to support both amd64 and arm64 processors). Setup instructions can be found in the [official documentation](https://docs.docker.com/buildx/working-with-buildx/)

**How should the frontend/broker/individual airline services be scaled?**

In `k8s/` directory, the appropriate `.yaml` file's `replicas` attribute can be modified.

### Team

- [Ahmed Jouda](https://github.com/AhmedJouda2000)
- [Chee Guan (Jason) Tee](https://www.jasontcg.com)
- [Noor Bari](https://github.com/noorb98)
- [Rajit Banerjee](https://rajitbanerjee.com)

### Acknowledgements

- [Assoc. Prof. Rem Collier, UCD](https://people.ucd.ie/rem.collier)
