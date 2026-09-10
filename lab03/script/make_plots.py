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

def evolve(xn, r) :
    return r * xn * (1 - xn)


def task1():
    r = 2
    x0 = [0.1 * i for i in range(1, 10)]

    n = 50

    plt.figure(dpi=200)


    for x in x0:
        results = []
        results.append(x)
        xn = x
        for i in range(n):
            xn = evolve(xn, r)
            results.append(xn)

        plt.plot(results, label=f'x0={x:.1f}')

    plt.legend()

    # plt.ylim(0.3, )

    plt.xlabel('$n$', fontsize=14)
    plt.ylabel('$x_n$', fontsize=14)

    plt.tight_layout()
    plt.savefig(plot_dir / 'task1.pdf')

def task2():
    x0 = 0.5
    n = 100

    r_arr = [1, 2, 3, 3.5, 3.55, 3.6]

    plt.figure(dpi=200)

    for r in r_arr:
        results = [x0]
        xn = x0
        for i in range(n):
            xn = evolve(xn, r)
            results.append(xn)

        plt.plot(results, label=f"$r=${r}", lw=1.0)

    plt.legend()

    plt.xlabel('$n$', fontsize=14)
    plt.ylabel('$x_n$', fontsize=14)

    plt.tight_layout()
    plt.savefig(plot_dir / 'task2.pdf')

def task3():
    n = 10_000
    x0 = 0.5
    r_arr = np.arange(1, 4.0, 0.0001)

    plt.figure(figsize=(13, 8), dpi=400)


    for r in r_arr:
        xn = x0
        results = []
        rr = []
        for i in range(n):
            xn = evolve(xn, r)
            if i > 9_000:
                results.append(xn)
                rr.append(r)
        plt.plot(rr, results, ',', color='navy', linestyle='None')

    plt.xlabel('$r$', fontsize=14)
    plt.ylabel('$x$', fontsize=14)

    plt.xlim(1, 4)
    plt.ylim(0, 1)

    plt.tight_layout()
    plt.savefig(plot_dir / 'task3.png') # .png is required here

if __name__ == "__main__":
    task1()

    task2()

    task3()