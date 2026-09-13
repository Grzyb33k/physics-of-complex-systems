import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
from pathlib import Path
import os

SCRIPT_DIR = Path(__file__).resolve().parent

ROOT_DIR = SCRIPT_DIR.parent

data_dir = ROOT_DIR / "output"
plot_dir = ROOT_DIR / "plots"

if not os.path.exists(plot_dir):
    os.makedirs(plot_dir)

df = pd.read_csv(data_dir / "zad23.csv")

fig, ax = plt.subplots(dpi=200)

L_unique = df["L"].unique()

markers = ['o', 'x', 's', '+']

for i, L in enumerate(L_unique):
    data = df[df["L"] == L]
    p = data["p"]
    W = data["W"]

    ax.plot(p, W, label=f"$L = {L}$", marker=markers[i], ms=4, lw=0.5)


ax.set_xlim(df["p"].min(), df["p"].max())
ax.set_ylim(df["W"].min(), df["W"].max())
ax.set_xlabel("$p$")
ax.set_ylabel("$W(p;L)$")
ax.grid(which="both")
plt.legend()

plt.tight_layout()
plt.savefig(plot_dir / "zad23.pdf")
