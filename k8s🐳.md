# kubectl
## describe
`kubectl describe deploy worker
## explain
## get (-w: watch)
```
#label
kubectl get pods -l app=rng
# selector
kubectl get pods --selector app=rng
```
    - all
    - namespace
    - pods
    - nodes
    - servers
## run
    - restart
      - =OnFailure
      - =Never
    

```
    # will create a spec in background
    kubectl run pingpone --image alpine ping 1.1.1.1
    kubectl run --restart=Never
     
```
## logs
```
    # 3rd party: https://github.com/wercker/stern
    logs xxx --tail 500 --follow
    logs -l run=pingpong --tail 10 --follow
```
## scale
```
kubectl scale deployment pingpone --replicas 3
```
## create
```
    kubectl create deployment httpenv --image=bretfisher/httpenv

    
```
## apply
imperative vs declarative

## delete
`kubectl delete -f xxx.yml

## expose:  service
```
    kubectl expose deployment httpenv --port 8888
     kubectl expose deploy/webui --type=NodePort --port=80

```
## attach
`kubectl attach --namespace=shpod -ti shpod`
## diff
`kubectl diff -f just-a-pod.yaml`
## label
```
kubectl label pods -l app=rng active=yes
# remove label
kubectl label pod -l app=rng,pod-template-hash active-
```
## edit daemonset
`kubectl edit daemonset rng`

## roleout
`kubectl rollout status deploy worker  `
`kubectl rollout undo deploy worker --to-revision=1`
`kubectl rollout history` 

## patch

## set
```
    kubectl set image deploy worker worker=dockercoins/worker:v0.2
```
## dump yml 
    ```
        kubectl get deploy/rng -o yaml >rng.yml
        kubectl create deployment web --image nginx -o yaml --dry-run=client
        kubectl apply -f web.yaml --server-dry-run --validate=false -o yaml
    ```

# k8s 
## Node types
    1. master: all through api server
        - api server: accept request, save in etcdS         
        - controller managment: get request from api server, create obj in etcd        
        - scheduler: decide where to create pod
        - etcd: database
    2. worker
       - kubelet: create pod
       - proxy
## Resource Type
### Service 
  - service
    - ClusterIP: default, only access in cluster
    - NodePort: high port range
    - LoadBalancer
    - ExternalName
    ```
        kubectl expose deployment httpenv --port 8888
        kubectl create service externalname k8s --external-name kubernetes.io

    ```
  - Ingresses
### pods
### deployment

### DaemonSet: run on every node 
    - kube-proxy
    - CNI plugin
    - monitoring agents

### events

### ConfigMap
```
    kubectl create configmap my-app-config --from-file=app.config
    kubectl create cm my-app-config --from-env-file=app.conf
    kubectl create configmap registry --from-literal=http.addr=0.0.0.0:80

apiVersion: v1
kind: Pod
metadata:
  name: haproxy
spec:
  volumes:
  - name: config
    configMap:
      name: haproxy
  containers:
  - name: haproxy
    image: haproxy:1
    volumeMounts:
    - name: config
      mountPath: /usr/local/etc/haproxy/


```

### secrets
```


```

# k8s yml
```
    apiVersion:  # find with "kubectl api-versions"
    kind:        # find with "kubectl api-resources"
    metadata:
    spec:        # find with "kubectl describe pod"
    kubectl explain <kind>.spec
    kubectl explain <kind> --recursive
```

# healthchecks
## hc type
1. liveness
    will be killed if dead
```
containers:
- name: ...
  image: ...
  livenessProbe:
    httpGet:
      path: /
      port: 80
    initialDelaySeconds: 30
    periodSeconds: 5
 
ontainers:
- name: redis
  image: custom-redis-image
  livenessProbe:
    exec:
      command:
      - /tini
      - -s
      - --
      - redis-cli
      - ping
    initialDelaySeconds: 30
    periodSeconds: 5

```
2. readiness
    if the container to ready to serve traffic
3. startup


## probe type
1. httpGet
2. TCP
3. exec: tini

## Ingress Controller
  1. Ingress Controller Pods
  2. Ingress Resource 
  3. Service for app
### nginx, 
### traefix



## others:
``

registry.aliyuncs.com/google_containers/

``               