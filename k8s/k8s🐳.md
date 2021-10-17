# kubectl cmd
  `kubectl [cmd] [type] [name] [flags]

## cluster-info
## config view
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

## check the user if can do
`kubectl auth can-i create deployments --as linda --namespace secret`

## dump yml 
    ```
        kubectl get deploy/rng -o yaml >rng.yml
        kubectl create deployment web --image nginx -o yaml --dry-run=client
        kubectl apply -f web.yaml --server-dry-run --validate=false -o yaml
    ```
## taint node key=value

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

## core objects
- pod
- upgrade
- replication
- deployment/DaemonSet/StatefulSet
- service
- pv
- pvc

## Resource Type
 `kubectl api-resources`
### Service 
   Service Discovery,   Load Balance...

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

  headless service: ClusterIP = None
    
### StatefulSet
  port domain name:
    name.servericeName.namespace.svc.cluster.local

### DaemonSet: run on every node 
    - kube-proxy
    - CNI plugin
    - monitoring agents

## Job, CronJob


### pods
  1. imagePullPolicy

    - Always
    - IfNotPresent
    - Never

  2. Resources
    cpu= value/1000
    - requests
      ```
        memory: "64Mi"
        cpu: "250m"
      ```
    - limits
      ```
        memory: "128Mi"
        cpu: "500m"
      ```
  3. restartPolicy

    - Always
    - OnFailure
    - Never

4. healthchecks
  - probe type
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


  -  probe type
    1. httpGet
    2. tcpSocket
    3. exec: tini

### scheduler 
 - nodeSelector
 - affinity --> nodeAffinity(亲和性)
   - 硬亲和性
   `requiredDuringSchedulingIngoredDuringExecution`
   - 软亲和性
    `preferredDuringSchedulingIngoredDuringExecution`
 - Node Taint (污点、污点容忍) - node property
 ` kubectl taint node env=NoSchedule`
  - value
    1. NoSchedule
    2. PreferNoSchedule
    3. NoExecute

  - 场景
   1. 专有节点
   2. 特定硬件节点
   3. 基于Taint驱逐

  


### Controller
 mngt and run pods
 controller -> selector (label) -> pods

 1. deployment
  create deployment via cmd or
    `kubectl create deployment name --image=nginx`
  change deployment image:
      `kubectl set image deployment name nginx=nginx:1.1.5`


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
apiVersion: v1 
kind: Secret 
metadata: 
  name: mysecret 
  type: Opaque 
  data: 
     password: MWYyZDFlMmU2N2Rm 
     username: YWRtaW4=

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



## Ingress Controller
  1. Ingress Controller Pods
  2. Ingress Resource 
  3. Service for app
### nginx, 
### traefix


## RBAC

1. Role ClusterRole, Role

```
  kind: Role
  apiVersion: rbac.authorization.k8s.io/v1
  metadata:
    namespace: ctnrs
    name: pod-reader
  rules:
  - apiGroups: [""] # "" indicates the core API group
    resources: ["pods"]
    verbs: ["get", "watch", "list"]
```

2. User, User Group, ServiceAccount
3. Role Binding

```
  kind: RoleBinding
  apiVersion: rbac.authorization.k8s.io/v1
  metadata:
    name: read-pods
    namespace: roletest
  subjects:
  - kind: User
    name: lucy # Name is case sensitive
    apiGroup: rbac.authorization.k8s.io
  roleRef:
    kind: Role #this must be Role or ClusterRole
    name: pod-reader # this must match the name of the Role or ClusterRole you wish to bind to
    apiGroup: rbac.authorization.k8s.io
```

## others:
``

registry.aliyuncs.com/google_containers/

``               
