import matplotlib.pyplot as plt
import numpy as np

# Speedups
speedups = [
    [1, 1.0],
    [2, 1.912],
    [3, 2.85],
    [4, 3.5],
    [5, 4.27],
    [6, 4.64],
    [7, 4.57],
    [8, 4.8],
    [9, 4.88]
]
xx, yy = zip(*speedups)
ideals = [
    [1, 1],
    [2, 2],
    [3, 3],
    [4, 4],
    [5, 5],
    [6, 6],
    [7, 7],
    [8, 8],
    [9, 9]
]
xxx, yyy = zip(*ideals)

plt.title('Processors vs. speedup')
plt.plot(xx, yy, color = 'blue', label = 'Actual')
plt.plot(xxx, yyy, color = 'green', label = 'Ideal')
plt.xlabel('Processors')
plt.ylabel('Speedup')
plt.legend()
plt.savefig('speedup-vs-processors.png')
plt.close()

# Absolute times
times = [
    [1, 23.709],
    [2, 12.398],
    [3, 8.310],
    [4, 6.773],
    [5, 5.501],
    [6, 5.113],
    [7, 5.188],
    [8, 4.942],
    [9, 4.863]
]
xx, yy = zip(*times)
ideals = [
    [1, 23.709],
    [2, 23.709 / 2],
    [3, 23.709 / 3],
    [4, 23.709 / 4],
    [5, 23.709 / 5],
    [6, 23.709 / 6],
    [7, 23.709 / 7],
    [8, 23.709 / 8],
    [9, 23.709 / 9]
]
xxx, yyy = zip(*ideals)

plt.title('Processors vs. time')
plt.plot(xx, yy, color = 'blue', label = 'Actual')
plt.plot(xxx, yyy, color = 'green', label = 'Ideal')
plt.xlabel('Processors')
plt.ylabel('Time, s.')
plt.yticks(np.arange(0, 25, 1))
plt.legend()
plt.savefig('time-vs-processors.png')
plt.close()

# By threshold
times = [
    [1, 11.461],
    [4, 11.823],
    [16, 10.005],
    [64, 8.649],
    [256, 8.294],
    [1024, 9.525]
]

xxx, yyy = zip(*times)

plt.title('Thresholds vs. time (fixed processor cnt = 5)')
plt.plot(xxx, yyy, color = 'blue', label = 'Actual')
plt.xlabel('Threshold')
plt.ylabel('Time, s.')
plt.xscale('log', base = 4)
plt.legend()
plt.savefig('threshold-vs-time.png')
plt.close()
