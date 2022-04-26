# Hooks Installation

1. By default, git hooks are contained in the folder `/.git/hooks` and are stored locally.


2. To share git hooks they should be stored globally in a repository in a separate folder, i.e. they cannot be stored in the folder `/.git/hooks`.


3. This project has customized git hooks that are stored in the `/.git-hooks` folder, i.e. outside the default folder for git hooks. 


4. To change the folder in which git hooks for a given project are stored, the custom folder with git hooks should be specified inside the `/.git/config` file. This file cannot be shared globally and can be modified only locally. For that reason, in order to enable customized git hooks for this project that are stored in the `/.git-hooks` folder, specify that folder inside the `/.git/config` file by running locally on the `master` branch the following command:<br>
`git config core.hooksPath '.git-hooks'`


5. After running the above command customized git hooks for this project will be enabled. This configuration will be lost in case of removing the `/.git/config` file locally, i.a. in case of removing the repository locally. In such situation the configuration should be repeated (i.e. you should run the command `git config core.hooksPath '.git-hooks'` once again).
