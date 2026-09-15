# Workflow Automation

A workflow automation platform built with a Java backend and a Next.js frontend.

The goal of the project is to let users automate workflows and repetitive tasks across their personal lives and businesses.

The first phase focuses on user authentication and billing, before moving on to the actual workflow implementation in Java.

## Tech Stack


| Layer    | Technology    |
| -------- | ------------- |
| Backend  | Java          |
| Frontend | Next.js       |
| Auth     | Stateless JWT |
| Payments | Stripe        |


## User Flow

1. A user signs up and is given the default free plan.
2. The user can create a workflow in one of three ways:
  - Manually
  - From a template
  - With AI (AI builder)
3. **Manually** — the user is redirected to the workflow graph, where they drag and connect nodes to build the workflow.
4. **From a template** — the user is redirected to the graph with the nodes already populated.
5. **With AI** — the user is redirected to the graph and a chat window opens so they can interact with the AI.
6. The user saves the workflow, and it is eventually triggered to perform its task.
7. The user can view their workflows, the runs of each workflow, create connections, and so on.



## Pricing Logic

Every user starts on the **free** plan:

```js
// free
limits:   { workflows: 3, runsPerMonth: 100, minScheduleMinutes: 60 }
features: ["core_connectors"]
```

Other plans are available:

```js
// pro
limits:   { workflows: Infinity, runsPerMonth: 5_000, minScheduleMinutes: 1 }
features: [
  "core_connectors",
  "pro_connectors",
  "ai_agent",
  "ai_builder",
  "schedules",
  "run_history_30d",
]
```

```js
// team
limits:   { workflows: Infinity, runsPerMonth: 50_000, minScheduleMinutes: 1 }
features: [
  "core_connectors",
  "pro_connectors",
  "ai_agent",
  "ai_builder",
  "schedules",
  "run_history_30d",
  "audit_log",
  "priority_runs",
]
```



### Authentication and Plan Resolution

Authentication is stateless and based on the user's JWT. The token claims give us:

- `user_id`s
- `planSlug`
- `planFeatures`

The most important claim is `planSlug`. If it is undefined, the user is on the default free plan.

We use the plan slug to look up the plan's limits from an in-code mapping, with **no database lookup**:

```js
free: { workflows: 3, runsPerMonth: 100, minScheduleMinutes: 60 }
```

This gives us the user's current plan:

```js
{
  slug: "free",
  features: ["core_connectors"],
  limits: { workflows: 3, runsPerMonth: 100, minScheduleMinutes: 60 },
}
```

The Java backend is the source of truth for this mapping and enforces every limit. The Next.js frontend keeps its own copy of the same mapping, but only to drive the UI — showing plan cards, disabling gated actions, and displaying usage against limits.

### Enforcing Limits

We check the user's plan limits when they:

- Create a workflow
- Run a workflow
- Schedule a workflow
- Attempt to use a gated plan feature

Return the appropriate error whenever a plan limit is exceeded.

## Upgrading a Plan

1. The user goes to the **Pricing** tab.
2. They see their current plan, renewal/due date, and so on.
3. They also see the available pricing cards for monthly and annual billing.
4. They select the plan they want and are handed off to our payment provider.
5. When the payment succeeds, the payment provider sends us a webhook confirming it.
6. We save the user's subscription details in our database. The subscription record can be `null`, which means the user is on the default free plan.

One caveat: the user's existing JWT still carries the old `planSlug` until the token expires, and we gate access on that claim. So there will be a window where the user is still treated as being on the old plan even though they know they have already paid.

## Data Models



### User

- `_id`
- `fullName`
- `email`
- `profilePicture`
- `passwordHash`
- `createdAt`
- `updatedAt`



### Subscription

- `_id`
- `name`
- `planSlug`
- `user._id`
- `status`
- `stripeCustomerId`
- `stripeSubscriptionId`
- `planPeriod`: `monthly` | `annual`
- `dueDate`
- `createdAt`
- `updatedAt`



### Workflow

- `_id`
- `name`
- `user._id`
- *...the rest will come later*



### Plans

Not stored in the database — defined in code.

- `planSlug`
- `features[]`
- `limits`: `{ workflows, runsPerMonth, minScheduleMinutes }`



### Usage

Needed to keep track of a user's runs.

- `_id`
- `user._id`
- `month`
- `runs`
- `createdAt`
- `updatedAt`

