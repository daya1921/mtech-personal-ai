# Daily progress

Use one short entry each day: what changed, how it was checked, and the next step. Commit the code and this log together so the public history shows steady progress.

## Day 1 — 2026-11-26

- Added the FastAPI application skeleton, environment-backed Pydantic settings, and `GET /health`.
- Added a Jetpack Compose shell with a button that requests the backend health endpoint.
- Added Windows setup notes and Git exclusions for secrets, generated files, and synced reference material.
- Verification: both Android manifests parse as XML and the Gradle wrapper JAR matches Gradle's published SHA-256. `GET /health` returned `status: ok`; `assembleDebug` succeeded and produced the debug APK. `.env` and `sources/` are excluded from Git.
- Setup: Installed Python 3.12.10 and backend requirements; installed Android Studio and Android SDK API 37.2. Docker Desktop is installed, and the WSL/Virtual Machine Platform features are enabled pending a Windows restart.
- Android compatibility: migrated to AGP 9 built-in Kotlin, aligned Java bytecode with Java 17, and set compile SDK API 37.2 to satisfy the current Compose dependencies.
- GitHub: Updated the public repository with today’s Android build fixes and progress entry; local `.env`, `.venv`, and build output remain untracked/ignored.
- Next: restart Windows to finish WSL setup, then start Docker Desktop and connect an Android device to verify the phone-to-backend request.

### Daily entry template

## Day N — YYYY-MM-DD

- Built:
- Checked:
- Learned / issue:
- Next:
