- Docker Image

  - Layers

- Container

- Dockerfile
  - FROM
  - RUN
  - ENV
  - ADD
  - VOLUMN
  - ENTRYPOINT or CMD

```
    /**
    ENTRYPOINT，表示镜像在初始化时需要执行的命令，不可被重写覆盖，需谨记
    CMD，表示镜像运行默认参数，可被重写覆盖( -it cmd)
    ENTRYPOINT/CMD都只能在文件中存在一次，并且最后一个生效 多个存在，只有最后一个生效，其它无效！
    */

    //exec format
    ENTRYPOINT ["executable", "param1", "param2"]

    //shell format
    ENTRYPOINT command param1 param2

    CMD ["executable","param1","param2"] (exec 格式，推荐用此格式)
    CMD ["param1","param2"] (作为ENTRYPOINT 的默认参数)
    CMD command param1 param2 (shell 格式)


```

- Volume
  docker volume

```
    docker volumn [cmd]
        create      Create a volume
        inspect     Display detailed information on one or more volumes
        ls          List volumes
        prune       Remove all unused local volumes
        rm          Remove one or more volumes

    docker run -v /my/own/data:/data/db -d mongo
```

- Network

-
