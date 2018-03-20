# $name$

[![Build Status](http://img.shields.io/travis/$github_user$/$name;format="lower,hyphen"$.svg?style=flat-square)](https://travis-ci.org/$github_user$/$name;format="lower,hyphen"$)


Usage
===

Ping Pong
---

**POST** _http://localhost:8080/ping_ Ping-Pong demo. To test application.

> curl -H "Content-Type: application/json"  -X POST -d '{"message":"hello"}' http://localhost:8080/ping

Health Monitor
---

**GET** _http://localhost:8080/health_  Health monitor

> curl -H "Content-Type: application/json"  -X GET -d '{"message":"hello"}' http://localhost:8080/health


Application Information
---

**GET** _http://localhost:8080/info_  Application Information

> curl -H "Content-Type: application/json"  -X GET  http://localhost:8080/info
