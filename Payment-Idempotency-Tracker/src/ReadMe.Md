Alright. This one is designed specifically around:

* your current level
* Stripe-style interview patterns
* your weak areas from previous mocks:

    * parsing
    * clean code under pressure
    * extensibility
    * edge cases
    * equality/hash semantics
    * choosing correct collections
    * incremental adaptation

This is intentionally less “DSA” and more “practical backend coding”.

Timer starts NOW (45 mins).

---

# Question: Payment Event Parser & Matcher

Stripe receives raw payment event logs from external partners.

Each raw event is a single string in this format:

```text
event_id=evt_123;merchant=amazon;amount=100;currency=USD
```

Fields:

* separated by `;`
* key/value separated by `=`

---

You are given:

```java
List<String> rawEvents
Set<String> validEventIds
```

---

# PART 1 — Parsing + Validation

Implement:

```java
List<Event> parseValidEvents(
    List<String> rawEvents,
    Set<String> validEventIds
)
```

Return only events where:

1. `event_id` exists
2. `event_id` is present in `validEventIds`
3. `amount` is a valid positive integer
4. `currency` exists and is non-empty

Ignore invalid events.

---

# Example

Input:

```java
rawEvents = [
  "event_id=evt_1;merchant=amazon;amount=100;currency=USD",
  "event_id=evt_2;merchant=uber;amount=-50;currency=USD",
  "event_id=evt_3;merchant=netflix;amount=200"
]

validEventIds = ["evt_1", "evt_3"]
```

Output:

```text
[
  Event(evt_1, amazon, 100, USD)
]
```

Because:

* evt_2 amount invalid
* evt_3 missing currency

---

# Expectations

Working solution > perfect solution.

Focus on:

* readability
* clean parsing
* test cases
* edge cases
* incremental design

No need for frameworks/JUnit.

Print-based tests are fine.

---

# Clarifications

You may ask questions like a real interview.

I will answer as interviewer.

When you finish PART 1, tell me:

> "done"

Then I’ll give incremental follow-ups/scope expansion.


