# MyResort - Garuga Resort Management App

A modern Android application built with Jetpack Compose for managing restaurant and resort operations at Garuga Resort.

## 📱 Features

- **Modern UI**: Built with Jetpack Compose for a smooth, responsive interface
- **Dashboard Navigation**: Easy access to key management areas
  - Bookings Management
  - Room Management  
  - Financial Reports
- **Room Categories**: Visual display of available room types (Grand, Double, Single)
- **Dark Theme**: Professional dark olive theme with luxury beige accents
- **Responsive Design**: Optimized for various screen sizes

## 🏗️ Technical Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Architecture**: MVVM pattern
- **Navigation**: Jetpack Navigation Compose
- **Build System**: Gradle with Kotlin DSL
- **Minimum SDK**: 24 (Android 7.0)
- **Target SDK**: 36 (Android 14)

## 🎨 Design Features

- **Color Scheme**:
  - Deep Olive (#3B4433) - Primary background
  - Luxury Beige (#D9C5A7) - Accent color
  - Dark Text (#2D2D2D) - Primary text
  - Surface White (#FDFCFB) - Card backgrounds

- **Typography**: Serif fonts for branding, clean sans-serif for content
- **Layout**: Scrollable content with fixed sidebar navigation
- **Icons**: Material Design Icons with AutoMirrored support

## 📦 Dependencies

### Core Libraries
- `androidx.core:core-ktx:1.17.0`
- `androidx.lifecycle:lifecycle-runtime-ktx:2.10.0`
- `androidx.activity:activity-compose:1.12.4`

### Compose Libraries
- `androidx.compose:compose-bom:2026.02.00`
- `androidx.compose.ui:ui`
- `androidx.compose.ui:ui-graphics`
- `androidx.compose.ui:ui-tooling-preview`
- `androidx.compose.material3:material3`
- `androidx.compose.material:material-icons-extended`

### Navigation
- `androidx.navigation:navigation-compose:2.9.7`

### Testing
- `junit:junit:4.13.2`
- `androidx.test.ext:junit:1.3.0`
- `androidx.test.espresso:espresso-core:3.7.0`

## 🚀 Getting Started

### Prerequisites
- Android Studio Hedgehog | 2023.1.1 or later
- Android SDK API 36
- Kotlin 2.0.21

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/KubanjaElijahEldred/My-restauarant.git
   cd My-restauarant
   ```

2. **Open in Android Studio**
   - Open Android Studio
   - Select "Open an existing project"
   - Navigate to the cloned directory

3. **Build and Run**
   - Connect your Android device or start an emulator
   - Click the "Run" button (▶️) in Android Studio
   - Or use command line:
     ```bash
     ./gradlew assembleDebug
     adb install app/build/outputs/apk/debug/app-debug.apk
     ```

## 📱 App Screens

### Main Screen
- Garuga Resort branding
- Dashboard navigation buttons
- Room categories display
- Social media sidebar

### Dashboard Screens
- **Bookings Management**: View and manage customer bookings
- **Room Management**: Control room availability and pricing
- **Financial Reports**: Track revenue and generate reports

## 🔧 Development Commands

### Build Commands
```bash
# Build debug APK
./gradlew assembleDebug

# Build release APK
./gradlew assembleRelease

# Clean build
./gradlew clean

# Run tests
./gradlew test
```

### Device Management
```bash
# Check connected devices
adb devices

# Install APK
adb install app/build/outputs/apk/debug/app-debug.apk

# Uninstall app
adb uninstall com.example.myrestaurant
```

## 📁 Project Structure

```
app/
├── src/main/
│   ├── java/com/example/myrestaurant/
│   │   ├── MainActivity.kt              # Main activity with navigation
│   │   ├── Restaurant.kt                # Main restaurant class
│   │   ├── resort/
│   │   │   └── GarugaResortScreen.kt    # Main UI screen
│   │   └── ui/theme/
│   │       ├── Color.kt                 # App color palette
│   │       ├── Theme.kt                 # Material 3 theme
│   │       └── Type.kt                  # Typography definitions
│   ├── res/                             # Android resources
│   └── AndroidManifest.xml              # App manifest
├── build.gradle.kts                     # App-level build configuration
└── proguard-rules.pro                   # ProGuard configuration

gradle/
├── libs.versions.toml                   # Version catalog
└── wrapper/                             # Gradle wrapper

build.gradle.kts                         # Project-level build configuration
settings.gradle.kts                      # Gradle settings
```

## 🎯 Future Enhancements

- [ ] Real-time booking system
- [ ] Customer database integration
- [ ] Payment processing
- [ ] Staff management module
- [ ] Inventory tracking
- [ ] Analytics dashboard
- [ ] Push notifications
- [ ] Multi-language support

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👨‍💻 Author

**Elijah Eldred Kubanja**
- GitHub: [@KubanjaElijahEldred](https://github.com/KubanjaElijahEldred)
- Project: MyRestaurant - Garuga Resort Management

## 📞 Support

For support, please contact elijah.eldred@example.com or create an issue in the GitHub repository.

---

**Built with ❤️ using Jetpack Compose and Kotlin**
