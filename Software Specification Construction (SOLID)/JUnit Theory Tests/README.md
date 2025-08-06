##  *Directory Description*

`JUnit Theory Tests` is a focused Java folder showcasing two distinct perspectives on software architecture—one centered on evolving equality enforcement, the other on abstraction specification enforcement. Each suite stands alone, yet both contribute to a shared theme: code clarity through structure and discipline.

- **Broken Inheritance Suite**  
  A modular experiment in refining equals/hashCode logic across value classes. From a simple `Point` to a color-aware `ColorPoint` and a deliberately broken-hash `BrokenHash`, it chronicles the journey from naive implementations to rigorous contract enforcement via JUnit Theories. Think of it as a contract-focused time-lapse: each file captures a stage in upholding Java’s object equality invariants.

- **IntSet Suite**  
  A targeted look at set abstraction via a `List<Integer>` wrapper. Multiple `equals` implementations (ordered vs. unordered) and four `hashCode` strategies expose the tension between implementation details and client expectations. Combined with property-based tests in JUnit Theories and a Suite runner, it reveals how specification enforcement and structured tests can surface silent contract violations. It’s part specification, part stress test.

Intended as both academic artifact and recruiter prompt, the folder speaks to thoughtful engineering: not just writing code, but designing systems that explain themselves.
