using Microsoft.EntityFrameworkCore;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using Microsoft.AspNetCore.Mvc;

namespace community_dotnet.Models
{
    public class Database : DbContext
    {
        public virtual DbSet<UserPoco> Users { get; set; }
        public virtual DbSet<MessagePoco> Messages { get; set; }

        public Database(){}

        public Database(DbContextOptions<Database> options) : base(options){ }

        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            optionsBuilder.UseSqlServer("Server=localhost,1433; Database=CommunityDb; User=SA; Password=dockerkth18");
        }
    }
}
