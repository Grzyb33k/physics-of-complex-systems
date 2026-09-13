#!/usr/bin/env bash
set -e

cd lab11-kuramoto
./gradlew run

uv run python script/make_plots.py
