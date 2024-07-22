"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const express_1 = __importDefault(require("express"));
const morgan_1 = __importDefault(require("morgan"));
const helmet_1 = __importDefault(require("helmet"));
const express_http_proxy_1 = __importDefault(require("express-http-proxy"));
const js_yaml_1 = require("js-yaml");
const path_1 = require("path");
const fs_1 = require("fs");
const app = (0, express_1.default)();
const pathFile = (0, path_1.resolve)(process.cwd(), "config.yml");
const readConfig = (0, fs_1.readFileSync)(pathFile, { encoding: "utf8" });
const config = (0, js_yaml_1.load)(readConfig);
app.use((0, morgan_1.default)('dev'));
app.use((0, helmet_1.default)());
app.use(express_1.default.json());
app.use(express_1.default.urlencoded({ extended: true }));
app.get('/', (req, res) => {
    return res.json({
        message: "Running application"
    });
});
config.services.map((service) => {
    app.use(`/${service.name}`, (0, express_http_proxy_1.default)(`${service.url}`, { timeout: 3000 }));
});
app.listen(3000, () => console.log("Running application in port 3000"));
