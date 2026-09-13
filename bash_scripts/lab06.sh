#!/usr/bin/env bash
set -e

cd lab06-2d-ising-model
./gradlew run

uv run python script/generate_results.py
