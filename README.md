<<<<<<< HEAD
"# test" 
=======

# 📥 WhatsApp Status Downloader

A modern Android app to browse, view, and download WhatsApp statuses — both images and videos — directly from the `.Statuses` folder, supporting Android 8 to 15+ using scoped storage and `DocumentFile` API.

---

## ✨ Features

- 📁 Access WhatsApp `.Statuses` media (Images & Videos)
- 🔐 Scoped storage support (Android 11+)
- 🎥 View image & video previews
- 💾 Download and save statuses locally
- 📊 MVVM architecture with ViewModel + LiveData
- 🧠 Detects wrong folder selection
- 📢 AdMob banner integration

---

## 🚀 Getting Started

### Prerequisites

- Android Studio Giraffe/Koala or later
- Android 8.0+ device or emulator
- WhatsApp installed with some statuses already viewed

### Setup

```bash
git clone https://github.com/rashidekbal/status-downloader.git

Open the project in Android Studio, sync Gradle, build, and run.

🛡️ Permissions
Android 10 and below:

READ_EXTERNAL_STORAGE

WRITE_EXTERNAL_STORAGE

Android 11 and above:

Uses ACTION_OPEN_DOCUMENT_TREE to request access to:

bash
Copy
Edit
Android/media/com.whatsapp/WhatsApp/Media/.Statuses
The app persists this access using takePersistableUriPermission().

📂 Folder Access Logic
If the user selects the wrong folder, the app shows a warning and prompts re-selection.

Checks:

treeUri.getPath() contains .Statuses

DocumentFile.isDirectory() is true

Media types verified using MIME

🧩 Tech Stack
Java (Native Android)

MVVM Architecture (ViewModel + LiveData)

DocumentFile API (Scoped Storage)

Glide (Image Loading)

Optional: ExoPlayer (for video previews)

AdMob SDK

🧠 Project Structure
bash
Copy
Edit
com.rtech.statusdownloaderwa/
│
├── AppRoot/              # Application context & shared prefs
├── adapters/             # ViewPager & RecyclerView adapters
├── fragments/            # All, Images, Videos tabs
├── filesProvider/        # Media access layer (DocumentFile/File APIs)
├── models/               # MediaModel (path, type, uriKind)
├── utils/                # Permission helpers, MediaTypeChecker
├── viewModels/           # MediaViewModel (LiveData)
├── MainActivity.java     # Entry point, permission flow
└── ...
📄 License
MIT License.
Feel free to use, modify, and share — with credit.

👤 Author
Rasid Ekbal
🎓 Engineering Student & Android Developer
🔗 GitHub https://github.com/rashidekbal

⚠️ Disclaimer
This app is not affiliated with WhatsApp or Meta.
It simply accesses locally cached .Statuses for personal offline viewing.
>>>>>>> 5d2c7e518e0fac05b5bb890c1b88fc84eea2e7de
