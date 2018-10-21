using System;
using System.Collections.Generic;
using community_dotnet.Models;
using community_dotnet.Controllers;

namespace community_dotnet
{
    public class Test
    {

        public User from = new User
        {
            Username = "sanyang@kth.se"
        };

        public User to = new User
        {
            Username = "hamdi@kth.se"
        };

        public void run(){
            var model = new Model();

            var message = new Message
            {
                FromUser = from,
                ToUser = to,
                TextMessage = "Hello World Text",
                IsSeen = false
            };

            model.SendMessage(message);


            
        }


    }
}
