# Spark

A simple LWJGL (Lightweight Java Game Library) project to test and run OpenGL + GLFW window creation in Java.  
Built using **Gradle** and compatible with **JDK 21+ (tested on JDK 24)**.

---

## 🚀 Features

- Uses **LWJGL 3.3.6** with GLFW, OpenGL, OpenAL, STB, and Assimp.
- Configured with **Gradle** for easy builds.
- Includes platform-specific **native libraries** (Windows, Linux, macOS).
- Demonstrates a basic **window creation** in Java.
- **New:** Scene change and blackfade effects added.
- **Improved:** MouseListener.java fixes for stack overflow and reliable event handling.

---

## 📂 Project Structure

```
spark/
├─ .gitignore
├─ .idea/
├─ CONTRIBUTING.md
├─ LICENSE
├─ LICENSE.EULA
├─ LICENSE.PROPRIETARY
├─ README.md
├─ build.gradle
├─ gradle/
├─ gradlew
├─ gradlew.bat
├─ settings.gradle
└─ src/
    └─ main/
        └─ java/
```

---

## 🔧 Prerequisites

- **Java JDK 21 or newer** (tested on JDK 24)  
- **Gradle** (bundled with IntelliJ IDEA)  
- **IntelliJ IDEA** or any IDE with Gradle support  

---

## ⚡ How to Run

### IntelliJ IDEA

1. Open the project in IntelliJ IDEA.  
2. Wait for Gradle to sync (`build.gradle` will download LWJGL dependencies).  
3. Go to **Run → Edit Configurations...**:
   - If using **Gradle run**:  
     - Create a new Gradle run config.  
     - Set task to:  
       ```
       run
       ```
   - If using **Java Application run**:  
     - Main class: `xtorq.Main`  
     - Use classpath of module: `spark.main`  
     - VM options:  
       ```
       --enable-native-access=ALL-UNNAMED
       ```
4. Click **Run ▶️** — you should see a GLFW window open.

---

### Gradle CLI

You can also run from terminal:

```bash
# On Linux / macOS
./gradlew run

# On Windows
gradlew.bat run
```

---

## 🌍 Platform Support (LWJGL Natives)

In `build.gradle`, the following natives are configured:

- **Windows** → `natives-windows`  
- **Linux** → `natives-linux`  
- **macOS (Intel/ARM)** → `natives-macos`  

> Gradle automatically picks the correct native libraries for your OS at runtime.  
> If you want to hardcode for your system, change the `lwjglNatives` variable in `build.gradle`.

---

## 🛠️ Troubleshooting

- **Error: Restricted native access**  
  Make sure to add this JVM flag in IntelliJ VM options (or keep it in `build.gradle`):  
  ```
  --enable-native-access=ALL-UNNAMED
  ```

- **UnsatisfiedLinkError / Cannot load LWJGL library**  
  Check that the right `natives-<os>` dependency is included in your `build.gradle`.  
  Example for Linux:  
  ```gradle
  runtimeOnly "org.lwjgl:lwjgl::$lwjglNatives"
  runtimeOnly "org.lwjgl:lwjgl-glfw::$lwjglNatives"
  ```

- **Process finished with exit code 1**  
  Run with `--stacktrace` to see details:  
  ```bash
  ./gradlew run --stacktrace
  ```

---

## 🤝 Contributing & Conduct

- See [CONTRIBUTING.md](CONTRIBUTING.md) for how to contribute, coding standards, and guidelines.
- All contributors must adhere to the project's code of conduct.

---

## 📜 License

This project is for learning purposes and currently has no explicit license.
Additional license details and terms can be found in:
- [LICENSE](LICENSE)
- [LICENSE.EULA](LICENSE.EULA)
- [LICENSE.PROPRIETARY](LICENSE.PROPRIETARY)

---

## 📝 Recent Updates

- Scene change and blackfade graphical effects.
- MouseListener.java improvements (stack overflow fix; safer event handling).
- Merge and conflict resolutions.
- Added contributing guidelines & code of conduct.
- Updated and clarified licensing documents.
