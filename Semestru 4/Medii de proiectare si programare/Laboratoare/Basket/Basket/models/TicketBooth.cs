using System;
using System.Collections.Generic;
using System.Text;

namespace Baschet.models
{
    public class TicketBooth : Entity
    {
        public string name { get; set; }
        
        public string password { get; set; }
        public string salt { get; set; }
        public string hash { get; set; }

        public TicketBooth(string id, string name, string password, string salt, string hash) {
            this.id = id;
            this.name = name;
            this.password = password;
            this.salt = salt;
            this.hash = hash;
        }

        public TicketBooth(string id, string name) {
            this.id = id;
            this.name = name;
        }

        public TicketBooth(string name) {
            this.name = name;
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

        public string getName()
        {
            return name;
        }

        public void setName(string name)
        {
            this.name = name;
        }

        public string getSalt()
        {
            return salt;
        }

        public void setSalt(string salt)
        {
            this.salt = salt;
        }

        public string getHash()
        {
            return hash;
        }

        public void setHash(string hash)
        {
            this.hash = hash;
        }*/
    }
}
