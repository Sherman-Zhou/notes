# suid

When an executable file with SUID is executed then the resulting process will have the permissions of the owner of the command, not the permissions of the user who executes the command

```
chmod 4XXX file
chmod u+s file

find /usr/bin/ -perm -4000

```

# guid

- If you set SGID on directories, all files or directories created inside that directory will be
  owned by the same group owner of the directory where SGID was configured.
- This is useful in creating shared directories, which are directories that are writable at
  the group level.

```
chmod 2XXX directory
chmod g+s directory

find / -perm -2000

```

# sticy bit

- The Sticky Bit is applied to directories.
- A user may only delete files that he owns or for which he has explicit write permission granted, even when he has write access to the directory.
- The sticky bit allows you to create a directory that everyone can use as a shared file storage. The files are protected because, no one can delete anyone else’s files

```
chmod 1XXX directory
chmod o+t directory

```
