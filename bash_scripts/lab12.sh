#!/usr/bin/env bash
set -e

cd lab12-random-walk
./gradlew run

uv run python script/generate_plots.py
