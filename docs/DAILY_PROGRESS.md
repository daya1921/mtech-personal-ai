# Daily progress

Use one short entry each day: what changed, how it was checked, and the next step. Keep the public log free of secrets and local-only details.

## Day 1 — 2026-11-26

- Built: Added the FastAPI application skeleton, environment-backed Pydantic settings, `GET /health`, and `POST /api/v1/assistant/message`. Added a Jetpack Compose text box and send button for the new endpoint. Without a provider key the response is explicitly marked as a development placeholder.
- Extended: Added an optional OpenRouter chat adapter using Python's standard library, configurable model/key/timeout settings, and a safe placeholder path when no key is configured. The default model setting is OpenRouter's free-model router. Android now allows up to 45 seconds for provider replies and explains when messages are sent to OpenRouter. No provider request was made during implementation.
- Checked: Python compilation/import and the no-key placeholder response passed locally. In the initial check, the server and Android rebuild were not verified, and no provider request was made. No API key or local network address is included in this log.
- Project setup: Added Windows setup instructions and Git exclusions for secrets, generated files, and synced reference material.
- Checked: `GET /health` and `POST /api/v1/assistant/message` returned expected responses from the PC's Wi-Fi interface. After rebuilding with a local-only phone API setting and resolving private-network access, the Android app on the physical phone displayed the backend's placeholder reply. This confirms the phone-to-FastAPI text round trip; real AI responses are not connected yet. The phone-specific setting, `.env`, `.venv`, and Android build output remain excluded from Git.
- Installed: Python 3.12.10 and backend requirements; Android Studio and Android SDK API 37.2; Docker Desktop. Updated the Android build to AGP 9 built-in Kotlin, Java 17 bytecode, and the Compose-compatible API 37.2 compile SDK.
- WSL/Docker status: After restarting, Windows reports the WSL and Virtual Machine Platform features enabled, and firmware virtualization and SLAT available. However, Windows reports that its hypervisor is not running; WSL says virtualization is unavailable, and the Docker CLI cannot connect to the Docker engine. No Docker containers have been started.
- Later update: A provider key was stored only in the Git-ignored local `.env`; its presence was confirmed without displaying it. The backend started on the local network interface, and opening `GET /health` returned `200 OK`. No model prompt was sent. The Android APK was not rebuilt because Gradle could not download required dependencies in the command environment.
- Next: Confirm that the physical phone can reach the backend on the same Wi-Fi. Rebuild the Android app when Gradle dependencies are reachable. Wait for the owner to request a live model test, and resolve the Windows hypervisor/WSL2 issue before retrying Docker.

### Daily entry template

## Day N — YYYY-MM-DD

- Built:
- Checked:
- Learned / issue:
- Next:
