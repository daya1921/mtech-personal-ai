# Daily progress

Use one short entry each day: what changed, how it was checked, and the next step. Keep the public log free of secrets and local-only details.

## Day 1 — 2026-11-26

- Built: Added the FastAPI application skeleton, environment-backed Pydantic settings, and `GET /health`. Added a Jetpack Compose Android shell with a button that requests the backend health endpoint.
- Project setup: Added Windows setup instructions and Git exclusions for secrets, generated files, and synced reference material.
- Checked: `GET /health` returned `status: ok`; Android `assembleDebug` succeeded and produced the debug APK. The Android manifests parse as XML, and the Gradle wrapper JAR checksum matches Gradle's published checksum. Local `.env`, `.venv`, and Android build output are excluded from Git.
- Installed: Python 3.12.10 and backend requirements; Android Studio and Android SDK API 37.2; Docker Desktop. Updated the Android build to AGP 9 built-in Kotlin, Java 17 bytecode, and the Compose-compatible API 37.2 compile SDK.
- WSL/Docker status: After restarting, Windows reports the WSL and Virtual Machine Platform features enabled, and firmware virtualization and SLAT available. However, Windows reports that its hypervisor is not running; WSL says virtualization is unavailable, and the Docker CLI cannot connect to the Docker engine. No Docker containers have been started.
- Next: Resolve the Windows hypervisor/WSL2 startup issue, then retry Docker. In parallel, use a physical Android device to verify the app's request to the local API; an emulator is a poor fit for this 8 GB RAM system.

### Daily entry template

## Day N — YYYY-MM-DD

- Built:
- Checked:
- Learned / issue:
- Next:
