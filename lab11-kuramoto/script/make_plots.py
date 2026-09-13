import pandas as pd
import numpy as np
import matplotlib.pyplot as plt

import os
from pathlib import Path

SCRIPT_DIR = Path(__file__).resolve().parent

ROOT_DIR = SCRIPT_DIR.parent

data_dir = ROOT_DIR / "output"
plot_dir = ROOT_DIR / "plots"
theta_dir = plot_dir / "theta"

if not os.path.exists(theta_dir):
    os.makedirs(theta_dir)


df_zad1 = pd.read_csv(data_dir / "zad1.csv")

# df_zad1

# df_zad1.describe()

# df_zad1["n"].unique(), df_zad1["k"].unique()

n_arr = df_zad1["n"].unique()
k_arr = df_zad1["k"].unique()
# i_arr = df_zad1["theta_i"].unique()

for n in n_arr:

    fig, ax = plt.subplots(dpi=200)

    ax.set_title(f"N = {n}")

    ax.set_xlabel("$t$")
    ax.set_ylabel("$r$")

    ax.set_xlim(0, 100)
    ax.set_ylim(0, 1)

    for k in k_arr:

        data = df_zad1.query("n == @n and k == @k and theta_i == 0")

        y = data["r"]
        x = data["t"]

        ax.plot(x, y, lw=1.0, label=f"$k = $ {k}")

    plt.legend(fontsize=8)

    plt.tight_layout()
    plt.savefig(plot_dir / f"zad1_r_n_{n}.pdf")


n, k = 50, 0.75

# fig, ax = plt.subplots()

# ax.set_title(f"N = {n}, K = {k}")

# for i in range(n):

#     data = df_zad1.query("n == @n and k == @k and theta_i == @i")

#     y = np.mod(data["theta_val"], 2 * np.pi)
#     x = data["t"]

#     ax.plot(x, y, lw=0.5)


# ax.set_xlabel("$t$")
# ax.set_ylabel("$\\theta$")

# plt.savefig("test.pdf")
# plt.close()

for n in n_arr:
    for k in k_arr:

        fig, ax = plt.subplots(dpi=200)
        
        ax.set_title(f"N = {n}, K = {k}")

        for i in range(n):

            data = df_zad1.query("n == @n and k == @k and theta_i == @i")

            y = np.mod(data["theta_val"], 2 * np.pi)
            x = data["t"]

            ax.plot(x, y, lw=0.5)


        ax.set_xlabel("$t$")
        ax.set_ylabel("$\\theta$")

        plt.tight_layout()
        plt.savefig(theta_dir / f"zad1_theta_n_{n}_k_{k}.pdf")
        plt.close()


df_zad2 = pd.read_csv(data_dir / "zad2.csv")

fig, ax = plt.subplots(dpi=200)

for n in df_zad2["n"].unique():

    data = df_zad2.query("n == @n")

    x = data["k"]
    y = data["r_avg"]

    ax.plot(x, y, label=f"$N = $ {n}", marker='x')

ax.set_xlabel("$K$")
ax.set_ylabel("$r$")

ax.set_xlim(x.min(), x.max())
ax.set_ylim(0.0, 1.05)

plt.legend()

plt.tight_layout()
plt.savefig(plot_dir / "zad2_r_n.pdf")

df_zad3 = pd.read_csv(data_dir / "zad3.csv")

fig, ax = plt.subplots(dpi=200)


colors = ['tomato', 'royalblue', 'green']

for i, k in enumerate(df_zad3["k"].unique()):

    for run in df_zad3["run"].unique():

        data = df_zad3.query("k == @k and run == @run")
        x = data["t"]
        y = data["r"]

        ax.plot(x, y, label=f"$K = $ {k}" if run == 0 else "", color=colors[i])

ax.set_xlabel("$t$")
ax.set_ylabel("$r$")

ax.set_xlim(0, 100)
ax.set_ylim(0, 1)

plt.legend()

plt.tight_layout()
plt.savefig(plot_dir / "zad3_r_k.pdf")