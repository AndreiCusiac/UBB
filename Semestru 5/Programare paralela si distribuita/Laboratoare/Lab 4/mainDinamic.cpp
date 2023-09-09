#include <iostream>
#include <fstream>
#include <thread>
#include <mutex>
#include <condition_variable>
#include <vector>

#define no_threads 8

/*
#define curr_file "0"
#define N 6
#define M 6
#define n 3
#define m 3
*/
/*
#define curr_file "1"
#define N 10
#define M 10
#define n 3
#define m 3
*/

/*
#define curr_file "2"
#define N 1000
#define M 1000
#define n 5
#define m 5
*/

/*
#define curr_file "3"
#define N 10
#define M 10000
#define n 5
#define m 5
*/

#define curr_file "4"
#define N 10000
#define M 10
#define n 5
#define m 5


using namespace std;

class my_barrier
{
public:
    my_barrier(int count) : thread_count(count), counter(0), waiting(0)
    {}

    void wait()
    {
        //fence mechanism
        unique_lock<mutex> lk(mut);
        ++counter;
        ++waiting;
        cv.wait(lk, [&]{return counter >= thread_count;});
        cv.notify_one();
        --waiting;
        if(waiting == 0)
        {  //reset barrier
            counter = 0;
        }
        lk.unlock();
    }

private:
    mutex mut;
    condition_variable cv;
    int counter;
    int waiting;
    int thread_count;
};

my_barrier barrier(no_threads);

bool equalFiles(const string& firstFile, const string& secondFile) {
    fstream first(firstFile, fstream::in), second(secondFile, fstream::in);
    char c1, c2;

    while (true) {
        c1 = first.get();
        c2 = second.get();

        if (c1 != c2) {
            return false;
        }

        if((c1==EOF)||(c2==EOF)) {
            if((c1==EOF) && (c2==EOF)) {
                break;
            }
            return false;
        }
    }

    return true;
}

void readMatrix(int **matrix, const string& fileName) {
    ifstream f(fileName);

    for (int i = 0; i < N; i++) {
        for (int j = 0; j < M; j++) {
            f >> matrix[i][j];
        }
    }
}

void readWindow(int **matrix, const string& fileName) {
    ifstream f(fileName);

    for (int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++) {
            f >> matrix[i][j];
        }
    }
}

void printMatrix(int **matrix, const string& fileName) {
    ofstream f(fileName);

    for (int i = 0; i < N; i++) {
        for (int j = 0; j < M; j++) {
            f << matrix[i][j] << " ";
        }
        f << '\n';
    }

    //cout << "File " << fileName << " successfully (over)written.\n";
}

void printThreads(int thr,  const string& fileName) {
    ofstream f(fileName);

    f << "Threads used last run: " << thr;
    //cout << "File " << fileName << " successfully (over)written.\n";
}

void calculateMatrix(int **f, int **w, int **v, int matrix_rows, int matrix_columns, int window_rows, int window_columns) {
    int row_shift = window_rows/2, column_shift = window_columns/2;
    int current_overlap_row, current_overlap_column;
    int sum, prod;

    for (int i = 0; i < matrix_rows; i++){
        for (int j = 0; j < matrix_columns; j++) {
            sum = 0;

            for (int k = 0; k < window_rows; k++){
                current_overlap_row = i - row_shift + k;
                for (int l = 0; l < window_columns; l++) {
                    current_overlap_column = j - column_shift + l;

                    if (current_overlap_row < 0 && current_overlap_column < 0 ||
                        current_overlap_row >= matrix_rows && current_overlap_column >= matrix_columns ||
                        current_overlap_row < 0 && current_overlap_column >= matrix_columns ||
                        current_overlap_row >= matrix_rows && current_overlap_column < 0) {
                        prod = f[i][j] * w[k][l];
                    } else if (current_overlap_row < 0 || current_overlap_row >= matrix_rows) {
                        prod = f[i][current_overlap_column] * w[k][l];
                    } else if (current_overlap_column < 0 || current_overlap_column >= matrix_columns) {
                        prod = f[current_overlap_row][j] * w[k][l];
                    } else {
                        prod = f[current_overlap_row][current_overlap_column] * w[k][l];
                    }

                    sum += prod;
                }
            }

            v[i][j] = sum;
        }
    }
}

void calculateMatrixByThreads(int **f, int **w, int matrix_rows, int matrix_columns, int window_rows, int window_columns, int iStart, int jStart, int pas) {
    int row_shift = window_rows/2, column_shift = window_columns/2;
    int current_overlap_row, current_overlap_column;
    int sum, prod;

    int i = iStart;
    int j = jStart;
    vector<int> buffer;

    while (i < matrix_rows) {
        sum = 0;

        for (int k = 0; k < window_rows; k++){
            current_overlap_row = i - row_shift + k;
            for (int l = 0; l < window_columns; l++) {
                current_overlap_column = j - column_shift + l;

                if (current_overlap_row < 0 && current_overlap_column < 0 ||
                    current_overlap_row >= matrix_rows && current_overlap_column >= matrix_columns ||
                    current_overlap_row < 0 && current_overlap_column >= matrix_columns ||
                    current_overlap_row >= matrix_rows && current_overlap_column < 0) {
                    prod = f[i][j] * w[k][l];
                } else if (current_overlap_row < 0 || current_overlap_row >= matrix_rows) {
                    prod = f[i][current_overlap_column] * w[k][l];
                } else if (current_overlap_column < 0 || current_overlap_column >= matrix_columns) {
                    prod = f[current_overlap_row][j] * w[k][l];
                } else {
                    prod = f[current_overlap_row][current_overlap_column] * w[k][l];
                }

                sum += prod;
            }
        }

        //v[i][j] = sum;
        buffer.push_back(sum);

        j += pas;
        if (j + pas > matrix_columns) {
            i += j / matrix_columns;
            j = j % matrix_columns;
        }
    }

    barrier.wait();

    i = iStart;
    j = jStart;
    int k = 0;

    while (i < matrix_rows) {
        f[i][j] = buffer.at(k);
        k++;

        j += pas;
        if (j + pas > matrix_columns) {
            i += j / matrix_columns;
            j = j % matrix_columns;
        }
    }
}

void calculateThreads(int **f, int **w, int matrix_rows, int matrix_columns, int window_rows, int window_columns) {
    thread* threads = new thread[no_threads];

    int iStart = 0;
    int jStart = 0;

    for (int i = 0; i < no_threads; i++) {
        threads[i] = thread(calculateMatrixByThreads, f, w, matrix_rows, matrix_columns, window_rows, window_columns, iStart, jStart, no_threads);

        jStart += 1;
        if (jStart >= M) {
            iStart += 1;
            jStart = 0;
        }
    }

    for (int i=0; i < no_threads; i++)
    {
        threads[i].join();
    }
}

int main(int argc, char *argv[]) {
    //cout << "Hello, Andrei!" << endl;

    if (argc > 0) {
        //#define no_threads = argv[2];
    }

    string path = R"(C:\Users\Andrei\Desktop\UBB\Semestru 5\Programare paralela si distribuita\Laboratoare\Lab 3\C++\)";
    string pathFinal = R"(C:\Users\Andrei\Desktop\UBB\Semestru 5\Programare paralela si distribuita\Laboratoare\Lab 4\C++\)";

    string m_file = curr_file;

    string matrix_file = path + "r" + m_file + ".txt";
    string w_file = "1";
    string window_file = path + "w" + m_file + ".txt";
    string v_file = "1";
    string final_file = pathFinal + "v" + m_file + ".txt";
    string check_file = path + "v" + m_file + "S.txt";
    string threads_used_file = pathFinal + "threads" + m_file + "S.txt";

    int** F = new int*[N];
    for (int i = 0; i < N; ++i)
        F[i] = new int[M];


    int** W = new int*[n];
    for (int i = 0; i < n; ++i)
        W[i] = new int[m];

    readMatrix(F, matrix_file);
    readWindow(W, window_file);

    auto start = chrono::steady_clock::now();
//    calculateMatrix(F, W, V, N, M, n, m); b
    calculateThreads(F, W, N, M, n, m);
    auto end = chrono::steady_clock::now();
    auto diff = end - start;

    auto timeDiff = chrono::duration <double, milli>(diff).count();

    printMatrix(F, final_file);
    printThreads(no_threads, threads_used_file);

    if (!equalFiles(final_file, check_file)) {
        throw runtime_error("Fisiere inegale!");
    } else {
        //cout << "Fisiere egale!";
        }

    //cout << "\n";

    cout << ((float) timeDiff  /*/1E6*/ );
}
