import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
import tabulate

from pathlib import Path
import os

SCRIPT_DIR = Path(__file__).resolve().parent

ROOT_DIR = SCRIPT_DIR.parent

data_dir = ROOT_DIR / "output"
output_dir = ROOT_DIR / "results"

if not os.path.exists(output_dir):
    os.makedirs(output_dir)


df = pd.read_csv(data_dir / 'zad1_probabilities.csv')

print(tabulate.tabulate(df, headers='keys', tablefmt='psql', floatfmt='.2e'))

with open(output_dir / 'zad1.txt', 'w') as f:
    print(tabulate.tabulate(df, headers='keys', tablefmt='psql', floatfmt='.2e'), file=f)

df1 = pd.read_csv(data_dir / 'zad2_m_vs_t_T_1.0.csv')
df2 = pd.read_csv(data_dir / 'zad2_m_vs_t_T_2.0.csv')
df3 = pd.read_csv(data_dir / 'zad2_m_vs_t_T_2.5.csv')
df4 = pd.read_csv(data_dir / 'zad2_m_vs_t_T_4.0.csv')

dfs = [df1, df2, df3, df4]
ts = [1.0, 2.0, 2.5, 4.0]

for i, data in enumerate(dfs):
    plt.figure(dpi=200)
    x = data['t']
    y = data['m']

    plt.plot(x, y)
    plt.xscale('log')
    plt.xlim(1e-1, 1e5)
    plt.xlabel('t')
    plt.ylabel('m')
    plt.title(f"$T = ${ts[i]}")
    plt.tight_layout()
    plt.savefig(output_dir / 'zad2_t_{ts[i]}.pdf')


df1 = pd.read_csv(data_dir / 'zad3_grid_T_1.0.csv')
df2 = pd.read_csv(data_dir / 'zad3_grid_T_2.0.csv')
df3 = pd.read_csv(data_dir / 'zad3_grid_T_2.5.csv')
df4 = pd.read_csv(data_dir / 'zad3_grid_T_4.0.csv')

dfs = [df1, df2, df3, df4]

for i, data in enumerate(dfs):
    data_filtered = data[data["spin"] == -1]
    x = data_filtered["x"]
    y = data_filtered["y"]
    plt.figure(dpi=200, figsize=(5, 5))

    plt.xlabel('$x$')
    plt.ylabel('$y$')
    plt.xlim(-1, 32)
    plt.ylim(-1, 32)
    plt.scatter(x, y, s=40, color='tomato', marker='s')
    plt.title(f"$T = ${ts[i]}")
    plt.tight_layout()
    plt.savefig(output_dir / f'zad3_t_{ts[i]}.pdf')

df = pd.read_csv(data_dir / "zad4_results.csv")

fig, ax = plt.subplots(dpi=200)

T = df["T"]
m = df["m_avg"]
chi = df["chi"]

def mT(T):
    return (1 - np.sinh(2 / T) ** (-4)) ** (1/8) if T < 2.269 else 0

t_linsp = np.linspace(0.25, 4.0, 100)
y_linsp = [mT(t) for t in t_linsp]
ax.plot(t_linsp, y_linsp, color='tomato', alpha=0.5, label='Teoretyczne $m(T)$', linestyle='--')
ax.vlines([2.269, 2.269], -0.02, 1.2, label="$T_C$", color='limegreen')
plt.legend()


ax.plot(T, m, color='tomato', marker='s', linestyle='', label='$m$')
ax.set_ylabel("$m(T)$", color='tomato')
ax.set_xlim(0.25, 4.0)
ax.set_ylim(-0.02, 1)
ax2 = plt.twinx(ax)
ax2.set_xlim(0.25, 4.0)
ax2.set_ylabel(r"$\chi(T)$", color='navy')
ax2.plot(T, chi, color='navy', marker='o', linestyle='', label=r'$\chi$')

ax.set_xlabel("$T$")


plt.tight_layout()
plt.savefig(output_dir / "zad4.pdf")