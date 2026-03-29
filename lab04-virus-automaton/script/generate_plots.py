import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns
from matplotlib.colors import LinearSegmentedColormap
from matplotlib.animation import FuncAnimation
from pathlib import Path
import os

cmap = LinearSegmentedColormap.from_list('custom_cmap', ["#000000", "#fc4242ff", "#4747ff"])

SCRIPT_DIR = Path(__file__).resolve().parent

ROOT_DIR = SCRIPT_DIR.parent

data_dir = ROOT_DIR / "output"
plot_dir = ROOT_DIR / "plots"
gifs_dir = ROOT_DIR / "gifs"

if not os.path.exists(plot_dir):
    os.makedirs(plot_dir)

if not os.path.exists(gifs_dir):
    os.makedirs(gifs_dir)


def generate_animation(df, alpha):
    fig, ax = plt.subplots(dpi=200)

    def update(frame):
        ax.clear()
        data = df[df['i'] == frame].pivot(index='x', columns='y', values='value')

        sns.heatmap(data,
                    cmap=cmap,
                    vmin=-1,
                    vmax=1,
                    cbar=False,
                    ax=ax,
                    xticklabels=False,
                    yticklabels=False)
        
        ax.set_title(fr'Time: {frame}, $\alpha = $ {alpha:.1f}', fontsize=12)
        plt.tight_layout()

    anim = FuncAnimation(fig, update, frames=df['i'].unique(), repeat=True)
    return anim


def make_stats_plot(df, alpha):
    def count_at_i(df, i):
        return df[df['i'] == i]['value'].value_counts()

    susceptible_counts = []
    infected_counts = []
    recovered_counts = []

    for i in range(len(df['i'].unique())):
        counts = count_at_i(df, i)
        susceptible_counts.append(counts.get(-1, 0))
        infected_counts.append(counts.get(0, 0))
        recovered_counts.append(counts.get(1, 0))

    size = df['x'].nunique() * df['y'].nunique()

    susceptible_counts = np.array(susceptible_counts) / size
    infected_counts = np.array(infected_counts) / size
    recovered_counts = np.array(recovered_counts) / size

    fig, ax = plt.subplots(figsize=(10, 6), dpi=200)
    ax.plot(susceptible_counts, label='Susceptible', color='black')
    ax.plot(infected_counts, label='Infected', color='tomato')
    ax.plot(recovered_counts, label='Recovered', color='royalblue')
    ax.set_xlabel('Time')
    ax.set_ylabel('Counts / Total')
    ax.set_title(fr'Epidemic Evolution ($\alpha$={alpha})')
    ax.set_xlim(0, 100)
    ax.legend()
    ax.grid()
    plt.tight_layout()
    plt.savefig(plot_dir / f'epidemic_evolution_stats_{alpha}.pdf')
    return fig

def make_infected_plots():
    def count_at_i(df, i):
        return df[df['i'] == i]['value'].value_counts()
    
    fig, ax = plt.subplots(figsize=(10, 6), dpi=200)

    colors = plt.cm.rainbow(np.linspace(0, 1, 9))

    for i in range(9):
        alpha = (i + 1) / 10
        filename = data_dir / f'epidemic_evolution_{alpha}.csv'
        df = pd.read_csv(filename)

        infected_counts = []
        for j in range(len(df['i'].unique())):
            counts = count_at_i(df, j)
            # susceptible_counts.append(counts.get(-1, 0))
            infected_counts.append(counts.get(0, 0))
            # recovered_counts.append(counts.get(1, 0))
        
        size = df['x'].nunique() * df['y'].nunique()
        infected_counts = np.array(infected_counts) / size

        ax.plot(infected_counts, label=fr'$\alpha$={alpha:.1f}', color=colors[i])

    ax.set_xlabel('Time')
    ax.set_ylabel('Infected / Total')
    ax.set_title(r'Evolution of infected population for different $\alpha$')
    ax.set_xlim(0, 200)
    ax.set_ylim(0,)
    ax.legend()
    ax.grid()
    plt.tight_layout()
    plt.savefig(plot_dir / 'infected_evolution.pdf')
    return fig


for i in range(3):
    alpha = (i + 1) / 10
    filename = data_dir / f'epidemic_evolution_{alpha}.csv'
    df = pd.read_csv(filename)
    
    # save_filename = gifs_dir / f'epidemic_evolution_{alpha}.gif'
    save_filename = gifs_dir / f'epidemic_evolution_{alpha}.mp4'
    # generate_animation(df, alpha).save(save_filename, writer='pillow', fps=10)
    generate_animation(df, alpha).save(save_filename, writer='ffmpeg', fps=10)


for i in range(9):
    alpha = (i + 1) / 10
    filename = data_dir / f'epidemic_evolution_{alpha}.csv'
    df = pd.read_csv(filename)
    
    make_stats_plot(df, alpha);


make_infected_plots();