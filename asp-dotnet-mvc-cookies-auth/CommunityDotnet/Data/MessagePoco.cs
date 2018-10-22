using System.ComponentModel.DataAnnotations;

namespace CommunityDotnet.Data
{
    public class MessagePoco
    {
        /// <summary>
        /// Gets or sets the message identifier.
        /// </summary>
        /// <value>The message identifier.</value>
        [Key, Required]
        public int MessageId { get; set; }

        /// <summary>
        /// Gets or sets the timestamp.
        /// </summary>
        /// <value>The timestamp.</value>
        [Required]
        public string Timestamp { get; set; }

        /// <summary>
        /// Gets or sets a value indicating whether this message is seen.
        /// </summary>
        /// <value><c>true</c> if is seen; otherwise, <c>false</c>.</value>
        public bool IsSeen { get; set; }

        /// <summary>
        /// Gets or sets the text message.
        /// </summary>
        /// <value>The text message.</value>
        public string TextMessage { get; set; }

        /// <summary>
        /// Gets or sets to user.
        /// </summary>
        /// <value>To user.</value>
        public virtual UserPoco ToUser { get; set; }

        /// <summary>
        /// Gets or sets from user.
        /// </summary>
        /// <value>From user.</value>
        public virtual UserPoco FromUser { get; set; }
    }
}
