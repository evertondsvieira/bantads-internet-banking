import express from "express"
import logger from "morgan"
import helmet from "helmet"
import httpProxy from "express-http-proxy"

import { load } from "js-yaml"
import { resolve } from "path"
import { readFileSync } from "fs"

interface Config {
  services: { name: string; url: string }[]
}

const app = express()

const pathFile = resolve(process.cwd(), "config.yml")
const readConfig = readFileSync(pathFile, { encoding: "utf8" })
const config: Config = load(readConfig) as Config

app.use(logger('dev'))
app.use(helmet())
app.use(express.json())
app.use(express.urlencoded({ extended: true }))

app.get('/', (req, res) => {
  return res.json({
    message: "Running application"
  })
})

config.services.map((service) => {
  app.use(`/${service.name}`, httpProxy(`${service.url}`, { timeout: 3000 }))
})

app.listen(3000, () => console.log("Running application in port 3000"))
