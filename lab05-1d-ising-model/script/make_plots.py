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


df1 = pd.read_csv(data_dir / 'task2_random.csv')
df2 = pd.read_csv(data_dir / 'task2_uniform.csv')

betas = df1['beta'].unique()

for beta in betas:
    data_random = df1[df1['beta'] == beta]
    data_ordered = df2[df2['beta'] == beta]
    
    fig, ax = plt.subplots(figsize=(6, 4))

    ax.plot(data_random['t'], data_random['density'], color='crimson', label="$m = 0$")
    ax.plot(data_ordered['t'], data_ordered['density'], color='teal', label='$m =  1$')
    ax.plot([0, len(data_random)], [-np.tanh(beta), -np.tanh(beta)])
    
    ax.set_title(fr"$\beta = $ {beta}")

    plt.legend()

    plt.savefig(plot_dir / f"task2_{beta}.pdf")