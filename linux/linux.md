# Network
1. openssh server
  config file: /etc/ssh
2. iptables
3.  ssh, scp, rsync
4. wget `wget -P /download`
 
5. netstat, ss, lsof, telnet, nmap

```
  netstat -tupan
  lsof iTCP:22 -sTCP:LISTEN -nP

```

# System Admin
1. Task Automation and Scheduling:  cron/anacron
  generator:   https://crontab.guru/

```
 #user specific:  /var/spool/cron 
 crontab -e
 # list jobs for current user
 crontab -l

 # remove cron jobs for user
 sudo crontab -r -u username

```

2. file mount
  - df
  - mount/unmount
  - fdisk
  - gparted

3 hardware info
  - lshw: -html
  - lscpu

# service mngt

## systemd
- systemd-analyze