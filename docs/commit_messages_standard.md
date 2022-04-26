# Commit Messages Standard

### Allowed Syntax

\<type><(optional scope)>: \<subject><br>
\<optional body>

### Types
Allowed types are:
* `feat` - a commit that adds a new feature
* `fix` - a commit that fixes a bug
* `refactor` - a commit that rewrites/restructures code, however does not change any behaviour
* `perf` - a commit that improves performance
* `style` - a commit that doesn't affect the meaning (white-space, formatting, missing semi-colons, etc.)
* `test` - a commit that adds missing tests or corrects the existing ones
* `docs` - a commit that affects documentation only
* `build` - a commit that affects build components like build tool, CI/CD pipeline, dependencies, project version, etc.
* `ops` - a commit that affects operational components like infrastructure, deployment, backup, recovery, etc.
* `chore` - miscellaneous commits, e.g. modifying `.gitignore`

### Scopes
The scope:
* should provide additional contextual information
* is an optional part of the message
* can consist of 1-20 characters

### Subject
The subject:
* should contain a succinct description of the change
* is a mandatory part of the message
* can consist of 1-100 characters
* should start from a lower case English letter
* cannot end with a dot
* should be formulated in an imperative present tense: "change", not "changed" nor "changes"

### Body
The `body`:
* should include the motivation for the change and contrast this with previous behavior
* is an optional part of the message

### Examples
```
feat(shopping cart): add the amazing button

feat: remove ticket list endpoint
BREAKING CHANGES: ticket enpoints no longer supports list all entites

fix: add missing parameter to the service call
The error occurred because of <reasons>

build(release): bump version to 1.0.0

build: update dependencies

refactor: implement calculation method as recursion

style: remove empty line
```
