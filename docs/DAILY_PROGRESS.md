# Daily progress

Use one short entry each day: what changed, how it was checked, and the next step. Keep the public log free of secrets and local-only details.

## Day 1 — 2026-11-26

- Built: Added the FastAPI application skeleton, environment-backed Pydantic settings, `GET /health`, and `POST /api/v1/assistant/message`. Added a Jetpack Compose text box and send button for the new endpoint. The response is explicitly marked as a development placeholder; no AI provider is connected yet.
- Project setup: Added Windows setup instructions and Git exclusions for secrets, generated files, and synced reference material.
- Checked: `GET /health` previously returned `status: ok`. The new message route is registered in FastAPI's OpenAPI schema; direct handler/schema checks confirmed text trimming, the placeholder response, and blank-input rejection. Android `assembleDebug` succeeded and produced the updated debug APK. A physical-device run is still pending because the ADB server would not start in this environment. Local `.env`, `.venv`, and Android build output are excluded from Git.
- Installed: Python 3.12.10 and backend requirements; Android Studio and Android SDK API 37.2; Docker Desktop. Updated the Android build to AGP 9 built-in Kotlin, Java 17 bytecode, and the Compose-compatible API 37.2 compile SDK.
- WSL/Docker status: After restarting, Windows reports the WSL and Virtual Machine Platform features enabled, and firmware virtualization and SLAT available. However, Windows reports that its hypervisor is not running; WSL says virtualization is unavailable, and the Docker CLI cannot connect to the Docker engine. No Docker containers have been started.
- Next: Run the app on a physical Android phone and verify the text request reaches the backend, then add the configurable model provider. Resolve the Windows hypervisor/WSL2 startup issue before retrying Docker; an emulator is a poor fit for this 8 GB RAM system.

### Daily entry template

## Day N — YYYY-MM-DD

- Built:
- Checked:
- Learned / issue:
- Next:

