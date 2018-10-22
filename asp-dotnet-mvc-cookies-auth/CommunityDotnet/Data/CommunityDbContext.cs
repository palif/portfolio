using Microsoft.EntityFrameworkCore;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using Microsoft.AspNetCore.Mvc;

namespace CommunityDotnet.Data
{
    public class CommunityDbContext : DbContext
    {
        public virtual DbSet<UserPoco> Users { get; set; }
        public virtual DbSet<MessagePoco> Messages { get; set; }

        public CommunityDbContext(){}

        public CommunityDbContext(DbContextOptions<CommunityDbContext> options) : base(options){ }

        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            optionsBuilder.UseSqlServer("Server=localhost,1433; Database=CommunityDb; User=SA; Password=dockerkth18");
        }
    }
}
