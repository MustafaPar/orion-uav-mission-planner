// Dev utility: regenerates the README screenshots in docs/screenshots/.
// Requires: `npm run dev` (frontend on :5173) and the backend/Postgres stack
// running (docker compose up), with the DataSeeder admin account available.
// Usage: node screenshot.mjs
import { chromium } from 'playwright-core';
import fs from 'fs';

const EDGE_PATH = 'C:\\Program Files (x86)\\Microsoft\\Edge\\Application\\msedge.exe';
const OUT_DIR = '../docs/screenshots';
fs.mkdirSync(OUT_DIR, { recursive: true });

const browser = await chromium.launch({ executablePath: EDGE_PATH, headless: true });
const page = await browser.newPage({ viewport: { width: 1440, height: 900 } });

await page.goto('http://localhost:5173/');
await page.waitForSelector('.login-card', { timeout: 10000 });
await page.waitForTimeout(500);

// --- Login ---
await page.fill('.login-card input[type="text"], .login-card input:not([type])', 'admin');
await page.fill('.login-card input[type="password"]', 'admin123');
await page.click('button[type="submit"]');
await page.waitForTimeout(1500);
await page.screenshot({ path: `${OUT_DIR}/01-login.png` });

// --- Dashboard ---
await page.waitForSelector('.dashboard-grid', { timeout: 5000 }).catch(() => {});
await page.waitForTimeout(800);
await page.screenshot({ path: `${OUT_DIR}/02-dashboard.png`, fullPage: true });

// --- Map (default zoom shows the whole fleet + route lines best) ---
await page.click('text=Map');
await page.waitForTimeout(2000);
await page.screenshot({ path: `${OUT_DIR}/03-map.png` });

// --- UAV Fleet ---
await page.click('text=UAVs');
await page.waitForTimeout(800);
await page.screenshot({ path: `${OUT_DIR}/05-uavs.png`, fullPage: true });

// --- Missions: assign the existing clean "Demo Recon Sweep" demo row ---
await page.click('text=Missions');
await page.waitForTimeout(500);
const assignBtn = page.locator('tr', { hasText: 'Demo Recon Sweep' }).locator('button:has-text("Auto-Assign"):not([disabled])').first();
if (await assignBtn.count() > 0) {
  await assignBtn.click();
  await page.waitForTimeout(1000);
}
await page.screenshot({ path: `${OUT_DIR}/04-missions-assign.png`, fullPage: true });

await browser.close();
console.log('Screenshots saved to', OUT_DIR);
