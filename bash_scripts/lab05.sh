#!/usr/bin/env bash
set -e

cd lab05-1d-ising-model
./gradlew run

uv run python script/make_plots.py
