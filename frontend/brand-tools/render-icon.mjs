import { chromium } from 'playwright-core';
import path from 'path';
import fs from 'fs';

const EDGE_PATH = 'C:\\Program Files (x86)\\Microsoft\\Edge\\Application\\msedge.exe';
const [, , svgFile, outFile, size] = process.argv;
const px = Number(size);

const svgAbs = path.resolve(svgFile).replace(/\\/g, '/');
const html = `<!doctype html><html><head><style>
html,body{margin:0;padding:0;background:transparent;}
img{display:block;width:${px}px;height:${px}px;}
</style></head><body><img src="file:///${svgAbs}"></body></html>`;

const tmpHtml = path.resolve('brand-tools/.tmp-render.html');
fs.writeFileSync(tmpHtml, html);

const browser = await chromium.launch({ executablePath: EDGE_PATH, headless: true });
const page = await browser.newPage({ viewport: { width: px, height: px } });
await page.goto('file://' + tmpHtml);
await page.waitForTimeout(200);
fs.mkdirSync(path.dirname(outFile), { recursive: true });
await page.screenshot({ path: outFile, omitBackground: true });
await browser.close();
fs.unlinkSync(tmpHtml);
console.log('saved', outFile, `${px}x${px}`);
