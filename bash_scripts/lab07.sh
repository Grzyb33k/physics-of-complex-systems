#!/usr/bin/env bash
set -e

cd lab07-site-percolation
./gradlew run

uv run python script/generate_plots.py
