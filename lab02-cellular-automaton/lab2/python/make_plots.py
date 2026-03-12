import matplotlib.pyplot as plt
import seaborn as sns
from matplotlib.animation import FuncAnimation
import pandas as pd
from pathlib import Path
import os
from matplotlib.colors import LinearSegmentedColormap

times = ["0", "1", "2", "5", "10", "20", "50", "100"]

colormap = LinearSegmentedColormap.from_list("custom_colormap", ["white", "crimson"])

SCRIPT_DIR = Path(__file__).resolve().parent

ROOT_DIR = SCRIPT_DIR.parent

data_dir = ROOT_DIR / "output"
plot_dir = ROOT_DIR / "plots"


runs = 3

for run in range(runs):
    names = [f"{run + 1}evolution_run_t{t}.csv" for t in times]
    dfs = [pd.read_csv(data_dir / name) for name in names]

    if not os.path.exists(plot_dir):
        os.makedirs(plot_dir)

    fig, ax = plt.subplots(figsize=(10, 10))

    def update(frame):
        ax.clear()
        sns.heatmap(
            dfs[frame],
            cbar=False,
            ax=ax,
            xticklabels=False,
            yticklabels=False,
            cmap=colormap
        )
        ax.set_title(f"Ewolucja dla t = {times[frame]}", fontsize=32)
        plt.tight_layout()

    ani = FuncAnimation(fig, update, frames=len(dfs), repeat=True)
    ani.save(plot_dir / f"evolution_run_{run + 1}.gif", writer="pillow", fps=2)

    fig, axes = plt.subplots(2, 4, figsize=(20, 10))

    axes_flat = axes.flatten()

    for i, df in enumerate(dfs):
        ax = axes_flat[i]
        sns.heatmap(df, cbar=False, ax=ax, xticklabels=False, yticklabels=False, cmap=colormap)
        ax.set_title(f"t = {times[i]}", fontsize=16)

    plt.suptitle("Ewolucja stanów automatu komórkowego w czasie", fontsize=24)
    plt.tight_layout()
    # plt.colorbar(plt.cm.ScalarMappable(cmap=colormap), ax=axes_flat)
    plt.savefig(plot_dir / f"evolution_heatmaps_run_{run + 1}.pdf")
