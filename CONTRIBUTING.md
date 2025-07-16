# Contributing to Java Examples

Thank you for your interest in contributing to the Java Examples project! This document provides guidelines and information for contributors.

## 📋 Table of Contents
- [Getting Started](#getting-started)
- [Development Setup](#development-setup)
- [Coding Standards](#coding-standards)
- [Project Structure](#project-structure)
- [Submission Guidelines](#submission-guidelines)
- [Code Review Process](#code-review-process)

## 🚀 Getting Started

### Prerequisites
- Java Development Kit (JDK) 11 or higher
- Git
- VS Code (recommended) with Java Extension Pack

### Development Setup

1. **Clone the repository**
```bash
git clone git@github.com:hkevin01/Java-Examples.git
cd Java-Examples
```

2. **Open in VS Code**
```bash
code .
```

3. **Install recommended extensions**
The project includes a `.vscode/extensions.json` file with recommended extensions.

4. **Build the project**
Use VS Code tasks or run manually:
```bash
mkdir -p build/classes
find src -name "*.java" -exec javac -cp src -d build/classes {} \;
```

## 📏 Coding Standards

### Java Style Guide
- Follow [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html)
- Use meaningful variable and method names
- Maximum line length: 100 characters
- Use 4 spaces for indentation (no tabs)

### Documentation Requirements
- **Class-level JavaDoc**: Every public class must have comprehensive JavaDoc
- **Method-level JavaDoc**: All public methods must include `@param` and `@return` tags
- **Inline Comments**: Complex logic should be explained with comments
- **Example Usage**: Include main() method demonstrating the concept

### Code Structure
```java
package src.category;

/**
 * Brief description of what this class demonstrates
 * 
 * Detailed explanation of the concept being taught,
 * including when and why to use this pattern/technique.
 * 
 * @author Java Examples Project
 * @version 1.0
 * @since 2025-07-15
 */
public class ExampleClass {
    
    /**
     * Main method demonstrating the concept
     * @param args command line arguments
     */
    public static void main(String[] args) {
        // Clear, step-by-step demonstration
        System.out.println("=== Concept Demonstration ===");
        // Implementation here
    }
}
```

## 📁 Project Structure

### Directory Organization
```
src/
├── basics/           # Fundamental Java concepts
├── oop/             # Object-oriented programming
├── datastructures/  # Data structure implementations
├── algorithms/      # Algorithm examples
├── patterns/        # Design patterns
└── advanced/        # Advanced topics
```

### Naming Conventions
- **Files**: PascalCase ending with descriptive noun (e.g., `PolymorphismDemo.java`)
- **Classes**: PascalCase, descriptive names
- **Methods**: camelCase, verb-based names
- **Variables**: camelCase, descriptive names
- **Constants**: UPPER_SNAKE_CASE

## 📝 Submission Guidelines

### Before Submitting
1. **Test your code**: Ensure it compiles and runs correctly
2. **Add documentation**: Include comprehensive JavaDoc comments
3. **Follow conventions**: Adhere to the coding standards
4. **Update README**: If adding new categories, update documentation

### Commit Message Format
```
category: Brief description of changes

- Detailed explanation of what was changed
- Why the change was necessary
- Any additional context

Examples:
oop: Add interface demonstration to PolymorphismDemo
algorithms: Implement QuickSort with detailed comments
basics: Add variable scoping examples
```

### Pull Request Process
1. **Fork the repository**
2. **Create a feature branch**
   ```bash
   git checkout -b feature/algorithm-binary-search
   ```
3. **Make your changes**
4. **Test thoroughly**
5. **Commit with descriptive messages**
6. **Push to your fork**
7. **Create a pull request**

### Pull Request Template
- **Description**: What does this PR add/fix?
- **Learning Objective**: What concept does this teach?
- **Testing**: How was this tested?
- **Documentation**: What documentation was added/updated?

## 🔍 Code Review Process

### Review Criteria
- **Correctness**: Code compiles and runs as expected
- **Educational Value**: Clearly demonstrates the intended concept
- **Code Quality**: Follows project standards and best practices
- **Documentation**: Adequate comments and JavaDoc
- **Completeness**: Includes working main() method and examples

### Review Timeline
- Initial review within 48 hours
- Follow-up reviews within 24 hours
- Merging after approval from project maintainers

## 🎯 Contribution Ideas

### Beginner Contributions
- Add examples to existing categories
- Improve documentation
- Fix typos or formatting issues
- Add more test cases

### Intermediate Contributions
- Implement missing data structures
- Add algorithm visualizations
- Create comprehensive examples
- Improve error handling

### Advanced Contributions
- Add performance benchmarking
- Implement design patterns
- Add concurrency examples
- Create integration examples

## ❓ Getting Help

### Resources
- **Project Documentation**: Check the `docs/` folder
- **Java Documentation**: [Oracle Java Docs](https://docs.oracle.com/en/java/)
- **Style Guide**: [Google Java Style](https://google.github.io/styleguide/javaguide.html)

### Contact
- **Issues**: Use GitHub Issues for bugs and feature requests
- **Discussions**: Use GitHub Discussions for questions
- **Email**: Contact project maintainers for sensitive matters

## 📜 License

By contributing to this project, you agree that your contributions will be licensed under the same license as the project.

---

Thank you for contributing to Java Examples! Your contributions help developers learn and grow. 🚀
