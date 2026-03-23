# Linting as Part of Refactoring Phase

## Rule ID: LINT-001

**Priority**: MANDATORY  
**Applies to**: All Code Mode TDD tasks

## Linting Execution Point

Linting is part of the **REFACTOR phase**. After GREEN phase succeeds and before returning DONE, the REFACTOR phase MUST include linting and code quality checks.

## Handling Lint Failures during REFACTOR

### Auto-Fixable Errors

When linter reports fixable issues:

1. Run auto-fix on all fixable issues
2. Run format check/fix
3. Re-run tests to ensure fixes don't break functionality
4. Commit changes with linting fixes
5. Complete REFACTOR phase

### Non-Auto-Fixable Errors

When linter reports issues that cannot be automatically fixed:

1. Manually fix the violations in the source code
2. Re-run linting checks to confirm all issues are resolved
3. Re-run tests to ensure manual fixes don't break functionality
4. Commit changes
5. Complete REFACTOR phase

## REFACTOR Phase Updated Definition

**REFACTOR**: Improve code structure AND ensure code quality via linting

- Self-check: All tests still pass after changes AND no lint errors remain
- Module-specific linting commands: See module-specific skill documentation
- If tests break: Revert and report BLOCKED
- If lint check fails after auto-fix: BLOCKED (requires manual fix decision)
- If tests fail after lint fixes: BLOCKED (implementation needs review)

## Return Protocol Integration

When returning DONE status after successful TDD cycle with linting in REFACTOR:

```
STATUS: DONE
PHASES: RED ✓ | GREEN ✓ | REFACTOR ✓
TEST: [TestClassName.testMethodName] - PASS
FILES CHANGED:
- [list]
SUMMARY: [description]
```

## Enforcement

- Linting check is NOT optional – it is part of the REFACTOR phase
- A REFACTOR phase without linting verification and fixes is NOT complete
- Linting failures block completion of the TDD cycle
