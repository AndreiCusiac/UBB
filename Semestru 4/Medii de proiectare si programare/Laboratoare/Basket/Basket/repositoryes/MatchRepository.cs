﻿using Baschet.Business;
using Basket.Business;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Basket.repositoryes
{
    interface MatchRepository : IRepository<Match>
    {
        bool updateAvailableSeats(Entity ide, Match matchToUpdate, int numberOfSeats);
    }
}
