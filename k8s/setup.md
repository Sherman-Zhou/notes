# os
- hostname
```
hostnamectl set-hostname k8s-master
hostnamectl set-hostname k8s-worker01
hostnamectl set-hostname k8s-worker02
```

-- hostname in master node

```
cat >> /etc/hosts << EOF
 192.168.213.100 k8s-master 
 192.168.213.101 k8s-worker01 
 192.168.213.102 k8s-worker02 
EOF

```
- add user to sudoer
```
cat >> /etc/sudoers <<EOF
sherman   ALL=(ALL)      NOPASSWD: ALL
EOF

```

- iptable
```
cat > /etc/sysctl.d/k8s.conf << EOF
 net.bridge.bridge-nf-call-ip6tables = 1
 net.bridge.bridge-nf-call-iptables = 1 
EOF

sysctl --system
```

# env
1. 卸载podman
centos8默认安装了podman容器，它和docker可能有冲突需要卸载掉

`sudo yum remove podman`
 
2. 关闭交换区
临时关闭
`cat sudo swapoff -a`
 
永久关闭
把/etc/fstab中的swap注释掉
`sudo sed -i 's/.*swap.*/#&/' /etc/fstab`

3. 禁用selinux
临时关闭
`setenforce 0`
 
永久关闭
`sudo sed -i "s/^SELINUX=enforcing/SELINUX=disabled/g" /etc/selinux/config`
 


4. 关闭防火墙
```
sudo systemctl stop firewalld.service
sudo systemctl disable firewalld.service
```
 
# install docker
[docker doc]!https://docs.docker.com/engine/install/centos/#install-from-a-package

or

 aliyun:

```
sudo yum install -y yum-utils device-mapper-persistent-data lvm2 net-tools
sudo yum-config-manager --add-repo https://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo
yum -y  install docker-ce docker-ce-cli containerd.io

## auto start docker
sudo systemctl enable docker
sudo systemctl start docker

cat > /etc/docker/daemon.json << EOF 
{
   "registry-mirrors" : ["https://mj9kvemk.mirror.aliyuncs.com"]
}
EOF



```

# install kubeadm, kubectl, kubelet

1. use k8s aliyun mirror
```
cat > /etc/yum.repos.d/kubernetes.repo << EOF
[kubernetes]
name=Kubernetes
baseurl=https://mirrors.aliyun.com/kubernetes/yum/repos/kubernetes-el7-x86_64/
enabled=1
gpgcheck=1
repo_gpgcheck=1
gpgkey=https://mirrors.aliyun.com/kubernetes/yum/doc/yum-key.gpg https://mirrors.aliyun.com/kubernetes/yum/doc/rpm-package-key.gpg
EOF
```
2. install
```
yum install -y kubelet kubeadm kubectl
systemctl enable kubelet
sudo systemctl start kubelet
# status error checking 
systemctl status kubelet
journalctl -xeu kubeletd

kubeadm version -o json
kubectl version --client -o json
kubelet --version

### to fix: kubelet cgroup driver: systemd  is different from docker cgroup driver: cgroupfs
vi /etc/sysconfig/kubelet 
KUBELET_EXTRA_ARGS=--cgroup-driver=cgroupfs

# or change docker cgroup driver:
vi /usr/lib/systemd/system/docker.servic
### append --exec-opt native.cgroupdriver=systemd at the end of ExecStart
systemctl daemon-reload
systemctl restart docker
```

3. init k8s master
```
kubeadm init \
--apiserver-advertise-address=192.168.213.100 \
--image-repository registry.aliyuncs.com/google_containers \
--kubernetes-version v1.22.1 \
--service-cidr=10.96.0.0/12 \
--pod-network-cidr=10.244.0.0/16

# 查看image
kubeadm config images list

# change the image coredns tag...

docker tag registry.aliyuncs.com/google_containers/coredns:1.8.4  registry.aliyuncs.com/google_containers/coredns:v1.8.4

#查看 component status
kubectl get componentStatus

#解决connection refused 问题：
   Get http://127.0.0.1:10251/healthz: dial tcp 127.0.0.1:10251: connect: connection refused

```

4. init worker node
```
kubeadm join 192.168.213.100:6443 --token w0wb3z.emfvqq8crgyoqfk9 \
        --discovery-token-ca-cert-hash sha256:4a29847057473f2bcf46472abc69044a3bf17cb3ad9818d782e3ad34f6804c2d


  vim /etc/kubernetes/manifests/kube-scheduler.yaml


    - command:
    - kube-scheduler
    - --authentication-kubeconfig=/etc/kubernetes/scheduler.conf
    - --authorization-kubeconfig=/etc/kubernetes/scheduler.conf
    - --bind-address=127.0.0.1
    - --kubeconfig=/etc/kubernetes/scheduler.conf
    - --leader-elect=true
#   - --port=0                  ## 注释掉这行
```

4. connect as non-root
```
 mkdir -p $HOME/.kube
 sudo cp -i /etc/kubernetes/admin.conf $HOME/.kube/config
 sudo chown $(id -u):$(id -g) $HOME/.kube/config

```


