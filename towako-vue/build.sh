#!/usr/bin/env bash
npm run build:staging

docker build -t hub.c.163.com/zhangcolin/towako-vue .

docker push hub.c.163.com/zhangcolin/towako-vue
