# basic

- 初始化一个Git仓库，使用git init命令。
- 使用命令git add file ，注意，可反复多次使用，添加多个文件；
- 使用命令git commit -m  message ，完成
- 要随时掌握工作区的状态，使用git status
- 用git diff可以查看修改内容。
- HEAD指向的版本就是当前版本，因此，Git允许我们在版本的历史之间穿梭，使用命令git reset --hard commit_id。

- 穿梭前，用git log可以查看提交历史，以便确定要回退到哪个版本。(--oneline)

- 要重返未来，用git reflog查看命令历史，以便确定要回到未来的哪个版本。
- 当你改乱了工作区某个文件的内容，想直接丢弃工作区的修改时，用命令git checkout -- file。
- 当你不但改乱了工作区某个文件的内容，还添加到了暂存区时，想丢弃修改，分两步，第一步用命令git reset HEAD  file ，就回到了场景1，第二步按场景1操作
- 命令git rm用于删除一个文件

# remote repo

 - 关联一个远程库，使用命令git remote add origin git@server-name:path/repo-name.git；
 - git push -u origin master第一次推送master分支的所有内容
 - 每次本地提交后，只要有必要，就可以使用命令git push origin master
 - 查看远程库信息，使用git remote -v
 - 从本地推送分支，使用git push origin branch-name
 - 在本地创建和远程分支对应的分支，使用git checkout -b branch-name origin/branch-name
 - 建立本地分支和远程分支的关联，使用git branch --set-upstream branch-name origin/branch-name
 - 从远程pull分支 git pull origin branchname

# branch
- 查看分支：git branch

- 创建分支：git branch name

- 切换分支：git checkout name或者git switch name

- 创建+切换分支：git checkout -b name或者git switch -c name

- 合并某分支到当前分支：git merge name
- 冲突后取消合并： git merge --abort

- 删除分支：git branch -d name
- 合并: git merge branch
- 查看分支合并图,  git log --graph --pretty=oneline --abbrev-commit
   合并分支时，加上--no-ff参数就可以用普通模式合并，合并后的历史有分支，能看出来曾经做过合并，而fast forward合并就看不出来曾经做过合并
- git stash 暂存改动， git stash pop恢复改动；
- 复制单个commit： git cherry-pick cimmit-id
- rebase操作可以把本地未push的分叉提交历史整理成直线；

# tag

- 命令git tag  tagname 用于新建一个标签，默认为HEAD，也可以指定一个commit id；

- 命令git tag -a tagname  -m "blablabla..."可以指定标签信息；

- 命令git tag可以查看所有标签

- 命令git push origin tagname可以推送一个本地标签；

- 命令git push origin --tags可以推送全部未推送过的本地标签；

- 命令git tag -d tagname可以删除一个本地标签；

- 命令git push origin :refs/tags/tagname可以删除一个远程标签。

# alias

 git config --global alias.lg "log --color --graph --pretty=format:'%Cred%h%Creset -%C(yellow)%d%Creset %s %Cgreen(%cr) %C(bold blue)<%an>%Creset' --abbrev-commit"

 # git log中文显示：
 1.  增加环境变量 LESSCHARSET=utf-8
 2.  git config
    
    git config --global core.quotepath false 
    git config --global gui.encoding utf-8
    git config --global i18n.commit.encoding utf-8 
    git config --global i18n.logoutputencoding utf-8 