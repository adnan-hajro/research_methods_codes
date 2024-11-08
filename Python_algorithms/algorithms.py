import time
import random
import sys
import tracemalloc

sys.setrecursionlimit(10 ** 6)  # Increase recursion limit for Quick Sort


# Bubble Sort Implementation
def bubble_sort(arr):
    n = len(arr)
    for i in range(n):
        for j in range(0, n - i - 1):
            if arr[j] > arr[j + 1]:
                arr[j], arr[j + 1] = arr[j + 1], arr[j]


# Quick Sort Implementation
def quick_sort(arr):
    if len(arr) <= 1:
        return arr
    pivot = arr[len(arr) // 2]
    left = [x for x in arr if x < pivot]
    middle = [x for x in arr if x == pivot]
    right = [x for x in arr if x > pivot]
    return quick_sort(left) + middle + quick_sort(right)


# Timing and memory measurement function for sorting algorithms
def measure_sorting_algorithm(algorithm, data):
    tracemalloc.start()  # Start memory tracking
    start_time = time.time()

    # Execute the sorting algorithm
    if algorithm == "bubble":
        bubble_sort(data)
    elif algorithm == "quick":
        data = quick_sort(data)  # Update the sorted list for Quick Sort

    end_time = time.time()
    current, peak = tracemalloc.get_traced_memory()
    tracemalloc.stop()

    execution_time = end_time - start_time
    memory_used = peak / 1024  # Convert memory usage to KB

    return execution_time, memory_used


# Test different scenarios and sizes
def test_scenarios():
    sizes = [100, 1000, 10000, 50000, 100000, 1000000]
    conditions = ["sorted", "random", "reverse"]

    for size in sizes:
        for condition in conditions:
            # Prepare the dataset
            if condition == "sorted":
                data = list(range(size))
                print(data)
            elif condition == "random":
                data = random.sample(range(size * 10), size)
            elif condition == "reverse":
                data = list(range(size, 0, -1))

            # Bubble Sort
            print(f"Testing Bubble Sort with {condition} data of size {size}")
            time_taken, memory_used = measure_sorting_algorithm("bubble", data.copy())
            print(f"Execution time: {time_taken*1000:.3f} ms\n")
            ##print(f"Memory used: {memory_used:.2f} KB\n")

            # Quick Sort
            print(f"Testing Quick Sort with {condition} data of size {size}")
            time_taken, memory_used = measure_sorting_algorithm("quick", data.copy())
            print(f"Execution time: {time_taken*1000:.3f} ms\n")
            ##print(f"Memory used: {memory_used:.2f} KB\n")


if __name__ == "__main__":
    test_scenarios()
