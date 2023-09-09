using Baschet.Business;
using Basket.Business;
using Basket.repositoryes;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Basket.services
{
    class TicketService
    {
        TicketRepository ticketRepository;

        public TicketService(TicketRepository ticketRepository)
        {
            this.ticketRepository = ticketRepository;
        }

        public bool save(Ticket ticket)
        {
            return ticketRepository.save(ticket);
        }

        public bool delete(Ticket ticket)
        {
            Entity entity = new Entity();
            entity.id = ticket.id;

            return ticketRepository.delete(entity);
        }

        public Ticket find(Ticket ticket)
        {
            Entity entity = new Entity();
            entity.id = ticket.id;

            return ticketRepository.find(entity);
        }

        public List<Ticket> getAll()
        {
            return ticketRepository.getAll();
        }
    }
}
