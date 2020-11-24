wget -m -e robots=off -k -E "https://5kxkzw.axshare.com/"

查看开放的端口号 firewall-cmd --list-all
设置开放的端口号
 firewall-cmd --add-service=http --permanent
 firewall-cmd --add-port=80/tcp --permanent
重启防火墙 firewall-cmd -reload