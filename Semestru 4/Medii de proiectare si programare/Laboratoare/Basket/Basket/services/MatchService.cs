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
    class MatchService
    {
        MatchRepository matchRepository;

        public MatchService(MatchRepository matchRepository)
        {
            this.matchRepository = matchRepository;
        }

        public bool save(Match match)
        {
            return matchRepository.save(match);
        }

        public bool delete(Match match)
        {
            Entity entity = new Entity();
            entity.id = match.id;
            
            return matchRepository.delete(entity);
        }

        public Match find(Match match)
        {
            Entity entity = new Entity();
            entity.id = match.id;

            return matchRepository.find(entity);
        }

        public List<Match> getAll()
        {
            return matchRepository.getAll();
        }

        public void updateAvailableSeats(Match match, int seats)
        {
            Entity entity = new Entity();
            entity.id = match.id;

            matchRepository.updateAvailableSeats(entity, match, seats);
        }
    }
}
