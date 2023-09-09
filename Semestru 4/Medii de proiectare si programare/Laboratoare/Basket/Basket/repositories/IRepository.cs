using Baschet.models;
using System;
using System.Collections;
using System.Collections.Generic;
using System.Text;

namespace Baschet.repositories
{
    interface IRepository<E>
    {
        public bool save(E e);

        public bool delete(E e);

        public List<E> getAll();
    }
}
