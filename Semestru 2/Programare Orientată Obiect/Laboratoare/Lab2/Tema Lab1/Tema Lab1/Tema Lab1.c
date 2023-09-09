// Tema Lab1.cpp : This file contains the 'main' function. Program execution begins and ends there.
//

#include <stdio.h>

typedef struct { float x1, y1, x2, y2;} segment;

segment coordonate_segment()
{
	/*
	 * Functia citeste de la consola coordonatele a 2 puncte in plan, ce determina capetele unui segment
	 * parametri de intrare - nu exista
	 * returneaza un parametru de tip segment (prin coordonatele capetelor)
	 * 
	 */

	
	float a;
	segment s;

	printf("\n");
	
	printf("Dati coordonatele segmentului: \n");
	
	printf("Prima abscisa: ");
	scanf("%f", &a);
	s.x1 = a;
	
	printf("Prima ordonata: ");
	scanf("%f", &a);
	s.y1 = a;

	printf("A doua abscisa: ");
	scanf("%f", &a);
	s.x2 = a;

	printf("A doua ordonata: ");
	scanf("%f", &a);
	s.y2 = a;

	return s;
}

float minim(float a, float b)
{
	/*
	 * Functia returneaza minimul dintre doua valori reale
	 * a, b - valori reale
	 * se returneaza valoarea minima dintre ele
	 * 
	 */
	
	if (a < b)
	{
		return a;
	}
	else
	{
		return b;
	}
}	 

float maxim(float a, float b)
{
	/*
	 * Functia returneaza maximul dintre doua valori reale
	 * a, b - valori reale
	 * se returneaza valoarea maxima dintre ele
	 *
	 */
	
	if (a > b)
	{
		return a;
	}
	else
	{
		return b;
	}
}

int punctul_apartine(float xp, float yp, float x1, float y1, float x2, float y2)
{
	/*
	 * Functia primeste coordonatele (xp, yp) unui punct si coordonatele unui segment (x1, y1) (x2, y2)
	 * Determina daca punctul apartine sau nu segmentului
	 * xp, yp, x1, y1, x2, y2 - valori reale
	 * se returneaza 1, daca punctul (xp, yp) apartine segmentului, 0 altfel
	 *
	 */
	
	if ( (xp >= minim(x1, x2) && xp <= maxim(x1, x2)) && (yp >= minim(y1, y2) && yp<= maxim(y1, y2)) )
	{
		return 1;
	}
	else
	{
		return 0;
	}
}

void intersectie_segmente(segment s1, segment s2)
{
	/*
	 * Functia primeste doua segmente si afiseaza un mesaj in consola in care se specifica intersectia lor
	 * s1, s2 - parametri de tip segment
	 * se afiseaza un mesaj in consola, nu se returneaza nimic
	 *
	 */
	
	float xp, yp, m1, m2, p1, p2, p3, p4, x1, x2, x3, x4;
	int k;
	int ok1 = 0, ok2 = 0;

	if ((s2.x2 - s2.x1) * (s1.y2 - s1.y1) != (s1.x2 - s1.x1) * (s2.y2 - s2.y1))
	{
		/*
		 *	segmentele au drepte suport cu pante diferite
		 */
		xp = ((s2.x2 - s2.x1) * (s1.x1 * s1.y2 - s1.x2 * s1.y1) + (s1.x2 - s1.x1) * (s2.x2 * s2.y1 - s2.x1 * s2.y2)) / ((s2.x2 - s2.x1) * (s1.y2 - s1.y1) - (s1.x2 - s1.x1) * (s2.y2 - s2.y1));
		
		if (s1.x2 == s1.x1)
		{
			yp = ((s2.y2 - s2.y1) / (s2.x2 - s2.x1)) * (xp - s2.x1) + s2.y1;
		}
		else
		{
			yp = ((s1.y2 - s1.y1) / (s1.x2 - s1.x1)) * (xp - s1.x1) + s1.y1;
		}
		
		if ((xp >= minim(s1.x1, s1.x2) && xp <= maxim(s1.x1, s1.x2)) && (yp >= minim(s1.y1, s1.y2) && yp <= maxim(s1.y1, s1.y2)) && (xp >= minim(s2.x1, s2.x2) && xp <= maxim(s2.x1, s2.x2)) && (yp >= minim(s2.y1, s2.y2) && yp <= maxim(s2.y1, s2.y2)))
		{
			printf("Cele doua segmente se intersecteaza in punctul de coordonate: \n (%f, %f) ", xp, yp);
		}
		else
		{
			printf("Cele doua segmente nu se intersecteaza! \n");
		}
	}
	else
	{
		/*
		 * segmentele au drepte suport cu aceeasi panta sau sunt simultan paralele una cate una cu axele de coordonate
		 */
		if (((s1.x1 == s1.x2) && (s1.y1 == s1.y2)) || ((s2.x1 == s2.x2) && (s2.y1 == s2.y2)))
		{
			if (((s1.x1 == s1.x2) && (s1.y1 == s1.y2)) && ((s2.x1 == s2.x2) && (s2.y1 == s2.y2)))
			{
				/*
				* segmentele sunt puncte
				*/
				if (s1.x2 == s2.x2)
				{
					printf("Cele doua segmente se intersecteaza in punctul de coordonate: \n (%f, %f) ", s1.x1, s1.x1);
				}
				else
				{
					printf("Cele doua segmente nu se intersecteaza! \n");
				}
			}
			else
			{
				/*
				* unul dintre segmente este un punct 
				*/
				if (((s1.x1 == s1.x2) && (s1.y1 == s1.y2)))
				{
					/*
					* primul segment este un punct
					*/
					xp = s1.x1;
					yp = s1.y1;
					if ((xp >= minim(s2.x1, s2.x2) && xp <= maxim(s2.x1, s2.x2)) && (yp >= minim(s2.y1, s2.y2) && yp <= maxim(s2.y1, s2.y2)))
					{
						printf("Cele doua segmente se intersecteaza in punctul de coordonate: \n (%f, %f) ", xp, yp);
					}
					else
					{
						printf("Cele doua segmente nu se intersecteaza! \n");
					}
				}
				else if (((s2.x1 == s2.x2) && (s2.y1 == s2.y2)))
				{
					/*
					* al doilea segment este un punct
					*/
					xp = s2.x1;
					yp = s2.y1;
					if ((xp >= minim(s1.x1, s1.x2) && xp <= maxim(s1.x1, s1.x2)) && (yp >= minim(s1.y1, s1.y2) && yp <= maxim(s1.y1, s1.y2)))
					{
						printf("Cele doua segmente se intersecteaza in punctul de coordonate: \n (%f, %f) ", xp, yp);
					}
					else
					{
						printf("Cele doua segmente nu se intersecteaza! \n");
					}
				}
			}
		}
		else if (s1.x1 == s1.x2 && s2.x1 == s2.x2)
		{
			/*
			 * ambele segmente sunt paralele cu Oy
			 */
			if (s1.x1 != s2.x1)
			{
				printf("Cele doua segmente nu se intersecteaza! \n");
			}
			else
			{
				ok1 = ok1 + punctul_apartine(s1.x1, s1.y1, s2.x1, s2.y1, s2.x2, s2.y2);
				ok2 = ok2 + punctul_apartine(s1.x2, s1.y2, s2.x1, s2.y1, s2.x2, s2.y2);

				if (ok1 + ok2 == 2)
				{
					printf("Cele doua segmente se intersecteaza de-a lungul segmentului de coordonate: \n (%f, %f) (%f, %f) ", s1.x1, s1.y1, s1.x2, s1.y2);
				}
				else if (ok1 == 1)
				{
					if (punctul_apartine(s2.x1, s2.y1, s1.x1, s1.y1, s1.x2, s1.y2) == 1)
					{
						printf("Cele doua segmente se intersecteaza de-a lungul segmentului de coordonate: \n (%f, %f) (%f, %f) ", s1.x1, s1.y1, s2.x1, s2.y1);
					}
					else
					{
						printf("Cele doua segmente se intersecteaza de-a lungul segmentului de coordonate: \n (%f, %f) (%f, %f) ", s1.x1, s1.y1, s2.x2, s2.y2);
					}
				}
				else if (ok2 == 1)
				{
					if (punctul_apartine(s2.x1, s2.y1, s1.x1, s1.y1, s1.x2, s1.y2) == 1)
					{
						printf("Cele doua segmente se intersecteaza de-a lungul segmentului de coordonate: \n (%f, %f) (%f, %f) ", s1.x2, s1.y2, s2.x1, s2.y1);
					}
					else
					{
						printf("Cele doua segmente se intersecteaza de-a lungul segmentului de coordonate: \n (%f, %f) (%f, %f) ", s1.x2, s1.y2, s2.x2, s2.y2);
					}
				}
				else if (ok1 + ok2 == 0)
				{
					ok1 = ok1 + punctul_apartine(s2.x1, s2.y1, s1.x1, s1.y1, s1.x2, s1.y2);
					ok2 = ok2 + punctul_apartine(s2.x2, s2.y2, s1.x1, s1.y1, s1.x2, s1.y2);

					if (ok1 + ok2 == 2)
					{
						printf("Cele doua segmente se intersecteaza de-a lungul segmentului de coordonate: \n (%f, %f) (%f, %f) ", s2.x1, s2.y1, s2.x2, s2.y2);
					}
					else if (ok1 == 1)
					{
						if (punctul_apartine(s1.x1, s1.y1, s2.x1, s2.y1, s2.x2, s2.y2) == 1)
						{
							printf("Cele doua segmente se intersecteaza de-a lungul segmentului de coordonate: \n (%f, %f) (%f, %f) ", s2.x1, s2.y1, s1.x1, s1.y1);
						}
						else
						{
							printf("Cele doua segmente se intersecteaza de-a lungul segmentului de coordonate: \n (%f, %f) (%f, %f) ", s2.x1, s2.y1, s1.x2, s1.y2);
						}
					}
					else if (ok2 == 1)
					{
						if (punctul_apartine(s1.x1, s1.y1, s2.x1, s2.y1, s2.x2, s2.y2) == 1)
						{
							printf("Cele doua segmente se intersecteaza de-a lungul segmentului de coordonate: \n (%f, %f) (%f, %f) ", s2.x2, s2.y2, s1.x1, s1.y1);
						}
						else
						{
							printf("Cele doua segmente se intersecteaza de-a lungul segmentului de coordonate: \n (%f, %f) (%f, %f) ", s2.x2, s2.y2, s1.x2, s1.y2);
						}
					}
					else
					{
						printf("Cele doua segmente nu se intersecteaza! \n");
					}
				}
			}
		}
		else if (s1.y1 == s1.y2 && s2.y1 == s2.y2)
		{
			/*
			* ambele segmente sunt paralele cu Ox
			*/
			if (s1.y1 != s2.y1)
			{
				printf("Cele doua segmente nu se intersecteaza! \n");
			}
			else
			{
				ok1 = ok1 + punctul_apartine(s1.x1, s1.y1, s2.x1, s2.y1, s2.x2, s2.y2);
				ok2 = ok2 + punctul_apartine(s1.x2, s1.y2, s2.x1, s2.y1, s2.x2, s2.y2);

				 if (ok1 + ok2 == 2)
				 {
					 printf("Cele doua segmente se intersecteaza de-a lungul segmentului de coordonate: \n (%f, %f) (%f, %f) ", s1.x1, s1.y1, s1.x2, s1.y2);
				 }
				 else if (ok1 == 1)
				 {
					 if (punctul_apartine(s2.x1, s2.y1, s1.x1, s1.y1, s1.x2, s1.y2) == 1)
					 {
						 printf("Cele doua segmente se intersecteaza de-a lungul segmentului de coordonate: \n (%f, %f) (%f, %f) ", s1.x1, s1.y1, s2.x1, s2.y1);
					 }
					 else
					 {
						 printf("Cele doua segmente se intersecteaza de-a lungul segmentului de coordonate: \n (%f, %f) (%f, %f) ", s1.x1, s1.y1, s2.x2, s2.y2);
					 }
				 }
				 else if (ok2 == 1)
				 {
					 if (punctul_apartine(s2.x1, s2.y1, s1.x1, s1.y1, s1.x2, s1.y2) == 1)
					 {
						 printf("Cele doua segmente se intersecteaza de-a lungul segmentului de coordonate: \n (%f, %f) (%f, %f) ", s1.x2, s1.y2, s2.x1, s2.y1);
					 }
					 else
					 {
						 printf("Cele doua segmente se intersecteaza de-a lungul segmentului de coordonate: \n (%f, %f) (%f, %f) ", s1.x2, s1.y2, s2.x2, s2.y2);
					 }
				 }
				 else if (ok1 + ok2 == 0)
				 {
					 ok1 = ok1 + punctul_apartine(s2.x1, s2.y1, s1.x1, s1.y1, s1.x2, s1.y2);
					 ok2 = ok2 + punctul_apartine(s2.x2, s2.y2, s1.x1, s1.y1, s1.x2, s1.y2);

					 if (ok1 + ok2 == 2)
					 {
						 printf("Cele doua segmente se intersecteaza de-a lungul segmentului de coordonate: \n (%f, %f) (%f, %f) ", s2.x1, s2.y1, s2.x2, s2.y2);
					 }
					 else if (ok1 == 1)
					 {
						 if (punctul_apartine(s1.x1, s1.y1, s2.x1, s2.y1, s2.x2, s2.y2) == 1)
						 {
							 printf("Cele doua segmente se intersecteaza de-a lungul segmentului de coordonate: \n (%f, %f) (%f, %f) ", s2.x1, s2.y1, s1.x1, s1.y1);
						 }
						 else
						 {
							 printf("Cele doua segmente se intersecteaza de-a lungul segmentului de coordonate: \n (%f, %f) (%f, %f) ", s2.x1, s2.y1, s1.x2, s1.y2);
						 }
					 }
					 else if (ok2 == 1)
					 {
						 if (punctul_apartine(s1.x1, s1.y1, s2.x1, s2.y1, s2.x2, s2.y2) == 1)
						 {
							 printf("Cele doua segmente se intersecteaza de-a lungul segmentului de coordonate: \n (%f, %f) (%f, %f) ", s2.x2, s2.y2, s1.x1, s1.y1);
						 }
						 else
						 {
							 printf("Cele doua segmente se intersecteaza de-a lungul segmentului de coordonate: \n (%f, %f) (%f, %f) ", s2.x2, s2.y2, s1.x2, s1.y2);
						 }
					 }
					 else
					 {
						 printf("Cele doua segmente nu se intersecteaza! \n");
					 }
				 }
			}
		}
		else
		{
		/*
		 * ambele segmente au aceeasi panta
		 */
		 m1 = ((s1.y2 - s1.y1) / (s1.x2 - s1.x1));
		 m2 = ((s2.y2 - s2.y1) / (s2.x2 - s2.x1));
		 if ( (s1.y1 - s2.y1 - m2 * (s1.x1 - s2.x1) ) != 0)
		 {
			 printf("Cele doua segmente nu se intersecteaza! \n");
		 }
		 else
		 {
			 ok1 = ok1 + punctul_apartine(s1.x1, s1.y1, s2.x1, s2.y1, s2.x2, s2.y2);
			 ok2 = ok2 + punctul_apartine(s1.x2, s1.y2, s2.x1, s2.y1, s2.x2, s2.y2);

			 if (ok1 + ok2 == 2)
			 {
				 printf("Cele doua segmente se intersecteaza de-a lungul segmentului de coordonate: \n (%f, %f) (%f, %f) ", s1.x1, s1.y1, s1.x2, s1.y2);
			 }
			 else if (ok1 == 1)
			 {
				 if (punctul_apartine(s2.x1, s2.y1, s1.x1, s1.y1, s1.x2, s1.y2) == 1)
				 {
					 printf("Cele doua segmente se intersecteaza de-a lungul segmentului de coordonate: \n (%f, %f) (%f, %f) ", s1.x1, s1.y1, s2.x1, s2.y1);
				 }
				 else
				 {
					 printf("Cele doua segmente se intersecteaza de-a lungul segmentului de coordonate: \n (%f, %f) (%f, %f) ", s1.x1, s1.y1, s2.x2, s2.y2);
				 }
			 }
			 else if (ok2 == 1)
			 {
				 if (punctul_apartine(s2.x1, s2.y1, s1.x1, s1.y1, s1.x2, s1.y2) == 1)
				 {
					 printf("Cele doua segmente se intersecteaza de-a lungul segmentului de coordonate: \n (%f, %f) (%f, %f) ", s1.x2, s1.y2, s2.x1, s2.y1);
				 }
				 else
				 {
					 printf("Cele doua segmente se intersecteaza de-a lungul segmentului de coordonate: \n (%f, %f) (%f, %f) ", s1.x2, s1.y2, s2.x2, s2.y2);
				 }
			 }
			 else if (ok1 + ok2 == 0)
			 {
				 ok1 = ok1 + punctul_apartine(s2.x1, s2.y1, s1.x1, s1.y1, s1.x2, s1.y2);
				 ok2 = ok2 + punctul_apartine(s2.x2, s2.y2, s1.x1, s1.y1, s1.x2, s1.y2);

				 if (ok1 + ok2 == 2)
				 {
					 printf("Cele doua segmente se intersecteaza de-a lungul segmentului de coordonate: \n (%f, %f) (%f, %f) ", s2.x1, s2.y1, s2.x2, s2.y2);
				 }
				 else if (ok1 == 1)
				 {
					 if (punctul_apartine(s1.x1, s1.y1, s2.x1, s2.y1, s2.x2, s2.y2) == 1)
					 {
						 printf("Cele doua segmente se intersecteaza de-a lungul segmentului de coordonate: \n (%f, %f) (%f, %f) ", s2.x1, s2.y1, s1.x1, s1.y1);
					 }
					 else
					 {
						 printf("Cele doua segmente se intersecteaza de-a lungul segmentului de coordonate: \n (%f, %f) (%f, %f) ", s2.x1, s2.y1, s1.x2, s1.y2);
					 }
				 }
				 else if (ok2 == 1)
				 {
					 if (punctul_apartine(s1.x1, s1.y1, s2.x1, s2.y1, s2.x2, s2.y2) == 1)
					 {
						 printf("Cele doua segmente se intersecteaza de-a lungul segmentului de coordonate: \n (%f, %f) (%f, %f) ", s2.x2, s2.y2, s1.x1, s1.y1);
					 }
					 else
					 {
						 printf("Cele doua segmente se intersecteaza de-a lungul segmentului de coordonate: \n (%f, %f) (%f, %f) ", s2.x2, s2.y2, s1.x2, s1.y2);
					 }
				 }
				 else
				 {
					 printf("Cele doua segmente nu se intersecteaza! \n");
				 }
			 }
		 }
		}
	}
	printf("\n");
}

void program()
{
	/*
	 * Functie de tip controller
	 * 
	 */
	
	segment s1, s2;
	float m1, m2;
	
	s1 = coordonate_segment();
	s2 = coordonate_segment();

	printf("\n Cele doua segmente citite sunt: \n");
	printf("(%f, %f), (%f, %f)", s1.x1, s1.y1, s1.x2, s1.y2);
	printf("\n");
	printf("(%f, %f), (%f, %f)", s2.x1, s2.y1, s2.x2, s2.y2);
	printf("\n");
	printf("\n");
	
	intersectie_segmente(s1, s2);

}

void problema_3()
{
	int n, i, j, k, ok=0, s;

	printf("\n Dati un numar n \n n=");
	scanf("%d", &n);

	printf("\n Modurile in care numarul %d poate fi scris ca suma de numere consecutive sunt: \n", n);

	for (i=1; i<n; i++)
	{
		s = 0;
		for (j=i; j<=n; j++)
		{
			s = s + j;
			if (s == n)
			{
				for (k=i; k<=j; k++)
				{
					printf("%d ", k);
				}
				printf("\n");
			}
		}
	}
	
}

void meniu()
{
	/*
	 * Functie de tip UI
	 * 
	 */

	int opt = 0, a;

	printf("\n Doriti sa accesati: \n Problema 3 - tasta 3 \n Problema 11 - tasta 11 \n ");
	printf("Optiunea dumneavoastra este: ");
	scanf("%d", &a);

	if (a == 3)
	{
		problema_3();
	}
	else if (a == 11)
	{
		program();
	}
	else
	{
		printf("Optiunea dumneavoastra nu exista! \n");
	}
	

	printf("\n Doriti repetarea programului? \n 1. Da - tasta 1 \n 2. Nu - tasta 0 \n");
	printf("Optiunea dumneavoastra este: ");
	scanf("%d", &a);
	if (a == 1)
	{
		meniu();
	}
}

int main()
{
	printf("Inceput program. \n");
	meniu();
	printf("Sfarsit program!");
	return 0;
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
