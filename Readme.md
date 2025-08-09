# 🎵 MindMelody


* Developed an Android application that fetches and plays songs based on the user's mood.
* Integrated Google Gemini API to analyze user-provided text and detect the mood.
* Used YouTube Dat****a API to fetch mood-relevant songs.
* Implemented in-app music playback for seamless user experience.
* Built a modern, responsive UI using Material Design principles.
* Utilized Retrofit and Coroutines for efficient network calls and asynchronous operations.
* Managed UI components effectively with ViewBinding.


# ✨ Features


* 🎤 Speech Recognition — Speak about your mood or surroundings, and the app will detect it.
* 🎶 Mood-Based Song Suggestions — AI-powered song recommendations using YouTube.
* 📺 Embedded YouTube Player — Play songs without leaving the app.
* 🔀 Auto Playlist — Play related songs after your selection ends.


# 🛠 Tech Stack


<ul>
<li><strong>Language:</strong> Kotlin</li>
<li><strong>UI:</strong> XML layouts, Material Design Components</li>
<li><strong>Architecture:</strong> MVVM + ViewPager2 Fragments</li>
<li><strong>Speech Recogination: </strong> Android SpeechRecognizer API</li>
<li><strong>API: </strong> Youtube API, Gemni API</li>
</ul>

# Directory Structure


MindMelody/
 ├── app/src/main/java/com/example/mindmelody/
 │    ├── ContactFragment.kt
 │    ├── DatabaseConnection.kt
 │    ├── GeminiClient.kt
 │    ├── GeminiRequest.kt
 │    ├── HomeFragment.kt
 │    ├── ImageFragment.kt
 │    ├── Login.kt
 │    ├── MainActivity.kt
 │    ├── OnboardingFragment.kt
 │    ├── OnboardingItem.kt
 │    ├── ProfileFragment.kt
 │    ├── RetrofitClient.kt
 │    ├── Signup.kt
 │    ├── Song.kt
 │    ├── SongAdapter.kt
 │    ├── SpeechFragment.kt
 │    ├── SplashScreen.kt
 │    ├── TextFragment.kt
 │    ├── TutorialPage.kt
 │    ├── VideoPlayer.kt
 │    ├── ViewPagerAdapter.kt
 │    ├── YoutubeAPIService.kt
 │    ├── YoutubeResponse.kt
 ├── res/
 │    ├── anim/
 │    ├── drawable/
 │    ├── layout/
 │              ├── activity_login.xml
 │              ├── activity_main.xml
 │              ├── activity_signup.xml
 │              ├── activity_splash_screen.xml
 │              ├── activity_tutorial_page.xml
 │              ├── activity_video_player.xml
 │              ├── fragment_contact.xml
 │              ├── fragment_home.xml
 │              ├── fragment_image.xml
 │              ├── fragment_onboarding.xml
 │              ├── fragment_profile.xml
 │              ├── fragment_profile.xml
 │              ├── fragment_speech.xml
 │              ├── fragment_text.xml
 │              ├── item_song.xml
 │    ├── minmap/
 │    ├── values/
 │    ├── xml/
 ├── README.md


# How it works


<ol>
<li><strong>Speech or Text Input</strong> — User speaks or types their mood.</li>
<li><strong>Mood Processing</strong> — Mood is matched with relevant genres.</li>
</ol>