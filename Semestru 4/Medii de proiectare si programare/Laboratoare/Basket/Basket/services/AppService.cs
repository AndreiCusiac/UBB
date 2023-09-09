using Basket.Business;
using java.util;
using java.util.stream;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Basket.services
{
    class AppService
    {
        TicketBooth currentTicketBooth;

        public TicketBooth getCurrentTicketBooth()
        {
            return currentTicketBooth;
        }

        public void setCurrentTicketBooth(TicketBooth currentTicketBooth)
        {
            this.currentTicketBooth = currentTicketBooth;
        }

        int numberOfTickets;

        MatchService matchService;
        TicketBoothService ticketBoothService;
        TicketService ticketService;

        public AppService(MatchService matchService, TicketBoothService ticketBoothService, TicketService ticketService)
        {
            this.matchService = matchService;
            this.ticketBoothService = ticketBoothService;
            this.ticketService = ticketService;
        }

        public bool saveMatch(Match match)
        {
            return matchService.save(match);
        }

        public bool deleteMatch(Match match)
        {
            return matchService.delete(match);
        }

        public Match findMatch(Match match)
        {
            return matchService.find(match);
        }

        public List<Match> getAllMatches()
        {
            return matchService.getAll();
        }

        public List<Match> getMatchesSortedByAvailableSeatsDesc()
        {

            var list = matchService.getAll();

            List<Match> sortedMatches = list.OrderByDescending(o => o.availableSeats).ToList();

            return sortedMatches;

        }

        public void sellTicket(Match match, String name, int seats)
        {

            numberOfTickets = getAllMTickets().Count();
            numberOfTickets += 1;

            Ticket ticket = new Ticket(numberOfTickets.ToString(), match.id, name, seats);
            ticketService.save(ticket);

            matchService.updateAvailableSeats(match, seats);
        }



        public bool saveTicketBooth(TicketBooth ticketBooth)
        {
            return ticketBoothService.save(ticketBooth);
        }

        public bool deleteTicketBooth(TicketBooth ticketBooth)
        {
            return ticketBoothService.delete(ticketBooth);
        }

        public TicketBooth findTicketBooth(TicketBooth ticketBooth)
        {
            return ticketBoothService.find(ticketBooth);
        }

        public List<TicketBooth> getAllMTicketBooths()
        {
            return ticketBoothService.getAll();
        }



        public bool saveTicket(Ticket ticket)
        {
            return ticketService.save(ticket);
        }

        public bool deleteTicket(Ticket ticket)
        {
            return ticketService.delete(ticket);
        }

        public Ticket findTicket(Ticket ticket)
        {
            return ticketService.find(ticket);
        }

        public List<Ticket> getAllMTickets()
        {
            return ticketService.getAll();
        }


        public TicketBooth getTicketBoothByName(String name)
        {
            var list = ticketBoothService.getAll();

            List<TicketBooth> filtered = list.Where(x => x.name.Equals(name)).ToList();

            return filtered.ElementAt(0);
        }

        public bool hasTicketBoothByName(String name)
        {
            var list = ticketBoothService.getAll();

            List<TicketBooth> filtered = list.Where(x => x.name.Equals(name)).ToList();

            return filtered.Count() > 0;
        }

        public bool isAuthValid(String currentName, String currentPassword)
        {
            if (hasTicketBoothByName(currentName) == false) {
                return false;
            }

            TicketBooth ticketBooth = getTicketBoothByName(currentName);

            if (ticketBooth.password.Equals(currentPassword) == false) {
                return false;
            }

            /*SecureRandom random = new SecureRandom();
            byte[] salt = new byte[16];
            random.nextBytes(salt);

            System.out.println("Salt = " + toHex(salt) + " " + toHex(salt).getClass());
            System.out.println("Salt = " + toHex(salt).getBytes() + " " + toHex(salt).getBytes().getClass() + " " + toHex(salt).getBytes().toString().getBytes());

            KeySpec spec = new PBEKeySpec(currentPassword.toCharArray(), "[B@2e517bd9".getBytes(), 65536, 128);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

            byte[] hash = factory.generateSecret(spec).getEncoded();

            System.out.println("Hash = " + toHex(hash));
            System.out.println("\n");
            System.out.println(hash.length);

            return false;
    /**/
            //String currentStoredSalt = getSaltByEmail(currentEmail);
            //String currentStoredSalt = "[B@7d9f52cd";
            //String currentStoredHash = getHashByEmail(currentEmail);

            //var generatedHash = generateHashForCurrent(currentPassword, currentStoredSalt.getBytes());

            //System.out.println("Hash = " + generatedHash.toString());
            //var z = generatedHash;

            //if (generatedHash.equals(currentStoredHash) == false) {
            //    return false;
            //}

            return true;
    }
}
}
