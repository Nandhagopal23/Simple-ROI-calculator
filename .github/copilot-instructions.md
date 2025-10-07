## Purpose

This file tells AI coding agents how to get productive in this repository quickly. The workspace currently has no source files checked in; these instructions therefore focus on discovery steps, likely project layouts for a "simple-ROI-calculator" app, and safe, repository-local actions to take. If project files appear later, follow the discovery checklist below and update this file with concrete examples.

## One-line contract for the agent
- Input: repository root with potential code for an ROI calculator.
- Output: small, well-scoped code edits, PR-ready branches, and clear run/test instructions.
- Error modes: repository is empty, missing build files, or ambiguous stack — when detected, create a short diagnostic and ask for missing artifacts.

## Quick discovery checklist (run immediately)
1. Look for these files at repository root and stop when found: `README.md`, `package.json`, `pom.xml`, `pyproject.toml`, `requirements.txt`, `src/`, `public/`, `static/`.
2. If a `.github/copilot-instructions.md` (or AGENT.md/AGENTS.md) already exists, merge changes: preserve existing human-written guidance, append new run/debug commands, and clearly mark any guesses as "assumptions".
3. If the repo is empty, create a short report file `DIAGNOSTIC.md` listing which files are missing and recommended next steps.

## Likely architectures to expect (and how to verify)
- Static frontend (HTML + vanilla JS): look for `index.html`, `src/` or `static/` with `.html` and `.js` files. Run locally by opening `index.html` or serving via `npx http-server .`.
- Node.js single-page app: look for `package.json`. Typical commands: `npm install` then `npm run start` or `npm run build`.
- Java / Spring Boot backend + static frontend: look for `pom.xml` / `mvnw` and `src/main/java` and `src/main/resources/static`. Run with `./mvnw spring-boot:run` or `mvn spring-boot:run`.
- Python Flask/FastAPI: look for `requirements.txt` / `pyproject.toml` and `app.py` or `main.py`. Run via the recommended command in the project, otherwise `python -m flask run` or `uvicorn main:app --reload`.

## Concrete commands to try (safe, in repo root)
- Check for files: `ls -la` or `git status`.
- If `package.json` exists:
  - `npm ci` then `npm start` (or `npm run dev`).
- If `pom.xml` or `mvnw` exists:
  - `./mvnw -v` then `./mvnw spring-boot:run` (Windows: `mvnw.cmd` when appropriate).
- If Python files exist:
  - create virtualenv, `pip install -r requirements.txt`, then `python main.py` or `uvicorn main:app --reload`.

## Project-specific conventions and patterns (what to look for)
- Naming: look for `ROI`, `roi`, `calculator`, `scenario`, `report` in filenames or class names — these are likely domain objects.
- Persistent scenarios: search for `save`, `load`, `scenario`, `localStorage` (frontend) or REST endpoints like `/scenarios` (backend).
- Reports or exports: search for `download`, `csv`, `pdf`, `report.html` — these indicate export flows.
- If backend + frontend are present, the frontend is typically served from `src/main/resources/static` or proxied in `package.json` via `proxy` field.

## Editing / PR guidance for agents
- Make small, testable changes and include:
  - a short description of the change and reasoning
  - how to run locally to verify (exact commands)
  - one automated test or a manual smoke-test checklist
- Branch naming: `copilot/<short-description>`.
- When changing behavior, include a short example input and expected output in the PR body.

## When files are missing (this repo currently)
- Create `DIAGNOSTIC.md` listing missing entry points (README, build manifest, src/). Example content:
  - "No `package.json`, `pom.xml`, or `pyproject.toml` found — unknown stack. Please add source or tell the agent which language to scaffold."
- Offer to scaffold a minimal example for one of the supported stacks (ask user to pick: static HTML, Node.js, Spring Boot, Python).

## Merge policy for existing `.github/copilot-instructions.md`
- If an existing file is detected later, preserve human-written sections verbatim. Add an "AI additions" section at the end with automated suggestions. Mark any assumptions.

## Where to look for examples in this repo (update when files exist)
- Frontend: `src/`, `public/`, `static/`, or `src/main/resources/static`.
- Backend: `src/main/java` (Java), `server/` or `api/` (Node/Python).
- Tests: `test/`, `spec/`, `src/test/java`, or `__tests__`.

## Feedback loop
- If anything here is unclear or you want the agent to assume a particular stack and scaffold boilerplate, reply with the preferred stack and whether to create a minimal runnable prototype. After that the agent will:
  1. create a tiny scaffold (README + minimal app + run instructions),
  2. run tests locally (where possible),
  3. open a draft PR branch.

---
Last updated: automatically generated. If this repo acquires source code, update the examples section with real file paths and one-liner run commands extracted from the project.
