// MPIHelloWorld.cpp : This file contains the 'main' function. Program execution begins and ends there.
//

#include <iostream>
#include <fstream>
#include <stdlib.h>
#include <string.h>
#include <chrono>
#include "mpi.h"

using namespace std;

#define SIZE 12
#define MAX_NUM_PROCS 100


bool equalFiles(const string& firstFile, const string& secondFile) {
	fstream first(firstFile, fstream::in), second(secondFile, fstream::in);
	char c1, c2;

	while (true) {
		c1 = first.get();
		c2 = second.get();

		////std::cout << c1 << " " << c2 << '\n';

		if (c1 != c2) {
			////std::cout << "c1 != c2";
			return false;
		}

		if ((c1 == EOF) || (c2 == EOF)) {
			if ((c1 == EOF) && (c2 == EOF)) {
				break;
			}
			////std::cout << "c1 EOF != c2 EOF";
			return false;
		}
	}

	return true;
}

void readNumber(int number[], const string& fileName) {
	ifstream f(fileName);

	int n;
	f >> n;

	for (int i = 0; i < n; i++) {
		f >> number[i];
	}
}

void printNumber(int* number, int len, const string& fileName) {
	ofstream f(fileName);

	f << len << '\n';

	for (int i = 0; i < len; i++) {
		f << number[len] << '\n';
	}
}

void secvential(string file1, string file2, string file3, string fileCheck)
{
	//std::cout << "Secvential \n";

	ifstream f1(file1);
	ifstream f2(file2);
	ofstream f3(file3);

	int noDigits1 = 0, noDigits2 = 0;
	f1 >> noDigits1;
	f2 >> noDigits2;

	int* n1 = new int[noDigits1];
	int* n2 = new int[noDigits2];

	readNumber(n1, file1);
	readNumber(n2, file2);

	int diff, maxx, noDigits3;

	if (noDigits1 > noDigits2)
	{
		noDigits3 = noDigits1;
		diff = noDigits1 - noDigits2;
		maxx = noDigits1;
	}
	else
	{
		noDigits3 = noDigits2;
		diff = noDigits2 - noDigits1;
		maxx = noDigits2;
	}

	int* n3 = new int[noDigits3 + 1];
	int carry = 0, sum;

	for (int i = 0; i < maxx; i++)
	{
		if (i >= noDigits1)
		{
			sum = n2[i] + carry;
		}
		else if (i >= noDigits2)
		{
			sum = n1[i] + carry;
		}
		else
		{
			sum = n1[i] + n2[i] + carry;
		}

		n3[i] = sum % 10;
		carry = sum / 10;
	}

	n3[noDigits3] = carry;

	////std::cout << "n3: ";

	f3 << noDigits3 + 1 << '\n';

	for (int i = 0; i < noDigits3 + 1; i++)
	{
		////std::cout << n3[i] << " ";
		f3 << n3[i] << '\n';
	}

	f3.close();

	if (equalFiles(file3, fileCheck) == false)
	{
		throw new exception("Fisiere inegale!\n");
		////std::cout << "Fisiere inegale!";
	}
	else
	{
		//std::cout << "Fisiere egale!\n";
	}
}

void varianta1(int argc, char* argv[], string file1, string file2, string file3, string fileCheck) {
	ifstream f1(file1);
	ifstream f2(file2);
	ofstream f3(file3);

	int noDigits1 = 0, noDigits2 = 0, noDigits3 = 0;
	f1 >> noDigits1;
	f2 >> noDigits2;

	////std::cout << "NoDigits2: " << noDigits2 << '\n';

	int rank, numprocs;

	int j1 = 0, j2 = 0;

	int diff, maxxDigits;

	if (noDigits1 > noDigits2)
	{
		noDigits3 = noDigits1;
		diff = noDigits1 - noDigits2;
		maxxDigits = noDigits1;
	}
	else
	{
		noDigits3 = noDigits2;
		diff = noDigits2 - noDigits1;
		maxxDigits = noDigits2;
	}

	////std::cout << "Argc: " << argc << '\n';
	////std::cout << "Argv[0]: " << argv[0] << '\n';
	// //std::cout << "Argv[1]: " << argv[1] << '\n';
	////std::cout << "Argv: " << *argv << '\n';
	////std::cout << "Before init\n";

	MPI_Init(&argc, &argv);
	MPI_Comm_rank(MPI_COMM_WORLD, &rank);
	MPI_Comm_size(MPI_COMM_WORLD, &numprocs);

	//int len = SIZE / numprocs;
	int len1 = noDigits1 / numprocs;
	int rest1 = noDigits1 % numprocs;
	int len2 = noDigits2 / numprocs;
	int rest2 = noDigits2 % numprocs;
	int len3 = noDigits3 / numprocs;
	int r1, r2, r3, rest3, rMaxx, restMaxx;
	int lenMaxx = maxxDigits / numprocs;
	restMaxx = maxxDigits % numprocs;

	int start1 = 0;
	int end1 = start1 + len1;
	int start2 = 0;
	int end2 = start2 + len2;
	int start3, end3;
	int startMaxx = 0;
	int endMaxx = startMaxx + lenMaxx;

	/*
	a1 = new int[len];
	b1 = new int[len];
	c1 = new int[len];*/

	std::cout << "\nProcess rank: " << rank << "; Num of processes: " << numprocs << '\n';
	////std::cout << "NoDigits3: " << noDigits3 << "; len1, 2, 3: " << len1 << ", " << len2 << ", " << len3 << '\n';

	if (rank == 0)
	{
		int carry = 0, sum;
		int* n1 = new int[maxxDigits];
		int* n2 = new int[maxxDigits];
		int* n3 = new int[maxxDigits + 1];

		for (int i = 1; i < numprocs; i++)
		{
			if (restMaxx > 0)
			{
				endMaxx += 1;
				restMaxx--;
				rMaxx = 1;
			}
			else
			{
				rMaxx = 0;
			}

			for (j1 = startMaxx; j1 < endMaxx; j1++)
			{
				if (j1 < noDigits1) {
					f1 >> n1[j1];
				}
				else {
					n1[j1] = 0;
				}
			}

			for (j2 = startMaxx; j2 < endMaxx; j2++)
			{
				if (j2 < noDigits2) {
					f2 >> n2[j2];
				}
				else {
					n2[j2] = 0;
				}
			}

			/*cout << "startMaxx: " << startMaxx << '\n';
			cout << "endMaxx: " << endMaxx << '\n';
			cout << "lenMaxx: " << lenMaxx << '\n';
			cout << "rMaxx: " << rMaxx << "\n\n";*/

			MPI_Send(&rMaxx, 1, MPI_INT, i, 11111, MPI_COMM_WORLD);
			MPI_Send(n1 + startMaxx, lenMaxx + rMaxx, MPI_INT, i, 11111, MPI_COMM_WORLD);
			MPI_Send(n2 + startMaxx, lenMaxx + rMaxx, MPI_INT, i, 11111, MPI_COMM_WORLD);

			startMaxx = endMaxx;
			endMaxx += lenMaxx;
		}

		MPI_Status status;
		// citeste in proces 0

		//std::cout << "\nCarry primit: " << carry << '\n';

		std::cout << "\nVoi citi n1 de la startMaxx = " << startMaxx << " pana la endMaxx = " << endMaxx << '\n';
		//std::cout << "\nCititre n1: " << '\n';

		for (j1 = startMaxx; j1 < endMaxx; j1++)
		{
			if (j1 < noDigits1) {
				f1 >> n1[j1];
			}
			else {
				n1[j1] = 0;
			}
			//std::cout << n1[j1] << ' ';
		}

		//std::cout << "\nVoi citi n2 de la startMaxx = " << startMaxx << " pana la endMaxx = " << endMaxx << '\n';
		//std::cout << "\nCititre n2: " << '\n';

		for (j2 = startMaxx; j2 < endMaxx; j2++)
		{
			if (j2 < noDigits2) {
				f2 >> n2[j2];
			}
			else {
				n2[j2] = 0;
			}
			//std::cout << n2[j2] << ' ';
		}

		if (numprocs > 1)
		{
			MPI_Recv(&carry, 1, MPI_INT, numprocs - 1, 11223, MPI_COMM_WORLD, &status);
		}

		//std::cout << "\n\startMaxx: " << startMaxx << "; endMaxx: " << endMaxx << '\n';
		// calculeaza in proces 0

		for (int i = startMaxx; i < endMaxx; i++)
		{
			sum = n1[i] + n2[i] + carry;
			n3[i] = sum % 10;
			carry = sum / 10;
		}

		n3[noDigits3] = carry;

		startMaxx = 0;

		lenMaxx = maxxDigits / numprocs;
		restMaxx = maxxDigits % numprocs;
		endMaxx = startMaxx + len3;

		for (int i = 1; i < numprocs; i++)
		{
			if (restMaxx > 0)
			{
				endMaxx += 1;
				restMaxx--;
				rMaxx = 1;
			}
			else
			{
				rMaxx = 0;
			}

			MPI_Recv(n3 + startMaxx, lenMaxx + rMaxx, MPI_INT, i, 12345, MPI_COMM_WORLD, &status);

			startMaxx = endMaxx;
			endMaxx += lenMaxx;
		}

		/*

		//std::cout << "\nProcess " << rank << ": ";

		for (int i = 0; i < SIZE; i++)
		{
			//std::cout << c[i] << " ";
		}
		*/


		//std::cout << "noDigits3: " << noDigits3 << '\n';
		f3 << noDigits3 + 1 << '\n';

		//std::cout << "n3: " << '\n';

		for (int i = 0; i < noDigits3 + 1; i++)
		{
			f3 << n3[i] << '\n';
		}

		f3.close();

		if (equalFiles(file3, fileCheck) == false)
		{
			//throw new exception("Fisiere inegale!");
			std::cout << "Fisiere inegale!";
		}
		else
		{
			std::cout << "Fisiere egale!";
		}
	}
	else
	{
		if (rank == 1)
		{
			int carry = 0, sum;
			MPI_Status statusA;
			MPI_Status statusB;
			MPI_Recv(&rMaxx, 1, MPI_INT, 0, 11111, MPI_COMM_WORLD, &statusA);


			int* n1 = new int[lenMaxx + rMaxx];
			int* n2 = new int[lenMaxx + rMaxx];
			int* n3 = new int[lenMaxx + rMaxx];

			MPI_Recv(n1, lenMaxx + rMaxx, MPI_INT, 0, 11111, MPI_COMM_WORLD, &statusA);
			MPI_Recv(n2, lenMaxx + rMaxx, MPI_INT, 0, 11111, MPI_COMM_WORLD, &statusB);

			for (int i = 0; i < lenMaxx + rMaxx; i++)
			{
				//cout << "n1[" << i << "], n2[" << i << "]: " << n1[i] << ", " << n2[i] << '\n';
				sum = n1[i] + n2[i] + carry;

				n3[i] = sum % 10;
				carry = sum / 10;
			}

			if (numprocs == 2)
			{
				MPI_Send(&carry, 1, MPI_INT, 0, 11223, MPI_COMM_WORLD);
			}
			else if (numprocs > 2)
			{
				MPI_Send(&carry, 1, MPI_INT, 2, 11223, MPI_COMM_WORLD);
			}

			//std::cout << "\nn3: " << '\n';

			MPI_Send(n3, lenMaxx + rMaxx, MPI_INT, 0, 12345, MPI_COMM_WORLD);

			//cout << "rMaxx: " << rMaxx;
		}
		else
		{
			int carry = 0, sum;
			MPI_Status statusA;
			MPI_Status statusB;

			MPI_Recv(&rMaxx, 1, MPI_INT, 0, 11111, MPI_COMM_WORLD, &statusA);


			int* n1 = new int[lenMaxx + rMaxx];
			int* n2 = new int[lenMaxx + rMaxx];
			int* n3 = new int[lenMaxx + rMaxx];

			MPI_Recv(n1, lenMaxx + rMaxx, MPI_INT, 0, 11111, MPI_COMM_WORLD, &statusA);
			MPI_Recv(n2, lenMaxx + rMaxx, MPI_INT, 0, 11111, MPI_COMM_WORLD, &statusB);

			MPI_Recv(&carry, 1, MPI_INT, rank - 1, 11223, MPI_COMM_WORLD, &statusA);

			for (int i = 0; i < lenMaxx + rMaxx; i++)
			{
				//cout << "n1[" << i << "], n2[" << i << "]: " << n1[i] << ", " << n2[i] << '\n';
				sum = n1[i] + n2[i] + carry;

				n3[i] = sum % 10;
				carry = sum / 10;
			}

			if (rank == numprocs - 1) {
				MPI_Send(&carry, 1, MPI_INT, 0, 11223, MPI_COMM_WORLD);

			}
			else {
				MPI_Send(&carry, 1, MPI_INT, rank + 1, 11223, MPI_COMM_WORLD);
			}
			MPI_Send(n3, lenMaxx + rMaxx, MPI_INT, 0, 12345, MPI_COMM_WORLD);

			//cout << "rMaxx: " << rMaxx;
		}
	}
	MPI_Finalize();
}

int closestBiggerDivisibleNumber(int nMax, int numprocs) {
	while (nMax % numprocs != 0) {
		nMax++;
	}

	return nMax;
}

void varianta2(int argc, char* argv[], string file1, string file2, string file3, string fileCheck) {
	ifstream f1(file1);
	ifstream f2(file2);
	ofstream f3(file3);

	int noDigits1 = 0, noDigits2 = 0, noDigits3 = 0;

	f1 >> noDigits1;
	f2 >> noDigits2;

	////std::cout << "NoDigits2: " << noDigits2 << '\n';

	int rank, numprocs;

	int j1 = 0, j2 = 0;

	int diff, maxxDigits;


	////std::cout << "Argc: " << argc << '\n';
	////std::cout << "Argv[0]: " << argv[0] << '\n';
	// //std::cout << "Argv[1]: " << argv[1] << '\n';
	////std::cout << "Argv: " << *argv << '\n';
	////std::cout << "Before init\n";

	MPI_Init(&argc, &argv);
	MPI_Comm_rank(MPI_COMM_WORLD, &rank);
	MPI_Comm_size(MPI_COMM_WORLD, &numprocs);

	if (noDigits1 > noDigits2)
	{
		noDigits3 = noDigits1;
		maxxDigits = closestBiggerDivisibleNumber(noDigits1, numprocs);
	}
	else
	{
		noDigits3 = noDigits2;
		maxxDigits = closestBiggerDivisibleNumber(noDigits2, numprocs);
	}

	cout << "maxxDigits: " << maxxDigits << '\n';
	std::cout << "NoDigits3: " << noDigits3 << '\n';

	//int len = SIZE / numprocs;
	int len1 = noDigits1 / numprocs;
	int rest1 = noDigits1 % numprocs;
	int len2 = noDigits2 / numprocs;
	int rest2 = noDigits2 % numprocs;
	int len3 = noDigits3 / numprocs;
	int r1, r2, r3, rest3, rMaxx, restMaxx;
	int lenMaxx = maxxDigits / numprocs;
	restMaxx = maxxDigits % numprocs;

	int start1 = 0;
	int end1 = start1 + len1;
	int start2 = 0;
	int end2 = start2 + len2;
	int start3, end3;
	int startMaxx = 0;
	int endMaxx = startMaxx + lenMaxx;

	/*
	a1 = new int[len];
	b1 = new int[len];
	c1 = new int[len];*/

	std::cout << "\nProcess rank: " << rank << "; Num of processes: " << numprocs << '\n';

	int carry = 0, carryR = 0, sum;
	int* n1 = new int[maxxDigits];
	int* n2 = new int[maxxDigits];
	int* n3 = new int[lenMaxx];
	int* n1R = new int[lenMaxx];
	int* n2R = new int[lenMaxx];
	int* n3S = new int[maxxDigits + 1];

	if (rank == 0)
	{
		for (j1 = 0; j1 < maxxDigits; j1++)
		{
			if (j1 < noDigits1) {
				f1 >> n1[j1];
			}
			else {
				n1[j1] = 0;
			}
		}

		for (j2 = 0; j2 < maxxDigits; j2++)
		{
			if (j2 < noDigits2) {
				f2 >> n2[j2];
			}
			else {
				n2[j2] = 0;
			}
		}
	}

	MPI_Status statusA;

	MPI_Scatter(n1, lenMaxx, MPI_INT, n1R, lenMaxx, MPI_INT, 0, MPI_COMM_WORLD);
	MPI_Scatter(n2, lenMaxx, MPI_INT, n2R, lenMaxx, MPI_INT, 0, MPI_COMM_WORLD);

	if (numprocs > 1 && rank != 0) {
		MPI_Recv(&carry, 1, MPI_INT, rank - 1, 11223, MPI_COMM_WORLD, &statusA);
		cout << "rank received: " << rank << "; carry: " << carry << '\n';
	}

	for (int i = 0; i < lenMaxx; i++) {
		sum = n1R[i] + n2R[i] + carry;

		n3[i] = sum % 10;
		carry = sum / 10;
	}

	if (numprocs > 1 && rank != numprocs - 1) {
		MPI_Send(&carry, 1, MPI_INT, rank + 1, 11223, MPI_COMM_WORLD);
		cout << "rank sent to: " << rank + 1 << "; carry: " << carry << '\n';
	}

	n3S[noDigits3] = carry;
	n3[noDigits3] = carry;

	MPI_Gather(n3, lenMaxx, MPI_INT, n3S, lenMaxx, MPI_INT, 0, MPI_COMM_WORLD);

	/*if (rank == numprocs - 1) {
		cout << "lenMaxx: " << lenMaxx << "; carry: " << carry << '\n';
		n3S[noDigits3] = carry;
		n3[noDigits3] = carry;
	}*/

	if (rank == 0) {
		f3 << noDigits3 + 1 << '\n';

		//std::cout << "n3: " << '\n';

		for (int i = 0; i < noDigits3 + 1; i++)
		{
			f3 << n3S[i] << '\n';
		}

		f3.close();

		if (equalFiles(file3, fileCheck) == false)
		{
			//throw new exception("Fisiere inegale!");
			std::cout << "Fisiere inegale!";
		}
		else
		{
			std::cout << "Fisiere egale!";
		}
	}

	MPI_Finalize();
}

void varianta33(int argc, char* argv[], string file1, string file2, string file3, string fileCheck) {
	int err = 0;
	int a[MAX_NUM_PROCS]; int total = 0;
	MPI_Request send_request;
	MPI_Request recv_request[MAX_NUM_PROCS];

	err = MPI_Init(&argc, &argv);
	int world_size;
	MPI_Comm_size(MPI_COMM_WORLD, &world_size);
	int world_rank;
	MPI_Comm_rank(MPI_COMM_WORLD, &world_rank);

	if (world_rank != 0)
		//transmitere asincrona
		MPI_Isend(&world_rank, 1, MPI_INT, 0, 10, MPI_COMM_WORLD, &send_request);
	else
	{// procesul 0 asteapta de la toate cate un mesaj  cu MPI_Irecv (fara blocare)
	 // apoi verifica (MPI_test) daca au venit sau nu mesajele – daca da le preia si le adauga intr-un string
		string buff = "hello from 0";
		int flag[MAX_NUM_PROCS];
		for (int i = 0; i < world_size; i++) {
			flag[i] = 0;
			MPI_Irecv(a + i, 1, MPI_INT, i, 10, MPI_COMM_WORLD, recv_request + i);
		}
		MPI_Status status[MAX_NUM_PROCS];

		while (total < world_size - 1) {
			for (int i = 1; i < world_size; i++) {
				if (flag[i] == 0) {
					MPI_Test(recv_request + i, &flag[i], &status[i]);
					if (flag[i] != 0) {
						char x = '0';
						x = x + a[i];
						buff += "\nhello from ";
						buff += x;
						total++;
					}
				}
			}
		}
		cout << buff;
	}
	MPI_Finalize();
}

int main(int argc, char* argv[])
{
	string file;

	if (argc > 1) {
		std::cout << "Fisier " << argv[1] << '\n';
		file = argv[1];
	}
	else {
		std::cout << "Fisier 3\n";
		file = "3";
	}

	string path = R"(C:\Users\Andrei\Desktop\UBB\Semestru 5\Programare paralela si distribuita\Laboratoare\Lab 5\)";
	
	string file1 = path + "n1" + file + ".txt";
	string file2 = path + "n2" + file + ".txt";
	string file3 = path + "n3" + file + ".txt";
	string fileCheck = path + "n3S" + file + ".txt";

	auto start = std::chrono::steady_clock::now();
	//secvential(file1, file2, file3, fileCheck);
	varianta1(argc, argv, file1, file2, file3, fileCheck);
	//varianta2(argc, argv, file1, file2, file3, fileCheck);
	//varianta33(argc, argv, file1, file2, file3, fileCheck);
	//varianta3(argc, argv, file1, file2, file3, fileCheck);
	auto end = std::chrono::steady_clock::now();

	auto diff = end - start;

	auto timeDiff = chrono::duration <double, milli>(diff).count();

	cout << '\n' << ((float)timeDiff /*/ 1E6*/);
}

// Run program: Ctrl + F5 or Debug > Start Without Debugging menu
// Debug program: F5 or Debug > Start Debugging menu

// Tips for Getting Started: 
//   1. Use the Solution Explorer window to add/manage files
//   2. Use the Team Explorer window to connect to source control
//   3. Use the Output window to see build output and other messages
//   4. Use the Error List window to view errors
//   5. Go to Project > Add New Item to create new code files, or Project > Add Existing Item to add existing code files to the project
//   6. In the future, to open this project again, go to File > Open > Project and select the .sln file
