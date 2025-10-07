# Deploying the Invoicing ROI Simulator

This document describes quick ways to host the app online: Render (recommended for simplicity), and a container registry + any container host (Docker Hub + Fly.io / VPS).

Prerequisites
- A Docker Hub account (optional, for pushing images)
- A GitHub account (for GitHub Actions builds)
- The repository pushed to GitHub

Option A — Render (recommended)
1. Create an account on https://render.com
2. Create a new Web Service, connect your GitHub repo, branch: `main`
3. For the Build Command use:

   mvn -B -DskipTests package

4. For the Start Command use:

   java -jar target/invoicing-roi-simulator-0.0.1-SNAPSHOT.jar

5. Set environment variables on Render:
   - PORT (Render will set it automatically, but the app reads ${PORT:8080})
   - Any production DB (if you choose Postgres instead of H2): set SPRING_DATASOURCE_URL, SPRING_DATASOURCE_USERNAME, SPRING_DATASOURCE_PASSWORD

Option B — Docker image (push to Docker Hub then deploy anywhere)
1. Build locally:

   docker build -t yourusername/invoicing-roi-simulator:latest .

2. Test run locally:

   docker run -p 8080:8080 yourusername/invoicing-roi-simulator:latest

3. Push to Docker Hub:

   docker login
   docker push yourusername/invoicing-roi-simulator:latest

4. Deploy to any container host (Fly.io, DigitalOcean App Platform, AWS ECS, etc.) using the image `yourusername/invoicing-roi-simulator:latest`.

Option C — Fly.io quick deploy
1. Install flyctl and run:

   flyctl launch --name invoicing-roi-simulator --region iad

2. Build and deploy the image (fly will guide you). Ensure you set `PORT=8080` and any DB environment variables.

Notes & next steps
- The current project uses H2 (in-memory) for convenience — for production use a managed Postgres and set `spring.datasource.url` accordingly.
- Add health checks and readiness probes depending on host.
- Configure secrets (DB credentials, SMTP for report emailing) in the host's environment variables or secrets store.
