#!/usr/bin/env bash
set -e

cd lab09-random-networks
./gradlew run

uv run python script/make_plots.py
