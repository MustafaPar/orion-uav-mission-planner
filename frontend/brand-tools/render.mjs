import { chromium } from 'playwright-core';
import path from 'path';
import fs from 'fs';

const EDGE_PATH = 'C:\\Program Files (x86)\\Microsoft\\Edge\\Application\\msedge.exe';
const [, , htmlFile, outFile, width, height] = process.argv;

const browser = await chromium.launch({ executablePath: EDGE_PATH, headless: true });
const page = await browser.newPage({ viewport: { width: Number(width) || 1000, height: Number(height) || 600 } });
await page.goto('file://' + path.resolve(htmlFile));
await page.waitForTimeout(300);
fs.mkdirSync(path.dirname(outFile), { recursive: true });
await page.screenshot({ path: outFile, omitBackground: false, fullPage: true });
await browser.close();
console.log('saved', outFile);
