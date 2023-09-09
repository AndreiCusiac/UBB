#include <iostream>
#include <fstream>
#include <thread>

#define no_threads 16

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

int F[N][M];
int W[n][m];
int V[N][M];

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

void readMatrix(int matrix[N][M], const string& fileName) {
    ifstream f(fileName);

    for (int i = 0; i < N; i++) {
        for (int j = 0; j < M; j++) {
            f >> matrix[i][j];
        }
    }
}

void readWindow(int matrix[n][m], const string& fileName) {
    ifstream f(fileName);

    for (int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++) {
            f >> matrix[i][j];
        }
    }
}

void printMatrix(int matrix[N][M],  const string& fileName) {
    ofstream f(fileName);

    for (int i = 0; i < N; i++) {
        for (int j = 0; j < M; j++) {
            f << matrix[i][j] << " ";
        }
        f << '\n';
    }

    cout << "File " << fileName << " successfully (over)written.\n";
}

void calculateMatrix(int f[N][M], int w[n][m], int v[N][M], int matrix_rows, int matrix_columns, int window_rows, int window_columns) {
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

void calculateMatrixByThreads(int f[N][M], int w[n][m], int v[N][M], int matrix_rows, int matrix_columns, int window_rows, int window_columns, int iStart, int jStart, int pas) {
    int row_shift = window_rows/2, column_shift = window_columns/2;
    int current_overlap_row, current_overlap_column;
    int sum, prod;

    int i = iStart;
    int j = jStart;

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

        v[i][j] = sum;

        j += pas;
        if (j + pas > matrix_columns) {
            i += j / matrix_columns;
            j = j % matrix_columns;
        }
    }
}

void calculateThreads(int f[N][M], int w[n][m], int v[N][M], int matrix_rows, int matrix_columns, int window_rows, int window_columns) {
    thread* threads = new thread[no_threads];

    int iStart = 0;
    int jStart = 0;

    for (int i = 0; i < no_threads; i++) {
        threads[i] = thread(calculateMatrixByThreads, f, w, v, matrix_rows, matrix_columns, window_rows, window_columns, iStart, jStart, no_threads);

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

    string m_file = curr_file;

    string matrix_file = path + "r" + m_file + ".txt";
    string w_file = "1";
    string window_file = path + "w" + m_file + ".txt";
    string v_file = "1";
    string final_file = path + "v" + m_file + ".txt";
    string check_file = path + "v" + m_file + "S.txt";

    readMatrix(F, matrix_file);
    readWindow(W, window_file);

    auto start = chrono::steady_clock::now();
//    calculateMatrix(F, W, V, N, M, n, m);
    calculateThreads(F, W, V, N, M, n, m);
    auto end = chrono::steady_clock::now();
    auto diff = end - start;

    auto timeDiff = chrono::duration <double, milli>(diff).count();

    //printMatrix(V, final_file);

    if (!equalFiles(final_file, check_file)) {
        throw runtime_error("Fisiere inegale!");
    } else {
        //cout << "Fisiere egale!";
    }

    //cout << "\n";

    cout << ((float) timeDiff /*/ 1E6*/ );
}
