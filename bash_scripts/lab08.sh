#!/usr/bin/env bash
set -e

cd lab08-soc
./gradlew run

uv run python script/generate_plots.py
