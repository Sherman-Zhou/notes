- Docker Image

  - Layers

- Container

- Dockerfile
  - FROM
  - RUN
  - COPY
  - USER
  - WORKDIR
  - ARG
     build time only, i.e. in Dockfile only
  - ENV(`--env on docker run`)
    ```
      ENV PORT 80
      EXPOSE $PORT
      docker run -e PORT=8080
    ```
  - ADD
  - VOLUMe
  - EXPOSE 
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
     
     //tip
     // less change cmd at the top of Docker files

```
- docker cmd:
`docker logs -n 5 -t containerName`
`docker history image-name`
`docker run -d -p 80:80 -name xxx`
`docker exec -it  containerName bash`
`docker ps -a`
`docker start containerName`
`docker rm -f containerName`
`docker cp file.txt container-name:/app/data`

- container related
`docker container prune`
`docker container rm -f $(docker container ls -aq)`

- Images related:
 
 `docker image save -o image.tar image`
 `docker image load -i image.tar`
 `docker image rm -f $(docker image ls -aq)`

- Volume related
  docker volume

  -- anonymous volume:
      ```
        #Dockfile:
        VOLUME [ "/app/node_modules" ]

        #cmd:
        docker run -d -v  /app/data myimage`

      ```
      will be removed once the container is deleted.
    -- named volumn
      `docker run -d -v myvolume:/app/data myimage`
    -- Bind Mounts: not managed by docker
    `docker run -d -v /home/myhome/data:/app/data myimage`
    `docker run -d -v $(pwd):/app/data myimage`

    -- readonly
    `docker run -d -v /home/myhome/data:/app/data:ro myimage`

```
    docker volumn [cmd]
        create      Create a volume
        inspect     Display detailed information on one or more volumes
        ls          List volumes
        prune       Remove all unused local volumes
        rm          Remove one or more volumes

    docker run -v /my/own/data:/data/db -d mongo
    //create volume of current work dir to /data/db 
    docker run -v $(pwd):/data/db -d mongo
```

- Network  
 - connect to host service
  `host.docker.internal`
 - create network
 ```
   docker network create mynetwork
   //use the network
   docker run --network mynetwork myimage

```

- j