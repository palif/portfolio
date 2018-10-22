using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Http;

namespace CommunityDotnet.ViewModels
{
    // You may need to install the Microsoft.AspNetCore.Http.Abstractions package into your project
    public class SendViewModel
    {
        public string To { get; set; }
        public string From { get; set; }
        public string Message { get; set; }
        public List<string> ListOfContacts { get; set; }
    }


}
