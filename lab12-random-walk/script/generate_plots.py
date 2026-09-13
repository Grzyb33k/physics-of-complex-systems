import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
from scipy.special import binom
import seaborn as sns

import os
from pathlib import Path

SCRIPT_DIR = Path(__file__).resolve().parent

ROOT_DIR = SCRIPT_DIR.parent

data_dir = ROOT_DIR / "output"
plot_dir = ROOT_DIR / "plots"

if not os.path.exists(plot_dir):
    os.makedirs(plot_dir)


df = pd.read_csv(data_dir / "zad1a.csv")

runs = df["run"].unique()

fig, ax = plt.subplots(dpi=200)

for run in runs:
    data = df.query("run == @run")
    x = data["step"]
    y = data["distance"]

    ax.plot(x, y, lw=1.0)

ax.set_xlabel("$N$")
ax.set_ylabel("Odległość $d$")

plt.tight_layout()
plt.savefig(plot_dir / "zad1.pdf")

df = pd.read_csv(data_dir / "zad1b.csv", header=None)

data = df.values

d_range = np.linspace(-20, 20, 21, dtype=int)

N = 20

exp_val = [(1 / 2 ** N) * binom(N, (d + N) / 2) for d in d_range]

fig, ax = plt.subplots(dpi=200)

# ax.hist(data, density=True, bins=len(d_range), align="right")

ax.scatter(d_range, exp_val, label="Rozkład oczekiwany", marker='x', zorder=2)

counts, bins = np.histogram(data, bins=len(d_range) - 1, range=(-20, 20))

counts = counts / np.sum(counts)

ax.scatter(bins[:-1], counts, color='crimson', label="Rozkład próbki $n=10^6$")

ax.set_xlabel("$d$")
ax.set_ylabel("$P(d)$")

plt.legend()
plt.tight_layout()

plt.savefig(plot_dir / "zad1b.pdf")

df = pd.read_csv(data_dir / "zad2.csv")

size = np.max(df.values)

heatmap = np.zeros((2 * size + 1, 2 * size + 1))

data = df.values

for endpoint in data:
    x = endpoint[0]
    y = endpoint[1]

    heatmap[x + size, y + size] += 1

fig, ax = plt.subplots(dpi=200)

extent = [-size - 0.5, size + 0.5, -size - 0.5, size + 0.5]

a = ax.imshow(heatmap, origin="lower", extent=extent, aspect="equal", cmap='magma')
ax.set_xlabel("x")
ax.set_ylabel("y")
ax.set_xticks(np.arange(-size, size + 1, 5))
ax.set_yticks(np.arange(-size, size + 1, 5))

plt.colorbar(a, ax=ax)

mean, std = np.mean(heatmap), np.std(heatmap)

plt.title(f"Średnia liczba odwiedzin: {mean:.0f} $\\pm$ {std:.0f}")
plt.tight_layout()

plt.savefig(plot_dir / "zad2.pdf")  