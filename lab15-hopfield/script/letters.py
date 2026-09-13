import numpy as np


def make_T():
    t = np.ones((5, 5))
    t *= -1
    t[0, :] = 1
    t[:, 2] = 1

    return t


def make_H():
    h = np.ones((5, 5)) * -1
    h[:, 0] = 1
    h[2, :] = 1
    h[:, 4] = 1

    return h


def make_A():
    a = np.ones((5, 5)) * -1
    a[0, 2] = 1

    a[1, 1] = 1
    a[1, 3] = 1

    a[2, 1] = 1
    a[2, 3] = 1

    a[3, :] = 1

    a[4, 0] = 1
    a[4, 4] = 1

    return a


def make_E():
    e = np.ones((5, 5)) * -1
    e[:, 0] = 1
    e[0, :] = 1
    e[4, :] = 1
    e[2, 0:4] = 1

    return e


def make_broken_T():
    t = np.ones((5, 5)) * -1

    t[0, :] = 1
    t[0, 3] = -1

    t[1, 2] = 1

    t[3, 0] = 1
    t[4, 2] = 1

    return t


def make_broken_H():
    h = np.ones((5, 5)) * -1
    h[:, 0] = 1
    h[2, :] = 1
    h[:, 4] = 1

    h[2, 1] = -1
    h[2:4, 4] = -1

    return h


def make_broken_A():
    a = np.ones((5, 5)) * -1

    a[1, 1] = 1
    a[1, 3] = 1

    a[2, 1] = 1
    a[2, 3] = 1

    a[4, 0] = 1
    a[4, 4] = 1

    return a
