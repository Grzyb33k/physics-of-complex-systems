#!/usr/bin/env bash
set -e

cd lab10-random-networks-2
./gradlew run

uv run python script/make_plots.py
