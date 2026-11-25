# MTech Personal AI

A lightweight research prototype for a context-aware Android assistant. The agreed project baseline is in the project instructions; this repository starts with the smallest end-to-end milestone: an Android app can call a FastAPI health endpoint.

## Day 1 scope

- FastAPI backend with Pydantic settings loaded from `.env` and a `GET /health` endpoint.
- Android Kotlin/Jetpack Compose shell with a **Check backend** button.
- Emulator default API URL: `http://10.0.2.2:8000`.
- No model keys, personal documents, or real device actions are needed for this milestone.

## Backend setup on Windows

Use Python 3.12 or 3.13. If Python 3.12 is not installed, run this once in PowerShell from any folder, then verify the runtime:

```powershell
py install 3.12
py -3.12 --version
```

Next, open PowerShell in the repository root (the folder containing this `README.md`). The project commands must run there; running them from your home folder will make files such as `requirements.txt` and `.env.example` appear missing.

```powershell
py -3.12 -m venv .venv
.\.venv\Scripts\Activate.ps1
python -m pip install --upgrade pip
python -m pip install -r requirements.txt
if (-not (Test-Path .env)) { Copy-Item .env.example .env }
python -m uvicorn app.main:app --app-dir backend --reload --host 0.0.0.0 --port 8000
```

In a second PowerShell window, confirm the health response:

```powershell
Invoke-RestMethod http://127.0.0.1:8000/health
```

Expected fields are `status`, `service`, and `version`. Interactive API docs are at `http://127.0.0.1:8000/docs`.

## Android setup

Open the `android-app` folder in Android Studio, sync the Gradle project, then run the `app` configuration on an emulator. The starter build uses Android Gradle Plugin 9.4.1, Gradle 9.6, JDK 17, Kotlin 2.4.21 with the matching Compose compiler plugin, and Compose BOM 2026.11.01. If Android Studio asks to install Android SDK Platform 37, accept it. The wrapper files are checked in so Gradle is downloaded per-user on first sync.

The default URL is for the Android emulator. To use a physical phone, connect both devices to the same private Wi-Fi, run the backend with the command above, then add this line to `android-app/local.properties` (keep any existing `sdk.dir` line):

```properties
mtech.apiBaseUrl=http://YOUR_PC_LAN_IP:8000
```

Allow Python through Windows Firewall on a private network if prompted. Plain HTTP is enabled only in the Android debug manifest; a deployed backend must use HTTPS. Keep `local.properties` and `.env` out of Git.

## Daily Git workflow

At the end of each work session:

1. Update `docs/DAILY_PROGRESS.md` with what you built, how you checked it, and the next step.
2. Review `git status` and inspect the changed files. Never add `.env`, API keys, private documents, Android build output, or anything from `sources/`.
3. Commit a focused change, for example:

   ```powershell
   git add README.md docs/DAILY_PROGRESS.md backend android-app requirements.txt .env.example .gitignore
   git commit -m "day 01: add API health check and Android shell"
   git push
   ```

For a public portfolio, create a **public** empty GitHub repository, add it as `origin`, and push the `main` branch. This local workspace has no remote configured yet. Add a license only if you want to grant reuse rights; a public repository without a license can still be viewed.

```powershell
git remote add origin https://github.com/YOUR_USERNAME/mtech-personal-ai.git
git push -u origin main
```

## Repository map

```text
backend/                 FastAPI app and settings
android-app/             Android Studio project and Compose app
docs/DAILY_PROGRESS.md   Incremental project log
AGENTS.md                Agreed project scope, security boundaries, and roadmap
.env.example             Safe configuration template
requirements.txt          Small Python dependency list
```

