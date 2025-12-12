# 🎅 Santa Claus ProgressBar

A festive and animated progress bar plugin for IntelliJ IDEA, Android Studio, and other JetBrains IDEs. Replace the default progress bar with a cheerful Santa Claus animation running alongside a beautiful red gradient bar!

![Santa Claus ProgressBar](resources/Santa.gif)

## 📹 Demo

Watch the plugin in action:

<video src="https://github.com/user-attachments/assets/33f68573-7d25-4127-87bf-de999168116b" width="800" controls>
  Your browser does not support the video tag. [Download the video](example.mov) instead.
</video>

## ✨ Features

- **Animated Santa Claus**: A cute animated Santa Claus GIF that runs along the progress bar
- **Smart Animation**: Santa automatically flips horizontally when moving in reverse direction
- **Beautiful Gradient**: Elegant vertical red gradient (`#7A1F1A` → `#A12823` → `#C8322C`) matching Santa's theme
- **Smooth Integration**: Works seamlessly with both determinate and indeterminate progress bars
- **Auto-sizing**: Santa icon automatically scales to fit the progress bar height
- **Centered Alignment**: Perfect vertical centering of the Santa animation

## 🎯 Compatibility

- **IntelliJ IDEA**: 2023.3+ (Build 233+)
- **Android Studio**: Supported via optional dependency
- **Other JetBrains IDEs**: Compatible with all IDEs based on IntelliJ Platform

## 📦 Installation

### From JetBrains Plugin Repository

1. Open your IDE (IntelliJ IDEA, Android Studio, etc.)
2. Go to **Settings/Preferences** → **Plugins**
3. Click **Marketplace**
4. Search for "Santa Claus ProgressBar"
5. Click **Install**
6. Restart your IDE

### Manual Installation

1. Download the latest release from the [Releases](../../releases) page
2. Go to **Settings/Preferences** → **Plugins**
3. Click the ⚙️ icon → **Install Plugin from Disk...**
4. Select the downloaded `.zip` file
5. Restart your IDE

## 🎨 How It Works

The plugin replaces the default progress bar UI with a custom implementation that:

- **Determinate Mode**: Shows Santa running ahead of the red progress bar as it fills
- **Indeterminate Mode**: Shows Santa running back and forth across the progress bar
- **Automatic Updates**: Listens to Look and Feel changes and application activation events to ensure the custom UI is always applied

## 🛠️ Development

### Building from Source

```bash
# Clone the repository
git clone <repository-url>
cd santa-claus-progressbar-plugin

# Build the plugin
./gradlew buildPlugin

# The plugin ZIP will be in build/distributions/
```

### Requirements

- Java 17+
- Gradle 8.14+
- IntelliJ Platform SDK (automatically downloaded by Gradle)

## 📝 Version History

### 1.1.0
- Updated progress bar gradient for better visual appeal

### 1.0.0
- Initial release with Santa Claus animated progress bar
- Support for determinate and indeterminate modes
- Horizontal flip animation when Santa reverses direction

## 👨‍💻 Author

**cardosofgui**

- Email: cardosofgui@example.com

## 📄 License

This project is licensed under the terms specified in the LICENSE file (if applicable).

## 🤝 Contributing

Contributions, issues, and feature requests are welcome! Feel free to check the [issues page](../../issues).

## ⭐ Show Your Support

If you like this plugin, please give it a ⭐ on the JetBrains Plugin Repository!

---

**Made with ❤️ for the JetBrains community**
