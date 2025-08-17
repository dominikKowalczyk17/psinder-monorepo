# Psinder Monorepo

A monorepo containing both the Psinder mobile app (React Native) and backend API (Spring Boot).

## Project Structure

```
psinder-monorepo/
├── mobile/          # React Native mobile app
├── api/             # Spring Boot backend API
├── .github/         # GitHub Actions workflows
├── package.json     # Root workspace configuration
└── README.md        # This file
```

## Getting Started

### Prerequisites

- Node.js >= 18.0.0
- npm >= 8.0.0
- Java 17+ (for Spring Boot API)
- Maven 3.6+ (for Spring Boot API)
- React Native development environment (for mobile)

### Installation

```bash
# Install all dependencies for both workspaces
npm run install:all
```

### Development

```bash
# Run both mobile and API in development mode
npm run dev

# Run mobile app only
npm run mobile:dev

# Run API only
npm run api:dev
```

### Building

```bash
# Build both projects
npm run build

# Build mobile app only
npm run mobile:build

# Build API only
npm run api:build
```

### Testing

```bash
# Run tests for both projects
npm run test

# Run mobile tests only
npm run mobile:test

# Run API tests only
npm run api:test
```

## GitHub Subtrees

This monorepo is set up to use GitHub subtrees for managing the separate repositories:

### Adding Subtrees (for future reference)

```bash
# Add mobile app subtree
git subtree add --prefix=mobile git@github.com:yourusername/psinder.git main --squash

# Add API subtree
git subtree add --prefix=api git@github.com:yourusername/psinder-app.git main --squash
```

### Pulling Updates from Subtrees

```bash
# Pull updates from mobile app
git subtree pull --prefix=mobile git@github.com:yourusername/psinder.git main --squash

# Pull updates from API
git subtree pull --prefix=api git@github.com:yourusername/psinder-app.git main --squash
```

### Pushing Changes to Subtrees

```bash
# Push mobile changes back to original repo
git subtree push --prefix=mobile git@github.com:yourusername/psinder.git main

# Push API changes back to original repo
git subtree push --prefix=api git@github.com:yourusername/psinder-app.git main
```

## CI/CD

The project includes GitHub Actions workflows for:
- Building and testing both mobile and API
- Running linting and code quality checks
- Deployment automation (when configured)

## Workspace Scripts

The root `package.json` includes convenient scripts for managing both workspaces:

- `npm run dev` - Run both mobile and API in development
- `npm run build` - Build both projects
- `npm run test` - Test both projects
- `npm run lint` - Lint mobile project
- `npm run clean` - Clean both projects
- `npm run install:all` - Install dependencies for both projects

## Contributing

1. Make changes in the appropriate workspace (`mobile/` or `api/`)
2. Test your changes locally
3. Commit changes to the monorepo
4. Use subtree commands to sync with individual repositories if needed