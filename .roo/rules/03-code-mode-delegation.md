# Code Mode Delegation

## Subtask Hierarchy

Code Mode is EXCLUSIVELY invoked via subtasks (`new_task`) by the **Orchestrator**:

```
Orchestrator → Code Mode (via new_task)
```

- **ONLY the Orchestrator** starts Code Mode subtasks
- The **Architect** plans the tasks, the **Orchestrator** delegates them as subtasks to Code Mode
- Code Mode executes the task and reports results via `attempt_completion`
- No other mode starts subtasks – only the Orchestrator

### Why Subtasks Instead of Mode Switch?
- Clean, bounded context per task
- Clear result reporting via `attempt_completion`
- Traceable task chain
- Orchestrator maintains the overall overview

## Principle

Code Mode uses a smaller, more efficient model. It is optimized for **execution**, not **thinking**.

## When to Switch to Code Mode

Code Mode is suitable for **explicit, atomic tasks**.

### Suitable Tasks (→ Code Mode)

- ✅ "Create class `XyzService` with method `process()`"
- ✅ "Add test `testCalculateTotal()` to class `OrderServiceTest`"
- ✅ "Implement method `findById()` in repository"
- ✅ "Run tests in module `xyz-service`"
- ✅ "Refactor `processData()` to extract helper method `validateInput()`"
- ✅ "Add field `status` to entity `Order`"

### Unsuitable Tasks (→ stay in Orchestrator and delegate to suitable mode)

- ❌ "Implement feature XYZ" (too vague - decompose first in Architect Mode)
- ❌ "Analyze the best solution" (thinking - in Orchestrator, Project Research and Architect Mode)
- ❌ "Plan the implementation" (planning - in Architect Mode)
- ❌ "Decide between option A and B" (decision)
- ❌ "Research how to..." (research)

## Task Routing Criteria

### Route to Code Mode when ALL apply:

- [ ] Task is atomic (single action)
- [ ] Location is precisely specified
- [ ] Success criterion is testable
- [ ] No design decisions required

### Stay in Architect Mode when ANY applies:

- [ ] Task requires decomposition
- [ ] Multiple valid implementations exist
- [ ] Trade-off decision needed
- [ ] Research or analysis required
- [ ] Task specification is ambiguous

## Handoff Protocol

When delegating to Code Mode, transfer the following state:

### Required Context

1. **Goal**: What should be achieved (one sentence)
2. **Location**: Exact file/class/method path
3. **Specification**: Implementation details or constraints
4. **Verification**: How to verify success (test command or assertion)
5. **Relevant Context**: Max 5 lines of code or information needed to understand the task

### Handoff Message Template

```
TASK: [Create|Modify|Delete|Run] [target]
LOCATION: [path/to/file.java] > [ClassName] > [methodName]
SPEC: [Implementation specification]
VERIFY: [Test command or success criterion]
CONTEXT:
- [Relevant fact 1]
- [Relevant fact 2]
```

### Example

```
TASK: Create test for OrderValidator
LOCATION: src/test/java/domain/validation/OrderValidatorTest.java
SPEC: Test that validate() returns INVALID when Order.items is empty
VERIFY: ./gradlew test --tests OrderValidatorTest.testEmptyItemsReturnsInvalid
CONTEXT:
- ValidationResult has states: VALID, INVALID
- Order.getItems() returns List<OrderItem>
```

## Return Protocol

After task completion, Code Mode MUST return:

### Success Response

```
STATUS: DONE
RESULT: [What was created/modified/deleted]
FILES: [List of changed files]
TESTS: [Test results - PASS/FAIL with count]
NEXT: [Suggested next step or NONE]
```

### Failure Response

```
STATUS: BLOCKED
REASON: [Why the task could not be completed]
ATTEMPTED: [What was tried]
NEED: [What information or decision is required]
```

### Partial Completion

```
STATUS: PARTIAL
COMPLETED: [What was done]
REMAINING: [What is still open]
BLOCKER: [What prevents completion]
```

## Fallback Mechanisms

### On Compilation Error

1. Code Mode attempts fix (max 2 iterations)
2. If unresolved: Return BLOCKED with error details
3. Architect Mode analyzes and provides corrected specification

### On Test Failure

1. Code Mode reports failing test with assertion details
2. Does NOT attempt fix without explicit instruction
3. Architect Mode decides: fix implementation or adjust test

### On Ambiguity

1. Code Mode does NOT guess
2. Returns BLOCKED with specific question
3. Architect Mode clarifies and re-delegates

### Escalation Threshold

After 3 failed attempts at same task: Escalate to user with full context.

## TDD Phases - Autonomous Progression

Code Mode executes TDD phases autonomously when successful. Returns to Architect Mode only on completion or blocker.

### Execution Flow

```
RED → [test fails?] → COMMIT → GREEN → [test passes?] → COMMIT → REFACTOR → [tests still pass?] → COMMIT → DONE
         ↓ no                     ↓ no                              ↓ no
       BLOCKED                  BLOCKED                           BLOCKED
```

**MANDATORY**: A git commit MUST be created after each successful phase (see `rules/02-commits.md`).

### Phase Definitions

1. **RED**: Create ONE failing test
   - Self-check: Test compiles AND fails with expected assertion error
   - If test passes unexpectedly: BLOCKED (test may be wrong or feature already exists)
   - **On success: git commit** (`test: add failing test for [feature]`)

2. **GREEN**: Minimal implementation to pass test
   - Self-check: Target test passes AND no other tests broken
   - If cannot pass within 3 attempts: BLOCKED
   - **On success: git commit** (`feat: implement [feature]`)

3. **REFACTOR**: Improve code structure AND ensure code quality via linting
   - Self-check: All tests still pass after changes AND no lint errors remain (see `rules-code/03-linting.md`)
   - If tests break: Revert and report BLOCKED
   - If lint errors remain after auto-fix: BLOCKED (requires manual decision)
   - **On success: git commit** (`refactor: improve [target]`)

### Return Conditions

| Condition | Action |
|-----------|--------|
| All 3 phases complete successfully | Return DONE with summary |
| Any phase fails self-check | Return BLOCKED with details |
| Ambiguity in specification | Return BLOCKED with specific question |
| Compilation error after 2 fix attempts | Return BLOCKED with error |

### Return Message Format

#### On Success (DONE)

```
STATUS: DONE
PHASES: RED ✓ | GREEN ✓ | REFACTOR ✓
TEST: [TestClassName.testMethodName] - PASS
FILES CHANGED:
- [path/to/TestFile.java] (created)
- [path/to/Implementation.java] (modified)
SUMMARY: [One sentence describing what was implemented]
```

#### On Blocker (BLOCKED)

```
STATUS: BLOCKED
PHASE: [RED|GREEN|REFACTOR]
REASON: [Specific reason]
ATTEMPTED: [What was tried]
NEED: [What decision or information is required]
```

### Delegation Format for TDD Task

When delegating a TDD task, specify:

```
TDD TASK: [Feature/behavior to implement]
LOCATION: [target package/class]
SPEC: [Implementation requirements]
TEST NAME: [Suggested test method name]
VERIFY: [Additional verification command if needed]
```

### Example

Delegation:

```
TDD TASK: Validate that Order rejects empty items list
LOCATION: domain/validation/OrderValidator
SPEC: validate(Order) returns ValidationResult.INVALID when order.getItems().isEmpty()
TEST NAME: testValidateReturnsInvalidWhenItemsEmpty
VERIFY: ./gradlew test --tests OrderValidatorTest
```

Return:

```
STATUS: DONE
PHASES: RED ✓ | GREEN ✓ | REFACTOR ✓
TEST: OrderValidatorTest.testValidateReturnsInvalidWhenItemsEmpty - PASS
FILES CHANGED:
- src/test/java/domain/validation/OrderValidatorTest.java (created)
- src/main/java/domain/validation/OrderValidator.java (created)
- src/main/java/domain/validation/ValidationResult.java (created)
SUMMARY: Implemented OrderValidator with validation for empty items list
```

### Clarification: Test Selection

The rule "ask user which test to write first" (from Senior Java Developer rules) applies to:

- Initial test of a new feature (before first RED phase)
- When multiple valid next tests exist after completing a TDD cycle

Does NOT apply to:

- Continuation within a delegated TDD task with specified TEST NAME
- Obvious next test in a sequence (e.g., edge cases after happy path)
