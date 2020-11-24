# redis:  REmote Dictionary Server

# Redis通信协议 REdis Serialization Protocol （ RESP)
```
 *1＼r\n\$4\r\nPING\r\n
```

# 数据类型

Install:

centos:
1.  wget 
2. tar -zxvf
3. make install PREFIX=/usr/local/redis
4. cp redis.conf /usr/local/redis/bin
5. change daemonize no to yes in redis.conf
6. auto start
- add /etc/systemd/system/redis.service
    ```
    [Unit]
    Description=redis-server
    After=network.target

    [Service]
    Type=forking
    ExecStart=/usr/local/redis/bin/redis-server /usr/local/redis/bin/redis.conf
    PrivateTmp=true

    [Install]
    WantedBy=multi-user.target

    ```
-  reload
    ```
    systemctl daemon-reload

    systemctl start redis.service

    systemctl enable redis.service
    ```
7. redis-cli

     ln -s /usr/local/redis/bin/redis-cli /usr/bin/redis-cli