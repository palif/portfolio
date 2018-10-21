using System;
namespace community_dotnet.Models
{
    public class User
    {
        public string Username { get; set; }
        public long LatestLogin { get; set; }
        public int NumberOfLoginsInMonth { get; set; }
        public int NumberOfUnreadMessages { get; set; }
        public int NumberOfMessageSent { get; set; }
    }
}
