# 🎵 MindMelody

> **An intelligent Android music companion that understands your mood and curates the perfect soundtrack for your emotions.**

* Developed an Android application that fetches and plays songs based on the user's mood.
* Integrated Google Gemini API to analyze user-provided text and detect the mood.
* Used YouTube Data API to fetch mood-relevant songs.
* Implemented in-app music playback for seamless user experience.
* Built a modern, responsive UI using Material Design principles.
* Utilized Retrofit and Coroutines for efficient network calls and asynchronous operations.
* Managed UI components effectively with ViewBinding.

## 🌟 Key Highlights

- **🤖 AI-Powered Mood Detection**: Utilizes Google Gemini AI to analyze text and speech input for accurate mood recognition
- **🎵 Smart Music Curation**: Fetches mood-relevant songs from YouTube's vast music library
- **🎤 Voice & Text Input**: Express your mood through speech or text - the app adapts to your preference
- **📱 Seamless Playback**: Built-in YouTube player ensures smooth, uninterrupted music experience
- **🎨 Modern UI/UX**: Beautiful Material Design interface with smooth animations and intuitive navigation
- **⚡ High Performance**: Optimized with Retrofit, Coroutines, and efficient architecture patterns

## ✨ Features

### 🎤 **Multi-Modal Input**
- **Speech Recognition**: Speak naturally about your mood or current situation
- **Text Input**: Type your thoughts and feelings for mood analysis
- **Smart Processing**: AI understands context, emotions, and musical preferences

### 🎶 **Intelligent Music Discovery**
- **Mood-Based Recommendations**: AI analyzes your input and suggests relevant songs
- **YouTube Integration**: Access to millions of songs and music videos
- **Auto-Playlist Generation**: Continuous playback of related songs
- **Personalized Experience**: Learns from your preferences over time

### 📱 **User Experience**
- **Modern Interface**: Clean, intuitive design following Material Design principles
- **Smooth Navigation**: Tab-based navigation with animated transitions
- **Offline Capability**: Cached recommendations for better performance
- **Responsive Design**: Optimized for various screen sizes and orientations

### 🔧 **Technical Excellence**
- **MVVM Architecture**: Clean, maintainable code structure
- **ViewBinding**: Type-safe view references
- **Coroutines**: Asynchronous operations for smooth performance
- **Firebase Integration**: User authentication and data management

## 🛠 Tech Stack

### **Core Technologies**
- **Language**: Kotlin 2.0.21
- **Platform**: Android (API 24+)
- **Architecture**: MVVM with Fragment-based navigation
- **Build System**: Gradle with Kotlin DSL

### **UI & Design**
- **UI Framework**: XML layouts with Material Design Components
- **View Binding**: Type-safe view references
- **Animations**: Custom scale animations and transitions
- **Design System**: Material Design 3 principles

### **Networking & APIs**
- **HTTP Client**: Retrofit 2.9.0 with Gson converter
- **API Integration**: 
  - Google Gemini AI API (v0.2.0)
  - YouTube Data API v3
- **Logging**: OkHttp logging interceptor

### **Authentication & Backend**
- **Firebase**: Authentication and user management
- **Google Services**: Integration with Google APIs

### **Development Tools**
- **IDE**: Android Studio
- **Version Control**: Git
- **Testing**: JUnit 4.13.2, Espresso 3.6.1
- **Code Quality**: ProGuard for release builds

## 📂 Project Structure

```
MindMelody/
├── app/
│   ├── src/main/
│   │   ├── java/com/example/mindmelody/
│   │   │   ├── Activities/
│   │   │   │   ├── MainActivity.kt          # Main navigation activity
│   │   │   │   ├── Login.kt                 # User authentication
│   │   │   │   ├── Signup.kt                # User registration
│   │   │   │   ├── SplashScreen.kt          # App launch screen
│   │   │   │   ├── TutorialPage.kt          # Onboarding tutorial
│   │   │   │   └── VideoPlayer.kt           # YouTube player activity
│   │   │   ├── Fragments/
│   │   │   │   ├── HomeFragment.kt          # Main dashboard
│   │   │   │   ├── SpeechFragment.kt        # Voice input interface
│   │   │   │   ├── TextFragment.kt          # Text input interface
│   │   │   │   ├── ImageFragment.kt         # Image-based mood detection
│   │   │   │   ├── ContactFragment.kt       # Contact/support
│   │   │   │   ├── ProfileFragment.kt       # User profile
│   │   │   │   └── OnboardingFragment.kt    # App introduction
│   │   │   ├── Models/
│   │   │   │   ├── Song.kt                  # Song data model
│   │   │   │   ├── OnboardingItem.kt        # Onboarding content
│   │   │   │   ├── GeminiRequest.kt         # AI request model
│   │   │   │   └── YoutubeResponse.kt       # API response model
│   │   │   ├── Adapters/
│   │   │   │   ├── SongAdapter.kt           # RecyclerView adapter
│   │   │   │   └── ViewPagerAdapter.kt      # Fragment pager adapter
│   │   │   ├── Services/
│   │   │   │   ├── GeminiClient.kt          # AI API client
│   │   │   │   ├── YoutubeAPIService.kt     # YouTube API service
│   │   │   │   ├── RetrofitClient.kt        # HTTP client setup
│   │   │   │   └── DatabaseConnection.kt    # Database utilities
│   │   │   └── Utils/
│   │   │       └── [Utility classes]
│   │   ├── res/
│   │   │   ├── layout/                      # XML layout files
│   │   │   ├── drawable/                    # Icons and graphics
│   │   │   ├── values/                      # Strings, colors, styles
│   │   │   ├── anim/                        # Animation resources
│   │   │   └── mipmap/                      # App icons
│   │   └── AndroidManifest.xml              # App configuration
│   ├── build.gradle.kts                     # App-level dependencies
│   └── google-services.json                 # Firebase configuration
├── build.gradle.kts                         # Project-level configuration
├── gradle/libs.versions.toml                # Dependency versions
└── README.md                                # Project documentation
```


## 🚀 How It Works

### **1. Input Processing**
- **Voice Input**: Speech recognition captures user's spoken mood or situation
- **Text Input**: Users can type their thoughts, feelings, or current state
- **Context Analysis**: AI processes natural language to understand emotional context

### **2. AI Mood Detection**
- **Gemini AI Integration**: Advanced language model analyzes input for emotional patterns
- **Mood Classification**: Identifies primary emotions (happy, sad, energetic, calm, etc.)
- **Musical Preference Mapping**: Converts detected mood into music genre preferences

### **3. Music Discovery**
- **YouTube API Integration**: Searches for songs matching the detected mood
- **Smart Filtering**: Applies genre, tempo, and mood-based filters
- **Playlist Generation**: Creates curated playlists of relevant songs

### **4. Seamless Playback**
- **Embedded Player**: YouTube player integrated directly into the app
- **Auto-Play**: Continuous playback of related songs
- **User Control**: Standard playback controls (play, pause, skip, etc.)

## ⚙️ Installation & Setup

### **Prerequisites**
- **Android Studio**: Latest version (Hedgehog or newer)
- **Android SDK**: API level 24 or higher
- **Java/Kotlin**: JDK 11 or higher
- **Device/Emulator**: Android 7.0 (API 24) or higher

### **API Keys Required**
Before running the app, you'll need to obtain the following API keys:

1. **Google Gemini AI API Key**
   - Visit [Google AI Studio](https://makersuite.google.com/app/apikey)
   - Create a new API key
   - Note: This is required for mood analysis

2. **YouTube Data API Key**
   - Go to [Google Cloud Console](https://console.cloud.google.com/)
   - Enable YouTube Data API v3
   - Create credentials (API key)
   - Note: This is required for music search

3. **Firebase Project** (Optional but recommended)
   - Create a Firebase project
   - Enable Authentication
   - Download `google-services.json`

### **Installation Steps**

#### **1. Clone the Repository**
```bash
git clone https://github.com/Aditya122221/MindMelody.git
cd MindMelody
```

#### **2. Configure API Keys**
Create or edit the `local.properties` file in the root directory:

```properties
# API Configuration
API_KEY=your_youtube_api_key_here
GEMINI_MODEL_NAME=your_gemini_model_name_here

# Optional: Firebase configuration
# Add your google-services.json to app/ directory
```

#### **3. Firebase Setup** (Optional)
1. Download `google-services.json` from your Firebase project
2. Place it in the `app/` directory
3. The app will automatically use Firebase for authentication

#### **4. Build and Run**
1. Open the project in Android Studio
2. Sync the project with Gradle files
3. Connect an Android device or start an emulator
4. Click "Run" or use the command:
   ```bash
   ./gradlew assembleDebug
   ```

### **Troubleshooting**

#### **Common Issues**
- **API Key Errors**: Ensure your API keys are correctly set in `local.properties`
- **Build Failures**: Check that all dependencies are properly synced
- **Permission Issues**: Ensure microphone permission is granted for speech recognition
- **Network Errors**: Verify internet connectivity and API quotas

#### **Debug Mode**
Enable debug logging by adding to your `local.properties`:
```properties
DEBUG_MODE=true
```

### **Development Setup**
For contributors and developers:

```bash
# Install dependencies
./gradlew build

# Run tests
./gradlew test

# Generate debug APK
./gradlew assembleDebug

# Generate release APK
./gradlew assembleRelease
```

## 📱 Usage Examples

### **Getting Started**
1. **Launch the App**: Open MindMelody and complete the onboarding process
2. **Choose Input Method**: Select between voice or text input
3. **Express Your Mood**: Speak or type how you're feeling
4. **Discover Music**: Let AI find the perfect songs for your mood
5. **Enjoy**: Play music seamlessly with the built-in player

### **Example Inputs**
- **Voice**: "I'm feeling really energetic today and want to work out"
- **Text**: "Just finished a long day at work, feeling tired and stressed"
- **Context**: "It's raining outside and I'm feeling nostalgic"

### **Expected Outputs**
- **Energetic**: Upbeat pop, rock, or electronic music
- **Relaxed**: Soft acoustic, ambient, or classical music
- **Nostalgic**: Retro hits, old favorites, or sentimental songs

## 📸 Screenshots

> **Note**: Screenshots will be added here once the app is built and running. These would include:
> - Main dashboard with mood input options
> - Speech recognition interface
> - Text input screen
> - Music discovery results
> - YouTube player interface
> - User profile and settings

## 🤝 Contributing

We welcome contributions to MindMelody! Here's how you can help:

### **Ways to Contribute**
- 🐛 **Bug Reports**: Found a bug? Open an issue with detailed steps to reproduce
- 💡 **Feature Requests**: Have an idea? Submit a feature request issue
- 🔧 **Code Contributions**: Submit pull requests for bug fixes or new features
- 📖 **Documentation**: Help improve documentation and README
- 🧪 **Testing**: Test the app and report issues

### **Development Guidelines**

#### **Code Style**
- Follow Kotlin coding conventions
- Use meaningful variable and function names
- Add comments for complex logic
- Maintain consistent indentation (4 spaces)

#### **Commit Messages**
Use clear, descriptive commit messages:
```
feat: Add voice recognition for mood input
fix: Resolve YouTube API rate limiting issue
docs: Update installation instructions
```

#### **Pull Request Process**
1. Fork the repository
2. Create a feature branch: `git checkout -b feature/amazing-feature`
3. Make your changes and test thoroughly
4. Commit your changes: `git commit -m 'Add amazing feature'`
5. Push to your branch: `git push origin feature/amazing-feature`
6. Open a Pull Request with a clear description

### **Issue Reporting**
When reporting issues, please include:
- **Device Information**: Android version, device model
- **App Version**: Current version of MindMelody
- **Steps to Reproduce**: Detailed steps to recreate the issue
- **Expected vs Actual Behavior**: What should happen vs what actually happens
- **Screenshots/Logs**: If applicable, include relevant screenshots or log files

## 🙏 Acknowledgments

- **Google Gemini AI** for providing powerful mood analysis capabilities
- **YouTube Data API** for music discovery and playback
- **Firebase** for authentication and backend services
- **Material Design** for beautiful UI components
- **Android Community** for excellent documentation and resources

## 📞 Support & Contact

- **Issues**: [GitHub Issues](https://github.com/Aditya122221/MindMelody/issues)
- **Discussions**: [GitHub Discussions](https://github.com/Aditya122221/MindMelody/discussions)
- **Email**: [Your contact email]

---

<div align="center">

**Made with ❤️ for music lovers everywhere**

[⭐ Star this repo](https://github.com/Aditya122221/MindMelody) | [🐛 Report Bug](https://github.com/Aditya122221/MindMelody/issues) | [💡 Request Feature](https://github.com/Aditya122221/MindMelody/issues)

</div>