#!/usr/bin/env bash
set -e

cd lab04-virus-automaton
./gradlew run

uv run python script/generate_plots.py
