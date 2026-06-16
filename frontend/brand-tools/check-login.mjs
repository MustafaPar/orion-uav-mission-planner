import { chromium } from 'playwright-core';

const EDGE_PATH = 'C:\\Program Files (x86)\\Microsoft\\Edge\\Application\\msedge.exe';
const browser = await chromium.launch({ executablePath: EDGE_PATH, headless: true });
const page = await browser.newPage({ viewport: { width: 1000, height: 700 } });
await page.goto('http://localhost:5173/');
await page.evaluate(() => localStorage.clear());
await page.reload();
await page.waitForSelector('.login-card', { timeout: 10000 });
await page.waitForTimeout(500);
await page.screenshot({ path: 'brand-tools/out/login-check.png' });
await browser.close();
console.log('done');
