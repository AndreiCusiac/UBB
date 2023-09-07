#include <iostream>
#include <fstream>
#include <vector>
using namespace std;

#define MAX 9999
#define SOURCE 0

struct muchie
{
    int flow, cap;

    int dela, catre;

    muchie(int flow, int capacity, int u, int v)
    {
        this->flow = flow;
        this->cap = cap;
        this->dela = u;
        this->catre = catre;
    }
};

struct nod
{
    int height, exces;

    nod(int h, int e_flow)
    {
        this->height = h;
        this->exces = e_flow;
    }
};


class Graf
{
    vector<nod> gf;
    vector<muchie> adj;

    int noduri;
    int muchii;

    int sum = 0;

    vector<int> start;
	
    int centre;
	
    string nume;

    bool push(int u);

    void relabel(int u);

    void preflow(int s);

    void updateReverseEdgeFlow(int i, int flow);


public:
	
    Graf()
    {
        noduri = muchii = 0;
    }

    Graf(const string& n) : nume{ n }
    {

    }

    Graf(int n, int m, int c, const string& ne) : noduri{ n }, muchii{ m }, centre{ c }, nume{ ne }
    {
        gf.resize(noduri + 1);

        gf[SOURCE].height = noduri + 1;
        gf[SOURCE].exces = MAX;

        for (int i = 1; i <= c; i++)
        {
            adj.push_back(muchie{ SOURCE, i, MAX, 0 });
        }

    }

    void set_noduri(int n)
    {
        noduri = n;
    }

    void set_muchii(int m)
    {
        muchii = m;
    }

    void adaugaMuchie(int a, int b, int pon);
    
    int pompare_de_preflux(int s);

    void print_muchii()
    {
        cout << endl;

        for (int i = 0; i < adj.size(); i++)
        {
            cout << "De la " << adj[i].dela << " catre " << adj[i].catre << ", cap=" << adj[i].cap << ", flux=" << adj[i].flow << endl;
        }

        cout << endl;
        cout << endl;
    }

    void print_start()
    {
        cout << "Suma: " << sum;
        cout << endl << "Start: ";

        for (auto i : start)
        {
            cout << i << " ";
        }

        cout << endl;
    }

    void print_fisier()
    {
        string name = nume + "-out.txt";
        ofstream of(name);

        of << sum << endl;
        for (auto i : start)
        {
            of << i << " ";
        }
    }
};

void Graf::adaugaMuchie(int a, int b, int pon)
{
    adj.push_back(muchie(0, pon, a, b));
}

void Graf::preflow(int s)
{
    gf[s].height = gf.size() + 1;

    for (int i = 0; i < adj.size(); i++)
    {
        if (adj[i].dela == s)
        {
            adj[i].flow = adj[i].cap;
            gf[adj[i].catre].exces += adj[i].flow;
            adj.push_back(muchie(-adj[i].flow, 0, adj[i].catre, s));
        }
    }
}

int overFlowVertex(vector<nod>& ver)
{
    for (int i = 1; i < ver.size() - 1; i++)
        if (ver[i].exces > 0)
            return i;

    return -1;
}

void Graf::updateReverseEdgeFlow(int i, int flow)
{
    int u = adj[i].catre, v = adj[i].dela;

    for (int j = 0; j < adj.size(); j++)
    {
        if (adj[j].catre == v && adj[j].dela == u)
        {
            adj[j].flow -= flow;
            return;
        }
    }

    muchie e = muchie(0, flow, u, v);
    adj.push_back(e);
}

bool Graf::push(int u)
{
    for (int i = 0; i < adj.size(); i++)
    {
        if (adj[i].dela == u)
        {
            if (adj[i].flow == adj[i].cap)
                continue;

            if (gf[u].height == gf[adj[i].catre].height +1)
            {
                int flow = min(adj[i].cap - adj[i].flow,
                    gf[u].exces);

                gf[u].exces -= flow;

                gf[adj[i].catre].exces += flow;

                adj[i].flow += flow;

                updateReverseEdgeFlow(i, flow);

                return true;
            }
        }
    }
    return false;
}

void Graf::relabel(int u)
{
    int mh = INT_MAX;
    
    for (int i = 0; i < adj.size(); i++)
    {
        if (adj[i].dela == u)
        {
            if (adj[i].flow == adj[i].cap)
                continue;

            if (gf[adj[i].catre].height < mh)
            {
                mh = gf[adj[i].catre].height;

                gf[u].height = mh + 1;
            }
        }
    }
}

int Graf::pompare_de_preflux(int s)
{
    preflow(s);

    while (overFlowVertex(gf) != -1)
    {
        int u = overFlowVertex(gf);
        if (!push(u))
            relabel(u);
    }

    return gf.back().exces;
}



int citire_graf(Graf& G, string nume)
{
    auto f_name = nume + ".txt";

    ifstream f(f_name);

    int n, m, a, b, c;

    f >> n >> c >> m;

    if (n < 0 || m < 0 || c < 0)
    {
        return -1;
    }

    Graf F{ n, m, c, nume };

    for (int i = 1; i <= m; i++)
    {
        f >> a >> b >> c;
        F.adaugaMuchie(a, b, c);
    }

    G = F;

    return 1;
}

int consola()
{
    string nume;

    cout << "Dati numele fisierului: ";

    cin >> nume;

    if (nume == "stop")
    {
        return 0;
    }

    Graf G{ nume };

    auto i = citire_graf(G, nume);

    if (i == -1)
    {
        cout << "Eroare\n\n";
        return -1;
    }

    G.print_muchii();
    cout << endl << G.pompare_de_preflux(SOURCE);
   // G.print_muchii();

  //  G.print_start();

    return 1;
}

int main()
{
    while (1)
    {
        if (consola() == 0)
        {
            break;
        }
    };

    return 0;
}