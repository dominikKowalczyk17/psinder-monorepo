# GitHub Subtree Setup Guide

This document provides step-by-step instructions for setting up and managing GitHub subtrees in this monorepo.

## Initial Setup (One-time)

### 1. Initialize the monorepo as a git repository

```bash
cd psinder-monorepo
git init
git add .
git commit -m "Initial monorepo setup"
```

### 2. Create remote repository

Create a new repository on GitHub called `psinder-monorepo` and add it as remote:

```bash
git remote add origin git@github.com:yourusername/psinder-monorepo.git
git push -u origin main
```

### 3. Add existing repositories as subtrees

```bash
# Add mobile app subtree (from psinder repo)
git subtree add --prefix=mobile git@github.com:yourusername/psinder.git main --squash

# Add API subtree (from psinder-app repo)
git subtree add --prefix=api git@github.com:yourusername/psinder-app.git main --squash
```

## Daily Workflow

### Working in the monorepo

1. Make changes in either `mobile/` or `api/` directories
2. Commit changes to the monorepo:
   ```bash
   git add .
   git commit -m "Your commit message"
   git push origin main
   ```

### Pulling updates from individual repositories

If changes are made directly to the individual repositories:

```bash
# Pull changes from mobile repo
git subtree pull --prefix=mobile git@github.com:yourusername/psinder.git main --squash

# Pull changes from API repo
git subtree pull --prefix=api git@github.com:yourusername/psinder-app.git main --squash
```

### Pushing changes back to individual repositories

To sync changes back to the original repositories:

```bash
# Push mobile changes back to psinder repo
git subtree push --prefix=mobile git@github.com:yourusername/psinder.git main

# Push API changes back to psinder-app repo
git subtree push --prefix=api git@github.com:yourusername/psinder-app.git main
```

## Setting up remotes for easier management

Add remotes for easier subtree management:

```bash
# Add remotes
git remote add mobile git@github.com:yourusername/psinder.git
git remote add api git@github.com:yourusername/psinder-app.git

# Now you can use shorter commands:
git subtree pull --prefix=mobile mobile main --squash
git subtree push --prefix=mobile mobile main

git subtree pull --prefix=api api main --squash
git subtree push --prefix=api api main
```

## CI/CD Considerations

- The monorepo CI/CD will run for all changes
- Individual repository CI/CD will run when changes are pushed back via subtree
- Make sure both CI/CD systems are compatible
- Consider disabling some checks on individual repos if they're covered by monorepo CI

## Best Practices

1. **Primary development in monorepo**: Do most development in the monorepo for better coordination
2. **Regular syncing**: Regularly push changes back to individual repos to keep them updated
3. **Consistent branching**: Use consistent branch names across all repositories
4. **Documentation**: Keep this guide updated with any workflow changes
5. **Team communication**: Ensure all team members understand the subtree workflow

## Troubleshooting

### Merge conflicts during subtree operations

If you encounter conflicts:

1. Resolve conflicts manually
2. Complete the merge: `git commit`
3. Continue with your workflow

### Divergent histories

If histories have diverged significantly:

```bash
# Force push to individual repo (use with caution)
git subtree push --prefix=mobile mobile main --force
```

### Starting fresh

If subtree gets too complicated, you can remove and re-add:

```bash
# Remove the subtree directory
rm -rf mobile/
git add -A
git commit -m "Remove mobile subtree"

# Re-add as fresh subtree
git subtree add --prefix=mobile mobile main --squash
```