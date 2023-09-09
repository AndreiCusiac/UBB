using System;
using System.Collections.Generic;
using System.Text;

namespace Baschet.models
{
    public class Match : Entity
    {
        public string homeTeam { get; set; }
        public string awayTeam { get; set; }

        public float ticketPrice {get; set; }
        public int totalSeats {get; set; }
        public int availableSeats {get; set; }

        public Match(string id, string homeTeam, string awayTeam, float ticketPrice, int totalSeats, int availableSeats)
        {
            this.id = id;
            this.homeTeam = homeTeam;
            this.awayTeam = awayTeam;
            this.ticketPrice = ticketPrice;
            this.totalSeats = totalSeats;
            this.availableSeats = availableSeats;
        }

        public Match(string id, string homeTeam, string awayTeam, float ticketPrice, int availableSeats)
        {
            this.id = id;
            this.homeTeam = homeTeam;
            this.awayTeam = awayTeam;
            this.ticketPrice = ticketPrice;
            this.availableSeats = availableSeats;
        }

        public Match(string homeTeam, string awayTeam, float ticketPrice, int totalSeats, int availableSeats)
        {
            this.homeTeam = homeTeam;
            this.awayTeam = awayTeam;
            this.ticketPrice = ticketPrice;
            this.totalSeats = totalSeats;
            this.availableSeats = availableSeats;
        }

        public Match(string homeTeam, string awayTeam, float ticketPrice, int availableSeats)
        {
            this.homeTeam = homeTeam;
            this.awayTeam = awayTeam;
            this.ticketPrice = ticketPrice;
            this.availableSeats = availableSeats;
        }
        
        /*
        public string getId()
        {
            return id;
        }

        public void setId(string id)
        {
            this.id = id;
        }

        public string getHomeTeam()
        {
            return homeTeam;
        }

        public void setHomeTeam(string homeTeam)
        {
            this.homeTeam = homeTeam;
        }

        public string getAwayTeam()
        {
            return awayTeam;
        }

        public void setAwayTeam(string awayTeam)
        {
            this.awayTeam = awayTeam;
        }

        public float getTicketPrice()
        {
            return ticketPrice;
        }

        public void setTicketPrice(float ticketPrice)
        {
            this.ticketPrice = ticketPrice;
        }

        public int getTotalSeats()
        {
            return totalSeats;
        }

        public void setTotalSeats(int totalSeats)
        {
            this.totalSeats = totalSeats;
        }

        public int getAvailableSeats()
        {
            return availableSeats;
        }

        public void setAvailableSeats(int availableSeats)
        {
            this.availableSeats = availableSeats;
        }*/
    }
}
