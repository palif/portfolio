using System.ComponentModel.DataAnnotations;

namespace community_dotnet.Models
{
    public class UserPoco
    {
        /// <summary>
        /// Gets or sets the username.
        /// </summary>
        /// <value>The username.</value>
        [Key, Required]
        public string Username { get; set; }

        /// <summary>
        /// Gets or sets the password.
        /// </summary>
        /// <value>The password.</value>
        [Required]
        public string Password { get; set; }

        /// <summary>
        /// Gets or sets the latest login.
        /// </summary>
        /// <value>The latest login.</value>
        public long LatestLogin { get; set; }

        /// <summary>
        /// Gets or sets the number of logins in month.
        /// </summary>
        /// <value>The number of logins in month.</value>
        public int NumberOfLoginsInMonth { get; set; }

        /// <summary>
        /// Gets or sets the number of unread messages.
        /// </summary>
        /// <value>The number of unread messages.</value>
        public int NumberOfUnreadMessages { get; set; }

        /// <summary>
        /// Gets or sets the number of message sent.
        /// </summary>
        /// <value>The number of message sent.</value>
        public int NumberOfMessageSent { get; set; }

        /// <summary>
        /// Gets or sets the number of message recv.
        /// </summary>
        /// <value>The number of message recv.</value>
        public int NumberOfMessageRecv { get; set; }
    }
}
