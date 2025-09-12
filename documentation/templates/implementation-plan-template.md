# Implementation Plan: [FEATURE_NAME]

## Metadata

- **Story ID**: ACME-XXX
- **Created**: YYYY-MM-DD
- **Status**: DRAFT | IN_PROGRESS | COMPLETED
- **Estimated Effort**: X story points / hours
- **Developer**: [Agent/Developer Name]

## Business Context

### Business Value

[Clear description of WHY this feature is needed and what value it delivers to the business/users]

### Feature Description

[Detailed description of WHAT needs to be implemented, including context and constraints]

### Acceptance Criteria

- [ ] AC1: [Specific, measurable criterion]
- [ ] AC2: [Specific, measurable criterion]
- [ ] AC3: [Specific, measurable criterion]

## Technical Analysis

### Architecture Alignment

- **Domain**: [Which domain/bounded context]
- **Use Cases**: [List of use cases to implement]
- **Adapters Required**: [List of adapters needed]
- **Documentation to Review**:
    - [ ] @/documentation/*
    - [ ] @/documentation/ADRs/*
    - [ ] @/README.adoc
    - [ ] Wiki: [Link to relevant pages]

### Dependencies

- **External Systems**: [List any external dependencies]
- **Internal Components**: [List internal dependencies]
- **New Libraries**: [List if any new dependencies needed]

## Implementation Steps

### Phase 1: Domain Model Foundation

```
INSTRUCTION: Start with domain model using factory methods only
```

- [ ] **Step 1.1**: Review existing domain model in @/domain/
- [ ] **Step 1.2**: Design new domain entities/value objects needed
- [ ] **Step 1.3**: Create factory methods with meaningful names (e.g., `neu()`, `ausBestehenderQuelle()`)
- [ ] **Step 1.4**: Ensure all invariants are enforced

### Phase 2: Use Case Implementation (TDD)

```
INSTRUCTION: Implement each use case with TDD - one test at a time
```

- [ ] **Step 2.1**: Create Cucumber feature file for use case
    - Feature description
    - Scenario outline
    - Reuse existing steps where possible
- [ ] **Step 2.2**: Run test - verify RED phase
- [ ] **Step 2.3**: Implement minimal use case code
- [ ] **Step 2.4**: Run test - achieve GREEN phase
- [ ] **Step 2.5**: Refactor for clean code
- [ ] **Step 2.6**: Repeat for each scenario

### Phase 3: Adapter Implementation

```
INSTRUCTION: Implement required adapters with integration tests
```

- [ ] **Step 3.1**: Define adapter interface in domain
- [ ] **Step 3.2**: Create fake adapter for component tests
- [ ] **Step 3.3**: Write integration test for real adapter
- [ ] **Step 3.4**: Implement real adapter
- [ ] **Step 3.5**: Verify integration test passes

### Phase 4: REST API Layer

```
INSTRUCTION: Expose functionality via REST endpoints
```

- [ ] **Step 4.1**: Design REST API contract
- [ ] **Step 4.2**: Create DTOs (use records if applicable)
- [ ] **Step 4.3**: Implement controller with proper validation
- [ ] **Step 4.4**: Write integration tests for endpoints

### Phase 5: Security & Validation

```
INSTRUCTION: Apply OWASP principles and security measures
```

- [ ] **Step 5.1**: Add input validation (@Valid, custom validators)
- [ ] **Step 5.2**: Implement authorization checks
- [ ] **Step 5.3**: Configure security headers
- [ ] **Step 5.4**: Add security-focused tests
- [ ] **Step 5.5**: Verify OWASP compliance

### Phase 6: End-to-End Testing

```
INSTRUCTION: Validate complete feature flow
```

- [ ] **Step 6.1**: Create E2E test scenarios
- [ ] **Step 6.2**: Implement tests in e2e directory
- [ ] **Step 6.3**: Run against deployed services
- [ ] **Step 6.4**: Verify all acceptance criteria met

### Phase 7: Documentation & Cleanup

```
INSTRUCTION: Update all relevant documentation
```

- [ ] **Step 7.1**: Update architecture documentation if needed
- [ ] **Step 7.2**: Update README.adoc with setup changes
- [ ] **Step 7.3**: Create/update Wiki pages
- [ ] **Step 7.4**: Remove any TODO comments
- [ ] **Step 7.5**: Update Github Issue status

## Quality Checkpoints

### After Each Phase

- [ ] All tests passing (unit, integration, e2e)
- [ ] Code coverage > 80%
- [ ] No code smells or duplication
- [ ] Clean Architecture principles maintained
- [ ] Documentation current

### Definition of Done

- [ ] All acceptance criteria met
- [ ] All tests automated and passing
- [ ] Code reviewed (by human or AI peer)
- [ ] Documentation updated
- [ ] No technical debt introduced
- [ ] Deployed to test environment
- [ ] JIRA story updated

## Execution Instructions for AI Agent

### How to Process This Plan

1. Read entire plan before starting
2. Execute phases sequentially
3. Complete all steps in a phase before moving to next
4. After each step, verify success before proceeding
5. If blocked, ask ONE specific question and wait for answer
6. Update checkbox status as you progress
7. Report completion of each phase

### TDD Reminder for Each Code Step

```
For EVERY code implementation:
1. Write ONE test first
2. See it fail (RED)
3. Write minimal code to pass (GREEN)
4. Refactor for clarity (REFACTOR)
5. Commit after each cycle
```

## Progress Tracking

### Current Status

- **Current Phase**: [Phase X]
- **Current Step**: [Step X.Y]
- **Blockers**: [List any blockers]
- **Questions**: [List pending questions]

### Completion Log

| Phase   | Completed | Duration | Notes |
|---------|-----------|----------|-------|
| Phase 1 | ⬜         | -        | -     |
| Phase 2 | ⬜         | -        | -     |
| Phase 3 | ⬜         | -        | -     |
| Phase 4 | ⬜         | -        | -     |
| Phase 5 | ⬜         | -        | -     |
| Phase 6 | ⬜         | -        | -     |
| Phase 7 | ⬜         | -        | -     |

---
*This plan follows TDD, Clean Code, and architecture principles strictly.*
