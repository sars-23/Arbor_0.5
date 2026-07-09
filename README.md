# Arbor 0.5

A **dynamically interpreted programming language** built with Java, designed to provide an elegant and efficient runtime for executing code with dynamic typing and interpretation.

## 📋 Table of Contents

- [Features](#features)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
  - [Quick Start](#quick-start)
- [Usage](#usage)
- [Architecture](#architecture)
- [Project Structure](#project-structure)
- [Development](#development)
- [Contributing](#contributing)
- [License](#license)
- [Roadmap](#roadmap)

## ✨ Features

- **Dynamic Typing**: Variables are dynamically typed at runtime
- **Interpreted Execution**: Code is interpreted directly without compilation to bytecode
- **Java-based**: Built on Java for reliability and cross-platform compatibility
- **Lightweight**: Minimal dependencies and efficient memory footprint
- **Extensible**: Easy to extend with custom functions and libraries

## 🚀 Getting Started

### Prerequisites

- **Java 11+** or higher
- **Maven** 3.6+ (for building from source)

### Installation

#### Option 1: Clone and Build from Source

```bash
git clone https://github.com/sars-23/Arbor_0.5.git
cd Arbor_0.5
mvn clean install
```

#### Option 2: Download Release

Visit the [Releases](https://github.com/sars-23/Arbor_0.5/releases) page to download the latest JAR file.

### Quick Start

```bash
# Run a simple script
java -jar Arbor.jar script.arb

# Interactive REPL mode
java -jar Arbor.jar
```

## 💻 Usage

### Basic Syntax Example

```arb
// Variable declaration and assignment
name = "Arbor"
version = 0.5

// Simple output
print(name)

// Control flow
if (version > 0) {
    print("Welcome to " + name + "!")
}

// Functions
func greet(name) {
    return "Hello, " + name
}

print(greet("World"))
```

### Running Scripts

Create a file `hello.arb`:

```arb
func fibonacci(n) {
    if (n <= 1) return n
    return fibonacci(n - 1) + fibonacci(n - 2)
}

print(fibonacci(10))
```

Run it:

```bash
java -jar Arbor.jar hello.arb
```

## 🏗️ Architecture

Arbor uses a **recursive descent parser** and an **interpreter pattern** for execution:

1. **Lexer**: Tokenizes source code into tokens
2. **Parser**: Builds an Abstract Syntax Tree (AST) from tokens
3. **Interpreter**: Traverses and evaluates the AST dynamically
4. **Runtime**: Manages variable scopes, function calls, and memory

## 📁 Project Structure

```
Arbor_0.5/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── arbor/
│   │           ├── Lexer.java           # Tokenization
│   │           ├── Parser.java          # AST construction
│   │           ├── Interpreter.java     # Code execution
│   │           ├── Runtime.java         # Runtime environment
│   │           ├── Value.java           # Dynamic value representation
│   │           └── Main.java            # Entry point
│   └── test/
│       └── java/
│           └── arbor/
│               └── InterpreterTest.java
├── pom.xml                              # Maven configuration
└── README.md
```

## 🛠️ Development

### Building

```bash
mvn clean compile
```

### Running Tests

```bash
mvn test
```

### Creating a JAR

```bash
mvn clean package -DskipTests
```

### Development Tips

- Review `src/main/java/arbor/Parser.java` for grammar rules
- Check `src/main/java/arbor/Interpreter.java` for runtime behavior
- Add new built-in functions in `Runtime.java`

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

### Reporting Issues

Please use [GitHub Issues](https://github.com/sars-23/Arbor_0.5/issues) to report bugs or suggest improvements.

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🗺️ Roadmap

- [ ] Standard library expansion
- [ ] Module/import system
- [ ] Object-oriented programming support
- [ ] Performance optimizations (JIT compilation)
- [ ] REPL improvements
- [ ] Comprehensive language documentation
- [ ] IDE support (syntax highlighting, plugins)

## 📚 Resources

- [Getting Help](#contributing) - Open an issue
- [Documentation](https://github.com/sars-23/Arbor_0.5/wiki) - Language guide (coming soon)

## 👤 Author

**sars-23**

- GitHub: [@sars-23](https://github.com/sars-23)

---

**Made with ❤️ for language enthusiasts**