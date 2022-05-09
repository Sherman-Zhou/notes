1. read from n line
   ` sed -n '2,$p' 1.txt`

2. user shell and all shells
   `echo $0`
   `cat /etc/shells`

3. find

```
find /etc/ -iname passwd 2>/dev/null

//find all files witch size > 100M
find / -type f -size +100M

find /var -type f -mtime 0 -ls

find /etc/ -type f -mtime 0 -exec cat {} \;

find /etc/ -type f -mtime -7 -exec cp {} /etc/backup \;

find /etc/ -type f -mtime -7 -ok cp {} /etc/backup \;
```

4. grep

```
 grep -in "SSH" /etc/ssh/*

 grep -R "xx" /var/

 ls -RF /etc/ | grep -v /| grep -v "^$"

```

5. tar, gzip

```
tar --exclude='*.mkv' --exclude='.config' -czvf etc.tar.gz /etc
tar -cjvf etc.tar.bz2 /etc

tar -cjvf etc-$(date =%F).tar.bz2 /etc

tar -xzvf etc.tar.gz -C /tmp/

```
