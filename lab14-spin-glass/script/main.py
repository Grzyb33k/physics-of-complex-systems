from itertools import product
import numpy as np
from pathlib import Path
import os

SCRIPT_DIR = Path(__file__).resolve().parent
ROOT_DIR = SCRIPT_DIR.parent
data_dir = ROOT_DIR / "results"

if not os.path.exists(data_dir):
    os.makedirs(data_dir)


def calc_energy(N, J):
    sigmas = list(product([-1, 1], repeat=N))

    E = []

    for sigma in sigmas:
        e = 0
        for i in range(N):
            e -= J * sigma[i] * sigma[(i + 1) % N]

        E.append(e)

    return E, sigmas


def count_stable_states(N, nrepeats=100):

    sigmas = list(product([-1, 1], repeat=N))

    mean_count = 0

    for _ in range(nrepeats):
        J = np.random.randn(N)
        count = 0

        for sigma in sigmas:
            is_stable = True

            for i in range(N):
                dE = (
                    2
                    * sigma[i]
                    * (J[(i - 1) % N] * sigma[(i - 1) % N] + J[i] * sigma[(i + 1) % N])
                )
                if dE < 0:
                    is_stable = False
                    break

            if is_stable:
                count += 1

        mean_count += count / nrepeats

    return mean_count


def count_stable_states_n2(N, nrepeats=100):
    sigmas = list(product([-1, 1], repeat=N))
    mean_count = 0

    for _ in range(nrepeats):
        J = np.random.randn(N)
        count = 0

        for sigma in sigmas:
            is_stable = True

            for i in range(N):
                left = J[(i - 1) % N] * sigma[(i - 1) % N] * sigma[i]
                right = J[(i + 1) % N] * sigma[(i + 1) % N] * sigma[(i + 2) % N]

                dEk2 = 2 * (left + right)

                dEk1 = (
                    2
                    * sigma[i]
                    * (J[(i - 1) % N] * sigma[(i - 1) % N] + J[i] * sigma[(i + 1) % N])
                )

                if dEk2 < 0 or dEk1 < 0:
                    is_stable = False
                    break

            if is_stable:
                count += 1

        mean_count += count / nrepeats

    return mean_count


def zad1():
    lines = []
    N_arr = [5, 6]
    J_arr = [1, -1]

    for J in J_arr:
        lines.append(
            f"Wyniki dla {'ferromagnetyka' if J == 1 else 'antyferromagnetyka'}."
        )

        for N in N_arr:
            lines.append(f"N = {N}")

            results = calc_energy(N, J)

            for e, conf in zip(results[0], results[1]):
                lines.append(f"Energia: {e} dla konfiguracji {conf}")

        lines.append("")

    with open(data_dir / "zad1.txt", "w") as file:
        file.write("\n".join(lines))


def zad2():
    lines = []
    N_arr = [9, 12, 15]

    for N in N_arr:
        results = count_stable_states(N)

        exp_val = 2 ** (N / 3.0)

        lines.append(
            f"N = {N}: Wartość średnia = {results:.2f}, wartość oczekiwana = {exp_val:.2f}"
        )
        lines.append("")

    with open(data_dir / "zad2.txt", "w") as file:
        file.write("\n".join(lines))


def zad3():
    lines = []
    N_arr = [9, 12, 15]

    for N in N_arr:
        results = count_stable_states_n2(N)

        exp_val = 2 ** (N / 5.0)

        lines.append(
            f"N = {N}: Wartość średnia = {results:.2f}, wartość oczekiwana = {exp_val:.2f}"
        )
        lines.append("")

    with open(data_dir / "zad3.txt", "w") as file:
        file.write("\n".join(lines))


def main():
    
    zad1()

    zad2()

    zad3()


main()