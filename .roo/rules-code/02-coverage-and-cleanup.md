# Code Coverage and Dead Code Removal

## Rule ID: COV-CLEAN-001

**Priority**: MANDATORY
**Applies to**: All Code Mode tasks after REFACTOR phase

## Coverage Check After Implementation

After completing the REFACTOR phase of a TDD cycle, Code Mode MUST run a coverage check on the changed module.

### Commands

| Module | Command |
|--------|---------|
| Backend (Java) | `cd backend && ./mvnw jacoco:report -pl .` |
| Agent (Python) | `cd agent && uv run pytest --cov=agent --cov-report=term-missing` |

### Interpretation

- Uncovered code = missing business test OR dead code
- Coverage is a diagnostic tool, NOT a target metric
- Zero uncovered lines in new/changed code is the goal

## Dead Code Resolution

Every uncovered line in new or changed code MUST be resolved:

| Situation | Action |
|-----------|--------|
| Code fulfills a business requirement | Add a **functional test** that covers it |
| Code is not needed | **Remove it** |
| Framework/infrastructure boilerplate (e.g., `main()`, Spring config, `__init__.py`) | **Document** the exception in the return message |

### Decision Sequence

```
Uncovered line found
  → Does it fulfill a business requirement?
    → YES: Write functional test (BDD/integration)
    → NO: Is it framework/infrastructure boilerplate?
      → YES: Document exception
      → NO: DELETE the code
```

## Functional Tests Only — No Coverage-Driven Unit Tests

Tests MUST validate business behavior, not implementation details.

### Allowed Test Types for Coverage

- ✅ BDD/Gherkin feature tests (Cucumber, pytest-bdd)
- ✅ Integration tests that exercise real behavior
- ✅ Tests validating business logic from user perspective
- ✅ Contract tests for API boundaries

### Prohibited Test Types

- ❌ Unit tests whose sole purpose is increasing coverage percentage
- ❌ Mock-heavy tests that only verify internal wiring
- ❌ Tests that assert implementation details (method call order, internal state)
- ❌ Trivial getter/setter tests

### Litmus Test

Before writing a test to cover uncovered code, answer:

> "Does this test break when business behavior changes, or only when internal implementation changes?"

- Breaks on behavior change → ✅ Write it
- Breaks only on implementation change → ❌ Do not write it

## Return Protocol Integration

Coverage information MUST be included in the return message.

### DONE Status (all new/changed code covered)

```
STATUS: DONE
PHASES: RED ✓ | GREEN ✓ | REFACTOR ✓
COVERAGE: [module] — all new/changed lines covered
TEST: [TestClass.testMethod] - PASS
FILES CHANGED:
- [list]
SUMMARY: [description]
```

### PARTIAL Status (uncovered code remains)

```
STATUS: PARTIAL
PHASES: RED ✓ | GREEN ✓ | REFACTOR ✓
COVERAGE: [module] — uncovered lines detected
UNCOVERED:
- [file:line-range] — [reason: needs functional test | infrastructure exception]
COMPLETED: [what was done]
REMAINING: [functional tests needed or code to remove]
```

### Coverage Exceptions Format

When documenting allowed exceptions:

```
COVERAGE EXCEPTIONS:
- [file:line-range] — [reason, e.g., "Spring Boot main class", "Framework annotation processor"]
```

## Enforcement

- Coverage check is NOT optional — it is part of task completion
- A task with uncovered new/changed code and no documented exception is NOT done
- Code Mode does NOT decide whether uncovered code should stay — if uncertain, return PARTIAL and let Architect/Orchestrator decide
