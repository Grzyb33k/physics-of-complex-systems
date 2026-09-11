#!/usr/bin/env bash
set -e

cd lab03
./gradlew run

uv run python script/make_plots.py
