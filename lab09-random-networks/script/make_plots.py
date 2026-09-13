import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
from pathlib import Path
import os
import math


SCRIPT_DIR = Path(__file__).resolve().parent

ROOT_DIR = SCRIPT_DIR.parent

data_dir = ROOT_DIR / "output"
plot_dir = ROOT_DIR / "plots"

if not os.path.exists(plot_dir):
    os.makedirs(plot_dir)


files = [
    "zad4_alpha_4.0.csv",
    "zad4_alpha_10.0.csv"
]

alphas = [4.0, 10.0]

N = 100

def poisson(k, k_exp):
    return np.exp(-k_exp) * k_exp**k / math.factorial(k)

for i, file in enumerate(files):
    df = pd.read_csv(data_dir / file).values.flatten()
    counts, bin_edges = np.histogram(df, bins=np.arange(min(df), max(df) + 2))

    k_exp = alphas[i] / N * (N - 1)
    k_arr = np.arange(0, bin_edges[-1])

    y = [poisson(k, k_exp) for k in k_arr]

    fig, ax = plt.subplots()

    ax.plot(k_arr, y, label="Rozkład Poissona", ls='--', lw=0.5)
    ax.scatter(bin_edges[:-1], counts/np.sum(counts), label=f"Rozkład dla $N={N}$", color='tomato')

    ax.set_xlabel("$k$")
    ax.set_ylabel("$p_k$")
    ax.set_title(fr"$\alpha = {alphas[i]:.1f}$")

    ax.set_xlim(0, bin_edges[-2])

    plt.legend()

    plt.tight_layout()
    plt.savefig(plot_dir / f"k_plot_alpha_{alphas[i]}.pdf")