using Baschet.Business;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Basket.repositoryes
{
    public class RepositoryException : ApplicationException
    {
        public RepositoryException() { }
        public RepositoryException(String mess) : base(mess) { }
        public RepositoryException(String mess, Exception e) : base(mess, e) { }
    }

    interface IRepository<E>
    {
        bool save(E e);

        E find(Entity id);

        bool delete(Entity id);

        List<E> getAll();
    }
}
