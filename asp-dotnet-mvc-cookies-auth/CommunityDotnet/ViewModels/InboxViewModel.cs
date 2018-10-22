using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using Microsoft.AspNetCore.Mvc.Rendering;
using CommunityDotnet.Models;

namespace CommunityDotnet.ViewModels
{
    public class InboxViewModel
    {

        public List<User> AllSenderViewModel { get; set; }

        public List<Message> SenderMessages { get; set; }

        public string SelectedSender { get; set; }

        public Message SelectedMessage { get; set; } 

        public int NumberOfUnreadMessage { get; set; }

        public int NumberOfMessages { get; set; }

    }
}
