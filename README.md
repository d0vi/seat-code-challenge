[![Build status](https://github.com/d0vi/seat-code-challenge/workflows/CI/badge.svg)](https://github.com/d0vi/seat-code-challenge/actions/workflows/main.yml)

# 🤖️ Skynet

Cutting grass never felt so easy 🫢️

### ➡️ Requirements

In order to run this application, a valid JDK 21 version must be present in your system.

Use [SDKMAN!](https://sdkman.io/) to install the Eclipse Temurin JDK:
```
sdk install java 21.0.4-tem
```

### 🏃🏻‍♂️ Run the application

Open a new terminal and execute:
```
./gradlew run
```

This should start a new Gradle daemon that runs the app. Running the application should
always yield the same result:
```
1 3 N
5 1 E
```

If you wish to change the behavior of the mowers, open `src/main/resources/input.txt` in your favorite
text editor and modify some line. You should now see a different output (or error 😉️).