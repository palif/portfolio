using System;
namespace CommunityDotnet.Models
{
    public class User
    {
        public string Username { get; set; }
        public string Password { get; set; }
        public string LatestLogin { get; set; }
        public int NumberOfLoginsInMonth { get; set; }
        public int NumberOfUnreadMessages { get; set; }
        public int NumberOfMessageSent { get; set; }
    }
}
