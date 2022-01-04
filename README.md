# Distributed Airways

## Requirements

- Docker Desktop for Mac/Windows
- JDK 8 and Apache Maven
- Node.js [_Note_: This is only required if starting the development server for the `ui/` module. Not necessary when running the services with `docker-compose`.]
- [kubectl](https://kubernetes.io/docs/tasks/tools/) for kubernetes deployment
- [minikube](https://minikube.sigs.k8s.io/docs/start/) for kubernetes deployment
## Getting Started
### docker-compose
- TLDR;

  - Clean build and start:
    ```
    time ./clean-start.sh && sleep 25 && open http://localhost:1337
    ```
  - [Optional] Quicker subsequent build alternative:

    ```
    docker-compose down && docker-compose up -d && sleep 25 && open http://localhost:1337
    ```

- Compile and run all services in Docker (detached mode) using:

  ```
  ./clean-start.sh
  ```

  This is equivalent to running:

  ```
  mvn clean install
  docker-compose down --remove-orphans
  docker-compose build --no-cache
  docker-compose up -d
  ```

- To view the logs for any particular container, use:
  ```
  docker-compose logs $CONTAINER_NAME
  ```
- [Optional] To run the basic CLI client, use:
  ```
  mvn exec:java -pl cli-client
  ```
- To view the frontend, navigate to http://localhost:1337.

### Kubernetes

Nginx ingress is not included by default so the following command must be run once:
```bash
minikube addons enable ingress
```
#### Running the cluster
First make sure minikube is running:
```bash
minikube start
```
Spin up services (takes around 2 - 5 minutes):
```bash
kubectl apply -f k8s 
```
For easy monitoring, use the kubernetes dashboard:
```bash
minikube dashboard
```

On another terminal, enable minikube tunnelling to make service accessible on localhost:
```bash
minikube tunnel
```

Frontend: [http://localhost/](http://localhost/)

Backend: [http://localhost/api/graphql/](http://localhost/api/graphql/)

#### Clean up
```bash
kubectl delete deployments --all                                                                                                                                                                                         ─╯
kubectl delete services --all
kubectl delete pods --all
kubectl delete daemonset --all
kubectl delete pvc -all 
kubectl delete pv -all 
```

#### FAQ

**Where are the images coming from?**

Images are uploaded to [dockerhub](https://hub.docker.com/u/distributedairways) and pulled when the cluster is created. 

**How do I scale the frontend/broker/individual airline services?**

Go to the `k8s` folder, find the correct `.yaml` file and modify the `replicas` attribute.

### Team

- [Ahmed Jouda](https://github.com/AhmedJouda2000)
- [Chee Guan (Jason) Tee](https://www.jasontcg.com)
- [Noor Bari](https://github.com/noorb98)
- [Rajit Banerjee](https://rajitbanerjee.com)

### Acknowledgements

- [Assoc. Prof. Rem Collier, UCD](https://people.ucd.ie/rem.collier)
