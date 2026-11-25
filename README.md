# MTech Personal AI

A lightweight research prototype for a context-aware Android assistant. The agreed project baseline is in the project instructions; this repository starts with the smallest end-to-end milestone: an Android app can call a FastAPI health endpoint.

## Day 1 scope

- FastAPI backend with Pydantic settings loaded from `.env`, `GET /health`, and `POST /api/v1/assistant/message`.
- Android Kotlin/Jetpack Compose shell with a health check and a text box that sends a request to the backend.
- The message endpoint returns an explicit placeholder unless an OpenRouter API key is configured. With a key, it sends the text to the configured model and returns its reply.
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

The text endpoint accepts a non-empty message up to 4,000 characters. Without a provider key, it returns a placeholder with `mode: "placeholder"` so the app never presents an acknowledgement as an AI reply.

## Optional AI provider

OpenRouter integration is optional and defaults to its free-model router (`openrouter/free`). Free-model availability and usage limits can change. A key is required, and each configured message is sent from your backend to OpenRouter. Keep the key only in the ignored local `.env` file; never paste it into GitHub or the Android app.

1. Create an API key in your OpenRouter account.
2. In the repository root, open `.env` and set `MTECH_OPENROUTER_API_KEY` to that key. `MTECH_OPENROUTER_MODEL=openrouter/free` is already the example default; you can change it to another model ID if desired.
3. Stop the running backend with `Ctrl+C`, then start it again using the command above so it reads the updated settings.
4. Send a short test message from the app. The response mode should be `openrouter`. If the key is absent, the endpoint continues to return the placeholder.

The backend limits provider waiting time to 35 seconds. The Android app waits up to 45 seconds for a text response. Provider errors are returned without exposing credentials or raw provider response bodies.

To check the placeholder path without sending anything to OpenRouter, leave `MTECH_OPENROUTER_API_KEY` empty and call the endpoint from PowerShell:

```powershell
$body = @{ message = "Hello from MTech" } | ConvertTo-Json
Invoke-RestMethod -Uri http://127.0.0.1:8000/api/v1/assistant/message `
  -Method Post -ContentType "application/json" -Body $body
```

The response should include `mode: "placeholder"`.

## Android setup

Open the `android-app` folder in Android Studio, sync the Gradle project, then run the `app` configuration. The starter build uses Android Gradle Plugin 9.4.1, Gradle 9.6, Java 17 bytecode compatibility, Kotlin 2.4.21 with the matching Compose compiler plugin, and Compose BOM 2026.11.01. If Android Studio asks to install Android SDK Platform 37, accept it. The wrapper files are checked in so Gradle is downloaded per-user on first sync. On an 8 GB RAM PC, a physical Android phone is usually lighter than an emulator.

The default URL is for the Android emulator. To use a physical phone, connect both devices to the same private Wi-Fi, run the backend with the command above, then add this line to `android-app/local.properties` (keep any existing `sdk.dir` line):

```properties
mtech.apiBaseUrl=http://YOUR_PC_LAN_IP:8000
```

Allow Python through Windows Firewall on a private network if prompted. Plain HTTP is enabled only in the Android debug manifest; a deployed backend must use HTTPS. Keep `local.properties` and `.env` out of Git.

## Daily Git workflow

The public repository is [daya1921/mtech-personal-ai](https://github.com/daya1921/mtech-personal-ai). At the end of each work session:

1. Update `docs/DAILY_PROGRESS.md` with what you built, how you checked it, and the next step.
2. Review `git status` and inspect the changed files. Never add `.env`, API keys, private documents, Android build output, or anything from `sources/`.
3. Commit a focused change, for example:

   ```powershell
   git add README.md docs/DAILY_PROGRESS.md backend android-app requirements.txt .env.example .gitignore
   git commit -m "day 01: add API health check and Android shell"
   git push
   ```

For local terminal-based Git work, clone the public repository into a writable folder and run commands from that clone. This ChatGPT project folder may be a read-only mirror and may not have a writable `.git` directory:

```powershell
git clone https://github.com/daya1921/mtech-personal-ai.git
cd mtech-personal-ai
```

Add a license only if you want to grant reuse rights; a public repository without a license can still be viewed.

## Repository map

```text
backend/                 FastAPI app and settings
android-app/             Android Studio project and Compose app
docs/DAILY_PROGRESS.md   Incremental project log
AGENTS.md                Agreed project scope, security boundaries, and roadmap
.env.example             Safe configuration template
requirements.txt          Small Python dependency list
```
