import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
from pathlib import Path
import os
import networkx as nx
from scipy.optimize import curve_fit

SCRIPT_DIR = Path(__file__).resolve().parent

ROOT_DIR = SCRIPT_DIR.parent

data_dir = ROOT_DIR / "output"
plot_dir = ROOT_DIR / "plots"

if not os.path.exists(plot_dir):
    os.makedirs(plot_dir)


### ZAD 1 ###

df = pd.read_csv(data_dir / "bag_edges_zad1.csv")

G = nx.from_pandas_edgelist(df, source="source", target="target")

nx.draw(G, node_size=60, with_labels=False, width=0.5)

plt.savefig(plot_dir / "zad1.pdf")


### ZAD 2 ###

df = pd.read_csv(data_dir / "bag_degrees_zad2.csv")

degrees = df["degree"].values

def p_k(k, alpha, C):
    return C * np.pow(k, -alpha)

k, counts = np.unique(degrees, return_counts=True)

pk = counts / np.sum(counts)

cut = 10

fit = curve_fit(p_k, k[:cut], pk[:cut])

fig, ax = plt.subplots(dpi=200)

ax.scatter(k, pk, label="Rozkład", color='tomato')

x = np.linspace(k[0], k[-1], 100)

ax.plot(x, p_k(x, *fit[0]), label=fr"Fit: $\alpha = $ {fit[0][0]:.2e} $\pm $ {np.sqrt(fit[1][0][0]):.2e}")

ax.set_xscale('log')
ax.set_yscale('log')

ax.set_xlabel('Liczba węzłów')
ax.set_ylabel('$P(k)$')

plt.legend()

plt.tight_layout()

plt.savefig(plot_dir / "zad2.pdf")


### ZAD 3 ###

plt.figure(dpi=200)

df = pd.read_csv(data_dir / "bag_edges_zad3.csv")

G = nx.from_pandas_edgelist(df, source="source", target="target")

nx.draw(G, node_size=60, with_labels=False, width=0.5)

plt.savefig(plot_dir / "zad3_graph.pdf")

df = pd.read_csv(data_dir / "bag_degrees_zad3.csv")

degrees = df["degree"].values

def p_k(k, alpha, C):
    return C * np.pow(k, -alpha)

k, counts = np.unique(degrees, return_counts=True)

pk = counts / np.sum(counts)

cut = 10

fit = curve_fit(p_k, k[:cut], pk[:cut])

fig, ax = plt.subplots(dpi=200)

ax.scatter(k, pk, label="Rozkład", color='tomato')

x = np.linspace(k[0], k[-1], 100)

ax.plot(x, p_k(x, *fit[0]), label=fr"Fit: $\alpha = $ {fit[0][0]:.2e} $\pm $ {np.sqrt(fit[1][0][0]):.2e}")

ax.set_xscale('log')
ax.set_yscale('log')

ax.set_xlabel('Liczba węzłów')
ax.set_ylabel('$P(k)$')

plt.legend()

plt.tight_layout()

plt.savefig(plot_dir / "zad3_plot.pdf")