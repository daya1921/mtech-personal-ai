# ChatGPT project context

This directory is a local mirror of the ChatGPT project “MTech”.

- Treat every file under `sources/` as read-only reference material.
- Do not edit, rename, move, or delete synced project files.
- These files may be replaced the next time a task is created from this ChatGPT project.


## Project instructions

# MTech project context — agreed baseline

Use this agreed baseline and keep the work achievable in 30 days. Do not claim novelty or measured results without literature review and experiments.

## Title and goal
**Design and Development of a Secure, Context-Aware Multi-Agent AI Assistant for Autonomous Smartphone and Simulated IoT Task Execution Using MCP, A2A and Hybrid Knowledge Retrieval.**
Build one integrated Android personal assistant: understand natural-language text/voice requests, plan multi-step tasks, delegate to specialist agents, retrieve personal/document context, call approved tools, verify results, and report status. Example: check schedule, summarize important messages, read room temperature, and prepare a study plan.

## Architecture and technology stack
- **Android client:** Kotlin, Android Studio, Jetpack Compose + Material 3; Retrofit or Ktor; Room; Android SpeechRecognizer/TTS; CameraX; WorkManager; Android Intents; optional permissioned Notification Listener. The phone executes its own actions locally after checking permissions/approval; the cloud model never gets unrestricted device control.
- **Backend/orchestration:** Python 3.12/3.13, FastAPI, Pydantic, LangGraph for state, planning, delegation, checkpoints, recovery and human approval; async tasks, WebSockets for progress, structured logs.
- **Models:** OpenRouter as configurable model gateway: reasoning for planning, fast model for simple commands, vision model for image/screenshot tasks, multilingual support (English/Hindi/Kannada), and fallback routing. Keep model IDs in config. Ollama is optional for selected offline experiments, not a first-prototype dependency.
- **Agent/tool protocols:** MCP for agent-to-tool/resource access; A2A for independent specialist-agent communication. Target MCP 2026-07-28 and A2A 1.0.0 as selected in the source architecture; use HTTP for remote agents, Agent Cards for capability discovery, authenticated requests and tracked task status.
- **Hybrid knowledge:** PostgreSQL + pgvector for chunks, metadata, structured memory and semantic search; Neo4j for entities/relationships and graph traversal; multilingual embeddings, PDF/TXT/Markdown ingestion, chunking, entity extraction, vector+graph retrieval, fusion/deduplication/reranking, grounded answers with source references.
- **Simulated IoT:** Python sensor/actuator simulator + Eclipse Mosquitto MQTT + IoT MCP server. Simulate temperature, humidity, motion, online/offline status and smart-light ON/OFF. Example topics: home/room1/temperature, humidity, motion, light/set, light/status. Tools: get_temperature, get_humidity, get_motion_status, get_device_status, set_light_state. Sensor cadence is configurable; actuator writes require policy checks.
- **Deployment/observability:** Docker + Docker Compose for reproducible local multi-service setup (API/orchestrator, agents/MCP servers, PostgreSQL/pgvector, Neo4j, Redis, Mosquitto and simulator). Add OpenTelemetry, Prometheus and Grafana as time permits. Local research prototype; make no production HA claims. Antigravity/VS Code + Android Studio are development tools, not runtime.

## Modules and agents
Modules: M1 mobile interface; M2 AI gateway; M3 LangGraph orchestrator; M4 A2A multi-agent layer; M5 MCP tool adapters; M6 hybrid knowledge system; M7 IoT simulator; M8 security/policy; M9 evaluation/observability.
Agents: main orchestrator (intent, plan, delegate, combine and verify); Research & Knowledge Agent (documents, vector/graph retrieval and evidence); Personal Planning Agent (reminders, schedules, preferences and communication tools); IoT Agent (sensor state and approved simulated actuator commands). Keep email/communication functions as MCP tools for the Planning Agent in v1, not separate agents.

## MCP/A2A flow
Android sends HTTPS requests to a FastAPI gateway; WebSockets may stream progress. LangGraph plans and selects models/tools. It delegates specialist work over A2A. Specialists call tools/resources through MCP; the IoT MCP server communicates with the simulator through MQTT. PostgreSQL/pgvector and Neo4j serve hybrid retrieval. The Android app validates and executes phone actions locally, checks permissions and any required confirmation, then returns results for orchestrator verification. MCP is agent-to-tool; A2A is agent-to-agent; MQTT is simulator messaging.

## Security and boundaries
Use JWT authentication, RBAC, request validation/rate limiting, per-tool scopes, least-privilege database credentials/networking, environment or Docker secrets, TLS outside local development, prompt-injection defenses for retrieved content, output/action validation, and redacted audit logs. Separate read and write operations. Read sensor values may be permitted; simulated light changes are controlled writes; sending a message needs explicit user confirmation; payments are out of scope. Dangerous or external actions must never run from model text alone.

## Research contribution and evaluation
Position novelty as a proposed, measured integration—not as inventing MCP, A2A, RAG, graphs or multi-agent systems. Hypothesis: protocol-based delegation + hybrid retrieval + policy-controlled recovery improves contextual smartphone/simulated-IoT task completion over a basic single-agent LLM.
Compare: A0 single LLM/no tools; A1 single agent + MCP + vector RAG; A2 proposed multi-agent A2A + MCP + hybrid RAG + recovery. Build 60 fixed tasks: 20 smartphone, 20 knowledge retrieval, 20 IoT. Record expected outputs and conditions; repeat runs to account for model variation.
Metrics: intent accuracy, task completion rate, tool-selection accuracy, Precision@K/Recall@K/MRR, graph relationship retrieval accuracy, recovery and failure rates, median/P95 latency, cost per completed task, unauthorized-action rejection rate, and throughput under concurrent requests. Ablations: disable A2A, graph retrieval, memory, recovery, and model fallback one at a time. Simulate provider timeouts, invalid tool responses, MQTT/database outages, duplicate requests, interrupted tasks and concurrent users; test retries, idempotency, checkpoints, timeouts, graceful degradation, prompt injection and approval bypass. Report actual results including failures; never invent numbers.

## Suggested repository layout
PersonalAI-Agent/{android-app/app/src/main/{java/com/personalai/{ui,voice,camera,device,permissions,network},AndroidManifest.xml},backend/app/{main.py,api,core,security,models,services,orchestration/{graph.py,state.py,nodes.py},agents/{orchestrator.py,research_agent.py,planning_agent.py,iot_agent.py},a2a/{server.py,agent_cards},mcp},iot-simulator, docker, data, tests, docs, docker-compose.yml, .env.example, README.md}. Keep Android UI/device adapters, orchestration/agents, protocol servers, simulator, deployment and evaluation code separated.

## 30-day roadmap and constraints
Days 1–5: Android shell, voice/text UI, model connection, basic chat. Days 6–10: LangGraph planning, tool calls, memory and model routing. Days 11–15: selected local Android actions and permissions/approval. Days 16–20: document/PDF retrieval, camera/screenshot understanding and optional service integrations if time permits. Days 21–25: recovery, approvals, baseline/model comparisons, task metrics and security tests. Days 26–30: bug fixes, final evaluation, diagrams, report, presentation and demo. Estimate: 6–8 hours/day.

Deliver a functional prototype, not a universal autonomous phone controller. No physical IoT hardware is required; hardware sensor behavior is simulated. Keep supported Android workflows selected and permission-dependent; WhatsApp automation is limited/platform-dependent. Support PDF/TXT/Markdown only in v1; do not build an enterprise graph pipeline. Avoid production-scale HA claims. Prioritize a small end-to-end system and reproducible measurements over adding many models/features. Never present a hypothesis as a result or claim global novelty without literature review and evidence.
