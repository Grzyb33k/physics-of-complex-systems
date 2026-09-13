import numpy as np
import matplotlib.pyplot as plt
import pandas as pd
from pathlib import Path
import os
from scipy.optimize import curve_fit


SCRIPT_DIR = Path(__file__).resolve().parent

ROOT_DIR = SCRIPT_DIR.parent

data_dir = ROOT_DIR / "output"
plot_dir = ROOT_DIR / "plots"

if not os.path.exists(plot_dir):
    os.makedirs(plot_dir)

df = pd.read_csv(data_dir / "zad2.txt").values.flatten()

max_val = np.max(df)
n = max_val / 4
print(max_val, n)

data = np.zeros(int(n))

for val in df:
    data[int(val / 4) - 1] += 1

data /= np.sum(data)

### FITTING PARAMETER TAU

def f_fit(x, tau, C):
    return C * x ** (-tau)

cut = 20

x = np.linspace(4, max_val, int(n))

fit = curve_fit(f_fit, x[:cut], data[:cut])
x_fit = np.logspace(1, 6, base=4, num=1000)

x = np.linspace(4, max_val, int(n))

### PLOTTING DATA ###

fig, ax = plt.subplots(dpi=200)

ax.scatter(x, data, color='tomato', s=5)
ax.plot(x_fit, f_fit(x_fit, *fit[0]), color='indigo', linewidth=1, label=fr"Fit: $\tau = {fit[0][0]:.2f} \pm {np.sqrt(fit[1][0][0]):.3f}$")

ax.set_xscale('log')
ax.set_yscale('log')

ax.set_xlabel('$s$')
ax.set_ylabel('$P(s)$')

ax.grid(which='both', ls='--', color='grey', lw=0.2)

plt.legend()

plt.tight_layout()
plt.savefig(plot_dir / 'zad2.pdf')