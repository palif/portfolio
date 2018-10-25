using System;
namespace CommunityDotnet.Models
{
    public class Message
    {
        public int MessageId { get; set; }
        public string Timestamp { get; set; }
        public bool IsSeen { get; set; }
        public string TextMessage { get; set; }
        public User ToUser { get; set; }
        public User FromUser { get; set; }

        public override string ToString()
        {
            return "From: " + FromUser.Username + ", To: " + ToUser.Username 
                      + ", Message: \n" + this.TextMessage + ", Time: " + DateTime.Now.ToString();
        }
    }
}
