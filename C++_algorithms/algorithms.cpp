#include <iostream>
#include <fstream> // Include for file output
#include <vector>
#include <algorithm>
#include <chrono>
#include <cstdlib>
#include <malloc.h> // for mallinfo on Linux

std::ofstream outputFile("results.txt"); // Create file stream for output

void bubbleSort(std::vector<int>& arr) {
    int n = arr.size();
    for (int i = 0; i < n - 1; i++) {
        for (int j = 0; j < n - i - 1; j++) {
            if (arr[j] > arr[j + 1]) {
                std::swap(arr[j], arr[j + 1]);
            }
        }
    }
}

std::vector<int> quickSort(const std::vector<int>& arr) {
    if (arr.size() <= 1) return arr;

    int pivot = arr[arr.size() / 2];
    std::vector<int> left, middle, right;

    for (int x : arr) {
        if (x < pivot) left.push_back(x);
        else if (x == pivot) middle.push_back(x);
        else right.push_back(x);
    }

    auto leftSorted = quickSort(left);
    auto rightSorted = quickSort(right);

    leftSorted.insert(leftSorted.end(), middle.begin(), middle.end());
    leftSorted.insert(leftSorted.end(), rightSorted.begin(), rightSorted.end());

    return leftSorted;
}

void measureSortingAlgorithm(const std::string& algorithm, std::vector<int> data) {
    struct mallinfo memInfoBefore = mallinfo();

    auto start = std::chrono::high_resolution_clock::now();

    if (algorithm == "bubble") {
        bubbleSort(data);
    } else if (algorithm == "quick") {
        data = quickSort(data);
    }

    auto end = std::chrono::high_resolution_clock::now();
    std::chrono::duration<double, std::milli> timeTaken = end - start;

    struct mallinfo memInfoAfter = mallinfo();
    int memoryUsed = memInfoAfter.uordblks - memInfoBefore.uordblks;

    outputFile << "Execution time: " << timeTaken.count() << " ms\n";
    outputFile << "Memory used: " << memoryUsed / 1024.0 << " KB\n";
}

void testScenarios() {
    std::vector<int> sizes = {100, 1000, 10000, 50000};
    std::string conditions[] = {"sorted", "random", "reverse"};

    for (int size : sizes) {
        for (const std::string& condition : conditions) {
            std::vector<int> data(size);

            if (condition == "sorted") {
                for (int i = 0; i < size; ++i) data[i] = i;
            } else if (condition == "random") {
                for (int i = 0; i < size; ++i) data[i] = rand() % (size * 10);
            } else if (condition == "reverse") {
                for (int i = 0; i < size; ++i) data[i] = size - i;
            }

            outputFile << "Testing Bubble Sort with " << condition << " data of size " << size << std::endl;
            measureSortingAlgorithm("bubble", data);

            outputFile << "Testing Quick Sort with " << condition << " data of size " << size << std::endl;
            measureSortingAlgorithm("quick", data);
        }
    }
}

int main() {
    testScenarios();
    outputFile.close(); // Close file after writing
    return 0;
}
