## Git Commit Hygiene

Create commits at these mandatory points:

- Mode switch
- Subtask start and end

### TDD Phase Commits (MANDATORY)

A git commit MUST be created after EACH TDD phase. No phase may proceed without committing the previous phase's changes first.

```
RED → git commit → GREEN → git commit → REFACTOR → git commit
```

| Phase | Commit Trigger | Commit Message Pattern |
|-------|---------------|----------------------|
| RED | Test written and verified as failing | `test: add failing test for [feature]` |
| GREEN | Minimal implementation passes test | `feat: implement [feature]` |
| REFACTOR | Code improved, all tests still pass | `refactor: improve [target]` |

**Rules:**
- Each phase MUST result in exactly ONE commit
- No phase may be skipped without a commit
- If a phase fails (BLOCKED), no commit is created for that phase
- Commits must be atomic: only changes from the current phase
- **NEVER use `git add --all` or `git add .`** — only stage files that were explicitly changed or created for the current task/feature. Use `git add <specific-file>` for each file individually

**Note**: For project-specific commit message format, see your project's `.roo/rules/*-commits.md`
