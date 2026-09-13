import numpy as np
import tabulate

from pathlib import Path
import os

from letters import *

SCRIPT_DIR = Path(__file__).resolve().parent
ROOT_DIR = SCRIPT_DIR.parent
data_dir = ROOT_DIR / "results"

if not os.path.exists(data_dir):
    os.makedirs(data_dir)


def make_w_ij(learning_set):
    w_ij = np.sum([np.outer(a, a) for a in learning_set], axis=0)

    np.fill_diagonal(w_ij, 0)

    return w_ij


def recognize(broken: np.ndarray, w_ij):
    s = broken.flatten()

    is_stabilized = False

    while not is_stabilized:
        m = w_ij @ s

        s_new = np.where(m == 0, s, np.sign(m))

        s = s_new

        if np.array_equal(s, s_new):
            is_stabilized = True

    return s


def fmt(x):
    return "X" if x == 1 else " "


def fmt_matrix(matrix):
    return [[fmt(x) for x in row] for row in matrix]


def zad1():
    learning_set = [make_A(), make_H(), make_T()]

    w_ij = make_w_ij(learning_set)

    w_ij_formatted = tabulate.tabulate(w_ij)

    with open(data_dir / "zad1.txt", "w") as file:
        file.write("Macierz w_ij dla liter A, H i T\n")
        file.write(w_ij_formatted)


def zad2():
    learning_set = [make_A(), make_H(), make_T()]
    broken_set = [make_broken_A(), make_broken_H(), make_broken_T()]

    w_ij = make_w_ij(learning_set)

    with open(data_dir / "zad2.txt", "w") as file:
        for ls, bs in zip(learning_set, broken_set):
            file.write("Wzór popsuty:\n")

            file.write(tabulate.tabulate(fmt_matrix(bs)))

            restored = recognize(bs, w_ij).reshape((5, 5))

            file.write("\nWzór odtworzony:\n")
            file.write(tabulate.tabulate(fmt_matrix(restored)))

            is_the_same = np.array_equal(ls, restored)

            file.write(
                f"\nWzór odtworzono {'prawidłowo' if is_the_same else 'niepoprawnie'}\n\n\n"
            )


def zad3():
    learning_set = [make_A(), make_H(), make_T(), make_E()]
    broken_set = [make_broken_A(), make_broken_H(), make_broken_T()]
    restoring_set = [make_A(), make_H(), make_T()]

    w_ij = make_w_ij(learning_set)

    with open(data_dir / "zad3.txt", "w") as file:
        for ls, bs in zip(restoring_set, broken_set):
            file.write("Wzór popsuty:\n")

            file.write(tabulate.tabulate(fmt_matrix(bs)))

            restored = recognize(bs, w_ij).reshape((5, 5))

            file.write("\nWzór odtworzony:\n")
            file.write(tabulate.tabulate(fmt_matrix(restored)))

            is_the_same = np.array_equal(ls, restored)

            file.write(
                f"\nWzór odtworzono {'prawidłowo' if is_the_same else 'niepoprawnie'}\n\n\n"
            )

        file.write(
            f"Dla siatki o rozmiarze 5x5 mamy 25 neuronów, a maksymalna pamięć to około {5**2 * 0.138:.2f}. Dodając zatem dodatkową literę E przepełniamy sieć."
        )


def main():
    zad1()

    zad2()

    zad3()


main()
