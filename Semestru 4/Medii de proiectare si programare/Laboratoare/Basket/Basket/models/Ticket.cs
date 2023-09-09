using System;
using System.Collections.Generic;
using System.Text;

namespace Baschet.models
{
    public class Ticket : Entity
    {
        public string  idMatch { get; set; }

        public string name { get; set; }
        public int seats { get; set; }

        public Ticket(string idTicket, string idMatch, string name, int seats) {
            this.id = idTicket;
            this.idMatch = idMatch;
            this.name = name;
            this.seats = seats;
        }
        
        public Ticket(string idMatch, string name, int seats) {
            this.idMatch = idMatch;
            this.name = name;
            this.seats = seats;
        }

        
    }
}
