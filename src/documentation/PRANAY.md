# Git Branching Strategy & Workflow

## 1. Overview

This project follows a structured Git branching strategy to maintain
clean code, organized development, and stable production releases.

We use three levels of branches:

1.  `master` -- Production branch
2.  `main` -- Development and integration branch
3.  `feature/*` -- Individual feature development branches

## 2. Branch Structure

``` text
master
  |
  └── main
       |
       ├── feature/dao-layer
       |
       ├── feature/service-layer
       |
       ├── feature/console-integration
       |
       └── feature/database-config
```

## 3. Branch Descriptions

  -----------------------------------------------------------------------
Branch                              Responsibility
  ----------------------------------- -----------------------------------
`master`                            Stable, production-ready code

`main`                              Integration of completed features

`feature/dao-layer`                 DAO and repository implementation

`feature/service-layer`             Business logic and service
implementation

`feature/console-integration`       Console-based application
integration

`feature/database-config`           Database configuration and
connectivity
  -----------------------------------------------------------------------

## 4. Branching Rules

-   Never develop directly on `master`.
-   Avoid direct development on `main`.
-   Create feature branches from `main`.
-   Each feature should have its own branch.
-   Push feature branches to GitHub regularly.
-   Merge completed features into `main` through Pull Requests.
-   Merge `main` into `master` only after successful integration and
    testing.
-   Resolve merge conflicts before completing a merge.

## 5. Initial Branch Setup

Create and push the required branches:

``` bash
# Switch to main
git switch main

# Create DAO branch
git switch -c feature/dao-layer
git push -u origin feature/dao-layer

# Create service branch
git switch main
git switch -c feature/service-layer
git push -u origin feature/service-layer

# Create console integration branch
git switch main
git switch -c feature/console-integration
git push -u origin feature/console-integration

# Create database configuration branch
git switch main
git switch -c feature/database-config
git push -u origin feature/database-config
```

## 6. Daily Development Workflow

Developers should follow these steps while working on a feature.

### Step 1: Switch to your feature branch

``` bash
git switch feature/dao-layer
```

### Step 2: Get the latest changes

``` bash
git fetch origin
git pull
```

### Step 3: Make code changes

Implement the required functionality.

### Step 4: Check modified files

``` bash
git status
```

### Step 5: Stage changes

``` bash
git add .
```

### Step 6: Commit changes

``` bash
git commit -m "feat: implement DAO layer"
```

### Step 7: Push changes

``` bash
git push
```

## 7. Merging Feature Branches into Main

Once a feature is completed:

1.  Push the feature branch to GitHub.
2.  Create a Pull Request on GitHub.
3.  Select `main` as the base branch.
4.  Select the feature branch as the compare branch.
5.  Review the changes.
6.  Merge the Pull Request after successful review and testing.

Example:

``` text
Base: main
Compare: feature/dao-layer
```

Repeat this process for all feature branches.

## 8. Releasing Code to Master

After all features have been integrated into `main` and tested:

1.  Create a Pull Request on GitHub.
2.  Select `master` as the base branch.
3.  Select `main` as the compare branch.
4.  Review and test the changes.
5.  Merge the Pull Request.

Example:

``` text
Base: master
Compare: main
```

This ensures that `master` always contains stable, tested code.

## 9. Useful Git Commands

Command                       Description
  ----------------------------- ---------------------------
`git branch`                  List local branches
`git branch -a`               List all branches
`git switch main`             Switch to main
`git switch -c branch-name`   Create and switch branch
`git status`                  Check working tree status
`git add .`                   Stage changes
`git commit -m "message"`     Commit changes
`git push`                    Push changes
`git pull`                    Pull latest changes
`git fetch --all`             Fetch remote updates
`git merge branch-name`       Merge another branch
`git branch -d branch-name`   Delete local branch

## 10. Commit Message Convention

Use meaningful commit messages with a consistent prefix.

Prefix        Purpose
  ------------- --------------------------------------------
`feat:`       Add a new feature
`fix:`        Fix a bug
`refactor:`   Improve code without changing its behavior
`config:`     Add or update configuration
`docs:`       Add or update documentation
`test:`       Add or update tests

Examples:

``` text
feat: implement DAO layer
feat: implement service layer
feat: integrate console interface
config: configure database connection
fix: resolve database connection issue
refactor: improve repository structure
docs: update project documentation
```

## 11. Important Guidelines

-   Always check the current branch using `git branch --show-current`.
-   Pull the latest changes before starting development.
-   Keep commits small and meaningful.
-   Do not push unfinished or untested code directly into `master`.
-   Use Pull Requests for merging.
-   Keep feature branches focused on individual responsibilities.
-   Test the application before merging into `master`.

## 12. Final Workflow Summary

``` text
        master
           |
           | Release after testing
           |
          main
           |
           | Pull Requests
           |
    -----------------------
    |         |           |
   DAO     Service     Console
    |         |           |
    -----------------------
           |
     Database Config
```

**Development Flow:**

`Feature Branch → Pull Request → Main → Testing → Pull Request → Master`

**Goal:** Maintain a clean, modular, maintainable, and production-ready
Git repository for the University Placement Spring Boot Project.
