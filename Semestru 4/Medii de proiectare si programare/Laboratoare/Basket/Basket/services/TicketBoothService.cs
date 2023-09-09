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
    class TicketBoothService
    {
        TicketBoothRepository ticketBoothRepository;

        public TicketBoothService(TicketBoothRepository ticketBoothRepository)
        {
            this.ticketBoothRepository = ticketBoothRepository;
        }

        public bool save(TicketBooth ticketBooth)
        {
            return ticketBoothRepository.save(ticketBooth);
        }

        public bool delete(TicketBooth ticketBooth)
        {
            Entity entity = new Entity();
            entity.id = ticketBooth.id;

            return ticketBoothRepository.delete(entity);
        }

        public TicketBooth find(TicketBooth ticketBooth)
        {
            Entity entity = new Entity();
            entity.id = ticketBooth.id;

            return ticketBoothRepository.find(entity);
        }

        public List<TicketBooth> getAll()
        {
            return ticketBoothRepository.getAll();
        }
    }
}
